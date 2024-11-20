package view.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Content {
	
	private int boardNo;
	private int memberno;
	private String title;
	private String content;
	private String isopened;
	private Date date;
	private String ad;
	private Date update;
}
