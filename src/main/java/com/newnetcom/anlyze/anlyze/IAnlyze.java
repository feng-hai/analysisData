package com.newnetcom.anlyze.anlyze;

import java.util.List;

//import com.newnetcom.anlyze.beans.RuleBean;
import com.newnetcom.anlyze.beans.PairResult;

/**   
*    
* 项目名称：com.newnetcom.anlyze   
* 类名称：IAnlyze   
* 类描述：   
* 创建人：FH   
* 创建时间：2017年10月27日 下午9:55:56   
* @version        
*/
public interface IAnlyze {
	  
      /** 
    * @Title: anlyzeContent 
    * @Description: 分析Can内容
    * @param @return    设定文件 
    * @return List<RuleBean>    返回类型 
    * @throws 
    */
    List<PairResult> anlyzeContent();
}
