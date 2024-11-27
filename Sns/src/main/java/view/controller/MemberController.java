package view.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import view.dao.face.MemberDao;
import view.dto.Login;
import view.dto.Member;
import view.service.face.MemberService;
import view.util.PasswordValidator;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) { 
        this.memberService = memberService;
    }

    
    @GetMapping("/signup")
    public String signupForm() {
        log.info("회원가입 페이지 요청됨");
        return "member/signup"; // 회원가입 페이지로 이동
    }


    @PostMapping("/signup")
    public String signup(@ModelAttribute Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("회원가입 데이터 바인딩 중 오류 발생: {}", result.getAllErrors());
            model.addAttribute("error", "잘못된 입력입니다. 다시 시도하세요.");
            return "member/signup"; // 바인딩 오류 발생 시 회원가입 페이지로 다시 이동
        }
        
        // 비밀번호 유효성 검사
        if (!PasswordValidator.isValid(member.getMemberPW())) {
            model.addAttribute("error",  "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 각각 최소 하나 포함해야 합니다.");
            return "member/signup";
        }
        
        // 가입 시간 설정
        member.setJoinTime(Timestamp.valueOf(LocalDateTime.now()));

        try {
            boolean isSuccess = memberService.signup(member);
            if (isSuccess) {
                return "redirect:/member/login";
            } else {
                model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
                return "member/signup"; // 회원가입 실패 시 다시 회원가입 페이지로 이동
            }
        } catch (Exception e) {
            log.error("회원가입 중 예외 발생: ", e);
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "member/signup"; // 예외 발생 시에도 회원가입 페이지로 돌아감
        }
    }

    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(@RequestParam("memberId") String memberId) {
        log.info("아이디 중복 체크 요청: {}", memberId);
        boolean isDuplicate = memberService.isIdDuplicate(memberId);
        return isDuplicate ? "duplicate" : "available";
    }
    
    @GetMapping("/checkPhone")
    @ResponseBody
    public boolean checkPhoneExists(@RequestParam("phone") String phone) {
        return memberService.isPhoneRegistered(phone);
    }
    
    @GetMapping("/login")
    public String loginForm() {
        log.info("Login form requested");
        return "/member/login"; // login.jsp로 이동
    }
    
    @PostMapping("/login")
    public String login(Login login, RedirectAttributes redirectAttributes, HttpSession session) {
        String loginErrorMessage = null;

        log.info("로그인 시도 - 입력된 아이디: {}, 비밀번호: {}", login.getMemberID(), login.getPassword());

        // 아이디가 존재하는지 확인
        if (!memberService.isUsernameExists(login.getMemberID())) {
            loginErrorMessage = "**  존재하지 않는 아이디입니다  **";
            log.info("로그인 실패 - 존재하지 않는 아이디: {}", login.getMemberID());
        } else {
            // 비밀번호가 맞는지 확인
            boolean isPasswordCorrect = memberService.checkPassword(login);
            log.info("비밀번호 확인 결과: {}", isPasswordCorrect);
            
            if (!isPasswordCorrect) {
                loginErrorMessage = "**  비밀번호가 일치하지 않습니다  **";
                log.info("로그인 실패 - 비밀번호가 일치하지 않음");
            }
        }

        if (loginErrorMessage != null) {
            redirectAttributes.addFlashAttribute("error", loginErrorMessage);
            return "redirect:/member/login"; // 로그인 실패 시 리다이렉트하며 오류 메시지를 전달
        }

        // 로그인 성공 시
        log.info("로그인 성공: {}", login.getMemberID());
        
        //로그인 아이디에 맞는 회원번호를 DB에서 조회하고
        int memberNo = memberService.getMemberno(login);

        log.info("로그인한 회원의 회원번호: {}", memberNo);

        //로그인 여부와 로그인 회원번호를 세션에 기록한다.
        session.setAttribute("islogin", true);
        session.setAttribute("memberNo", memberNo);
        session.setAttribute("memberID", login.getMemberID());
        
        //이후 세션을 이용해서 로그인된 회원만 접근할 수 있는 기능 제한을 걸 것이다.
        
        return "redirect:/main/main"; // 메인 페이지로 리다이렉트
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	// 로그아웃 구현
    	
    	// 세션 삭제
    	//세션이 이미 무효화되었다는 오류가 발생하여서 예외처리를 해야 할듯
    	//원인은 찾았지만, 이미 만들어놓은 예외처리 코드는 그대로 살려둠
    	if(session != null) {
    		session.invalidate();
    	}
    	
    	//로그아웃(세션 삭제) 후 로그인 페이지로 리다이렉트
    	return "redirect:/member/login";
    }
    
    @GetMapping("/myinfo")
    public String myinfo(Model model, HttpSession session) {
    	log.info("회원정보 수정 페이지 요청됨");
    	// 기존 회원정보 조회
    	
		if(String.valueOf(session.getAttribute("islogin")) != "true") {
			//로그인되어있지 않으면 로그인 화면으로 리다이렉트
			return "redirect:/member/login";
		}
		
    	// 로그인 중이라면 다음의 코드를 실행
    	// 세션에서 현재 로그인 중인 회원번호를 호출
    	int memberno = Integer.parseInt ( String.valueOf ( session.getAttribute("memberNo") ) );

    	// 호출하고 새로운 Member 형식 변수 member에 회원정보를 조회하여 저장
    	Member member = memberService.findMemberBymemberno(memberno);
    	
    	// 저장된 회원정보를 모델에 추가하고 View로 포워딩
    	model.addAttribute("myinfo", member);
    	
    	return "member/myinfo"; // 회원정보 수정 페이지로 이동
    }
    @PostMapping("/myinfo")
    public String myinfoProc(@ModelAttribute Member member, BindingResult result, Model model, HttpSession session) {
    	//(코드 구현 예정)
        if (result.hasErrors()) {
            log.info("회원정보 수정 데이터 바인딩 중 오류 발생: {}", result.getAllErrors());
            model.addAttribute("error", "잘못된 입력입니다. 다시 시도하세요.");
            
        	// 기존 회원정보 조회
        	// 세션에서 현재 로그인 중인 회원번호를 호출
        	int memberno = Integer.parseInt ( String.valueOf ( session.getAttribute("memberNo") ) );

        	// 호출하고 새로운 Member 형식 변수 member에 회원정보를 조회하여 저장
        	member = memberService.findMemberBymemberno(memberno);
        	
        	// 저장된 회원정보를 모델에 추가하고 View로 포워딩
        	model.addAttribute("myinfo", member);
            return "member/myinfo"; // 바인딩 오류 발생 시 회원정보 수정 페이지로 다시 이동
        }
        
        // 비밀번호 유효성 검사
        if (!PasswordValidator.isValid(member.getMemberPW())) {
            model.addAttribute("error",  "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 각각 최소 하나 포함해야 합니다.");
            
        	// 기존 회원정보 조회
        	// 세션에서 현재 로그인 중인 회원번호를 호출
        	int memberno = Integer.parseInt ( String.valueOf ( session.getAttribute("memberNo") ) );

        	// 호출하고 새로운 Member 형식 변수 member에 회원정보를 조회하여 저장
        	member = memberService.findMemberBymemberno(memberno);
        	
        	// 저장된 회원정보를 모델에 추가하고 View로 포워딩
        	model.addAttribute("myinfo", member);
            
            return "member/myinfo";
        }
        
        // 가입 시간 설정
//        member.setJoinTime(Timestamp.valueOf(LocalDateTime.now()));

        // 수정하는 회원정보를 세션에서 대입
        member.setMemberNo(Integer.parseInt ( String.valueOf ( session.getAttribute("memberNo") ) ));
        try {
            boolean isSuccess = memberService.myinfo(member);
            if (isSuccess) {
            	// 다시 로그인하도록 로그아웃 후 로그인 화면 호출
            	session.invalidate();
            	
                return "redirect:/member/login";
            } else {
                model.addAttribute("error", "회원정보 수정에 실패했습니다. 다시 시도해주세요.");
                
            	// 기존 회원정보 조회
            	// 세션에서 현재 로그인 중인 회원번호를 호출
            	int memberno = Integer.parseInt ( String.valueOf ( session.getAttribute("memberNo") ) );

            	// 호출하고 새로운 Member 형식 변수 member에 회원정보를 조회하여 저장
            	member = memberService.findMemberBymemberno(memberno);
            	
            	// 저장된 회원정보를 모델에 추가하고 View로 포워딩
            	model.addAttribute("myinfo", member);
                
                return "member/myinfo"; // 회원정보 수정 실패 시 다시 회원정보 수정 페이지로 이동
            }
        } catch (Exception e) {
            log.error("회원정보 수정중 예외 발생: ", e);
            model.addAttribute("error", "회원정보 수정 중 오류가 발생했습니다. 다시 시도해주세요.");
            
        	// 기존 회원정보 조회
        	// 세션에서 현재 로그인 중인 회원번호를 호출
        	int memberno = Integer.parseInt ( String.valueOf ( session.getAttribute("memberNo") ) );

        	// 호출하고 새로운 Member 형식 변수 member에 회원정보를 조회하여 저장
        	member = memberService.findMemberBymemberno(memberno);
        	
        	// 저장된 회원정보를 모델에 추가하고 View로 포워딩
        	model.addAttribute("myinfo", member);
            return "member/myinfo"; // 예외 발생 시에도 회원정보 수정 페이지로 돌아감
        }
    }
    //회원탈퇴
    //회원정보 삭제 구현
    @GetMapping("/leave")
    public String leave(@ModelAttribute Member member, Model model) {
    	//회원탈퇴(코드 구현 예정)
    	//- 이때 회원 관련한 모든 정보를 삭제할 생각이지만, 삭제가 어렵다면, 작성한 게시글과 댓글, 추천은 그대로 남겨둘 것이다
    	
    	return "main/main";
    }
}
