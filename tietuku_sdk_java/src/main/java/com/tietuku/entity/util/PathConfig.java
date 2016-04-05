package com.tietuku.entity.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PathConfig {
	private static Properties config;

	static {
		try {
			config = PropertiesLoaderUtils.loadAllProperties("tietuku_setting.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String property) {
		return config.getProperty(property);
	}

	public static void main(String[] args) {
		System.out.println(PathConfig.getProperty("tie.tu.ku.post.api"));
	}
}
