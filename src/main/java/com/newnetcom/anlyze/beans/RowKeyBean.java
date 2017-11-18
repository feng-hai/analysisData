package com.newnetcom.anlyze.beans;

import org.apache.hadoop.hbase.util.Bytes;

public class RowKeyBean {
	
	
	  private static final byte[] DELIMITER = new byte[] { 45 };// -


	  public static byte[] makeRowKey(String unid, long timestamp)
	  {
	    byte[] b2 = Bytes.toBytes( Long.MAX_VALUE - timestamp );
	    return Bytes.add( fromHexStringToByteArray( unid ), DELIMITER, b2 );
	  }
	  private static byte[] fromHexStringToByteArray(String hex)
	  {
	    hex = hex.replaceAll( " ", "" );

	    if (hex.length() % 2 != 0)
	      hex += "0";

	    byte[] array = new byte[hex.length() / 2];

	    for ( int i = 0; i < array.length; i++ )
	      array[i] = (byte) Integer.parseInt( hex.substring( 2 * i, 2 * i + 2 ), 16 );

	    return array;
	  }

}
