package com.company;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyuanqing on 2017/5/13.
 */
public class CronTest3 {

    private static String[] unit = {"秒","分", "点", "日", "月", "周", "年"};

    private static Map<String, String> weekDays = new HashMap<>();

    static {
        weekDays.put("1", "周日");
        weekDays.put("2", "周一");
        weekDays.put("3", "周二");
        weekDays.put("4", "周三");
        weekDays.put("5", "周四");
        weekDays.put("6", "周五");
        weekDays.put("7", "周六");
    }

    public static String getContent(String cron) {
        StringBuilder content = new StringBuilder();
        String[] cronArray = cron.split("\\s+");
        int len = cronArray.length;
        StringBuilder[] subcontent = new StringBuilder[len];
        StringBuilder suffixContent = new StringBuilder();
        for(int i = 0; i < len; i++){
            if(cronArray[i].matches("\\*")){
                if(i >= 1 && cronArray[i-1].matches("\\*|\\?"))
                    subcontent[i] = new StringBuilder("");
                else if(i == 2)
                    subcontent[i] = new StringBuilder("每小时");
                else
                    subcontent[i] = new StringBuilder("每").append(unit[i]);
            }
            else if(cronArray[i].matches("\\?")){
                if(i == 3 && cronArray[i+2].equals("*"))
                    subcontent[i] = new StringBuilder("每").append(unit[i]);
                else
                    subcontent[i] = new StringBuilder("");
            }
            else if(cronArray[i].matches("\\d+-\\d+")){
                if(i == 5){
                    String[] tmpStr = cronArray[i].split("-");
                    subcontent[i] = new StringBuilder(weekDays.get(tmpStr[0])).append("至").append(weekDays.get(tmpStr[1]));
                }else {
                    subcontent[i] = new StringBuilder(cronArray[i]).append(unit[i]);
                }
            }
            else if(cronArray[i].matches("\\d+")){
                if(i == 5){
                    subcontent[i] = new StringBuilder(weekDays.get(cronArray[i]));
                }else {
                    subcontent[i] = new StringBuilder(cronArray[i]).append(unit[i]);
                }
            }
            else if(cronArray[i].matches("(\\d+,\\d+)+")){
                if(i == 5){
                    String[] tmpStr = cronArray[i].split(",");
                    for(int j = 0; j < tmpStr.length; j++){
                        if(j == 0) {
                            subcontent[i] = new StringBuilder(weekDays.get(tmpStr[j]));
                        }
                        subcontent[i] = subcontent[i].append(",").append(weekDays.get(tmpStr[j]));
                    }
                }else {
                    subcontent[i] = new StringBuilder(cronArray[i]).append(unit[i]);
                }
            }
            else if(cronArray[i].matches("\\*/\\d+")){
                if(i == 5)
                { return cron; }
                String[] tmpStr = cronArray[i].split("/");
                subcontent[i] = new StringBuilder("从");
                if(i == 2)
                    suffixContent = new StringBuilder("开始，每隔").append(tmpStr[1]).append("小时");
                else
                    suffixContent = new StringBuilder("开始，每隔").append(tmpStr[1]).append(unit[i]);

            }
            else if(cronArray[i].matches("\\d+/\\d+")){
                if(i == 5)
                { return cron; }
                String[] tmpStr = cronArray[i].split("/");
                subcontent[i] = new StringBuilder("从").append(tmpStr[0]).append(unit[i]);
                if(i == 2)
                    suffixContent = new StringBuilder("开始，每隔").append(tmpStr[1]).append("小时");
                else
                    suffixContent = new StringBuilder("开始，每隔").append(tmpStr[1]).append(unit[i]);
            }
            else
            {
                return cron;
            }
        }

        for(int i = len-1; i >=0; i--){
            if(i == 5)
                continue;
            content = content.append(subcontent[i]);
            if(i == 4)
                content = content.append(subcontent[i+1]);
        }
        if(content.length() == 0)
            content = content.append("每秒");

        String cronContent;
        content = content.append(suffixContent).append("执行");
        if(content.indexOf("从开始，") > -1){
            int dot = content.indexOf("，");
            cronContent = content.substring(dot+1);
            return cronContent;
        }

        cronContent = content.toString();
        return cronContent;
    }
}
