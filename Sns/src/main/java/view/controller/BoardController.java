package view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/main")
@Controller
@Slf4j
public class BoardController {

	@GetMapping("/main")
	public String mainPage() {
		
	    return "main/main"; // main.jsp로 이동
	}
}
