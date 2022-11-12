package me.elyor.memberservice;

import me.elyor.memberservice.global.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableConfigurationProperties(AppProperties.class)
public class MemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MemberServiceApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
