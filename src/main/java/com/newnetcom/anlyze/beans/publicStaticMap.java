package com.newnetcom.anlyze.beans;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class publicStaticMap {
	
	private  publicStaticMap()
	{
		
	}
	
	public  static Boolean stopTag=false;//从kafka中获取数据的开关标识
	public static  Boolean reloadData=false;//是否重新加载数据
	
	private static Map<String,VehicleInfo >vehicles=new ConcurrentHashMap <>();
	
	public static Map<String, VehicleInfo> getVehicles() {
		return vehicles;
	}
	public static void setVehicles(Map<String, VehicleInfo> vehicles) {
		publicStaticMap.vehicles = vehicles;
	}

	/** 
	* @Fields rawDataQueue :原始数据存放地址
	*/ 
	private static BlockingQueue<ProtocolBean>  rawDataQueue = new LinkedBlockingQueue<>(); 
	public static BlockingQueue<ProtocolBean> getRawDataQueue() {
		return rawDataQueue;
	}
	public static void setRawDataQueue(BlockingQueue<ProtocolBean> rawDataQueue) {
		publicStaticMap.rawDataQueue = rawDataQueue;
	}
	
	/** 
	* @Fields sendDataQueue : 待发送kafka的数据
	* 	
	* */ 
	private static BlockingQueue<ResultBean>  sendDataQueue = new LinkedBlockingQueue<>(); 
	
	public static BlockingQueue<ResultBean> getSendDataQueue() {
		return sendDataQueue;
	}
	public static void setSendDataQueue(BlockingQueue<ResultBean> sendDataQueue) {
		publicStaticMap.sendDataQueue = sendDataQueue;
	}

	private static BlockingQueue<ResultBean>  cmdQueue = new LinkedBlockingQueue<ResultBean>();
	/** 
	* @Fields redisQueue : 缓存要更新redis的数据
	*/ 
	private static BlockingQueue<ResultBean>  redisQueue = new LinkedBlockingQueue<ResultBean>();
	public static BlockingQueue<ResultBean> getRedisQueue() {
		return redisQueue;
	}
	public static void setRedisQueue(BlockingQueue<ResultBean> redisQueue) {
		publicStaticMap.redisQueue = redisQueue;
	}
	public static BlockingQueue<ResultBean> getCmdQueue() {
		return cmdQueue;
	}
	private static Map<String,Map<String,List<Pair>>> values=new ConcurrentHashMap <>();
	
	
	public  static  Map<String,Map<String,List<Pair>>> getCans()
	{
		return values;
	}
	public static void setCans(Map<String,Map<String,List<Pair>>> canIn)
	{
		values=canIn;
	}
	
	private static Map<String,Map<String, List<Pair>>>  a2LValues=new ConcurrentHashMap <>();
	
	public static Map<String,Map<String, List<Pair>>>  getA2LValues() {
		return a2LValues;
	}
	public static void setA2LValues(Map<String,Map<String, List<Pair>>> a2lValues) {
		a2LValues = a2lValues;
	}

	/** 
	* @Fields alarmPair :808报警解析规则 
	*/ 
	private static Vector<Pair> alarmPair=new Vector<>();

	public static List<Pair> getAlarmPair() {
		return alarmPair;
	}
	public static void setAlarmPair(Vector<Pair> alarmPair) {
		publicStaticMap.alarmPair = alarmPair;
	}
	
	private static  Vector<String> fibers=new Vector<>();
	
	public static Vector<String> getFibers() {
		return fibers;
	}
	public static void setFibers(Vector<String> fibers) {
		publicStaticMap.fibers = fibers;
	}

	/** 
	* @Fields statusPair : 808状态解析规则
	*/ 
	private static Vector<Pair> statusPair=new Vector<>();

	public static Vector<Pair> getStatusPair() {
		return statusPair;
	}
	public static void setStatusPair(Vector<Pair> statusPair) {
		publicStaticMap.statusPair = statusPair;
	}
	
	
	
	
	

}
