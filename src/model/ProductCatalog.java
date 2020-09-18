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

public class ProductCatalog {

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
            System.out.println(item.toString());
            con.disconnect();
            return item;


        } catch(IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void getProductByKeyWord(String keyword){
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
                System.out.println(item.toString());
            }

            con.disconnect();

        } catch(IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void getProductByName(String name){
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
            System.out.println(item.toString());
            con.disconnect();

        } catch(IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
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
                System.out.println(item.toString());
            }

            con.disconnect();

        } catch(IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

}
