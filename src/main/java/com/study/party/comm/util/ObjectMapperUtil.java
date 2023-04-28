package com.study.party.comm.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

    public static <T1, T2> T2 castClass(T1 srcClass, T2 targetClass) {
        ObjectMapper om = new ObjectMapper();
        T2 result = (T2) om.convertValue(srcClass, targetClass.getClass());
        return result;
    }

}
