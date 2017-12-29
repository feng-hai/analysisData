package com.newnetcom.anlyze.index;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.thread.SubmitIndex;

public class SensorIndex {
	private static CopyOnWriteArrayList<VehicleIndex> vehiclesIndex = new CopyOnWriteArrayList<>();
	
	private static int threadNum = Integer
			.parseInt(PropertyResource.getInstance().getProperties().get("indexHistoryThreadNum"));
	private static ExecutorService executor;
	static{
		executor = Executors.newFixedThreadPool(threadNum);
	}
	public synchronized static void setValue(VehicleIndex vi,String tableName) {
		
			vehiclesIndex.add(vi);
			if (vehiclesIndex.size() > 100) {
				new SubmitIndex(tableName, "vehicle", vehiclesIndex).run();;
				vehiclesIndex.clear();
			}
		
	}
}
