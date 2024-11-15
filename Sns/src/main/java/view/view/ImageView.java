package view.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import lombok.extern.slf4j.Slf4j;
import view.dto.ContentFile;

//기존의 JSTL과 JSP를 이용한 View 대신 이번에는 파일을 다운로드받기 위한
//새로운 타입의 View를 만들어 본다
//응답 View가 기존 HTML(JSP)기반, 여기 만든 파일 다운로드 자바 기반,
//외에도 json으로 응답하는 View 형태도 있다
@Component
@Slf4j
public class ImageView extends AbstractView {

    @Autowired
    private ServletContext context;

    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model, 
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 모델에서 파일 정보 가져오기
        ContentFile file = (ContentFile) model.get("viewimage");

        // 파일 경로와 이름 가져오기
        String filePath = file.getPath() + File.separator + file.getStored();
        String originalFileName = file.getOriginal();

        log.info("인코딩");
        // 파일이 존재하는지 확인
        File src = new File(filePath);
        if (!src.exists()) {
            log.error("파일이 존재하지 않습니다. filePath: {}", filePath);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 응답 데이터의 유형 설정
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
//            mimeType = "application/octet-stream";
            mimeType = "image";
        }
        response.setContentType(mimeType);

		// 응답 데이터의 크기 설정
		response.setContentLengthLong( src.length() );		

		//응답 데이터의 인코딩 설정
		String encodedFileName = URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20");
		
        // 파일 다운로드 헤더 설정
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

		//** 응답 메시지의 BODY영역 설정하기
		//서버 File객체(src) -> FileInputStream 입력 -> HTTP Response OutputStream 출력
		
		//서버의 파일 입력 스트림 객체
		FileInputStream in = new FileInputStream(src);
		
		//클라이언트의 응답 출력 스트림 객체
		OutputStream out = response.getOutputStream();
		
		//이제 in에서 out으로 read/write 해주면 된다
		
		//in스트림에서 out스트림으로 복사
		FileCopyUtils.copy(in, out);
		//Spring Framework에서 제공하는 라이브러리인데 내부 구조는 이전에 배웠던
		//자바 파일 처리 코드로 되어 있다
        
    }
}