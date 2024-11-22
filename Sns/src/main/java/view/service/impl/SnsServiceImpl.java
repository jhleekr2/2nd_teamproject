package view.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import view.dao.face.SnsDao;
import view.dto.Comment;
import view.dto.Commentlike;
import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Fileparam;
import view.dto.Paging;
import view.dto.Pagingcomm;
import view.dto.Recommend;
import view.service.face.SnsService;

@Service
@Slf4j
public class SnsServiceImpl implements SnsService {

	// 서비스 객체 연결
	@Autowired
	private SnsDao snsDao;
	
	@Autowired
	private SnsService snsService;
	
	// 컨텍스트 서비스 객체 연결
	@Autowired
	private ServletContext context;

	@Override
	public List<Content> list() {

		log.info("SnsService.list() 호출");
		return snsDao.View();
	}

	@Override
	public void addContent(Content content, Fileparam fileparam) {
		log.info("SnsService.addContent() 호출");

		log.info("content : {}", content);
		log.info("fileparam : {}", fileparam);

		// 게시물 등록 및 파일 처리

		// 게시물 등록

		// 이때 마이바티스에서 글번호를 반환해야 한다
		// (파일 처리에 필요)

		// 마이바티스에서 useGeneratedKeys="true" keyProperty="boardNo" 옵션 지정하면
		// 알아서 Content타입 변수 content에 boardNo를 대입해 준다는 것을 확인했다.
		// -> 따라서, content.setBoardNo(snsDao.addConTentDB( content )); 따위는 필요없다
		snsDao.addContentDB(content);
		log.info("boardNo: " + content.getBoardNo());
		// 파일 처리 파라미터에 마이바티스에서 반환된 글번호 대입
		fileparam.setBoardNo(content.getBoardNo());

		// 파일 처리
		log.info("content : {}", content);
		log.info("fileparam : {}", fileparam);

		// 파일 업로드 절차 시작
		MultipartFile file = fileparam.getFile();

		// ------------------------------------------------------
		if (file.isEmpty() || file.getSize() <= 0) {
			log.info("파일 업로드 잘못됨, 처리 중단!");

			// 파일 처리 메소드 filesave() 중단
			return;
		}

		// ------------------------------------------------------

		// 파일의 저장 경로 - Real Path
		String storedPath = context.getRealPath("upload");
		log.info("storedPath : {}", storedPath);

		// upload 폴더가 없으면 생성하기
		File storedFolder = new File(storedPath);
		storedFolder.mkdir();

		// 업로드된 파일이 저장되는 이름
		String storedName = null;

		// 파일을 저장시킬 객체
		File dest = null;

		// 매우 낮은 확률로 이전 파일을 덮어씌울 수 있기 때문에 안전장치로 넣어둔 코드
		// 저장될 파일명이 중복되지 않도록 반복한다
//		do {
//			storedName = file.getOriginalFilename(); //원본 파일명
//			storedName += UUID.randomUUID().toString().split("-")[4]; //UUID 추가
//			//랜덤 파일명 만드는 법 중 UUID법을 사용했고, 중복이름 검사는 반드시 해야한다
//			dest = new File( storedFolder, storedName );
//		} while ( dest.exists() );

		// 실제 SNS코드 분석 후 저장 파일명을 변경해야 할것 같다
		do {
			storedName = UUID.randomUUID().toString().split("-")[4];
			storedName += ".";
			storedName += StringUtils.getFilenameExtension(file.getOriginalFilename());
			// SNS코드 분석 결과에 따라 저장 파일명 규칙을 변경했다.
			// 새로운 저장 파일명 규칙 = UUID + . + 파일의 원래 확장자(나중에 이미지 보여줄때 확장자를 이용)
			dest = new File(storedFolder, storedName);
		} while (dest.exists());

		// ------------------------------------------------------

		try {
			// 업로드된 임시 파일을 upload 폴더로 옮겨 실제 파일을 생성한다
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ------------------------------------------------------

		// DB에 기록하기
		// 기록하기 위해 파일 부분을 떼어낸 새로운 DTO를 new해서 들여온다
		ContentFile contentFile = new ContentFile();

		// 파일 번호 - DB에서 자동으로 순차적으로 부여

		// 게시글 글번호 등록
		contentFile.setBoardNo(fileparam.getBoardNo());

		// 저장경로 등록
		contentFile.setPath(storedPath);

		// 원래 파일이름 등록
		contentFile.setOriginal(file.getOriginalFilename());

		// 저장된 파일이름 등록
		contentFile.setStored(storedName);

		// 저장날짜 시간 - DB에서 자동으로 현재 시각으로 기록

		// DB에 기록
		snsDao.uploadFile(contentFile);

	}

	@Override
	public List<ContentFile> viewPhoto(Content content) {
		// 업로드된 파일 중 게시글의 번호와 일치한 사진자료 조회

		// 업로드된 게시글에 해당하는 파일 리스트 조회하여 파일 정보를 DB에서 호출
		List<ContentFile> list = snsDao.getFileBoardno(content.getBoardNo());
		log.info("Filelist : {}", list);
		return list;
	}

	@Override
	public ContentFile getimage(int fileno) {

		// 파일 번호에 맞는 파일 정보 호출
		ContentFile fileInfo = snsDao.getFileno(fileno);
		return fileInfo;
	}

	@Override
	public int checkRecommend(Recommend recommend) {
		// 게시글 추천여부 확인
		int isRecommend = snsDao.isRecommend(recommend);

		// 여기서 추천여부가 0이면 아직 사용자가 해당 게시물에 추천을 하지 않았고
		// 추천여부가 1이상이면 사용자가 해당 게시물에 추천을 한 것으로 간주한다
		// 추천여부 반환
		return isRecommend;
	}

	@Override
	public int viewRecommend(Recommend recommend) {
		// 게시글 추천수 확인
		int RecommendNum = snsDao.RecommendNum(recommend);

		// 추천수 반환
		return RecommendNum;
	}

	@Override
	public void addRecommend(Recommend recommend) {
		// 추천수 추가
		snsDao.addRecommend(recommend);

	}

	@Override
	public void delRecommend(Recommend recommend) {
		// 추천수 제거
		snsDao.delRecommend(recommend);
	}

	@Override
	public List<Comment> viewComment(int boardNo) {
		// 게시글에 해당하는 댓글 조회
		List<Comment> result = snsDao.viewComment(boardNo);
		return result;
	}

	@Override
	public List<Comment> viewComment(Pagingcomm paging) {
		// 게시글에 해당하는 댓글을 페이징을 반영하여 조회
		List<Comment> result = snsDao.viewCommentwithPaging( paging );
		return result;
	}
	
	@Override
	public String checkMemberNick(Comment c) {
		// 댓글 작성자 닉네임 조회
		String result = snsDao.checkMemberNick(c);
		return result;
	}

	@Override
	public int commentRecommendNo(Comment c) {
		// 댓글 추천수 조회
		int recommend = snsDao.commentRecommendNo(c);
		return recommend;
	}

	@Override
	public int commentRecommendNo(int commentno) {
		// 댓글 추천수 조회
		// 통일성 위해 Comment 데이터타입 DTO변수 잠깐 정의
		Comment c = new Comment();
		c.setCommentno(commentno);
		int recommend = snsDao.commentRecommendNo(c);
		return recommend;
	}

	@Override
	public int iscommentRecommend(Commentlike c) {
		// 댓글 추천여부 조회(추천되어 있으면 1, 아니면 0)
		int isRecommend = snsDao.iscommentRecomment(c);
		return isRecommend;
	}

	@Override
	public void addRecommendComment(Commentlike commentlike) {
		// 댓글 추천수 추가
		snsDao.addRecommendComment(commentlike);
	}

	@Override
	public void delRecommendComment(Commentlike commentlike) {
		// 댓글 추천수 삭제
		snsDao.delRecommendComment(commentlike);
	}

	@Override
	public void addComment(Comment param) {
		// 댓글 추가
		snsDao.addComment(param);
	}

	@Override
	public void delComment(Comment param) {
		//삭제하고자 하는 댓글 추천수 삭제
		int bn = param.getCommentno();
		snsDao.delRecommendCommentforRemoveContent(bn);
		// 댓글 삭제
		snsDao.delComment(param);
	}

	@Override
	public List<Content> listmember(int memberno) {
		// 회원이 작성한 게시물 리스트 확인
		return snsDao.listmember(memberno);
	}

	@Override
	public List<Content> listmember(Paging paging) {
		// 회원이 작성한 게시물 리스트를 페이지네이션을 적용하여 확인
		return snsDao.listmemberwithPaging(paging);
	}
	@Override
	public Content chkContentDB(Content param) {
		// 회원이 작성한 게시물 정보 조회
		return snsDao.chkContentDB(param);
	}

	@Override
	public void delfiles(List<String> delFiles) {
		// 선택된 파일 삭제
		// file관련 루틴
		if (delFiles != null) {
			for (String fileno : delFiles) {
				log.info("Deleting file with fileno: {}", fileno);
				int filenoparam = Integer.parseInt(fileno); // fileno를 정수로 변환
				
				//삭제하고자 하는 파일정보 조회
				ContentFile uploadfile = snsService.getimage(filenoparam);
				log.info("delete file : {}", uploadfile);
				//업로드된 실제파일 삭제
				//이때 주의할점은 파일을 업로드할때 "upload"로 올렸기 때문에 여기서도 "upload"로 삭제해야 한다
				File file = new File(context.getRealPath("upload"), uploadfile.getStored());
				log.info(context.getRealPath("upload"));
				
				log.info(uploadfile.getStored());
				file.delete();

				//업로드된 DB정보 삭제
				snsDao.delFile( fileno );
			}
		}

	}

	@Override
	public void updateContent(Content content, Fileparam fileparam) {
		// 게시물 및 파일 업데이트(사실상 앞의 파일 업로드 부분을 상당부분 재활용할듯)
		log.info("updateContent() 호출");
		log.info("Content : {}", content);
		log.info("fileparam : {}", fileparam);
		
		// 게시물 업데이트
		snsDao.UpdateContentDB( content );

		// 새로운 파일 업로드(앞의 파일 업로드 코드 재활용)
		// 파일 처리

		// 파일 업로드 절차 시작
		MultipartFile file = fileparam.getFile();

		// ------------------------------------------------------
		if (file.isEmpty() || file.getSize() <= 0) {
			log.info("파일 업로드 잘못됨, 처리 중단!");

			// 파일 처리 메소드 filesave() 중단
			return;
		}

		// ------------------------------------------------------

		// 파일의 저장 경로 - Real Path
		String storedPath = context.getRealPath("upload");
		log.info("storedPath : {}", storedPath);

		// upload 폴더가 없으면 생성하기
		File storedFolder = new File(storedPath);
		storedFolder.mkdir();

		// 업로드된 파일이 저장되는 이름
		String storedName = null;

		// 파일을 저장시킬 객체
		File dest = null;

		// 매우 낮은 확률로 이전 파일을 덮어씌울 수 있기 때문에 안전장치로 넣어둔 코드
		// 저장될 파일명이 중복되지 않도록 반복한다
//		do {
//			storedName = file.getOriginalFilename(); //원본 파일명
//			storedName += UUID.randomUUID().toString().split("-")[4]; //UUID 추가
//			//랜덤 파일명 만드는 법 중 UUID법을 사용했고, 중복이름 검사는 반드시 해야한다
//			dest = new File( storedFolder, storedName );
//		} while ( dest.exists() );

		// 실제 SNS코드 분석 후 저장 파일명을 변경해야 할것 같다
		do {
			storedName = UUID.randomUUID().toString().split("-")[4];
			storedName += ".";
			storedName += StringUtils.getFilenameExtension(file.getOriginalFilename());
			// SNS코드 분석 결과에 따라 저장 파일명 규칙을 변경했다.
			// 새로운 저장 파일명 규칙 = UUID + . + 파일의 원래 확장자(나중에 이미지 보여줄때 확장자를 이용)
			dest = new File(storedFolder, storedName);
		} while (dest.exists());

		// ------------------------------------------------------

		try {
			// 업로드된 임시 파일을 upload 폴더로 옮겨 실제 파일을 생성한다
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ------------------------------------------------------

		// DB에 기록하기
		// 기록하기 위해 파일 부분을 떼어낸 새로운 DTO를 new해서 들여온다
		ContentFile contentFile = new ContentFile();

		// 파일 번호 - DB에서 자동으로 순차적으로 부여

		// 게시글 글번호 등록
		contentFile.setBoardNo(fileparam.getBoardNo());

		// 저장경로 등록
		contentFile.setPath(storedPath);

		// 원래 파일이름 등록
		contentFile.setOriginal(file.getOriginalFilename());

		// 저장된 파일이름 등록
		contentFile.setStored(storedName);

		// 저장날짜 시간 - DB에서 자동으로 현재 시각으로 기록

		// DB에 기록
		snsDao.uploadFile(contentFile);

	}

	@Override
	public void removeContent(Content param) {
		// 게시글 삭제
		log.info("Content: {}", param);
		
		// 삭제하려는 게시글에 입력되어 있는 댓글 추천수, 댓글, 게시글 추천, 파일 삭제
		
		//param에서 삭제하려는 게시글번호 추출
		int boardNo = param.getBoardNo();
		
		
		//게시글번호에 맞는 댓글번호 추출
		List<Comment> viewComment = snsDao.viewComment(boardNo);
		
		List<Integer> commentno = new ArrayList<>();
		for (Comment c : viewComment) {
			commentno.add(c.getCommentno());
		}
		log.info("commentno : {}", commentno);
		
		
		// 댓글 추천수 삭제
		for( Integer bn : commentno ) {
			snsDao.delRecommendCommentforRemoveContent(bn);
		}
		
		// 댓글 삭제
		snsDao.removeComment(boardNo);
		
		// 게시물에 업로드된 파일 검색
		List<ContentFile> uploadFiles = snsDao.getFileBoardno(boardNo);
		log.info("uploadFiles : {}", uploadFiles);
		List<String> delFiles = new ArrayList<>();
		
		for (ContentFile file : uploadFiles) {
		    // fileno 값을 추출하여 delFiles 리스트에 추가
		    delFiles.add(String.valueOf(file.getFileno())); // fileno를 추출하고 형변환해서 delFiles에 저장
		}

		log.info("delFiles : {}", delFiles);
		
		//검색된 파일 삭제
		snsService.delfiles(delFiles);
		
		//마지막으로 게시글 삭제
		snsDao.removeContent( param );
	}

	@Override
	public Paging getPagingContent(Paging paging) {
		// 게시물의 페이징 계산하기
		// 전달파라미터 curPage 추출하기
		int curPage = paging.getCurPage();
		
		// 전달파라미터 search 추출하기
		String search = paging.getSearch();
		
		// 전달파라미터 memberno 추출하기
		int memberno = paging.getMemberno();
		
		//아래 코드는 스프링 프레임워크 + 롬복 플러그인때문에 사용X
		//테스트 결과 잘못된 요청이 오면 알아서 스프링 프레임워크에서 자체적으로 요청을 거부한다
		
		//서블릿 게시판 만들때는 추가되어야 하는 코드
//		int curPage = 0;
		
//		if( param != null && !"".equals(param) ) {
//			curPage = Integer.parseInt(param);
//		} else {
//			System.out.println("[경고] BoardService - curPage값이 null이거나 비어있음");
//		}
		
		
		//검색어가 반영된 게시글 수 조회하기(없으면 총 게시글 수 조회한다)
//		int totalCount = snsDao.selectCntAll( search );

		//검색어, 회원정보가 모두 반영된 게시글 수 조회하기(없으면 총 게시글 수 조회한다)
		int totalCount = snsDao.selectCntAll( search, memberno );
		
		//이때 마이바티스가 2개 이상의 변수를 잘 받아들이지 못하는 문제가 발생하니 주의한다.
		//따라서, snsDao에서 넘기려는 2개 이상의 변수 각각을 @Param 어노테이션을 사용해서 넘겨야 한다.
		
		//페이징 계산하기
//		Paging paging2 = new Paging(curPage, totalCount);
		//검색어가 반영된 페이징 계산하기
//		Paging paging2 = new Paging(curPage, totalCount, search);
		//검색어, 회원정보가 모두 반영된 페이징 계산하기
		Paging paging2 = new Paging(curPage, totalCount, search, memberno);
		return paging2;
	}


	@Override
	public Pagingcomm getPagingComm(Pagingcomm paging) {
		// 댓글의 페이징 계산하기
		// 전달파라미터 curPage 추출하기
		int curPage = paging.getCurPage();
		
		// 전달파라미터 boardNo 추출하기
		int boardNo = paging.getBoardNo();
		
		//게시물 번호가 반영된 게시글 수 조회하기(없으면 총 댓글 수 조회한다)
		int totalCount = snsDao.selectCntAllComm( boardNo );
		
		//게시물 번호가 반영된 페이징 계산하기
		Pagingcomm paging2 = new Pagingcomm(curPage, totalCount, boardNo);
		return paging2;
	}


}
