package com.newnetcom.anlyze.protocols;

import java.nio.ByteBuffer;
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
import com.newnetcom.anlyze.protocols.yuchai.Protocol50ForYuchai;
import com.newnetcom.anlyze.protocols.yuchai.ProtocolForYuchai;
import com.newnetcom.anlyze.protocols.yuchai.ProtocolHeadForYuchai;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.PointDouble;
import com.newnetcom.anlyze.utils.Wars2Wgs;

/**
 * 
 * 项目名称：com.newnetcom.anlyze 类名称：ProtocolYUCHAI 类描述： 创建人：FH 创建时间：2018年11月2日
 * 上午11:28:37
 * 
 * @version
 */
public class ProtocolYUCHAI implements IProtocol {

	private static Logger logger = LoggerFactory.getLogger(ProtocolYUCHAI.class);
	/**
	 * @Fields content : TODO(用一句话描述这个变量表示什么)
	 */
	private byte[] content;

	/**
	 * @Fields head : TODO(用一句话描述这个变量表示什么)
	 */
	private ProtocolHeadForYuchai head;

	private String fiber;

	private CommandTypeEnum cte;

	private ProtocolForYuchai vehcleInfo;

	private Protocol50ForYuchai vehicleCan;

	/**
	 * @return the vehcleInfo
	 */
	public ProtocolForYuchai getVehcleInfo() {
		return vehcleInfo;
	}

	/**
	 * @param vehcleInfo
	 *            the vehcleInfo to set
	 */
	public void setVehcleInfo(ProtocolForYuchai vehcleInfo) {
		this.vehcleInfo = vehcleInfo;
	}

	private ProtocolBean protocolBean;

	public ProtocolYUCHAI(ProtocolBean protocolBean) {
		this.protocolBean = protocolBean;
		this.fiber = protocolBean.getFIBER_UNID();
		try {
			try {
				this.content = descape(protocolBean.getContent());
			} catch (Exception ex) {
				logger.error("解析descape错误" + ByteUtils.byte2HexStr(protocolBean.getContent()), ex);
			}
			head = new ProtocolHeadForYuchai(this.content);
			if ((int) 0xA5 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
				cte = CommandTypeEnum.CA5;
				vehcleInfo = new ProtocolForYuchai(this.content);
			} else if ((int) 0xA6 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
				cte = CommandTypeEnum.CA6;
				vehcleInfo = new ProtocolForYuchai(this.content);
			} else if ((int) 0xA0 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
				cte = CommandTypeEnum.CA0;
				vehcleInfo = new ProtocolForYuchai(this.content);
			} else if ((int) 0x50 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
				cte = CommandTypeEnum.C50;
				vehicleCan = new Protocol50ForYuchai(this.content);
			}

		} catch (Exception ex) {
			logger.error("ProtocolYuchai解析出错" + ByteUtils.byte2HexStr(protocolBean.getContent()), ex);
		}
	}

	private static final byte OX7C = 0x7c;

	private static final byte[] OX7C_ESCAPE = { 0x7c, 0x0c };

	public static final byte OX7B = 0x7b;

	private static final byte[] OX7B_ESCAPE = { 0x7c, 0x0b };

	/**
	 * 还原转义
	 */
	protected byte[] descape(byte[] octets) {
		if (octets != null && octets.length > 2) {
			ByteBuffer buffer = ByteBuffer.allocate(octets.length);
			buffer.put(octets[0]);// head
			int i = 1;
			for (; i < octets.length - 2; i++) {
				if (octets[i] == OX7C) {
					if (octets[i + 1] == OX7B_ESCAPE[1]) {
						buffer.put(OX7B);
						i++;
					} else if (octets[i + 1] == OX7C_ESCAPE[1]) {
						buffer.put(OX7C);
						i++;
					} else {
						buffer.put(octets[i]);
					}
				} else {
					buffer.put(octets[i]);
				}
			}
			if (i == octets.length - 2)
				buffer.put(octets[octets.length - 2]);
			buffer.put(octets[octets.length - 1]);
			buffer.flip();
			byte[] octetsDescaped = new byte[buffer.remaining()];
			buffer.get(octetsDescaped);
			return octetsDescaped;
		}
		return null;
	}

	// private byte[] messageType = new byte[2];

	/**
	 * 转义
	 */
	protected byte[] escape(byte[] octets) {
		if (octets != null && octets.length > 2) {
			byte[] octetsDescaped = octets;
			ByteBuffer buffer = ByteBuffer.allocate(octetsDescaped.length * 2);
			buffer.put(octetsDescaped[0]);// head
			for (int i = 1; i < octetsDescaped.length - 1; i++) {
				if (octetsDescaped[i] == OX7B)
					buffer.put(OX7B_ESCAPE);
				else if (octetsDescaped[i] == OX7C)
					buffer.put(OX7C_ESCAPE);
				else
					buffer.put(octetsDescaped[i]);
			}
			buffer.put(octetsDescaped[octetsDescaped.length - 1]);// tail
			buffer.flip();
			byte[] octetsEscaped = new byte[buffer.remaining()];
			buffer.get(octetsEscaped);
			return octetsEscaped;
		}
		return new byte[0];
	}

	public ProtocolHeadForYuchai getHead() {
		return head;
	}

	public void setHead(ProtocolHeadForYuchai head) {
		this.head = head;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	/*
	 * (非 Javadoc) <p>Title: getContent</p> <p>Description:解析CAN针和CAN内容对应关系 </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.protocols.IProtocol#getContent()
	 */
	@Override
	public Map<byte[], byte[]> getContent() {

		if (cte == CommandTypeEnum.C50) {
			return vehicleCan.getCans();
		}

		// if (canContent != null) {
		// return canContent.getCans();
		// } else if (yueboContent != null) {
		// return yueboContent.getCans();
		//
		// } else {
		return new HashMap<>();
		// }
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

	// @Override
	// public String getKey() {
	// // TODO Auto-generated method stub
	// return head.getTerminalId();
	// }

	// private static Map<String, Map<String, PairResult>> vehilces = new
	// ConcurrentHashMap<>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

			if (cte == CommandTypeEnum.CA5) {
				List<PairResult> tempPairs = contentA5(rb);
				if(tempPairs!=null)
				beanList.addAll(tempPairs);
			} else if (cte == CommandTypeEnum.CA6) {
			
				List<PairResult> tempPairs = contentA6(rb);
				if(tempPairs!=null)
				beanList.addAll(tempPairs);
			} else if (cte == CommandTypeEnum.CA0) {
				List<PairResult> tempPairs = contentA0(rb);
				if(tempPairs!=null)
				beanList.addAll(tempPairs);
			}
			rb.setPairs(beanList);
			publicStaticMap.getCmdQueue().put(rb);// 用于更新hbase
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("异常错误", e);
		}
		// System.out.println("解析05");
		return rb;
	}

	private List<PairResult> content(ResultBean rb) {
		List<PairResult> tempPairs = new ArrayList<PairResult>();
		LocalInfo localInfo = publicStaticMap.getLastLocal().get(rb.getVehicleUnid());
		tempPairs.add(new PairResult("localstate", "localstate", "定位状态",
				String.valueOf(this.vehcleInfo.getLocal().getLocationState())));
		tempPairs.add(new PairResult("LatDi", "LatDi", "东经或西经", String.valueOf(this.vehcleInfo.getLocal().getLatDi())));
		tempPairs.add(new PairResult("LngDi", "LngDi", "东纬或西纬", String.valueOf(this.vehcleInfo.getLocal().getLngDi())));
		tempPairs.add(
				new PairResult("DATIME_GPS", "DATIME_GPS", "GPS时间", sdf.format(this.vehcleInfo.getLocal().getDate())));
		if (rb.getLng() != null && rb.getLat() != null) {
			tempPairs.add(new PairResult("LON", "LON", "经度", String.valueOf(this.vehcleInfo.getLocal().getLng())));
			tempPairs.add(new PairResult("LAT", "LAT", "维度", String.valueOf(this.vehcleInfo.getLocal().getLat())));

			localInfo.setLng(this.vehcleInfo.getLocal().getLng());
			localInfo.setLat(this.vehcleInfo.getLocal().getLat());
			PointDouble pd = new PointDouble(this.vehcleInfo.getLocal().getLng(), this.vehcleInfo.getLocal().getLat());
			if (pd != null) {
				try {
					PointDouble en = Wars2Wgs.s2c(pd);

					if (en != null) {
						localInfo.setLng(en.x);
						localInfo.setLat(en.y);
						tempPairs.add(new PairResult("LON_D", "LON_D", "经度", String.valueOf(en.x)));
						tempPairs.add(new PairResult("LAT_D", "LAT_D", "维度", String.valueOf(en.y)));
					}
				} catch (Exception ex) {
					logger.error("偏移出错" + pd.toString(), ex);
				}
			}
		}
		if (rb.getDatetime() != null) {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				tempPairs.add(new PairResult("DATIME_RX", "DATIME_RX", "在线时间",
						sdf.format(this.vehcleInfo.getLocal().getDate())));
			} catch (Exception ex) {
				logger.error("在线时间不存在" + rb.getDatetime(), ex);
			}
		} else {
			return null;
		}
		tempPairs.add(new PairResult("SPEED_GPS", "SPEED_GPS", "GPS速度",
				String.valueOf(this.vehcleInfo.getLocal().getSpeed())));
		// beanList.add(new PairResult("ALT", "ALT", "高度",
		// String.valueOf(this.gpsInfo.getHeight())));
		tempPairs.add(
				new PairResult("BEARING", "BEARING", "方向角", String.valueOf(this.vehcleInfo.getLocal().getDirection())));
		tempPairs.add(new PairResult("ENTITY_UNID", "ENTITY_UNID", "车辆唯一标识", rb.getVehicleUnid()));
		tempPairs.add(new PairResult("ACC", "ACC", "ACC", String.valueOf(this.vehcleInfo.getVehicle().getAcc())));
		tempPairs.add(new PairResult("GPSALARM", "GPSALARM", "GPS报警",
				String.valueOf(this.vehcleInfo.getVehicle().getGpsStatus())));
		tempPairs.add(new PairResult("OPENALARM", "OPENALARM", "开盖报警",
				String.valueOf(this.vehcleInfo.getVehicle().getOpenStatus())));
		tempPairs.add(new PairResult("MESTATUS", "MESTATUS", "主电状态",
				String.valueOf(this.vehcleInfo.getVehicle().getMainElectricState())));
		tempPairs.add(new PairResult("MELOW", "MELOW", "主欠压报警",
				String.valueOf(this.vehcleInfo.getVehicle().getMainElectricLow())));
		tempPairs.add(new PairResult("CANALARM", "CANALARM", "CAN状态",
				String.valueOf(this.vehcleInfo.getVehicle().getCanStatus())));
		tempPairs.add(new PairResult("ESTATUS", "ESTATUS", "备电状态",
				String.valueOf(this.vehcleInfo.getVehicle().getElectricPower())));
		tempPairs.add(new PairResult("ELOW", "ELOW", "备电欠压",
				String.valueOf(this.vehcleInfo.getVehicle().getElectricPowerLow())));
		return tempPairs;
	}

	/**
	 * @Title: contentA5 @Description: TODO(解析A5) @param 设定文件 @return void
	 *         返回类型 @throws
	 */
	private List<PairResult> contentA5(ResultBean rb) {
		List<PairResult> tempPairs = content(rb);
		if(tempPairs!=null)
		{
		tempPairs.add(new PairResult("COMMANDTYPE", "COMMANDTYPE", "命令类型", "A5"));
		}
		return tempPairs;

	}

	/**
	 * @Title: contentA5 @Description: TODO(解析A5) @param 设定文件 @return void
	 *         返回类型 @throws
	 */
	private List<PairResult> contentA0(ResultBean rb) {
		List<PairResult> tempPairs = content(rb);
		if(tempPairs!=null)
		{
		tempPairs.add(new PairResult("COMMANDTYPE", "COMMANDTYPE", "命令类型", "A0"));
		}
		return tempPairs;

	}

	/**
	 * @Title: contentA5 @Description: TODO(解析A6) @param 设定文件 @return void
	 *         返回类型 @throws
	 */
	private List<PairResult> contentA6(ResultBean rb) {
		List<PairResult> tempPairs = content(rb);
		if(tempPairs!=null)
		{
		tempPairs.add(new PairResult("COMMANDTYPE", "COMMANDTYPE", "命令类型", "A6"));
		}
		return tempPairs;
	}

	@Override
	public String getFiber() {
		// TODO Auto-generated method stub
		return this.fiber;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return this.protocolBean.getDEVICE_ID();
	}

}
