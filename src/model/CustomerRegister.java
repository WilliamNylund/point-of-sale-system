package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CustomerRegister {

    private static CustomerRegister instance = new CustomerRegister();

    private CustomerRegister(){

    }

    public static CustomerRegister getInstance(){
        return instance;
    }

    //returns customer object with customerNo
    public Customer findByCustomerNo(int customerNo){
        Customer customer = new Customer();
        try{
            URL url = new URL("http://localhost:9004/rest/findByCustomerNo/" + customerNo);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("customer");
            Node node = nList.item(0);
            Element eElement = (Element) node;

            customer.setCustomerNo(Integer.parseInt(eElement.getAttribute("customerNo")));
            customer.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
            customer.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
            try{
                customer.setBirthDate(LocalDate.parse(eElement.getElementsByTagName("birthdate").item(0).getTextContent()));
                //customer.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(eElement.getElementsByTagName("birthDate").item(0).getTextContent()));
            } catch(Exception e){
                System.out.println("couldnt parse date");
            }
            customer.setStreetAddress(eElement.getElementsByTagName("streetAddress").item(0).getTextContent());
            customer.setPostalCode(eElement.getElementsByTagName("postalCode").item(0).getTextContent());
            customer.setPostOffice(eElement.getElementsByTagName("postOffice").item(0).getTextContent());
            customer.setCountry(eElement.getElementsByTagName("country").item(0).getTextContent());


            customer.setBonusCard(getBonusCardFromXml(eElement));

            con.disconnect();
            return customer;

        } catch(IOException | ParserConfigurationException | SAXException e) {
            System.out.println("findCustomer failed");
            return null;
        }
    }

    //returns customer object with customerNo
    public Customer findByCustomerBonusCard(int id, int goodThruYear, int goodThruMonth){
        Customer customer = new Customer();
        try{
            URL url = new URL("http://localhost:9004/rest/findByBonusCard/" + id + "/" + goodThruYear + "/" + goodThruMonth);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            InputStream xml = con.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("customer");
            Node node = nList.item(0);
            Element eElement = (Element) node;


            customer.setCustomerNo(Integer.parseInt(eElement.getAttribute("customerNo")));
            customer.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
            customer.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
            try{
                customer.setBirthDate(LocalDate.parse(eElement.getElementsByTagName("birthdate").item(0).getTextContent()));
                //customer.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(eElement.getElementsByTagName("birthDate").item(0).getTextContent()));
            } catch(Exception e){
                System.out.println("couldnt parse date");
            }
            customer.setStreetAddress(eElement.getElementsByTagName("streetAddress").item(0).getTextContent());
            customer.setPostalCode(eElement.getElementsByTagName("postalCode").item(0).getTextContent());
            customer.setPostOffice(eElement.getElementsByTagName("postOffice").item(0).getTextContent());
            customer.setCountry(eElement.getElementsByTagName("country").item(0).getTextContent());


            customer.setBonusCard(getBonusCardFromXml(eElement));
            con.disconnect();

            System.out.println(customer.getBonusCard().getHolderName());
            System.out.println(customer.getStreetAddress());
            return customer;

        } catch(IOException | ParserConfigurationException | SAXException e) {
            System.out.println("findCustomer failed");
            return null;
        }
    }


    private BonusCard getBonusCardFromXml(Element eElement){
        BonusCard bonusCard = new BonusCard();

        bonusCard.setNumber(eElement.getElementsByTagName("number").item(0).getTextContent());
        bonusCard.setGoodThruMonth(eElement.getElementsByTagName("goodThruMonth").item(0).getTextContent());
        bonusCard.setGoodThruYear(eElement.getElementsByTagName("goodThruYear").item(0).getTextContent());
        bonusCard.setBlocked(Boolean.valueOf(eElement.getElementsByTagName("number").item(0).getTextContent()));
        bonusCard.setExpired(Boolean.valueOf(eElement.getElementsByTagName("number").item(0).getTextContent()));
        bonusCard.setHolderName(eElement.getElementsByTagName("holderName").item(0).getTextContent());

        return bonusCard;
    }

    public void run(){
        try{
            Runtime.getRuntime().exec("java -jar CustomerRegister.jar");
        } catch(Exception e){
            System.out.println("Couldnt start CustomerRegister.jar");
        }
    }

}
