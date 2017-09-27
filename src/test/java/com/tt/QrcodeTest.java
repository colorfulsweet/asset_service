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
		QrcodeUtils.createQrcode("{\"lzIds\":[\"40289f935ec2e71e015ec2ee632a0002\",\"40289f935ec2e71e015ec2ee641d0003\"],\"operateType\":\"1\"}", output);
	}
}
