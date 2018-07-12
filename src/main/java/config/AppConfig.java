package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import config2.thirdparty.JasyptConfig;

@Configuration
@ComponentScan(basePackages={"dao"})
@Import(value={JasyptConfig.class})
@ImportResource("classpath*:/config/app/*")
public class AppConfig {
}
