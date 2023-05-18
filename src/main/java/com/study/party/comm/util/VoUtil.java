package com.study.party.comm.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class VoUtil {

    /**
     * Map -> Vo로 변경
     * @param prmMap
     * @param voPrmClass
     * @return
     * @param <T>
     */
    public static <T> T getVo(Map<String,Object> prmMap, Class<T> voPrmClass){
        ObjectMapper om = new ObjectMapper();
        return om.convertValue(om, voPrmClass);
    }
}
