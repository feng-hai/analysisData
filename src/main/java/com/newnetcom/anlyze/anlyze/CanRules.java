package com.newnetcom.anlyze.anlyze;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import java.util.Map;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.utils.ByteUtils;

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
	public static List<Pair> getRuleBeanByCanId(String fibeid,Boolean isTrue,Map<String,List<Pair>> rul,byte[] canId) {
		//System.out.println(ByteUtils.byte2HexStr(canId));
	List<Pair> rules=new ArrayList<>() ;
		if (isTrue) {
			int tempInt=ByteUtils.getIntForLarge(canId, 0)&0xffffffff;
			//String temp =ByteUtils.byte2HexStr(canId);		
			rules= rul.get(tempInt);
			//System.out.println(dataSet+"-"+tempInt);
			//System.out.println(JsonUtils.serialize( publicStaticMap.getA2LValues()));
		}else
		{
			rules= rul.get(ByteUtils.byte2HexStr(canId));
		}
		if (rules == null) {
			logger.debug("错误：数据字典ID："+fibeid+"CanID:"+ByteUtils.byte2HexStr(canId));
			rules = new ArrayList<>();
		} 	
		return rules;
	}

}
