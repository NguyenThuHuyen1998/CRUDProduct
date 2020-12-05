package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/*
    created by HuyenNgTn on 29/11/2020
*/
public class ObjectImpl {
    public static final Logger logger = LoggerFactory.getLogger(ObjectImpl.class);

    public JSONObject getPagingJSONObject(int totalCount, int limit, int totalPage, int page){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put(InputParam.TOTAL_COUNT, totalCount);
        jsonObject.put(InputParam.RECORD_IN_PAGE, limit);
        jsonObject.put(InputParam.TOTAL_PAGE, totalPage);
        jsonObject.put(InputParam.CURRENT_PAGE, page);
        return jsonObject;
    }
}
