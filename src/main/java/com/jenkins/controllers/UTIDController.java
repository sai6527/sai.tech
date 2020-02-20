package com.jenkins.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UTIDController {

	@GetMapping("/homepage")
	public String getUtidDetails() {
		
		
		System.out.println("======ENTERED======= ");
		
		
		
		return "test";
	}
	
	
}
