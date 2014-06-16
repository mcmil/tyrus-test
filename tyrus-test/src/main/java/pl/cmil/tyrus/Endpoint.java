package pl.cmil.tyrus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/endpoint")
public class Endpoint {
	private static final Logger logger = Logger.getLogger("Endpoint");

	/** All open WebSocket sessions */
	static List<Session> peers = Collections
			.synchronizedList(new ArrayList<Session>());

	@OnOpen
	public void openConnection(Session session)
			throws IllegalArgumentException, IOException {
		peers.add(session);

		session.getBasicRemote().sendPong(ByteBuffer.wrap(new byte[10]));

		logger.info("New connection opened " + session.getId());
	}

	@OnClose
	public void closedConnection(Session session) {
		peers.remove(session);
		logger.log(Level.INFO, "closing " + session.getId());
	}

	@OnMessage
	public void message(final Session session, String value) {
		send(session.getId() + ";" + value);

		logger.log(Level.INFO, "Sent: {0}", session.getId() + ";" + value);
	}

	public static void send(String msg) {
		try {
			for (Session session : peers) {
				if (session.isOpen()) {
					logger.info("Sending update to " + session.getId());

					session.getBasicRemote().sendObject(msg);
				}
			}
		} catch (IOException | EncodeException e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

}
