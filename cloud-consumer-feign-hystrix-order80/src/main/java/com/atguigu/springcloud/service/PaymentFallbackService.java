package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
	@Override
	public String paymentInfo_OK(Long id) {
		return "服务调用失败，提示来自：PaymentFallbackService-paymentInfo_OK";
	}

	@Override
	public String paymentInfo_TimeOut(Long id) {
		return "服务调用失败，提示来自：PaymentFallbackService-paymentInfo_TimeOut";
	}
}
