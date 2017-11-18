package com.newnetcom.anlyze.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.RowKeyBean;
import com.newnetcom.anlyze.beans.publicStaticMap;
//import com.newnetcom.anlyze.utils.JsonUtils;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.utils.JsonUtils;

import cn.ngsoc.hbase.HBase;
import cn.ngsoc.hbase.util.HBaseUtil;

public class UpdateHbaseMyTask extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHbaseMyTask.class);
	private Long lastTime;

	public UpdateHbaseMyTask() {
		// loadData();PropertyResource.getInstance().getProperties().get("zks")
		HBaseUtil.init(PropertyResource.getInstance().getProperties().get("zks"));
		lastTime = System.currentTimeMillis();
	}

	List<Put> puts = new ArrayList<>();

	@Override
	public void run() {
		while (true) {
			try {
				// logger.info(String.valueOf(i++));
				ResultBean results = publicStaticMap.getCmdQueue().take();
				logger.debug("更新hbase个数：" + publicStaticMap.getCmdQueue().size());
				try {
					List<PairResult> pairs = results.getPairs();
					if (results.getDatetime() == null) {
						logger.debug(JsonUtils.serialize(results));
						continue;
					}
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
					Long curentTime = System.currentTimeMillis();
					if (puts.size() > 5000 || curentTime - lastTime > 10000) {
						lastTime = curentTime;
						List<Put> tempPuts = new ArrayList<>();
						tempPuts.addAll(puts);
						HBase.batchAsyncPut("CUBE_SENSOR", tempPuts, false);
						puts.clear();
						Thread.sleep(5);
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
