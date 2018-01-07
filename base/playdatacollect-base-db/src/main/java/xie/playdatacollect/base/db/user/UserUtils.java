package xie.playdatacollect.base.db.user;

import org.apache.shiro.SecurityUtils;

public class UserUtils extends SecurityUtils {
	public static boolean hasRole(String roleStr) {
		try {
			return getSubject().hasRole(roleStr);
		} catch (Exception e) {
			return false;
		}
	}
}
