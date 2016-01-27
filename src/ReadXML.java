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
 * Created by id12jzn on 2016-01-10.
 *
 * Denna klass läser in data från en xml-fil/sida från angivna taggar. Den sparar datat i ModelOffers-klassen och
 * skapar en lista av alla Offers den har läst in
 */
public class ReadXML {

    List offers = new ArrayList<ModelOffers>();
    ModelOffers model;

    /**
     * Läser in data genom en SAXParser
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     */
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
            boolean bCityName = false;
            boolean bRoomDescription = false;

            /**
             * Metoden kallas på varje gång parsern läser in en start-tagg '<' och identifierar vilken tagg den hittat
             *
             * @param uri
             * @param localName
             * @param qName
             * @param attributes
             * @throws SAXException
             */
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
                if(qName.contentEquals("CityName")) {
                    bCityName = true;
                }
                if(qName.contentEquals("RoomDescription")) {
                    bRoomDescription = true;
                }

                }

            /**
             * Läser in data som finns i en huvudtagg
             *
             * @param uri
             * @param localName
             * @param qName
             * @throws SAXException
             */
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if(qName.contentEquals("Offer")) {
                    offers.add(model);
                }
            }

            /**
             * Läser in de element som finns mellan en start och en slut-tagg
             *
             * @param ch
             * @param start
             * @param length
             * @throws SAXException
             */
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
                    if(bCityName) {
                        model.setCityName(new String(ch, start, length));
                        bCityName = false;
                    }
                    if(bRoomDescription) {
                        model.setRoomDescription(new String(ch, start, length));
                        bRoomDescription = false;
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


