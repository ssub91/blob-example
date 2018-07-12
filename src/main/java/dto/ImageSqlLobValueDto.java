package dto;

import org.springframework.jdbc.core.support.SqlLobValue;

/**
 * 
 * @author sblee
 *
 * SqlLobValue 필드 타입 형태
 * 
 * 요구 조건은 DTO에 SqlLobValue 필드 타입 형태인 것 같은데...
 * DTO에 Spring JDBC 기술이 있는 것이 바람직해 보이지 않음
 * 
 */
public class ImageSqlLobValueDto {
	private Long no;
	private SqlLobValue data;
}
