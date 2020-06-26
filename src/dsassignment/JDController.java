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

public class JDController implements Initializable {

    @FXML
    private TextField txtJDUsername;
    @FXML
    private PasswordField txtJDPassword;
    @FXML
    private Label lblError;
    @FXML
    private Button btnRegister;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button btnBackToRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void handleButtonAction(MouseEvent event) {
        
        if(event.getSource() == btnRegister){
            if (LogIn().equals("Success")) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Interests.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    InterestsController ic = loader.getController();
                    ic.getUsername(txtJDUsername.getText());
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
        String username = txtJDUsername.getText();
        String password = txtJDPassword.getText();

        String sql = "SELECT * FROM jdusers Where jd_name = ? and jd_password = ?";

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
                jdusername = resultSet.getString("jd_name");
                jdpassword = resultSet.getString("jd_password");
                email = resultSet.getString("jd_email");
                gender = resultSet.getString("jd_gender");
                register(jdusername, jdpassword, email, gender, x, y);
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

    public JDController() throws Exception {
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
    String jdusername;
    String jdpassword;
    String email;
    String gender;
    int x = generateX();
    int y = generateY();

    private void register(String jdusername, String jdpassword, String jdemail, String jdgender, int coordinatex, int coordinatey) {

        try {

            String sql = "INSERT INTO `users` (`username`, `user_password`, `user_email`, `user_gender`, `coordinate_x`, `coordinate_y`) VALUES (?,?,?,?,?,?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, jdusername);
            preparedStatement.setString(2, jdpassword);
            preparedStatement.setString(3, jdemail);
            preparedStatement.setString(4, jdgender);
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
