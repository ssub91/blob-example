package dao;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import config.AppConfig;
import config.TestConfig;
import dto.ImageByteArrayDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, TestConfig.class})
@Transactional
public class ImageDaoTest {
	
	private static ImageByteArrayDto imageDto = new ImageByteArrayDto();
	
	@Autowired
	private ImageDao imageDao;

	@Test
	@Commit
	public void testInertImageWithImageByteArrayDto() throws Exception {
		
		File file = new File( "src/test/resources/ssub1.jpg" );
		if ( file.exists() == false ) {
			Assert.fail("Image File Not Found");
		}
		byte[] data = Files.readAllBytes(file.toPath());
		imageDto.setData(data);

		Long no = imageDao.insertImage(imageDto);

		imageDto.setNo(no);
		imageDto.setData(null);
		
		assertNotNull(no);
	}

	@Test
	public void testFetchImageWithImageByteArrayDto() {
		imageDto = imageDao.fetchImage(imageDto.getNo());
		assertNotNull(imageDto);
	}

	@Test
	@Commit
	public void testUpdateImageWithImageByteArrayDto() throws Exception {
		
		File file = new File( "src/test/resources/ssub2.jpg" );
		if ( file.exists() == false ) {
			Assert.fail("Image File Not Found");
		}
		byte[] data = Files.readAllBytes(file.toPath());
		imageDto.setData(data);

		Boolean result = imageDao.updateImage(imageDto);
		assert(result);
	}
	
}