package com.newnetcom.anlyze.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.RowKeyBean;
import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.beans.publicStaticMap;
//import com.newnetcom.anlyze.utils.JsonUtils;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.index.SensorIndex;
import com.newnetcom.anlyze.utils.JsonUtils;
import cn.ngsoc.hbase.HBase;
import cn.ngsoc.hbase.util.HBaseUtil;
import cn.ngsoc.hbase.util.pages.Esutil;


public class UpdateHbaseMyTask extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHbaseMyTask.class);
	private SimpleDateFormat tableFormat = new SimpleDateFormat("yyyy");
	private Long lastTime;
	
	 //private int  threadNum=Integer.parseInt( PropertyResource.getInstance().getProperties().get("indexHistoryThreadNum"));

   //  private ExecutorService executor = Executors.newFixedThreadPool(threadNum);

	public UpdateHbaseMyTask() {
		// loadData();PropertyResource.getInstance().getProperties().get("zks")
		HBaseUtil.init(PropertyResource.getInstance().getProperties().get("zks"));
		lastTime = System.currentTimeMillis();
	}
	private String isIndex=PropertyResource.getInstance().getProperties().get("isIndex");
	List<Put> puts = new ArrayList<>();
    private long hbaseNum=0;
	//private List<VehicleIndex> vehicleIndexs=new ArrayList<>();

	@Override
	public void run() {
		while (true) {
			try {
				// logger.info(String.valueOf(i++));
				ResultBean results = publicStaticMap.getCmdQueue().take();
				logger.debug("更新hbase个数：" + publicStaticMap.getCmdQueue().size());
				if(hbaseNum<Long.MAX_VALUE)
				{
					hbaseNum++;
				}
				try {
					List<PairResult> pairs = results.getPairs();
				Date tableDate=	results.getDatetime();
				if(tableDate==null)
				{
					tableDate=new Date();
				}
					if (results.getDatetime() == null) {
						logger.debug(JsonUtils.serialize(results));
						continue;
					}
					String tableName="CUBE_SENSOR_"+tableFormat.format(tableDate);
					if(isIndex.equals("true"))
					{
					  SensorIndex.setValue(new VehicleIndex(results.getVehicleUnid(), results.getDatetime().getTime()),tableName.toLowerCase());
				
					}//vehicleIndexs.add(new VehicleIndex(results.getVehicleUnid(), String.valueOf(results.getDatetime().getTime())));
					Put put = new Put(RowKeyBean.makeRowKey(results.getVehicleUnid(), results.getDatetime().getTime()));
					for (PairResult pair : pairs) {
						put.addColumn(Bytes.toBytes("CUBE"),
								Bytes.toBytes(pair.getAlias().isEmpty() || pair.getAlias().equals("null")
										? pair.getCode() : pair.getAlias()),
								Bytes.toBytes(pair.getValue()));
					}
					if (pairs.size() > 0) {
						puts.add(put);
					} else {
						// logger.error("没有数据项："+JsonUtils.serialize(results));
					}
					pairs=null;
					Long curentTime = System.currentTimeMillis();
					if (puts.size() > 5000 || curentTime - lastTime > 5000) {
						lastTime = curentTime;
						if(publicStaticMap.logStatus)
						{
							System.out.println("更新hbase数量："+String.valueOf(hbaseNum));
							//logger.info("更新hbase数量："+String.valueOf(hbaseNum));
						}
						synchronized (puts) {
							List<Put> tempPuts = new ArrayList<>();
							tempPuts.addAll(puts);
							puts.clear();
							
							HBase.batchAsyncPut(tableName, tempPuts, false);
							tempPuts=null;
						}
						//提交索引列表
//						List<VehicleIndex> tempIndexs=new ArrayList<>();
//						tempIndexs.addAll(vehicleIndexs);	
//						executor.submit(new SubmitIndex("cube_sensor","vehicle",tempIndexs));
//						
						
						Thread.sleep(1);
					}
				} catch (Exception ex) {
					logger.error("更新hbase错误：", ex);
				}
			} catch (Exception e) {
				logger.error("插入hbase数据库有问题", e);
			}
		}
	}
}
