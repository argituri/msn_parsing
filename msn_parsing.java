import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


/*
        This program should be able to parse msn messenger xml
        logs to a simple readable form and save the new form to a txt-file

        Ohjelman tarkoituksena on lukea msn messengerin xml loki ja tallentaa se txt-tiedostoon


 */



public class msn_parsing {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document luettavaXml = builder.parse(args[0]);
            NodeList msgList = luettavaXml.getElementsByTagName("Message");
            File file = new File(args[0].substring(0,args[0].length()-4) + ".txt");
            FileOutputStream fileOut = new FileOutputStream(file);
            BufferedWriter kirj = new BufferedWriter(new OutputStreamWriter(fileOut));
            for (int i=0;i<msgList.getLength();i++){
                Node m = msgList.item(i);
                if (m.getNodeType()==Node.ELEMENT_NODE){            //Selvitä NodeTypet
                    Element msg = (Element) m;
                    NodeList msgNodes = msg.getChildNodes();
                    for (int j=0;j<msgNodes.getLength();j++){
                        Node from = msgNodes.item(j);
                        if (from.getNodeName()=="From"){
                            Element nimiElem = (Element) from;
                            NodeList fromNodes = nimiElem.getChildNodes();
                            for (int k=0;k<fromNodes.getLength();k++){
                                Node user = fromNodes.item(k);
                                if (user.getNodeName() == "User"){
                                    Element userElem = (Element) user;
                                    String nimi = userElem.getAttribute("FriendlyName");
                                    System.out.println(nimi + " :");
                                    kirj.write(nimi + " :");
                                    kirj.newLine();
                                }
                            }
                            //System.out.println(nimi);
                        }
                        if (from.getNodeName()=="Text"){
                            String viestiStr = from.getFirstChild().getNodeValue();
                            System.out.println(viestiStr + "\n");
                            kirj.write(viestiStr + "\n");
                            kirj.newLine();
                        }
                    }
                    /*NodeList msgElems = m.getChildNodes();
                    Element from = msgElems.getatt;*/
                }
            }
            kirj.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
