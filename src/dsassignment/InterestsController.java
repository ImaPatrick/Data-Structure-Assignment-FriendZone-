package dsassignment;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class InterestsController implements Initializable {

    @FXML
    private ComboBox<String> cbFood;
    @FXML
    private ComboBox<String> cbMusic;
    @FXML
    private ComboBox<String> cbHobby;
    @FXML
    private ComboBox<String> cbMovies;
    @FXML
    private TextField txtAbout;
    @FXML
    private Label lblGetUsername;
    @FXML
    private Button btnInterests;
    int age;
    @FXML
    private DatePicker dpDOB;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String username;
    int interest_age;
    String interest_hobby;
    String interest_food;
    String interest_music;
    String interest_movies;
    String interest_about;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbFood.getItems().addAll("Mexican Food", "Indian Cuisine", "Italian Cuisine", "Thai Food", "Chinese Food", "Japanese Cuisine", "Korean Food", "American Food", "Mediterranean Cuisine", "Vietnamese Food", "Spanish Cuisine", "Caribbean Food", "Turkish Cuisine");
        cbMusic.getItems().addAll("Rock", "Jazz", "KPop", "Hip Hop", "Blues", "Classical Music", "Country music", "Heavy Metal", "EDM", "Rapper", "Orchestra", "Instrumental", "Dupstep", "Soundtrack");
        cbHobby.getItems().addAll("Hiking", "Reading", "Writing", "Cooking and Baking", "Playing Games", "Volunteering", "Get Artsy", "Learning", "Camping", "Playing Music", "Exercising", "Fostering Animals");
        cbMovies.getItems().addAll("Horror", "Science Fiction", "Action", "Comedy", "Adventure", "Western", "Crime", "Romance", "Thriller", "Documentary", "War", "Mystery");
    }

    public void getUsername(String text) {
        username = text;
        lblGetUsername.setText("Hi " + username + "! Let Us Know More About You!");
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnInterests) {
            registerDetails();
            try {
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

    private int getAge() {
        LocalDate localdate = dpDOB.getValue();
        age = 2020 - localdate.getYear();
        return age;
    }

    public InterestsController() throws Exception {
        con = ConnectionUtils.getConnection();

    }

    private void registerDetails() {
        try {
            interest_age = getAge();
            interest_hobby = cbHobby.getSelectionModel().getSelectedItem().toString();
            interest_food = cbFood.getSelectionModel().getSelectedItem().toString();
            interest_music = cbMusic.getSelectionModel().getSelectedItem().toString();
            interest_movies = cbMovies.getSelectionModel().getSelectedItem().toString();
            interest_about = txtAbout.getText().toString();

            String sql = "INSERT INTO `user_details` (`username`,`user_age`, `user_hobby`, `user_food`, `user_music`,`user_movies`,`user_about`) VALUES (?,?,?,?,?,?,?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, interest_age);
            preparedStatement.setString(3, interest_hobby);
            preparedStatement.setString(4, interest_food);
            preparedStatement.setString(5, interest_music);
            preparedStatement.setString(6, interest_movies);
            preparedStatement.setString(7, interest_about);
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
