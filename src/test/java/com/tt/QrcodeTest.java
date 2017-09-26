package com.tt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrcodeTest {
	@Test
	public void createQrcode() throws WriterException, IOException {
		int width = 200; // 二维码图片宽度430 
        int height = 200; // 二维码图片高度430 

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
         // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数

        BitMatrix bitMatrix = new MultiFormatWriter().encode("我是中文",//要编码的内容
                BarcodeFormat.QR_CODE,
                width, //条形码的宽度
                height, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选

        //输出路径
        FileOutputStream outputStream = new FileOutputStream("/Users/Sookie/Documents/qrcode.jpg");

        // 生成二维码图片文件
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", outputStream);
	}
}
