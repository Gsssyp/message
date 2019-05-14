package com.dtsz.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dtsz.repository.MailRepository;
import com.dtsz.entity.Mail;
import com.dtsz.entity.MailConfig;
import com.dtsz.repository.MailConfigRepository;
import com.dtsz.utils.BaseAppException;

@Service
public class MailMessageSender {
	
	@Resource
	private MailConfigRepository mailConfigRepository;
	@Resource
	private MailRepository mailRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	// SMTP 主机地址
	private String smtpHost;
	// SMTP 主机端口号 默认25
	private String smtpPort = "25";
	// 邮箱用户名
	private String username;
	// 邮箱授权码 ,不是邮箱密码
	private String password;
	// 使用SSL协议
	private Boolean useSSL = false;

	public Mail sendMessage(String subject, String messageContext, String mailTo) throws Exception {
		// 记录发送详情
		Mail mail = null;
		// 获取用户配置
		boolean success = setMailInfoConf();
		if (!success) {
			logger.error("can not get the mail conf");
			throw new BaseAppException("can not get the mail conf");
		}
		// 给邮件客户端设置参数
		Properties prop = new Properties();
		prop.setProperty("mail.host", smtpHost);
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.port", smtpPort);
		prop.setProperty("mail.smtp.auth", "true");
		// 开启了SSL协议,才走SSL
		if (null != useSSL && true == useSSL) {
			prop.setProperty("mail.smtp.socketFactory.port", smtpPort);
			prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		}
		Transport ts = null;
		try {
			// 1、创建session会话
			Session session = Session.getInstance(prop);
			// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(true);
			// 2、通过session得到transport对象
			ts = session.getTransport();
			// 3、验证邮箱信息
			ts.connect(smtpHost, username, password);
			// 4、创建邮件并记录
			Message message = createMail(session, subject, messageContext, username, mailTo);
			mail = new Mail(mailTo, subject, messageContext, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) , false);
			// 5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			// 邮件发送成功
			mail.setSendStatatus(true);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BaseAppException("发送给[" + mailTo + "]的邮件失败", e);
		}finally {
			// 关闭连接
			ts.close();
			// 当邮件发送后，将发送详情进行记录
			mailRepository.save(mail);
		}
		return mail;
	}

	// 配置消息发送参数
	private boolean setMailInfoConf() {
		// 从数据库获取配置
		MailConfig mailConfig = null;
		List<MailConfig> allConfig = mailConfigRepository.findAll();
		if(allConfig != null && allConfig.size() > 0) {
			mailConfig = allConfig.get(0);
		}else {
			return false;
		}
		String smtpHost = mailConfig.getSmtpHost();
		String smtpPort = mailConfig.getSmtpPort();
		String username = mailConfig.getUsername();
		String password = mailConfig.getPassword();
		Boolean useSSL = mailConfig.getUseSSL();

		setSmtpHost(smtpHost);
		setSmtpPort(smtpPort);
		setUsername(username);
		setPassword(password);
		setUseSSL(useSSL);
		return true;
	}


	/**
	 * 创建邮件
	 * 
	 * @param session
	 * @param subject
	 * @param messageContent
	 * @param mailFrom
	 * @param mailTo
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMail(Session session, String subject, String messageContent, String mailFrom,
			String mailTo) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(mailFrom));
		// 指明邮件的收件人，可能有多个收件人
		String[] mailToArr = mailTo.split(";");
		Address addressArr[] = new Address[mailToArr.length];
		for (int i = 0; i < mailToArr.length; i++) {
			Address address = new InternetAddress(mailToArr[i].trim());
			addressArr[i] = address;
		}
		message.setRecipients(Message.RecipientType.TO, addressArr);
		// 邮件的标题
		message.setSubject(subject);
		// 邮件的文本内容
		message.setContent(messageContent, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Boolean getUseSSL() {
		return useSSL;
	}

	public void setUseSSL(Boolean useSSL) {
		this.useSSL = useSSL;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

}
