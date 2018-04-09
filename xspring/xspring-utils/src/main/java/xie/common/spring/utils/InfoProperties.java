package xie.common.spring.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Configuration
@ConfigurationProperties
public class InfoProperties {
	private String infoProjectGroupId;
	private Map<String, String> info = new HashMap<>();

	public String getInfoProjectGroupId() {
		return infoProjectGroupId;
	}

	public void setInfoProjectGroupId(String infoProjectGroupId) {
		this.infoProjectGroupId = infoProjectGroupId;
	}

	public Map<String, String> getInfo() {
		return info;
	}

	public void setInfo(Map<String, String> info) {
		this.info = info;
	}
}
