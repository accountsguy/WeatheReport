package accountsguy.net.weathereport;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textview);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


//                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                                    startActivity(new Intent(android.provider.Settings
//                                            .ACTION_LOCATION_SOURCE_SETTINGS));
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    final AlertDialog alert = builder.create();
//                    alert.show();
//                }
//
                new NetworkCaller(MainActivity.this, textView, progressBar).execute();
            }
        });

    }

//    private void turnGPSOn(){
//        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//        if(!provider.contains("gps")){ //if gps is disabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            sendBroadcast(poke);
//        }
//    }
//
//    private void turnGPSOff(){
//        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//        if(provider.contains("gps")){ //if gps is enabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            sendBroadcast(poke);
//        }
//    }
//
//    private boolean canToggleGPS() {
//        PackageManager pacman = getPackageManager();
//        PackageInfo pacInfo = null;
//
//        try {
//            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
//        } catch (NameNotFoundException e) {
//            return false; //package not found
//        }
//
//        if(pacInfo != null){
//            for(ActivityInfo actInfo : pacInfo.receivers){
//                //test if recevier is exported. if so, we can toggle GPS.
//                if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported){
//                    return true;
//                }
//            }
//        }
//
//        return false; //default
//    }

}