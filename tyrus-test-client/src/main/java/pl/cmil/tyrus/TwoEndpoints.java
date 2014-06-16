package pl.cmil.tyrus;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer;

public class TwoEndpoints {

	@ClientEndpoint
	public static class Client {
		private static Logger logger = Logger.getLogger(Client.class.getName());

		@OnOpen
		public void open(Session s) {
			logger.info("Channel oppened " + s.getId());
		}

		@OnMessage
		public void message(Session s, String message) {
			logger.info(s.getId() + " received " + message);
		}

		@OnError
		public void err(Throwable ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) throws Exception {

		ClientManager container = ClientManager.createClient();
		container.getProperties().put(GrizzlyClientContainer.SHARED_CONTAINER,
				false);

		Session session = container.connectToServer(Client.class, new URI(
				"ws://localhost:8080/tyrus-test/endpoint"));

		Session session2 = container.connectToServer(Client.class, new URI(
				"ws://localhost:8080/tyrus-test/endpoint2"));

		Integer i = 0;
		while (true) {
			i++;
			Thread.sleep(2000);
			session.getBasicRemote().sendText(i.toString());

			Thread.sleep(2000);
			session2.getBasicRemote().sendText(i.toString());
		}

	}
}
