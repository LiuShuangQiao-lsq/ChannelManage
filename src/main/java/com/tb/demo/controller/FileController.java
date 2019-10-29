package com.tb.demo.controller;

import com.tb.demo.service.FileService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("file/upload")
    public String MinIOUpload(MultipartFile file, String type) {

        if (file.isEmpty() || file.getSize() == 0) {
            return "文件为空";
        }

        try {
            MinioClient minioClient = new MinioClient("http://192.168.**.**:9000", "*****", "******");

            if (!minioClient.bucketExists("test")) {
                minioClient.makeBucket("test");
            }

            String fileName = file.getOriginalFilename();
            String newName =type + "/"+UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();
            minioClient.putObject("test", newName, inputStream, "application/octet-stream");
            inputStream.close();
            String url = minioClient.getObjectUrl("test", newName);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /*
    @PostMapping("pic/upload")
    public String picUpload(MultipartFile file) {
        try {
            String fileUrl = "E:/pic/";
            String fileName = file.getOriginalFilename();
            String newName = UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));
            File dest = new File(fileUrl + newName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest); // 保存文件
            return "/pic/view/" + newName;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
    */

    @PostMapping("pic/upload")
    public String picUpload(MultipartFile file) {
        return MinIOUpload(file, "pic");
    }


    @PostMapping("pic/uploadAndAdd")
    public List<Map<String,Object>> uploadAndAdd(Integer channelId, MultipartFile file) {

        String picUrl = MinIOUpload(file, "pic");
        String picName = file.getOriginalFilename();

        List<Map> picList = new LinkedList<>();
        Map pic = new HashMap<>();
        pic.put("newName", picUrl);
        pic.put("fileName", picName);
        picList.add(pic);
        fileService.addPic(picList, channelId);

        return fileService.picList(channelId);
    }

    @GetMapping("pic/list")
    public List<Map<String,Object>> getPics(Integer channelId) {
        if (channelId != null) {
            List<Map<String,Object>> result = fileService.picList(channelId);
            return result;
        }
        return null;
    }

    @GetMapping("pic/delete")
    public String fileDelete(Integer channelId, Integer id, String url) {
        if (channelId != null && id == null) {
            fileService.deletePic(channelId);
            return "success";
        } else if (id != null && channelId == null) {
            fileService.deletePicById(id);
            return "success";
        } else if (url != null && url.length() != 0) {
            fileService.deleteFileFromMinIO(url);
            return "";
        }
        return "failed";
    }

    @PostMapping("pic/front")
    public List<Map<String,Object>> setFront(Integer channelId, Integer id, Integer isFront) {

        if (channelId != null && id != null && isFront != null)  {
            int i = fileService.modifyIsFront(channelId,id,isFront);
            if (i==1) {
                return getPics(channelId);
            }
        }
        return null;
    }

    @PostMapping("pic/modify")
    public String modifyPicInfo(Integer id, String column, String value) {

        if (id != null && column.length() > 0 && column != null) {
            if (fileService.modifyPicInfo(id,column,value) == 1) {
                return "success";
            }
        }
        return "failed";
    }




    @PostMapping("video/upload")
    public List<Map<String,Object>> videoUpload(MultipartFile file, Integer channelId) {

        String newName = MinIOUpload(file, "video");

        String oldName = file.getOriginalFilename();

        List<Map> videoList = new LinkedList<>();
        Map video = new HashMap<>();
        video.put("newName", newName);
        video.put("fileName", oldName);
        videoList.add(video);
        fileService.addVideo(videoList,channelId);

        return fileService.videoList(channelId);
    }

    @GetMapping("video/list")
    public List<Map<String,Object>> getVideos(Integer channelId) {
        if (channelId != null) {
            List<Map<String,Object>> result = fileService.videoList(channelId);
            return result;
        }
        return null;
    }

    @PostMapping("video/modify")
    public String modifyVideoInfo(Integer id, String column, String value) {

        if (id != null && column.length() > 0 && column != null) {
            if (fileService.modifyVideoInfo(id,column,value) == 1) {
                return "success";
            }
        }
        return "failed";
    }


    @GetMapping("video/delete")
    public String videoDelete(Integer channelId, Integer id) {
        if (channelId != null && id == null) {
            fileService.deleteVideo(channelId);
            return "success";
        } else if (id != null && channelId == null) {
            fileService.deleteVideoById(id);
            return "success";
        }
        return "failed";
    }

    @GetMapping("video/number")
    public Map getVideoNumber(Integer channelId) {
        if (channelId == null) {
            return null;
        }
        return fileService.selectVideoNumber(channelId);
    }

    @GetMapping("video/number/free")
    public int setFreeVideoNumber(Integer channelId, Integer videoNumber) {
        if (channelId == null || videoNumber == null) {
            return 0;
        }
        return fileService.modifyVideoToFree(channelId, videoNumber);
    }

    @GetMapping("video/number/vip")
    public int setVIPVideoNumber(Integer channelId, Integer videoNumber) {
        if (channelId == null || videoNumber == null) {
            return 0;
        }
        return fileService.modifyVideoToVIP(channelId, videoNumber);
    }

    @GetMapping("video/number/private")
    public int setPrivateVideoNumber(Integer channelId, Integer videoNumber) {
        if (channelId == null || videoNumber == null) {
            return 0;
        }
        return fileService.modifyVideoToPrivate(channelId, videoNumber);
    }

}
