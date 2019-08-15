package api.java.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * 
 * 在实际生产环境直接使用JavaMail是不方便的，还需要对JavaMail进行封装
 * 使用发邮件工具的人，只要知道提供收件人、邮件主题、邮件内容就可以了
 * @author Dev-005
 *
 */
public class SendMailText {
	/** 发件人地址*/
	public static String senderAddress = "mrxue9527@sina.com";
	/** 收件人地址*/
	public static String recipientAddress = "295077145@qq.com";
	/** 发件人账户名*/
	public static String senderAccount = "mrxue9527";
	/** 发件人账户密码*/
	public static String senderPassword = "6864479215d30a65";
	
	public static void main(String[] args) throws Exception {
		// 1.连接邮件服务器的参数配置
		Properties props = new Properties();
		// 设置用户的认证方式
		props.setProperty("mail.smtp.auth", "true");
		// 设置传输协议
		props.setProperty("mail.transport.protocol", "smtp");
		// 设置发件人的SMTP服务器地址
		props.setProperty("mail.smtp.host", "smtp.sina.com.cn");
		
		// 2.创建定义整个应用程序所需的环境信息的Session对象
		Session session = Session.getInstance(props);
		// 设置调试信息在控制台打印出来
		session.setDebug(true);
		
		// 3.创建邮件的实例对象
		Message msg = getMimeMessage(session);
		
		// 4.根据session对象获取邮件传输对象Transport
		Transport transport = session.getTransport();
		// 设置发件人账户名和密码
		transport.connect(senderAccount, senderPassword);
		// 发送邮件
		transport.sendMessage(msg, msg.getAllRecipients());
		
		// 5.关闭邮件连接
		transport.close();
	}
	/**
	 * 获得创建一封邮件的实例对象
	 */
	public static MimeMessage getMimeMessage(Session session) throws Exception {
		// 创建一封邮件的实例对象
		MimeMessage msg = new MimeMessage(session);
		// 设置发件人地址
		msg.setFrom(senderAddress);
		/*
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
		msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
		msg.setSubject("邮件主题", "utf-8");
		msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");
		// 设置邮件的发送时间，默认立即发送
		msg.setSentDate(new Date());
		
		return msg;
	}
}
