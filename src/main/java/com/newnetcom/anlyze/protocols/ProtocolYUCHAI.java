package com.newnetcom.anlyze.protocols;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.enums.ContentTypeEnum;
import com.newnetcom.anlyze.protocols.yuchai.ProtocolA5ForYuchai;
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

	private ProtocolA5ForYuchai vehcleInfo;

	/**
	 * @return the vehcleInfo
	 */
	public ProtocolA5ForYuchai getVehcleInfo() {
		return vehcleInfo;
	}

	/**
	 * @param vehcleInfo
	 *            the vehcleInfo to set
	 */
	public void setVehcleInfo(ProtocolA5ForYuchai vehcleInfo) {
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
				vehcleInfo = new ProtocolA5ForYuchai(this.content);
			} else if ((int) 0xA6 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {

				vehcleInfo = new ProtocolA5ForYuchai(this.content);
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

	@Override
	public Map<byte[], byte[]> getContent() {

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

//			if (this.vehcleInfo != null) {
//				rb.setDatetime(this.vehcleInfo.getLocal().getDate());
//				rb.setLat(this.vehcleInfo.getLocal().getLat());
//				rb.setLng(this.vehcleInfo.getLocal().getLng());
//				rb.setDirection(this.vehcleInfo.getLocal().getDirection());
//				if (rb.getDatetime() != null) {
//					beanList.add(new PairResult("DATIME_VS", "DATIME_VS", "参数采集的时间",
//							sdf.format(this.vehcleInfo.getLocal().getDate())));
//				}
//
//			}

			rb.setKey(this.protocolBean.getDEVICE_ID());

			if (this.vehcleInfo != null) {
				rb.setDatetime(this.vehcleInfo.getLocal().getDate());
				rb.setLat(this.vehcleInfo.getLocal().getLat());
				rb.setLng(this.vehcleInfo.getLocal().getLng());
				rb.setSystemDate(new Date());
				rb.setAcc(this.vehcleInfo.getLocal().getAcc());
				rb.setLocationState(this.vehcleInfo.getLocal().getLocationState());
				// rb.setDistance(this.gpsInfo.getDistance());
				rb.setDirection(this.vehcleInfo.getLocal().getDirection());
				rb.setElectricityStatus(this.vehcleInfo.getLocal().getElectricityStatus());
				// rb.setGpsStatus(this.gpsInfo.getGpsStatus());
				// rb.sethVersion(this.gpsInfo.gethVersion());
				// rb.setsVersion(this.gpsInfo.getsVersion());
				// rb.setStarNum(this.gpsInfo.getStarNum());
				if (rb.getDatetime() != null) {
					beanList.add(new PairResult("DATIME_GPS", "DATIME_GPS", "GPS时间", sdf.format(rb.getDatetime())));
				}
				beanList.add(new PairResult("SPEED_GPS", "SPEED_GPS", "GPS速度",
						String.valueOf(this.vehcleInfo.getLocal().getSpeed())));
				// beanList.add(new PairResult("ALT", "ALT", "高度",
				// String.valueOf(this.gpsInfo.getHeight())));
				beanList.add(new PairResult("BEARING", "BEARING", "方向角", String.valueOf(rb.getDirection())));
				// beanList.add(new PairResult("VERSION_HW", "VERSION_HW",
				// "硬件版本", String.valueOf(rb.gethVersion())));
				// beanList.add(new PairResult("VERSION_FW", "VERSION_FW",
				// "软件版本", String.valueOf(rb.getsVersion())));
				// beanList.add(new PairResult("MILEAGE_GPS", "MILEAGE_GPS",
				// "gps里程", String.valueOf(rb.getDistance())));
			}
			rb.setSystemDate(new Date());
			if (rb.getLng() != null && rb.getLat() != null) {
				beanList.add(new PairResult("LON", "LON", "经度", String.valueOf(rb.getLng())));
				beanList.add(new PairResult("LAT", "LAT", "维度", String.valueOf(rb.getLat())));
				PointDouble pd = new PointDouble(rb.getLng(), rb.getLat());
				if (pd != null) {
					try {
						PointDouble en = Wars2Wgs.s2c(pd);
						if (en != null) {
							beanList.add(new PairResult("LON_D", "LON_D", "经度", String.valueOf(en.x)));
							beanList.add(new PairResult("LAT_D", "LAT_D", "维度", String.valueOf(en.y)));
						}
					} catch (Exception ex) {
						logger.error("偏移出错" + pd.toString(), ex);
					}
				}
			}
			if (rb.getDatetime() != null) {
				try {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					beanList.add(new PairResult("DATIME_RX", "DATIME_RX", "在线时间", sdf.format(rb.getDatetime())));
				} catch (Exception ex) {
					logger.error("在线时间不存在" + rb.getDatetime(), ex);
				}
			} else {
				return null;
			}
			beanList.add(new PairResult("ENTITY_UNID", "ENTITY_UNID", "车辆唯一标识", rb.getVehicleUnid()));
			rb.setPairs(beanList);
			publicStaticMap.getCmdQueue().put(rb);// 用于更新hbase
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("异常错误", e);
		}
		// System.out.println("解析05");
		return rb;
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
