package view.dao.face;

import java.util.List;

import view.dto.Content;

public interface SnsDao {

	/**
	 * 게시물을 조회한다
	 * @return 조회된 게시물 목록
	 */
	public List<Content> View();

}
