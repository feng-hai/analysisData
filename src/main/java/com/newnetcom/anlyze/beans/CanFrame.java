package com.newnetcom.anlyze.beans;

import java.util.Map;

public class CanFrame
{

	private String id; // propertyName

	private String name; // canId

	private String title;// 显示名称

	private Integer type; // 协议类型

	private String alias; // 别名

	private Map<String, Object> canContentMap;


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getTitle()
	{
		return title;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}


	public Integer getType()
	{
		return type;
	}


	public void setType(Integer type)
	{
		this.type = type;
	}


	public String getAlias()
	{
		return alias;
	}


	public void setAlias(String alias)
	{
		this.alias = alias;
	}


	public Map<String, Object> getCanContentMap()
	{
		return canContentMap;
	}


	public void setCanContentMap(Map<String, Object> canContentMap)
	{
		this.canContentMap = canContentMap;
	}


	@Override
	public String toString()
	{
		return "CanFrame [id=" + id + ", name=" + name + ", title=" + title + ", type=" + type + ", alias=" + alias + "]";
	}

}
