package dsassignment;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class RegisterPasswordController implements Initializable {

    @FXML
    private PasswordField txtPassword1;
    @FXML
    private PasswordField txtPassword2;
    @FXML
    private Button btnRegisterPassword;
    @FXML
    private Label lblError;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String email, username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnRegisterPassword) {
            if (!txtPassword1.getText().equals(txtPassword2.getText())) {
                setlblError(Color.TOMATO, "Password incorrect");
            } else {
                if (LogIn().equals("Success")) {
                    try {
                        register();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Homepage.fxml"));
                        Parent root = (Parent) loader.load();
                        Scene scene = new Scene(root);
                        HomepageController hpc = loader.getController();
                        hpc.getUsername(username);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setlblError(Color color, String text) {
        lblError.setText(text);
        lblError.setTextFill(color);
        System.out.println(text);
    }

    private void register() {

        try {
            String registerPassword = txtPassword2.getText();
            String sql = "UPDATE `users` SET `user_password` = ? WHERE (`user_email` = ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, registerPassword);
            preparedStatement.setString(2, email);
            int status = preparedStatement.executeUpdate();
            if (status < 0) {
                System.out.println("Failed");
            } else {
                System.out.println("Successful");
            }
        } catch (SQLException ex) {
            System.out.println("");
        }

    }

    public RegisterPasswordController() throws Exception {
        con = ConnectionUtils.getConnection();

    }

    public void getEmail(String text) {
        email = text;
    }

    private String LogIn() {
        String status = "Success";

        String sql = "SELECT * FROM users Where user_email = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                status = "Error";
            } else {

                username = resultSet.getString("username");
            }

        } catch (SQLException ex) {
            System.err.println("");
            status = "Exception";
        }
        return status;

    }
}
