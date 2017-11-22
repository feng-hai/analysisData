package com.newnetcom.anlyze.anlyze;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
//import java.util.Map;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.JsonUtils;

public class CanRules {

	//private static Map<String, List<Pair>> ruleListMap = new HashMap<>();
     private static final Logger logger=LoggerFactory.getLogger(CanRules.class);
	static {

	}

	/** 
	* @Title: getRuleBeanByCanId 
	* @Description: 更加数据字典和canid查询解析规则
	* @param @param dataSet 数据字典唯一标识
	* @param @param canId  
	* @param @return    设定文件 
	* @return List<Pair>    返回类型 
	* @throws 
	*/
	public static List<Pair> getRuleBeanByCanId(String dataSet,byte[] canId) {
		//System.out.println(ByteUtils.byte2HexStr(canId));
		List<Pair> rules ;
		if (publicStaticMap.getFibers().contains(dataSet)) {
			int tempInt=ByteUtils.getIntForLarge(canId, 0)&0xffffffff;
			//String temp =ByteUtils.byte2HexStr(canId);		
			rules= publicStaticMap.getA2LValues().get(dataSet+"-"+tempInt);
			//System.out.println(dataSet+"-"+tempInt);
			//System.out.println(JsonUtils.serialize( publicStaticMap.getA2LValues()));
		}else
		{
			rules= publicStaticMap.getCans().get(dataSet+"-"+ByteUtils.byte2HexStr(canId));
		}
		if (rules == null) {
			logger.debug("错误：数据字典ID："+dataSet+"CanID:"+ByteUtils.byte2HexStr(canId));
			rules = new ArrayList<>();
		} 	
		return rules;
	}

}
