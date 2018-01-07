package xie.playdatacollect.base.db.entity;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/** 
 * JPA AuditorAware实现
 * 实现获取当前系统操作的用户 名称
 */
public class AuditorAwareImpl implements AuditorAware<String>{
	
	private Logger _log = LoggerFactory.getLogger(AuditorAwareImpl.class);

	/**
	 * 返回当前登陆用户的ID 
	 * @return 
	 * @see AuditorAware#getCurrentAuditor()
	 */
	public Optional<String> getCurrentAuditor() {
		String userId;
		try{
			if(SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipal() == null){
//				userId = "未登录用户";
				userId = "no id";
			}else{
				//ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
				//userId = user.id;
				userId = "shiro id";
			}
		}catch(Exception e){
			userId = null;
			_log.debug("获取当前用户id失败！");
		}
		return Optional.of(userId);
	}
}

