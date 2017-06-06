/**
 * @since 2014-11-29 下午10:50:57
 */
package xie.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import xie.common.stage.XNumberListener;
import xie.common.string.XStringUtils;

/**
 * @since 2014-11-29 下午10:50:57
 */
public class XFileUtils {

	public static boolean copyDirectory(File srcDirectory, File desDirectory) {
		if (srcDirectory == null || !srcDirectory.exists()) {
			return false;
		}

		if (!desDirectory.exists()) {
			if (!desDirectory.mkdirs()) {
				System.err.println("文件夹[" + desDirectory.getAbsolutePath() + "]创建失败。");
				return false;
			} else {
				System.err.println("文件夹[" + desDirectory.getAbsolutePath() + "]创建成功。");
			}
		}

		for (File file : srcDirectory.listFiles()) {
			if (file.isDirectory()) {
				File newDir = new File(desDirectory, file.getName());
				if (!newDir.mkdirs()) {
					System.err.println("文件夹[" + newDir.getAbsolutePath() + "]创建失败。");
					return false;
				} else {
					System.err.println("文件夹[" + desDirectory.getAbsolutePath() + "]创建成功。");
				}

				if (!copyDirectory(file, newDir)) {
					return false;
				}
			} else {
				File newCopyFile = new File(desDirectory, file.getName());
				try {
					if (!copyFile(file, newCopyFile)) {
						System.err.println("拷贝文件[" + file.getAbsolutePath() + "]到[" + newCopyFile.getAbsolutePath() + "]失败。");
					} else {
						System.err.println("拷贝文件[" + file.getAbsolutePath() + "]到[" + newCopyFile.getAbsolutePath() + "]成功。");
					}
				} catch (Exception e) {
					System.err.println("拷贝文件[" + file.getAbsolutePath() + "]到[" + newCopyFile.getAbsolutePath() + "]失败。");
				}
			}
		}

		return true;
	}

	public static boolean copyFile(InputStream in, OutputStream out) {
		return false;
	}

	public static boolean copyFile(File fileIn, File fileOut) throws IOException {
		if (fileIn == null || !fileIn.exists()) {
			return false;
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileIn);
			copyFile(inputStream, fileOut);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public static boolean copyFile(InputStream in, File fileOut) throws IOException {
		return copyFile(in, fileOut, null);
	}

	public static boolean copyFile(InputStream in, File fileOut, XNumberListener processListener) throws IOException {

		if (!fileOut.getAbsoluteFile().getParentFile().exists()) {
			fileOut.getAbsoluteFile().getParentFile().mkdirs();
		}
		RandomAccessFile randomFile = new RandomAccessFile(fileOut.getAbsoluteFile(), "rw");
		try {
			int hasReadLen = 0;
			byte[] buffer = new byte[500 * 1024];
			int beginIndex = 0;
			int endIndex = buffer.length;
			while ((hasReadLen = in.read(buffer, beginIndex, endIndex - beginIndex)) != -1) {
				beginIndex += hasReadLen;
				if (processListener != null) {
					processListener.addNumber(hasReadLen);
				}
				if (beginIndex < endIndex) {
					continue;
				} else {
					randomFile.write(buffer, 0, beginIndex);
					beginIndex = 0;
				}
			}
			// 最后未处理完的部分
			if (beginIndex > 0) {
				randomFile.write(buffer, 0, beginIndex);
			}
		} finally {
			randomFile.close();
		}
		return true;
	}

	public static boolean deleteFile(File file) {
		boolean result = file.delete();
		return result;
	}

	public static boolean deleteDirectory(File directory) {
		if (directory == null) {
			return true;
		}

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				if (!deleteDirectory(file)) {
					return false;
				}
			} else {
				if (!file.delete()) {
					System.err.println("文件[" + file.getAbsolutePath() + "]删除失败。");
					return false;
				} else {
					System.err.println("文件[" + file.getAbsolutePath() + "]删除成功。");
				}
			}
		}

		if (!directory.delete()) {
			System.err.println("文件[" + directory.getAbsolutePath() + "]删除失败。");
			return false;
		} else {
			System.err.println("文件[" + directory.getAbsolutePath() + "]删除成功。");
			return true;
		}
	}

	/**
	 * 获取某文件的编码
	 */
	public static String testFileEncoding(File file) {
		return testFileEncoding(file.getAbsolutePath());
	}

	/**
	 * 获取某文件的编码
	 */
	public static String testFileEncoding(String filePath) {
		FileCharsetDetector fileCharsetDetector = new FileCharsetDetector();
		try {
			return fileCharsetDetector.guestFileEncoding(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取出扩展名后的文件名
	 */
	public static String getNameRemoveExt(String fileName) {
		if (XStringUtils.isBlank(fileName)) {
			return fileName;
		}

		String nameNoExt = fileName.substring(0, fileName.lastIndexOf("."));
		return nameNoExt;
	}

	public static void main(String[] args) throws IOException {
		// copyFile(new File("E:\\\\ggg.txt"), new File("E:\\\\ggg2.txt"));
		// File file = new File("E:\\\\ggg");
		//
		// copyDirectory(file, new File("E:\\\\fff1"));
		// copyDirectory(new File("E:\\\\fff1"), new File("E:\\\\fff2"));
		// deleteDirectory(new File("E:\\\\fff1"));

		System.out.println(testFileEncoding("E:\\AnimeShotSIte\\anime\\M\\秒速五厘米\\字幕\\[诸神字幕组][秒速5厘米][5_Centimeters_per_Second][1280x720][5DualAudio(jp-it-ger-ru-en)][x264_aac][8Sub(gb-BIG5-jp-en-it-ger-ru-uk)][BDrip]_jp.ass"));
		System.out.println(testFileEncoding("E:\\AnimeShotSIte\\anime\\M\\秒速五厘米\\字幕\\unicode.txt"));
		System.out.println(testFileEncoding("E:\\AnimeShotSIte\\anime\\M\\秒速五厘米\\字幕\\unicode1.txt"));
		System.out.println(testFileEncoding("E:\\AnimeShotSIte\\anime\\M\\秒速五厘米\\字幕\\gb.txt"));
		System.out.println(testFileEncoding("E:\\AnimeShotSIte\\anime\\C\\超时空要塞\\Δ\\字幕\\[dmhy][Macross_Delta][18][x264_aac][GB_BIG5][1080P_mkv]_track3_und.ass"));
	}
}
