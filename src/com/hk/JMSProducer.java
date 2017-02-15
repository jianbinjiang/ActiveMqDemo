package com.hk;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息的生产者（发送者）
 * @author jiangjianbin
 *
 */

public class JMSProducer {

	// 默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	// 发送的消息数量
	private static final int SENDNUM = 10;

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		// 消息的目的地
		Destination destination;
		MessageProducer messageProducer;
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个名称为hello的消息队列
			destination = session.createQueue("name");
			messageProducer = session.createProducer(destination);
			sendMessage(session, messageProducer);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}
	}

	private static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
		for (int i = 0; i < SENDNUM; i++) {
			TextMessage message = session.createTextMessage("Activemq 发送消息" + i);
			System.out.println("发送消息：Activemq 发送消息" + i);
			// 通过消息生产者发出消息
			messageProducer.send(message);
		}
	}
}