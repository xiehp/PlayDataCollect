package xie.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xie.common.constant.XConst;
import xie.common.string.XStringUtils;

public class XFileWriter {

	private static final Logger logger = LoggerFactory.getLogger(XFileWriter.class);

	public static void writeListWithTrim(String filePath, List<String> stringList, boolean addMode) throws IOException {
		writeList(filePath, stringList, true, addMode);
	}

	public static void writeList(String filePath, List<String> stringList, boolean trimFlg, boolean addMode) throws IOException {

		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {

			file.createNewFile();
		}

		FileOutputStream fis = null;
		BufferedWriter bw = null;
		try {
			fis = new FileOutputStream(filePath, addMode);
			bw = new BufferedWriter(new OutputStreamWriter(fis, XConst.CHARSET_UTF8));
			boolean firstLineFlg = true;
			for (String lineStr : stringList) {
				if (lineStr != null) {
					String value = lineStr;
					if (trimFlg) {
						value = lineStr.trim();
					}
					if (!firstLineFlg || addMode) {
						// 非AddMode的时候， 并且是第一行时，不要换行，
						bw.newLine();
					}
					firstLineFlg = false;
					bw.write(value);
					bw.flush();

				}
			}
		} finally {
			if (fis != null)
				fis.close();
			if (bw != null)
				bw.close();
		}
	}

	public static List<String> readList(String filePath) throws IOException {
		return readList(filePath, false);
	}

	public static List<String> readList(String filePath, boolean removeBlankLine) throws IOException {
		String encode = XFileUtils.testFileEncoding(filePath);
		logger.info("获得文件[{}]的编码：{}", filePath, encode);
		return readList(filePath, encode, removeBlankLine);
	}

	/**
	 * 读取整个文件到List
	 * 
	 * @param filePath
	 * @param charset
	 * @param removeBlankLine 是否去除空行
	 * @return
	 * @throws IOException
	 */
	public static List<String> readList(String filePath, String charset, boolean removeBlankLine) throws IOException {
		List<String> lineList = new ArrayList<String>();
		File file = new File(filePath);
		if (!file.exists()) {
			return lineList;
		}
		FileInputStream fileInputStream = null;
		BufferedReader bufferedReader = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			if (charset == null) {
				bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, charset));
			}
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println("转换前：" + line);
				// XDebugPrint.printlnNowTime("转换前：" + line);
				// line = new String(line.getBytes(), XConst.CHARSET_UTF8);
				// System.out.println("转换后：" + line);

				if (removeBlankLine && XStringUtils.isBlank(line)) {
					continue;
				}

				lineList.add(line);
			}
		} finally {
			if (fileInputStream != null)
				fileInputStream.close();
			if (bufferedReader != null)
				bufferedReader.close();
		}

		return lineList;
	}
}
