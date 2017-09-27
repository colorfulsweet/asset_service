package com.tt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import com.google.zxing.WriterException;
import com.utils.QrcodeUtils;

public class QrcodeTest {
	@Test
	public void createQrcode() throws WriterException, IOException {
		OutputStream output = new FileOutputStream("/Users/Sookie/Documents/qrcode.jpg");
		QrcodeUtils.createQrcode("我是二维码的内容", output);
	}
}
