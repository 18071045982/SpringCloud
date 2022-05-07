package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
	@Resource
	private PaymentService paymentService;

	@Value("${server.port}")
	private String serverPort;

	@PostMapping("/create")
	public CommonResult<Integer> create(@RequestBody Payment payment) {
		int result = paymentService.create(payment);
		log.info("******插入操作返回结果：" + result);

		if (result > 0) {
			return new CommonResult(200, "插入数据库成功, serverPort: " + serverPort, result);
		} else {
			return new CommonResult(400, "插入数据库失败", null);
		}
	}

	@GetMapping("/getPaymentById/{id}")
	public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
		Payment payment = paymentService.getPaymentById(id);
		log.info("******查询结果：" + payment);

		if (payment != null) {
			return new CommonResult<>(200, "查询成功, serverPort: " + serverPort, payment);
		} else {
			return new CommonResult<>(400, "查询失败，id：" + id, null);
		}
	}

	@GetMapping(value = "/lb")
	public String getPaymentLB()
	{
		return serverPort;
	}
}
