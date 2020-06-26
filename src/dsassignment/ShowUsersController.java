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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.ConnectionUtils;

public class ShowUsersController implements Initializable {

    @FXML
    private TextField txtDistance;
    @FXML
    private ChoiceBox<String> cbGender;
    @FXML
    private Button btnShow;
    @FXML
    private Label lbluser1;
    @FXML
    private Label lbluser2;
    @FXML
    private Label lbluser3;
    @FXML
    private Label lbluser4;
    private Label lbluser5;
    int distance;
    int user_x, user_y;
    String username;
    int actual_distance;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String gender;
    LinkedList<String> list_gender = new LinkedList<>();
    LinkedList<Integer> list_coordinatex = new LinkedList<>();
    LinkedList<Integer> list_coordinatey = new LinkedList<>();
    LinkedList<String> list_showList = new LinkedList<>();
    LinkedList<String> list_distance = new LinkedList<>();
    LinkedList<String> list_otherHobby = new LinkedList<>();
    LinkedList<String> list_otherFood = new LinkedList<>();
    LinkedList<String> list_otherMusic = new LinkedList<>();
    LinkedList<String> list_otherMovies = new LinkedList<>();
    LinkedList<Integer> list_score = new LinkedList<>();
    String userHobby;
    String userFood;
    String userMusic;
    String userMovies;
    int count = 0;
    LinkedList<String> list_finalOtherHobby = new LinkedList<>();
    LinkedList<String> list_finalOtherFood = new LinkedList<>();
    LinkedList<String> list_finalOtherMusic = new LinkedList<>();
    LinkedList<String> list_finalOtherMovies = new LinkedList<>();
    LinkedList<Integer> list_finalScore = new LinkedList<>();
    LinkedList<String> list_finalShowList = new LinkedList<>();
    private Button btnButton;
    @FXML
    private Button btnHomepage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbGender.getItems().addAll("Male", "Female");
    }

    public void getUsername(String text) {
        username = text;
        System.out.println(username);
        getUserCoordinate();
        System.out.println(user_x + " " + user_y);

    }

    private void calculate(int distance) {
        list_showList.clear();
        list_distance.clear();
        getUserGender();
        double dbactual_distance;
        for (int i = 0; i < list_gender.length(); i++) {
            int temp_x = list_coordinatex.get(i);
            int temp_y = list_coordinatey.get(i);
            double temp = (Math.pow(user_x - temp_x, 2)) + (Math.pow(user_y - temp_y, 2));
            double dbdistance = Math.sqrt(temp);
            actual_distance = (int) Math.round(dbdistance);
            if (actual_distance <= distance && actual_distance != 0) {
                list_showList.addNode(list_gender.get(i));
                list_distance.addNode(" (" + Integer.toString((actual_distance)) + "m away)");
            }
        }

    }

    public ShowUsersController() throws Exception {
        con = ConnectionUtils.getConnection();
    }

    private void getUserCoordinate() {
        String sql = "SELECT * FROM users Where username = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user_x = resultSet.getInt("coordinate_x");
                user_y = resultSet.getInt("coordinate_y");
            }
        } catch (SQLException ex) {
            System.err.println("");
        }
    }

    private void getUserGender() {
        list_gender.clear();
        list_coordinatex.clear();
        list_coordinatey.clear();
        gender = cbGender.getSelectionModel().getSelectedItem().toString();
        String sql = "SELECT * FROM users Where user_gender = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, gender);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString("username").equals("admin")) {
                    list_gender.addNode(resultSet.getString("username"));
                    list_coordinatex.addNode(resultSet.getInt("coordinate_x"));
                    list_coordinatey.addNode(resultSet.getInt("coordinate_y"));
                }

            }
        } catch (SQLException ex) {
            System.err.println("");
        }
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        
        if(event.getSource() == btnHomepage){
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/Homepage.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                HomepageController hpc = loader.getController();
                hpc.getUsername(username);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (event.getSource() == btnShow) {

            lbluser1.setText("");
            lbluser2.setText("");
            lbluser3.setText("");
            lbluser4.setText("");

            distance = Integer.parseInt(txtDistance.getText());
            calculate(distance);
            getUserInterests();
            System.out.println(userHobby + userFood + userMusic + userMovies);
            getOthersInterests();
            compareInterests();
            compareScore();
            if (list_showList.get(0) == null) {
                lbluser1.setText("No user nearby, please try further distance.");
            } else {
                if (list_showList.get(0) != null) {
                    lbluser1.setText(list_showList.get(0) + list_distance.get(0) + "\n" + list_score.get(0) + " common interest(s)");
                }
                if (list_showList.get(1) != null) {
                    lbluser2.setText(list_showList.get(1) + list_distance.get(1) + "\n" + list_score.get(1) + " common interest(s)");
                }
                if (list_showList.get(2) != null) {
                    lbluser3.setText(list_showList.get(2) + list_distance.get(2) + "\n" + list_score.get(2) + " common interest(s)");
                }
                if (list_showList.get(3) != null) {
                    lbluser4.setText(list_showList.get(3) + list_distance.get(3) + "\n" + list_score.get(3) + " common interest(s)");
                }

            }

        }

        if (event.getSource() == btnButton) {

            getOthersInterests();

            compareInterests();

            System.out.println("\nShowing " + list_showList.length() + " users");
            for (int i = 0; i < list_showList.length(); i++) {
                System.out.print(list_otherHobby.get(i) + " ");
                System.out.print(list_otherFood.get(i) + " ");
                System.out.print(list_otherMusic.get(i) + " ");
                System.out.print(list_otherMovies.get(i) + " ");
                System.out.println("\n" + list_score.get(i) + " common interest(s)");
                System.out.println("");
            }
            compareScore();
            System.out.println("");
            for (int i = 0; i < list_score.length(); i++) {
                System.out.println(list_score.get(i));
            }
            System.out.println("");

            list_otherHobby.clear();
            list_otherFood.clear();
            list_otherMusic.clear();
            list_otherMovies.clear();

        }
        if (list_showList != null) {
            if (event.getSource() == lbluser1) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_showList.get(0));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(username);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (list_showList != null) {
            if (event.getSource() == lbluser1) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_showList.get(0));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(username);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource() == lbluser2) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_showList.get(1));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(username);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource() == lbluser3) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_showList.get(2));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(username);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource() == lbluser4) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_showList.get(3));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(username);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource() == lbluser5) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_showList.get(4));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(username);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getUserInterests() {
        String sql = "SELECT * FROM user_details Where username = ? ";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userHobby = resultSet.getString("user_hobby");
                userFood = resultSet.getString("user_food");
                userMusic = resultSet.getString("user_music");
                userMovies = resultSet.getString("user_movies");

            }
        } catch (SQLException ex) {
            System.err.println("");
        }
    }

    private void getOthersInterests() {
        for (int i = 0; i < list_showList.length(); i++) {
            String other = list_showList.get(i);
            String sql = "SELECT * FROM user_details Where username = ? ";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, other);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list_otherHobby.addNode(resultSet.getString("user_hobby"));
                    list_otherFood.addNode(resultSet.getString("user_food"));
                    list_otherMusic.addNode(resultSet.getString("user_music"));
                    list_otherMovies.addNode(resultSet.getString("user_movies"));

                }
            } catch (SQLException ex) {
                System.err.println("");
            }

        }
    }

    private void compareInterests() {

        for (int i = 0; i < list_showList.length(); i++) {
            if (userHobby.equals(list_otherHobby.get(i).toString())) {
                count++;
            }

            if (userFood.equals(list_otherFood.get(i).toString())) {
                count++;
            }

            if (userMusic.equals(list_otherMusic.get(i).toString())) {
                count++;
            }

            if (userMovies.equals(list_otherMovies.get(i).toString())) {
                count++;
            }
            list_score.addNode(count);
            count = 0;
        }

    }

    private void compareScore() {
        int temp;
        String usernameTemp, distanceTemp;
        for (int i = 0; i < list_showList.length(); i++) {
            for (int j = 0; j < list_showList.length() - 1; j++) {
                if (list_score.get(j) < list_score.get(j + 1)) {
                    temp = list_score.get(j);
                    list_score.set(list_score.get(j + 1), j);
                    list_score.set(temp, j + 1);
                    distanceTemp = list_distance.get(j);
                    list_distance.set(list_distance.get(j + 1), j);
                    list_distance.set(distanceTemp, j + 1);
                    usernameTemp = list_showList.get(j);
                    list_showList.set(list_showList.get(j + 1), j);
                    list_showList.set(usernameTemp, j + 1);
                }
            }
        }
    }

}

