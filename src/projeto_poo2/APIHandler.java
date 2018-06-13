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
        
        this.equities = new ArrayList();
        equities.add(new Equity("PETROBRAS PN", "PETR4"));
        equities.add(new Equity("VALE ON", "VALE3"));
        equities.add(new Equity("PETROBRAS BR", "PETR3"));
        equities.add(new Equity("BRADESCO PN", "BBDC4"));
    }
        
    public void addEquity(Equity equity) {
        equities.add(equity);
    }
    
    public void removeEquity(Equity equity) {
        equities.remove(equity);
    }
    
    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public List<Equity> getEquities() {
        return equities;
    }
    
    public TimeSeries getSMAIndicatorData(Equity eq, int minuteInterval) throws MalformedURLException, IOException {
        TimeSeries output = new TimeSeries(eq.getName() + " SMA");
        JsonObject timestamps = new JsonObject();
        int tryCount = 0;
        
        boolean done = false;
        while (!done) {
            URL url = new URL(APIURL + "query?function=SMA&"
                    + "symbol=" + eq.getCode() + "&"
                    + "interval=" + String.valueOf(minuteInterval) + "min&"
                    + "time_period=10&"
                    + "series_type=close&"
                    + "apikey=" + APIKey);
            try {
                URLConnection request = url.openConnection();
                request.connect();

                JsonParser jp = new JsonParser();
                JsonObject root;
                root = jp.parse(new InputStreamReader((InputStream) request.getContent()))
                        .getAsJsonObject();
            
                timestamps = root.get("Technical Analysis: SMA").getAsJsonObject();
                done = true;
            } catch(NullPointerException e) {
                tryCount++;
                if (tryCount > 2) {
                    throw e;
                }
            }
        
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
        int tryCount = 0;
        
        boolean done = false;
        while (!done) {
            URL url = new URL(APIURL + "query?"
                    + "function=TIME_SERIES_INTRADAY&"
                    + "symbol=" + eq.getCode() + "&"
                    + "interval=" + String.valueOf(minutesInterval) + "min&"
                    + "apikey=" + APIKey + "&"
                    + "dataype=json");
            try {
                URLConnection request = url.openConnection();
                request.connect();   

                JsonParser jp = new JsonParser();
                JsonObject root;
                root = jp.parse(new InputStreamReader((InputStream) request.getContent()))
                    .getAsJsonObject();

                timestamps = root.get("Time Series (1min)").getAsJsonObject();
                done = true;
            } catch (NullPointerException e) {
                tryCount++;
                System.out.println(tryCount);
                if (tryCount > 2) {
                    System.out.println(tryCount);
                    throw e;
                }
            }
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
