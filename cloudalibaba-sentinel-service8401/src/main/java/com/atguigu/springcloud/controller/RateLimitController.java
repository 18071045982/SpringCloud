package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
	@GetMapping("/byResource")
	@SentinelResource(value = "byResource", blockHandler = "handleException")
	public CommonResult byResource() {
		return new CommonResult(200, "按资源名称限流测试OK", new Payment(2022L, "serial001"));
	}

	public CommonResult handleException(BlockException blockException) {
		return new CommonResult(400, blockException.getClass().getCanonicalName() + "\t 服务不可用");
	}
}
