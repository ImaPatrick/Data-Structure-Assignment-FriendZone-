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

public class ForgetPasswordController implements Initializable {

    @FXML
    private TextField txtFPEmail;
    @FXML
    private Button btnFPVerify;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Label lblError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnFPVerify) {
            if (LogIn().equals("Success")) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/RegisterPassword.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    RegisterPasswordController rpc = loader.getController();
                    rpc.getEmail(txtFPEmail.getText());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String LogIn() {
        String status = "Success";
        String email = txtFPEmail.getText();

        String sql = "SELECT * FROM users Where user_email = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                setlblError(Color.TOMATO, "Invalid email");
                status = "Error";
            } else {
                setlblError(Color.GREEN, "Valid email");
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

    public ForgetPasswordController() throws Exception {
        con = ConnectionUtils.getConnection();

    }

}
