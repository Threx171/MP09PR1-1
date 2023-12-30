package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller0 {

    @FXML
    private Button encrypter;
    
    @FXML
    private Button decrypter;

    @FXML
    private void toEncrypt(ActionEvent event) {
        UtilsViews.setView("View1");
    }
    
    @FXML
    private void toDecrypt(ActionEvent event) {
        UtilsViews.setView("View2");
    }

    public void initialize() {
        encrypter.setOnAction(this::toEncrypt);
        decrypter.setOnAction(this::toDecrypt);
    }


}