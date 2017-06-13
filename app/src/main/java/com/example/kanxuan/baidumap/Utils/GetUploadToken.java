package com.example.kanxuan.baidumap.Utils;

/**
 * Created by kanxuan on 2017/6/4.
 */

public class GetUploadToken {
    private final static String UploadPolocy = "{\"scope\":\"kanxuan\",\"deadline\":1496629422}";
    private final static String AccessKey = "0kgFvvZPnN4KioE-Dah_t2MtLWH0S3ZREmXArbMI";
    private final static String SecretKey = "_ZXb50vG-9ZWP6METCCYfGrt632UlaGr6uZZdqNi";

    public static String get() {

        try {

            String encodedPolocy = Base64Utils.encode(UploadPolocy.getBytes())
                    .replaceAll("\\+", "-")
                    .replaceAll("/", "_");

            String HMAC_SHA1_UrlBase64Encoded = Base64Utils
                    .encode(
                            HMACSHA1.HmacSHA1Encrypt(
                                    encodedPolocy,
                                    SecretKey))
                    .replaceAll("\\+", "-")
                    .replaceAll("/", "_");


            String UploadToken = AccessKey +":"+HMAC_SHA1_UrlBase64Encoded+":"+encodedPolocy;

            return UploadToken;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
