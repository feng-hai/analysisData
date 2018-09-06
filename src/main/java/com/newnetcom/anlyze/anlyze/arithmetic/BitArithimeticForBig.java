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

public class BitArithimeticForBig implements ILoadData {
	private Pair bean;
	private static final Logger logger = LoggerFactory.getLogger(BitArithimeticForBig.class);

	public BitArithimeticForBig(Pair inbean) {
		this.bean = inbean;
	}

	@Override
	public void setPairValue(byte[] content) {

		content = ByteUtils.endianChange(content);// 大小段转换，转为大端模式 =
													// ByteUtils.endianChange(tBytes);//
													// 大小段转换，转为小端模式
		boolean isNum = bean.getResolving().matches("[0-9]+");// 判断是否是整数
		String bitStr = ByteUtils.binary(content, 2);
		bitStr = StrFormat.addZeroForLeftNum(bitStr, 64);

		int index = 64 - 1 - bean.getStart();
		// if(this.bean.getTitle().equals("手刹信号"))
		// {
		// //System.out.println("");
		// }
		String bitResult = bitStr.substring(index - bean.getLength() + 1, index + 1);

		ByteUtils.bit2byte(bitResult);
		if (!this.bean.getByteOrder()) {
			bitResult = new StringBuffer(bitResult).reverse().toString();
		}
		if (!isNum) {
			float resolving = Float.parseFloat(bean.getResolving());
			int num = ByteUtils.getNum(bean.getResolving());
			bean.setValue(String.valueOf(
					ByteUtils.formatDouble((Long.parseLong(bitResult, 2)) * resolving + bean.getOffset(), num)));

		} else {
			int resolving = Integer.parseInt(bean.getResolving());

			bean.setValue(String.valueOf((Long.parseLong(bitResult, 2)) * resolving + (long) bean.getOffset()));

		}

		//
		// if(this.bean.getCanid().equals("0CFF0209"))
		// {
		// System.out.println("打印");
		// }
		// if (bean.getLength() + bitIndex <= 8) {//判断是否跨字节
		//
		// Byte tByte = content[8-startByteIndex-1];
		// String bitStr = ByteUtils.byte2bitsByIndex(tByte,
		// (byte)(7-bitIndex-bean.getLength()), (byte) bean.getLength());
		// if (!isNum) {
		// int num = ByteUtils.getNum(bean.getResolving());
		// bean.setValue(String.valueOf(ByteUtils.formatDouble(
		// (ByteUtils.bit2byte(bitStr)) * Float.parseFloat(bean.getResolving())
		// + bean.getOffset(), num)));
		// } else {
		// bean.setValue(String.valueOf(
		// (ByteUtils.bit2byte(bitStr)) * Integer.parseInt(bean.getResolving())
		// + (int) bean.getOffset()));
		// }
		//
		// } else {
		//
		//
		//
		//
		//
		// int byteLength = 2;
		//
		// int length = bean.getLength() / 8;
		// if (length >= 1) {
		// byteLength += length - 1;
		// }
		// int lastIndex = bean.getLength() % 8;
		//
		// if (lastIndex + bitIndex > 8) {
		// byteLength++;
		// }
		// if(startByteIndex+byteLength>8)
		// {
		// logger.debug("规则不对，超出范围："+JsonUtils.serialize(bean));
		// return ;
		// }
		// byte[] tBytes = ByteUtils.getSubBytes(content,
		// 8-startByteIndex-byteLength, byteLength);
		//
		//
		// String bitStr = ByteUtils.binary(tBytes, 2);
		// bitStr = StrFormat.addZeroForLeftNum(bitStr, byteLength * 8);
		// String bitResult = bitStr.substring(byteLength * 8-1-bitIndex,
		// bitIndex + bean.getLength());
		// if (!isNum) {
		// float resolving = Float.parseFloat(bean.getResolving());
		// int num = ByteUtils.getNum(bean.getResolving());
		// if (byteLength <= 4) {
		// bean.setValue(String.valueOf(ByteUtils
		// .formatDouble((Integer.parseInt(bitResult, 2)) * resolving +
		// bean.getOffset(), num)));
		// } else {
		// bean.setValue(String.valueOf(ByteUtils
		// .formatDouble((Long.parseLong(bitResult, 2)) * resolving +
		// bean.getOffset(), num)));
		// }
		// } else {
		// int resolving = Integer.parseInt(bean.getResolving());
		// if (byteLength <= 4) {
		// bean.setValue(
		// String.valueOf((Integer.parseInt(bitResult, 2)) * resolving + (int)
		// bean.getOffset()));
		// } else {
		// bean.setValue(String.valueOf((Long.parseLong(bitResult, 2)) *
		// resolving + (long) bean.getOffset()));
		// }
		// }
		// }
		// logger.info(this.bean.getCanid()+this.bean.getTitle()+":"+bean.getStart()+"-"+bean.getLength()+":"+this.bean.getValue()+"-"+ByteUtils.byte2HexStr(content)+"--"+bitStr);
	}

	/**
	 * 将字节数组转为long<br>
	 * 如果input为null,或offset指定的剩余数组长度不足8字节则抛出异常
	 * 
	 * @param input
	 * @param offset
	 *            起始偏移量
	 * @param littleEndian
	 *            输入数组是否小端模式
	 * @return
	 */
	public static long longFrom8Bytes(byte[] input, int offset, boolean littleEndian) {
		if (offset < 0 || offset + 8 > input.length)
			throw new IllegalArgumentException(
					String.format("less than 8 bytes from index %d  is insufficient for long", offset));
		long value = 0;
		for (int count = 0; count < 8; ++count) {
			int shift = (littleEndian ? count : (7 - count)) << 3;
			value |= ((long) 0xff << shift) & ((long) input[offset + count] << shift);
		}
		return value;
	}

}
