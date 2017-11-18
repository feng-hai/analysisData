package com.newnetcom.anlyze.protocols;

import java.util.List;
import java.util.Map;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.beans.ResultBean;

import com.newnetcom.anlyze.enums.ContentTypeEnum;
import com.newnetcom.anlyze.enums.ProtocolTypeEnum;

/**
 * 
 * 项目名称：com.newnetcom.anlyze 类名称：DynamicProtocol 类描述： 创建人：FH 创建时间：2017年10月27日
 * 下午9:36:15
 * 
 * @version
 */
public class DynamicProtocol implements IProtocol {
	/**
	 * @Fields protocol : TODO(用一句话描述这个变量表示什么)
	 */
	private IProtocol protocol;

	/**
	 * @Fields content : TODO(用一句话描述这个变量表示什么)
	 */
	// private byte[] content;
	private String fiber;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param protocolType
	 * @param content
	 */
	public DynamicProtocol(ProtocolTypeEnum protocolType, ProtocolBean protocolBean) {
		this.fiber = protocolBean.getFIBER_UNID();
		// this.content=protocolBean.getContent();

		switch (protocolType) {
		case P3G: {
			protocol = new Protocol3G(protocolBean);
			break;
		}
		case P808: {
			protocol = new Protocol808(protocolBean);
			break;
		}
		case PGB: {
			protocol = new ProtocolGB();
			break;
		}
		}
	}

	/*
	 * (非 Javadoc) <p>Title: getContent</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.protocols.IProtocol#getContent()
	 */
	public Map<byte[], byte[]> getContent() {
		return protocol.getContent();
	}

	/*
	 * (非 Javadoc) <p>Title: checkValidity</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.protocols.IProtocol#checkValidity()
	 */
	@Override
	public Boolean checkValidity() {
		// TODO Auto-generated method stub
		return protocol.checkValidity();
	}

	/*
	 * (非 Javadoc) <p>Title: getContentType</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.protocols.IProtocol#getContentType()
	 */
	@Override
	public ContentTypeEnum getContentType() {
		// TODO Auto-generated method stub
		return protocol.getContentType();
	}

	/*
	 * (非 Javadoc) <p>Title: getKey</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.newnetcom.anlyze.protocols.IProtocol#getKey()
	 */
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return protocol.getKey();
	}

	@Override
	public ResultBean toResult(List<PairResult> beanList) {
		// TODO Auto-generated method stub
		return protocol.toResult(beanList);

	}

	@Override
	public String getFiber() {
		// TODO Auto-generated method stub
		return this.fiber;
	}
}
