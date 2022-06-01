package com.zapcorp.chat;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MessagesStatus {
    private Consumer<ArrayList<Message>> onReceiveCallback;
    private ArrayList<Message> messages = new ArrayList();
    private ChatServerConnection connection;
    private ChatViewController controller;

    public MessagesStatus(Consumer<ArrayList<Message>> onReceiveCallback, ChatServerConnection conn, ChatViewController control) {
        this.onReceiveCallback = onReceiveCallback;
        connection = conn;
        controller = control;
        listen();
    }

    private void listen() {
            Thread thread = new Thread(() -> {
                ArrayList<Message> list;
                while (true) {
                    try {
                        list = (ArrayList<Message>) connection.getInStream().readObject();
                        messages = list;
                        onReceiveCallback.accept(messages);
                    } catch (SocketException ex) {
                        break;
                    } catch (Exception e) {
                        connection.close();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
    }

}