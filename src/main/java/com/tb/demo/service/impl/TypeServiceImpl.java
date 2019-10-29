package com.tb.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.tb.demo.mapper.TypeMapper;
import com.tb.demo.pojo.Tag;
import com.tb.demo.pojo.Type;
import com.tb.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Map> getAllTypesAndTags() {

        List<Map> types = typeMapper.selectType();
        for (Map map : types) {
            List<String> tags = typeMapper.selectTagsByType((Integer) map.get("id"));
            map.put("tags", tags);
        }

        return types;
    }

    @Override
    public List<String> getAllTypes() {
        return typeMapper.selectTypes();
    }

    @Override
    public List<Map> getAllType() {
        return typeMapper.selectType();
    }

    @Override
    public List<String> getTagsByType(Integer type) {
        return typeMapper.selectTagsByType(type);
    }

    @Override
    public Map getTypeAndTagsById(Integer id) {
        String type = typeMapper.seleteTypeById(id);
        List<Map> tags = typeMapper.selectTagByType(id);

        Map result = new HashMap();
        result.put("type", type);
        result.put("tags", tags);

        return result;
    }

    @Override
    public String addTypeAndTags(String type, String tags) {
        Type t = new Type();
        t.setType(type);
        int i = typeMapper.addType(t);

        List<String> tagList = JSON.parseArray(tags, String.class);
        if (i == 1) {
            if (tagList.size() > 0) {
                typeMapper.addTags(t.getId(),tagList);
            }
            return "success";
        }
        return "failed";
    }

    @Override
    public int addType(String type) {

        Type t = new Type();
        t.setType(type);
        typeMapper.addType(t);

        return t.getId();
    }

    @Override
    public int addTag(String type, String tag) {
        Tag t = new Tag();
        t.setType(type);
        t.setTag(tag);
        int i = typeMapper.addTag(t);
        return t.getId();
    }

    @Override
    public int deleteType(Integer id) {
        int i = typeMapper.deleteType(id);
        if (i > 0) {
            typeMapper.deleteTagByType(id);
        }
        return i;
    }

    @Override
    public int modifyType(Integer id, String type) {
        return typeMapper.modifyType(id, type);
    }

    @Override
    public int deleteTag(Integer id) {
        return typeMapper.deleteTag(id);
    }
}
