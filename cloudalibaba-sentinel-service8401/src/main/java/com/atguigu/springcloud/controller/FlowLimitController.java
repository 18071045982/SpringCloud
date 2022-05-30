package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.springcloud.service.SentinelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

	@Resource
	private SentinelService sentinelService;

	@GetMapping("/testA")
	public String testA()
	{
//		try {
//			TimeUnit.MILLISECONDS.sleep(800);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		sentinelService.sentinelChain();
		log.info(Thread.currentThread().getName());
		return "------testA";
	}

	@GetMapping("/testB")
	public String testB()
	{
//		sentinelService.sentinelChain();
		return "------testB";
	}

	@GetMapping("/testD")
	public String testD() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "------testD";
	}

	@GetMapping("/testHotKey")
	@SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
	public String testHotKey(
			@RequestParam(value = "p1", required = false) String p1,
			@RequestParam(value = "p2", required = false) String p2
	) {
		return "------testHotKey";
	}

	public String deal_testHotKey(String p1, String p2) {
		return "------deal_testHotKey";
	}

}
