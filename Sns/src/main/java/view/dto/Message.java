package view.dto;

import lombok.Data;

@Data
public class Message {

	private String code; //상태코드
	private String sender; //보내는사람
	private String receiver; //받는사람
	private String content; //대화내용
	private String regdate; //날짜
	
}
