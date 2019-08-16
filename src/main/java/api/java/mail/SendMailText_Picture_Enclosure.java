package api.java.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMailText_Picture_Enclosure {
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
		// 1.创建一封邮件的实例对象
		MimeMessage msg = new MimeMessage(session);
		// 2.设置发件人地址
		msg.setFrom(senderAddress);
		/*
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
		msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
		msg.setSubject("邮件主题(包含图片和附件)", "utf-8");
		msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");
		
		// 5.创建图片“节点”
		MimeBodyPart image = new MimeBodyPart();
		// 读取本地文件
		DataHandler dh = new DataHandler(new FileDataSource("./Relaitonship-Circle.jpeg"));
		image.setDataHandler(dh);
		image.setContentID("Relaitonship-Circle");
		
		// 6.创建文本“节点”
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("这是一张图片<br/><a href='http://www.cnblogs.com/ysocean/p/7666061.html'><img src='cid:Relaitonship-Circle'/></a>", "text/html;charset=UTF-8");
		
		// 7.将文本和图片混合成一个节点
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(text);
		mm_text_image.addBodyPart(image);
		mm_text_image.setSubType("related");
		
		// 8.将 文本 + 图片 的混合“节点”封装成一个普通“节点”
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);
		
		// 9.创建附件节点
		MimeBodyPart attachment = new MimeBodyPart();
		// 读取本地文件
		DataHandler dh2 = new DataHandler(new FileDataSource("./readme.txt"));
		// 将附件数据添加到节点
		attachment.setDataHandler(dh2);
		// 设置附件的文件名（需要编码）
		attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
		
		// 10.设置 文本+附件 和 附件的关系
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text_image);
		mm.addBodyPart(attachment);
		mm.setSubType("mixed");
		
		// 11.设置整个邮件的关系(将最终的混合“节点”作为邮件的内容添加到邮件对象)
		msg.setContent(mm);
		
		return msg;
	}
}
