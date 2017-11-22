package com.newnetcom.anlyze.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.VehicleInfo;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.redisUtils.RedisUtils;

public class UpdateRedisTask extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(UpdateRedisTask.class);
	private RedisUtils redis;
	private static final String pre = "BIG_VEHICLE:";

	private static final String snapshot = "ANA_SNAPSHOT:";
	Map<String, Map<String, String>> contentsMap = new ConcurrentHashMap<>();
	Map<String, Map<String, String>> contentsMapVehicle = new ConcurrentHashMap<>();
	private long lastTime;
	public UpdateRedisTask() {
		redis = new RedisUtils();
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		while (true) {

			try {
				ResultBean results = publicStaticMap.getRedisQueue().take();
				List<PairResult> pairs = results.getPairs();
				Map<String, String> pairsMap = new HashMap<>();
				Map<String, String> pairsMap2 = new HashMap<>();
				for (PairResult pair : pairs) {
					pairsMap.put(pair.getAlias().isEmpty() || pair.getAlias().equals("null") ? pair.getCode()
							: pair.getAlias(), pair.getValue());
					if (pair.getAlias().equals("DATIME_RX") || pair.getAlias().equals("MILEAGE")
							|| pair.getAlias().equals("LON") || pair.getAlias().equals("LAT")
							|| pair.getAlias().equals("LON_D") || pair.getAlias().equals("LAT_D")) {
						pairsMap2.put(pair.getAlias(), pair.getValue());
					}
				}
				VehicleInfo info=publicStaticMap.getVehicles().get(results.getVehicleUnid());
				pairsMap2.put("domain_unid",info.getDomain_unid());
				pairsMap2.put("fiber_unid",info.getFIBER_UNID());
				contentsMapVehicle.put(pre + results.getVehicleUnid(), pairsMap2);
				contentsMap.put(snapshot + results.getVehicleUnid(), pairsMap);
				long current = System.currentTimeMillis();
				if (contentsMap.size() > 5000 || current - lastTime > 10000) {
					lastTime = current;
					if (contentsMap.size() > 2) {
						Map<String, Map<String, String>> cMap = new HashMap<>();
						cMap.putAll(contentsMap);
						Map<String, Map<String, String>> cMap2 = new HashMap<>();
						cMap2.putAll(contentsMapVehicle);
						contentsMap.clear();
						contentsMapVehicle.clear();
						long tempTime = System.currentTimeMillis();
						redis.setKeys(cMap);
						redis.setKeys(cMap2);
						Thread.sleep(5);
						System.out
								.println("更新redis：" + cMap.size() + "更新时间:" + (System.currentTimeMillis() - tempTime));
					}
				}
			} catch (Exception ex) {
				logger.error("更新redis出错", ex);
				try {
					Thread.sleep(5000);// 休眠5s，然后重新开启redisUtils
					redis = new RedisUtils();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
