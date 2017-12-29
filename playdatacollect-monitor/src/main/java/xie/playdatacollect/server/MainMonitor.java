package xie.playdatacollect.server;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class MainMonitor {

	@RequestMapping("/")
	public String index() {
		return "This is Server!";
	}

	public static void main(String[] args) throws Exception {
		// 完全不使用开发辅助工具热重启
		//System.setProperty("spring.devtools.restart.enabled", "false");

		SpringApplication.run(MainServer.class, args);
	}
}