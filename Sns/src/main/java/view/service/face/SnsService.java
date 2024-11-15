package view.service.face;

import java.util.List;

import view.dto.Comment;
import view.dto.Commentlike;
import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Fileparam;
import view.dto.Recommend;

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

	/**
	 * 게시물에 해당하는 업로드된 사진자료 조회
	 * @param content 업로드된 사진자료
	 * @return 조회 결과
	 */

	public List<ContentFile> viewPhoto(Content content);

	/**
	 * 조건에 맞는 사진자료 보여주기
	 * @param fileno 보여주고자 하는 사진파일 번호
	 * @return 사진파일
	 */
	public ContentFile getimage(int fileno);

	/**
	 * 사용자의 게시글 추천여부 확인
	 * @param recommend 추천수를 조회하고자 하는 사용자와 게시글 번호
	 * @return 추천여부 (1 이상 - 이미 추천했음, 0 - 아직 추천하지 않음)
	 */
	
	public int checkRecommend(Recommend recommend);

	/**
	 * 게시글 추천수 조회
	 * @param recomend 추천수를 조회하고자 하는 게시글 번호 포함된 DTO 객체
	 * @return 게시글 추천수
	 */
	public int viewRecommend(Recommend recommend);

	/**
	 * 게시글 추천수 추가
	 * @param recommend 추천수 추가하고자 하는 게시글 번호와 회원정보
	 */
	public void addRecommend(Recommend recommend);

	/**
	 * 게시글 추천수 제거
	 * @param recommend 추천수 제거하고자 하는 게시글 번호와 회원정보
	 */
	public void delRecommend(Recommend recommend);

	/**
	 * 게시물의 댓글 조회
	 * @param boardNo 조회하고자 하는 게시물 번호
	 * @return 게시물 번호에 해당하는 댓글
	 */
	public List<Comment> viewComment(int boardNo);

	/**
	 * 댓글 작성자 조회
	 * @param c 조회하고자 하는 댓글
	 * @return 댓글 작성자
	 */
	public String checkMemberNick(Comment c);

	/**
	 * 댓글 추천수 조회
	 * @param c 조회하고자 하는 댓글 또는
	 * @param commentno 조회하고자 하는 댓글 번호
	 * @return 댓글 추천수
	 */
	public int commentRecommendNo(Comment c);
	public int commentRecommendNo(int commentno);

	/** 
	 * 댓글 추천여부 조회
	 * @param c 조회하고자 하는 댓글 번호와 로그인된 회원번호
	 * @return 댓글 추천여부
	 */
	public int iscommentRecommend(Commentlike c);

	/**
	 * 댓글 추천수 추가
	 * @param commentlike 추가하고자 하는 댓글 번호와 로그인된 회원번호
	 */
	public void addRecommendComment(Commentlike commentlike);

	/**
	 * 댓글 추천수 삭제
	 * @param commentlike 삭제하고자 하는 댓글 번호와 로그인된 회원번호
	 */
	public void delRecommendComment(Commentlike commentlike);

	/**
	 * 댓글 추가
	 * @param param 추가하고자 하는 댓글의 정보
	 */
	public void addComment(Comment param);
	
	/**
	 * 댓글 삭제
	 * @param param 삭제하고자 하는 댓글의 정보
	 */
	public void delComment(Comment param);





}
