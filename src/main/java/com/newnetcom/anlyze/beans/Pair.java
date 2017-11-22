/**  
* @Title: Pair.java
* @Package com.wlwl.cube.analyse.bean
* @Description: TODO(用一句话描述该文件做什么)
* @author fenghai  
* @date 2016年9月24日 下午4:18:53
* @version V1.0.0  
*/
package com.newnetcom.anlyze.beans;

import java.io.Serializable;

import com.newnetcom.anlyze.utils.JsonUtils;

/**
 * @ClassName: Pair
 * @Description: TODO(这里用一句话描述这个类的作用) "code": "guqi2ed", "alias": "", "title":
 *               "包头", "value": "true"
 * @author fenghai
 * @date 2016年9月24日 下午4:18:53
 *
 */
/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：Pair   
* 类描述：   
* 创建人：FH   
* 创建时间：2017年11月21日 下午6:48:09   
* @version        
*/
public class Pair implements Serializable, Comparable<Pair>,Cloneable  {

	private static final long serialVersionUID = 8418206139459599885L;
	private String code = "";
	private String alias = "";
	private String title = "";
	private String value = "-1";
	private Integer inx;
	private String canid;
	private String protocolId;
	
	private String ALGORITHM;
	
	public String getALGORITHM() {
		return ALGORITHM;
	}
	public void setALGORITHM(String aLGORITHM) {
		ALGORITHM = aLGORITHM;
	}

	/** 
	* @Fields PREREQUISITE_VALUE : 数据地址10进制
	*/ 
	private String PREREQUISITE_VALUE;
	public String getPREREQUISITE_VALUE() {
		return PREREQUISITE_VALUE;
	}
	public void setPREREQUISITE_VALUE(String pREREQUISITE_VALUE) {
		PREREQUISITE_VALUE = pREREQUISITE_VALUE;
	}
	public Pair()
	{}
	public Pair(String code,String alias,String title ,String value)
	{
		this.code=code;
		this.alias=alias;
		this.title=title;
		this.value=value;
	}

	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public String getCanid() {
		return canid;
	}

	public void setCanid(String canid) {
		this.canid = canid;
	}

	public Integer getInx() {
		return inx;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	private int start;
	
	private int length;
	
	private double offset;

	private String resolving;

	private String dataType = "1";

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public String getResolving() {
		return resolving;
	}

	public void setResolving(String resolving) {
		this.resolving = resolving;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            要设置的 code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            要设置的 alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            要设置的 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            要设置的 value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {

		return JsonUtils.serialize(this);
	}

	@Override
	public int compareTo(Pair o) {
		// TODO Auto-generated method stub
		return this.getInx().compareTo(o.getInx());
	}
	
	 @Override  
	    public Pair clone() {  
	        Pair stu = null;  
	        try{  
	            stu = (Pair)super.clone();  
	        }catch(CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return stu;  
	    }  
}
