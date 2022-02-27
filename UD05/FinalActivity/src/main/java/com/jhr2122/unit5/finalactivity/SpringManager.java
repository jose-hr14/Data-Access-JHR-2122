package com.jhr2122.unit5.finalactivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class will manage the http requests thrown to spring concerning reservations.
 */
public class SpringManager {
    /**
     * Makes a POST http request to spring to save a new reservation
     *
     * @param reservation to save in the database
     * @throws Exception if the post request fails
     */
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
            if (conn.getResponseCode() != 200)
                throw new Exception("Reservation POST failed");
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    /**
     * Makes a DELETE http request to spring to delete a reservation
     * @param reservation
     * @throws Exception if the http request fails
     */
    public void deleteReservation(ReservationsEntity reservation) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://localhost:8080/api-rest/Library/Reservations/" + reservation.getId());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (conn.getResponseCode() != 200)
                throw new Exception("Reservation DELETE failed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }
}
