package com.newnetcom.anlyze.index;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.newnetcom.anlyze.beans.VehicleIndex;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.thread.SubmitIndex;

public class SensorIndex {
	private static Vector<VehicleIndex> vehiclesIndex = new Vector<>();
	private static int threadNum = Integer
			.parseInt(PropertyResource.getInstance().getProperties().get("indexHistoryThreadNum"));
	private static ExecutorService executor;
	static{
		executor = Executors.newFixedThreadPool(threadNum);
	}
	public static void setValue(VehicleIndex vi) {
		synchronized (vehiclesIndex) {
			vehiclesIndex.add(vi);
			if (vehiclesIndex.size() > 100) {
				executor.submit(new SubmitIndex("cube_sensor", "vehicle", vehiclesIndex));
			}
		}
	}
}
