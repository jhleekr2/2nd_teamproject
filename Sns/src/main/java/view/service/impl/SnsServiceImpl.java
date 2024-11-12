package view.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import view.dao.face.SnsDao;
import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Fileparam;
import view.dto.Recommend;
import view.service.face.SnsService;

@Service
@Slf4j
public class SnsServiceImpl implements SnsService {

	//서비스 객체 연결
	@Autowired private SnsDao snsDao;
	
	//컨텍스트 서비스 객체 연결
	@Autowired private ServletContext context;
	
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
		
		//게시물 등록 및 파일 처리
		
		//게시물 등록
		
		//이때 마이바티스에서 글번호를 반환해야 한다
		//(파일 처리에 필요)
		
		//마이바티스에서 useGeneratedKeys="true" keyProperty="boardNo" 옵션 지정하면
		//알아서 Content타입 변수 content에 boardNo를 대입해 준다는 것을 확인했다.
		//-> 따라서, content.setBoardNo(snsDao.addConTentDB( content )); 따위는 필요없다
		snsDao.addContentDB(content);
		log.info("boardNo: " + content.getBoardNo());
		//파일 처리 파라미터에 마이바티스에서 반환된 글번호 대입
		fileparam.setBoardNo(content.getBoardNo());
		
		//파일 처리
		log.info("content : {}", content);
		log.info("fileparam : {}", fileparam);
		
		//파일 업로드 절차 시작
		MultipartFile file = fileparam.getFile();
		
		//------------------------------------------------------
		if( file.isEmpty() || file.getSize() <= 0 ) {
			log.info("파일 업로드 잘못됨, 처리 중단!");
			
			//파일 처리 메소드 filesave() 중단
			return;
		}
		
		//------------------------------------------------------

		//파일의 저장 경로 - Real Path
		String storedPath = context.getRealPath("upload");
		log.info("storedPath : {}", storedPath);
		
		//upload 폴더가 없으면 생성하기
		File storedFolder = new File(storedPath);
		storedFolder.mkdir();
		
		//업로드된 파일이 저장되는 이름
		String storedName = null;
		
		//파일을 저장시킬 객체
		File dest = null;
		
		//매우 낮은 확률로 이전 파일을 덮어씌울 수 있기 때문에 안전장치로 넣어둔 코드
		//저장될 파일명이 중복되지 않도록 반복한다
//		do {
//			storedName = file.getOriginalFilename(); //원본 파일명
//			storedName += UUID.randomUUID().toString().split("-")[4]; //UUID 추가
//			//랜덤 파일명 만드는 법 중 UUID법을 사용했고, 중복이름 검사는 반드시 해야한다
//			dest = new File( storedFolder, storedName );
//		} while ( dest.exists() );
		
		//실제 SNS코드 분석 후 저장 파일명을 변경해야 할것 같다
		do {
			storedName = UUID.randomUUID().toString().split("-")[4];
			storedName += ".";
			storedName += StringUtils.getFilenameExtension(file.getOriginalFilename());
			//SNS코드 분석 결과에 따라 저장 파일명 규칙을 변경했다.
			//새로운 저장 파일명 규칙 = UUID + . + 파일의 원래 확장자(나중에 이미지 보여줄때 확장자를 이용)
			dest = new File( storedFolder, storedName );
		} while ( dest.exists() );
		
		
		//------------------------------------------------------
		
		try {
			// 업로드된 임시 파일을 upload 폴더로 옮겨 실제 파일을 생성한다
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//------------------------------------------------------
		
		//DB에 기록하기
		//기록하기 위해 파일 부분을 떼어낸 새로운 DTO를 new해서 들여온다
		ContentFile contentFile = new ContentFile();
		
		//파일 번호 - DB에서 자동으로 순차적으로 부여
		
		//게시글 글번호 등록
		contentFile.setBoardNo(fileparam.getBoardNo());
		
		//저장경로 등록
		contentFile.setPath(storedPath);
		
		//원래 파일이름 등록
		contentFile.setOriginal(file.getOriginalFilename());
		
		//저장된 파일이름 등록
		contentFile.setStored(storedName);
		
		//저장날짜 시간 - DB에서 자동으로 현재 시각으로 기록
		
		//DB에 기록
		snsDao.uploadFile( contentFile );
		
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
		
		//파일 번호에 맞는 파일 정보 호출
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
}
