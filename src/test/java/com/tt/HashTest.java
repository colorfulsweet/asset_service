package com.tt;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class HashTest {
	
	@Test
	public void hash() {
		String text = DigestUtils.sha1Hex("123");
		System.out.println(text);
	}
	
	@Test
	public void dateFormatTest() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM");
		System.out.println(sdf.format(now));
	}
}
