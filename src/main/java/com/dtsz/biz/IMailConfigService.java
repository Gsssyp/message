package com.dtsz.biz;

import com.dtsz.entity.MailConfig;

/** 
 * @ClassName: IMailConfigService 
 * @Description: TODO
 * @see: 
 * @author: Gsy
 * @date: 2019年3月26日 下午10:27:55 
 * @version :1.0 
 */
public interface IMailConfigService {
	public Boolean save(MailConfig mailConfig);
	public MailConfig get();
}
