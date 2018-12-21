package com.lmdsoft.opentsdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**时间工具类
 * Created by Administrator on 2018/12/5.
 */
public class DateTimeUtil {

    public static Date parse(String date,String fm){
        Date res=null;
        try {
            SimpleDateFormat sft=new SimpleDateFormat(fm);
            res=sft.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String nextSecond(){
        String res=null;
        try {
            SimpleDateFormat sft=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            res=sft.format(new Date(System.currentTimeMillis()+1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String nowDate(){
        String res=null;
        try {
            SimpleDateFormat sft=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            res=sft.format(new Date(System.currentTimeMillis()-2000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Date format(Date date){
        try {
            SimpleDateFormat sft=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return sft.parse(sft.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
