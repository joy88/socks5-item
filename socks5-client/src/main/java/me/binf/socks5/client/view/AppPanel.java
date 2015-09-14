package me.binf.socks5.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 */
public class AppPanel extends VBox {

    @FXML Label status;
    @FXML TextField serverIP;
    @FXML TextField serverPort;
    @FXML TextField localPort;

    @FXML public void onSave() {
        status.setText(computeStatus());
    }


    private String computeStatus() {
        return "Name: " + serverIP.getText() + ", Email: " + serverPort.getText() +
                ", Comments: " + localPort.getText();
    }

}
