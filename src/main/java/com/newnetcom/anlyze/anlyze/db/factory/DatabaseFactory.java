package com.newnetcom.anlyze.anlyze.db.factory;

import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.anlyze.db.mysql.MysqlDatabase;
import com.newnetcom.anlyze.anlyze.db.xml.XMLDatabase;

public class DatabaseFactory {

	public static IDatabase getDB(int dbType) {
		IDatabase db = null;
		switch (dbType) {
		case 1:
			db = new XMLDatabase();
			break;
		case 2:
			db=new MysqlDatabase();//从数据库中获取
			break;
		}
		return db;
	}

}
