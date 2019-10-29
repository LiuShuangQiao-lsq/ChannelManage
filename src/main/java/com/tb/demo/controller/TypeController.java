package com.tb.demo.controller;

import com.tb.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("type/list")
    public List<Map> getTypesAndTags() {
        List<Map> result = typeService.getAllTypesAndTags();

        return result;
    }

    @GetMapping("type/tag")
    public List<String> getTags(Integer type) {
        return typeService.getTagsByType(type);
    }

    @GetMapping("type/manage")
    public Map getTypeByTypeId(Integer id) {
        if (id == null) {
            return null;
        }
        return typeService.getTypeAndTagsById(id);
    }



    @GetMapping("types")
    public List<Map> getTypes() {
        List<Map> result = typeService.getAllType();

        return result;
    }




    @PostMapping("type/add")
    public String addType(String type, String tags) {
        if (type != null && type.length() != 0 && tags != null && tags.length() != 0) {
            return typeService.addTypeAndTags(type,tags);
        }
        return  "failed";
    }

    @PostMapping("type/tag/add")
    public int addTag(String type, String tag) {
         return typeService.addTag(type, tag);
    }

    @GetMapping("type/delete")
    public String deleteType(String type, Integer id) {
        if (id != null) {
            int i = typeService.deleteType(id);
            if (i == 1) {
                return "success";
            }
        }
        return "failed";
    }

    @GetMapping("type/tag/delete")
    public String deleteTag(Integer id) {
        if (id != null) {
            int i = typeService.deleteTag(id);
            if (i == 1) {
                return "success";
            }
        }
        return "failed";
    }

    @PostMapping("type/modify")
    public String modifyType(String type, Integer id) {
        if (id != null && type != null && type.length() != 0) {
            int i = typeService.modifyType(id, type);
            if (i == 1) {
                return "success";
            }
        }
        return "failed";
    }


}
