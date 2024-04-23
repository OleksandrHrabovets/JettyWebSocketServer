package org.example.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebSocketEndpoint {

  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

  @OnWebSocketConnect
  public void onWebSocketConnect(Session session) {
    sessions.add(session);
    System.out.println("Client connected: " + session.getRemoteAddress().toString());
  }

  @OnWebSocketClose
  public void onWebSocketClose(Session session) {
    sessions.remove(session);
    System.out.println("Client disconnected: " + session.getRemoteAddress().toString());
  }

  @OnWebSocketError
  public void onWebSocketError(Throwable error) {
    System.out.println("Client error " + error.toString());
  }

  @OnWebSocketMessage
  public void onWebSocketMessage(Session session, String message) throws IOException {
    session.getRemote().sendString(message + " - Ok");
    System.out.println("Client message: " + message);
  }

}
