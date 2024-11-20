package view.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {

	private int commentno;
	private int boardNo;
	private int Memberno;
	private String content;
	private Date date;
}
