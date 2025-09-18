package com.xiaoshen.xiaoshenlike.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

	@PostMapping("/check")
    public String check(){
		return "ok";
	}
}
