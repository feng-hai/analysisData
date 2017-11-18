package com.newnetcom.anlyze.protocols;

import java.util.List;
import java.util.Map;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.enums.ContentTypeEnum;

/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：IProtocol   
* 类描述：   
* 创建人：FH   
* 创建时间：2017年10月28日 下午5:22:45   
* @version        
*/
public interface IProtocol {
	
	/** 
	* @Title: getContent 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Map<byte[],byte[]>    返回类型 
	* @throws 
	*/
	Map<byte[],byte[]> getContent();
	
	//RuleBean getResults();
	/** 
	* @Title: toResult 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param beanList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	ResultBean toResult(List<PairResult> beanList);
	/** 
	* @Title: checkValidity 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	Boolean checkValidity();
	
	/** 
	* @Title: getContentType 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return ContentTypeEnum    返回类型 
	* @throws 
	*/
	ContentTypeEnum getContentType();
	
	/** 
	* @Title: getKey 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getKey();
	
	/** 
	* @Title: getFiber 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getFiber();

}
