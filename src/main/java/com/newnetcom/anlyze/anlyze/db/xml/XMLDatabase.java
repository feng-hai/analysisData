package com.newnetcom.anlyze.anlyze.db.xml;

import java.util.List;
import java.util.Map;

import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.anlyze.db.xml.utils.XMLReaderUtils;
import com.newnetcom.anlyze.beans.Pair;


public class XMLDatabase implements IDatabase {

	@Override
	public Map<String, List<Pair>> getRules() {
		return XMLReaderUtils.readerToCatch();	 
	}

}
