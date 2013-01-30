package vnd.virtualarmor.hybridrouter.mdb;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;

import org.apache.log4j.Logger;

/**
 * ScriptJob message bean for processing messages from exec-script to update device configuration.
 *
 */
@MessageDriven(mappedName = "/api/hornet-q/queues/jms.queue.hybrid-router-queue")
public class ScriptJobMessageBean implements MessageListener {

	@Resource
	private MessageDrivenContext mdc;

	private static final Logger logger = Logger
			.getLogger(ScriptJobMessageBean.class);

	public ScriptJobMessageBean() {

	}

	@Override
	public void onMessage(Message msg) {

		TextMessage tmsg = null;

		try {
			tmsg = (TextMessage) msg;
			String text = tmsg.getText();
			
//TODO process scriptjob message here
			
		} catch (JMSException e) {
			logger.error("Rollback transaction processing ScriptJob message, error is: "
					+ e.getMessage());
			mdc.setRollbackOnly();
		}

	}
}
