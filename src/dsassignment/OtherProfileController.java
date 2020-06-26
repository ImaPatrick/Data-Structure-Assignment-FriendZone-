package dsassignment;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class OtherProfileController implements Initializable {

    @FXML
    private Button btnHomepage;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblHobby;
    @FXML
    private Label lblFood;
    @FXML
    private Label lblMusic;
    @FXML
    private Label lblMovies;
    @FXML
    private Label lblAbout;
    @FXML
    private Label lblMoment1;
    @FXML
    private Label lblMoment2;
    @FXML
    private Label lblMoment3;
    @FXML
    private Label lblMoment4;
    String username;
    int age;
    String hobby, food, music, movies, about;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Label lblUser;
    String oriUsername;

    LinkedList<String> list_moments = new LinkedList<>();
    @FXML
    private Label lblMoment5;
    @FXML
    private Button btnChat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButtonAction(MouseEvent event) throws Exception {
        if (event.getSource() == btnHomepage) {
            try {
                Encryption encrypt = new Encryption();
                encrypt.launch();
                encrypt.getSender(oriUsername);
                encrypt.getReceiver(username);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Homepage.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                HomepageController hpc = loader.getController();
                hpc.getUsername(oriUsername);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (event.getSource() == btnChat) {
            Client newClient = new Client(oriUsername, username);
            newClient.launch();
            Encryption encrypt = new Encryption();
            encrypt.getSender(oriUsername);
            encrypt.getReceiver(username);
            
        }

    }

    public void getUsername(String text) {
        username = text;
        lblUser.setText(username + " Profile");
    }

    public void getOriUsername(String text) {
        oriUsername = text;
    }

    public void getDetails() {
        String sql = "SELECT * FROM user_details Where username = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                age = resultSet.getInt("user_age");
                hobby = resultSet.getString("user_hobby");
                food = resultSet.getString("user_food");
                music = resultSet.getString("user_music");
                movies = resultSet.getString("user_movies");
                about = resultSet.getString("user_about");

            }
        } catch (SQLException ex) {
            System.err.println("");
        }
    }

    public OtherProfileController() throws Exception {
        con = ConnectionUtils.getConnection();
    }

    public void setDetails() {
        lblAge.setText(Integer.toString(age));
        lblHobby.setText(hobby);
        lblFood.setText(food);
        lblMusic.setText(music);
        lblMovies.setText(movies);
        lblAbout.setText(about);
    }

    public void getMoments() {
        list_moments.clear();
        String sql = "SELECT * FROM moment Where moment_username = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list_moments.addFrontNode(resultSet.getString("moment_content"));
            }
        } catch (SQLException ex) {
            System.err.println("");
        }
    }

    public void setMoments() {
        if (list_moments.get(0) != null) {
            if (list_moments.get(0) != null) {
                lblMoment1.setText(list_moments.get(0));
            }
            if (list_moments.get(1) != null) {
                lblMoment2.setText(list_moments.get(1));
            }
            if (list_moments.get(2) != null) {
                lblMoment3.setText(list_moments.get(2));
            }
            if (list_moments.get(3) != null) {
                lblMoment4.setText(list_moments.get(3));
            }
            if (list_moments.get(4) != null) {
                lblMoment5.setText(list_moments.get(4));
            }
        } else {
            lblMoment1.setText("admin: This user has no moment yet. ");
        }

    }
}
