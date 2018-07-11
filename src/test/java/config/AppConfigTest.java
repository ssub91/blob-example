package config;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.ImageDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class AppConfigTest {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private ImageDao imageDao;
	
	@Test
	public void dataSourceNotBeNull() {
		assertNotNull(dataSource);
	}

	@Test
	public void imageDaoNotBeNull() {
		assertNotNull(imageDao);
	}
}