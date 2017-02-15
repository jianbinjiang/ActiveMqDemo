package com.hk;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消费者
 * @author jiangjianbin
 *
 */
public class JMSConsumer {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认连接用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认连接密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认连接地址

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("name");
			consumer = session.createConsumer(destination);
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) consumer.receive(500000);
				if (null != message) {
					System.out.println("收到消息" + message.getText());
				} else {
					break;
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}
	}
}
