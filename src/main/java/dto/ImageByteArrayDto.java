package dto;

/**
 * 
 * @author sblee
 * 
 * byte[] 필드 타입 형태 
 *
 */
public class ImageByteArrayDto {
	private Long no;
	private byte[] data;

	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
