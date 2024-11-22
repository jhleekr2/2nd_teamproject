package view.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import view.dto.Member;
import view.service.face.MemberService;

@Controller
@Slf4j
@RequestMapping("/find")
public class FindAccountController {

    private final MemberService memberService;

    public FindAccountController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/idpw")
    public String idpw() {
        return "/find/idpw";
    }

    @GetMapping("/id/idEN")
    public String idEN() {
        return "/find/id/idEN";
    }

    @GetMapping("/id/idEmail")
    public String idEmail() {
        return "/find/id/idEmail";
    }

    @RequestMapping(value = "/id/findAccountByEmail", method = RequestMethod.POST)
    public String findIdByEmail(@RequestParam("email") String email, Model model) {
        Member member = memberService.findMemberByEmail(email);
        
        if (member != null) {
            model.addAttribute("memberId", member.getMemberID());
            model.addAttribute("memberName", member.getName());
            return "/find/id/findEid";
        } else {
            model.addAttribute("error", "등록된 계정이 없습니다.");
            return "/find/id/findEid";
        }
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkEmailExists(@RequestParam("email") String email) {
        return memberService.isEmailRegistered(email);
    }
    
    @GetMapping("/id/idNumber")    
    public String idNumber() {
        return "/find/id/idNumber"; 
    }

    // 전화번호로 계정 찾기
    @RequestMapping(value = "/id/findAccountByPhone", method = RequestMethod.POST)
    public String findIdByPhone(@RequestParam("phone") String phone, Model model, RedirectAttributes redirectAttributes) {
        List<Member> members = memberService.findMembersByPhone(phone);

        if (members != null && !members.isEmpty()) {
            if (members.size() == 1) {
                Member member = members.get(0);
                model.addAttribute("memberId", member.getMemberID());
                model.addAttribute("memberName", member.getName());
                return "/find/id/findNid";
            } else {
                redirectAttributes.addFlashAttribute("error", "해당 전화번호로 여러 계정이 발견되었습니다.");
                return "redirect:/find/id/duplicateAccounts";
            }
        } else {
            model.addAttribute("error", "해당 전화번호로 등록된 계정이 없습니다.");
            return "/find/id/findNid";
        }
    }

 // 전화번호가 등록되어 있는지 확인
    @RequestMapping(value = "/checkPhone", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkPhoneExists(@RequestParam("phone") String phone) {
        return memberService.isPhoneRegistered(phone);
    }
    
    @GetMapping("/id/duplicateAccounts")
    public String duplicateAccounts() {
        return "/find/id/duplicateAccounts";
    }
    
    @GetMapping("/pw/pwNumber")
    public String pwNumber() {
        return "/find/pw/pwNumber";
    }

    // 전화번호와 이름이 모두 일치하는지 확인하는 메서드
    @RequestMapping(value = "/checkPhoneAndName", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkPhoneExists(@RequestParam("name") String name, @RequestParam("phone") String phone) {
        return memberService.isNameAndPhoneRegistered(name, phone);
    }

    @RequestMapping(value = "/pw/findAccountByPhoneAndName", method = RequestMethod.POST)
    public String findPasswordByPhoneAndName(@RequestParam("name") String name, 
                                             @RequestParam("phone") String phone, 
                                             Model model, 
                                             RedirectAttributes redirectAttributes) {
        log.info("Received name: " + name + ", phone: " + phone);  // 로그 확인

        // 전화번호와 이름을 통해 회원을 찾음
        Member member = memberService.findMemberByNameAndPhone(name, phone);

        if (member != null) {
            // 회원 정보를 모델에 추가
            model.addAttribute("memberId", member.getMemberID());
            model.addAttribute("memberName", member.getName());
            
            // 리다이렉트 시 파라미터 추가
            redirectAttributes.addAttribute("name", name);
            redirectAttributes.addAttribute("phone", phone);
            
            // resetPassword 페이지로 리다이렉트
            return "redirect:/find/pw/resetPassword";
        } else {
            // 회원이 없으면 에러 메시지와 함께 pwNumber 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("error", "해당 이름과 전화번호로 등록된 계정이 없습니다.");
            return "redirect:/find/pw/pwNumber";
        }
    }


    @GetMapping("/pw/resetPassword")
    public String showResetPasswordPage(@RequestParam("name") String name, 
                                        @RequestParam("phone") String phone,
                                        Model model) {
        // 전달된 name과 phone을 모델에 추가하여 JSP로 전달
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        return "/find/pw/resetPassword";
    }


    @RequestMapping(value = "/pw/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("name") String name, 
                                 @RequestParam("phone") String phone, 
                                 Model model) {

        // 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "/find/pw/resetPassword";
        }

        // 회원 정보 조회
        Member member = memberService.findMemberByNameAndPhone(name, phone);

        if (member == null) {
            model.addAttribute("error", "이름과 전화번호가 일치하는 회원이 없습니다.");
            return "/find/pw/resetPassword";
        }

        // 기존 비밀번호와 새 비밀번호가 같은지 확인
        if (member.getMemberPW().equals(newPassword)) {
            model.addAttribute("error", "기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
            return "/find/pw/resetPassword";
        }

        // 비밀번호 업데이트 시도
        boolean isUpdated = memberService.updatePassword(member.getMemberID(), newPassword);

        if (isUpdated) {
            return "redirect:/member/login";
        } else {
            model.addAttribute("error", "비밀번호 변경 실패. 다시 시도해 주세요.");
            return "/find/pw/resetPassword";
        }
    }



}
