import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zacke on 2016-01-10.
 */
public class ReadXML {

    List offers = new ArrayList<ModelOffers>();
    ModelOffers model;

    public void readFile() throws ParserConfigurationException, SAXException {

        // SAX Parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);

        //Obtain objext for parser
        SAXParser saxParser = factory.newSAXParser();

        // Default handler for SAX
        DefaultHandler handler = new DefaultHandler() {

            boolean bCampaign = false;
            boolean bDepaurte = false;
            boolean bOutDate = false;
            boolean bDestinationName = false;
            boolean bHotelImage = false;
            boolean bCurrentPrice = false;

            //Method is called every time the parser gets an open tag '<'
            //Identifies which tag is being open
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                if(qName.contentEquals("Offer")) {
                    model = new ModelOffers();
                }
                if(qName.contentEquals("CampaignName")) {
                    bCampaign = true;
                }
                if(qName.contentEquals("DepartureName")) {
                    bDepaurte = true;
                }
                if(qName.contentEquals("OutDate")) {
                    bOutDate = true;
                }
                if(qName.contentEquals("DestinationName")) {
                    bDestinationName = true;
                }
                if(qName.contentEquals("HotelImage")) {
                    bHotelImage = true;
                }
                if(qName.contentEquals("CurrentPrice")) {
                    bCurrentPrice = true;
                }

                }

            //Print data stored between tags
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if(qName.contentEquals("Offer")) {
                    offers.add(model);
                }
            }

                //Print data stored between tags
                public void characters(char ch[], int start, int length) throws SAXException {

                    if(bCampaign) {
                        model.setCampaignName(new String(ch, start, length));
                        bCampaign = false;
                    }
                    if(bDepaurte) {
                        model.setDepartureName(new String(ch, start, length));
                        bDepaurte = false;
                    }
                    if(bOutDate) {
                        model.setOutDate(new String(ch, start, length));
                        bOutDate = false;
                    }
                    if(bDestinationName) {
                        model.setDestinationName(new String(ch, start, length));
                        bDestinationName = false;
                    }
                    if(bHotelImage) {
                        model.setHotelImage(new String(ch, start, length));
                        bHotelImage = false;
                    }
                    if(bCurrentPrice) {
                        //model.setCurrentPrice(Integer.parseInt(new String(ch, start, length).split("\\s+")));
                        String price = new String(ch, start, length);
                        String priceString[] = price.split("\\s+");
                        model.setCurrentPrice(Integer.parseInt(priceString[0]));
                        bCurrentPrice = false;
                    }
                }
            };
        try {
            saxParser.parse("131209_15-55-00.xml", handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ModelOffers> getList() {
        return offers;


    }
}


