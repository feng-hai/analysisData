package com.newnetcom.anlyze.thread;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.VehicleInfo;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.utils.JsonUtils;
import cn.ngsoc.hbase.util.HBaseUtil;

public class RawDataMyTaskRun extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(RawDataMyTaskRun.class);



	public RawDataMyTaskRun() {
		HBaseUtil.init(PropertyResource.getInstance().getProperties().get("zks"));
		//lastTime = System.currentTimeMillis();
	}

	@Override
	public void run() {

		Properties props = new Properties();
		props.put("bootstrap.servers", PropertyResource.getInstance().getProperties().get("kafka.server"));
		props.put("group.id", PropertyResource.getInstance().getProperties().get("kafka.groupID"));
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		// props.put("auto.offset.reset", "earliest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		@SuppressWarnings("resource")
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(PropertyResource.getInstance().getProperties().get("kafka.sourcecodeTopic")));
		while (true) {
			if(publicStaticMap.stopTag)//如果关闭
			{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			try {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					String sentence = record.value();
					sentence = sentence.replace("DEVICE_ID", "device_ID").replace("TIMESTAMP", "timestamp")
							.replace("IP4", "ip4").replace("raw_octets", "raw_OCTETS");
					ProtocolBean temp = JsonUtils.deserialize(sentence, ProtocolBean.class);
					
					if(publicStaticMap.getVehicles().size()==0)
					{
						Thread.sleep(1000);
					}
					
					if(temp.getFIBER_UNID()==null||temp.getFIBER_UNID().isEmpty())
					{
						VehicleInfo info=publicStaticMap.getVehicles().get(temp.getUnid());
						if(info!=null)
						{
							temp.setFIBER_UNID(info.getFIBER_UNID());
						}
					}
					publicStaticMap.getRawDataQueue().put(temp);
					
					if (publicStaticMap.getRawDataQueue().size() > 100000) {
						Thread.sleep(100);// 一分钟后重新启动kafka
					}
				}
			} catch (Exception ex) {
				logger.error("kafka错误", ex);
				try {
					Thread.sleep(60000);// 一分钟后重新启动kafka
					consumer = new KafkaConsumer<String, String>(props);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	
}
