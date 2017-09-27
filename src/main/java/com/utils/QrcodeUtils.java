package com.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码处理相关工具方法
 * @author Sookie
 *
 */
public class QrcodeUtils {
	/**
	 * 图片宽度
	 */
	private static int WIDTH = 300;
	/**
	 * 图片高度
	 */
	private static int HEIGHT = 300;
	/**
	 * 输出的图片格式
	 */
	private static String FORMAT = "jpg";
	/**
	 * 二维码中文本的编码类型
	 */
	private static String CHARSET = "utf-8";
	/**
	 * 生成二维码并输出到对应的输出流
	 * @param content 二维码中加入的内容
	 * @param output 字节输出流
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQrcode(String content, OutputStream output) throws WriterException, IOException {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
         // 纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,//要编码的内容
                BarcodeFormat.QR_CODE,
                WIDTH, //条形码的宽度
                HEIGHT, //条形码的高度
                hints);//生成条形码时的一些配置,此项可选

        // 生成二维码图片文件
        MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, output);
	}
}
