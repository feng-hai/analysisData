package com.newnetcom.anlyze;

import java.util.Date;
import java.util.Map;
//import java.util.Timer;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.newnetcom.anlyze.thread.UpdateHbaseMyTask;
//import com.newnetcom.anlyze.thread.UpdateRedisTask;
//import com.newnetcom.anlyze.utils.ByteUtils;
//import com.newnetcom.anlyze.anlyze.AnlyzeCans;
import com.newnetcom.anlyze.anlyze.AnlyzeMain;
import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.config.PropertyResource;
//import com.newnetcom.anlyze.thread.AnlyzeDataTask;
//import com.newnetcom.anlyze.thread.CheckCatchTask;
//import com.newnetcom.anlyze.thread.DataToKafKaTask;
//import com.newnetcom.anlyze.thread.MyTask;
//import com.newnetcom.anlyze.thread.RawDataMyTaskRun;

public class TestApp {
	// private static final Logger logger =
	// LoggerFactory.getLogger(TestApp.class);

	public static void main(String[] args) {
		// ByteUtils.toEscape(ByteUtils.hexStr2Bytes("7E00000000E802C001FE4342313039363902010000262D000028F03B07B2B43C0228002D004D01110B0E022F3400C01C00007B21FF18007E"));
		// logger.error("tet");
		// TODO Auto-generated method stub
		// UpdateHbaseMyTask sendData = new UpdateHbaseMyTask();
		// sendData.setDaemon(true);
		// sendData.start();
		// AnlyzeDataTask sendData1 = new AnlyzeDataTask();
		// sendData1.setDaemon(true);
		// sendData1.start();
		// UpdateRedisTask sendDataRedis = new UpdateRedisTask();
		// sendDataRedis.setDaemon(true);
		// sendDataRedis.start();
		//
		// RawDataMyTaskRun sendData2 = new RawDataMyTaskRun();
		// sendData2.setDaemon(true);
		// sendData2.start();
		//
		// DataToKafKaTask sendDataTask = new DataToKafKaTask();
		// sendDataTask.setDaemon(true);
		// sendDataTask.start();
		//
		// Timer timer = new Timer();
		// timer.schedule(new CheckCatchTask(), new Date(), 10000);
		Map<String,String> config=	PropertyResource.getInstance().getProperties();
		DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")),"A2L");//1获取配置文件的分析类
		DatabaseFactory.getDB(Integer.parseInt(config.get("databaseType")),"CAN");
		ProtocolBean protocol = new ProtocolBean();
		protocol.setFIBER_UNID("6C6D6C343DA748FF8B34DF9FFD734E48");
		protocol.setRAW_OCTETS(
				"7e00000000e8022a01bd4342333039353102010000bc430000e7f33c070805dc012b00feff7a00110b16073b1b00c01200001e9fff1800dc9696c8e5c77bfd0000f301ff1800e5ff0cf70c3f3b650000f302ff1800fd18c87ce8fdc8960000f303ff18008d2003280103870a0000f304ff1800102000004c84f1ff0000f300151c0035a6a6a6a6a6a6a60000f300161c00093d3e3d3d3c3c3d0000273fff0c0041006d0000a4fd000000038fff180095901b847171000f0000038dff180040000016ffffffff0000038eff18003fc67bff7f7d017f7d0100000390ff18003f62ffffffffffff0000a701ff18003f62003f3e330200000028d03818000000b2b702000000000028d13818006e0b52b1f90100000000d0280a0c008202000001007bc60000ef30200c00f0000f0200000000000030ef101800d41b005b02000000028d047e");
		protocol.setProto_unid("CD039E17A8E84137AF6DE1CDC172C274");
		//AF27DA9036174426A2E2F7C19A9A959C
		//CD039E17A8E84137AF6DE1CDC172C274
		protocol.setUnid("276D8F32B73946BFA2D3CBEAC0C65EC0");
		protocol.setTIMESTAMP(String.valueOf(new Date().getTime()));
		new AnlyzeMain(protocol).run();

	}
}
