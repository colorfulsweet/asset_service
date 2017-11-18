package com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

public class ReflectUtils {
	private static Logger log = Logger.getLogger(ReflectUtils.class);
	
	private static final int serialVersionCode = Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL;
	/**
	 * 将某个对象当中不为空的属性拷贝覆盖到另一个对象当中<br>
	 * (两个对象的类型必须相同)
	 * @param source 原对象(从该对象拷贝属性)
	 * @param target 目标对象(将属性值拷贝到该对象)
	 */
	public static void reflectCopyField(Object source, Object target) {
		if(source == null || target == null) {
			log.warn("原对象或目标对象为null -- source:" + source + ",target:"+target);
			return;
		}
		Class<? extends Object> sourceClass = source.getClass();
		Class<? extends Object> targetClass = target.getClass();
		//目标对象可能是hibernate产生的代理对象
		if(!targetClass.getName().startsWith(sourceClass.getName())) {
			log.warn("原对象与目标对象类型不同 -- source:" + sourceClass.getName() 
			+ ",target:"+targetClass.getName());
			return;
		}
		Field[] fields = sourceClass.getDeclaredFields();
		for(Field field : fields) {
			if(field.getModifiers() == serialVersionCode) {
				continue;
			}
			try {
				Method getterMethod = sourceClass.getMethod("get"+firstCharToUpper(field.getName()));
				Object value = getterMethod.invoke(source);
				if(value != null) {
					Method setterMethod = targetClass.getMethod("set"+firstCharToUpper(field.getName()), value.getClass());
					setterMethod.invoke(target, value);
				}
			} catch (NoSuchMethodException | SecurityException | 
					IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException e) {
				log.error("反射执行出错!", e);
			} 
		}
	}
	/**
	 * 将属性名称的首字母大写以便于获取到对应的get或set方法名称
	 * @param fieldName 属性名称
	 * @return
	 */
	private static String firstCharToUpper(String fieldName) {
		StringBuilder builder = new StringBuilder(fieldName);
		builder.setCharAt(0, Character.toUpperCase(fieldName.charAt(0)));
		return builder.toString();
	}
}
