package com.project;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller2{

    @FXML
    private Button back;

    @FXML
    private TextField privateKey;
    @FXML
    private TextField file;
    @FXML
    private TextField password;
    @FXML
    private TextField path;

    private String keyFolder = "src/main/resources/data/keys/";
    private String outputFolder = "src/main/resources/data/out/";

    private String pwd = "safe";

    @FXML
    private void back(ActionEvent event) {
        UtilsViews.setView("View0");
    }

    @FXML
    private void toDecrypt(ActionEvent event){
        if(pwd.equals(password.getText())){
            try {
                PrivateKey privateKeyFile = privateKeyLoader(keyFolder + privateKey.getText());
                byte[] encryptedFile = Files.readAllBytes(Paths.get(outputFolder + file.getText()));
                byte[] decryptedFile = fileDecrypter(encryptedFile, privateKeyFile);
                Files.write(Paths.get(outputFolder + path.getText()), decryptedFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PrivateKey privateKeyLoader(String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PrivateKey) ois.readObject();
        }
    }


    public static byte[] fileDecrypter(byte[] data, PrivateKey key) throws Exception {
        Cipher cphr = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cphr.init(Cipher.DECRYPT_MODE, key);
        return cphr.doFinal(data);
    }
    
}