package com.newnetcom.anlyze.protocols;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.ResultBean;

import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.enums.ContentTypeEnum;
import com.newnetcom.anlyze.protocols.p3g.Protocol02E8For3G;
import com.newnetcom.anlyze.protocols.p3g.ProtocolGPSFor3G;
import com.newnetcom.anlyze.protocols.p3g.ProtocolHeadFor3G;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.JsonUtils;
import com.newnetcom.anlyze.utils.PointDouble;
import com.newnetcom.anlyze.utils.Wars2Wgs;

/**
 * 
 * 项目名称：com.newnetcom.anlyze 类名称：Protocol3G 类描述： 创建人：FH 创建时间：2017年10月29日
 * 上午9:49:23
 * 
 * @version
 */
public class Protocol3G implements IProtocol {

	private static Logger logger = LoggerFactory.getLogger(Protocol3G.class);
	/**
	 * @Fields content : TODO(用一句话描述这个变量表示什么)
	 */
	private byte[] content;
	/**
	 * @Fields head : TODO(用一句话描述这个变量表示什么)
	 */
	private ProtocolHeadFor3G head;

	private ProtocolGPSFor3G gpsInfo;

	private String fiber;
	/**
	 * @Fields canContent : TODO(用一句话描述这个变量表示什么)
	 */
	private Protocol02E8For3G canContent;

	private ProtocolBean protocolBean;

	public Protocol3G(ProtocolBean protocolBean) {
		this.protocolBean = protocolBean;
		this.fiber = protocolBean.getFIBER_UNID();
		try {
			try {
				this.content = ByteUtils.toEscape(protocolBean.getContent());
			} catch (Exception ex) {
				logger.error("解析7D01错误" + ByteUtils.byte2HexStr(protocolBean.getContent()), ex);
			}
			head = new ProtocolHeadFor3G(this.content);
			if ((int) 0x02E8 == head.getCanId() && head.getCanLength() > 0) {
				try {
					if (this.content.length > 20 + head.getCanLength()) {
						canContent = new Protocol02E8For3G(
								ByteUtils.getSubBytes(this.content, 20, head.getCanLength()));
					} else {
						logger.debug("数据错误：指定长度和实际不符" + ByteUtils.byte2HexStr(this.content));
					}
				} catch (Exception ex) {
					logger.error(
							head.getCanLength() + "Protocol02E8解析出错" + ByteUtils.byte2HexStr(protocolBean.getContent()),
							ex);
				}
			}
			if ((int) 0x0181 == head.getTerminalCommandId() && head.getTerminalCommandLength() > 0) {
				if (this.content.length > 20 + head.getTerminalCommandLength()) {
					gpsInfo = new ProtocolGPSFor3G(
							ByteUtils.getSubBytes(this.content, 20, head.getTerminalCommandLength()));
				}else {
					logger.debug("数据错误：指定长度和实际不符" + ByteUtils.byte2HexStr(this.content));
				}
			}
		} catch (Exception ex) {
			logger.error("Protocol3G解析出错" + ByteUtils.byte2HexStr(protocolBean.getContent()), ex);
		}
	}

	public ProtocolHeadFor3G getHead() {
		return head;
	}

	public void setHead(ProtocolHeadFor3G head) {
		this.head = head;
	}

	public Protocol02E8For3G getCanContent() {
		return canContent;
	}

	public void setCanContent(Protocol02E8For3G canContent) {
		this.canContent = canContent;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public Map<byte[], byte[]> getContent() {

		if (canContent != null) {
			return canContent.getCans();
		} else {
			return new HashMap<>();
		}
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
		return head.getTerminalId();
	}

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
			if (this.canContent != null) {

				// Map<String, PairResult> rbs =
				// vehilces.get(this.protocolBean.getUnid());
				// for (PairResult pair : beanList) {
				// if (rbs.containsKey(pair.getCode())) {
				// rbs.replace(pair.getCode(), pair);
				// } else {
				// rbs.put(pair.getCode(), pair);
				// }
				// }
				// beanList = (List<PairResult>) rbs.values();

				rb.setDatetime(this.canContent.getStartTime());
				rb.setLat(this.canContent.getLat());
				rb.setLng(this.canContent.getLng());
				rb.setDirection(this.canContent.getDirection());
				if (rb.getDatetime() != null) {
					beanList.add(new PairResult("DATIME_CAN", "DATIME_CAN", "参数采集的时间",
							sdf.format(this.canContent.getStartTime())));
				}

			}
			if (this.head != null) {
				rb.setKey(this.head.getTerminalId());
			}
			if (this.gpsInfo != null) {
				rb.setDatetime(this.gpsInfo.getDate());
				rb.setLat(this.gpsInfo.getLat());
				rb.setLng(this.gpsInfo.getLng());
				rb.setSystemDate(new Date());
				rb.setAcc(this.gpsInfo.getAcc());
				rb.setLocationState(this.gpsInfo.getLocationState());
				rb.setDistance(this.gpsInfo.getDistance());
				rb.setDirection(this.gpsInfo.getDirection());
				rb.setElectricityStatus(this.gpsInfo.getElectricityStatus());
				rb.setGpsStatus(this.gpsInfo.getGpsStatus());
				rb.sethVersion(this.gpsInfo.gethVersion());
				rb.setsVersion(this.gpsInfo.getsVersion());
				rb.setStarNum(this.gpsInfo.getStarNum());
				if (rb.getDatetime() != null) {
					beanList.add(new PairResult("DATIME_GPS", "DATIME_GPS", "GPS时间", sdf.format(rb.getDatetime())));
				}
				beanList.add(new PairResult("SPEED_GPS", "SPEED_GPS", "GPS速度", String.valueOf(this.gpsInfo.getSpeed())));
				beanList.add(new PairResult("ALT", "ALT", "高度", String.valueOf(this.gpsInfo.getHeight())));
				beanList.add(new PairResult("BEARING", "BEARING", "方向角", String.valueOf(this.gpsInfo.getDirection())));
				beanList.add(new PairResult("VERSION_HW", "VERSION_HW", "硬件版本", String.valueOf(rb.gethVersion())));
				beanList.add(new PairResult("VERSION_FW", "VERSION_FW", "软件版本", String.valueOf(rb.getsVersion())));
				beanList.add(new PairResult("MILEAGE_GPS", "MILEAGE_GPS", "gps里程", String.valueOf(rb.getDistance())));
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
			// beanList.add(new Pair("","","",rb.getKey()));
			rb.setPairs(beanList);
			publicStaticMap.getCmdQueue().put(rb);// 用于更新hbase

			publicStaticMap.getRedisQueue().put(rb);// 用于更新redis

			publicStaticMap.getSendDataQueue().put(rb);// 用于发送kafka数据
			// System.out.println("解析05-1");

			// if (publicStaticMap.getSendDataQueue().size() > 100000) {
			// logger.error("kafka更新异常");
			// publicStaticMap.getSendDataQueue().clear();
			// }
			//
			// if (publicStaticMap.getCmdQueue().size() > 1000000) {
			// Thread.sleep(5 * 60000);// 一分钟后重新启动kafka
			// }
			// if (publicStaticMap.getRedisQueue().size() > 100000) {
			// logger.error("redis更新异常");
			// publicStaticMap.getRedisQueue().clear();
			// }
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

}
