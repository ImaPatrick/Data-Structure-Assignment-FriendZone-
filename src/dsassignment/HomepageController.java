package dsassignment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomepageController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblGetUsername;
    @FXML
    private Button btnShowUsers;
    String username;
    @FXML
    private Button btnSearchUser;
    private Button btnChat;
    @FXML
    private Button btnUserProfile;
    boolean status = true;
    @FXML
    private Button btnUpdateDetails;

    public void getUsername(String text) {
        username = text;
        lblGetUsername.setText("Welcome " + username);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButtonAction(MouseEvent event) throws Exception {
        if (event.getSource() == btnLogout) {
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

        if (event.getSource() == btnShowUsers) {
            try {

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/ShowUsers.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                ShowUsersController suc = loader.getController();
                suc.getUsername(username);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == btnSearchUser) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Search.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                SearchController sc = loader.getController();
                sc.getOriUsername(username);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println("");
            }
        }

        if (event.getSource() == btnUpdateDetails) {
          try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/UpdateInterests.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                UpdateInterestsController uic = loader.getController();
                uic.getUsername(username);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println("");
            }
        }

        if (event.getSource() == btnUserProfile) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Profile.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                ProfileController pc = loader.getController();
                pc.getUsername(username);
                pc.getDetails();
                pc.setDetails();
                pc.getMoments();
                pc.setMoments();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
}
