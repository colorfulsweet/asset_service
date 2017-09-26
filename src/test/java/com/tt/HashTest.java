package com.tt;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class HashTest {
	
	@Test
	public void hash() {
		String text = DigestUtils.sha1Hex("123");
		System.out.println(text);
	}
}
