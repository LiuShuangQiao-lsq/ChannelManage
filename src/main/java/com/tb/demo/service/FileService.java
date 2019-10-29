package com.tb.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FileService {

    void deleteFileFromMinIO(String url);

    int addPic(List<Map> pics, Integer channelId);
    List<Map<String,Object>> picList(Integer channelId);
    int deletePic(Integer channelId);
    int deletePicById(Integer id);
    int modifyIsFront(Integer channelId, Integer id, Integer isFront);
    String getPicUrlById(Integer id);
    int modifyPicInfo(Integer id, String column, String value);

    int addVideo(List<Map> videos, Integer channelId);
    List<Map<String, Object>> videoList(Integer channelId);
    int modifyVideoInfo(Integer id, String column, String value);
    int deleteVideo(Integer channelId);
    int deleteVideoById(Integer id);
    String getVideoUrlById(Integer id);

    Map selectVideoNumber(Integer channelId);
    int modifyVideoToFree(Integer channelId, Integer videoNumber);
    int modifyVideoToVIP(Integer channelId, Integer videoNumber);
    int modifyVideoToPrivate(Integer channelId, Integer videoNumber);
}
