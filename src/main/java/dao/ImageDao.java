package dao;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import dto.ImageByteArrayDto;

@Repository
public class ImageDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ImageDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Long insertImage(final ImageByteArrayDto imageDto) {
		final String sql = "INSERT INTO IMAGE_TABLE VALUES (IMAGE_TABLE_SEQ.nextval, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement pstmt = connection.prepareStatement(sql, new String[] { "NO" });
				pstmt.setBlob(1, new ByteArrayInputStream(imageDto.getData()));
				return pstmt;
			}
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key == null) {
			throw new RuntimeException("fails - insert BLOB ata");
		}

		return key.longValue();
	}
	
	public Boolean updateImage(final ImageByteArrayDto imageDto) {
		final String sql = "UPDATE IMAGE_TABLE SET DATA = ? WHERE NO = ?";

		int count = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement pstmt = connection.prepareStatement(sql);

				pstmt.setBlob(1, new ByteArrayInputStream(imageDto.getData()));
				pstmt.setLong(2, imageDto.getNo());

				return pstmt;
			}
		});
		
		return count == 1;
	}

	public ImageByteArrayDto fetchImage(Long no) {
		String sql = "SELECT NO, DATA FROM IMAGE_TABLE WHERE NO=?";

		ImageByteArrayDto result = jdbcTemplate.queryForObject(sql, new Object[] {no}, new RowMapper<ImageByteArrayDto>() {
			@Override
			public ImageByteArrayDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				ImageByteArrayDto imageDto = new ImageByteArrayDto();

				imageDto.setNo(resultSet.getLong("NO"));

				Blob blob = resultSet.getBlob("DATA");
				byte[] bytes = blob.getBytes(1, (int)blob.length());
				imageDto.setData(bytes);
				return imageDto;
			}
		});

		return result;

	}

}
