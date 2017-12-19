package com.newnetcom.anlyze.anlyze.arithmetic;

import java.util.Map;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.config.PropertyResource;

public class ArithimeticFactory {
	private static Map<String, String> config = PropertyResource.getInstance().getProperties();

	public static ILoadData getArithimetic(Pair bean) {
		int dataType = Integer.parseInt(config.get("databaseType"));
		int bitIndex = bean.getStart() % 8;
		int num = bean.getLength() / 8;

		if (bitIndex > 0 || num == 0) {// 判断 是否按位计算
			if (dataType == 3) {
				return new BitArithimeticForBig(bean);
			} else {
				return new BitArithimetic(bean);
			}
		} else {
			if (dataType == 3) {
				return new ByteArithimeticForBig(bean);
			} else {
				return new ByteArithimetic(bean);
			}
		}
	}

}
