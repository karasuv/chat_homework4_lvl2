package ru.geekbrains.chat.client.src.test.java;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainChatController {


    public TextArea chatArea;
    public ListView onlineUsers;
    public TextField inputField;
    public Button btnSendMessage;

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
        appendTextFromTF();
    }

    private void appendTextFromTF() {
        String msg = inputField.getText();
        if (msg.isEmpty()) return;


        chatArea.appendText("ME: " + msg + System.lineSeparator());
//        chatArea.set
        inputField.clear();
    }
}
