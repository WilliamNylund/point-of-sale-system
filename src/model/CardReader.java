package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class CardReader {

    private static CardReader instance = new CardReader();

    private CardReader(){

    }

    public static CardReader getInstance(){
        return instance;
    }

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
            System.out.println("cardreader resetting");
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

    public void getResult() {
        try{
            URL url = new URL("http://localhost:9002/cardreader/result");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("result");
            Node node = nList.item(0);
            Element eElement = (Element) node;

            //TODO: set these to transaction?
            String paymentCardNumber = getPropertiesSafely(eElement, "paymentCardNumber");
            String paymentState = getPropertiesSafely(eElement, "paymentState");
            String paymentCardType = getPropertiesSafely(eElement, "paymentCardType");

            //
            String bonusCardNumber = getPropertiesSafely(eElement, "bonusCardNumber");
            String bonusState = getPropertiesSafely(eElement, "bonusState");

            System.out.println(paymentCardNumber);
            System.out.println(paymentCardType);
            System.out.println(paymentState);
            System.out.println(bonusState);
            System.out.println(bonusCardNumber);
            con.disconnect();

        } catch(SAXException SAXE){
            System.out.println("Card has not been swiped!");
        } catch(IOException | ParserConfigurationException e) {
            e.printStackTrace();
            System.out.println("something went wrong in cardreader");
        }
    }

    public void run(){
        try{
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "CardReader.jar");
            pb.directory(new File(System.getenv("ENV_CARD")));
            Process p = pb.start();
        } catch(Exception e){
            e.printStackTrace();
        }

        /*try{
            Runtime.getRuntime().exec("java -jar CardReader.jar");
        } catch(Exception e){
            System.out.println("Couldnt start CardReader.jar");
        }*/
    }

    private String getPropertiesSafely(Element eElement, String tagName){
        try{
            return eElement.getElementsByTagName(tagName).item(0).getTextContent();
        } catch(Exception e){
            System.out.println("Could not find " + tagName);
            return null;
        }
    }

    public void listenForPayment(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(getStatus().equals("DONE")){
                    System.out.println("jaus den blev betald");
                    getResult(); //TODO: get Customer object from cardReader and set transaction customer

                    reset();
                    this.cancel();
                } else {
                    System.out.println("waiting for payment yo");
                }
            }
        }, 0, 1000);

    }
}
