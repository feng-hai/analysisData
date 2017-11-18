package com.newnetcom.anlyze.anlyze.db.xml.utils;

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

public class XMLReaderUtils {
	private static Map<String, List<Pair>> values = new HashMap<>();

	public static Map<String, List<Pair>>  readerToCatch() {
		String path;
		Element element = null;
		try {
			path = new File(".").getCanonicalPath() + "/src/main/resources/config.xml";
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			element = Dom4jUtil.getDocument(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		listNodes(element, "", "");
		return values;
        
		//publicStaticMap.setCans(values);
	}

	//
	public static void listNodes(Element node, String protol, String can) {
		String nodeName = node.getName();
		// System.out.println("当前节点的名称：" + node.getName());
		// 首先获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		// Map<String,String > tempValue=new HashMap<>();

		Pair pair = new Pair();
		for (Attribute attribute : list) {
			if (nodeName == "analyzeType") {
				if (attribute.getName() == "type") {
					protol = attribute.getValue();
				}
			}
			if (nodeName == "can") {
				if (attribute.getName() == "name") {
					can = attribute.getValue();
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
			List<Pair> pairs = values.get(protol + "-" + can);
			if (pairs != null) {

				pairs.add(pair);

			} else {
				pairs = new ArrayList<>();
				pairs.add(pair);
			}
			values.put(protol + "-" + can, pairs);
			return;
		}
		// 如果当前节点内容不为空，则输出
		if (!(node.getTextTrim().equals(""))) {
			System.out.println(node.getName() + "：" + node.getText());
		}
		// 同时迭代当前节点下面的所有子节点
		// 使用递归
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e, protol, can);
		}
	}

}
