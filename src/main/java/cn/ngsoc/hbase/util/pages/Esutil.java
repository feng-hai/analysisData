package cn.ngsoc.hbase.util.pages;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.config.PropertyResource;

public class Esutil {
	public static TransportClient client = null;
	
	private static final Logger logger=LoggerFactory.getLogger(Esutil.class);

	private static int max=0;
	public static TransportClient getClient() {
		if (client != null) {
			return client;
		}
		Settings settings = Settings.builder().put("cluster.name", "Heracles")// 集群名称
				.put("client.transport.sniff", true)//自动嗅探整个ES集群节点
				.put("transport.type","netty3")
				.put("http.type", "netty3")
			
				.build();////每5秒提交一次数据，类似oracle中的commit

		try {
			client = new  PreBuiltTransportClient(settings);
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
			//e.printStackTrac();
			logger.error("连接索引错误",e);
		}
		return client;
	}
	/**
	 * @Title: addIndex @Description: TODO(这里用一句话描述这个方法的作用) @param @param index
	 *         表名 小写 @param @param type ”vehicle“ @param @param vehicles
	 *         设定文件 @return void 返回类型 @throws
	 */
	public static void addIndex(String index, String type, List<VehicleIndex> vehicles) {
		try {
			//logger.info("开始提交");
			BulkRequestBuilder bulkRequest = getClient().prepareBulk();
		
			for (VehicleIndex vehicle : vehicles) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("id", vehicle.getVehicleUnid());
				hashMap.put("time", vehicle.getTime());
				bulkRequest.add(getClient().prepareIndex(index, type)
						.setId(vehicle.getVehicleUnid() + "-" + vehicle.getTime()).setSource(hashMap));
				if(max++%100==0);
				{
					bulkRequest.execute().actionGet();
				}
			}
			bulkRequest.execute().actionGet();
			//logger.info("开始结束"+max);
		} catch (Exception ex) {
			logger.error("插入所有错误",ex);
			client = null;
		}
	}
}
