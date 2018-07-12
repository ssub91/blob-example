package dao;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import config.AppConfig;
import config.TestConfig;
import dto.ImageDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, TestConfig.class})
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageDaoByRawBytesTest {
	
	private static ImageDto imageDto = new ImageDto();
	
	@Autowired
	private ImageDao imageDao;

	@Test
	@Commit
	public void test01_InertImageByRawBytes() throws Exception {
		File file = new File( "src/test/resources/ssub1.jpg" );
		if ( file.exists() == false ) {
			Assert.fail("Image File Not Found");
		}
		byte[] data = Files.readAllBytes(file.toPath());
		imageDto.setData(data);

		Long no = imageDao.insertImageByRawBytes(imageDto);

		imageDto.setNo(no);
		imageDto.setData(null);
		
		assertNotNull(no);
	}

	@Test
	public void test02_FetchImage() {
		imageDto = imageDao.fetchImage(imageDto.getNo());
		assertNotNull(imageDto);
	}

	@Test
	@Commit
	public void test03_UpdateImageByRawBytes() throws Exception {
		File file = new File( "src/test/resources/ssub2.jpg" );
		if ( file.exists() == false ) {
			Assert.fail("Image File Not Found");
		}
		byte[] data = Files.readAllBytes(file.toPath());
		imageDto.setData(data);

		Boolean result = imageDao.updateImageByRawBytes(imageDto);
		assert(result);
	}
	
}