package com.tb.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TypeService {
    List<Map> getAllTypesAndTags();
    List<String> getAllTypes();
    List<Map> getAllType();
    List<String> getTagsByType(Integer type);
    Map getTypeAndTagsById(Integer id);
    String addTypeAndTags(String type, String tags);
    int addType(String type);
    int addTag(String type, String tag);
    int deleteType(Integer id);
    int modifyType(Integer id, String type);
    int deleteTag(Integer id);
}
