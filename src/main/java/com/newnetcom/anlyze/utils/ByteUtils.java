package com.newnetcom.anlyze.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

/**
 * @author FH
 *
 */
public class ByteUtils {

	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	public static String bytesToHexString(byte[] b) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();

	}

	/**
	 * 日期转字�?
	 * 
	 * @param d
	 * @return
	 * @throws NullPointerException
	 */
	public static byte[] dateToBytes(Date d) throws NullPointerException {
		if (d == null) {
			throw new NullPointerException("Null Date value.");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		byte[] result = new byte[6];
		result[0] = (byte) (c.get(Calendar.YEAR) - 2000);
		result[1] = (byte) (c.get(Calendar.MONTH) + 1);
		result[2] = (byte) c.get(Calendar.DAY_OF_MONTH);
		result[3] = (byte) c.get(Calendar.HOUR_OF_DAY);
		result[4] = (byte) c.get(Calendar.MINUTE);
		result[5] = (byte) c.get(Calendar.SECOND);
		c = null;
		return result;
	}

	/** 
	* @Title: BytesTodate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param d
	* @param @param startIndex
	* @param @return
	* @param @throws NullPointerException    设定文件 
	* @return Date    返回类型 
	* @throws 
	*/
	public static Date BytesTodate(byte[] d, int startIndex) throws NullPointerException {
		if (d == null) {
			throw new NullPointerException("Null Date value.");
		}
		Calendar c = Calendar.getInstance();
		int year = (int) d[startIndex] + 2000;
		int month = (int) d[startIndex + 1]-1;
		int date = (int) d[startIndex + 2];
		int hourOfDay = (int) d[startIndex + 3] + 8;
		int minute = (int) d[startIndex + 4];
		int second = (int) d[startIndex + 5];
		int ms = (int) d[startIndex + 6] / 100;
		c.setTimeInMillis(ms);
		c.set(year, month, date, hourOfDay, minute, second);
		return c.getTime();
	}
	public static Date BytesTodateForTwoByte(byte[] d, int startIndex) throws NullPointerException {
		if (d == null) {
			throw new NullPointerException("Null Date value.");
		}
		Calendar c = Calendar.getInstance();
		int year = (int) d[startIndex] + 2000;
		int month = (int) d[startIndex + 1]-1;
		int date = (int) d[startIndex + 2];
		int hourOfDay = (int) d[startIndex + 3] ;
		int minute = (int) d[startIndex + 4];
		int second = (int) d[startIndex + 5];
		int ms = ByteUtils.getShort(d, 6);
		c.setTimeInMillis(ms);
		c.set(year, month, date, hourOfDay, minute, second);
		return c.getTime();
	}
	public static Date BytesToTimeForHours(byte[]d,int startIndex)
	{
		Calendar c = Calendar.getInstance();
		int hourOfDay = (int) d[startIndex] + 8;
		int minute = (int) d[startIndex + 1];
		int second = (int) d[startIndex + 2];
		int ms = (int) getShort(d,startIndex + 3) / 100;
		c.setTimeInMillis(ms);
		c.set(hourOfDay, minute, second);
		return c.getTime();
	}
	/** 
	* @Title: BytesTodateForSecond 小段模式
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param d
	* @param @param startIndex
	* @param @return
	* @param @throws NullPointerException    设定文件 
	* @return Date    返回类型 
	* @throws 
	*/
	public static Date BytesTodateForSecond(byte[] d, int startIndex) throws NullPointerException {
		if (d == null) {
			throw new NullPointerException("Null Date value.");
		}
		Calendar c = Calendar.getInstance();
		int year = (int) d[startIndex] + 2000;
		int month = (int) d[startIndex + 1]-1;
		int date = (int) d[startIndex + 2];
		int hourOfDay = (int) d[startIndex + 3] + 8;
		int minute = (int) d[startIndex + 4];
		int second = (int) d[startIndex + 5];
		c.set(year, month, date, hourOfDay, minute, second);
		return c.getTime();
	}
	
	/** 
	* @Title: BytesTodateForSecond 小段模式
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param d
	* @param @param startIndex
	* @param @return
	* @param @throws NullPointerException    设定文件 
	* @return Date    返回类型 
	* @throws 
	*/
	public static Date BytesTodateForSecondForNotUTC(byte[] d, int startIndex) throws NullPointerException {
		if (d == null) {
			throw new NullPointerException("Null Date value.");
		}
		Calendar c = Calendar.getInstance();
		int year = (int) d[startIndex] + 2000;
		int month = (int) d[startIndex + 1]-1;
		int date = (int) d[startIndex + 2];
		int hourOfDay = (int) d[startIndex + 3];
		int minute = (int) d[startIndex + 4];
		int second = (int) d[startIndex + 5];
		c.set(year, month, date, hourOfDay, minute, second);
		return c.getTime();
	}
	/**
	 * 转换short为byte
	 * 
	 * @param b
	 * @param s
	 *            �?要转换的short
	 * @param index
	 */
	public static void putShort(byte b[], short s, int index) {
		b[index + 1] = (byte) (s >> 8);
		b[index + 0] = (byte) (s >> 0);
	}

	/**
	 * 大端模式，short转byte[]
	 * 
	 * @param b
	 * @param s
	 * @param index
	 */
	public static void putShortForBig(byte b[], short s, int index) {
		b[index + 0] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	/**
	 * 通过byte数组取到short，小端模式
	 * 
	 * @param b
	 * @param index
	 *            第几位开始取
	 * @return
	 */
	public static int getShort(byte[] b, int index) {
		return (int) (((b[index + 1] << 8) | b[index + 0] & 0xff))&0xffff;
	}

	/**
	 * 通过byte数组取到short 大端模式�?
	 * 
	 * @param b
	 * @param index
	 *            第几位开始取
	 * @return
	 */
	public static int getShortForLarge(byte[] b, int index) {
		return (int) (((b[index + 0] << 8) | b[index + 1] & 0xff))&0xffff;
	}

	/**
	 * 转换int为byte数组
	 * 
	 *
	 * @param x
	 * @param index
	 */
	public static void putInt(byte[] bb, int x, int index) {
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	/**
	 * 通过byte数组取到int
	 * 
	 * 
	 * @param index
	 *            第几位开�?
	 * @return
	 */
	public static long getInt(byte[] bb, int index) {
		return (long) ((((bb[index + 3] & 0xff) << 24) | ((bb[index + 2] & 0xff) << 16) | ((bb[index + 1] & 0xff) << 8)
				| ((bb[index + 0] & 0xff) << 0)))&0xffffffff;
	}
	
	public static long getIntForLarge(byte[] bb, int index) {
		return (long) ((((bb[index + 0] & 0xff) << 24) | ((bb[index + 1] & 0xff) << 16) | ((bb[index + 2] & 0xff) << 8)
				| ((bb[index + 3] & 0xff) << 0)))&0xffffffff;
	}
	
	public static int getThreeByteForLarger(byte[] bb, int index) {
		return (int) (( ((bb[index + 0] & 0xff) << 16) | ((bb[index + 1] & 0xff) << 8)
				| ((bb[index + 2] & 0xff) << 0)))&0xffffff;
	}
	
	 /** 
     * int整数转换为4字节的byte数组 
     *  
     * @param i  整数 
     * @return byte数组 
     */  
    public static byte[] intToByte4(int i) {  
        byte[] targets = new byte[4];  
        targets[3] = (byte) (i & 0xFF);  
        targets[2] = (byte) (i >> 8 & 0xFF);  
        targets[1] = (byte) (i >> 16 & 0xFF);  
        targets[0] = (byte) (i >> 24 & 0xFF);  
        return targets;  
    } 
    /** 
     * long整数转换为8字节的byte数组 
     *  
     * @param lo  long整数 
     * @return byte数组 
     */  
    public static byte[] longToByte8(long lo) {  
        byte[] targets = new byte[8];  
        for (int i = 0; i < 8; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((lo >>> offset) & 0xFF);  
        }  
        return targets;  
    }

	/** 
	* @Title: getThreeByte 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param bb
	* @param @param index
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public static int getThreeByte(byte[] bb, int index) {
		return (int) (( ((bb[index + 2] & 0xff) << 16) | ((bb[index + 1] & 0xff) << 8)
				| ((bb[index + 0] & 0xff) << 0)))&0xffffff;
	}

	/**
	 * 转换long型为byte数组
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 7] = (byte) (x >> 56);
		bb[index + 6] = (byte) (x >> 48);
		bb[index + 5] = (byte) (x >> 40);
		bb[index + 4] = (byte) (x >> 32);
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	/**
	 * 通过byte数组取到long
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 7] & 0xff) << 56) | (((long) bb[index + 6] & 0xff) << 48)
				| (((long) bb[index + 5] & 0xff) << 40) | (((long) bb[index + 4] & 0xff) << 32)
				| (((long) bb[index + 3] & 0xff) << 24) | (((long) bb[index + 2] & 0xff) << 16)
				| (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
	}
	
	/**
	 * 通过byte数组取到long 大端模式
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public static long getLongForLarge(byte[] bb, int index) {
		return ((((long) bb[index + 0] & 0xff) << 56) | (((long) bb[index + 1] & 0xff) << 48)
				| (((long) bb[index +2] & 0xff) << 40) | (((long) bb[index + 3] & 0xff) << 32)
				| (((long) bb[index + 4] & 0xff) << 24) | (((long) bb[index + 5] & 0xff) << 16)
				| (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
	}


	/**
	 * 字符到字节转�?
	 * 
	 * @param ch
	 * 
	 */
	public static void putChar(byte[] bb, char ch, int index) {
		int temp = (int) ch;
		// byte[] b = new byte[2];
		for (int i = 0; i < 2; i++) {
			bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
			temp = temp >> 8; // 向右�?8�?
		}
	}

	/**
	 * 字节到字符转�?
	 * 
	 * @param b
	 * @return
	 */
	public static char getChar(byte[] b, int index) {
		int s = 0;
		if (b[index + 1] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		s *= 256;
		if (b[index + 0] > 0)
			s += b[index + 1];
		else
			s += 256 + b[index + 0];
		char ch = (char) s;
		return ch;
	}

	/**
	 * float转换byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putFloat(byte[] bb, float x, int index) {
		// byte[] b = new byte[4];
		int l = Float.floatToIntBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = new Integer(l).byteValue();
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 * 
	 * @param b
	 * @param index
	 * @return
	 */
	public static float getFloat(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	/**
	 * double转换byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putDouble(byte[] bb, double x, int index) {
		// byte[] b = new byte[8];
		long l = Double.doubleToLongBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = new Long(l).byteValue();
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public static double getDouble(byte[] b, int index) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}

	/**
	 * 字符串转换成十六进制字符�?
	 */

	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * 十六进制转换字符�?
	 */

	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * bytes转换成十六进制字符串
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * bytes转换成十六进制字符串
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;

		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	/**
	 * String的字符串转换成unicode的String
	 */
	public static String str2Unicode(String strText) throws Exception {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128) {
				strRet += "//u" + strHex;
			} else {
				// 低位在前面补00
				strRet += "//u00" + strHex;
			}
		}
		return strRet;
	}

	/**
	 * unicode的String转换成String的字符串
	 */
	public static String unicode2Str(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 高位�?要补�?00再转
			String s1 = s.substring(2, 4) + "00";
			// 低位直接�?
			String s2 = s.substring(4);
			// �?16进制的string转为int
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// 将int转换为字�?
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}

	public static String bytes2Str(byte[] bytes, int start, int lenght) {
		String ss = null;
		if (bytes.length > lenght) {
			byte[] b = new byte[lenght];
			for (int i = 0; i < lenght; i++) {
				b[i] = bytes[i + start];
			}

			try {
				ss = new String(b, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return ss;
	}

	public static byte[] str2bytes(String str) {
		return str.getBytes();
	}

	/**
	 * 获取子字节数�?
	 * 
	 * @param bytes
	 * @param start
	 * @param lenght
	 * @return
	 */
	/**
	 * @param bytes
	 * @param start
	 * @param lenght
	 * @return
	 */
	public static byte[] getSubBytes(byte[] bytes, int start, int lenght) {
		byte[] temp = new byte[lenght];
		for (int i = start; i < start + lenght; i++) {
			temp[i - start] = bytes[i];
		}
		return temp;
	}

	public static byte[] getStrToAsciibytes(String str) {
		byte[] temp = new byte[str.length()];
		for (int i = 0; i < str.length(); i++) {
			temp[i] = (byte) str.charAt(i);
		}
		return temp;
	}

	/**
	 * @param value
	 * @return
	 */
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	public static String bytesToAsciiString(byte[] bytes, int start, int length) {
		String s = null;
		byte[] tempBytes = getSubBytes(bytes, start, length);
		try {
			s = new String(tempBytes, "ascii");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	// 该方法等同于Integer.toBinaryString(b)
	/** 
	* @Title: byte2bits 
	* @Description: 将字节转换为位字符串
	* @param @param b
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String byte2bits(byte b) {
		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}
	
	public static String byte2bitsByIndex(byte b,byte start,byte length) {
//		int z = b;
//		z |= 256;
//		String str = Integer.toBinaryString(z);
//		int len = str.length();
		return byte2bits(b).substring(start, length+start);
	}

	// 将二进制字符串转换回字节
	/** 
	* @Title: bit2byte 
	* @Description: 将二进制字符串转换为字节
	* @param @param bString
	* @param @return    设定文件 
	* @return byte    返回类型 
	* @throws 
	*/
	public static byte bit2byte(String bString) {
		byte result = 0;
		for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
			result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
		}
		return result;
	}
	
	
	public static long bit2long(String bString)
	{
		long result = 0;
		for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
			result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
		}
		return result;
	}
	
	
	/** 
	* @Title: endianChange 
	* @Description: 大小段转换
	* @param @param bytes
	* @param @return    设定文件 
	* @return byte[]    返回类型 
	* @throws 
	*/
	public static byte[] endianChange(byte[] bytes)
	{
		byte[] temp =new byte[bytes.length];
		for(int i=0;i<bytes.length;i++)
		{
			temp[bytes.length-1-i]=bytes[i];
		}
		return temp;
	}
	
	/** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        String str= new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
       // int len = str.length();
		return str;
    } 
    
    
    /** 
    * @Title: formatDouble 
    * @Description:  格式化小数点位数
    * @param @param data 浮点型数据
    * @param @param scale  保存小数点位数
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws 
    */
    public static  String formatDouble(double data,int scale)
    {
    	// int   scale  =   2;//设置位数  
    	  int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
    	  BigDecimal   bd  =   new  BigDecimal(data);  
    	  bd   =  bd.setScale(scale,roundingMode);  
    	  return  bd.toString(); 
    }
    
    /** 
    * @Title: getNum 
    * @Description: 获取小数点位数
    * @param @param data
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws 
    */
    public static int getNum(String data)
    {
         return data.substring(data.indexOf(".")+1).length();
    }
    
  //数据转义
  	/** 
  	* @Title: toEscape 
  	* @Description: 数据转义 
  	* @param @param inContent
  	* @param @return    设定文件 
  	* @return byte[]    返回类型 
  	* @throws 
  	*/
  	public static  byte[] toEscape(byte[]inContent)
  	{	
  		ByteBuffer ioBuffer=ByteBuffer.allocate(inContent.length);
  		for(int i=0;i<inContent.length-1;i++)
  		{
  			if(inContent[i]==(byte)0x7D&&inContent[i+1]==(byte)0x02)
  			{
  				ioBuffer.put((byte)0x7E);
  				i++;
  				continue;
  			}else if(inContent[i]==(byte)0x7D&&inContent[i+1]==(byte)0x01)
  			{
  				ioBuffer.put((byte)0x7D);
  				i++;
  				continue;
  			}else{
  				ioBuffer.put(inContent[i]);
  			}
  		}
  		
  		ioBuffer.put(inContent[inContent.length-1]);
  		ioBuffer.flip(); 
  	    return ioBuffer.array();	
  	}
  	
  	/** 
  	* @Title: getDu 
  	* @Description: TODO(经度纬度度分秒转换为度) 
  	* @param @param bb
  	* @param @param index
  	* @param @return    设定文件 
  	* @return double    返回类型 
  	* @throws 
  	*/
  	public static double getDu(byte[] bb,int index){
  		int du=bb[0+index]&0xff;
  		int fen=(bb[1+index]&0xff);
  		int fenP=getThreeByteForLarger(bb,2+index);
  		double duAll=du+(fen+fenP/1000000.0)/60.0;
  		
		return duAll;
		
	}

}
