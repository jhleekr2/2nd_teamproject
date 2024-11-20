package view.dto;

import java.util.Date;

import lombok.Data;

@Data
public class commentview {

	private int commentno;
	private int boardNo;
	private int Memberno;
	private String content;
	private Date date;
	private String Nick;
	private int recommendno;
}
