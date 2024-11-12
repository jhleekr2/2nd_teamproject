
package view.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Fileparam;
import view.dto.Recommend;
import view.dto.RecommendInfo;
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
    	
    	//View로 리다이렉트
    	return "redirect:./view";
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
    	//현재 게시글에 아무것도 안넣고 등록하려 하면 에러가 발생하는 예외 처리가 안되어 있음이 발견되었다!
    	
    	return "redirect:./view";
    }
    
    @GetMapping("/view")
    public String view(Content content, Model model) {
    	log.info("view() 호출");
    	
    	//랜덤한 게시물 목록 조회(단, 조회순서는 최근글 순으로 조회한다)
    	//조회할때 본래 AI가 있어야 한다
    	//AI로서 가중치 랜덤 알고리즘을 사용한다
    	
    	//랜덤한 게시글 목록 조회
    	List<Content> list = snsService.list();
    	
//    	for( Content c : list ) {
//    		System.out.println(c);
//    		
//    		//조회된 리스트를 contentlist라는 프론트단 호출 변수로 모델에 추가
//    		model.addAttribute("contentlist", list);
//    		
//    		//해당 게시물 번호에 맞는 업로드된 파일을 조회해서 그림을 프론트에 출력하는 코드 개발
//    		List<ContentFile> filelist = snsService.viewPhoto( c );
//    		
//    		//조회된 업로드된 파일을 filelist라는 프론트단 호출 변수로 모델에 추가
//    		model.addAttribute("filelist_" + c.getBoardNo(), filelist);
//    		//해당 게시물 번호에 맞는 댓글을 조회해서 댓글을 프론트에 출력하고 수정할 수 있도록 함
//    	}
    	
    	// 파일 리스트를 저장할 Map
        Map<Integer, List<ContentFile>> fileMap = new HashMap<>();

        // 추천여부를 저장할 Map
        Map<Integer, Integer> isRecommendMap = new HashMap<>();
        
        // 추천수를 저장할 Map
        Map<Integer, Integer> RecommendMap = new HashMap<>();
        
        // 댓글 리스트를 저장할 Map
//        Map<Integer, List<Comment>> commentMap = new HashMap<>();
        
        // 어쩌면 덧글과 덧글내용까지 추가될 수도 있음
        // 각 게시물에 대해 파일 목록, 추천 여부, 추천수를 조회하고 Map에 저장
        for (Content c : list) {
            System.out.println(c);
            
    		//조회된 리스트를 contentlist라는 프론트단 호출 변수로 모델에 추가
    		model.addAttribute("contentlist", list);
    		
            // 게시물 번호에 맞는 업로드된 파일을 조회
            List<ContentFile> filelist = snsService.viewPhoto(c);

            // 게시물 번호를 키로 하고 파일 리스트를 값으로 추가
            fileMap.put(c.getBoardNo(), filelist);
           
            // 사용자의 게시글 추천여부를 확인하기 위해 Recommend라는 DTO를 따로 정의
            Recommend recommend = new Recommend();
            
            // 조회된 게시글 번호를 recommend DTO에 대입
            recommend.setBoardNo(c.getBoardNo());
            
            // 테스트용 사용자 번호를 recommend DTO에 대입
            recommend.setMemberno(2);
            
            // 사용자의 게시글 추천여부 확인
            int isRecommend = snsService.checkRecommend(recommend);
            log.info("isRecommend: {}", isRecommend);
            
            // 게시물 번호를 키로 하고 추천 여부를 값으로 추가
            isRecommendMap.put(c.getBoardNo(), isRecommend);

            // 모델에 isRecommendMap 추가
            model.addAttribute("recommendMap", isRecommendMap);

            // 게시물의 추천수를 확인하기 위해 recommend라는 DTO를 다시 이용
            // 게시글 추천수 확인
            int Recommendno = snsService.viewRecommend(recommend);
            
            // 조회된 게시글 추천수를 map에 대입
            // 게시물 번호를 키로 하고 추천수를 값으로 추가 대입
            RecommendMap.put(c.getBoardNo(), Recommendno);
            
            // 모델에 RecommendMap 추가
            model.addAttribute("numberofRecommend", RecommendMap);
        }

        // 모델에 fileMap 추가                 
        model.addAttribute("fileMap", fileMap);
    	
        
    	return "view";
    }
    
    @RequestMapping("/image")
    public String imageview(Model model, int fileno) {
    	// FileDownloadView를 이용해서 Http 응답을 파일로 받아서 처리
    	// 프론트단에서는 file.fileno로 코드 적힐 예정
    	log.info("imageview() 호출");
    	log.info("fileno: {}", fileno);
    	
		// 파일 번호(PK)에 해당하는 파일 정보를 DB에서 가져오기
		ContentFile file = snsService.getimage(fileno);
		log.info("file : {}", file);

		// 모델값 전달
		model.addAttribute("viewimage", file);

		// viewName을 응답용 객체 뷰(스프링 빈)의 이름으로 지정한다
		return "viewimage";
    }
    
	@ResponseBody //응답값을 뷰페이지 없이 그대로 응답 
    @GetMapping("/recommend")
    public RecommendInfo recommend(Model model, int memberno, int boardNo) {
    	log.info("recommend() 호출");
    	log.info("model: {}", model);
    	log.info("memberno: {}", memberno);
    	log.info("boardNo: {}", boardNo);
    	
        // 사용자의 게시글 추천여부를 확인하기 위해 Recommend라는 DTO를 따로 정의
        Recommend recommend = new Recommend();
        
        // 전달된 memberno와 boardNo를 Recommend DTO에 대입
        recommend.setBoardNo(boardNo);
        recommend.setMemberno(memberno);
        
        // 사용자의 게시글 추천여부 확인
        int isRecommend = snsService.checkRecommend(recommend);
        
        if (isRecommend == 0) {
        	//게시글이 추천되어 있지 않을때
        	//게시글 추천수 추가
        	snsService.addRecommend(recommend);
        }
        else {
        	//이미 게시글이 추천되어 있을 때
        	//게시글 추천수 삭제
        	snsService.delRecommend(recommend);
        }
        
        // 게시물의 추천수를 확인하기 위해 recommend라는 DTO를 다시 이용
        // 게시글 추천수 확인
        int Recommendno = snsService.viewRecommend(recommend);
        
        //반환값으로 게시글 추천수와 추천여부를 업데이트해서 AJAX 방식으로 전송하는 것을 검토한다
        RecommendInfo result = new RecommendInfo();
        
        // 사용자의 게시글 추천여부 다시 확인
        isRecommend = snsService.checkRecommend(recommend);
        
        //확인된 글번호, 게시글 추천여부, 추천수 대입하고 반환
        result.setBoardNo(boardNo);
        result.setIsRecommend(isRecommend);
        result.setRecommendno(Recommendno);
    	return result;
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
