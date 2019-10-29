package com.tb.demo.controller;

import com.alibaba.fastjson.JSON;
import com.tb.demo.pojo.PropertyManage;
import com.tb.demo.service.DemoService;
import com.tb.demo.service.FileService;
import com.tb.demo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private FileService fileService;

    //新增，必须传title，其他属性不限
    @PostMapping("insert")
    public String insert(@RequestParam Map<String,String> map) {
        String title = map.get("title");
        if(title== null || title.length()==0) {
            return "failed";
        }
        return demoService.insert(map);
    }

    //删除
    @PostMapping("delete")
    public String delete(Integer id) {
        if (id == null) {
            return "failed";
        }
        fileService.deleteVideo(id);
        fileService.deletePic(id);
        return demoService.delete(id);
    }

    //修改属性及标题
    @PostMapping("modify")
    public String modify(@RequestParam Map<String,String> map) {
        String title = map.get("title");
        Integer id = Integer.valueOf(map.get("id"));
        if(title== null || title.length()==0 || id == null) {
            return "failed";
        }
        return demoService.modifyAllById(map);
    }

    //根据节目Id获取节目title及属性
    @GetMapping("get")
    public Map<String,Object> getOneById(Integer id) {
        if (id == null) {
            return null;
        }
        Map<String, Object> channel = demoService.selectById(id);
        return channel;
    }

    @GetMapping("getTitle")
    public String getTitleById(Integer id) {
        if (id == null) {
            return "";
        }
        String title = demoService.getTitle(id);
        return title;
    }

    //获取所有节目信息
    @GetMapping("list")
    public Map getList(Integer page, Integer limit, @RequestParam(value = "params", required = false)String param) {
        Map params = JSON.parseObject(param);
        if (params.get("id") != null && ((String)params.get("id")).length() != 0) {
            params.put("id", "%"+params.get("id")+"%");
        }
        if (params.get("title") != null && ((String)params.get("title")).length() != 0) {
            params.put("title", "%" + params.get("title") + "%");
        }
        if (params.get("tag") != null && ((String)params.get("tag")).length() != 0) {
            params.put("tag", "%\"" + params.get("tag") + "\"%");
        }
        Map<String, Integer> pageInfo = PageUtil.getPageAndLimit(page, limit);
        params.put("first", pageInfo.get("first"));
        params.put("limit", pageInfo.get("limit"));
        int count = demoService.getChannelCount(params);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("count", count);
        result.put("curr", pageInfo.get("page"));
        result.put("size", pageInfo.get("limit"));
        if (count > 0) {
            result.put("data", demoService.selectList(params));
        }
        return result;
    }

    //新增节目时获取所有需填写的属性
    @GetMapping("properties")
    public List<PropertyManage> getProperties() {
        return demoService.getPropertyMang();
    }

    //属性列表
    @GetMapping("property/manage/list")
    public Map allProperties() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", demoService.getPropertyMang());
        return result;
    }

    //删除属性
    @PostMapping("property/manage/delete")
    public String deletePropertyMang(Integer id) {
        if (id == null) {
            return "failed";
        }
        int i = demoService.deletePropertyMang(id);
        if (i == 1) {
            return "success";
        }
        return "failed";
    }

    //管理通用属性 新增
    @PostMapping("property/manage/insert")
    public String insertPropertyMang(PropertyManage property) {
        String propertyName = property.getPropertyName();
        String propertyShow = property.getPropertyShow();
        if (propertyName == null || propertyName.length()==0 || propertyShow == null || propertyShow.length() == 0) {
            return "failed";
        }
        int i = demoService.insertPropertyMang(property);
        if (i == 1) {
            return "success";
        }
        return "failed";
    }

}
