package com.newnetcom.anlyze.anlyze.arithmetic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.JsonUtils;

public class ByteArithimetic implements ILoadData {

	private Pair bean;
	private static final Logger logger = LoggerFactory.getLogger(ByteArithimetic.class);
	public ByteArithimetic(Pair inbean) {
		this.bean = inbean;
	}

	@Override
	public void setPairValue(byte[] content) {
		// TODO Auto-generated method stub
		int startByteIndex = bean.getStart() / 8;
		// int bitIndex = bean.getStart() % 8;
		int num = bean.getLength() / 8;
		if(startByteIndex+num>8)
		{
			logger.debug("解析规则有问题："+JsonUtils.serialize(bean));
		}
		boolean isNum = bean.getResolving().matches("[0-9]+");
		
		if (this.bean.getByteOrder()) {
		if (!isNum) {
			float resolving = Float.parseFloat(bean.getResolving());
			int nums=ByteUtils.getNum(bean.getResolving());
			switch (num) {
			case 1:
				bean.setValue(String.valueOf(ByteUtils.formatDouble((content[startByteIndex]&0xff) * resolving + bean.getOffset(),nums)));
				break;
			case 2:
				bean.setValue(
						String.valueOf(ByteUtils.formatDouble((ByteUtils.getShort(content, startByteIndex)) * resolving + bean.getOffset(),nums)));
				break;
			case 3:
				bean.setValue(String
						.valueOf(ByteUtils.formatDouble((ByteUtils.getThreeByte(content, startByteIndex)) * resolving + bean.getOffset(),nums)));
				break;
			case 4:
				bean.setValue(
						String.valueOf(ByteUtils.formatDouble((ByteUtils.getInt(content, startByteIndex)) * resolving + bean.getOffset(),nums)));
				break;
			}
		} else {
			int resolving = Integer.parseInt(bean.getResolving());
			switch (num) {
			case 1:
				bean.setValue(String.valueOf((content[startByteIndex]&0xff) * resolving + (int)bean.getOffset()));
				break;
			case 2:
				bean.setValue(
						String.valueOf((ByteUtils.getShort(content, startByteIndex)) * resolving + (int)bean.getOffset()));
				break;
			case 3:
				bean.setValue(String
						.valueOf((ByteUtils.getThreeByte(content, startByteIndex)) * resolving + (int)bean.getOffset()));
				break;
			case 4:
				bean.setValue(
						String.valueOf((ByteUtils.getInt(content, startByteIndex)) * resolving + (int)bean.getOffset()));
				break;
			}
		}
		}else
		{
			if (!isNum) {
				float resolving = Float.parseFloat(bean.getResolving());
				int nums=ByteUtils.getNum(bean.getResolving());
				switch (num) {
				case 1:
					bean.setValue(String.valueOf(ByteUtils.formatDouble((content[startByteIndex]&0xff) * resolving + bean.getOffset(),nums)));
					break;
				case 2:
					bean.setValue(
							String.valueOf(ByteUtils.formatDouble((ByteUtils.getShortForLarge(content, startByteIndex)) * resolving + bean.getOffset(),nums)));
					break;
				case 3:
					bean.setValue(String
							.valueOf(ByteUtils.formatDouble((ByteUtils.getThreeByteForLarger(content, startByteIndex)) * resolving + bean.getOffset(),nums)));
					break;
				case 4:
					bean.setValue(
							String.valueOf(ByteUtils.formatDouble((ByteUtils.getIntForLarge(content, startByteIndex)) * resolving + bean.getOffset(),nums)));
					break;
				}
			} else {
				int resolving = Integer.parseInt(bean.getResolving());
				switch (num) {
				case 1:
					bean.setValue(String.valueOf((content[startByteIndex]&0xff) * resolving + (int)bean.getOffset()));
					break;
				case 2:
					bean.setValue(
							String.valueOf((ByteUtils.getShortForLarge(content, startByteIndex)) * resolving + (int)bean.getOffset()));
					break;
				case 3:
					bean.setValue(String
							.valueOf((ByteUtils.getThreeByteForLarger(content, startByteIndex)) * resolving + (int)bean.getOffset()));
					break;
				case 4:
					bean.setValue(
							String.valueOf((ByteUtils.getIntForLarge(content, startByteIndex)) * resolving + (int)bean.getOffset()));
					break;
				}
			}
		}
		
		//logger.info(this.bean.getByteOrder()+"-"+this.bean.getCanid()+this.bean.getTitle()+":"+bean.getStart()+"-"+bean.getLength()+":"+this.bean.getValue()+"-"+ByteUtils.byte2HexStr(content)+"-"+this.bean.getCode());
	}
}
