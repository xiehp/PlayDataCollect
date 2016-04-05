package xie.tietuku.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TietukuConfig {
	@Value("#{tietuku.token}")
	private String tietukuToken;

	public String getTietukuToken() {
		return tietukuToken;
	}

	public void setTietukuToken(String tietukuToken) {
		this.tietukuToken = tietukuToken;
	}

}
