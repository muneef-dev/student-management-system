package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.util.tools.VerificationCodeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ForgotPasswordFormController {

    public AnchorPane context;
    public TextField txtEmail;

    public void backToLoginForm(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void sendCodeOnAction(ActionEvent actionEvent) {
        // code generation
        // System.out.println((int)'0'); // char ondeera 0 azuda int value a kaattu 48 -> 0
        // System.out.println(new VerificationCodeGenerator().getCode(5)); // izula case eendha first number 0 aa eendha print aahurulla
        // use sout to check every thing
        try {
            int verificationCode = new VerificationCodeGenerator().getCode(5);

            // java mail properties
            String fromEmail = "youngblood6265@gmail.com";
            String toEmail = txtEmail.getText();
            // need a SMTP host
            String host = "localhost";


            Properties properties = System.getProperties();
            // properties ikkku SMTP i set seithal
            properties.setProperty("mail.smtp.host", host);
            // properties.setProperty("smtp.gmail.com", host); // my answer
            // node -> nodemailer // ellaththukkum -> sendGrid // phone -> twilio
            Session session = Session.getDefaultInstance(properties);
            /*Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("youngblood6265@gmail.com", "young465");
                        }
                    });*/

            // making mail ------------------------- can use Mime message or HTML or plain text
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(fromEmail));

            // subject of message
            mimeMessage.setSubject("Verification Code"); // subject of email
            mimeMessage.setText("Verification Code : "+ verificationCode); // this is the message

            // sending to whom
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // sending part
            // Transport.send(mimeMessage); // comment this to message a send pannaama eeka (error vandha)
            System.out.println("Completed"); // 1.52.12

            // CodeVerificationFormController da ui a load seithal aza maththa murela setUi aaala iza load seyya sellitharalla
            // izu new mehtod
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CodeVerificationForm.fxml"));
            // Object load = fxmlLoader.load();// izula vaara exception a catch aala throw sei case illa
            // load() aala vaarazu parent sutti aza parant type ikku maaththiko
            Parent parent = fxmlLoader.load();// izula vaara exception a catch aala throw sei case illa
            CodeVerificationFormController controller =  fxmlLoader.getController(); // izula vaarazu
            // CodeVerificationFormController type da controller sutti aza type reference la assing senji kolonum
            // getController(-T-) izu Type a thaan parameter la (keakkum) kudukkonum
            // ippa CodeVerificationFormController la eera methods a leasa access seyya ealum
            controller.setUserData(verificationCode,txtEmail.getText());
            // so angala values a pass panni nonna CodeVerificationForm iza form ikku navigate panni udonum
            Stage stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(parent)); // ippa run pannina smooth aa work kattayam email a aza
            // form la kudu illatti error vaaara Illegal address in string ``''

        } catch (MessagingException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ location +".fxml"))));
        stage.centerOnScreen();
    }
}
