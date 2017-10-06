package com.utils.converters;

import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.utils.enums.LzType;

/**
 * 自定义转化器<br>
 * 流转操作标识(1出库,2流转,3回收)转化为LzFlag枚举对象
 * @author 夏夜梦星辰
 *
 */
@Component
public class String2LzType implements Converter<String, LzType> {
	private static Logger log = Logger.getLogger(String2LzType.class);
	@Override
	public LzType convert(String source) {
		if(StringHelper.isEmpty(source)) {
			return null;
		}
		for(LzType lzFlag : LzType.values()) {
			if(lzFlag.getFlag().toString().equals(source)) {
				return lzFlag;
			}
		}
		log.warn("未知的操作类型标识 : " + source);
		return null;
	}

}
