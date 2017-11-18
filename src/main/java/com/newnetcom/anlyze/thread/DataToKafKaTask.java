package com.newnetcom.anlyze.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.kafka.HandlerProducer;

public class DataToKafKaTask extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(DataToKafKaTask.class);

	public DataToKafKaTask() {
		// initKafka();
	}
	// private void initKafka() {
	//
	// HashMap<String, String> config =
	// PropertyResource.getInstance().getProperties();
	// Properties props = new Properties();
	// props.put("bootstrap.servers", config.get("kafka.server"));
	// //props.put("acks", "1");
	// //props.put("retries", 0);
	// props.put("producer.type","async");
	// props.put("batch.size", 16384);
	// props.put("linger.ms", 10);
	// props.put("buffer.memory", 33554432);
	// props.put("key.serializer",
	// "org.apache.kafka.common.serialization.StringSerializer");
	// props.put("value.serializer",
	// "org.apache.kafka.common.serialization.StringSerializer");
	//
	// // 可选配置，如果不配置，则使用默认的partitioner
	// //props.put("partitioner.class", "com.wlwl.kafka.PartitionerDemo");
	// producer = new KafkaProducer<String, String>(props);
	//
	// }
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// HashMap<String, String> config =
	// PropertyResource.getInstance().getProperties();
    private int count=0;
	ExecutorService executor = Executors.newFixedThreadPool(5);
	public void run() {
		while (true) {
			try {
				if(++count>5000)
				{
					count=0;
					Thread.sleep(5);
				}
				ResultBean message = publicStaticMap.getSendDataQueue().take();
				logger.debug("插入kafka信息："+message.toString());
				executor.submit(new HandlerProducer(message));
				// ObjectModelOfKafka temPMessage=new ObjectModelOfKafka();
				// temPMessage.setDATIME_RX(sdf.format(message.getDatetime()));
				// temPMessage.setPairs(message.getPairs());
				// temPMessage.setTIMESTAMP(message.getDatetime());
				// // String strMessage = message.toString();
				// ProducerRecord<String, String> myrecord = new
				// ProducerRecord<String, String>(
				// config.get("kafka.pairs"),message.getVehicleUnid(),
				// temPMessage.toString());

				// if(config.getIsDebug()==1){
				// System.out.println("kafka sending! topic:
				// "+config.getSourcecodeTopic()+" message: "+ strMessage);
				// }

				// try {
				// Date time = new Date(Long.parseLong(message.getTIMESTAMP()));
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss");
				// logger.info(sdf.format(time) +
				// message.getDEVICE_ID()+strMessage);
				// } catch (Exception ex) {
				// logger.error("数据转化：", ex);
				// }
				// logger.error(strMessage);
				// List<String> watchs =
				// java.util.Arrays.asList(config.get("terminals").split(","));
				// if (watchs.contains(message.getDEVICE_ID())) {
				// new AychWriter(message.getRAW_OCTETS(), "Octests").start();
				// }
				// producer.send(myrecord);

				// producer.send(myrecord, new Callback() {
				//
				// public void onCompletion(RecordMetadata metadata, Exception
				// e) {
				// if (e != null) {
				// try {
				// Thread.sleep(60000);
				// } catch (InterruptedException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// initKafka();// 重新创建一个kafka对象
				// logger.error(e.toString());
				// }
				//
				// logger.debug("The offset of the record we just sent is: " +
				// metadata.offset() + ","
				// + metadata.topic());
				//
				// }
				// });

			} catch (Exception e) {
				
				logger.error("插入kafka错误", e);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			;
			}
		}
	}

}
