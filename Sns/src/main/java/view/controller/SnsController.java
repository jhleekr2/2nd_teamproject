
package view.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/sns")
public class SnsController {

    @GetMapping("/main")
    public String login(Model model) {
    	return "login";
    }
    @PostMapping("/main")
    public String main() {
    	return "main";
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
}
