package me.zhengjie.utils;


import me.zhengjie.utils.enums.ResponseCodeEnums;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 工具类
 * Created by  on 2021/06/23.
 */
public class MyUtil {
    public final static String STANDARD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String STANDARD_DATETIME_PATTERN_YY = "yyyy-MM-dd";
    public final static String STANDARD_DATETIME = "yyyyMMdd";

    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

     /**
     * 生成唯一主键
     *
     * @return
     */
    public static String generateUniqueKey() {
        // 格式化当前时间为yyyyMMddHHmmss
        // 雪花算法生成long型id转为String类型，再截取第11位之后的部分
        // 拼接以后的长度共20位
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(PURE_DATETIME_PATTERN))
                + String.valueOf(SnowflakeIdWorker.getInstance().nextId());
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, STANDARD_DATETIME_PATTERN);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTimeYY(Date date) {
        return formatDate(date, STANDARD_DATETIME_PATTERN_YY);
    }


    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return null;
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 获取当前时间戳
     */
    public static int getCurrentTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static Timestamp getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    /**
     * java.util.Date 转 时间戳
     */
    public static Integer convertDateToTimestamp(Date date) {
        if (date == null) {
            return 0;
        }
        long timestamp = date.getTime() / 1000;
        //时间戳超过int最大值
        if (timestamp > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) timestamp;
    }

    /**
     * 返回类型封装
     *
     * @param responseCodeEnums
     * @return
     */
    public static Map<String, Object> getResponseMessage(ResponseCodeEnums responseCodeEnums) {
        HashMap json = new HashMap();
        json.put("code", responseCodeEnums.getCode());
        json.put("msg", responseCodeEnums.getMsg());
        json.put("data", null);

        return json;
    }

    /**
     * 返回类型封装 + 动态data
     *
     * @param responseCodeEnums
     * @return
     */
    public static Map<String, Object> getResponseMessage(ResponseCodeEnums responseCodeEnums, Object data) {
        HashMap json = new HashMap();
        json.put("code", responseCodeEnums.getCode());
        json.put("msg", responseCodeEnums.getMsg());
        json.put("data", data);

        return json;
    }

    /**
     * 返回非空项填写
     *
     * @param responseCodeEnums
     * @param message
     * @return
     */
    public static Map<String, Object> getResponseMessage(ResponseCodeEnums responseCodeEnums, String message) {
        HashMap json = new HashMap();
        json.put("code", responseCodeEnums.getCode());
        json.put("msg", message == null ? responseCodeEnums.getMsg() : message);
        json.put("data", null);
        return json;
    }


    /**
     * 返回类型封装
     *
     * @param responseCodeEnums
     * @return
     */
    public static Map<String, Object> getLoginResult(ResponseCodeEnums responseCodeEnums) {
        HashMap json = new HashMap();
        json.put("code", responseCodeEnums.getCode());
        json.put("msg", responseCodeEnums.getMsg());
        json.put("data", null);

        return json;
    }


    /**
     * List集合拆分
     *
     * @param list  原始数据
     * @param count 每个list的元素数量
     * @param <T>
     * @return 拆分得到的list集合
     */
    public static <T> List<List<T>> splitListByCount(List<T> list, int count) {
        List<List<T>> listAll = new ArrayList<>();
        int size = list.size();
        if (size > count) {
            int absInt = Math.abs(size / count);
            if (size - absInt * count > 0) {
                listAll.add(list.subList(absInt * count, size));
            }
            for (int i = 1; i < absInt + 1; i++) {
                listAll.add(list.subList((i - 1) * count, i * count));
            }
        } else {
            listAll.add(list);
        }
        return listAll;
    }

    /**
     * 将dubbo转换为小数点两位
     */
    public static Double changeDubboByTwo(Double val) {
        BigDecimal b = new BigDecimal(val);
        double v = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return v;
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author jqlin
     */

    public static boolean isEffectiveDate(Date startTime, Date endTime) {
        Calendar ca = Calendar.getInstance();
        Date nowTime = ca.getTime();

        if (nowTime.getTime() == startTime.getTime()

                || nowTime.getTime() == endTime.getTime()) {

            return true;

        }

        Calendar date = Calendar.getInstance();

        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();

        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();

        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {

            return true;

        } else {

            return false;

        }

    }


    /**
     * 将dubbo转换为int  不保留小数
     */
    public static int changeDubbo(Double val) {
        BigDecimal b = new BigDecimal(val);
        double v = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        return (int) v;
    }


    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 获取指定年月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_PATTERN);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_PATTERN);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取今年年份
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 将时间时分秒的字符串去掉
     *
     * @return
     */
    public static String changeYYD(String dateStr) {
        dateStr = dateStr.replaceAll("00:00:00", "");
        dateStr = dateStr.replaceAll("23:59:59", "");
        dateStr = dateStr.replaceAll(" ", "");
        return dateStr;
    }

    /**
     * 获取当前时间
     * code 1年，2月，3星期，5日，11小时，12分钟，13秒，14毫秒
     * time 操作数值
     * <p>
     * 获取当前时间的一年前的时间：getTimeDateAdd(1,-1);
     * <p>
     * 获取当前时间的一个月前的时间：getTimeDateAdd(2,-1);
     * <p>
     * 获取当前时间的7天前的时间：getTimeDateAdd(5,-7);
     */
    public static Date getTimeDateAdd(int code, Integer time) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(code, time);
        return calendar.getTime();
    }

    /**
     * 获取当前时间
     * code 1年，2月，3星期，5日，11小时，12分钟，13秒，14毫秒
     * time 操作数值
     */
    public static Date getTimeDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayBeforeAdd(String specifiedDay) {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 时间戳转换为Date
     *
     * @return
     */
    public static Date timestampTurnDate(Integer time) {
        Date date = new Date(time);
        return date;
    }

    /**
     * 解析日期
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return"
     */
    public static Date strTurnDate(String dateStr) {
        try {
            DateFormat format = new SimpleDateFormat(STANDARD_DATETIME_PATTERN);
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析日期根据传入的格式进行转换
     *
     * @param dateStr yyyy-MM-dd
     * @return"
     */
    public static Date strTurnDateYYYYMMDD(String dateStr, String PATTERN) {
        if (StringUtils.isEmpty(PATTERN)) {
            PATTERN = STANDARD_DATETIME_PATTERN;
        }
        try {
            DateFormat format = new SimpleDateFormat(PATTERN);
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间戳转换为Date
     *
     * @return
     */
    public static String timestampTurnStr(Timestamp time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        //Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
        String string = dateFormat.format(time);

        return string;
    }

    /**
     * 时间戳转换为Date
     *
     * @return
     */
    public static Map<String, Object> lastTime() {
        Map map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_PATTERN);//格式化时间格式
        Date date = new Date(); //当前时间
        //Date date = sdf.parse("2022-01-22 08:00:22");  //测试数据
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); //设置为前一天
        String beforeOneDay = sdf.format(calendar.getTime()); //得到前一天的时间
        String startTime = beforeOneDay.substring(0, 10) + " 00:00:00";
        String endTime = beforeOneDay.substring(0, 10) + " 23:59:59";
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * 时间戳转换为Date
     *
     * @return
     */
    public static Map<String, Object> lastTimeShort() {
        Map map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME);//格式化时间格式
        Date date = new Date(); //当前时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        String nowDay = sdf.format(calendar.getTime()); //得到今天的时间

        calendar.add(Calendar.DAY_OF_MONTH, -1); //设置为前一天
        String beforeOneDay = sdf.format(calendar.getTime()); //得到前一天的时间
        map.put("startTime", beforeOneDay);

        map.put("endTime", nowDay);
        return map;
    }



}
