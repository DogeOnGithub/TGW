package cn.tgw.common.utils;

import com.github.pagehelper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateUtil {

	/**
	 * 获取当年年月日字符串
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	
	/**
	 * 把日期字符串格式化成日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format)throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	/**
	 * 把日期对象格式化成字符串
	 *
	 *  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String formatDate(Date date,String format)throws Exception{
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 获取指定范围内的日期集合
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static List<String> getRangeDates(String begin,String end)throws Exception{
		List<String> datas=new ArrayList<String>();
		Calendar cb=Calendar.getInstance();
		Calendar ce=Calendar.getInstance();
		cb.setTime(formatString(begin,"yyyy-MM-dd HH:mm:ss"));
		ce.setTime(formatString(end,"yyyy-MM-dd HH:mm:ss"));
		datas.add(begin);
		while(cb.before(ce)){
			cb.add(Calendar.DAY_OF_MONTH, 1);
			datas.add(formatDate(cb.getTime(),"yyyy-MM-dd HH:mm:ss"));
		}
		return datas;
	}
	
	/**
	 * 获取指定范围内的月份集合
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static List<String> getRangeMonths(String begin,String end)throws Exception{
		List<String> months=new ArrayList<String>();
		Calendar cb=Calendar.getInstance();
		Calendar ce=Calendar.getInstance();
		cb.setTime(formatString(begin,"yyyy-MM"));
		ce.setTime(formatString(end,"yyyy-MM"));
		months.add(begin);
		while(cb.before(ce)){
			cb.add(Calendar.MONTH, 1);
			months.add(formatDate(cb.getTime(),"yyyy-MM"));
		}
		return months;
	}

	/*
	 * @Description:用于获取当前日期指定days的天数前或者后，正数指当前日期后days天，负数指当前日期后days天，且返回的Date中，时分秒均为0，reset参数指定是否将时间重置为00：00：00
	 * @Param:[days, reset]
	 * @Return:java.util.Date
	 * @Author:TjSanshao
	 * @Date:2018-12-14
	 * @Time:10:30
	 **/
	public static Date getDateDealWithDays(int days, boolean reset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);

		if (reset) {
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		}

		return calendar.getTime();
	}
	
	public static void main(String[] args) throws Exception {
		List<String> dates=getRangeMonths("2016-10","2017-12");
		for(String date:dates){
			System.out.println(date);
		}
	}
}
