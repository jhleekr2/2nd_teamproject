package view.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    public String login(Login login, RedirectAttributes redirectAttributes) {
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
        return "redirect:/main/main"; // 메인 페이지로 리다이렉트
    }
    
    
    
}
