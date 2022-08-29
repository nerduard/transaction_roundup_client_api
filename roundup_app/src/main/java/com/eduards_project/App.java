package com.eduards_project;

import java.io.IOException;

import com.eduards_project.RequestHandler;

public class App {
    public static void main(String[] args) throws IOException {

        // CloseableHttpClient httpClient = HttpClients.createDefault();

        // try {

        //     HttpGet request = new HttpGet("https://api-sandbox.starlingbank.com/api/v2/accounts");

        //     // add request headers
        //     request.addHeader("Authorization", "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5LbIBD8lS2dly3JlvW65ZYfyAcMMNiUEVCAvNlK5d-DBLIsryvH6Z5HNzPSn0J6XwwFWEk4jubDB3BK6jMFff1gZizeCz_RmIFNTQUyJEA5kLrvGgJH7AnCiQHv-ZGCiMn42xZD1TRV27dVW74XEkIGqrKdAWDMTDr8NIqj-yV57M2quuuq2LvlgpO6pRWhp74jJfJTWZetYB2PvYO5ok4VfVfTA2UtaZqyInXTnkjX1xDV0J5V4tR2dK6Itn4wht6nqmOUXsKhIa3oW1KzqowejhXpWS-OvDz05amdDTNjcX6UpJQwZTzywSHwtxW7LPKJhhFfEuHLPhGSow5SSHR7XEkfdkgOOHdR-IBchnuQmBCAXUa8Z27xp5MB32AKF-Okj2skUnN5k3wClZIpKNAsS2PgOGFGB2dUGjQjmTNaSDdCkEYTI4iYNPd3yt-nr0EazSYfzLhaxBFkbqwwCtHnAaxVX_doyRpBcwg4cFQYW6xh5twVw2zEOhToMGr3_6OSjMRZBfGmpA54douPx8LvZC5Fxy6wuhsxQFQDA4vhwuZ4MWXhC3GlUpBNpGBLInKEc_aUgTjZ6XUtKf-BDg60B7apjjChk7oO63ZxgzYFKd5EpHhtMN9IvLdRhq2nMiyKeOiwAMTMR_KM5ipnhFSrpeRxBy1ZLv4rpA27wO-p9ODL8hyoTHq4xZ16cjabrB2Wze2wb5Xp_WLjVy028kWvjUxN2QX5pJCT_H4ZxhCi38nm0ML6TcUf6HLbxDj-MH6PrnP36It6Yj71HQ-4LJD52zNkucjQRD1z8XHnu1mnPGJL1uNxLft7vrbi7z9YF1ByGQYAAA.nAiS1ismBBLbzAxF7Kk9E49hLdWISaQYLfsOEBBvvif-UovuADhqBPTWB9PTRgh9ETbjaidy2MFAjnNbXCJDF4X9OxpjPlezMbXc37P4McXINsGBs2XKnhqC6G87YO9WwGZMMGDSDXdu1Db7YeNHz_6uwSdC5uHFadNo5JkgOW3rbLPP-8Zj0GXMwLsKTf5XBi9yNLss5QB4welZ-KlSZDeWkOQY_RBuJc95yOueyUD7G-U6Jb_RskAy_c39FQcU_mWjIhjTn1OeITQryT3QkTu_9J_6Zua7FNfyrmXMiP1g1fPub0TwVf9BtIDNMr6GgPoirfPLmM5G6mcFV85NSzlERJLEC7BHIWlgITMwL_EV_nJGrPlmZKoQzZZXQJuGRd24rI5NWJt3SEj6vhPNpmen0nc7o-y-lbBs5K4lCfx_adUhCbFin0h4sAb0c4rSEC5nsiBprOpvAuVwGvx7O1yEcB5TG9NsrHzmNu7zIr9EYsF2Tt3I7u4wY1jL5cj6KnQXoyXFa3ZCcis4Sfktg34IJP5R7Lhfe-S9140FucJxOegoSz64n9zC2dOliAOREkdVUNEvD0RQmssUw21MmKqlTnWWgNguojN7aqrT64ou8S6-MxOkROVfyMtaO_f01GOLuoiAYtVx8EBhwUHSz8SaOZLeyIeNKntCHiYcIa0");
        //     request.addHeader("Accept", "application/json");

        //     CloseableHttpResponse response = httpClient.execute(request);

        //     try {

        //         // Get HttpResponse Status
        //         System.out.println(response.getProtocolVersion()); // HTTP/1.1
        //         System.out.println(response.getStatusLine().getStatusCode()); // 200
        //         System.out.println(response.getStatusLine().getReasonPhrase()); // OK
        //         System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK

        //         HttpEntity entity = response.getEntity();
        //         if (entity != null) {
        //             // return it as a String
        //             String result = EntityUtils.toString(entity);
        //             JSONObject obj = new JSONObject(result);
        //             // System.out.println(result);
        //             // String accountUid = obj.getJSONObject("accounts").getString("accountUid");
        //             System.out.println(obj.getJSONArray("accounts"));
        //         }

        //     } finally {
        //         response.close();
        //     }
        // } finally {
        //     httpClient.close();
        // }

        RequestHandler handler = new RequestHandler();
        int sum_of_roundups = handler.getTransactions();
        System.out.println("Sum of roundups: " + sum_of_roundups);
    }
}
