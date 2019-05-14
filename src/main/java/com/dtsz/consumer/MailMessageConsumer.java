package com.dtsz.consumer;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dtsz.biz.MailMessageSender;
import com.dtsz.frame.kafka.KafkaConsumerConfig;
import com.dtsz.vo.MessageVO;

/**
 * kafka消费者测试
 */
@Component
public class MailMessageConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private MailMessageSender mailMessageSender;
	@Resource
	private KafkaConsumerConfig kafkaConsumerConfig;

	@KafkaListener(topics = "${topicname.email}")
	public void listen(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
		try {
			String messageInfo = (String) record.value();
			MessageVO messageVO = JSONObject.parseObject(messageInfo, MessageVO.class);
			mailMessageSender.sendMessage(messageVO.getSubject(), messageVO.getMessage(), messageVO.getMessageTo());
			
			acknowledgment.acknowledge();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("kafka消息消费失败", e);
		}
	}
}