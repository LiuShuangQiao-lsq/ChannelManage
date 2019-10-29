package com.tb.demo.mapper;

import com.tb.demo.pojo.Channel;
import com.tb.demo.pojo.PropertyManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DemoMapper {
    int insertToChannel(Channel channel);

    int insertToProperty(int id, String name, String value);

    String getTitle(Integer id);

    int deleteFromChannel(Integer id);//从channel表删除

    int deleteFromProperty(Integer channnelId);//从channel_property表删除

    int modifyChannel(Channel channel);

    int modifyProperty(int id, String name, String value);

    Map<String,String> selectChannelById(Integer id);

    List<Map<String,String>> selectPropertiesByChannelId(int channelId);//根据节目id查询节目的所有属性

    int getChannelCount(Map params);

    List<Map> selectAllFromChannel(Map params);//查询所有节目信息

    List<PropertyManage> getAllPropertyMang();//获取已配置的所有属性

    int insertPropertyMang(PropertyManage property);//新增属性

    int deletePropertyMang(Integer id);
}
