package com.dtsz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="mail_config")
public class MailConfig {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
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
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
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
	public Boolean getUseSSL() {
		return useSSL;
	}
	public void setUseSSL(Boolean useSSL) {
		this.useSSL = useSSL;
	}
	@Override
	public String toString() {
		return "MailConfig [id=" + id + ", smtpHost=" + smtpHost + ", smtpPort=" + smtpPort + ", username=" + username
				+ ", password=" + password + ", useSSL=" + useSSL + "]";
	}

	
}
