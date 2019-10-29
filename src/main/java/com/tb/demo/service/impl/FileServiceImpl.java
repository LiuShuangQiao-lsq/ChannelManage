package com.tb.demo.service.impl;

import com.tb.demo.mapper.FileMapper;
import com.tb.demo.service.FileService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public int addPic(List<Map> pics, Integer channelId) {
        if (pics == null || pics.size() == 0) {
            return 0;
        }
        return fileMapper.addPic(channelId, pics);
    }

    @Override
    public List<Map<String,Object>> picList(Integer channelId) {
        return fileMapper.picList(channelId);
    }

    @Override
    public int deletePic(Integer channelId) {
        List<Map<String,Object>> pics = picList(channelId);
        for (Map<String, Object> pic : pics) {
            String url = (String) pic.get("pic_url");
            /*
            String name = url.substring(url.lastIndexOf("/")+1);
            url = "E:/pic/" + name;
            File file = new File(url);
            file.delete();
            */
            deleteFileFromMinIO(url);
        }
        return fileMapper.deletePicByChannelId(channelId);
    }

    @Override
    public void deleteFileFromMinIO(String url) {

        String objectName = url.replaceAll("http://192.168.**.**:9000/test/", "");

        try {
            MinioClient minioClient = new MinioClient("http://192.168.**.**:9000", "*****", "******");
            minioClient.removeObject("test", objectName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int deletePicById(Integer id) {
        String url = getPicUrlById(id);
        deleteFileFromMinIO(url);
        return fileMapper.deletePicById(id);
    }

    @Override
    public int modifyIsFront(Integer channelId, Integer id, Integer isFront) {
       if(isFront == 0) {
           return fileMapper.modifyIsFrontById(id, isFront);
       } else if (isFront == 1) {
           fileMapper.modifyIsFrontByChannelId(channelId);//取消其他主海报
           return fileMapper.modifyIsFrontById(id, isFront);
       }
        return 0;
    }


    @Override
    public String getPicUrlById(Integer id) {
        return fileMapper.getPicUrlById(id);
    }

    @Override
    public int modifyPicInfo(Integer id, String column, String value) {
        return fileMapper.modifyPicInfo(id,column,value);
    }


    @Override
    public int addVideo(List<Map> videos, Integer channelId) {
        return fileMapper.addVideo(channelId, videos);
    }

    @Override
    public List<Map<String, Object>> videoList(Integer channelId) {
        return fileMapper.videoList(channelId);
    }

    @Override
    public int modifyVideoInfo(Integer id, String column, String value) {
        return fileMapper.modifyVideoInfo(id,column,value);
    }

    @Override
    public int deleteVideo(Integer channelId) {
        //批量删除
        List<Map<String,Object>> videos = videoList(channelId);
        for (Map<String, Object> video : videos) {
            String url = (String) video.get("video_url");
            deleteFileFromMinIO(url);
        }
        return fileMapper.deleteVideoByChannelId(channelId);
    }

    @Override
    public int deleteVideoById(Integer id) {
        String url = getVideoUrlById(id);
        deleteFileFromMinIO(url);
        return fileMapper.deleteVideoById(id);
    }

    @Override
    public String getVideoUrlById(Integer id) {
        return fileMapper.getVideoUrlById(id);
    }

    @Override
    public Map selectVideoNumber(Integer channelId) {
        int freeNumber = fileMapper.selectMaxFreeNumber(channelId);
        int VIPNumber = fileMapper.selectMaxVIPNumber(channelId);
        Map result = new HashMap();
        result.put("freeNumber", freeNumber);
        result.put("vipNumber", VIPNumber);
        return result;
    }

    @Override
    public int modifyVideoToFree(Integer channelId, Integer videoNumber) {
        return fileMapper.modifyVideoToFree(channelId, videoNumber);
    }

    @Override
    public int modifyVideoToVIP(Integer channelId, Integer videoNumber) {
        return fileMapper.modifyVideoToVIP(channelId, videoNumber);
    }

    @Override
    public int modifyVideoToPrivate(Integer channelId, Integer videoNumber) {
        return fileMapper.modifyVideoToPrivate(channelId, videoNumber);
    }


}
