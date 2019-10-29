package com.tb.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    int addPic(Integer channelId, List<Map> picList);
    List<Map<String,Object>> picList(Integer channelId);
    int deletePicByChannelId(Integer channelId);
    int deletePicById(Integer id);
    String getPicUrlById(Integer id);
    int modifyIsFrontByChannelId(Integer channelId);
    int modifyIsFrontById(Integer id, Integer isFront);
    int modifyPicInfo(Integer id, String column, String value);

    int addVideo(Integer channelId, List<Map> videos);
    List<Map<String,Object>> videoList(Integer id);
    int deleteVideoByChannelId(Integer channelId);
    int deleteVideoById(Integer id);
    String getVideoUrlById(Integer id);
    int modifyVideoInfo(Integer id, String column, String value);

    int selectMaxFreeNumber(Integer channelId);
    int selectMaxVIPNumber(Integer channelId);
    int modifyVideoToFree(Integer channelId, Integer videoNumber);
    int modifyVideoToVIP(Integer channelId, Integer videoNumber);
    int modifyVideoToPrivate(Integer channelId, Integer videoNumber);

}
