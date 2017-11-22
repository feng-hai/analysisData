package com.newnetcom.anlyze.anlyze.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.newnetcom.anlyze.anlyze.AnlyzeCans;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.utils.ByteUtils;

public class A2LArithimetic implements ILoadData {
	private Pair bean;
	private static final Logger logger = LoggerFactory.getLogger(A2LArithimetic.class);

	public A2LArithimetic(Pair inbean) {
		this.bean = inbean;
	}

	@Override
	public void setPairValue(byte[] content) {

		int num = content.length;
		boolean isNum = bean.getResolving().matches("[0-9]+");
		if (!isNum) {
			float resolving = Float.parseFloat(bean.getResolving());
			int nums = ByteUtils.getNum(bean.getResolving());
			switch (num) {
			case 1:
				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(
							String.valueOf(ByteUtils.formatDouble((content[0]) * resolving + bean.getOffset(), nums)));
				} else {
					bean.setValue(String
							.valueOf(ByteUtils.formatDouble((content[0] & 0xff) * resolving + bean.getOffset(), nums)));

				}
				break;
			case 2:
				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(String.valueOf(ByteUtils.formatDouble(
							(ByteUtils.getShortForLarge(content, 0)) * resolving + bean.getOffset(), nums)));
				} else {
					bean.setValue(String.valueOf(ByteUtils.formatDouble(
							(ByteUtils.getShortForLarge(content, 0) & 0xffff) * resolving + bean.getOffset(), nums)));
				}
				break;
			case 3:
				bean.setValue(String.valueOf(ByteUtils
						.formatDouble((ByteUtils.getThreeByte(content, 0)) * resolving + bean.getOffset(), nums)));
				break;
			case 4:
				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(String.valueOf(ByteUtils.formatDouble(
							(ByteUtils.getIntForLarge(content, 0)) * resolving + bean.getOffset(), nums)));
				} else {
					bean.setValue(String.valueOf(ByteUtils.formatDouble(
							(ByteUtils.getIntForLarge(content, 0) & 0xffffffff) * resolving + bean.getOffset(), nums)));
				}
				break;
			}
		} else {
			int resolving = Integer.parseInt(bean.getResolving());
			switch (num) {
			case 1:
				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(String.valueOf((content[0]) * resolving + (int) bean.getOffset()));
				} else {
					bean.setValue(String.valueOf((content[0] & 0xff) * resolving + (int) bean.getOffset()));
				}
				break;
			case 2:
				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(String
							.valueOf((ByteUtils.getShortForLarge(content, 0)) * resolving + (int) bean.getOffset()));
				} else {
					bean.setValue(String.valueOf(
							(ByteUtils.getShortForLarge(content, 0) & 0xffff) * resolving + (int) bean.getOffset()));
				}
				break;
			case 3:

				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(
							String.valueOf((ByteUtils.getThreeByte(content, 0)) * resolving + (int) bean.getOffset()));
				} else {
					bean.setValue(
							String.valueOf((ByteUtils.getThreeByte(content, 0)) * resolving + (int) bean.getOffset()));
				}
				break;
			case 4:
				if (bean.getALGORITHM().equals("13")) {
					bean.setValue(String
							.valueOf((ByteUtils.getIntForLarge(content, 0)) * resolving + (int) bean.getOffset()));
				} else {
					bean.setValue(String.valueOf(
							(ByteUtils.getIntForLarge(content, 0) & 0xffffffff) * resolving + (int) bean.getOffset()));
				}
				break;
			}
		}

		// logger.info(this.bean.getPREREQUISITE_VALUE()+this.bean.getTitle()+":"+this.bean.getValue()+"-"+bean.getProtocolId()+":"+ByteUtils.byte2HexStr(content));
	}
}
