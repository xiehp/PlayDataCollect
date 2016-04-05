package com.tietuku.entity.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	public static String base64(byte[] target) {
		return new Base64().encodeToString(target).replace('+', '-').replace('/', '_');
	}
}
