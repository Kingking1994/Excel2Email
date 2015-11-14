package com.king.test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaMail {
	private String host = null;
	private String from = null;
	private String username = null;
	private String password = null;
	private String subject = null;
	private String content = null;

	public JavaMail() {
		host = null;
		from = null;
		username = null;
		password = null;
		subject = null;
		content = null;
	}

	public void setHost(String str)// 设置邮件服务器。
	{
		this.host = str;
	}

	public String getHost() {
		return this.host;
	}

	public void setFrom(String str)// 设置发送地址
	{
		this.from = str;
	}

	public String getFrom() {
		return this.from;
	}

	public void setUsername(String str)// 设置登陆名
	{
		this.username = str;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String str)// 设置登陆密码
	{
		this.password = str;
	}

	public String getPassword() {
		return this.password;
	}

	public void setSubject(String str)// 设置主题
	{
		this.subject = str;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setContent(String str)// 设置内容
	{
		this.content = str;
	}

	public String getContent() {
		return this.content;
	}

	public String[] getTo(String str)// 获取多发邮件的地址，每2个邮件用逗号隔开。
	{
		char temp[] = str.toCharArray();
		int num = 0;
		if (str != null && !str.equals(""))
			num = 1;
		else
			return null;
		for (int i = 0; i < temp.length; i++)// 确定一共有多少个邮件地址
		{
			if (temp[i] == ',')
				num++;
		}
		System.out.println(num);
		String[] To = new String[num];
		To[0] = "";
		int j = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == ',') {
				j = j + 1;
				i = i + 1;
				To[j] = "";
			}
			To[j] += temp[i];
		}
		return To;
	}

	public boolean SendMailto(String tto)// 发送邮件。
	{
		try {
			// Get system properties
			Properties props = new Properties();
			// Setup mail server
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true"); // 这样才能通过验证
			// Get session
			Session session = Session.getDefaultInstance(props);

			// watch the mail commands go by to the mail server
			session.setDebug(true);

			// Define message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			String to[] = getTo(tto);
			for (int i = 0; i < to.length; i++) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to[i]));
			}
			message.setSubject(this.subject);
			message.setText(this.content);
			// Send message
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			// close transport
			transport.close();
			return true;
		} catch (MessagingException m) {
			System.out.println(m.toString());
			return false;
		}
	}

	public static void main(String[] args) {
		JavaMail mail = new JavaMail();
		mail.setContent("This is a test!");
		mail.setFrom("kingking1995@yeah.net");
		mail.setHost("smtp.yeah.net");
		mail.setUsername("kingking1995");
		mail.setSubject("Test");
		mail.setPassword("king929966345");
		mail.SendMailto("kingking1995@yeah.net");
	}

}
