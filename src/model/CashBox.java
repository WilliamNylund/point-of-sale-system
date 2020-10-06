package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


//Singleton CashBox
public class CashBox {

    private static CashBox instance = new CashBox();

    private CashBox(){

    }

    public static CashBox getInstance(){
        return instance;
    }

    public void open() {
        try{
            System.out.println("opening CashBox!");
            URL url = new URL("http://localhost:9001/cashbox/open");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getInputStream();
            System.out.println("CashBox opened!");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("something went terrible wrong in CashBox.open() :(((");
        }
    }

    public String getStatus() {
        try{
            URL url = new URL("http://localhost:9001/cashbox/status");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch(IOException e){
            e.printStackTrace();
            return "something went terrible wrong in CashBox.getStatus() :(((";
        }
    }

    public void run(){

        try{
            Runtime.getRuntime().exec("java -jar CashBox.jar");
        } catch(Exception e){
            System.out.println("Couldnt start CashBox.jar");
        }
    }
}
