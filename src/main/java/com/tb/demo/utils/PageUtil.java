package com.tb.demo.utils;

import java.util.HashMap;
import java.util.Map;

//分页工具
public class PageUtil {
    public static Map<String, Integer> getPageAndLimit(Integer page, Integer limit) {
        Map<String, Integer> map = new HashMap<>();

        if (limit == null) {
            limit = 10;
        }
        if (page == null) {
            page = 1;
        }
        Integer first = limit * (page - 1);

        map.put("first", first);
        map.put("page", page);
        map.put("limit", limit);
        return map;
    }
}
