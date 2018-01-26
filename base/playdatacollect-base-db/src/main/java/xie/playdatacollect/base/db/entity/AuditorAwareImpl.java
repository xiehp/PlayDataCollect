package xie.playdatacollect.base.db.entity;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import xie.common.string.XStringUtils;

import java.util.Optional;

/**
 * JPA AuditorAware实现
 * 实现获取当前系统操作的用户 名称
 */
@EnableJpaAuditing
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	private Logger _log = LoggerFactory.getLogger(AuditorAwareImpl.class);

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return new AuditorAwareImpl();
//	}

	/**
	 * 返回当前登陆用户的ID
	 */
	public Optional<String> getCurrentAuditor() {
		String userId;
		try {
			if (SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipal() == null) {
//				userId = "未登录用户";
				userId = "no id";
			} else {
				//ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
				//userId = user.id;
				userId = "shiro id";
			}
		} catch (Exception e) {
			userId = null;
			_log.debug("获取当前用户id失败！");
		}

		if (XStringUtils.isBlank(userId)) {
			userId = "no user";
		}

		return Optional.ofNullable(userId);
	}
}

