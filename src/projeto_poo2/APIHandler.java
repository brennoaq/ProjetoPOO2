package projeto_poo2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;

public class APIHandler {
    //constructor
    public APIHandler(String APIKey) {
        this.APIKey = APIKey;
        
        // Load some default equities, for test purposes
        this.equities = new ArrayList();
        equities.add(new Equity("PETROBRAS PN", "PETR4"));
        equities.add(new Equity("VALE ON", "VALE3"));
        equities.add(new Equity("PETROBRAS BR", "PETR3"));
        equities.add(new Equity("BRADESCO PN", "BBDC4"));
    }

    public List<Equity> getEquities() {
        return equities;
    }
    
    public TimeSeries getSMAIndicatorData(Equity eq, int minuteInterval) throws MalformedURLException, IOException {
        TimeSeries output = new TimeSeries(eq.getName() + " SMA");
        JsonObject timestamps = new JsonObject();

        URL url = new URL(APIURL + "query?function=SMA&"
                + "symbol=" + eq.getCode() + "&"
                + "interval=" + String.valueOf(minuteInterval) + "min&"
                + "time_period=10&"
                + "series_type=close&"
                + "apikey=" + APIKey);

        URLConnection request = null;
        
        try {
            request = url.openConnection();
            request.connect();   
        } catch (IOException e) {
            if (e.toString().contains("502")) {
                System.out.println("Server not found in SMA: " + eq.getName());   
                throw new IOException("");
            }
        }

        JsonParser jp = new JsonParser();
        JsonObject root;
        root = jp.parse(new InputStreamReader((InputStream) request.getContent()))
                 .getAsJsonObject();

        if (root.has("Information")) {
            throw new IOException(root.get("Information").getAsString());

        } else if (root.has("Technical Analysis: SMA")) {
            timestamps = root.get("Technical Analysis: SMA").getAsJsonObject();
        }

        int i = 1;
        for (Map.Entry<String, JsonElement> entry : timestamps.entrySet()) {
            String time = entry.getKey();
            
            double SMA = entry.getValue()
                              .getAsJsonObject()
                              .get("SMA")
                              .getAsDouble();
            output.add(new Second(0,
                                  Integer.parseInt(time.substring(14, 16)),
                                  Integer.parseInt(time.substring(11, 13)),
                                  Integer.parseInt(time.substring(8, 10)),
                                  Integer.parseInt(time.substring(5, 7)),
                                  Integer.parseInt(time.substring(0, 4))),
                       SMA);
            i++;
            if (i >= 100) {
                break;
            }
        }
        
        return output;
    }
    public TimeSeries getIntradayData(Equity eq, int minutesInterval) throws MalformedURLException, IOException, NullPointerException {
        TimeSeries output = new TimeSeries(eq.getName() + " data");
        JsonObject timestamps = new JsonObject();
        

        URL url = new URL(APIURL + "query?"
                + "function=TIME_SERIES_INTRADAY&"
                + "symbol=" + eq.getCode() + "&"
                + "interval=" + String.valueOf(minutesInterval) + "min&"
                + "apikey=" + APIKey + "&"
                + "dataype=json");
        
        URLConnection request = null;
        
        try {
            request = url.openConnection();
            request.connect();   
        } catch (IOException e) {
            if (e.toString().contains("502")) {
                System.out.println("Server not found in data: " + eq.getName());   
                throw new IOException("");
            }
        }
        

        JsonParser jp = new JsonParser();
        JsonObject root;
        root = jp.parse(new InputStreamReader((InputStream) request.getContent()))
            .getAsJsonObject();

        if (root.has("Information")) {
            throw new IOException(root.get("Information").getAsString());

        } else if (root.has("Time Series (" + String.valueOf(minutesInterval) + "min)")) {
            timestamps = root.get("Time Series (" + String.valueOf(minutesInterval) + "min)").getAsJsonObject();
        }

        int i = 1;
        for (Map.Entry<String, JsonElement> entry : timestamps.entrySet()) {
            String time = entry.getKey();
            
            double value = entry.getValue()
                                .getAsJsonObject()
                                .get("4. close")
                                .getAsDouble();
            
            output.add(new Second(Integer.parseInt(time.substring(17, 19)),
                                  Integer.parseInt(time.substring(14, 16)),
                                  Integer.parseInt(time.substring(11, 13)),
                                  Integer.parseInt(time.substring(8, 10)),
                                  Integer.parseInt(time.substring(5, 7)),
                                  Integer.parseInt(time.substring(0, 4))),
                       value);
            i++;
            if (i >= 100) {
                break;
            }
        }
        return output;        
    }   
    
    // Properties
    private String APIKey;
    private List<Equity> equities;
    private String APIURL = "https://www.alphavantage.co/";
}
