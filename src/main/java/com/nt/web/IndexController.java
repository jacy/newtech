package com.nt.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@RequestMapping(value = { "/", "**/**" })
	String home() {
		return "Welcome to new tech!";
	}

	@RequestMapping(value = { "/ping" })
	String ping() {
		return "Pong.";
	}

}
