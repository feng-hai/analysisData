package com.newnetcom.anlyze.anlyze;

import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.ResultBean;
import com.newnetcom.anlyze.enums.ProtocolTypeEnum;
import com.newnetcom.anlyze.protocols.DynamicProtocol;
import com.newnetcom.anlyze.protocols.IProtocol;

/**
 * 
 * 项目名称：com.newnetcom.anlyze 类名称：AnlyzeMain 类描述： 创建人：FH 创建时间：2017年10月29日
 * 上午9:50:11
 * 
 * @version
 */
public class AnlyzeMain implements Runnable {

	private ProtocolBean protocolBean;

	public AnlyzeMain(ProtocolBean inprotocolBeans) {
		this.protocolBean = inprotocolBeans;
	}

	private ResultBean getResults(ProtocolBean protocolBean) {
		ProtocolTypeEnum protocolType = ProtocolTypeEnum.P3G;

		if (protocolBean.getProto_unid().equals("CD039E17A8E84137AF6DE1CDC172C274")) {
			protocolType = ProtocolTypeEnum.P3G;
		} else if (protocolBean.getProto_unid().equals("AF27DA9036174426A2E2F7C19A9A959C")) {
			protocolType = ProtocolTypeEnum.P808;
			// return null;
		} else if (protocolBean.getProto_unid().equals("EF039E17A8E84137AF6DE1CDC172C274")) {
			protocolType = ProtocolTypeEnum.PGB;
		}
		// System.out.println("解析开始");
		// 分析协议头部和协议内容
		// long temp=System.currentTimeMillis();
		IProtocol protocolD = new DynamicProtocol(protocolType, protocolBean);
		// System.out.println("解析头部文件需要时间："+(System.currentTimeMillis()-temp));
		// System.out.println("解析开始01");
		DynamicAnlyze anlyze = new DynamicAnlyze(protocolD);
		// System.out.println("解析文件需要时间："+(System.currentTimeMillis()-temp));
		// System.out.println("解析开始02");
		// anlyze.anlyzeContent();
		return protocolD.toResult(anlyze.anlyzeContent());
		// return null;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.getResults(this.protocolBean);
	}

}
