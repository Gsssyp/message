package com.dtsz.action;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtsz.biz.IMailConfigService;
import com.dtsz.biz.MailMessageSender;
import com.dtsz.entity.MailConfig;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: MailConfigController 
 * @Description: TODO
 * @see: 
 * @author: Gsy
 * @date: 2019年3月26日 下午8:54:24 
 * @version :1.0 
 */
@RestController
public class MailConfigController {
	
	@Resource
	MailMessageSender mailMessageSender;
	@Resource
	IMailConfigService mailConfigService;
	
	/**
	 * 测试发送邮件接口
	 * @param subject
	 * @param message
	 * @param mail
	 * @return
	 */
	@ApiOperation(value="测式发送邮件", notes="测式用的")
	@GetMapping("/sendMail")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "subject", value = "邮件主题", required = true, dataType = "字符串"),
		@ApiImplicitParam(name = "message", value = "邮件内容", required = true, dataType = "字符串"),
		@ApiImplicitParam(name = "mail", value = "邮件接收者", required = true, dataType = "字符串"),
	})
	public String sendMail(String subject, String message, String mail) {
		try {
			mailMessageSender.sendMessage("关于XXX的邮件", "你建的流程出错了", "1179435602@qq.com;915271920@qq.com");
		} catch (Exception e) {
			e.printStackTrace();
			return "发送失败！";
		}
		return "发送成功！";
	}
	
	/**
	 * 保存邮箱配置
	 * @param mailConfig
	 * @return
	 */
	@PostMapping(value="/mailconfig")
	@ApiOperation(value="保存邮箱服务器配置",notes="保存时id不用传入")
	@ApiImplicitParam(name = "mailConfig", value = "邮箱服务器配置", required = true, dataType = "MailConfig")
	public Boolean saveMailConfig(MailConfig mailConfig) {
		Boolean saveStatus = mailConfigService.save(mailConfig);
		return saveStatus;
	}
	
	/**
	 * 获取邮箱配置
	 * @param mailConfig
	 * @return
	 */
	@GetMapping(value="/mailconfig")
	@ApiOperation(value="获取邮箱服务器配置")
	public MailConfig getMailConfig() {
		return mailConfigService.get();
	}
	
}
