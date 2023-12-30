package com.project;

import javax.crypto.Cipher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;

public class Controller1 {

    @FXML
    private Button back;

    @FXML
    private TextField publicKey;
    @FXML
    private TextField file;
    @FXML
    private TextField path;

    private String keyFolder = "src/main/resources/data/keys/";
    private String fileFolder = "src/main/resources/data/files/";
    private String outputFolder = "src/main/resources/data/out/";

    @FXML
    private void back(ActionEvent event) {
        UtilsViews.setView("View0");
    }

    @FXML
    private void toEncrypt(ActionEvent event){
        if(!publicKey.getText().equals("") && !file.getText().equals("") && !path.getText().equals("")){
            try {
                PublicKey publicKeyFile = publicKeyLoader(keyFolder + publicKey.getText());
                String fileToEncrypt = new String(Files.readAllBytes(Paths.get(fileFolder + file.getText())));
                byte[] encryptedFile = fileEncrypter(fileToEncrypt, publicKeyFile);
                Files.write(Paths.get(outputFolder + path.getText()), encryptedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PublicKey publicKeyLoader(String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PublicKey) ois.readObject();
        }
    }


    public static byte[] fileEncrypter(String data, PublicKey key) throws Exception {
        Cipher cphr = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cphr.init(Cipher.ENCRYPT_MODE, key);
        return cphr.doFinal(data.getBytes());
    }
}