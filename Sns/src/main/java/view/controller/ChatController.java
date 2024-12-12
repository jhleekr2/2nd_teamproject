package view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

	@GetMapping("/index")
	public void index(Model model) {
		
	}
	
	@GetMapping("/socket/chat")
	public void chat(Model model) {
		
	}
}
