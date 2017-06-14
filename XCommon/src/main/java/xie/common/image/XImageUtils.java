package xie.common.image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

public class XImageUtils {
	// public static void setJpegQuality(float quality) {
	// ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
	// ImageWriteParam param = writer.getDefaultWriteParam();
	// param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	// param.setCompressionQuality(1.0F); // Highest quality
	// // Write the JPEG to our ByteArray stream
	// ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	// ImageOutputStream imageOutputStream = ImageIO
	// .createImageOutputStream(byteArrayOutputStream);
	// writer.setOutput(imageOutputStream);
	// writer.write(null, new IIOImage(bufferedImage, null, null), param);
	//
	// ImageIO.write(im, formatName, output)
	// return byteArrayOutputStream.toByteArray();
	// }

	public static void writeWithQuality(RenderedImage im, String formatName, File output, float quality) throws IOException {
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);
		IIOMetadata iioMetadata = writer.getDefaultStreamMetadata(param);
		// Write the JPEG to our ByteArray stream
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(output);
		writer.setOutput(imageOutputStream);
		IIOImage iioImage = new IIOImage(im, null, null);
		writer.write(iioMetadata, iioImage, param);
		imageOutputStream.close();
	}

	public static void editEif(RenderedImage im, String formatName, File output, float quality) throws IOException {
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);

		IIOMetadata iioMetadata = writer.getDefaultStreamMetadata(param);
		String nametiveName = iioMetadata.getNativeMetadataFormatName();
		if (nametiveName == null) {
			nametiveName = "javax_imageio_jpeg_stream_1.0";
		}

		// Write the JPEG to our ByteArray stream
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(output);
		writer.setOutput(imageOutputStream);
		IIOImage iioImage = new IIOImage(im, null, null);
		writer.write(null, iioImage, param);
		imageOutputStream.close();
	}

	public static void main(String[] args) throws IOException {
//		BufferedImage bufferedImage = ImageIO.read(new File("D:\\work\\temp\\save\\5955207_204816166000_2.jpg"));
//		ImageIO.write(bufferedImage, "jpeg", new File("D:\\work\\temp\\save\\" + new Date().getTime() + "before.jpg"));
//		writeWithQuality(bufferedImage, "jpeg", new File("D:\\work\\temp\\save\\" + new Date().getTime() + "quality75.jpg"), 0.75f);
//		writeWithQuality(bufferedImage, "jpeg", new File("D:\\work\\temp\\save\\" + new Date().getTime() + "quality80.jpg"), 0.8f);
//		writeWithQuality(bufferedImage, "jpeg", new File("D:\\work\\temp\\save\\" + new Date().getTime() + "quality85.jpg"), 0.85f);
//		writeWithQuality(bufferedImage, "jpeg", new File("D:\\work\\temp\\save\\" + new Date().getTime() + "quality90.jpg"), 0.9f);
//		ImageIO.write(bufferedImage, "jpeg", new File("D:\\work\\temp\\save\\" + new Date().getTime() + "after.jpg"));

		BufferedImage bufferedImage2 = ImageIO.read(new File("D:\\work\\temp\\jpegtest\\aaa.jpg"));

		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa91.jpg"), 0.91f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa92.jpg"), 0.92f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa93.jpg"), 0.93f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa94.jpg"), 0.94f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa95.jpg"), 0.95f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa96.jpg"), 0.96f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa97.jpg"), 0.97f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa98.jpg"), 0.98f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa99.jpg"), 0.99f);
		writeWithQuality(bufferedImage2, "jpeg", new File("D:\\work\\temp\\jpegtest\\aaa100.jpg"), 1.00f);
		System.exit(0);
	}

}
