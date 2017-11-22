package com.newnetcom.anlyze.anlyze;

import java.util.List;

//import com.newnetcom.anlyze.beans.RuleBean;
import com.newnetcom.anlyze.beans.PairResult;
import com.newnetcom.anlyze.protocols.IProtocol;

public class DynamicAnlyze implements IAnlyze {
	private IAnlyze anlyze;
	private IProtocol protocol;
	public DynamicAnlyze(IProtocol inProtocol)
	{
		this.protocol=inProtocol;
		switch(inProtocol.getContentType())
		{
		case PCanType:{
			anlyze=new AnlyzeCans(inProtocol.getContent(),this.protocol);
			break;
		}default:{
			anlyze=new AnlyzeProtocolBody(inProtocol.getContent());
		}
		}
	}
	@Override
	public List<PairResult> anlyzeContent() {
		// TODO Auto-generated method stub
		return anlyze.anlyzeContent();
	}
	

}
