package com.tb.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.tb.demo.mapper.DemoMapper;
import com.tb.demo.mapper.FileMapper;
import com.tb.demo.mapper.TypeMapper;
import com.tb.demo.pojo.Channel;
import com.tb.demo.pojo.PropertyManage;
import com.tb.demo.service.DemoService;
import com.tb.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper mapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private FileService fileService;

    @Override
    public String insert(Map<String, String> map) {

        String title = map.get("title");
        map.remove("title");
        String type = map.get("type");
        map.remove("type");
        String tag = map.get("tag");
        map.remove("tag");
        int ratings = Integer.parseInt(map.get("ratings"));
        map.remove("ratings");

        String fileList = map.get("fileList");
        List<Map> list = JSON.parseArray(fileList, Map.class);
        map.remove("fileList");

        Channel channel = new Channel();
        channel.setTitle(title);
        channel.setType(type);
        channel.setTag(tag);
        channel.setRatings(ratings);

        int i = mapper.insertToChannel(channel);
        Integer id = channel.getId();//节目

        if (i == 0 ||id == null || id == 0) {
            return "failed";
        } else {
            map.forEach((key,value) -> {    //属性
                mapper.insertToProperty(id, key, value);
            });
            fileService.addPic(list,id);    //pic
            return "success";
        }
    }

    @Override
    public String delete(Integer id) {

        int i = mapper.deleteFromChannel(id);   //节目表
        if (i != 1) {
            return "failed";
        }
        mapper.deleteFromProperty(id);  //属性表
        return "success";
    }

    @Override
    public String getTitle(Integer id) {
        return mapper.getTitle(id);
    }

    @Override
    public String modifyAllById(Map<String, String> map) {
        Integer id = Integer.valueOf(map.get("id"));
        String title = map.get("title");
        String type = map.get("type");
        String tag = map.get("tag");
        int ratings = Integer.parseInt(map.get("ratings"));
        map.remove("id");
        map.remove("title");
        map.remove("type");
        map.remove("tag");
        map.remove("ratings");

        Channel channel = new Channel();
        channel.setTitle(title);
        channel.setId(id);
        channel.setType(type);
        channel.setTag(tag);
        channel.setRatings(ratings);

        int i = mapper.modifyChannel(channel);
        if (i != 1) {
            return "failed";
        }

        map.forEach((key,value) -> {    //属性,可能存在数据库内没有属性名的情况，要新增
            int isSuccess = mapper.modifyProperty(id, key, value);
            if (isSuccess == 0) {   //修改不成功，没有这个属性
                mapper.insertToProperty(id, key, value);    //新增
            }
        });

        return "success";
    }

    @Override
    public Map<String, Object> selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id", id.toString());

        Map<String,String> channel = mapper.selectChannelById(id);
        map.put("type", channel.get("type"));
        map.put("title", channel.get("title"));
        map.put("ratings",channel.get("ratings"));

        String tags = channel.get("tag");
        List<String> list = JSON.parseArray(tags, String.class);
        map.put("tag", list);

        List<Map<String, String>> properties = mapper.selectPropertiesByChannelId(id);
        for (Map<String, String> property : properties) {
            map.put(property.get("property_name"),property.get("property_value"));
        }
        return map;
    }

    @Override
    public int getChannelCount(Map params) {
        return mapper.getChannelCount(params);
    }

    @Override
    public List<Map> selectList(Map params) {


        List<Map> channels = mapper.selectAllFromChannel(params);

        for (Map channel : channels) {
            String tags = (String) channel.get("tag");
            List<String> list = JSON.parseArray(tags, String.class);
            channel.put("tag", list);
            int id = (int) channel.get("id");
            List<Map<String, String>> properties = mapper.selectPropertiesByChannelId(id);
            for (Map<String, String> property : properties) {
                channel.put(property.get("property_name"), property.get("property_value"));
            }
        }
        return channels;
    }

    @Override
    public List<PropertyManage> getPropertyMang() {
        return mapper.getAllPropertyMang();
    }

    @Override
    public int insertPropertyMang(PropertyManage property) {
        return mapper.insertPropertyMang(property);
    }

    @Override
    public int deletePropertyMang(Integer id) {
        return mapper.deletePropertyMang(id);
    }


}
