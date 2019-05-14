package com.dtsz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName: Mail
 * @Description: TODO
 * @see:
 * @author: Gsy
 * @date: 2019年3月27日 上午10:11:04
 * @version :1.0
 */
@Entity
@Table(name = "mail")
public class Mail {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	// 邮件接收者
	private String mailTo;
	// 邮件主题
	private String subject;
	// 邮件内容
	@Lob
	private String mailContent;
	// 发送日期
	@Column(length = 20)
	private String sendDate;
	// 发关状态
	private Boolean sendStatatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public Boolean getSendStatatus() {
		return sendStatatus;
	}

	public void setSendStatatus(Boolean sendStatatus) {
		this.sendStatatus = sendStatatus;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Mail() {
		super();
	}

	public Mail(String mailTo, String subject, String mailContent, String sendDate, Boolean sendStatatus) {
		super();
		this.mailTo = mailTo;
		this.subject = subject;
		this.mailContent = mailContent;
		this.sendDate = sendDate;
		this.sendStatatus = sendStatatus;
	}

}
