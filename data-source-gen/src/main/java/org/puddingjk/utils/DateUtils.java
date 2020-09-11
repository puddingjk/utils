package org.puddingjk.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName : DateUtils
 * @Description : 时间工具类
 * @Author : LuoHongyu
 * @Date: 2020-08-25 20:09
 */
public class DateUtils {
    public static final String YY_MM_DD = "yyyy-MM-dd";
    /***
    * @Param
    * @description  date 类型转 str
    * @author LuoHongyu
    * @date 2020/8/25 20:10
    */
    public static String dateToStr(Date date, String formatter){
        if(date==null){return "";}
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        String dateStr = dateTimeFormatter.format(localDateTime);
        return dateStr;
    }
}
