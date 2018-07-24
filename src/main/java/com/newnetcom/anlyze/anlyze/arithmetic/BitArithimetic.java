package com.newnetcom.anlyze.anlyze.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.newnetcom.anlyze.anlyze.AnlyzeCans;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.JsonUtils;
import com.newnetcom.anlyze.utils.StrFormat;

public class BitArithimetic implements ILoadData {
	private Pair bean;
	private static final Logger logger = LoggerFactory.getLogger(BitArithimetic.class);

	public BitArithimetic(Pair inbean) {
		this.bean = inbean;
	}

	@Override
	public void setPairValue(byte[] content) {
		int startByteIndex = bean.getStart() / 8;

		int bitIndex = bean.getStart() % 8;
		boolean isNum = bean.getResolving().matches("[0-9]+");
		if (bean.getLength() + bitIndex <= 8) {

			Byte tByte = content[startByteIndex];
			String bitStr = ByteUtils.byte2bitsByIndex(tByte, (byte) bitIndex, (byte) bean.getLength());
			if (!isNum) {
				int num = ByteUtils.getNum(bean.getResolving());
				bean.setValue(String.valueOf(ByteUtils.formatDouble(
						(ByteUtils.bit2byte(bitStr)) * Float.parseFloat(bean.getResolving()) + bean.getOffset(), num)));
			} else {
				bean.setValue(String.valueOf(
						(ByteUtils.bit2byte(bitStr)) * Integer.parseInt(bean.getResolving()) + (int) bean.getOffset()));
			}

		} else {
			int byteLength = 2;

			int length = bean.getLength() / 8;
			if (length >= 1) {
				byteLength += length - 1;
			}
			int lastIndex = bean.getLength() % 8;

			if (lastIndex + bitIndex > 8) {
				byteLength++;
			}
			if (startByteIndex + byteLength > 8) {
				logger.debug("规则不对，超出范围：" + JsonUtils.serialize(bean));
				return;
			}
			byte[] tBytes = ByteUtils.getSubBytes(content, startByteIndex, byteLength);
			// 默认是大端模式
			tBytes = ByteUtils.endianChange(tBytes);// 大小段转换，转为小端模式
			String bitStr = ByteUtils.binary(tBytes, 2);
			bitStr = StrFormat.addZeroForLeftNum(bitStr, byteLength * 8);
			String bitResult = bitStr.substring(bitIndex, bitIndex + bean.getLength());
			if (this.bean.getByteOrder()) {
				if (!isNum) {
					float resolving = Float.parseFloat(bean.getResolving());
					int num = ByteUtils.getNum(bean.getResolving());

					if (byteLength <= 4) {
						bean.setValue(String.valueOf(ByteUtils
								.formatDouble((Integer.parseInt(bitResult, 2)) * resolving + bean.getOffset(), num)));
					} else {
						bean.setValue(String.valueOf(ByteUtils
								.formatDouble((Long.parseLong(bitResult, 2)) * resolving + bean.getOffset(), num)));
					}
				} else {
					int resolving = Integer.parseInt(bean.getResolving());
					if (byteLength <= 4) {
						bean.setValue(
								String.valueOf((Integer.parseInt(bitResult, 2)) * resolving + (int) bean.getOffset()));
					} else {
						bean.setValue(
								String.valueOf((Long.parseLong(bitResult, 2)) * resolving + (long) bean.getOffset()));
					}
				}
			}else{
				
				if (!isNum) {
					float resolving = Float.parseFloat(bean.getResolving());
					int num = ByteUtils.getNum(bean.getResolving());
					
					if (byteLength <= 4) {
						Integer i =Integer.parseInt(bitResult, 2);	
						bean.setValue(String.valueOf(ByteUtils
								.formatDouble((ByteUtils.getIntForLarge(i.toString().getBytes(),0)) * resolving + bean.getOffset(), num)));
					} else {
						Long i =Long.parseLong(bitResult, 2);	
						bean.setValue(String.valueOf(ByteUtils
								.formatDouble((ByteUtils.getLongForLarge(i.toString().getBytes(),0)) * resolving + bean.getOffset(), num)));
					}
				} else {
					int resolving = Integer.parseInt(bean.getResolving());
					if (byteLength <= 4) {
						Integer i =Integer.parseInt(bitResult, 2);	
						bean.setValue(
								String.valueOf((ByteUtils.getIntForLarge(i.toString().getBytes(),0) * resolving + (int) bean.getOffset())));
					} else {
						Long i =Long.parseLong(bitResult, 2);	
						bean.setValue(
								String.valueOf((ByteUtils.getLongForLarge(i.toString().getBytes(),0)) * resolving + (long) bean.getOffset()));
					}
				}
				
			}
		}
		// logger.info(this.bean.getCanid()+this.bean.getTitle()+":"+bean.getStart()+"-"+bean.getLength()+":"+this.bean.getValue()+"-"+ByteUtils.byte2HexStr(content)+"-"+this.bean.getCode());
	}
}
