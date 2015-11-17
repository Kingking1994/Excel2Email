package com.king.main;

import java.util.List;

public class Doer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    String smtp = "smtp.qq.com";  
	    String from = "king3141592653@foxmail.com ";     
	    String subject = "校园段子手大赛，6000＋奖金，你不拿走谁拿走！";
	    //content
	    String imagePath = "resourse\\image\\boluo.jpg";  
	    String username="king3141592653@foxmail.com";  
	    String password="pi3.141592653";
	    List<String> tos = null;
	    
	    String filename = "resourse\\excel\\qqEmail.xls";
	    //批量发送
		int sheetNum = 5;
		for (int i = 0; i < sheetNum; i++) {
			tos = ExcelReader.getNum(filename, i);
			MailSenter.send(tos, smtp, from, subject, imagePath, username, password);
			tos = null;
		}
	    
	    //一次发送
//	    String to ="929966345@qq.com";
//	    MailSenter.sendImageMail(smtp, from, to, subject, imagePath, username, password);
	    

	}

}
