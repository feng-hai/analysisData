package cn.ngsoc.hbase.util;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

enum RandCodeEnum {
	/**
	 * 混合字符�?
	 */
	ALL_CHAR("0123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), // 去除小写的l和o这个两个不容易区分的字符�?
	/**
	 * 字符
	 */
	LETTER_CHAR("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	/**
	 * 小写字母
	 */
	LOWER_CHAR("abcdefghijklmnopqrstuvwxyz"),
	/**
	 * 数字
	 */
	NUMBER_CHAR("0123456789"),
	/**
	 * 大写字符
	 */
	UPPER_CHAR("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	
	/**
	 * Hbase 离散前缀
	 */
	HBASE_CHAR("123456789ABCDEF");
	
	/**
	 * 待生成的字符�?
	 */
	private String charStr;

	private RandCodeEnum(final String charStr) {
		this.charStr = charStr;
	}

	public String generateStr(final int codeLength) {
		final StringBuffer sb = new StringBuffer();
		final Random random = new Random();
		final String sourseStr = getCharStr();

		for (int i = 0; i < codeLength; i++) {
			sb.append(sourseStr.charAt(random.nextInt(sourseStr.length())));
		}

		return sb.toString();
	}

	public String getCharStr() {
		return charStr;
	}
	
	
	public String[] getHbaseKeys(int pNum, int b, boolean only) {
		Set<String> ts = new TreeSet<String>();
		int tss = 0;
		while ((tss = ts.size()) < pNum) {
			if (!only) {
				for (int i = 1; i <= b; i++) {
					ts.add(RandCodeEnum.HBASE_CHAR.generateStr(i));
				}
			} else {
				ts.add(RandCodeEnum.HBASE_CHAR.generateStr(b));
			}
		}
		return ts.toArray(new String[tss]);
	}
	
	public static void main(String[] args) {
		String[] hbaseKeys = RandCodeEnum.HBASE_CHAR.getHbaseKeys(240,2,false);
		for (String s : hbaseKeys) {
			System.out.println(s);
		}
		System.out.println(hbaseKeys.length);
	}
}
