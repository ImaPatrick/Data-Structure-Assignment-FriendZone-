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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class LoginController implements Initializable {

    private Label label;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblRegister;
    @FXML
    private Label lblNewUser;
    @FXML
    private Label lblError;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Label lblForgetPassword;

    @FXML
    void handleButtonAction(MouseEvent event) throws SQLException {
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
        if (event.getSource() == btnLogin) {
            if (LogIn().equals("Success")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Homepage.fxml"));
                    Stage stage = new Stage();
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    HomepageController hpc = loader.getController();
                    hpc.getUsername(txtUsername.getText());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == lblRegister) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Registration.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                System.err.println("");
            }

        }
        if (event.getSource() == lblForgetPassword) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/ForgetPassword.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println("");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private String LogIn() {
        String status = "Success";
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String sql = "SELECT * FROM users Where username = ? and user_password = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                setlblError(Color.TOMATO, "Invalid username or password");
                status = "Error";
            } else {
                setlblError(Color.GREEN, "Login Successfully");
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

    public LoginController() throws Exception {
        con = ConnectionUtils.getConnection();

    }

}
