package xie.playdatacollect.collector;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableAutoConfiguration
public class MainCollector {

    @RequestMapping("/aaaa")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        // 完全不使用开发辅助工具热重启
        //System.setProperty("spring.devtools.restart.enabled", "false");

        SpringApplication.run(MainCollector.class, args);
    }
}