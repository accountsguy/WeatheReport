package accountsguy.net.weathereport;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by advic on 12/04/2018.
 */

class NetworkCaller extends AsyncTask<Void, Void, String> {

    Button button;
    TextView textView;
    ProgressBar progressBar;

    Context context;

    String weather, city;
    String date = null;
    int temperature, pressure, humidity, wind, cloudiness;

    LocationManager locationManager;

    public NetworkCaller(Context context, TextView textView, ProgressBar progressBar) {
        this.context = context;
        this.textView = textView;
        this.progressBar = progressBar;


    }


    public static String getWeatherReport(String urlString){
        StringBuffer jsonBuffer = new StringBuffer();
        URL url;
        String urlWithNoSpace = urlString.replace(" ", "%20");

        HttpURLConnection httpURLConnection = null;

        try{
            url = new URL(urlWithNoSpace);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data != -1){
                char current = (char) data;
                data = inputStreamReader.read();
                jsonBuffer.append(current);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                httpURLConnection.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return jsonBuffer.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        return getWeatherReport("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText("");

        if(s != null){
            try{
                JSONObject jsonObject = new JSONObject(s);
                date = String.valueOf(jsonObject.getInt("dt"));
                city = jsonObject.getString("name");

                JSONArray weatherjsonArray = jsonObject.getJSONArray("weather");
                JSONObject weatherObject = weatherjsonArray.getJSONObject(0);
                weather = weatherObject.getString("description");

                JSONObject mainjsonObject = jsonObject.getJSONObject("main");
                temperature = mainjsonObject.getInt("temp");
                pressure = mainjsonObject.getInt("pressure");
                humidity = mainjsonObject.getInt("humidity");

                JSONObject cloudinessjsonObject = jsonObject.getJSONObject("clouds");
                cloudiness = cloudinessjsonObject.getInt("all");

                int tempdate = jsonObject.getInt("dt");
                date = DateFormat.getDateTimeInstance().format(new Date(tempdate*1000));

                JSONObject windjsonObject = jsonObject.getJSONObject("wind");
                wind = windjsonObject.getInt("speed");

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Welcome to the Weather Report \n\nCity: " + city);
//                stringBuilder.append("\nDate: " + String.valueOf(date));
                stringBuilder.append("\nCloudyness: " + String.valueOf(cloudiness)+"%");
                stringBuilder.append("\nTemperature: " + String.valueOf(temperature));
                stringBuilder.append("\nPressure: " + String.valueOf(pressure));
                stringBuilder.append("\nHumidity: " + String.valueOf(humidity)+"%");
                stringBuilder.append("\nWind: "+String.valueOf(wind)+" km/h");

                textView.setText(stringBuilder.toString());
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        progressBar.setVisibility(View.INVISIBLE);
    }
}
