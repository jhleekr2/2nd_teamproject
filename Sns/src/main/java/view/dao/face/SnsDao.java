package view.dao.face;

import java.util.List;

import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Fileparam;

public interface SnsDao {

	/**
	 * 게시물을 조회한다
	 * @return 조회된 게시물 목록
	 */
	public List<Content> View();

	/**
	 * DB에 새로운 게시물을 등록한다
	 * @param content 등록할 게시물 내용
	 * @return 등록된 게시물의 내부번호(파일 추가할때 사용예정)
	 */
	public int addContentDB(Content content);

	/**
	 * 업로드 파일 DB에 업로드된 파일정보를 등록한다
	 * @param contentFile 업로드된 파일정보
	 */
	public void uploadFile(ContentFile contentFile);


}
