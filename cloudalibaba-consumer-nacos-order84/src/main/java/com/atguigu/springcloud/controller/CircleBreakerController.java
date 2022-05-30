package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {

	public static final String SERVICE_URL = "http://nacos-payment-provider";

	@Resource
	private RestTemplate restTemplate;

	@GetMapping("/consumer/fallback/{id}")
	@SentinelResource(value = "fallback", fallback = "handlerFallback")
	public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
		CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
		if (id == 4) {
			throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
		} else if (result.getData() == null) {
			throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
		}

		return result;
	}

	public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id, Throwable e) {
		return new CommonResult<>(444,"兜底异常handlerFallback,exception内容  "+e.getMessage(), new Payment(id, null));
	}


	//==================OpenFeign
	@Resource
	private PaymentService paymentService;

	@GetMapping(value = "/consumer/paymentSQL/{id}")
	public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
	{
//		if(id == 4)
//		{
//			throw new RuntimeException("没有该id");
//		}
		return paymentService.paymentSQL(id);
	}
}
