package com.example.partnerclient.controller;

import com.example.partnerclient.model.Partner;
import com.example.partnerclient.service.PartnerService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.*;
import java.util.List;

public class PrintViewController {

    @FXML private TableView<Partner> printTable;
    @FXML private TableColumn<Partner, String> nameColumn;
    @FXML private TableColumn<Partner, String> directorColumn;
    @FXML private TableColumn<Partner, String> phoneColumn;

    private final PartnerService service = new PartnerService();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        List<Partner> partners = service.getAllPartners();
        printTable.getItems().setAll(partners);
    }

    @FXML
    private void onSavePdf() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить как PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(getStage());
            if (file != null) {
                createPdf(file);
                showAlert("Файл PDF успешно сохранён!");
            }
        } catch (Exception e) {
            showAlert("Ошибка при сохранении PDF: " + e.getMessage());
        }
    }

    private void createPdf(File file) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("Список партнёров"));
        PdfPTable table = new PdfPTable(3);
        table.addCell("Наименование");
        table.addCell("Руководитель");
        table.addCell("Телефон");
        for (Partner partner : printTable.getItems()) {
            table.addCell(partner.getName());
            table.addCell(partner.getDirector());
            table.addCell(partner.getPhone());
        }
        document.add(table);
        document.close();
    }

    @FXML
    private void onPrint() {
        try {
            // Сохраняем во временный PDF
            File tempFile = File.createTempFile("partners_", ".pdf");
            createPdf(tempFile);

            // Печать через системный диалог
            InputStream is = new FileInputStream(tempFile);
            Doc pdfDoc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            if (printService != null) {
                DocPrintJob printJob = printService.createPrintJob();
                printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
                showAlert("Документ отправлен на печать");
            } else {
                showAlert("Принтер не найден");
            }
            is.close();
            tempFile.deleteOnExit();
        } catch (Exception e) {
            showAlert("Ошибка при печати: " + e.getMessage());
        }
    }

    private Stage getStage() {
        return (Stage) printTable.getScene().getWindow();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
