package com.newnetcom.anlyze.anlyze.db.interfaces;

import java.util.List;
import java.util.Map;

import com.newnetcom.anlyze.beans.Pair;

public interface IDatabase {
	
	/** 
	* @Title: getRules 
	* @Description: 获取解析规则Map<字典+canid，解析集合>
	* @param @return    设定文件 
	* @return Map<String,Pair>    返回类型 
	* @throws 
	*/
	Map<String,Map<String,List<Pair>>> getRules();
}
