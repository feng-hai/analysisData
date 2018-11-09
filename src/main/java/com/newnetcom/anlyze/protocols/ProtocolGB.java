package com.newnetcom.anlyze.protocols;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.LocalInfo;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.enums.CommandTypeEnum;
import com.newnetcom.anlyze.enums.ContentTypeEnum;
import com.newnetcom.anlyze.protocols.p3g.ProtocolHeadFor3G;
import com.newnetcom.anlyze.protocols.pgb.Protocol01ForGB;
import com.newnetcom.anlyze.protocols.pgb.Protocol02ForGB;
import com.newnetcom.anlyze.protocols.pgb.ProtocolHeadForGB;
import com.newnetcom.anlyze.protocols.yuchai.Protocol50ForYuchai;
import com.newnetcom.anlyze.protocols.yuchai.ProtocolForYuchai;
import com.newnetcom.anlyze.protocols.yuchai.ProtocolHeadForYuchai;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.PointDouble;
import com.newnetcom.anlyze.utils.Wars2Wgs;


public class ProtocolGB implements IProtocol {
	private ProtocolBean protocolBean;
	private String fiber;
	private static final Logger logger = LoggerFactory.getLogger(ProtocolGB.class);
	/**
	 * @Fields content : TODO(用一句话描述这个变量表示什么)
	 */
	private byte[] content;
	
	/**
	 * @Fields head : TODO(用一句话描述这个变量表示什么)
	 */
	private ProtocolHeadForGB head;
	private CommandTypeEnum cte;
	
	private Protocol01ForGB protocol101;
	private Protocol02ForGB protocol102;
	public ProtocolGB(ProtocolBean protocolBean) {
		this.protocolBean = protocolBean;
		this.fiber = protocolBean.getFIBER_UNID();
		try {
			try {
				this.content = protocolBean.getContent();
			} catch (Exception ex) {
				logger.error("解析descape错误" + ByteUtils.byte2HexStr(protocolBean.getContent()), ex);
			}
			head = new ProtocolHeadForGB(this.content);
			if ((int) 0x01 == head.getCommandTag() && head.getCommandTag() > 0) {
				cte = CommandTypeEnum.C01;
				protocol101 = new Protocol01ForGB(this.content);
			} else if ((int) 0x02 == head.getCommandTag() && head.getCommandTag() > 0) {
				cte = CommandTypeEnum.C02;
				protocol102 = new Protocol02ForGB(ByteUtils.getSubBytes(this.content, 24, head.getDataLength()),protocolBean.getUnid());
			} 
//			else if ((int) 0xA0 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
//				cte = CommandTypeEnum.CA0;
//				vehcleInfo = new ProtocolForYuchai(this.content);
//			} else if ((int) 0x50 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
//				cte = CommandTypeEnum.C50;
//				vehicleCan = new Protocol50ForYuchai(this.content);
//			}

		} catch (Exception ex) {
			logger.error("ProtocolGB解析出错" + ByteUtils.byte2HexStr(protocolBean.getContent()), ex);
		}
	}

	@Override
	public Map<byte[],byte[]> getContent() {
		// TODO Auto-generated method stub

//		if (cte == CommandTypeEnum.C50) {
//			return vehicleCan.getCans();
//		}

		// if (canContent != null) {
		// return canContent.getCans();
		// } else if (yueboContent != null) {
		// return yueboContent.getCans();
		//
		// } else {
		return new HashMap<>();
	}

	@Override
	public Boolean checkValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentTypeEnum getContentType() {
		// TODO Auto-generated method stub
		return ContentTypeEnum.PCanType;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getFiber() {
		// TODO Auto-generated method stub
		return this.fiber;
	}

	@Override
	public ResultBean toResult(List<PairResult> beanList) {
		ResultBean rb = new ResultBean();

		// System.out.println("解析04");
		try {
			rb.setVehicleUnid(this.protocolBean.getUnid());
			rb.setFiber_unid(this.protocolBean.getFIBER_UNID());
			rb.setContent(this.protocolBean.getRAW_OCTETS());
			rb.setKey(this.protocolBean.getDEVICE_ID());
			rb.setSystemDate(new Date());

			if (cte == CommandTypeEnum.C01) {
				
//				if(tempPairs!=null)
//				beanList.addAll(tempPairs);
			} else if (cte == CommandTypeEnum.C02) {
			
//				List<PairResult> tempPairs = contentC02(rb);
//				if(tempPairs!=null)
				beanList.addAll(protocol102.getResult());
			} 
//			else if (cte == CommandTypeEnum.CA0) {
//				List<PairResult> tempPairs = contentA0(rb);
//				if(tempPairs!=null)
//				beanList.addAll(tempPairs);
//			}
			rb.setPairs(beanList);
			publicStaticMap.getCmdQueue().put(rb);// 用于更新hbase
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("异常错误", e);
		}
		// System.out.println("解析05");
		return rb;
	}

	
	
	
}
