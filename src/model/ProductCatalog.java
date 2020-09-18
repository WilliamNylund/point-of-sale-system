package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductCatalog {

    /*Admin UI (web-based):         http://localhost:9003/
Find by barcode:         GET  http://localhost:9003/rest/findByBarCode/{barcode}
   - Case sensitive
Find by keyword:         GET  http://localhost:9003/rest/findByKeyword/{keyword}
   - Case sensitive, accepts only whole keywords
Find by name:            GET  http://localhost:9003/rest/findByName/{name}
   - Case insensitive, asterisks (*) are used as wildcards*/


        public String findBarcode() throws IOException {

            URL url = new URL("http://localhost:9003/rest/findByBarCode/2020");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            System.out.println(in.read());
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            System.out.println(content.toString());
            return content.toString();
        }
}
