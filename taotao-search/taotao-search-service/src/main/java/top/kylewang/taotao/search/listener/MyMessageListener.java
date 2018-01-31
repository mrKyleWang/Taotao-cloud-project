package top.kylewang.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收activemq队列消息的监听器
 * <p>Title: MyMessageListener</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
public class MyMessageListener implements MessageListener {

	
	@Override
	public void onMessage(Message message) {
		//取消息的内容
		try {
			TextMessage textMessage = (TextMessage) message;
			//取内容
			String text = textMessage.getText();
			System.out.println(text);
			//其他业务逻辑
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
