package com.newnetcom.anlyze.protocols.p808;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.protocols.p3g.Protocol02E8For3G;
import com.newnetcom.anlyze.utils.ByteUtils;

/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：Protocol0705For808   
* 类描述：   解析Can针数据
* 创建人：FH   
* 创建时间：2017年11月5日 下午9:51:56   
* @version        
*/
public class Protocol0705For808 {
	
	private static final Logger logger = LoggerFactory.getLogger(Protocol02E8For3G.class);
	private byte[] contents;
	private ProtocolBean protocolBean;
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param contents 
	*/
	public Protocol0705For808(ProtocolBean protocolBean,byte[] contents) {
		this.contents = contents;
		this.protocolBean=protocolBean;
		try {
			this.anlyze();
		} catch (Exception ex) {
			logger.error("解析02E8时出错", ex);
		}
	}
	/** 
	* @Fields canNum : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private short canNum;// CAN数据包个数
	
	private Date canTime;
	/** 
	* @Fields timeInterval : TODO(用一句话描述这个变量表示什么) 
	*/ 
	//private short timeInterval;// [和上包CAN数据时间间隔]1
	/** 
	* @Fields cans : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private Map<byte[], byte[]> cans = new HashMap<>();// can帧集合

	public Date getCanTime() {
		return canTime;
	}

	public void setCanTime(Date canTime) {
		this.canTime = canTime;
	}

	/** 
	* @Title: getCans 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Map<byte[],byte[]>    返回类型 
	* @throws 
	*/
	public Map<byte[], byte[]> getCans() {
		return cans;
	}

	/** 
	* @Title: setCans 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param cans    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void setCans(Map<byte[], byte[]> cans) {
		this.cans = cans;
	}

	/** 
	* @Title: anlyze 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void anlyze() {
		canNum = ByteUtils.getShortForLarge(this.contents, 0);
		canTime=new Date(Long.parseLong(this.protocolBean.getTIMESTAMP()));//ByteUtils.BytesToTimeForHours(this.contents, 1);
		//timeInterval = ByteUtils.getShort(this.contents, 28);
		for (int i = 0; i < canNum; i++) {
			byte[] key =ByteUtils.endianChange( getKey(ByteUtils.getSubBytes(this.contents, i * 12+7,4)));
			byte[] value = ByteUtils.getSubBytes(this.contents,i * 12+7+4,8);
			cans.put(key, value);
		}
	}
	private byte[] getKey(byte[]keys)
	{
		keys[0]=(byte)(keys[0]&31);
		return keys;
	}
}
