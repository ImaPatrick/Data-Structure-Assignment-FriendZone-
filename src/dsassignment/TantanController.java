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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class TantanController implements Initializable {

    @FXML
    private TextField txtTantanUsername;
    @FXML
    private PasswordField txtTantanPassword;
    @FXML
    private Button btnTantan;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Label lblError;
    private Button btnLogin;
    private Button btnRegister;
    @FXML
    private Button btnBackToRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnTantan) {
            if (LogIn().equals("Success")) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Interests.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    InterestsController ic = loader.getController();
                    ic.getUsername(txtTantanUsername.getText());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == btnBackToRegister) {
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
    }

    private String LogIn() {
        String status = "Success";
        String username = txtTantanUsername.getText();
        String password = txtTantanPassword.getText();

        String sql = "SELECT * FROM tantanusers Where tantan_name = ? and tantan_password = ?";

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
                ttusername = resultSet.getString("tantan_name");
                ttpassword = resultSet.getString("tantan_password");
                email = resultSet.getString("tantan_email");
                gender = resultSet.getString("tantan_gender");
                register(ttusername, ttpassword, email, gender, x, y);
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

    public TantanController() throws Exception {
        con = ConnectionUtils.getConnection();
    }

    Random r = new Random();

    public int generateX() {
        x = r.nextInt(1001);
        return x;
    }

    public int generateY() {
        y = r.nextInt(1001);
        return y;
    }
    String ttusername;
    String ttpassword;
    String email;
    String gender;
    int x = generateX();
    int y = generateY();

    private void register(String tantanusername, String tantanpassword, String tantanemail, String tantangender, int coordinatex, int coordinatey) {

        try {

            String sql = "INSERT INTO `users` (`username`, `user_password`, `user_email`, `user_gender`, `coordinate_x`, `coordinate_y`) VALUES (?,?,?,?,?,?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, tantanusername);
            preparedStatement.setString(2, tantanpassword);
            preparedStatement.setString(3, tantanemail);
            preparedStatement.setString(4, tantangender);
            preparedStatement.setInt(5, coordinatex);
            preparedStatement.setInt(6, coordinatey);
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
}
