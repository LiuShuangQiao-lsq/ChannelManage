package com.tb.demo.mapper;

import com.tb.demo.pojo.Tag;
import com.tb.demo.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TypeMapper {

    List<String> selectTypes();
    List<Map> selectType();
    List<String> selectTagsByType(Integer type);
    String seleteTypeById(Integer id);
    List<Map> selectTagByType(Integer type);
    int addType(Type type);
    int addTags(int type,List<String> tagList);
    int addTag(Tag tag);

    int deleteType(Integer id);
    int deleteTagByType(Integer id);
    int deleteTag(Integer id);
    int modifyType(Integer id, String type);

}
