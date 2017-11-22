package com.newnetcom.anlyze.protocols;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.enums.ContentTypeEnum;
import com.newnetcom.anlyze.protocols.p3g.Protocol038EFor3G;
import com.newnetcom.anlyze.protocols.p808.Protocol0200For808;
import com.newnetcom.anlyze.protocols.p808.Protocol0705For808;
import com.newnetcom.anlyze.protocols.p808.ProtocolHeadFor808;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.PointDouble;
import com.newnetcom.anlyze.utils.Wars2Wgs;

public class Protocol808 implements IProtocol {

	private static final Logger logger = LoggerFactory.getLogger(Protocol808.class);
	private String fiber;
	private byte[] contents;
	/**
	 * @Fields head : 协议头部解析
	 */
	private ProtocolHeadFor808 head;
	private Protocol0705For808 body;
	private Protocol0200For808 body0200;
	private Protocol038EFor3G yueboContent;

	public Protocol0200For808 getBody0200() {
		return body0200;
	}

	public void setBody0200(Protocol0200For808 body0200) {
		this.body0200 = body0200;
	}

	private ProtocolBean protocolBean;

	public ProtocolHeadFor808 getHead() {
		return head;
	}

	public void setHead(ProtocolHeadFor808 head) {
		this.head = head;
	}

	public Protocol808(ProtocolBean protocolBean) {
		this.fiber = protocolBean.getFIBER_UNID();
		this.protocolBean = protocolBean;
		try {
			this.contents = ByteUtils.toEscape(protocolBean.getContent());
			head = new ProtocolHeadFor808(this.contents);
			if (!checkCode())// 验证码不正确
			{
				logger.error("808验证码错误" + ByteUtils.byte2HexStr(this.contents));
			}
			if ((short) 0x0705 == head.getCommondId() && head.getDataLength() > 0) {
				body = new Protocol0705For808(this.protocolBean,
						ByteUtils.getSubBytes(this.contents, head.getHeadLength(), head.getDataLength()));
			} else if ((short) 0x0200 == head.getCommondId() && head.getDataLength() > 0) {
				body0200 = new Protocol0200For808(
						ByteUtils.getSubBytes(this.contents, head.getHeadLength(), head.getDataLength()));
			}else if ((int) 0x038E == head.getCommondId() && head.getDataLength() > 0) {
				try {
					
						yueboContent = new Protocol038EFor3G(
								ByteUtils.getSubBytes(this.contents, 20, head.getDataLength()));
					
				} catch (Exception ex) {
					logger.error(
							head.getDataLength() + "Protocol038e解析出错" + ByteUtils.byte2HexStr(protocolBean.getContent()),
							ex);
				}
			}
		} catch (Exception ex) {
			logger.error("Protocol808错误", ex);
		}
	}

	private Boolean checkCode() {
		return true;
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public ResultBean toResult(List<PairResult> beanList) {
		ResultBean rb = new ResultBean();
		try {
			rb.setVehicleUnid(this.protocolBean.getUnid());
			if (this.body != null) {
				rb.setDatetime(this.body.getCanTime());
				// rb.setLat(this.body.getLat());
				// rb.setLng(this.body.getLng());
				// this.head.get
				if (rb.getDatetime() != null) {
					beanList.add(new PairResult("DATIME_CAN", "DATIME_CAN", "参数采集的时间", sdf.format(rb.getDatetime())));
				}
			}
			if(this.yueboContent!=null)
			{
				rb.setDatetime(this.yueboContent.getStartTime());
				rb.setLat(this.yueboContent.getLat());
				rb.setLng(this.yueboContent.getLng());
				rb.setDirection(this.yueboContent.getDirection());
				if (rb.getDatetime() != null) {
					beanList.add(new PairResult("DATIME_CAN", "DATIME_CAN", "参数采集的时间",
							sdf.format(this.yueboContent.getStartTime())));
				}
			}
			if (this.head != null) {
				rb.setKey(this.head.getPhone());
			}

			if (this.body0200 != null) {
				rb.setDatetime(this.body0200.getDateTime());
				rb.setLat(this.body0200.getLat());
				rb.setLng(this.body0200.getLng());
				rb.setDirection(this.body0200.getDirection());

				if (rb.getLng() != null && rb.getLat() != null) {
					beanList.add(new PairResult("LON", "LON", "经度", String.valueOf(rb.getLng())));
					beanList.add(new PairResult("LAT", "LAT", "维度", String.valueOf(rb.getLat())));
					PointDouble pd = new PointDouble(rb.getLng(), rb.getLat());
					if (pd != null) {
						PointDouble en = Wars2Wgs.s2c(pd);
						beanList.add(new PairResult("LON_D", "LON_D", "经度", String.valueOf(en.x)));
						beanList.add(new PairResult("LAT_D", "LAT_D", "维度", String.valueOf(en.y)));
					}
				}
				if (rb.getDatetime() != null) {
					beanList.add(new PairResult("DATIME_GPS", "DATIME_GPS", "GPS时间",  sdf.format(rb.getDatetime())));
				}
			}

			// if(this.gpsInfo!=null)
			// {
			// rb.setDatetime(this.gpsInfo.getDate());
			// rb.setLat(this.gpsInfo.getLat());
			// rb.setLng(this.gpsInfo.getLng());
			// rb.setSystemDate(new Date());
			// rb.setAcc(this.gpsInfo.getAcc());
			// rb.setLocationState(this.gpsInfo.getLocationState());
			// rb.setDistance(this.gpsInfo.getDistance());
			// rb.setDirection(this.gpsInfo.getDirection());
			// rb.setElectricityStatus(this.gpsInfo.getElectricityStatus());
			// rb.setGpsStatus(this.gpsInfo.getGpsStatus());
			// rb.sethVersion(this.gpsInfo.gethVersion());
			// rb.setsVersion(this.gpsInfo.getsVersion());
			// rb.setStarNum(this.gpsInfo.getStarNum());
			// }
			rb.setSystemDate(new Date());
			if (rb.getDatetime() != null) {
				try {
					beanList.add(new PairResult("DATIME_RX", "DATIME_RX", "在线时间", sdf.format(rb.getDatetime())));
				} catch (Exception ex) {
					logger.error("在线时间" + rb.getDatetime(), ex);
				}
			}else{
				
				return null;
			}
			beanList.add(new PairResult("ENTITY_UNID","ENTITY_UNID","车辆唯一标识",rb.getVehicleUnid()));
			rb.setPairs(beanList);
			publicStaticMap.getCmdQueue().put(rb);
			publicStaticMap.getRedisQueue().put(rb);// 用于更新redis
			publicStaticMap.getSendDataQueue().put(rb);// 用于发送kafka数据
			// if (publicStaticMap.getSendDataQueue().size() > 100000) {
			// logger.error("kafka更新异常");
			// publicStaticMap.getSendDataQueue().clear();
			// }
			// if (publicStaticMap.getCmdQueue().size() > 1000000) {
			// Thread.sleep(5 * 60000);// 一分钟后重新启动kafka
			// }
			// if (publicStaticMap.getRedisQueue().size() > 100000) {
			// logger.error("redis更新异常");
			// publicStaticMap.getRedisQueue().clear();
			// }
		} catch (Exception ex) {
			logger.error("结果赋值错误", ex);
		}
		return rb;
	}

	/*
	 * (非 Javadoc) <p>Title: getContent</p> <p>Description: 获取Can针集合</p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.protocols.IProtocol#getContent()
	 */
	@Override
	public Map<byte[], byte[]> getContent() {
		// TODO Auto-generated method stub
		if (body != null) {
			return body.getCans();
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
		return null;
	}

	@Override
	public String getFiber() {
		// TODO Auto-generated method stub
		return this.fiber;
	}

}
