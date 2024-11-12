package view.service.face;

import java.util.List;

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




}
