package com.newnetcom.anlyze.anlyze.arithmetic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.JsonUtils;

public class ByteArithimeticForBig implements ILoadData {

	private Pair bean;
	private static final Logger logger = LoggerFactory.getLogger(ByteArithimeticForBig.class);
	public ByteArithimeticForBig(Pair inbean) {
		this.bean = inbean;
	}

	@Override
	public void setPairValue(byte[] content) {
		// TODO Auto-generated method stub
		int startByteIndex = bean.getStart() / 8;
		// int bitIndex = bean.getStart() % 8;
		int num = bean.getLength() / 8;
		
		content = ByteUtils.endianChange(content);// 大小段转换，转为大端模式 = ByteUtils.endianChange(tBytes);// 大小段转换，转为小端模式
		
		if(startByteIndex+num>8)
		{
			logger.debug("解析规则有问题："+JsonUtils.serialize(bean));
		}
		
		boolean isNum = bean.getResolving().matches("[0-9]+");
		if (!isNum) {
			float resolving = Float.parseFloat(bean.getResolving());
			int nums=ByteUtils.getNum(bean.getResolving());
			switch (num) {
			case 1:
				bean.setValue(String.valueOf(ByteUtils.formatDouble((content[8-startByteIndex-1]&0xff) * resolving + bean.getOffset(),nums)));
				break;
			case 2:
				bean.setValue(
						String.valueOf(ByteUtils.formatDouble((ByteUtils.getShortForLarge(content, 8-startByteIndex-2)) * resolving + bean.getOffset(),nums)));
				break;
			case 3:
				bean.setValue(String
						.valueOf(ByteUtils.formatDouble((ByteUtils.getThreeByte(content, 8-startByteIndex-3)) * resolving + bean.getOffset(),nums)));
				break;
			case 4:
				bean.setValue(
						String.valueOf(ByteUtils.formatDouble((ByteUtils.getIntForLarge(content, 8-startByteIndex-4)) * resolving + bean.getOffset(),nums)));
				break;
			}
		} else {
			int resolving = Integer.parseInt(bean.getResolving());
			switch (num) {
			case 1:
				bean.setValue(String.valueOf((content[8-startByteIndex-1]&0xff) * resolving + (int)bean.getOffset()));
				break;
			case 2:
				bean.setValue(
						String.valueOf((ByteUtils.getShortForLarge(content, 8-startByteIndex-2)) * resolving + (int)bean.getOffset()));
				break;
			case 3:
				bean.setValue(String
						.valueOf((ByteUtils.getThreeByte(content, 8-startByteIndex-3)) * resolving + (int)bean.getOffset()));
				break;
			case 4:
				bean.setValue(
						String.valueOf((ByteUtils.getIntForLarge(content, 8-startByteIndex-4)) * resolving + (int)bean.getOffset()));
				break;
			}
		}
		
		logger.info(this.bean.getCanid()+this.bean.getTitle()+":"+bean.getStart()+"-"+bean.getLength()+":"+this.bean.getValue()+"-"+ByteUtils.byte2HexStr(content));
	}
}
