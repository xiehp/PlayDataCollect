package xie.tietuku.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TietukuConfig {
	@Value("#{tietuku.token}")
	private String tietukuToken;

	/** 存放gif的token */
	@Value("#{tietuku.tokengif}")
	private String tietukuTokenGif;

	public String getTietukuToken() {
		return tietukuToken;
	}

	public void setTietukuToken(String tietukuToken) {
		this.tietukuToken = tietukuToken;
	}

	public String getTietukuTokenGif() {
		return tietukuTokenGif;
	}

	public void setTietukuTokenGif(String tietukuTokenGif) {
		this.tietukuTokenGif = tietukuTokenGif;
	}

}
