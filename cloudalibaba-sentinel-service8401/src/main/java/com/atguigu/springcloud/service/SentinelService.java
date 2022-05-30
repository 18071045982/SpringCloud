package com.atguigu.springcloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class SentinelService {
	@SentinelResource("sentinelChain")
	public String sentinelChain() {
		return "sentinelServiceChain";
	}
}
