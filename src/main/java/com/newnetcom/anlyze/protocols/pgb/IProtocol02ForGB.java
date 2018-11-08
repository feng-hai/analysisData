package com.newnetcom.anlyze.protocols.pgb;

import java.util.List;

import com.newnetcom.anlyze.beans.PairResult;

public interface IProtocol02ForGB {
	
	List<PairResult>  getResults();
	int getDataLength();

}
