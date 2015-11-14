package com.king.main;

import java.util.List;

public class Doer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    String smtp = "smtp.qq.com";  
	    String from = "1041156951@qq.com";     
	    String subject = "test";
	    //content
	    String imagePath = "resourse\\image\\boluo.jpg";  
	    String username="1041156951@qq.com";  
	    String password="pi3.141592653";
	    List<String> tos = null;
	    
	    String filename = "resourse\\excel\\qqEmail.xls";
	    //批量发送
	    tos = ExcelReader.getNum(filename,3);
	    MailSenter.send(tos, smtp, from, subject, imagePath, username, password);
	    //一次发送
//	    String to ="929966345@qq.com";
//	    MailSenter.sendImageMail(smtp, from, to, subject, imagePath, username, password);
	    

	}

}
