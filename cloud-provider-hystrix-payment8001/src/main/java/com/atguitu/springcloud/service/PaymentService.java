package com.atguitu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
	public String paymentInfo_OK(Long id) {
		return "线程池" + Thread.currentThread().getName() + "paymentInfo_OK, id: " + id;
	}

	@HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
	})
	public String paymentInfo_TimeOut(Long id) {
//		try {
//			TimeUnit.SECONDS.sleep(2);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		int i = 10 / 0;
		return "线程池" + Thread.currentThread().getName()  + "paymentInfo_TimeOut, O(∩_∩)O id: " + id;
	}

	public String paymentInfo_TimeOutHandler(Long id) {
		return "线程池" + Thread.currentThread().getName() + "paymentInfo_TimeOutHandler, 调用支付接口超时或异常";
	}

	// 服务熔断
	@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
	})
	public String paymentCircuitBreaker(Long id) {
		if (id < 0) {
			throw new RuntimeException("******id 不能负数");
		}
		String serialNumber = IdUtil.simpleUUID();
		return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
	}

	public String paymentCircuitBreaker_fallback(Long id) {
		return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
	}
}
