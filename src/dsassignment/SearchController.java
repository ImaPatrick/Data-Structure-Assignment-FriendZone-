/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Stage;
import utils.ConnectionUtils;

/**
 * FXML Controller class
 *
 * @author Zheng Shin
 */
public class SearchController implements Initializable {

    @FXML
    private Label lblSearchUser1;
    @FXML
    private Label lblSearchUser2;
    @FXML
    private Label lblSearchUser3;
    @FXML
    private Label lblSearchUser4;
    @FXML
    private Label lblSearchUser5;
    @FXML
    private Label lblSearchUser6;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    LinkedList<String> list_initialNameList = new LinkedList<>();
    LinkedList<String> list_finalNameList = new LinkedList<>();
    @FXML
    private TextField txtSearchUser;
    @FXML
    private Button btnSearch;
    String searchUser, oriUsername;
    @FXML
    private Button btnHomepage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSearch) {
            if (txtSearchUser.getText() == null) {
                lblSearchUser1.setText("Please enter username");
            } else {
                list_initialNameList.clear();
                lblSearchUser1.setText("");
                lblSearchUser2.setText("");
                lblSearchUser3.setText("");
                lblSearchUser4.setText("");
                lblSearchUser5.setText("");
                lblSearchUser6.setText("");
                searchUser = "%" + txtSearchUser.getText() + "%";
                String sql = "SELECT * FROM user_details Where username like ?";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, searchUser);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        list_initialNameList.addNode(resultSet.getString("username"));
                    }
                    if (!resultSet.next()) {
                        lblSearchUser1.setText("User doesn't exist");
                    }
                } catch (SQLException ex) {
                    System.err.println("");
                }
                //searchUser();
                setUser();
            }
        }
        if(event.getSource() == btnHomepage){
            try {
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
                ex.printStackTrace();
            }
        }
        if (lblSearchUser1 != null) {
            if (event.getSource() == lblSearchUser1) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_initialNameList.get(0));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(oriUsername);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (lblSearchUser2 != null) {
            if (event.getSource() == lblSearchUser2) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_initialNameList.get(1));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(oriUsername);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (lblSearchUser3 != null) {
            if (event.getSource() == lblSearchUser3) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_initialNameList.get(2));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(oriUsername);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (lblSearchUser4 != null) {
            if (event.getSource() == lblSearchUser4) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_initialNameList.get(3));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(oriUsername);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (lblSearchUser5 != null) {
            if (event.getSource() == lblSearchUser5) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_initialNameList.get(4));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(oriUsername);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (lblSearchUser6 != null) {
            if (event.getSource() == lblSearchUser6) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsassignment/OtherProfile.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    OtherProfileController opc = loader.getController();
                    opc.getUsername(list_initialNameList.get(5));
                    opc.getDetails();
                    opc.setDetails();
                    opc.getMoments();
                    opc.setMoments();
                    opc.getOriUsername(oriUsername);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SearchController() throws Exception {
        con = ConnectionUtils.getConnection();
    }

    private void searchUser() {
        for (int i = 0; i < list_initialNameList.length(); i++) {
            if (list_initialNameList.get(i).contains(txtSearchUser.getText())) {
                list_finalNameList.addNode(list_initialNameList.get(i));
            }
        }
    }

    private void setUser() {
        if (list_initialNameList.get(0) != null) {
            lblSearchUser1.setText(list_initialNameList.get(0));
        }
        if (list_initialNameList.get(1) != null) {
            lblSearchUser2.setText(list_initialNameList.get(1));
        }
        if (list_initialNameList.get(2) != null) {
            lblSearchUser3.setText(list_initialNameList.get(2));
        }
        if (list_initialNameList.get(3) != null) {
            lblSearchUser4.setText(list_initialNameList.get(3));
        }
        if (list_initialNameList.get(4) != null) {
            lblSearchUser5.setText(list_initialNameList.get(4));
        }
        if (list_initialNameList.get(5) != null) {
            lblSearchUser6.setText(list_initialNameList.get(5));
        }

    }

    public void getOriUsername(String text) {
        oriUsername = text;
    }

}
