package view.service.face;

import java.util.List;

import view.dto.Content;
import view.dto.Fileparam;

public interface SnsService {

	/**
	 * 전체 게시물 조회
	 * @return 조회된 결과
	 */
	public List<Content> list();

	/**
	 * 게시물 등록
	 * @param content 업로드하고 싶은 게시물 내용
	 * @param contentfile 업로드하고 싶은 파일 및 파일 정보가 담긴 DTO
	 */
	
	public void addContent(Content content, Fileparam contentfile);



}
