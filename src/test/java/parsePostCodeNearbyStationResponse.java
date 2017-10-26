
import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class parsePostCodeNearbyStationResponse {

    public static void main(String[] args) {
        Map<String, Integer> unsortedStationByDistance = new HashMap<String,Integer>();
        // parsing response using DOM parser
         try {
             File response = new File("response.xml");
             DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
             DocumentBuilder builder = dbFactory.newDocumentBuilder();
             Document document = builder.parse(response);
             document.getDocumentElement().normalize();
             NodeList nearByStationList = document.getElementsByTagName("PostcodeStationWithCoords");
             int size = nearByStationList.getLength();
             for(int i =0 ; i<=size;i++) {
                 Node node  = nearByStationList.item(i);
                 short nodeType = node.getNodeType();
                 if(nodeType == Node.ELEMENT_NODE) {
                     // extracted distance and crs and pushed to hashmap
                     Element element = (Element) node;
                     int distance = Integer.parseInt(element.getElementsByTagName("Distance").item(0).getTextContent());
                     String crs = element.getElementsByTagName("Crs").item(0).getTextContent();
                     unsortedStationByDistance.put(crs,distance);
                 }
             }

             //sorting hashmap by value
             List<Map.Entry<String,Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortedStationByDistance.entrySet());
             Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                 public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());

                 }
             });

             // loop over sorted list and create a sorted hashmap
             Map<String , Integer> sortedStationsByDistance = new LinkedHashMap<String,Integer>();
             for(Map.Entry<String,Integer> entry:list) {
                 sortedStationsByDistance.put(entry.getKey(),entry.getValue());
             }

             // prints CRS of the second closest station
             getCRSClosestStation(1,list,sortedStationsByDistance );

         }catch(Exception e) {
             System.out.println("exception occured"+ e);
             e.printStackTrace();
        }

    }


    // CODE QUALITY - I reckon we can improve code quality by using appropriate reusable functions , like this . So now we can call this function
     // n number of times with different 'number' values ( pls note here number refers to second closest station , third closest station and so on..

    private static void getCRSClosestStation(int number,List<Map.Entry<String,Integer>> list, Map<String,Integer> sortedStationsByDistance) {
        for (int j =0 ;j <= list.size();j++) {
            if(j == number ){
                System.out.println (sortedStationsByDistance.get(j));
            }
        }
    }
}
