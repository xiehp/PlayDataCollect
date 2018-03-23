package xie.common.spring.printstartupinfo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("xie.common.listener")
public class PrintInfoOnStartProperties {
	private boolean noPrintInfoBeforeAppStart = false;
	private boolean noPrintInfoAfterAppStart = false;

	public boolean isNoPrintInfoBeforeAppStart() {
		return noPrintInfoBeforeAppStart;
	}

	public void setNoPrintInfoBeforeAppStart(boolean noPrintInfoBeforeAppStart) {
		this.noPrintInfoBeforeAppStart = noPrintInfoBeforeAppStart;
	}

	public boolean isNoPrintInfoAfterAppStart() {
		return noPrintInfoAfterAppStart;
	}

	public void setNoPrintInfoAfterAppStart(boolean noPrintInfoAfterAppStart) {
		this.noPrintInfoAfterAppStart = noPrintInfoAfterAppStart;
	}
}
