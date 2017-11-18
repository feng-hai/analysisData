package com.newnetcom.anlyze.protocols.p808.extra;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.newnetcom.anlyze.beans.Pair;
import com.newnetcom.anlyze.utils.ByteUtils;
import com.newnetcom.anlyze.utils.StrFormat;

public class Protocol0200Extra {
	private byte[] content;
	private static Logger logger = LoggerFactory.getLogger(Protocol0200Extra.class);

	public Protocol0200Extra(byte[] incontent) {
		this.content = incontent;
		// this.content = incontent;
		// this.charContents =
		// StrFormat.addZeroForNum(ByteUtils.binary(this.content, 2), 32);
		// this.statusPairs = Read808Config.getVehicleStatus();
		this.anlysis(0,0);
	}

	/**
	 * @Fields alarmPairs : TODO(用一句话描述这个变量表示什么)
	 */
	private List<Pair> extraPairs = new ArrayList<>();

	/**
	 * @Title: getAlarmPairs @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 *         设定文件 @return List<Pair> 返回类型 @throws
	 */
	public List<Pair> getExtra() {
		return extraPairs;
	}

	/**
	 * @Title: anlysis @Description: TODO(这里用一句话描述这个方法的作用) @param 设定文件 @return
	 *         void 返回类型 @throws
	 */
	private void anlysis(int index,int lenghtE) {
		try {
			byte byteIndex = this.content[index];
			byte lenght = this.content[index + 1];
			lenghtE+=lenght+2;
			byte[] contents = ByteUtils.getSubBytes(this.content, index + 2, lenght);
			Pair pair = new Pair();
			switch (byteIndex) {
			case 1:
				pair.setTitle("里程，DWORD，1/10km，对应车上里程表读数");
				pair.setValue(String.valueOf(ByteUtils.getIntForLarge(contents, 0)));
				extraPairs.add(pair);
				break;
			case 2:
				pair.setTitle("油量，WORD，1/10L，对应车上油量表读数");
				pair.setValue(String.valueOf(ByteUtils.getShortForLarge(contents, 0)));
				extraPairs.add(pair);
				break;
			case 3:
				pair.setTitle("行驶记录功能获取的速度，WORD，1/10km/h");
				pair.setValue(String.valueOf(ByteUtils.getShortForLarge(contents, 0)));
				extraPairs.add(pair);
				break;
			case 4:
				pair.setTitle("需要人工确认报警事件的 ID，WORD，从 1 开始计数");
				pair.setValue(String.valueOf(ByteUtils.getShortForLarge(contents, 0)));
				extraPairs.add(pair);
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 0x10:
				break;
			case 0x11:
				byte temp = contents[0];
				if (temp == 0) {

				}
				break;
			case 0x12:
				break;
			case 0x13:
				break;
			case 0x14:
				break;
			case 0x15:
				break;
			case 0x16:
				break;
			case 0x17:
				break;
			case 0x18:
				break;
			case 0x19:
				break;
			case 0x20:
				break;
			case 0x21:
				break;
			case 0x22:
				break;
			case 0x25:
				extraPairs.addAll(new Protocol0200ExtraStatus(content).getStatus());
				break;
			case 0x2A:
				char[] tempchars = StrFormat.addZeroForNum(ByteUtils.binary(contents, 2), 32).toCharArray();
				pair.setTitle("深度休眠状态");
				pair.setValue(String.valueOf(tempchars[0]));
				extraPairs.add(pair);
				Pair pair2 = new Pair();
				pair2.setTitle("休眠状态");
				pair2.setValue(String.valueOf(tempchars[1]));
				extraPairs.add(pair2);
				break;
			case 0x2B:
				break;
			case 0x30:
				break;
			case 0x31:
				break;
			}
			if (this.content.length - lenghtE - 2 > 2) {
				anlysis(index + 2 + lenght,lenghtE);
			}
		} catch (Exception ex) {
			logger.error("808附加信息错误", ex);
		}

	}

}
