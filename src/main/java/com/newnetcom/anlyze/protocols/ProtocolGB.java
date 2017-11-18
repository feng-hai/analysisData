package com.newnetcom.anlyze.protocols;

import java.util.List;
import java.util.Map;

import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.beans.ResultBean;

import com.newnetcom.anlyze.enums.ContentTypeEnum;


public class ProtocolGB implements IProtocol {

	@Override
	public Map<byte[],byte[]> getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean checkValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentTypeEnum getContentType() {
		// TODO Auto-generated method stub
		return ContentTypeEnum.PCanType;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getFiber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean toResult(List<PairResult> beanList) {
		// TODO Auto-generated method stub
		return null;
	}

}
