package com.example.partnerclient.controller;

import com.example.partnerclient.model.Partner;
import com.example.partnerclient.service.PartnerService;
import com.example.partnerclient.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPartnerController {

    @FXML private TextField nameField;
    @FXML private TextField directorField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField faxField;

    private Partner currentPartner;
    private final PartnerService service = new PartnerService();

    public void setPartner(Partner partner) {
        this.currentPartner = partner;
        nameField.setText(partner.getName());
        directorField.setText(partner.getDirector());
        addressField.setText(partner.getAddress());
        phoneField.setText(partner.getPhone());
        faxField.setText(partner.getFax());
    }

    @FXML
    private void onUpdate() {
        currentPartner.setName(nameField.getText());
        currentPartner.setDirector(directorField.getText());
        currentPartner.setAddress(addressField.getText());
        currentPartner.setPhone(phoneField.getText());
        currentPartner.setFax(faxField.getText());

        try {
            service.updatePartner(currentPartner);
            DialogUtil.showInfo("Успех", "Партнёр успешно обновлён.");
            closeWindow();
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось обновить партнёра.");
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
