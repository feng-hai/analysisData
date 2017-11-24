package com.newnetcom.anlyze.anlyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.anlyze.arithmetic.A2LArithimetic;

//import javax.swing.text.html.parser.Entity;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.newnetcom.anlyze.anlyze.arithmetic.ArithimeticFactory;
//import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
//import com.newnetcom.anlyze.anlyze.db.interfaces.IDatabase;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.publicStaticMap;
//import com.newnetcom.anlyze.beans.RuleBean;
import com.newnetcom.anlyze.protocols.IProtocol;
import com.newnetcom.anlyze.utils.ByteUtils;
//import com.newnetcom.anlyze.utils.ByteUtils;
//import com.newnetcom.anlyze.utils.JsonUtils;
import com.newnetcom.anlyze.utils.JsonUtils;

/**
 * 
 * 项目名称：com.newnetcom.anlyze 类名称：AnlyzeCans 类描述： 创建人：FH 创建时间：2017年11月1日
 * 下午9:32:45
 * 
 * @version
 */
public class AnlyzeCans implements IAnlyze {

	private static final Logger logger = LoggerFactory.getLogger(AnlyzeCans.class);

	private Map<byte[], byte[]> content;
	private IProtocol protocol;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param content
	 * @param inProtocol
	 */
	public AnlyzeCans(Map<byte[], byte[]> content, IProtocol inProtocol) {
		// logger.info(JsonUtils.serialize(content));
		// for (byte[] key : content.keySet()) {
		// logger.info("key:" + ByteUtils.byte2HexStr(key) + "\nvalue:" +
		// ByteUtils.byte2HexStr(content.get(key)));
		// }
		this.protocol = inProtocol;
		this.content = content;
	}

	/*
	 * (非 Javadoc) <p>Title: anlyzeContent</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.anlyze.IAnlyze#anlyzeContent()
	 */
	@Override
	public List<PairResult> anlyzeContent() {
		Boolean isYuebo=false;
		
		String fiberId=this.protocol.getFiber();
		if (publicStaticMap.getFibers().contains(fiberId)) 
		{
			isYuebo=true;
		}
		Map<String,List<Pair>> res=new HashMap<>();
		if(isYuebo)
		{
			res=publicStaticMap.getA2LValues().get(fiberId);
		}else
		{
			res=publicStaticMap.getCans().get(fiberId);
		}
		// logger.info(JsonUtils.serialize(this.content));
		List<PairResult> result = new ArrayList<>();
//System.out.println("-------------------------------------------------");
		for (byte[] key : content.keySet()) {
//			// 根据canid获取解析规则
			
			List<Pair> rules = CanRules.getRuleBeanByCanId(fiberId,isYuebo,res ,key);
			byte[] content=this.content.get(key);
//			// 遍历规则，根据规则查询数据值
			for (Pair bean : rules) {
				//System.out.println(JsonUtils.serialize(bean));
				PairResult result2 = new PairResult();
				try {
					result2.setAlias(bean.getAlias());
					result2.setCode(bean.getCode());
					result2.setTitle(bean.getTitle());
					if (isYuebo) {
						String hx= Integer.toHexString(Integer.parseInt(bean.getPREREQUISITE_VALUE())).toUpperCase();
						bean.setCode("0x"+hx);
					  new A2LArithimetic(bean).setPairValue(content);
					} else {
						ArithimeticFactory.getArithimetic(bean).setPairValue(content);
					}
					result2.setValue(bean.getValue());
					// logger.info(result2.toString());
					result.add(result2);
				} catch (Exception ex) {
					logger.error("解析错误,数据字典id" + this.protocol.getFiber() + "解析规则" + JsonUtils.serialize(bean) + "内容项："
							+ ByteUtils.byte2HexStr(this.content.get(key)), ex);
				}
			}
		}
		return result;
	}
}
