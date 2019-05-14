package com.dtsz.biz;

import org.springframework.data.domain.Page;

import com.dtsz.entity.Mail;

/** 
 * @ClassName: IMailConfigService 
 * @Description: TODO
 * @see: 
 * @author: Gsy
 * @date: 2019年3月26日 下午10:27:55 
 * @version :1.0 
 */
public interface IMailService {
	public Page<Mail> findAll(Integer pageNo, Integer pageSize);
	public Mail getMail(String id);
	public Mail updateMail(String id);
	public Mail mergerMail(Mail mail);
}
