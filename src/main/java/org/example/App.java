package org.example;

import java.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.example.websocket.WebSocketEndpoint;

/**
 * Jetty websocket server
 */
public class App {


  public static void main(String[] args) {
    App app = new App();
    app.run();
  }

  private void run() {
    Server server = new Server(8889);
    ServletContextHandler handler = new ServletContextHandler(server, "/websocket");
    server.setHandler(handler);

    JettyWebSocketServletContainerInitializer.configure(handler,
        ((servletContext, container) -> {
          container.setIdleTimeout(Duration.ofMinutes(15L));
          container.addMapping("/", WebSocketEndpoint.class);
        }));

    try {
      server.start();
      System.out.println("Websocket server started");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
