package view.dao.face;

import java.util.List;

import view.dto.Comment;
import view.dto.Commentlike;
import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Recommend;

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

	/**
	 * 조건에 맞는 업로드된 파일정보를 검색한다
	 * @param boardNo 조회하고자 하는 게시물 번호
	 * @return 업로드된 파일 정보
	 */
	public List<ContentFile> getFileBoardno(int boardNo);

	/**
	 * 조회하려는 파일 정보를 불러온다
	 * @param fileno 파일번호
	 * @return 조회되는 파일 정보
	 */
	public ContentFile getFileno(int fileno);

	/**
	 * 로그인된 사용자의 게시글 추천 여부를 확인한다
	 * @param recommend 조회하고자 하는 게시글과 사용자 정보
	 * @return 추천여부
	 */
	public int isRecommend(Recommend recommend);

	/**
	 * 게시글의 추천수를 확인한다
	 * @param recommend 조회하고자 하는 게시글과 사용자 정보
	 * @return 추천수
	 */
	public int RecommendNum(Recommend recommend);

	/**
	 * 게시글 추천수 추가
	 * @param recommend 추가하고자 하는 게시글과 사용자 정보
	 */
	public void addRecommend(Recommend recommend);

	/**
	 * 게시글 추천수 제거
	 * @param recommend 제거하고자 하는 게시글과 사용자 정보
	 */
	public void delRecommend(Recommend recommend);

	/**
	 * 게시글에 해당하는 댓글 조회
	 * @param boardNo 조회하고자 하는 게시글 번호
	 * @return 조회된 댓글
	 */
	public List<Comment> viewComment(int boardNo);

	/**
	 * 댓글 작성자 닉네임 조회
	 * @param c 조회하고자 하는 댓글
	 * @return 닉네임
	 */
	public String checkMemberNick(Comment c);

	/**
	 * 댓글 추천수 조회
	 * @param c 조회하고자 하는 댓글
	 * @return 추천수
	 */
	public int commentRecommendNo(Comment c);

	/**
	 * 댓글 추천여부 조회
	 * @param c 조회하고자 하는 댓글 번호와 로그인된 회원번호
	 * @return 추천여부
	 */
	public int iscommentRecomment(Commentlike c);

	/**
	 * 댓글 추천수 추가
	 * @param commentlike 추천수 추가하려는 댓글번호와 로그인된 회원번호
	 */
	public void addRecommendComment(Commentlike commentlike);

	/**
	 * 댓글 추천수 삭제
	 * @param commentlike 추천수 삭제하려는 댓글번호와 로그인된 회원번호
	 */
	public void delRecommendComment(Commentlike commentlike);

	/**
	 * 댓글 추가
	 * @param param 추가하고자 하는 댓글
	 */
	public void addComment(Comment param);

	/**
	 * 댓글 삭제
	 * @param param 삭제하고자 하는 댓글
	 */
	public void delComment(Comment param);

	/**
	 * 회원이 작성한 게시물 리스트 확인
	 * @param memberno 조회하고자 하는 회원번호
	 * @return 작성 게시물 리스트
	 */
	public List<Content> listmember(int memberno);
	
	/**
	 * 회원이 작성한 게시물 정보 조회
	 * @param content 조회하고자 하는 게시물 정보
	 * @return 조회된 게시물 정보
	 */
	public Content chkContentDB(Content content);

	/**
	 * 업로드된 파일을 DB에서 삭제
	 * @param fileno DB에서 지우고자 하는 파일 번호
	 */
	public void delFile(String fileno);

	/**
	 * 게시물 수정
	 * @param content 수정하고자 하는 게시물 정보
	 */
	public void UpdateContentDB(Content content);

	/**
	 * 삭제하려고 하는 게시물의 댓글 추천수 삭제
	 * @param commentno 삭제하려는 게시물에 달린 댓글 번호
	 */
	public void delRecommendCommentforRemoveContent(int commentno);

	/**
	 * 삭제하려고 하는 게시물의 댓글 삭제
	 * @param boardNo 삭제하려고 하는 게시물 번호
	 */
	public void removeComment(int boardNo);

	/**
	 * 게시물 삭제
	 * @param param 삭제하려고 하는 게시물
	 */
	public void removeContent(Content param);

}