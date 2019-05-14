package com.dtsz.action;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtsz.action.res.ActionResult;
import com.dtsz.biz.IMailService;
import com.dtsz.entity.Mail;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: MailController
 * @Description: TODO
 * @see:
 * @author: Gsy
 * @date: 2019年3月26日 下午8:54:24
 * @version :1.0
 */
@RestController
public class MailController {

	@Resource
	IMailService mailService;

	@GetMapping(value = "/mails")
	@ApiOperation(value = "获取邮件列表", notes = "带分页获取邮邮件列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNo", value = "当前页", required = true, dataType = "数值型", defaultValue = "0"),
			@ApiImplicitParam(name = "pageSize", value = "每页的记录数", required = true, dataType = "数值型", defaultValue = "10"), 
	})
	public Page<Mail> getMails(Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 0;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		return mailService.findAll(pageNo, pageSize);
	}

	@GetMapping(value = "/mail/{id}")
	@ApiOperation(value = "获取一封邮件", notes = "获取一封邮件")
	@ApiImplicitParam(name = "id", value = "邮件ID", required = true, dataType = "字符型")
	public ActionResult getMail(@PathVariable("id") String id) {
		ActionResult result = new ActionResult();
		// id为空直
		if (id == null || id.equals("")) {
			result.setSuccess(false);
			result.setMessage("ID为空");
			return result;
		}
		Mail mail = mailService.getMail(id);
		if(mail == null) {
			result.setSuccess(false);
			result.setMessage("根据ID查询不到邮件");
			return result;
		}
		result.setSuccess(true);
		result.setObject(mail);
		return result;
	}
	
	@PutMapping(value = "/mail")
	@ApiOperation(value = "修改一封邮件", notes = "修改一封邮件")
	@ApiImplicitParam(name = "mail", value = "邮件", required = true, dataType = "Mail")
	public ActionResult updateMail(Mail mail) {
		ActionResult result = new ActionResult();
		// id为空，直接返回
		if (mail.getId() == null || mail.getId().equals("")) {
			result.setSuccess(false);
			result.setMessage("ID为空");
			return result;
		}
		// 更新Mail
		Mail newMail = mailService.mergerMail(mail);
		if(newMail == null) {
			result.setSuccess(false);
			result.setMessage("根据ID更新邮件失败");
			return result;
		}
		result.setSuccess(true);
		result.setObject(newMail);
		return result;
	}
	
	@GetMapping(value = "/mail/send/{id}")
	@ApiOperation(value = "重新发送邮件", notes = "重新发送邮件")
	@ApiImplicitParam(name = "id", value = "邮件ID", required = true, dataType = "字符型")
	public ActionResult reSendMail(@PathVariable("id") String id) {
		ActionResult result = new ActionResult();
		// id为空直
		if (id == null || id.equals("")) {
			result.setSuccess(false);
			result.setMessage("ID为空");
			return result;
		}
		Mail mail = mailService.updateMail(id);
		if(mail == null) {
			result.setSuccess(false);
			result.setMessage("根据ID查询不到邮件");
			return result;
		}
		if(mail.getSendStatatus() != null && mail.getSendStatatus() == true)
		result.setSuccess(true);
		result.setObject(mail);
		return result;
	}
	
	
}
