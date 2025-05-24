package com.example.partnerclient.controller;

import com.example.partnerclient.model.Partner;
import com.example.partnerclient.service.PartnerService;
import com.example.partnerclient.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPartnerController {

    @FXML private TextField nameField;
    @FXML private TextField directorField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField faxField;

    private final PartnerService service = new PartnerService();

    @FXML
    private void onAdd() {
        Partner partner = new Partner();
        partner.setName(nameField.getText());
        partner.setDirector(directorField.getText());
        partner.setAddress(addressField.getText());
        partner.setPhone(phoneField.getText());
        partner.setFax(faxField.getText());

        try {
            service.addPartner(partner);
            DialogUtil.showInfo("Успех", "Партнёр успешно добавлен.");
            closeWindow();
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось добавить партнёра.");
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
