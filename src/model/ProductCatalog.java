package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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
import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {

    private ObservableList<Item> catalog = FXCollections.observableArrayList();

    private static ProductCatalog instance = new ProductCatalog();

    private ProductCatalog(){

    }

    public static ProductCatalog getInstance(){
        return instance;
    }


    public Item getProductByBarCode(int barCode){
        Item item = new Item();

        try{
            URL url = new URL("http://localhost:9003/rest/findByBarCode/" + barCode);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("product");
            Node node = nList.item(0);
            Element eElement = (Element) node;
            item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
            item.setVat(Double.parseDouble(eElement.getElementsByTagName("vat").item(0).getTextContent()));
            item.setBarCode(Integer.parseInt(eElement.getElementsByTagName("barCode").item(0).getTextContent()));
            item.setId(Integer.parseInt(eElement.getAttribute("id")));
            item.setPrice(3.00);
            NodeList keywords = eElement.getElementsByTagName("keyword");
            for (int i = 0; i < keywords.getLength(); i++){
                item.getKeywords().add(keywords.item(i).getTextContent());
            }
            con.disconnect();
            return item;

        } catch(IOException | ParserConfigurationException | SAXException e) {
            errorMessage(Integer.toString(barCode));
            System.out.println("error404");
            e.printStackTrace();
            return null;
        }
    }


    public List getProductByKeyWord(String keyword){

        List<Item> itemList = new ArrayList<>();

        try{
            URL url = new URL("http://localhost:9003/rest/findByKeyword/" + keyword);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("product");

            for(int i=0; i< nList.getLength(); i++){
                Node node = nList.item(i);
                Element eElement = (Element) node;
                Item item = new Item();
                item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                item.setVat(Double.parseDouble(eElement.getElementsByTagName("vat").item(0).getTextContent()));
                item.setBarCode(Integer.parseInt(eElement.getElementsByTagName("barCode").item(0).getTextContent()));
                item.setId(Integer.parseInt(eElement.getAttribute("id")));
                item.setPrice(3.00); //TODO: remove
                NodeList keywords = eElement.getElementsByTagName("keyword");
                for (int j = 0; j < keywords.getLength(); j++){
                    item.getKeywords().add(keywords.item(j).getTextContent());
                }
                itemList.add(item);
            }
            con.disconnect();
            return itemList;

        } catch(IOException | ParserConfigurationException | SAXException e) {
            errorMessage(keyword);
            e.printStackTrace();
            return null;
        }
    }

    public Item getProductByName(String name){
        try{
            URL url = new URL("http://localhost:9003/rest/findByName/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("product");
            Node node = nList.item(0);
            Element eElement = (Element) node;
            Item item = new Item();
            item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
            item.setVat(Double.parseDouble(eElement.getElementsByTagName("vat").item(0).getTextContent()));
            item.setBarCode(Integer.parseInt(eElement.getElementsByTagName("barCode").item(0).getTextContent()));
            item.setId(Integer.parseInt(eElement.getAttribute("id")));
            item.setPrice(3.00); //TODO: remove
            NodeList keywords = eElement.getElementsByTagName("keyword");
            for (int i = 0; i < keywords.getLength(); i++){
                item.getKeywords().add(keywords.item(i).getTextContent());
            }
            con.disconnect();
            return item;

        } catch(IOException | ParserConfigurationException | SAXException e) {
            errorMessage(name);
            e.printStackTrace();
            return null;
        }
    }

    public void getAllProducts(){
        try{
            URL url = new URL("http://localhost:9003/rest/findByName/*");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("product");

            for(int i=0; i< nList.getLength(); i++){
                Node node = nList.item(i);
                Element eElement = (Element) node;
                Item item = new Item();
                item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                item.setVat(Double.parseDouble(eElement.getElementsByTagName("vat").item(0).getTextContent()));
                item.setBarCode(Integer.parseInt(eElement.getElementsByTagName("barCode").item(0).getTextContent()));
                item.setId(Integer.parseInt(eElement.getAttribute("id")));
                item.setPrice(3.00); //TODO: remove
                NodeList keywords = eElement.getElementsByTagName("keyword");
                for (int j = 0; j < keywords.getLength(); j++){
                    item.getKeywords().add(keywords.item(j).getTextContent());
                }
                catalog.add(item);
            }

            con.disconnect();

        } catch(IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        try{
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "ProductCatalog.jar");
            pb.directory(new File(System.getenv("ENV_CATALOG")));
            Process p = pb.start();
        } catch(Exception e){
            e.printStackTrace();
        }


        /*
        try{
            Runtime.getRuntime().exec("java -Dserver.port=9003 -jar ProductCatalog.jar");
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Productcatalog.jar couldnt be started");
        }*/
    }

    public ObservableList<Item> getCatalog() {
        return catalog;
    }

    public void setCatalog(ObservableList<Item> catalog) {
        this.catalog = catalog;
    }

    private void errorMessage (String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No matches");
        alert.setHeaderText("No matches");
        alert.setContentText("'" + msg + "'" + " did not match any items\nPlease try another search term");
        alert.showAndWait();
    }


}