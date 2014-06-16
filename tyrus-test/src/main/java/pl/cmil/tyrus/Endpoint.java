package pl.cmil.tyrus;

import java.io.IOException;
import java.nio.ByteBuffer;
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

  @OnOpen
  public void openConnection(Session session)
      throws IllegalArgumentException, IOException {

    session.getBasicRemote().sendPong(ByteBuffer.wrap(new byte[10]));

    logger.info("New connection opened " + session.getId());
  }

  @OnClose
  public void closedConnection(Session session) {
    logger.log(Level.INFO, "closing " + session.getId());
  }

  @OnMessage
  public void message(final Session session, String value) {
    send(session, session.getId() + ";" + value);

    logger.log(Level.INFO, "Sent: {0}", session.getId() + ";" + value);
  }

  public static void send(Session session, String msg) {
    for (Session s : session.getOpenSessions()) {
      try {
        logger.info("Sending update to " + session.getId());

        s.getBasicRemote().sendObject(msg);

      } catch (IOException | EncodeException e) {
        logger.log(Level.SEVERE, e.toString());
      }
    }
  }
}
