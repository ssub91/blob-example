package dao;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

import dto.ImageDto;

@Repository
public class ImageDao {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public ImageDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * 
	 * @param imageDto
	 * @return
	 * 
	 * byte[]로 BLOB Insert(C) 처리
	 * 
	 */
	public Long insertImageByRawBytes(final ImageDto imageDto) {
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
			throw new RuntimeException("fails - insert BLOB data");
		}

		return key.longValue();
	}

	/**
	 * 
	 * @param imageDto
	 * @return
	 * 
	 * SqlLobValue 으로 BLOB Insert(C) 처리
	 */
	public Long insertImageBySqlLobValue(final ImageDto imageDto) {
		final String sql = "INSERT INTO IMAGE_TABLE VALUES (IMAGE_TABLE_SEQ.nextval, :data)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue( "data", new SqlLobValue(new ByteArrayInputStream(imageDto.getData()), imageDto.getData().length, new DefaultLobHandler()), Types.BLOB);		
		
		namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{"NO"});
		
		Number key = keyHolder.getKey();
		if (key == null) {
			throw new RuntimeException("fails - insert BLOB data");
		}

		return key.longValue();
	}
	
	/**
	 * 
	 * @param imageDto
	 * @return
	 * 
	 * byte[]로 BLOB Update(U) 처리
	 */
	public Boolean updateImageByRawBytes(final ImageDto imageDto) {
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

	/**
	 * 
	 * @param no
	 * @return
	 * 
	 * 
	 */
	public ImageDto fetchImage(Long no) {
		String sql = "SELECT NO, DATA FROM IMAGE_TABLE WHERE NO=?";

		ImageDto result = jdbcTemplate.queryForObject(sql, new Object[] {no}, new RowMapper<ImageDto>() {
			@Override
			public ImageDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				ImageDto imageDto = new ImageDto();

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
