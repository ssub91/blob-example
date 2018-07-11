package config;


/*
 *	from hone-web-support
 *
 */
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.jasypt.spring31.properties.EncryptablePreferencesPlaceholderConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class JasyptConfig implements ApplicationContextAware {

	private static final String ALGORITHM = "PBEWithMD5AndDES";
	private static final String PASSWORD = "APP_ENC_HMP";

	@Bean(name = "stringPBEConfig")
	public StringPBEConfig stringPBEConfig() {
		// SimpleStringPBEConfig stringPBEConfig = new SimpleStringPBEConfig();
		EnvironmentStringPBEConfig pdeConfig = new EnvironmentStringPBEConfig();
		pdeConfig.setAlgorithm(ALGORITHM);
		pdeConfig.setPassword(PASSWORD);
		return pdeConfig;
	}

	@Bean(name = "encryptor")
	public StringEncryptor encryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setConfig(stringPBEConfig());
		return encryptor;
	}

	@Bean
	public EncryptablePreferencesPlaceholderConfigurer propertyConfigurer() {
		EncryptablePreferencesPlaceholderConfigurer configurer = new EncryptablePreferencesPlaceholderConfigurer(encryptor());
		
//		configurer.setLocations(new Resource[] {//
//				applicationContext.getResource("classpath:honeconfig/properties/jdbc-properties.xml")//
//				, applicationContext.getResource("classpath:honeconfig/properties/hone-properties.xml") //
//				, applicationContext.getResource("classpath:honeconfig/properties/biz-properties.xml") //
//		});
		
		configurer.setIgnoreUnresolvablePlaceholders(true);
		return configurer;
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
} 