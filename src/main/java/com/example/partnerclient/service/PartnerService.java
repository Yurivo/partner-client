package com.example.partnerclient.service;

import com.example.partnerclient.model.Partner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class PartnerService {
    private static final String BASE_URL = "http://localhost:8080/partners"; // измени порт, если другой!

    // Получить всех партнёров
    public List<Partner> getAllPartners() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int code = conn.getResponseCode();
            if (code != 200) throw new RuntimeException("Ошибка ответа сервера: " + code);

            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) inline.append(scanner.nextLine());
            scanner.close();

            String response = inline.toString();
            System.out.println("Ответ сервера: " + response);

            ObjectMapper mapper = new ObjectMapper();
            List<Partner> partners = mapper.readValue(response, new TypeReference<List<Partner>>() {});
            System.out.println("Партнёров получено: " + partners.size());
            return partners;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Добавить партнёра
    public void addPartner(Partner partner) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(partner);

            conn.getOutputStream().write(json.getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            int code = conn.getResponseCode();
            if (code != 200 && code != 201) throw new RuntimeException("Ошибка добавления: " + code);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Обновить партнёра
    public void updatePartner(Partner partner) {
        try {
            URL url = new URL(BASE_URL + "/" + partner.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(partner);

            conn.getOutputStream().write(json.getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            int code = conn.getResponseCode();
            if (code != 200) throw new RuntimeException("Ошибка обновления: " + code);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Удалить партнёра
    public void deletePartner(Long id) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.connect();

            int code = conn.getResponseCode();
            if (code != 200 && code != 204) throw new RuntimeException("Ошибка удаления: " + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
