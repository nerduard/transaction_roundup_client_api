package com.eduards_project;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import javax.sound.sampled.SourceDataLine;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class RequestHandler {
    final String BASE_API_URL = "https://api-sandbox.starlingbank.com/";
    final String ACCESS_TOKEN = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5LbIBD8lS2dly3JlvW65ZYfyAcMMNiUEVCAvNlK5d-DBLIsryvH6Z5HNzPSn0J6XwwFWEk4jubDB3BK6jMFff1gZizeCz_RmIFNTQUyJEA5kLrvGgJH7AnCiQHv-ZGCiMn42xZD1TRV27dVW74XEkIGqrKdAWDMTDr8NIqj-yV57M2quuuq2LvlgpO6pRWhp74jJfJTWZetYB2PvYO5ok4VfVfTA2UtaZqyInXTnkjX1xDV0J5V4tR2dK6Itn4wht6nqmOUXsKhIa3oW1KzqowejhXpWS-OvDz05amdDTNjcX6UpJQwZTzywSHwtxW7LPKJhhFfEuHLPhGSow5SSHR7XEkfdkgOOHdR-IBchnuQmBCAXUa8Z27xp5MB32AKF-Okj2skUnN5k3wClZIpKNAsS2PgOGFGB2dUGjQjmTNaSDdCkEYTI4iYNPd3yt-nr0EazSYfzLhaxBFkbqwwCtHnAaxVX_doyRpBcwg4cFQYW6xh5twVw2zEOhToMGr3_6OSjMRZBfGmpA54douPx8LvZC5Fxy6wuhsxQFQDA4vhwuZ4MWXhC3GlUpBNpGBLInKEc_aUgTjZ6XUtKf-BDg60B7apjjChk7oO63ZxgzYFKd5EpHhtMN9IvLdRhq2nMiyKeOiwAMTMR_KM5ipnhFSrpeRxBy1ZLv4rpA27wO-p9ODL8hyoTHq4xZ16cjabrB2Wze2wb5Xp_WLjVy028kWvjUxN2QX5pJCT_H4ZxhCi38nm0ML6TcUf6HLbxDj-MH6PrnP36It6Yj71HQ-4LJD52zNkucjQRD1z8XHnu1mnPGJL1uNxLft7vrbi7z9YF1ByGQYAAA.nAiS1ismBBLbzAxF7Kk9E49hLdWISaQYLfsOEBBvvif-UovuADhqBPTWB9PTRgh9ETbjaidy2MFAjnNbXCJDF4X9OxpjPlezMbXc37P4McXINsGBs2XKnhqC6G87YO9WwGZMMGDSDXdu1Db7YeNHz_6uwSdC5uHFadNo5JkgOW3rbLPP-8Zj0GXMwLsKTf5XBi9yNLss5QB4welZ-KlSZDeWkOQY_RBuJc95yOueyUD7G-U6Jb_RskAy_c39FQcU_mWjIhjTn1OeITQryT3QkTu_9J_6Zua7FNfyrmXMiP1g1fPub0TwVf9BtIDNMr6GgPoirfPLmM5G6mcFV85NSzlERJLEC7BHIWlgITMwL_EV_nJGrPlmZKoQzZZXQJuGRd24rI5NWJt3SEj6vhPNpmen0nc7o-y-lbBs5K4lCfx_adUhCbFin0h4sAb0c4rSEC5nsiBprOpvAuVwGvx7O1yEcB5TG9NsrHzmNu7zIr9EYsF2Tt3I7u4wY1jL5cj6KnQXoyXFa3ZCcis4Sfktg34IJP5R7Lhfe-S9140FucJxOegoSz64n9zC2dOliAOREkdVUNEvD0RQmssUw21MmKqlTnWWgNguojN7aqrT64ou8S6-MxOkROVfyMtaO_f01GOLuoiAYtVx8EBhwUHSz8SaOZLeyIeNKntCHiYcIa0";
    final String ACCOUNT_HOLDER_UID = "c14881ce-7dfd-47b1-b598-0ed50407fc8d";
    final String ACCOUNT_UID = "591be6c6-d37f-40ae-a9c7-caf1bdc0c4e0";
    final String DEFAULT_CATEGORY = "c131a573-99b3-48c0-b123-318f8d70019a";

    public RequestHandler() {
    }

    public int getTransactions() throws IOException {
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // System.out.println(dtf.format(now));
        LocalDateTime last_week = now.minusDays(7);
        String request_date = formatRequestDate(last_week);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String request_uri = this.BASE_API_URL + "api/v2/feed/account/" +
                    this.ACCOUNT_UID + "/category/" + this.DEFAULT_CATEGORY +
                    "?changesSince=" + request_date;
            HttpGet request = new HttpGet(request_uri);

            // add request headers
            request.addHeader("Authorization",
                    "Bearer " + this.ACCESS_TOKEN);
            request.addHeader("Accept", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                // System.out.println(response.getProtocolVersion()); // HTTP/1.1
                // System.out.println(response.getStatusLine().getStatusCode()); // 200
                // System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK

                int sum_of_roundups = 0;
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    JSONObject obj = new JSONObject(result);
                    JSONArray arr = obj.getJSONArray("feedItems");

                    for (int i = 0; i < arr.length(); i++) {
                        String direction = arr.getJSONObject(i).getString("direction");

                        JSONObject amount_dict = arr.getJSONObject(i).getJSONObject("amount");
                        int amount = amount_dict.getInt("minorUnits");

                        if (direction.equals("IN")) {
                            System.out.println(direction);
                            System.out.println(amount);
                            System.out.println(getRoundupValue(amount));
                            System.out.println("_____________");

                            sum_of_roundups += getRoundupValue(amount);
                        }
                    }
                }
                return sum_of_roundups;
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    static int getRoundupValue(int minorUnits) {
        if (minorUnits > 0) {
            int pence = minorUnits % 100;
            if (pence > 0) {
                int pence_to_pound = 100 - pence;
                return pence_to_pound;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    static String formatRequestDate(LocalDateTime input) {
        String request_date = input.getYear() + "-";
        if (input.getMonthValue() >= 10) {
            request_date += input.getMonthValue();
        } else {
            request_date += "0";
            request_date += input.getMonthValue();
        }
        request_date += "-";

        if (input.getDayOfMonth() >= 10) {
            request_date += input.getDayOfMonth();
        } else {
            request_date += "0";
            request_date += input.getDayOfMonth();
        }
        request_date += "T";

        if (input.getHour() >= 10) {
            request_date += Integer.toString(input.getHour());
        } else {
            request_date += "0";
            request_date += Integer.toString(input.getHour());
        }
        request_date += ":";

        if (input.getMinute() >= 10) {
            request_date += Integer.toString(input.getMinute());
        } else {
            request_date += "0";
            request_date += Integer.toString(input.getMinute());
        }
        request_date += ":";

        if (input.getSecond() >= 10) {
            request_date += Integer.toString(input.getSecond());
        } else {
            request_date += "0";
            request_date += Integer.toString(input.getSecond());
        }

        request_date += ".000Z";
        return request_date;
    }

}
