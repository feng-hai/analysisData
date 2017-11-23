package cn.ngsoc.hbase.util.pages;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import com.newnetcom.anlyze.beans.VehicleIndex;

public class Esutil {
	public static TransportClient client = null;


	  @SuppressWarnings("unchecked")
	  public static TransportClient getClient()
	  {
	    if (client != null)
	    {
	      return client;
	    }

	    Settings settings = Settings.builder().put( "cluster.name", "Heracles" ).put( "xpack.security.user", "elastic:changeme" ).put( "client.transport.sniff", true ).build();
	    
	    try
	    {
	       client = new PreBuiltXPackTransportClient( settings ).addTransportAddress( new
	       InetSocketTransportAddress(
	       InetAddress.getByName( "192.168.1.21" ),9300 ) ).addTransportAddress( new 
	       InetSocketTransportAddress(
	       InetAddress.getByName( "192.168.1.22" ),9300 ) ).addTransportAddress( new 
	       InetSocketTransportAddress(
	       InetAddress.getByName( "192.168.1.23" ),9300 ) ).addTransportAddress( new 
	       InetSocketTransportAddress(
	       InetAddress.getByName( "192.168.1.24" ), 9300 ) );
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return client;
	  }
	  
	  /** 
	* @Title: addIndex 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param index  表名  小写
	* @param @param type   ”vehicle“ 
	* @param @param vehicles    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public static void addIndex(String index, String type, List<VehicleIndex> vehicles)
	  {
	    BulkRequestBuilder bulkRequest = getClient().prepareBulk();
	    for ( VehicleIndex vehicle : vehicles )
	    {
	      HashMap<String, Object> hashMap = new HashMap<String, Object>();
	      hashMap.put( "id", vehicle.getVehicleUnid() );
	      hashMap.put( "time", vehicle.getTime() );
	      bulkRequest.add( getClient().prepareIndex( index, type ).setId( vehicle.getVehicleUnid() + "-"
	          + vehicle.getTime() ).setSource( hashMap ) );
	    }
	    
	    bulkRequest.execute().actionGet();
	  }
}
