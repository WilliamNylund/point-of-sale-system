package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CardReader {


    public void waitForPayment(double amount){
        try{
            System.out.println("Waiting for payment");
            URL url = new URL("http://localhost:9002/cardreader/waitForPayment");
            String data = "amount=" + amount;
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(data.getBytes("UTF-8"));
            con.getInputStream();
            con.disconnect();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("something went terrible wrong in CardReader.waitForPayment :(((");
        }
    }

    public void abort(){
        try{
            System.out.println("cardreader aborting");
            URL url = new URL("http://localhost:9002/cardreader/abort");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getInputStream();
            con.disconnect();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("something went terrible wrong in CardReader.abort() :(((");
        }
    }

    public void reset(){
        try{
            System.out.println("cardreader aborting");
            URL url = new URL("http://localhost:9002/cardreader/reset");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getInputStream();
            con.disconnect();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("something went terrible wrong in CardReader.abort() :(((");
        }
    }

    public String getStatus() {
        try{
            URL url = new URL("http://localhost:9002/cardreader/status");
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
            return "something went terrible wrong in CardReader.getStatus() :(((";
        }
    }

    public String getResult() {
        try{
            URL url = new URL("http://localhost:9002/cardreader/result");
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
            return "something went terrible wrong in CardReader.getResult() :(((";
        }
    }
}
