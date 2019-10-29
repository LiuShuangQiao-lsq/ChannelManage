package com.tb.demo.service;

import com.tb.demo.pojo.PropertyManage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DemoService {
    String insert(Map<String,String> map);
    String delete(Integer id);
    String getTitle(Integer id);
    String modifyAllById(Map<String, String> map);
    Map<String,Object> selectById(Integer id);
    int getChannelCount(Map params);
    List<Map> selectList(Map params);    //可添加条件查询

    List<PropertyManage> getPropertyMang();
    int insertPropertyMang(PropertyManage property);
    int deletePropertyMang(Integer id);
}
