package com.atguitu.springcloud.controller;

import com.atguitu.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

	@Resource
	private IMessageProvider messageProvider;

	@GetMapping("/sendMessage")
	public String sendMessage() {
		return messageProvider.send();
	}
}
