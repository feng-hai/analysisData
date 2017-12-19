package com.newnetcom.anlyze.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.RowKeyBean;
import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.beans.VehicleInfo;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.index.RawIndex;
import com.newnetcom.anlyze.utils.JsonUtils;

import cn.ngsoc.hbase.HBase;
import cn.ngsoc.hbase.util.HBaseUtil;

public class RawDataMyTaskRun extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(RawDataMyTaskRun.class);
//	private int threadNum = Integer
//			.parseInt(PropertyResource.getInstance().getProperties().get("indexHistoryThreadNum"));

	//private ExecutorService executor;
	private long lastTime = 0;
	private long rawHabaseNum = 0;
	
	private String isIndex=PropertyResource.getInstance().getProperties().get("isIndex");

	public RawDataMyTaskRun() {
		HBaseUtil.init(PropertyResource.getInstance().getProperties().get("zks"));
		lastTime = System.currentTimeMillis();
		//executor = Executors.newFixedThreadPool(threadNum);
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<Put> puts = new ArrayList<>();
	//private List<VehicleIndex> vehicleIndexs = new ArrayList<>();

	private void saveRaw(ProtocolBean protocol) {
		try {
			if (rawHabaseNum < Long.MAX_VALUE) {
				rawHabaseNum++;
			}
			long time = Long.parseLong(protocol.getTIMESTAMP());
			Put put = new Put(RowKeyBean.makeRowKey(protocol.getUnid(), time));
			put.addColumn(Bytes.toBytes("CUBE"), Bytes.toBytes("DATIME_RX"), Bytes.toBytes(sdf.format(new Date(time))));
			put.addColumn(Bytes.toBytes("CUBE"), Bytes.toBytes("RAW_OCTETS"),
					Bytes.toBytes(protocol.getRAW_OCTETS().toUpperCase()));
			puts.add(put);
			if(isIndex.equals("true"))
			{
				RawIndex.setValue(new VehicleIndex(protocol.getUnid(), protocol.getTIMESTAMP()));
			}
			//vehicleIndexs.add(new VehicleIndex(protocol.getUnid(), protocol.getTIMESTAMP()));
			Long curentTime = System.currentTimeMillis();
			if (puts.size() > 5000 || curentTime - lastTime > 10000) {
				lastTime = curentTime;
				// long temp = System.currentTimeMillis();
				if (puts.size() > 0) {
					synchronized (puts) {
						List<Put> tempPuts = new ArrayList<>();
						tempPuts.addAll(puts);
						puts.clear();
						HBase.put("CUBE_RAW", tempPuts, false);
						tempPuts=null;
					}
					// 提交索引列表
				//	List<VehicleIndex> tempIndexs = new ArrayList<>();
//					tempIndexs.addAll(vehicleIndexs);
//					vehicleIndexs.clear();
				//executor.submit(new SubmitIndex("cube_raw", "vehicle", tempIndexs));
					if (publicStaticMap.logStatus) {
						System.out.println("原始数据插入数据：" + String.valueOf(rawHabaseNum));
						//logger.info("原始数据插入数据：" + String.valueOf(rawHabaseNum));
					}
					Thread.sleep(1);
					// System.out.println(tempPuts.size() + "车辆原始数据" +
					// (System.currentTimeMillis() - temp));
				}
			}
		} catch (Exception e) {
			logger.error("插入hbase数据库有问题", e);
		}

	}

	private long lock = 0;

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
			if (publicStaticMap.stopTag)// 如果关闭
			{
				try {
					Thread.sleep(5000);
					// continue;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}

//			if (lock++ % 1000 == 0) {
//				lock=0;
//				try {
//					Thread.sleep(5);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			try {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					String sentence = record.value();
					sentence = sentence.replace("DEVICE_ID", "device_ID").replace("TIMESTAMP", "timestamp")
							.replace("IP4", "ip4").replace("raw_octets", "raw_OCTETS");
					ProtocolBean temp = JsonUtils.deserialize(sentence, ProtocolBean.class);

					if (publicStaticMap.getVehicles().size() == 0) {
						Thread.sleep(5000);
						continue;
					}

					if (temp.getFIBER_UNID() == null || temp.getFIBER_UNID().isEmpty()) {
						VehicleInfo info = publicStaticMap.getVehicles().get(temp.getUnid());
						if (info != null) {
							temp.setFIBER_UNID(info.getFIBER_UNID());
						}
					}
					saveRaw(temp);
					if (publicStaticMap.getRawDataQueue().size() > 5000) {
						Thread.sleep(1000);// 一分钟后重新启动kafka
						publicStaticMap.getRawDataQueue().put(temp);
					}else
					{
						publicStaticMap.getRawDataQueue().put(temp);
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
