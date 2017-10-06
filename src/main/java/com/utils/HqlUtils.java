package com.utils;

import javax.persistence.Query;
/**
 * HQL语句处理工具方法
 * @author 夏夜梦星辰
 *
 */
public class HqlUtils {
	/**
	 * 创建占位符列表(常用于in子句)
	 * @param num 占位符数量
	 * @return
	 */
	public static String createPlaceholder(int num) {
		StringBuilder builder = new StringBuilder();
		for(int i=0 ; i<num ; i++) {
			builder.append("?,");
		}
		builder.setLength(builder.length()-1);
		return builder.toString();
	}
	/**
	 * 设置查询参数列表(常用于in子句)<br>
	 * 占位符索引从1开始
	 * @param query 查询对象
	 * @param args 参数列表
	 * @param start 起始索引
	 */
	public static void setParamList(Query query, String[] args, int start) {
		for(int i=start ; i<=args.length+start-1 ; i++) {
			query.setParameter(i, args[i-start]);
		}
	}
}
