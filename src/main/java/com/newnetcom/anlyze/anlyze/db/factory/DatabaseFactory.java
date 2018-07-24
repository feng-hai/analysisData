package com.newnetcom.anlyze.anlyze.db.factory;

import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.anlyze.db.mysql.MysqlDatabase;
import com.newnetcom.anlyze.anlyze.db.mysql.MysqlDatabaseA2L;
import com.newnetcom.anlyze.anlyze.db.mysql.MysqlDatabaseBig2;
import com.newnetcom.anlyze.anlyze.db.xml.XMLDatabase;

/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：DatabaseFactory   
* 类描述：   从数据库中读取数据（1 xml 2
* 创建人：FH   
* 创建时间：2018年7月23日 下午12:59:29   
* @version        
*/
public class DatabaseFactory {

	public static IDatabase getDB(int dbType, String dataType) {
		IDatabase db = null;
		switch (dbType) {
		case 1: {
			db = new XMLDatabase();
			break;
		}
		case 2: {
			if (dataType.equals("CAN")) {
				db = new MysqlDatabase();// 从数据库中获取
			} else if (dataType.equals("A2L")) {
				db = new MysqlDatabaseA2L();// 从数据库中获取
			}
			break;
		}
		case 3: {
			if (dataType.equals("CAN")) {
				db = new MysqlDatabaseBig2();// 从数据库中获取
			} else if (dataType.equals("A2L")) {
				db = new MysqlDatabaseA2L();// 从数据库中获取
			}
			break;
		}
		}
		return db;
	}

}
