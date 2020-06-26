package dsassignment;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class RegistrationController implements Initializable {

    @FXML
    private Button btnBackToLogin;
    @FXML
    private TextField txtRegistrationUsername;
    @FXML
    private TextField txtRegistrationEmail;
    @FXML
    private PasswordField txtRegistrationPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private ComboBox<String> cbRegistrationGender;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button btnTantan;
    @FXML
    private Button btnTinder;
    @FXML
    private Button btnJD;
    @FXML
    private Label lblError;

    public void initialize(URL url, ResourceBundle rb) {
        cbRegistrationGender.getItems().add("Male");
        cbRegistrationGender.getItems().add("Female");
    }

    @FXML
    private void handleButtonAction(MouseEvent event) throws SQLException {
        if (event.getSource() == btnBackToLogin) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Login.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println("");
            }
        }
        if (event.getSource() == btnRegister) {
            if (LogIn().equals("Success")) {
                try {
                    register();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Interests.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (event.getSource() == btnTantan) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Tantan.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == btnTinder) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Tinder.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == btnJD) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/JD.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void register() {

        try {
            String registerUsername = txtRegistrationUsername.getText();
            String registerPassword = txtRegistrationPassword.getText();
            String registerEmail = txtRegistrationEmail.getText();
            String registerGender = cbRegistrationGender.getSelectionModel().getSelectedItem().toString();
            int registerX = generateX();
            int registerY = generateY();

            String sql = "INSERT INTO `users` (`username`, `user_password`, `user_email`, `user_gender`, `coordinate_x`, `coordinate_y`) VALUES (?,?,?,?,?,?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, registerUsername);
            preparedStatement.setString(2, registerPassword);
            preparedStatement.setString(3, registerEmail);
            preparedStatement.setString(4, registerGender);
            preparedStatement.setInt(5, registerX);
            preparedStatement.setInt(6, registerY);
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

    public RegistrationController() throws Exception {
        con = ConnectionUtils.getConnection();

    }

    int x, y;
    Random r = new Random();

    public int generateX() {
        x = r.nextInt(1001);
        return x;
    }

    public int generateY() {
        y = r.nextInt(1001);
        return y;
    }

    private String LogIn() {
        String status = "Success";
        String username = txtRegistrationUsername.getText();

        String sql = "SELECT * FROM users Where username = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                setlblError(Color.TOMATO, "User exists, please use another username");
                status = "Error";
            } else {
                setlblError(Color.GREEN, "Valid Username");
            }

        } catch (SQLException ex) {
            System.err.println("");
            status = "Exception";
        }
        return status;

    }

    public void setlblError(Color color, String text) {
        lblError.setText(text);
        lblError.setTextFill(color);
        System.out.println(text);
    }

}
