package ru.geekbrains.chat.client.src.test.java;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.geekbrains.april_chat.common.ChatMessage;
import ru.geekbrains.april_chat.common.MessageType;
import ru.geekbrains.april_chat.network.ChatMessageService;
import ru.geekbrains.april_chat.network.ChatMessageServiceImpl;
import ru.geekbrains.april_chat.network.MessageProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainChatController implements Initializable, MessageProcessor {


    public TextArea chatArea;
    public ListView onlineUsers;
    public TextField inputField;
    public PasswordField passwordField;
    public Button btnSendAuth;

    public Button btnSendMessage;
    public TextField loginField;

    private ChatMessageService messageService;
    private String currentName;

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("MOCK!");
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void showAbout(ActionEvent actionEvent) {

        //тут возник вопрос  : использование средств Swing внутри  fx   это нормально или не желательно?

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        JLabel info = new JLabel("Some information about chat ");
        JLabel info1 = new JLabel("Version 1.0");
        JLabel info2 = new JLabel("Created by geekbrain with student ");

        panel.add(info);
        panel.add(info1);
        panel.add(info2);

        frame.add(panel);
        frame.setTitle("Information");
        frame.setSize(300,300);
         frame.setVisible(true);
        frame.setAlwaysOnTop(true);



    }
    public void showAbout2(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //Alert alert = new Alert (Alert.AlertType.NONE);
        // не успел разобраться почему-то  при типе  Alert.AlertType.NONE окно не закрывается

        alert.setTitle("Information ");

        alert.setContentText("");
        alert.setContentText("Some information about chat\nVersion 1.0\nCreated by geekbrains with student  ");

        alert.showAndWait();
        alert.close();


    }


    public void showHelp(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI("https://docs.google.com/document/d/1wr0YEtIc5yZtKFu-KITqYnBtp8KC28v2FEYUANL0YAM/edit?usp=sharing"));
    }

    public void sendMessage(ActionEvent actionEvent) {
        String text = inputField.getText();
        if(text.isEmpty()) return;
        ChatMessage msg = new ChatMessage();
        msg.setMessageType(MessageType.PUBLIC);
        msg.setFrom(currentName);
        msg.setBody(text);
        messageService.send(msg.marshall());
        inputField.clear();

    }

    private void appendTextFromTF(ChatMessage msg) {
     //   String msg = inputField.getText();
     //   if (msg.isEmpty()) return;
        String text = String.format("[%s] %s\n", msg.getFrom(),msg.getBody());


        chatArea.appendText(text);
//        chatArea.set
    //    inputField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.messageService = new ChatMessageServiceImpl("localhost",12256,this);
        messageService.connect();
    }

    @Override
    public void processMessage(String msg) {
        ChatMessage message = ChatMessage.unmarshall(msg);
        System.out.println("Received message");

        switch (message.getMessageType()){
            case PUBLIC: {
                appendTextFromTF(message);
                break;
            }
            case CLIENT_LIST:{
                refreshOnlineUsers(message);
                break;
            }
            case AUTH_CONFIRM: {
                    this.currentName = message.getBody();
                    App.stage1.setTitle(currentName);
                break;
            }




        }


    }
    private void sendAuth (){
            String log = loginField.getText();
            String pass = passwordField.getText();
            if(log.isEmpty()|| pass.isEmpty()) return;
            ChatMessage msg = new ChatMessage();
            msg.setMessageType(MessageType.SEND_AUTH);
            msg.setLogin(log);
            msg.setPassword(pass);
            messageService.send(msg.marshall());
    }

    private void refreshOnlineUsers(ChatMessage message) {
        this.onlineUsers.setItems(FXCollections.observableArrayList(message.getOnlineUsers()));

    }
    public void sendAuth(ActionEvent actionEvent) {

    }

}
