package com.study.BingRPC.Util;

import org.apache.commons.lang3.StringUtils;

/**
* @ClassName StringUtil
* @Description 字符串工具类
* @author fxbing
* @date 2018年10月2日
*
*/
    
public class StringUtil {

	/**
	* @Title isEmpty
	* @Description 判断字符串是否为空
	* @param str
	* @return
	* 
	*/
	    
	public static boolean isEmpty(String str) {
		if (str != null) {
			str = str.trim();
		}
		return StringUtils.isEmpty(str);
	}
	
	/**
	* @Title isNotEmpty
	* @Description 判断字符串是否非空
	* @param str
	* @return
	* 
	*/
	    
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	* @Title split
	* @Description 分割固定格式的字符串
	* @param str
	* @param separator
	* @return
	* 
	*/
	    
	public static String[] split(String str, String separator) {
		return StringUtils.splitByWholeSeparator(str, separator);
	}
}
