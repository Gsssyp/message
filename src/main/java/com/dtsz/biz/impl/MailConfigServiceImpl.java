package com.dtsz.biz.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dtsz.biz.IMailConfigService;
import com.dtsz.entity.MailConfig;
import com.dtsz.repository.MailConfigRepository;


@Service
public class MailConfigServiceImpl implements IMailConfigService {
	
	@Resource
	private MailConfigRepository mailConfigRepository;
	
	/**
	 * 保存配置
	 */
	@Override
	@Transactional
	public Boolean save(MailConfig mailConfig) {
		mailConfigRepository.deleteAll();
		mailConfigRepository.save(mailConfig);
		return true;
	}

	/**
	 * 获取配置
	 */
	@Override
	public MailConfig get() {
		List<MailConfig> allConfig = mailConfigRepository.findAll();
		if(allConfig != null && allConfig.size() > 0) {
			MailConfig mailConfig = allConfig.get(0);
			mailConfig.setPassword(null);
			return mailConfig;
		}
		return new MailConfig();
	}

}
