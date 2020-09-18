package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);

            //doc is now a Document con containing xml data

            con.disconnect();
            return "yoo";

        } catch(IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return "something went terrible wrong in CardReader.getResult() :(((";
        }
    }
}
