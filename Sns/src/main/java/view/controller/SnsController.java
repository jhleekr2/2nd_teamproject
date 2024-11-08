
package view.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import view.dto.Content;
import view.dto.Fileparam;
import view.service.face.SnsService;


@Controller
@Slf4j
@RequestMapping("/sns")
public class SnsController {

	//서비스 객체 의존성 주입
//	private SnsService snsService = new SnsServiceImpl(); 코드 대신해서 다음과 같은 코드 사용

	@Autowired 
	private SnsService snsService;
	
    @GetMapping("/main")
    public String login(Model model) {
    	log.info("main() 호출");
    	
    	//로그인 여부 확인 후 로그인되어 있으면 진행
    	
    	
    	//전체 게시글 목록 조회
    	List<Content> list = snsService.list();
    	
    	model.addAttribute("list", list);
    	
    	log.info("list : {}", list);
    	
    	//리스트 정상적으로 동작함
    	return "login";
    }
    @PostMapping("/main")
    public String main(Content content, Model model) {
    	log.info("main() [POST] 호출");
    	
    	//기존에 이 부분에 위치하던 업로드 코드를 다른 곳으로 바꿈
    	
    	return "./main";
    }
    @GetMapping("/newlogin")
    public String signup() {
        return "Newlogin";
    }
    @GetMapping("/findAccount")
    public String findAccount() {
    	return "findAccount";
    }
    @GetMapping("/findid")
    public String findid() {
    	return "findid";
    }
    @PostMapping("/findid")
    public String findedid() {
    	return "findfind";
    }
    
    
    @GetMapping("/findpw")
    public String findpw() {
    	return "findpw";
    }
    
    @PostMapping("/findpw")
    public String findedpw() {
    	return "findfindpw";
    }
    
    @GetMapping("/upload")
    public String upload() {
    	
    	
    	
    	
    	return "upload/upload";
    	
    	
    }
    
    @PostMapping("/upload")
//    public String uploadProc(Content content, Model model, Fileparam fileparam, MultipartFile file) {
    public String uploadProc(Content content, Model model, Fileparam fileparam) {
    	log.info("uploadProc() [POST] 호출");
    	
    	//파일 업로드 및 게시글 추가 코드 있어야 함
    	
    	log.info("content 변수값 : {}", content);
    	log.info("모델 변수값 : {}", model);
//    	log.info("파일 정보 : {}", file);
    	log.info("파일 DTO : {}", fileparam);
    	//현재 개발상황 - 글쓰기 내용은 넘어오나, 한글이 깨지고 있는 상황
    	//웹 필터 기능(web.xml) 파일 수정으로 해결
    	
    	//파일 처리를 동반해야 하기에 multipart 방식 필요
    	//-> servlet-context.xml 파일 수정
    	
    	//로그인 부분과 결합하여 게시글 작성 사용자번호를 세션으로부터 넣겠지만
    	//현재 단계에서는 세션부분 고려하지 않고 테스트데이터를 대신 넣음
    	content.setMemberno(2); //테스트용 Memberno = 2
    	
    	//게시글 등록
    	snsService.addContent(content, fileparam);
    	
    	return "./main";
    }
    
    @GetMapping("/view")
    public String view() {
    	log.info("view() 호출");
    	
    	return "view";
    }
    
    @GetMapping("/update")
    public void update() {
    	
    }
    
    @PostMapping("/update")
    public void updateProc() {
    	
    }
    
    @GetMapping("/delete")
    public void delete() {
    	
    }
}
