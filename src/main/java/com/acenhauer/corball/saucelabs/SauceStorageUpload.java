package com.acenhauer.corball.saucelabs;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//curl -u <sauce_username>:<sauce_access_key> -X POST -H "Content-Type: application/octet-stream" https://saucelabs.com/rest/v1/storage/<sauce_username>/<upload_filename>?overwrite=true --data-binary @<path/to/your_file_name>
public class SauceStorageUpload {

    private static final Logger LOGGER = LogManager.getLogger(SauceStorageUpload.class);

    public SauceStorageUpload() {
    }

    public void uploadFile(String sauceUsername, String sauceAccessKey, String path) {
        String[] parts = path.split("/");
        String fileName = parts[parts.length - 1];
        String cmd = "curl -u " + sauceUsername + ":" + sauceAccessKey + " -X POST -H \"Content-Type: application/octet-stream\" https://saucelabs.com/rest/v1/storage/" + sauceUsername + "/" + fileName + "?overwrite=true --data-binary @" + path;
        LOGGER.info(cmd);
        String url = "https://" + sauceUsername + ":" + sauceAccessKey + "@saucelabs.com/rest/v1/storage/" + sauceUsername + "/" + fileName + "?overwrite=true";


        ProcessBuilder p = new ProcessBuilder("curl", "-u", sauceUsername + ":" + sauceAccessKey, "-X",
                "POST", "-H", "Content-Type: application/octet-stream", "https://saucelabs.com/rest/v1/storage/" + sauceUsername + "/" + fileName + "?overwrite=true", "--data-binary", "@" + path);
        try {
            final Process proc = p.start();
            InputStream inputStream = proc.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost("https://" + sauceUsername + ":" + sauceAccessKey + "@saucelabs.com/rest/v1/storage/" + sauceUsername + "/" + fileName + "?overwrite=true");
//        try {
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//            nameValuePairs.add(new BasicNameValuePair("H", "Content-Type: application/octet-stream"));
//            nameValuePairs.add(new BasicNameValuePair("data-binary", "@" + path));
//            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//            HttpResponse response = client.execute(post);
//            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            String line = "";
//            while ((line = rd.readLine()) != null) {
//                System.out.println(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
