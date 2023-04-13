package com.study.party.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class StringUtil {

    /**
     * 빈객체 확인
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyObj(Object obj){
        if (obj == null){
            return true;
        }
        if (obj instanceof List){
            if (((List) obj).size() == 0){
                return true;
            }
        }
        if (obj instanceof Map){
            if (((Map) obj).isEmpty()){
                return true;
            }
        }
        return "".equals(obj.toString());
    }

    /**
     * 빈항목 치환
     * @param obj
     * @return
     */
    public static String nvl(Object obj) {
        return nvl(obj, "");
    }

    /**
     * 빈항목 치환용(디폴트값 지정)
     * @param obj
     * @param defStr
     * @return
     */
    public static String nvl(Object obj, String defStr){
        if (obj == null) {
            return defStr;
        }
        if ("".equals(obj.toString().trim())) {
            return defStr;
        }
        return obj.toString();
    }

    /**
     * 문자 반복
     * @param param : 반복 문자
     * @param cnt   : 횟수
     * @return
     */
    public static String repeatStr( String param, int cnt ) {
        return new String(new char[cnt]).replace("\0", param);
    }

    /**
     * Left Padding
     * @param str    : 원본문자
     * @param padStr : 반복 문자
     * @param totLen : 전체 문자 길이
     * @return
     */
    public static String lPadding( String str, String padStr, int totLen ) {
        int cnt = totLen - str.length(); // 반복 횟수
        if( cnt < 0 ) {
            return str;
        }
        return repeatStr( padStr, cnt ) + str;
    }

    /**
     * Right Padding
     * @param str    : 원본문자
     * @param padStr : 반복 문자
     * @param totLen : 전체 문자 길이
     * @return
     */
    public static String rPadding( String str, String padStr, int totLen ) {
        int cnt = totLen - str.length(); // 반복 횟수
        if( cnt < 0 ) {
            return str;
        }
        return str + repeatStr( padStr, cnt );
    }


}
