package view.dto;

import java.util.Date;

import lombok.Data;

//파일 부분을 떼어내고 실제 DB에 기록되는 DTO
@Data
public class ContentFile {
	private int fileno;
	private int boardNo;
	private String path;
	private String original;
	private String stored;
	private Date uploadTime;
}
