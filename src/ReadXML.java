import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by id12jzn on 2016-01-10.
 * <p>
 * Denna klass laser in data fran en xml-fil/sida fran angivna taggar. Den
 * sparar datat i ModelOffers-klassen och
 * skapar en lista av alla Offers den har last in
 */
public class ReadXML {

    private List offers = new ArrayList<ModelOffers>();
    private ModelOffers model;
    private String parseString = "http://www.fritidsresor" +
            ".se/Blandade-Sidor/feeds/tradera/";

    /**
     * Laser in data genom en SAXParser
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void readFile() throws ParserConfigurationException, SAXException,
            IOException {

        // SAX Parser and default handler
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        DefaultHandler handler = new DefaultHandler() {

            private boolean bCampaign = false;
            private boolean bDepaurte = false;
            private boolean bOutDate = false;
            private boolean bDestinationName = false;
            private boolean bHotelImage = false;
            private boolean bCurrentPrice = false;
            private boolean bCityName = false;
            private boolean bRoomDescription = false;

            /**
             * Metoden kallas pa varje gang parsern laser in en start-tagg
             * '<' och identifierar vilken tagg den hittat
             *
             * @param uri
             * @param localName
             * @param qName
             * @param attributes
             * @throws SAXException
             */
            public void startElement(String uri, String localName, String
                    qName, Attributes attributes) throws SAXException {
                if (qName.contentEquals("Offer")) {
                    model = new ModelOffers();
                }
                if (qName.contentEquals("CampaignName")) {
                    bCampaign = true;
                }
                if (qName.contentEquals("DepartureName")) {
                    bDepaurte = true;
                }
                if (qName.contentEquals("OutDate")) {
                    bOutDate = true;
                }
                if (qName.contentEquals("DestinationName")) {
                    bDestinationName = true;
                }
                if (qName.contentEquals("HotelImage")) {
                    bHotelImage = true;
                }
                if (qName.contentEquals("CurrentPrice")) {
                    bCurrentPrice = true;
                }
                if (qName.contentEquals("CityName")) {
                    bCityName = true;
                }
                if (qName.contentEquals("RoomDescription")) {
                    bRoomDescription = true;
                }
            }

            /**
             * Laser in data som finns i en huvudtagg
             *
             * @param uri
             * @param localName
             * @param qName
             * @throws SAXException
             */
            public void endElement(String uri, String localName, String
                    qName) throws SAXException {
                if (qName.contentEquals("Offer")) {
                    offers.add(model);
                }
            }

            /**
             * Laser in de element som finns mellan en start och en slut-tagg
             *
             * @param ch
             * @param start
             * @param length
             * @throws SAXException
             */
            public void characters(char ch[], int start, int length) throws
                    SAXException {
                if (bCampaign) {
                    model.setCampaignName(new String(ch, start, length));
                    bCampaign = false;
                }
                if (bDepaurte) {
                    model.setDepartureName(new String(ch, start, length));
                    bDepaurte = false;
                }
                if (bOutDate) {
                    model.setOutDate(new String(ch, start, length));
                    bOutDate = false;
                }
                if (bDestinationName) {
                    model.setDestinationName(new String(ch, start, length));
                    bDestinationName = false;
                }
                if (bHotelImage) {
                    model.setHotelImage(new String(ch, start, length));
                    bHotelImage = false;
                }
                if (bCurrentPrice) {
                    String price = new String(ch, start, length);
                    String priceString[] = price.split("\\s+");
                    model.setCurrentPrice(Integer.parseInt(priceString[0]));
                    bCurrentPrice = false;
                }
                if (bCityName) {
                    model.setCityName(new String(ch, start, length));
                    bCityName = false;
                }
                if (bRoomDescription) {
                    model.setRoomDescription(new String(ch, start, length));
                    bRoomDescription = false;
                }
            }
        };

        saxParser.parse(parseString, handler);

    }

    public List<ModelOffers> getList() {
        return offers;
    }
}


