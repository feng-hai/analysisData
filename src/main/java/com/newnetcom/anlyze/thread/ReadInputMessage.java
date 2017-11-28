package com.newnetcom.anlyze.thread;
import java.util.Scanner;
import com.newnetcom.anlyze.beans.publicStaticMap;
public class ReadInputMessage extends Thread {
	@SuppressWarnings("resource")
	@Override
	public void run() {
		while (true) {
			// 读取输入内容
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入你要监控的内容，如需帮助请输入help：");
			String inputStr = sc.next();
			switch (inputStr) {
			case "help": {
				System.out.println("stop:停止从kafka中获取数据");
				System.out.println("start:开始从kafka中获取数据");
				System.out.println("r:重新加载解析规则");
				break;
			}
			case "stop": {
				System.out.println("关闭通道-kakfa");
				publicStaticMap.stopTag=true;
				break;
			}
			case "start": {
				System.out.println("打开通道-kakfa");
				publicStaticMap.stopTag=false;
				break;
			}
			case "r": {
				publicStaticMap.reloadData=true;
				break;
			}case "startLog":{
				System.out.println("打开日志");
				publicStaticMap.logStatus=true;
				break;
			}case "endLog":{
				System.out.println("关闭日志");
				publicStaticMap.logStatus=false;
				break;
			}
			default:
				System.out.println("stop:停止从kafka中获取数据");
				System.out.println("start:开始从kafka中获取数据");
				System.out.println("r:重新加载解析规则");
				System.out.println("startLog:开启日志");
				System.out.println("endLog:关闭日志");
				break;
			}
		}
	}

}
