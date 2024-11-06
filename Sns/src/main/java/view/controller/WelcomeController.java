package view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String main() {
//		return "member/main";
		return "forward:/member/main";
	}
	
}




