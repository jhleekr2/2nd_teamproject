package view.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// 파일 정보와 파일까지 묶은 DTO
@Data
public class Fileparam {
	private int fileno;
	private int boardNo;
	private String path;
	private String original;
	private String stored;
	private Date uploadTime;
	private MultipartFile file;
}
