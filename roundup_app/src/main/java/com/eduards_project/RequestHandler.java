package com.eduards_project;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import java.util.UUID;
import java.time.LocalDateTime;

public class RequestHandler {
    final String BASE_API_URL = "https://api-sandbox.starlingbank.com/";
    final String ACCESS_TOKEN = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lS3Oqy1YbPO45ZYfyAcMmsFWWUiUJHazlcq_Z0DCGK8rx-meR7dm4E-mvM_aDEYlkAb75gM4rcy5A3N9k3bIXjM_dZxBp0PXkyQBHYI4NPVJQEmNIDhKwAbLDnpOpt9j1hanU1HX9bEoXzMFIQH5ezEDIKWdTPhpNZL7pZB7y-JQ1wX3rrBHcai6QnTHphY54TE_5FUva-TewV7JxIqGSpRFLkVVFbU4dGUvOtmdBKvI6xJOWGLNFWzrh5TkfawqWXoO7ydR9U0lDtyAPZSFaGTTl5i_N_mxmg1LO9L8KFGpkNp6wtYR4MuKXRb5wsBAT4nwNT4QCskE1Stye1wrH3ZIChAdC28JVbgFkQkB5GWgW-YWfzoV6AWmcLFOeV6jUAbVh8IJdEzuQIORSZoEh0JaE5zVcdCMJM6aXrkBgrJG2F70k0F_o_xt-hrE0XLywQ6rRRpApcaaWIg5tzCO-usWLVkDGIRALZImbrGGiXNXCrOR0VFPjli7_x8VZURu1MA3pUygs1t83Bd-J1MpOXmB1d1AAVgNtJLDhU3xYmqEL6KVikEyEYMtSagBzslTAniyM-taYv4dHRwYD3JTzbDoJn1t1-3SBm0KYryJiPHaYL4RvrdBha2ntpJF3HVYAGHnI3lEU5WzvdKrpehxBy1Zjv8Vagy7wO-p-ODL8hzoRHr44J16cbabrB2WzO2wb5Xx_bjxsxYb-aTXRsam8kI4aUKR3i_BFAL7ncYUjrB-U_wDXW5bWId34_foOnePPqkX9tPc8EDLAqX_eIRG7BM0dV46ftz5btYp99iSdX9cy_4ery37-w-s-QVQGQYAAA.fDgQUSb9aAnQYF-as9BmhHK5kEEUIQikfPaqXBHZ_20NT8Bbs9C_dTm8o54DVH__DsdIXAw6h28BLo7aktTTWSL1RA8mtcAtzEwqfcXkbiLlRQHnLFZKM2oKJlKEkSTSi49K7xxnQH7DMjT4zllxsDYToAiTxEUzeNCAWJvqkx-aUxgjiae6qdaPujpP_I-ik8Eb-VkefB2v3H_Fjz4BLDhca904JYa0ypZERLAyxxybKnJDXs0EsVLYAIPYeFfxEm61qwK3sm8NugO8S5-h52kq-lGJLOQJE0yO-5V4YSDs3klg7X9626uwGiaMpCK70yA98BgKiDH6Y3Re3yfsuzwp2wQbuAuLTJ_c8vwEJXG_5z9Dnx7Fm3HGXGkU5G_7K8zCdInPV1_-TdmTiKzNQDp37cnqadtQhgQf2Pp8XLI6zoa2GwYczYMLMPVaCuUmMOkluy41HYM96g9Lg5utrycvvBAPWUTMy_LYGj7QB57YIstSW2RNLz-pUAxQdLpeibx53VFokCiaxERrCGMbkxyiu1vWcuwAcYuXeV8vSr5bA5BryAqtWOoH5MocDh180NHIhPY89a_sqvx9KiQHV7xrsSWKh9hX-IyfNGD5SV2xc2KEwscM_mc1cO_hfu36QbE694ZMMxBJM4bAHa4Iqj_InjQwGYR_waS0OJp3Dd8";
    final String ACCOUNT_HOLDER_UID = "c14881ce-7dfd-47b1-b598-0ed50407fc8d";
    final String ACCOUNT_UID = "591be6c6-d37f-40ae-a9c7-caf1bdc0c4e0";
    final String DEFAULT_CATEGORY = "c131a573-99b3-48c0-b123-318f8d70019a";

    public RequestHandler() {
    }

    // retrieves all transactions over the past 7 days
    // and sums up all roundup values for all transactions
    // which have gone 'OUT'
    public int getSumOfRoundups() throws IOException {
        LocalDateTime now = LocalDateTime.now();
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
                System.out.println("Retrieve transactions status: " + response.getStatusLine().toString()); // HTTP/1.1
                                                                                                            // 200 OK

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

                        if (direction.equals("OUT")) {
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

    // return number of Savings Goal pots the account
    // holder has in their current account.
    // This function does not get called for the
    // current logic present in App.java
    public int getSavingsGoalListSize() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String request_uri = this.BASE_API_URL + "api/v2/account/" +
                    this.ACCOUNT_UID + "/savings-goals";
            HttpGet request = new HttpGet(request_uri);

            // add request headers
            request.addHeader("Authorization",
                    "Bearer " + this.ACCESS_TOKEN);
            request.addHeader("Accept", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String result = EntityUtils.toString(entity);

                    System.out.println(result);
                    JSONObject obj = new JSONObject(result);
                    JSONArray arr = obj.getJSONArray("savingsGoalList");
                    return arr.length();
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return -1;
    }

    // Create a new Savings Goal pot and return its UID
    public String createAndReturnSavingsGoalUid(int target) throws IOException {
        String result = "";
        String request_uri = this.BASE_API_URL + "api/v2/account/" +
                this.ACCOUNT_UID + "/savings-goals";

        HttpPut put = new HttpPut(request_uri);

        // add request headers
        put.addHeader("Authorization",
                "Bearer " + this.ACCESS_TOKEN);
        put.addHeader("Accept", "application/json");

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"name\":\"Roundup Demo\",");
        json.append("\"currency\":\"GBP\",");
        json.append("\"target\": {");
        json.append("\"currency\":\"GBP\",");
        json.append("\"minorUnits\":" + Integer.toString(target));
        json.append("},");
        json.append("\"base64EncodedPhoto\":\"string\"");
        json.append("}");

        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        put.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(put)) {
            System.out.println("Create Savings Goal status: " + response.getStatusLine().toString()); // HTTP/1.1 200 OK

            result = EntityUtils.toString(response.getEntity());
        }

        JSONObject obj = new JSONObject(result);
        String savingsGoalUid = obj.getString("savingsGoalUid");
        System.out.println(savingsGoalUid + " ---> created successfully!");
        return savingsGoalUid;
    }

    // add amount of pence to Savings Goal
    public void addToSavingsGoal(String savingsGoalUid, int amount) throws IOException {
        String result = "";
        UUID uuid = UUID.randomUUID();
        String transferUid = uuid.toString();
        String request_uri = this.BASE_API_URL + "api/v2/account/" +
                this.ACCOUNT_UID + "/savings-goals/" + savingsGoalUid + "/add-money/" + transferUid;

        HttpPut put = new HttpPut(request_uri);

        // add request headers
        put.addHeader("Authorization",
                "Bearer " + this.ACCESS_TOKEN);
        put.addHeader("Accept", "application/json");

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"amount\": {");
        json.append("\"currency\":\"GBP\",");
        json.append("\"minorUnits\":" + Integer.toString(amount));
        json.append("}");
        json.append("}");

        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        put.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(put)) {
            System.out.println("Add to Savings Goal status: " + response.getStatusLine().toString()); // HTTP/1.1 200 OK

            result = EntityUtils.toString(response.getEntity());
        }

        JSONObject obj = new JSONObject(result);
    }

    // return number of pence to the closest pound
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

    // format a LocalDateTime object to a date format
    // compatible with Starling Bank's Open API
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
