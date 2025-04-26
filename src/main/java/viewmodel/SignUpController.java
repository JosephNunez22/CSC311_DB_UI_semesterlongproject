package viewmodel;

import com.sun.jdi.connect.spi.Connection;
import dao.DbConnectivityClass;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Departments;
import model.Major;
import model.Person;

import java.io.File;
import java.util.function.Function;

public class SignUpController {

    @FXML
    private VBox btnVbox;

    @FXML
    private Button createAccountBtn, loginBtn;

    @FXML
    private ComboBox<String> majorCB,departmentCB;

    @FXML
    private Label fNameStatusLbl, lNameStatusLbl, emailStatusLbl;

    @FXML
    private TextField fNameTF, lNameTF, emailTF, passwordTF;

    @FXML
    private ImageView profilePicIV;

    private final DbConnectivityClass db = DbConnectivityClass.getInstance();

    @FXML
    public void initialize() {
        majorCB.setItems(FXCollections.observableArrayList(Major.getAllQuestions()));
        departmentCB.setItems(FXCollections.observableArrayList(Departments.getAllQuestions()));
        btnVbox.setDisable(!isValidPersonForm());
        ChangeListener<Object> formListener = (obs, oldVal, newVal) -> {
            if (allFieldsFilled()) {
                btnVbox.setDisable(false);
            } else {
                btnVbox.setDisable(true);
            }
        };

        // Add listeners to fields
        fNameTF.textProperty().addListener(formListener);
        lNameTF.textProperty().addListener(formListener);
        emailTF.textProperty().addListener(formListener);
        passwordTF.textProperty().addListener(formListener);
        majorCB.valueProperty().addListener(formListener);
        departmentCB.valueProperty().addListener(formListener);
    }

    @FXML
    void createNewAccountBtnClicked(ActionEvent event) {
        if (isValidPersonForm()) {
            Person p = new Person(
                    fNameTF.getText().trim(),
                    lNameTF.getText().trim(),
                    departmentCB.getValue(),
                    majorCB.getValue(),
                    emailTF.getText().trim(),
                    profilePicIV.getImage().getUrl(),
                    passwordTF.getText().trim());
            db.insertUser(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Account Created! welcome " + fNameTF.getText().trim());
            alert.showAndWait();
        }
    }

    @FXML
    void loginBtnClicked(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void picBtnClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        try {
            File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                profilePicIV.setImage(image);
            }
        } catch (Exception e) {
            showAlert("Error loading image: " + e.getMessage());
        }

    }

    private boolean isValidPersonForm() {
        String nameRegex = "^[A-Za-z\\-\\s]+$";
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";

        boolean isValid = true;
        StringBuilder alertMessages = new StringBuilder();

        if (!fNameTF.getText().matches(nameRegex)) {
            fNameStatusLbl.setText("Invalid first name.");
            isValid = false;
        } else {
            fNameStatusLbl.setText("");
        }

        if (!lNameTF.getText().matches(nameRegex)) {
            lNameStatusLbl.setText("Invalid last name.");
            isValid = false;
        } else {
            lNameStatusLbl.setText("");
        }

        if (!emailTF.getText().matches(emailRegex)) {
            emailStatusLbl.setText("Invalid email address.");
            isValid = false;
        } else {
            emailStatusLbl.setText("");
        }

        if (departmentCB.getValue() == null || majorCB.getValue() == null) {
            alertMessages.append("Please select a department and major.\n");
            isValid = false;
        }

        if (profilePicIV.getImage() == null) {
            alertMessages.append("Please select a profile picture.\n");
            isValid = false;
        }

        if (passwordTF.getText().trim().matches(passwordRegex)) {
            alertMessages.append("Please enter a password.\n");
            isValid = false;
        }

        if (!isValid && alertMessages.length() > 0) {
            showAlert(alertMessages.toString().trim());
        }

        return isValid;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean allFieldsFilled() {
        return !fNameTF.getText().trim().isEmpty()
                && !lNameTF.getText().trim().isEmpty()
                && !emailTF.getText().trim().isEmpty()
                && !passwordTF.getText().trim().isEmpty()
                && majorCB.getValue() != null
                && departmentCB.getValue() != null;
    }
}
