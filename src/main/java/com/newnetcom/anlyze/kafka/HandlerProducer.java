package com.newnetcom.anlyze.kafka;

import java.util.HashMap;

import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.config.PropertyResource;

public class HandlerProducer implements Runnable {

	private ResultBean message;

	public HandlerProducer(ResultBean message) {
		this.message = message;
	}

	@Override
	public void run() {
		KafkaProducerSingleton kafkaProducerSingleton = KafkaProducerSingleton.getInstance();
		HashMap<String, String> config = PropertyResource.getInstance().getProperties();
		kafkaProducerSingleton.init(config.get("kafka.pairs"), 3);
		//System.out.println("当前线程:" + Thread.currentThread().getName() + ",获取的kafka实例:" + kafkaProducerSingleton);
		kafkaProducerSingleton.sendKafkaMessage(message);
	}

}
