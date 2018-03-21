package xie.common.utils.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xie.common.utils.string.XStringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;

/** */

/**
 * 根据properties中配置的路径把jar和配置文件加载到classpath中。
 * 
 * @author jnbzwm
 * 
 */
public class XExtClasspathLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** URLClassLoader的addURL方法 */
	private Method addURL = initAddMethod();

	private URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

	private String jarFileDir;

	private boolean dirNotExistThrowFlg = true;

	public void setJarFileDir(String jarFileDir) {
		this.jarFileDir = jarFileDir;
	}

	public void setDirNotExistThrowFlg(boolean dirNotExistThrowFlg) {
		this.dirNotExistThrowFlg = dirNotExistThrowFlg;
	}

	public void setClassLoader(URLClassLoader classloader) {
		this.classloader = classloader;
	}

	protected File getJarFileDir() {
		if (XStringUtils.isBlank(jarFileDir)) {
			return null;
		}

		if (jarFileDir.contains(":")) {
			String userDirStr = System.getProperty("user.dir");
			File userDir = new File(userDirStr);
			return new File(userDir, jarFileDir);
		} else {
			return new File(jarFileDir);
		}
	}

	/**
	 * 不执行下载jar文件 默认 false<br>
	 */
	protected boolean isSkipDownloadJarFile() {
		return false;
	}

	/**
	 * 初始化addUrl 方法.
	 * 
	 * @return 可访问addUrl方法的Method对象
	 */
	private Method initAddMethod() {
		try {
			Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			add.setAccessible(true);
			return add;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** */
	/**
	 * 加载jar classpath。
	 */
	public void loadClasspath() {
		try {
			List<String> files = getJarFiles();
			for (String f : files) {
				loadClasspath(f);
			}

			List<String> resFiles = getResFiles();
			for (String r : resFiles) {
				loadResourceDir(r);
			}
		} finally {
		}
	}

	private void loadClasspath(String filepath) {
		File file = new File(filepath);
		loopFiles(file);
	}

	private void loadResourceDir(String filepath) {
		File file = new File(filepath);
		loopDirs(file);
	}

	/** */
	/**
	 * 循环遍历目录，找出所有的资源路径。
	 * 
	 * @param file 当前遍历文件
	 */
	private void loopDirs(File file) {
		// 资源文件只加载路径
		if (file.isDirectory()) {
			addURL(file);
			File[] tmps = file.listFiles();
			for (File tmp : tmps) {
				loopDirs(tmp);
			}
		}
	}

	/** */
	/**
	 * 循环遍历目录(包括子目录)，找出所有的jar包。
	 * 
	 * @param file 当前遍历文件
	 */
	private void loopFiles(File file) {
		if (file.isDirectory()) {
			File[] tmps = file.listFiles();
			for (File tmp : tmps) {
				loopFiles(tmp);
			}
		} else {
			if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
				addURL(file);
			}
		}
	}

	/** */
	/**
	 * 通过filepath加载文件到classpath。
	 * 
	 * @param filePath 文件路径
	 * @return URL
	 * @throws Exception 异常
	 */
	private void addURL(File file) {
		try {
			URL url = file.toURI().toURL();
			addURL.invoke(classloader, new Object[] { url });
			logger.info("载入jar:" + url.getPath() + " " + url.getFile());
		} catch (Exception e) {
			System.out.println(e);
			logger.error("载入jar文件发生错误。", e);

			if (file.exists()) {
				file.delete();
			}
		}
	}

	/** */
	/**
	 * 从配置文件中得到配置的需要加载到classpath里的路径集合。
	 * 
	 * @return
	 */
	private List<String> getJarFiles() {
		List<String> list = new ArrayList<String>();

		list.add(getJarDir().getAbsolutePath());

		return list;
	}

	public File getJarDir() {
		File jarDir = getJarFileDir();
		if (jarDir == null || !jarDir.exists() || jarDir.isFile()) {
			FileSystemNotFoundException e = new FileSystemNotFoundException("jarDir不存在:" + jarDir == null ? "null" : jarDir.getAbsolutePath());
			if (dirNotExistThrowFlg) {
				throw e;
			} else {
				logger.warn(e.getMessage());
			}
		}

		return jarDir;
	}

	/** */
	/**
	 * 从配置文件中得到配置的需要加载classpath里的资源路径集合
	 * 
	 * @return
	 */
	private List<String> getResFiles() {
		List<String> resFiles = new ArrayList<String>();
		return resFiles;
	}
}