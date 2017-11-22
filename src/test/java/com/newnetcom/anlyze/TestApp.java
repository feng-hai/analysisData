package com.newnetcom.anlyze;

import java.util.Date;
import java.util.Map;
import java.util.Timer;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.thread.UpdateHbaseMyTask;
import com.newnetcom.anlyze.thread.UpdateRedisTask;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.anlyze.AnlyzeCans;
import com.newnetcom.anlyze.anlyze.AnlyzeMain;
import com.newnetcom.anlyze.anlyze.db.factory.DatabaseFactory;
import com.newnetcom.anlyze.beans.ProtocolBean;
import com.newnetcom.anlyze.config.PropertyResource;
import com.newnetcom.anlyze.thread.AnlyzeDataTask;
import com.newnetcom.anlyze.thread.CheckCatchTask;
import com.newnetcom.anlyze.thread.DataToKafKaTask;
import com.newnetcom.anlyze.thread.MyTask;
import com.newnetcom.anlyze.thread.RawDataMyTaskRun;

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
		protocol.setFIBER_UNID("EF79482EEFBC42F084FE76A70A588E82");
		protocol.setRAW_OCTETS(
				"7e000000008e037501db1e4232303337380201000220e32d070e7be10161000c001701110b1406391a6f033600002e9101030000280c0101000027ae0100000027de0101000028110100000027df0100000028100100000027cc020a63000028d602021600002926020215000028d2021ce8000027b8021ccf000028de02001a000028e0020026000027a002135f0000277d020200040000279e0204ee000028d40204f10000279c0204ee0000280e0204ee000027aa0102000027e202000000002924015a000027b6020059000027960201f800002798020d05000027c6020001000027ca020000000027460101000028e8021ce8000028cf010000003164011c00003169012400003163014000003168010100003160010100003166012300002cf00200040000291f01000000274801010000274901010000272e0100000028d80200c30000292802ff31000028e602045b000028e301010000272c0101000028e201010000274701010000272d01010000273001000000272b01000000272f0100000028160204ea3c077e");
		protocol.setProto_unid("CD039E17A8E84137AF6DE1CDC172C274");
		//AF27DA9036174426A2E2F7C19A9A959C
		//CD039E17A8E84137AF6DE1CDC172C274
		protocol.setUnid("276D8F32B73946BFA2D3CBEAC0C65EC0");
		protocol.setTIMESTAMP(String.valueOf(new Date().getTime()));
		new AnlyzeMain(protocol).run();

	}
}
