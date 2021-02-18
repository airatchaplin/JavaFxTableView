package sample.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sample.ContractDTO.ContractDTO;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Controller {

    private ObservableList<ContractDTO> data = FXCollections.observableArrayList();

    @FXML
    private TableView<ContractDTO> tableView;

    @FXML
    private TableColumn<ContractDTO, Integer> id;

    @FXML
    private TableColumn<ContractDTO, Integer> numberOfContract;

    @FXML
    private TableColumn<ContractDTO, String> agreementDate;

    @FXML
    private TableColumn<ContractDTO, String> updatedAgreementDate;

    @FXML
    private void initialize() throws Exception {

        loadAllContracts();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        numberOfContract.setCellValueFactory(new PropertyValueFactory<>("numberOfContract"));
        agreementDate.setCellValueFactory(new PropertyValueFactory<>("agreementDate"));
        updatedAgreementDate.setCellValueFactory(new PropertyValueFactory<>("updatedAgreementDate"));
        tableView.setItems(data);
    }

    private void loadAllContracts() throws Exception {

        HttpGet get = new HttpGet("http://localhost:8080/api/contracts");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Type contactListType = new TypeToken<ArrayList<ContractDTO>>() {
            }.getType();
            List<ContractDTO> contracts = gson.fromJson(json, contactListType);
            data.addAll(contracts);
        }
    }

    @FXML
    CheckBox checkBox;

    public void clickCheckBox() throws Exception {
        if (checkBox.isSelected()) {
            List<ContractDTO> list = data;
            data = FXCollections.observableArrayList();
            for (ContractDTO contract : list) {
                if (DAYS.between(contract.getUpdatedAgreementDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()) < 60) {
                    data.add(contract);
                }
            }
            tableView.setItems(data);
        } else {
            data = FXCollections.observableArrayList();
            loadAllContracts();
            tableView.setItems(data);
        }
    }

}
