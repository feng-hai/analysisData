package com.newnetcom.anlyze.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.newnetcom.anlyze.beans.CanFrame;



public class Dom4jUtil {

	public static Element getDocument(InputStream stream) {
		Element element = null;
		SAXReader reader = new SAXReader();
		InputStreamReader strInStream;
		try {
			strInStream = new InputStreamReader(stream);
			element = reader.read(strInStream).getRootElement();
		} catch (DocumentException e) {
			//LogUtil.getLogger(Dom4jUtil.class).error("XML不合法! :", e);
		}

		return element;

	}


	public static Map<String, List<CanFrame>> getMapByDocument(Element element, String elementName) {
		List<Element> analyzeTypes = element.elements(elementName);
		Map<String, List<CanFrame>> maps = new HashMap<String, List<CanFrame>>();
		for (Element el : analyzeTypes) {

			String type = el.attributeValue("type");

			List<Element> cans = el.elements("can");
			for (Element can : cans) {
				String name = can.attributeValue("name");
				List<CanFrame> list = new ArrayList<CanFrame>();
				List<Element> values = can.elements("value");
				for (Element value : values) {
					CanFrame canFrame = new CanFrame();
					canFrame.setName(name);
					Map<String, Object> map = new HashMap<String, Object>();

					String id = value.attributeValue("id");
					String title = value.attributeValue("title");
					String alias = value.attributeValue("alias");
					canFrame.setTitle(title);
					canFrame.setId(id);
					canFrame.setAlias(alias);
					canFrame.setType(Integer.valueOf(type));
					canFrame.setCanContentMap(map);
					list.add(canFrame);
				}
				maps.put(name, list);

			}
		}
		// System.out.println( maps );

		return maps;
		// return maps;
	}


	public static Map<String, Object> getCanInfo(Element element, Integer analyzeType, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		// Element database = (Element)
		// element.selectSingleNode("//analyzeType[@type='"+type+"']") ;

	
		List<Node> databases=element.selectNodes("//analyzeType[@type='" + analyzeType + "']//value[@alias='" + name + "']");
		if(null==databases||databases.size()==0){
			databases=element.selectNodes("//energy//value[@alias='" + name + "']");
		}
		if(null==databases||databases.size()==0){
			return null;
		}
		Element database =(Element) databases.get(0);
		String title = database.attributeValue("title");
		String id = database.attributeValue("id");
		String canName = database.getParent().attributeValue("name");
		map.put("title", title);
		map.put("id", id);
		map.put("canId", canName);
		map.put("analyzeType", analyzeType);
		map.put("alias", name);

		// Element el = (Element) database
		// .selectNodes("//analyzeType[@type='" + type + "']//value[@alias='" +
		// name + "']");
		//

		return map;
	}


	public static Map<String, Map<String, Object>> getMapsByDocument(Element element, String elementName) {
		List<Element> analyzeTypes = element.elements(elementName);
		Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
		for (Element el : analyzeTypes) {

			String type = el.attributeValue("type");

			List<Element> cans = el.elements("can");
			for (Element can : cans) {
				String name = can.attributeValue("name");
				List<Element> values = can.elements("value");
				for (Element value : values) {
					Map<String, Object> map = new HashMap<String, Object>();

					String id = value.attributeValue("id");
					String title = value.attributeValue("title");
					String alias = value.attributeValue("alias");
					map.put("id", id);
					map.put("title", title);
					map.put("alias", alias);
					map.put("type", type);
					map.put("name", name);
					maps.put(id, map);
				}

			}
		}
		// System.out.println( maps );

		return maps;
		// return maps;
	}

	// public static Map<String, String> getMapByDocument(Document document)
	// {
	// Map<String, String> map = new HashMap<String, String>( 200 );
	// try
	// {
	// List<Element> level = document.getRootElement().element( "Authentication"
	// ).elements();
	// List<Element> level3 = document.getRootElement().element(
	// "TrainOrderService" ).elements();
	// List<Element> level2 = document.getRootElement().element(
	// "TrainOrderService" ).element( "OrderInfo"
	// ).elements();
	// Element element = document.getRootElement().element( "TrainOrderService"
	// ).element( "DeliveryInfo" );
	// for ( Element e : level )
	// {
	// map.put( e.getName(), e.getTextTrim() );
	// }
	// for ( Element e : level2 )
	// {
	// map.put( e.getName(), e.getTextTrim() );
	// }
	// if (null != element)
	// {
	// List<Element> list = element.elements();
	// for ( Element e : list )
	// {
	// map.put( e.getName(), e.getTextTrim() );
	// }
	// }
	// for ( Element e : level3 )
	// {
	// map.put( e.getName(), e.getTextTrim() );
	// }
	// }
	// catch (Exception e)
	// {
	// LogUtil.getLogger( Dom4jUtil.class ).error(
	// "Dom4jUtil-->getMapByDocument()订单推送过来的XML节点问题,请查看原始的XML信息!", e );
	// }
	// return map;
	// }

	public static void main(String[] args) {

		// System.out.println(Dom4jUtil.getMapsByDocument(
		// Dom4jUtil.getDocument(Dom4jUtil.class.getClassLoader().getResourceAsStream("config.xml")),
		// "analyzeType"));
		// ;
		// System.out.println(Dom4jUtil.getMapsByDocument(
		// Dom4jUtil.getDocument(Dom4jUtil.class.getClassLoader().getResourceAsStream("config.xml")),
		// "energy"));
		// System.out.println(Dom4jUtil.getMapByDocument(
		// Dom4jUtil.getDocument(Dom4jUtil.class.getClassLoader().getResourceAsStream("config.xml")),
		// "energy"));
		// ;

		System.out.println(Dom4jUtil.getCanInfo(
				Dom4jUtil.getDocument(Dom4jUtil.class.getClassLoader().getResourceAsStream("config.xml")), 1, "SOC"));

	}
}
