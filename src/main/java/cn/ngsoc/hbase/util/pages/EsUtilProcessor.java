package cn.ngsoc.hbase.util.pages;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.config.PropertyResource;

public class EsUtilProcessor {

	private EsUtilProcessor() {

	}

	public static TransportClient client = null;

	private static final Logger logger = LoggerFactory.getLogger(Esutil.class);

	private static BulkProcessor bulkProcessor = null;

	public static TransportClient getClient() {
		if (client != null) {
			return client;
		}
		Settings settings = Settings.builder().put("cluster.name", "Heracles")// 集群名称
				.put("client.transport.sniff", true)// 自动嗅探整个ES集群节点
				.put("transport.type", "netty3").put("http.type", "netty3").put("index.refresh_interval", "5s").build();//// 每5秒提交一次数据，类似oracle中的commit

		try {
			client = new PreBuiltTransportClient(settings);
			String servers = PropertyResource.getInstance().getProperties().get("index.server");
			String[] serverIndex = servers.split(",");
			for (String index : serverIndex) {
				client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(index), 9300));
			}
			// client = new PreBuiltTransportClient( settings
			// ).addTransportAddress( new
			// InetSocketTransportAddress(
			// InetAddress.getByName( "192.168.1.21" ),9300 )
			// ).addTransportAddress( new
			// InetSocketTransportAddress(
			// InetAddress.getByName( "192.168.1.22" ),9300 )
			// ).addTransportAddress( new
			// InetSocketTransportAddress(
			// InetAddress.getByName( "192.168.1.23" ),9300 )
			// ).addTransportAddress( new
			// InetSocketTransportAddress(
			// InetAddress.getByName( "192.168.1.24" ), 9300 ) );
		} catch (Exception e) {
			// e.printStackTrac();
			logger.error("连接索引错误", e);
		}
		return client;
	}

	public static BulkProcessor getProcessor() {
		if (bulkProcessor != null) {
			return bulkProcessor;
		} else {
			bulkProcessor = BulkProcessor.builder(getClient(), new BulkProcessor.Listener() {
				@Override
				public void beforeBulk(long executionId, BulkRequest request) {
				}

				@Override
				public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				}

				@Override
				public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				}
			}).setBulkActions(5000).setBulkSize(new ByteSizeValue(100, ByteSizeUnit.MB))
					.setFlushInterval(TimeValue.timeValueSeconds(5)).build();
			return bulkProcessor;
		}
	}
	public static void addIndex(String index, String type, List<VehicleIndex> vehicles) {
		try {
			logger.info("开始提交");
			BulkProcessor bulkProcessor=getProcessor();
		   // int max=0;
			for (VehicleIndex vehicle : vehicles) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("id", vehicle.getVehicleUnid());
				hashMap.put("time", vehicle.getTime());
				bulkProcessor.add(new IndexRequest(index, type, vehicle.getVehicleUnid() + "-" + vehicle.getTime()).source(hashMap));  
//				bulkProcessor.add(getClient().prepareIndex(index, type)
//						.setId(vehicle.getVehicleUnid() + "-" + vehicle.getTime()).setSource(hashMap));
//				if(max++%100==0);
//				{
//					max=0;
//					bulkRequest.execute().actionGet();
//				}
			}
			//bulkRequest.execute().actionGet();
			logger.info("开始结束");
		} catch (Exception ex) {
			logger.error("插入所有错误",ex);
			client = null;
		}
	}

	

}
