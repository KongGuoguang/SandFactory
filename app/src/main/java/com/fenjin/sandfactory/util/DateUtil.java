package com.fenjin.sandfactory.util;

import java.util.Calendar;

public class DateUtil {
    /**
     * 获取今日日期
     */
    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        String yearStr = calendar.get(Calendar.YEAR) + "";//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        String monthStr = month < 10 ? "0" + month : month + "";
        int day = calendar.get(Calendar.DATE);//获取日
        String dayStr = day < 10 ? "0" + day : day + "";
        return yearStr + "-" + monthStr + "-" + dayStr;
    }

    /**
     * 获取本月第一天日期
     */
    public static String getMonthFirstDayDate() {
        Calendar calendar = Calendar.getInstance();
        String yearStr = calendar.get(Calendar.YEAR) + "";//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        String monthStr = month < 10 ? "0" + month : month + "";
        return yearStr + "-" + monthStr + "-01";
    }

    /**
     * 获取本月最后一天日期
     */
    public static String getMonthLastDayDate() {
        Calendar calendar = Calendar.getInstance();
        String yearStr = calendar.get(Calendar.YEAR) + "";//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        String monthStr = month < 10 ? "0" + month : month + "";
        int day = calendar.getActualMaximum(Calendar.DATE);//获取当月天数
        String dayStr = day < 10 ? "0" + day : day + "";
        return yearStr + "-" + monthStr + "-" + dayStr;
    }

    /**
     * 获取本年第一天日期
     */
    public static String getYearFirstDayDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "-01-01";
    }

    /**
     * 获取本年最后一天日期
     */
    public static String getYearLastDayDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "-12-31";
    }
}
