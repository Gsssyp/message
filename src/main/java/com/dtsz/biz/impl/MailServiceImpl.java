package com.dtsz.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dtsz.repository.MailRepository;
import com.dtsz.biz.IMailService;
import com.dtsz.biz.MailMessageSender;
import com.dtsz.entity.Mail;

/** 
 * @ClassName: MailConfigServiceImpl 
 * @Description: TODO
 * @see: 
 * @author: Gsy
 * @date: 2019年3月26日 下午10:29:16 
 * @version :1.0 
 */
@Service
@Transactional
public class MailServiceImpl implements IMailService {
	
	@Resource
	private MailRepository mailRepository;

	@Resource
	private MailMessageSender mailMessageSender;

	@Override
	public Page<Mail> findAll(Integer pageNo, Integer pageSize) {
		// 按发送时间倒序排序
		Sort sort = new Sort(Sort.Direction.DESC, "sendDate");
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Mail> allMail = mailRepository.findAll(pageable);
		return allMail;
	}
	
	@Override
	public Mail getMail(String id) {
		Optional<Mail> findById = mailRepository.findById(id);
		// 如果根据ID查询不到，刚返加空（默认查询不到返回一个代理对象）
		Mail mail = findById.orElse(null);
		return mail;
	}

	@Override
	public Mail updateMail(String id) {
		Optional<Mail> findById = mailRepository.findById(id);
		// 如果根据ID查询不到，刚返加空（默认查询不到返回一个代理对象）
		Mail mail = findById.orElse(null);
		Mail sendMail = null;
		if(mail != null) {
			try {
				sendMail = mailMessageSender.sendMessage(mail.getSubject(), mail.getMailContent(), mail.getMailTo());
				mailRepository.save(sendMail);
				mailRepository.delete(mail);
				return sendMail;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mail;
	}

	@Override
	public Mail mergerMail(Mail mail) {
		Optional<Mail> findByIdMail = mailRepository.findById(mail.getId());
		Mail optMail = findByIdMail.orElse(null);
		if(optMail == null) {
			return null;
		}
		// 按需更新
		if(mail.getMailTo()!=null)
		optMail.setMailTo(mail.getMailTo());
		if(mail.getMailContent()!=null)
		optMail.setMailContent(mail.getMailContent());
		if(mail.getSendStatatus()!=null)
		optMail.setSendStatatus(mail.getSendStatatus());
		if(mail.getSubject()!=null)
		optMail.setSubject(mail.getSubject());
		optMail.setSendDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 更新
		mailRepository.flush();
		return optMail;
	}
	
}
