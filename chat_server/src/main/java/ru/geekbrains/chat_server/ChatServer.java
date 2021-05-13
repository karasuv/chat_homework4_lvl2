package ru.geekbrains.chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
      private static final int PORT =     12256;

      public void start() {
          try (ServerSocket serverSocket = new ServerSocket(PORT)) {
              System.out.println("Server started");

              while (true) {
                  System.out.println("Waiting for connection");
                  Socket socket = serverSocket.accept();
                  System.out.println("Client connected");
                  new ClientHandler(socket,this).handle();
              }
          }catch (IOException e) {
              e.printStackTrace();
          }
      }


}
