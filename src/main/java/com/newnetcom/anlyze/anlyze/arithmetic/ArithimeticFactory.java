package com.newnetcom.anlyze.anlyze.arithmetic;

import com.newnetcom.anlyze.beans.Pair;

public class ArithimeticFactory {
	
	public static ILoadData getArithimetic(Pair bean)
	{
		int bitIndex = bean.getStart() % 8;
		int num = bean.getLength() / 8;
		if (bitIndex > 0 || num == 0) {// 判断 是否按位计算
			return new BitArithimeticForBig(bean);
		} else {
			return new ByteArithimeticForBig(bean);
		}
	}

}
