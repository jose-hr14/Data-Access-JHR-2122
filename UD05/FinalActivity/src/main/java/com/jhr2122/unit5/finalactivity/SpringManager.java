package com.jhr2122.unit5.finalactivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpringManager {
    public void PostUser(UsersEntity user) throws JSONException {
        HttpURLConnection conn = null;
        String jsonInputString = new JSONObject()
                .put("code", user.getCode())
                .put("name", user.getName())
                .put("surname", user.getSurname())
                .put("birthdate", user.getBirthdate())
                .put("fined", user.getFined())
                .put("lentBooks", new JSONArray())
                .put("reservedBooks", new JSONArray())
                .toString();
        try {
            URL url = new URL("http://localhost:8080/api-rest/Library/Users");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            if (conn.getResponseCode() == 200)
                System.out.println("User inserted");
            else
            {
                System.out.println("Connection failed");
                throw new Exception("User post failed");
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.disconnect();
        }
    }
    public void PostReservation(ReservationsEntity reservation) throws Exception {
        HttpURLConnection conn = null;
        String jsonInputString = new JSONObject()
                .put("date", reservation.getDate())
                .put("book", new JSONObject()
                        .put("isbn", reservation.getBook().getIsbn())
                        .put("title", reservation.getBook().getTitle())
                        .put("copies", reservation.getBook().getCopies())
                        .put("cover", reservation.getBook().getCover())
                        .put("outline", reservation.getBook().getOutline())
                        .put("publisher", reservation.getBook().getPublisher()))
                .put("borrower", new JSONObject()
                        .put("code", reservation.getBorrower().getCode())
                        .put("name", reservation.getBorrower().getName())
                        .put("surname", reservation.getBorrower().getSurname())
                        .put("birthdate", reservation.getBorrower().getBirthdate())
                        .put("fined", reservation.getBorrower().getFined()))
                .toString();
        try {
            URL url = new URL("http://localhost:8080/api-rest/Library/Reservations");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            if (conn.getResponseCode() == 200)
                System.out.println("Reservation inserted");
            else
            {
                System.out.println("Connection failed");
                throw new Exception("Reservation POST failed");
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.disconnect();
        }
    }
    public static void GetUsers(){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(
                    " http://localhost:8080/api-rest/Library/Users");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject)
                            jsonArray.get(i);
                    System.out.println(jsonObject.get("name") + " "
                            + jsonObject.get("surname"));
                }
            }
            else
                System.out.println("Connection failed.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.disconnect();
        }
    }
    public void deleteReservation(ReservationsEntity reservation) throws Exception
    {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://localhost:8080/api-rest/Library/Reservations/" + reservation.getId());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (conn.getResponseCode() == 200)
                System.out.println("Reservation deleted");
            else
            {
                System.out.println("Connection failed");
                throw new Exception("Reservation DELETE failed");
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.disconnect();
        }

    }
}
