package com.zuzush.zuzush.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 正则匹配
 * @author liujun
 *
 */
public class RegesUtils {
	public static boolean isPwd(String pwd) {
		String regex = "^[^ ]{6,16}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pwd);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断都是数字，并且长度是11位
	 */
	public static boolean IsPhone(String phNum) {

		// 手机号验证规则
		String regEx = "^[1][34578][0-9]{9}$";//^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(phNum);
		// 括号里的是判断是不是手机号并且长度必须是11位，如果是进入方法判断长度
		if (matcher.matches() && phNum.length() == 11) {
			return true;
		}
		return false;
	}

	/**
	 * 去除空格
	 * @param str
	 * @return
     */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 替换手机号为****
	 * @param str
	 * @return
     */
	public static String makePhoneNunToStar(String str){
		String result = "";
		if (str != null && str.length() >7) {
			result = str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}
		return  result;
	}
	/**
	 * 格式化
	 *
	 * @param jsonStr
	 * @return
	 * @author lizhgb
	 * @Date 2015-10-14 下午1:17:35
	 * @Modified 2017-04-28 下午8:55:35
	 */
	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr))
			return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		boolean isInQuotationMarks = false;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
				case '"':
					if (last != '\\'){
						isInQuotationMarks = !isInQuotationMarks;
					}
					sb.append(current);
					break;
				case '{':
				case '[':
					sb.append(current);
					if (!isInQuotationMarks) {
						sb.append('\n');
						indent++;
						addIndentBlank(sb, indent);
					}
					break;
				case '}':
				case ']':
					if (!isInQuotationMarks) {
						sb.append('\n');
						indent--;
						addIndentBlank(sb, indent);
					}
					sb.append(current);
					break;
				case ',':
					sb.append(current);
					if (last != '\\' && !isInQuotationMarks) {
						sb.append('\n');
						addIndentBlank(sb, indent);
					}
					break;
				default:
					sb.append(current);
			}
		}

		return sb.toString();
	}

	/**
	 * 添加space
	 *
	 * @param sb
	 * @param indent
	 * @author lizhgb
	 * @Date 2015-10-14 上午10:38:04
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}
}
