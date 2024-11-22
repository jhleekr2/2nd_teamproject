
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import view.dto.Comment;
import view.dto.Commentlike;
import view.dto.Content;
import view.dto.ContentFile;
import view.dto.Fileparam;
import view.dto.Paging;
import view.dto.Pagingcomm;
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
        
        
        // 어쩌면 덧글과 덧글내용까지 추가될 수도 있음 - 별도의 viewfile을 따로 구성하는 형태로 구성
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

//            // 모델에 isRecommendMap 추가
//            model.addAttribute("recommendMap", isRecommendMap);

            // 게시물의 추천수를 확인하기 위해 recommend라는 DTO를 다시 이용
            // 게시글 추천수 확인
            int Recommendno = snsService.viewRecommend(recommend);
            
            // 조회된 게시글 추천수를 map에 대입
            // 게시물 번호를 키로 하고 추천수를 값으로 추가 대입
            RecommendMap.put(c.getBoardNo(), Recommendno);
            
//            // 모델에 RecommendMap 추가
//            model.addAttribute("numberofRecommend", RecommendMap);
        }
        // 모델에 isRecommendMap 추가
        model.addAttribute("recommendMap", isRecommendMap);
        // 모델에 RecommendMap 추가
        model.addAttribute("numberofRecommend", RecommendMap);

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
    
	@GetMapping("/viewcomment")
	public void viewcomment(Model model, int memberno, int boardNo, Pagingcomm paging) {
		log.info("model : {}", model);
		log.info("memberno : {}", memberno);
		log.info("boardNo : {}", boardNo);
		
		
		//페이징 변수에 조회하고자 하는 게시물번호 대입
		paging.setBoardNo(boardNo);
		
		//전달파라미터를 이용하여 현재 페이징 객체 알아내기
		paging = snsService.getPagingComm(paging);
		
		//View로부터 전달받은 변수 이용하여 댓글 조회
//		List<Comment> list = snsService.viewComment(boardNo);
		
		
		//댓글 페이지네이션 적용
		//본래는 게시글의 페이지네이션을 공용으로 쓰려 했으나
		//변수명이 꼬이는 등의 문제가 발생(스파게티 코드) 된다 판단하여
		//댓글 페이지네이션 DTO(VO)를 분리하여 정의할 생각
		
		//페이지네이션 적용하여 댓글 조회
		List<Comment> list = snsService.viewComment(paging);
		
		
		//BLOB 타입 조회
		//일반적인 방식으로는 조회할 수 없고 다음과 같이 조회해야 한다
		//SELECT DBMS_LOB.SUBSTR(BLOB_COLUMN_NAME) FROM 테이블명;
		//이때 BLOB 타입 중 앞의 4000자만 조회되도록 내용이 잘리는거같다
		//-> 되도록이면 BLOB 타입 사용하지 말자!
		
        // 댓글 작성자 리스트를 저장할 Map
        Map<Integer, String> memberMap = new HashMap<>();
        
        // 댓글 추천수 리스트를 저장할 Map
        Map<Integer, Integer> commentRecommendMap = new HashMap<>();
        
        // 사용자의 댓글 추천여부 리스트를 저장할 Map
        Map<Integer, Integer> isCommentRecommendMap = new HashMap<>();
		
        // 로그인 사용자 번호(테스트용으로 사용자 번호 = 2로 지정하고 테스트)
        //알고보니 이미 viewcomment에서 전달을 받았음
        
		//댓글이 없을 경우 예외처리를 해야 한다는 사실이 테스트 결과 밝혀져서 예외 처리 루틴 추가
		if(list == null) {
			log.info("댓글이 없습니다");
		} else {
			for(Comment c : list) {
				System.out.println(c);
				
				//조회되는 댓글마다 작성자 닉네임과 추천수를 조회해야 한다
				String viewcomment = snsService.checkMemberNick(c);
				log.info("viewcomment: {}", viewcomment);
				
	            // 댓글 번호를 키로 하고 추천 여부를 값으로 추가
	            memberMap.put(c.getCommentno(), viewcomment);
	            
	            
	            //조회되는 댓글마다 추천수 조회
//	            int commentrecommendno = snsService.commentRecommendNo(c);
	            //코드 재사용성을 위해 코드를 다음과 같이 변경해 보자
	            int commentrecommendno = snsService.commentRecommendNo(c.getCommentno());
	            
	            
	            //댓글 번호를 키로 하고 추천수를 값으로 추가
	            commentRecommendMap.put(c.getCommentno(), commentrecommendno);
	            
	            //commentlike라는 새로운 DTO를 정의(댓글 추천여부를 다루는 DTO)
	            Commentlike commentlike = new Commentlike();
	            
	            //DTO에 로그인된 사용자 대입
	            commentlike.setMemberno(memberno);
	            //DTO에 댓글 번호 대입
	            commentlike.setCommentno(c.getCommentno());
	            
	            //조회되는 댓글마다 로그인된 사용자의 추천여부 조회
	            int isCommentRecommend = snsService.iscommentRecommend(commentlike);
	            
	            // 댓글 번호를 키로 하고 추천 여부를 값으로 추가
	            isCommentRecommendMap.put(c.getCommentno(), isCommentRecommend);
	            
			}
			
			
		}
		//모델에 memberMap 추가
		model.addAttribute("memberMap", memberMap);
		//모델에 commentRecomendMap 추가
		model.addAttribute("recommendMap", commentRecommendMap);
		//모델에 iscommentRecommendMap 추가
		model.addAttribute("isCommentRecommendMap", isCommentRecommendMap);
		
    	//페이징 정보를 paging이라는 프론트단 호출 변수로 모델에 추가
    	model.addAttribute("paging", paging);
		
        // 모델에 list 추가(프론트단에서는 listcomment로 호출                 
        model.addAttribute("listcomment", list);
        
	}

	@ResponseBody //응답값을 뷰페이지 없이 그대로 응답
	@PostMapping("/recommendcomment") //기존 게시글 추천 코드를 상당부분 재사용했다
	public RecommendInfo recommendcommentProc(Model model, int memberno, int commentno) {
		log.info("model : {}", model);
		log.info("memberno : {}", memberno);
		log.info("commentno : {}", commentno);
		
        // 사용자의 댓글 추천여부를 확인하기 위해 Commentlike라는 DTO를 따로 정의
        Commentlike commentlike = new Commentlike();
        
        // 전달된 memberno와 commentno를 Commentlike DTO에 대입
        commentlike.setCommentno(commentno);
        commentlike.setMemberno(memberno);
        
        // 사용자의 댓글 추천여부 확인
        int isCommentRecommend = snsService.iscommentRecommend(commentlike);
        
        if (isCommentRecommend == 0) {
        	//댓글이 추천되어 있지 않을때
        	//댓글 추천수 추가
        	snsService.addRecommendComment(commentlike);
        }
        else {
        	//이미 댓글이 추천되어 있을 때
        	//댓글 추천수 삭제
        	snsService.delRecommendComment(commentlike);
        }
        
        // 댓글의 추천수를 확인하기 위해 recommend라는 DTO를 다시 이용
        // 댓글 추천수 확인
        int commentrecommendno = snsService.commentRecommendNo(commentno);
        
        //반환값으로 게시글 추천수와 추천여부를 업데이트해서 AJAX 방식으로 전송하는 것을 검토한다
        RecommendInfo result = new RecommendInfo();
        //이때 RecommendInfo DTO는 원래 게시글 추천수를 다루는 DTO지만
        //댓글 추천수 부분에서 대입하는 변수명만 바꿔서 재사용한다
        
        // 사용자의 댓글 추천여부 다시 확인
        isCommentRecommend = snsService.iscommentRecommend(commentlike);
        
        //확인된 글번호, 댓글 추천여부, 추천수 대입하고 반환
        result.setBoardNo(commentno);
        result.setIsRecommend(isCommentRecommend);
        result.setRecommendno(commentrecommendno);
    	return result;
	}
	
	@ResponseBody //응답값을 뷰페이지 없이 그대로 응답 
	@PostMapping("/uploadcomment")
	public String uploadcomment(Model model, int boardNo, int memberno, String upcomment) {
		log.info("uploadcomment() 호출");
		log.info("model : {}", model );
		log.info("boardNo: {}", boardNo);
		log.info("memberno: {}", memberno);
		log.info("upcomment: {}", upcomment);
		
		//AJAX 응답값
		String response;
		
		//댓글 추가를 위해서 먼저 Comment타입의 DTO변수 하나를 정의한다
		Comment param = new Comment();
		
		param.setBoardNo(boardNo);
//		param.setCommentno(memberno) -> DB에서 자동부여
		param.setContent(upcomment);
//		param.setDate(null) -> DB에서 자동부여
		param.setMemberno(memberno);
		
		//DB에 댓글 추가
		snsService.addComment(param);
        // 성공적으로 추가되었으면 응답
		
		response = "1";
		
		//응답 루틴을 try-catch 이용하여 댓글 추가/삭제 성공시와 실패시의
		//AJAX 응답을 다르게 만드는 추가 개발을 할 수도 있다.
    return response;
	}
	
	@ResponseBody //응답값을 뷰페이지 없이 그대로 응답 
	@PostMapping("/delcomment")
	public String delcomment(Model model, int boardNo, int memberno, int commentno) {
		log.info("delcomment() 호출");
		log.info("model : {}", model );
		log.info("boardNo: {}", boardNo);
		log.info("memberno: {}", memberno);
		log.info("commentno: {}", commentno);
		
		//AJAX 응답값
		String response;
		
		//댓글 삭제를 위해서 먼저 Comment타입의 DTO변수 하나를 정의한다
		Comment param = new Comment();
		
		param.setBoardNo(boardNo);
		param.setMemberno(memberno);
//		param.setContent(upcomment); -> 필요없음
//		param.setDate(null) -> 필요없음
		param.setCommentno(commentno);
		
		//테스트 결과 추천수가 있는 댓글이 삭제되지 않는 문제점이 발견
		//추천수가 있으면 추천수 먼저 삭제 후 DB에서 댓글 삭제해야함
		//snsService.delComment에 해당 코드 추가
		
		//DB에서 댓글 삭제
		snsService.delComment(param);
 
		response = "1";
    return response;
	}
	
    @GetMapping("/update")
    public void update(Model model, Paging paging) {
    	log.info("update() 호출");
    	//로그인 여부 확인 후 로그인되어 있으면 진행
    	int memberno = 2; //임시로 회원번호 = 2로 설정하고 개발 진행
    	
    	//페이징 변수에 조회하고자 하는 회원번호 대입
    	paging.setMemberno(memberno);
    	
    	//이때 검색기능도 추가할 수 있다.
    	
		//전달파라미터를 이용하여 현재 페이징 객체 알아내기
		paging = snsService.getPagingContent(paging);
    	
    	//로그인된 사용자가 작성한 게시글 목록 조회
//    	List<Content> list = snsService.listmember( memberno );
    	
		//로그인된 사용자가 작성한 게시글 목록을 페이지네이션 적용하여 조회
		List<Content> list = snsService.listmember( paging );
    	
		//페이징 정보를 paging이라는 프론트단 호출 변수로 모델에 추가
		model.addAttribute("paging", paging);
		
		//조회된 리스트를 contentlist라는 프론트단 호출 변수로 모델에 추가
		model.addAttribute("contentlist", list);
		
    }
    
    @GetMapping("/updatecontent")
    public void updatecontent(Model model, int boardNo) {
    	log.info("updatecontent() 호출");
    	//로그인 여부 확인 후 로그인되어 있으면 진행
    	int memberno = 2; //임시로 회원번호 = 2로 설정하고 개발 진행
    	log.info("boardNo: {}", boardNo ); //정상적으로 파라미터가 전달된다
    	
    	//전달받은 회원번호 및 게시글번호와 일치하는 게시물 조회
    	
    	//조회하기 위해서 Content 형식의 매개변수를 정의하고
    	//나중에 조회한 게시물 정보를 저장하도록 한다
    	Content param = new Content();
    	
    	//정의된 매개변수에 조회하고자 하는 값 대입
    	param.setMemberno(memberno);
    	param.setBoardNo(boardNo);
    	
    	//전달받은 회원번호 및 게시글번호와 일치하는 게시물 조회
    	param = snsService.chkContentDB(param);
    	
    	//조회하고 View로 전달
        // 모델에 content param 추가(프론트단에서는 content로 호출)                 
        model.addAttribute("content", param);
        
        //업로드된 파일 조회
        
        // 업로드된 게시글에 해당하는 파일 리스트 조회하여 파일 정보를 DB에서 가져온다
        // 이때 메인 뷰에서 파일을 보여주기 위해 사용했던 코드를 재활용한다
        List<ContentFile> filelist = snsService.viewPhoto(param);
        
        log.info("filelist: {}", filelist);
        //조회하고 View로 전달
        // 모델에 filelist 추가(프론트단에서는 filelist로 호출)
        model.addAttribute("filelist", filelist);
    }
    
    
    @PostMapping("/update")
    public String updateProc(
    		@RequestParam(value = "delCheck", required = false) List<String> delFiles, // 삭제할 파일 번호
    		Content param, Fileparam fileparam
    		) {
    	log.info("delFiles: {}", delFiles);
    	log.info("param: {}", param);
    	log.info("fileparam : {}", fileparam);
    	
    	//삭제 요청 들어간 파일 삭제
    	snsService.delfiles( delFiles );
    	
    	//게시글 업데이트 및 파일 업로드
    	snsService.updateContent(param, fileparam);
    	
    	//게시글 목록으로 리다이렉트 or 포워딩(코드 추가 예정)
    	return "redirect:view";
    }
    
    @GetMapping("/delete")
    public void delete(Model model, int boardNo) {
    	//게시물 삭제
    	
    	//임시로 회원번호 = 2로 두고 개발
    	int memberno = 2;
    	
    	//게시글 삭제를 위해 Content param을 정의하고 회원번호와 게시글번호를 대입
    	Content param = new Content();
    	
    	//받은 조건들을 대입하고 조건에 맞는 게시글을 삭제
    	param.setBoardNo(boardNo);
    	param.setMemberno(memberno);
    	
    	//게시글 삭제
    	snsService.removeContent( param );
    }
}
