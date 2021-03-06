package com.king.test;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class JavaMail3 { 

	private MimeMessage mimeMsg; //MIME邮件对象 
	private Session session; //邮件会话对象 
	private Properties props; //系统属性 
	private boolean needAuth = false; //smtp是否需要认证 
	//smtp认证用户名和密码 
	private String username; 
	private String password; 
	private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象 
	private Authenticator auth;
	/**
	 * Constructor
	 * @param smtp 邮件发送服务器
	 */
	public JavaMail3(String smtp){
		auth =new MyAuthenticator(username,password);
		setSmtpHost(smtp); 
		createMimeMessage(); 
	} 

	/**
	 * 设置邮件发送服务器
	 * @param hostName String 
	 */
	public void setSmtpHost(String hostName) { 
		System.out.println("设置系统属性：mail.smtp.host = "+hostName); 
		if(props == null)
			props = System.getProperties(); //获得系统属性对象 	
		props.put("mail.smtp.host",hostName); //设置SMTP主机 
	} 


	/**
	 * 创建MIME邮件对象  
	 * @return
	 */
	public boolean createMimeMessage() 
	{ 
		try { 
			System.out.println("准备获取邮件会话对象！"); 
			session = Session.getDefaultInstance(props,auth); //获得邮件会话对象 
		} 
		catch(Exception e){ 
			System.err.println("获取邮件会话对象时发生错误！"+e); 
			return false; 
		} 
	
		System.out.println("准备创建MIME邮件对象！"); 
		try { 
			mimeMsg = new MimeMessage(session); //创建MIME邮件对象 
			mp = new MimeMultipart(); 
		
			return true; 
		} catch(Exception e){ 
			System.err.println("创建MIME邮件对象失败！"+e); 
			return false; 
		} 
	} 	
	
	/**
	 * 设置SMTP是否需要验证
	 * @param need
	 */
	public void setNeedAuth(boolean need) { 
		System.out.println("设置smtp身份认证：mail.smtp.auth = "+need); 
		if(props == null) props = System.getProperties(); 
		if(need){ 
			props.put("mail.smtp.auth","true"); 
		}else{ 
			props.put("mail.smtp.auth","false"); 
		} 
	} 

	/**
	 * 设置用户名和密码
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name,String pass) { 
		username = name; 
		password = pass; 
		props.put("username", username);
		props.put("password", password);
	} 

	/**
	 * 设置邮件主题
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) { 
		System.out.println("设置邮件主题！"); 
		try{ 
			mimeMsg.setSubject(mailSubject); 
			return true; 
		} 
		catch(Exception e) { 
			System.err.println("设置邮件主题发生错误！"); 
			return false; 
		} 
	}
	
	/**
	 * 设置邮件正文为图片 
	 * @param imagePath
	 * @return
	 */
	public boolean setBody(String imagePath) { 
		try{
			//3.设置邮件正文
			MimeBodyPart text = new MimeBodyPart();  
	        text.setContent("<br/><img src='cid:xx.jpg'><br/>", "text/html");
	        //4.设置邮件图片
			MimeBodyPart image = new MimeBodyPart();  
	        image.setDataHandler(new DataHandler(new FileDataSource(imagePath)));  //javamail jaf  
	        image.setContentID("xx.jpg");
	
	        mp.addBodyPart(text);
			mp.addBodyPart(image); 
		
			return true; 
		} catch(Exception e){ 
		System.err.println("设置邮件正文时发生错误！"+e); 
		return false; 
		} 
	} 
	
	/** 
	 * 设置发信人
	 * @param from String 
	 */ 
	public boolean setFrom(String from) { 
		System.out.println("设置发信人！"); 
		try{ 
			mimeMsg.setFrom(new InternetAddress(from)); //设置发信人 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 
	} 
	/** 
	 * 设置收信人
	 * @param to String 
	 */ 
	public boolean setTo(String to){ 
		if(to == null)return false; 
		try{ 
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to)); 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 	
	} 
	
	
	/** 
	 * 发送邮件
	 */ 
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public boolean sendOut() 
	{ 
		try{ 
			mimeMsg.setContent(mp); 
			mimeMsg.saveChanges(); 
			System.out.println("正在发送邮件...."); 
			
			
			Session mailSession = Session.getInstance(props,auth); 
			Transport transport = mailSession.getTransport("smtp"); 
			transport.connect((String)props.get("mail.smtp.host"),username,password);
			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
			//下面一行代码会引起空指针异常
			//transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC)); 
			//transport.send(mimeMsg); 
			
			System.out.println("发送邮件成功！"); 
			transport.close(); 
			
			return true; 
		} catch(Exception e) { 
			System.err.println("邮件发送失败！"+e); 
			return false; 
		} 
	} 

	/**
	 * 调用sendOut方法完成邮件发送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendImageMail(String smtp,String from,String to,String subject,String imagePath,String username,String password) {
		JavaMail3 theMail = new JavaMail3(smtp);
		theMail.setNeedAuth(true); //需要验证
		
		if(!theMail.setSubject(subject)) return false;
		if(!theMail.setBody(imagePath)) return false;
		if(!theMail.setTo(to)) return false;
		if(!theMail.setFrom(from)) return false;
		theMail.setNamePass(username,password);
		
		if(!theMail.sendOut()) return false;
		return true;
	}
	
	
	public static void main(String[] args){  
	    String smtp = "smtp.qq.com";  
	    String from = "929966345@qq.com";  
	    String to = "929966345@qq.com";    
	    String subject = "test";
	    String content = "resourse\\image\\boluo.jpg";  
	    String username="929966345@qq.com";  
	    String password="pi3.141592653";  
	    JavaMail3.sendImageMail(smtp, from, to, subject, content, username, password);  
	} 
	
} 

