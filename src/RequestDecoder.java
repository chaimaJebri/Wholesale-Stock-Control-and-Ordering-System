import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * The RequestDecoder class is responsible for decoding an HTTP request parameters
 * and converts them into a HashMap.
 *
 * @author Chaima Jebri
 * @version 1.2
 */
public class RequestDecoder {
//Chaima Jebri

    /**
     * Default constructor for the RequestDecoder class
     */
    public RequestDecoder()
    {

    }
    /**
     * Converts an HTTP request into a HashMap
     *
     * @param request The URL-encoded string containing key-value pairs.
     * @return a HashMap containing the decoded key-value pairs.
     */
    public HashMap<String, String> requestStringToMap(String request) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] pairs = request.split("&");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            try {
                String[] kv = pair.split("=");
                String key = kv[0];
                key = URLDecoder.decode(key, "UTF-8");

                String value = kv[1];
                value = URLDecoder.decode(value, "UTF-8");

                map.put(key, value);
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }
        }
        return map;
    }
}
