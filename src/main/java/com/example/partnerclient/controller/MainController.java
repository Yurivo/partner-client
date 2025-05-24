package com.example.partnerclient.controller;

import com.example.partnerclient.model.Partner;
import com.example.partnerclient.service.PartnerService;
import com.example.partnerclient.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class MainController {

    @FXML private TableView<Partner> partnerTable;
    @FXML private TableColumn<Partner, String> nameColumn;
    @FXML private TableColumn<Partner, String> directorColumn;
    @FXML private TableColumn<Partner, String> phoneColumn;

    @FXML private Button minimizeButton;
    @FXML private Button maximizeButton;
    @FXML private Button closeButton;

    @FXML private MenuItem exitMenuItem;

    @FXML private TextField searchField; // Новое поле поиска

    private final PartnerService service = new PartnerService();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Поиск
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable(newVal));
        refreshTable();
    }

    private void filterTable(String filter) {
        if (filter == null || filter.isEmpty()) {
            refreshTable();
            return;
        }
        List<Partner> partners = service.getAllPartners();
        String lower = filter.toLowerCase();
        List<Partner> filtered = partners.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower)
                        || p.getDirector().toLowerCase().contains(lower)
                        || p.getPhone().toLowerCase().contains(lower))
                .toList();
        partnerTable.getItems().setAll(filtered);
    }

    @FXML
    private void onDelete() {
        Partner selected = partnerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            DialogUtil.showError("Ошибка", "Сначала выберите партнёра для удаления.");
            return;
        }

        boolean confirmed = DialogUtil.showConfirm("Подтверждение", "Удалить партнёра \"" + selected.getName() + "\"?");
        if (!confirmed) return;

        try {
            service.deletePartner(selected.getId());
            partnerTable.getItems().remove(selected);
            DialogUtil.showInfo("Успех", "Партнёр удалён.");
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось удалить партнёра.");
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/partnerclient/add-partner.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Добавить партнёра");
            stage.showAndWait();
            refreshTable();
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось открыть форму добавления.");
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditForm() {
        Partner selected = partnerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            DialogUtil.showError("Ошибка", "Сначала выберите партнёра для редактирования.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/partnerclient/edit-partner.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Редактировать партнёра");

            EditPartnerController controller = loader.getController();
            controller.setPartner(selected);

            stage.showAndWait();
            refreshTable();
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось открыть форму редактирования.");
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        try {
            List<Partner> partners = service.getAllPartners();
            partnerTable.getItems().setAll(partners);
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось обновить таблицу.");
            e.printStackTrace();
        }
    }

    // ======= Кнопка "Печать" =======
    @FXML
    private void onPrint() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/partnerclient/print-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Печать");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            DialogUtil.showError("Ошибка", "Не удалось открыть форму печати.");
            e.printStackTrace();
        }
    }

    // ======= Управление окном =======

    @FXML
    private void onMinimize() {
        getStage().setIconified(true);
    }

    @FXML
    private void onMaximize() {
        Stage stage = getStage();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    private void onClose() {
        getStage().close();
    }

    @FXML
    private void onExit() {
        onClose();
    }

    private Stage getStage() {
        return (Stage) closeButton.getScene().getWindow();
    }
}
