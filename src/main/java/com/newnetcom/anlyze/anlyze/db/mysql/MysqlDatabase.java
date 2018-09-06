package com.newnetcom.anlyze.anlyze.db.mysql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.anlyze.db.mysql.utils.JdbcUtils;
import com.newnetcom.anlyze.anlyze.db.mysql.utils.SingletonJDBC;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.publicStaticMap;
import com.newnetcom.anlyze.utils.ByteUtils;
public class MysqlDatabase implements IDatabase {

	private static final Logger logger = LoggerFactory.getLogger(MysqlDatabase.class);

	
	public MysqlDatabase()
	{
		getRules();
	}
	/*
	 * (非 Javadoc) <p>Title: getRules</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.db.interfaces.IDatabase#getRules()
	 */
	@Override
	public Map<String,Map<String,List<Pair>>> getRules() {
		// TODO Auto-generated method stub
		// 数据字典和协议族之间的对应关系，

		// 获取全部协议族
		// 数据字典和对应协议族集合
		// 获取全部协议

		JdbcUtils jdbcUtils = SingletonJDBC.getJDBC();
		jdbcUtils.getConnection();

		List<Object> params = new ArrayList<Object>();
		// params.add("90A62ABEA6DB415D93D17DD31FBD5A1B");
		// List<VehicleInfo> list = null;
		// try {
		// List<Map<String, Object>> resultsForProtocol =
		// jdbcUtils.findModeResult(sqlProtocol, params);
		// logger.info(JsonUtils.serialize(resultsForProtocol));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		
		//查询数据字典和协议族的根的关系
		List<Map<String, Object>> resultsForfiberandfamily = null;
		String sqlProtocolFamilyandFiber = "select * from (SELECT f.unid fiberId, fs.unid familyId  FROM cube.BIG_FIBER f"
				+ " inner join cube.BIG_FIBER_PROTOCOL_FAMILY_MAP fm on f.unid=fm.FIBER_UNID and f.FLAG_DEL=0 and fm.FLAG_DEL=0 "
				+ " inner join cube.PDA_PROTOCOL_FAMILY fs on fm.PROTO_FAMILY_UNID =fs.unid and fs.FLAG_DEL=0 )b";
		try {
			resultsForfiberandfamily = jdbcUtils.findModeResult(sqlProtocolFamilyandFiber, params);
			// logger.info(JsonUtils.serialize(resultsForfiberandfamily));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询数据库错误",e);
		}

		
		//所有协议族数据
		String sqlFamilys = "SELECT fm.unid,fm.super_proto_family_unid,pm.PROTO_UNID  FROM cube.PDA_PROTOCOL_FAMILY fm "
				+ "left join cube.PDA_PFP_MAP pm on fm.unid=pm.PROTO_FAMILY_UNID and pm.FLAG_DEL=0 where fm.FLAG_DEL=0";
		List<Map<String, Object>> resultsfamily = null;
		try {
			resultsfamily = jdbcUtils.findModeResult(sqlFamilys, params);
			// logger.info(JsonUtils.serialize(resultsfamily));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询数据库错误",e);
		}

		
		
		//所有协议项数据
		String protocolSql = "select * from (SELECT p.PREREQUISITE_HEX ,f.BYTE_ORDER, f.ALIAS,f.CODE,f.BIT_LENGTH,f.OFFSET, CAST(f.WEIGHT AS CHAR(8)) WEIGHT ,f.BIT_OFFSET,f.TITLE,f.INX,f.PROTO_UNID,f.PREREQUISITE_VALUE FROM cube.PDA_FIELD  f inner join cube.PDA_PFP_MAP p on p.PROTO_UNID=f.proto_unid and f.FLAG_DEL=0 and p.FLAG_DEL =0)d  ";

		List<Map<String, Object>> resultsProtocol = null;
		try {
			resultsProtocol = jdbcUtils.findModeResult(protocolSql, params);
			// logger.info(JsonUtils.serialize(resultsProtocol));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询数据库错误",e);
		}

		
		//更加数据字典和协议族，遍历数据字典对应的所有协议族谢协议
		for (Map<String, Object> entity : resultsForfiberandfamily) {
			Object fiberid = entity.get("fiberId");
			Object familyId = entity.get("familyId");
			if (fiberid != null && familyId != null) {
				familys(familyId.toString(), resultsfamily, fiberid.toString());
			}
		}
		
		
		
		//对所有的协议进行整理，整理协议对应的协议项内容
		List<Pair> pairs = new ArrayList<>();
		Map<String, List<Pair>> protocolList = new HashMap<>();// 协议id,解析规则集合
		for (Map<String, Object> entity : resultsProtocol) {
			Pair pair = new Pair();
			pair.setAlias(entity.get("ALIAS").toString());
			pair.setCode(entity.get("CODE").toString());
			pair.setByteOrder(entity.get("BYTE_ORDER").toString().equals("true"));
			if (!entity.get("BIT_LENGTH").toString().isEmpty()
					&& entity.get("BIT_LENGTH").toString().matches("[0-9]+")) {
				pair.setLength(Integer.parseInt(entity.get("BIT_LENGTH").toString()));
			}
			// String reg ="^[\\-\\+]?\\d+?$" ; &&
			// entity.get("OFFSET").toString().matches(reg)
			if (!entity.get("OFFSET").toString().isEmpty()) {
				pair.setOffset(Double.parseDouble(entity.get("OFFSET").toString()));
			}
			pair.setResolving(entity.get("WEIGHT").toString());
			if (!entity.get("BIT_OFFSET").toString().isEmpty()
					&& entity.get("BIT_OFFSET").toString().matches("-?\\d+")) {
				pair.setStart(Integer.parseInt(entity.get("BIT_OFFSET").toString()));
			}
			pair.setTitle(entity.get("TITLE").toString());
			pair.setCanid(entity.get("PREREQUISITE_HEX").toString());
			if (!entity.get("INX").toString().isEmpty() && entity.get("INX").toString().matches("[0-9]+")) {
				pair.setInx(Integer.parseInt(entity.get("INX").toString()));
			}
			
			
			pair.setProtocolId(entity.get("PROTO_UNID").toString());
			if (protocolList.containsKey(pair.getProtocolId())) {
				pairs = protocolList.get(pair.getProtocolId());
				pairs.add(pair);
				protocolList.put(pair.getProtocolId(), pairs);
			} else {
				pairs = new ArrayList<>();
				pairs.add(pair);
				protocolList.put(pair.getProtocolId(), pairs);
			}
		}
		
		//记录数据字典和Can针 ，对应的数据解析方式
		Map<String, Map<String,List<Pair>>> resultsMap = new HashMap<>();
		for (String key : fiberProtocol.keySet()) {// key 是数据字典id
			List<String> tempProtocols = fiberProtocol.get(key);
			Map<String,List<Pair> > res=new HashMap<>();
			for (String protocol : tempProtocols) {
				List<Pair> pairs2 = protocolList.get(protocol);
				if (pairs2 != null && pairs2.size() > 0) {
					Collections.sort(pairs2);
					int inxTemp = 0;
					String canId = pairs2.get(0).getCanid();
					
					if(canId.equals("0CFF0109"))
					{
						System.out.println(canId+":"+pairs2.get(0).getTitle());
					}
					List<Pair> pairsTemp=new ArrayList<>(); 
					for (Pair pair : pairs2) {
						Pair temp=pair.clone();
						temp.setStart(inxTemp + pair.getStart());
						inxTemp = temp.getStart() + pair.getLength();
						pairsTemp.add(temp);
						//System.out.println(JsonUtils.serialize(temp));
					}
					byte[] temp = ByteUtils.hexStr2Bytes(canId);
					temp = ByteUtils.endianChange(temp);
					String canStr = ByteUtils.byte2HexStr(temp);
					
					if (!canStr.isEmpty() && !canStr.equals("00")) {
						if (!resultsMap.containsKey(canStr)) {
							res.put(canStr, pairsTemp);
						}
					}
				
				}
			}
			
			resultsMap.put(key, res);
		}
		if(resultsMap.size()>0)
		{
		publicStaticMap.setCans(resultsMap);
		}
		return resultsMap;
	}

	List<Map<String, Object>> temp = new ArrayList<>();
	Map<String, List<String>> fiberProtocol = new HashMap<>();

	private  void familys(String parentId, List<Map<String, Object>> familys, String fiberId) {
		for (Map<String, Object> entity : familys) {
			Object supers = entity.get("SUPER_PROTO_FAMILY_UNID");
			Object familyId = entity.get("UNID");
			Object protocolId = entity.get("PROTO_UNID");
			if (supers != null && familyId != null && supers.toString().equals(parentId)) {
				List<String> protocols;
				if (protocolId != null) {
					if (fiberProtocol.containsKey(fiberId)) {
						protocols = fiberProtocol.get(fiberId);
						if (!protocols.contains(protocolId.toString())) {
							protocols.add(protocolId.toString());
						}

					} else {
						protocols = new ArrayList<>();
						protocols.add(protocolId.toString());
					}
					fiberProtocol.put(fiberId, protocols);
				}
				familys(familyId.toString(), familys, fiberId);
			}
		}
	}

}
