package com.newnetcom.anlyze.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.utils.Dom4jUtil;

public class Read808Config {
	
	static
	{
		alarmStatusMap = new HashMap<>();
		String path;
		Element element = null;
		try {
			path = new File(".").getCanonicalPath() + "/src/main/resources/alarmStatus.xml";
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			element = Dom4jUtil.getDocument(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		readAlarmStatus(element,"");
	}
	private static Map<String, List<Pair>> alarmStatusMap ;
	private static  void readAlarmStatus(Element node,String name)
	{
		String nodeName = node.getName();
		// System.out.println("当前节点的名称：" + node.getName());
		// 首先获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		Pair pair = new Pair();
		for (Attribute attribute : list) {
			if (nodeName == "status") {
				if (attribute.getName() == "name") {
					name = attribute.getValue();
				}
			}
			if (nodeName == "value") {
				if (attribute.getName().equals("alias")) {
					pair.setAlias(attribute.getValue());
				} else if (attribute.getName().equals("id")) {
					pair.setCode(attribute.getValue());
				} else if (attribute.getName().equals("title")) {
					pair.setTitle(attribute.getValue());
				} else if (attribute.getName().equals("start")) {
					pair.setStart(Integer.parseInt(attribute.getValue()));
				} else if (attribute.getName().equals("length")) {
					pair.setLength(Integer.parseInt(attribute.getValue()));
				} else if (attribute.getName().equals("offset")) {
					pair.setOffset(Integer.parseInt(attribute.getValue()));
				} else if (attribute.getName().equals("resolving")) {
					pair.setResolving(attribute.getValue());
				} else if (attribute.getName().equals("dataType")) {
					pair.setDataType(attribute.getValue());
				}

			}
		}
		if (nodeName == "value") {
			List<Pair> pairs = alarmStatusMap.get(name);
			if (pairs != null) {
				pairs.add(pair);
			} else {
				pairs = new ArrayList<>();
				pairs.add(pair);
			}
			alarmStatusMap.put(name, pairs);
			return;
		}
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			readAlarmStatus(e,name);
		}
	}
	
	
	
	public static  List<Pair> getAlarmStatus()
	{
		return alarmStatusMap.get("alarm");
	}
	
	public static List<Pair> getVehicleStatus()
	{
		return alarmStatusMap.get("status");
	}
	public static List<Pair> getExtraStatus()
	{
		return alarmStatusMap.get("extraStatus");
	}

}
