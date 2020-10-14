package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {

    private ObservableList<Item> catalog = FXCollections.observableArrayList();

    private static ProductCatalog instance = new ProductCatalog();

    private ProductCatalog() {

    }

    public static ProductCatalog getInstance() {
        return instance;
    }


    public Item getProductByBarCode(int barCode) {
        Item item = new Item();

        try {
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
            item.setBestBefore(createRandomDate(2020, 2020));
            NodeList keywords = eElement.getElementsByTagName("keyword");
            for (int i = 0; i < keywords.getLength(); i++) {
                item.getKeywords().add(keywords.item(i).getTextContent());
            }
            con.disconnect();
            return item;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            errorMessage(Integer.toString(barCode));
            System.out.println("error404");
            e.printStackTrace();
            return null;
        }
    }


    public List getProductByKeyWord(String keyword) {

        List<Item> itemList = new ArrayList<>();

        try {
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

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                Element eElement = (Element) node;
                Item item = new Item();
                item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                item.setVat(Double.parseDouble(eElement.getElementsByTagName("vat").item(0).getTextContent()));
                item.setBarCode(Integer.parseInt(eElement.getElementsByTagName("barCode").item(0).getTextContent()));
                item.setId(Integer.parseInt(eElement.getAttribute("id")));
                item.setPrice(3.00); //TODO: remove
                item.setBestBefore(createRandomDate(2020, 2020));
                NodeList keywords = eElement.getElementsByTagName("keyword");
                for (int j = 0; j < keywords.getLength(); j++) {
                    item.getKeywords().add(keywords.item(j).getTextContent());
                }
                itemList.add(item);
            }
            con.disconnect();
            return itemList;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            errorMessage(keyword);
            e.printStackTrace();
            return null;
        }
    }

    public Item getProductByName(String name) {
        try {
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
            item.setBestBefore(createRandomDate(2020, 2020));
            NodeList keywords = eElement.getElementsByTagName("keyword");
            for (int i = 0; i < keywords.getLength(); i++) {
                item.getKeywords().add(keywords.item(i).getTextContent());
            }
            con.disconnect();
            return item;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            errorMessage(name);
            e.printStackTrace();
            return null;
        }
    }

    public void getAllProducts() {
        try {
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

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                Element eElement = (Element) node;
                Item item = new Item();
                item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                item.setVat(Double.parseDouble(eElement.getElementsByTagName("vat").item(0).getTextContent()));
                item.setBarCode(Integer.parseInt(eElement.getElementsByTagName("barCode").item(0).getTextContent()));
                item.setId(Integer.parseInt(eElement.getAttribute("id")));
                item.setPrice(3.00); //TODO: remove
                item.setBestBefore(createRandomDate(2020, 2020));
                NodeList keywords = eElement.getElementsByTagName("keyword");
                for (int j = 0; j < keywords.getLength(); j++) {
                    item.getKeywords().add(keywords.item(j).getTextContent());
                }
                catalog.add(item);
            }

            con.disconnect();

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Productcatalog.jar couldnt be started");
        }
    }

    public ObservableList<Item> getCatalog() {
        return catalog;
    }

    public void setCatalog(ObservableList<Item> catalog) {
        this.catalog = catalog;
    }

    private void errorMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No matches");
        alert.setHeaderText("No matches");
        alert.setContentText("'" + msg + "'" + " did not match any items\nPlease try another search term");
        alert.showAndWait();
    }

    public int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public LocalDate createRandomDate(int startYear, int endYear) {
        int day;
        int month;
        int year;
        while (true) {
            day = createRandomIntBetween(1, 28);
            month = createRandomIntBetween(1, 12);
            year = createRandomIntBetween(startYear, endYear);
            if (LocalDate.of(year, month, day).isAfter(LocalDate.now())) {
                break;
            }
        }
        return LocalDate.of(year, month, day);
    }

    public void uppdatePrices(List<Item> allitems) throws IOException, ParserConfigurationException, SAXException {
        System.out.println("----------");




       /* for (int i=0;i<nList.getLength();i++) {
            Node node=nList.item(i);
            System.out.println(node.getAttributes());
            System.out.println(node.getTextContent());
            System.out.println(node.getFirstChild());

            }
            */


        }





    }

