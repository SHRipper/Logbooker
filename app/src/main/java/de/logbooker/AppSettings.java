package de.logbooker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AppSettings extends ActionBarActivity implements AdapterView.OnItemClickListener {


    // onClick variables
    Integer onPos0_Click = 0;
    Integer onPos1_Click = 0;
    Integer onPos2_Click = 0;
    Integer onPos3_Click = 0;
    Integer onPos4_Click = 0;
    Integer onPos5_Click = 0;

    // save file
    String saveFile = "Settings";

    // Settings
    String Windspeed = "m/s";
    String Temperature = "°C";
    String Distance = "km";
    String Boatspeed = "km/h";
    Integer Interval = 30;
    String Language = "deutsch";

    // Adapter
    ArrayAdapter<String> SettingsOverviewAdapter;

    // Arrays
    String[] SettingsArray;

    // Objects
    ListView ListViewSettingsOverview;
    Button ButtonSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        saveSettings(saveFile);

        loadSettings(saveFile);


        // get the Save Button
        ButtonSaveSettings = (Button) findViewById(R.id.ButtonSaveSettings);

        // fill SettingsArray with default
        SettingsArray = new String[]{"Windgeschwindigkeitseinheit [" + Windspeed + "]",
                "Temperatur [" + Temperature + "]", "Streckeneinheit [" + Distance + "]",
                "Bootsgeschwindigkeit [" + Boatspeed + "]", "Eintragsintervall [" + Interval + "]",
                "Sprache [deutsch]"};

        // configure the listview
        ListViewSettingsOverview = (ListView) findViewById(R.id.listViewSettingsOverview);
        SettingsOverviewAdapter = new ArrayAdapter<String>(this,
                R.layout.createtriplist,
                R.id.tvCreateTripList,
                SettingsArray);
        ListViewSettingsOverview.setAdapter(SettingsOverviewAdapter);

        // set an on item click listener for the listview
        ListViewSettingsOverview.setOnItemClickListener(this);
    }

    public void onButtonSaveSettings_Click(View view) {
        saveSettings(saveFile);

        Toast.makeText(AppSettings.this, "Erfolgreich gespeichert!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AppSettings.this, SelectTripActivity.class);
        startActivity(intent);
    }

    public void loadSettings(String saveFile) {
        try {
            InputStream inputStream = openFileInput(saveFile);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);

                // read the data out of the file
                Windspeed = br.readLine();
                Temperature = br.readLine();
                Distance = br.readLine();
                Boatspeed = br.readLine();

                //Interval = Integer.parseInt(br.readLine());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings(String saveFile) {
        try {

            FileOutputStream fos = openFileOutput(saveFile, MODE_PRIVATE);

            // write data into the file
            fos.write((Windspeed + "\n").getBytes());
            fos.write((Temperature + "\n").getBytes());
            fos.write((Distance + "\n").getBytes());
            fos.write((Boatspeed + "\n").getBytes());
            //fos.write(Interval);
            fos.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // Windspeed

                switch (Windspeed) {
                    case "m/s":
                        Windspeed = "bft";
                        break;
                    case "bft":
                        Windspeed = "kn";
                        break;
                    case "kn":
                        Windspeed = "m/s";
                        break;
                }
                SettingsArray[0] = "Windgeschwindigkeitseinheit [" + Windspeed + "]";
                break;

            case 1: // Temperature

                switch (Temperature) {
                    case "°C":
                        Temperature = "°F";
                        break;
                    case "°F":
                        Temperature = "°C";
                        break;
                }
                SettingsArray[1] = "Temperatur [" + Temperature + "]";
                break;
            case 2: // Distance

                switch (Distance) {
                    case "km":
                        Distance = "m";
                        break;
                    case "m":
                        Distance = "NM";
                        break;
                    case "NM":
                        Distance = "km";
                        break;
                }
                SettingsArray[2] = "Streckeneinheit [" + Distance + "]";
                break;
            case 3: // Boatspeed

                switch (Boatspeed) {
                    case "km/h":
                        Boatspeed = "kn";
                        break;
                    case "kn":
                        Boatspeed = "NM/h";
                        break;
                    case "NM/h":
                        Boatspeed = "km/h";
                        break;
                }
                SettingsArray[3] = "Bootsgeschwindigkeit [" + Boatspeed + "]";
                break;
            case 4: // Interval

                switch (Interval) {
                    case 30:
                        Interval = 60;
                        break;
                    case 60:
                        Interval = 120;
                        break;
                    case 120:
                        Interval = 30;
                        break;
                }
                SettingsArray[4] = "Eintragsintervall [" + Interval + "]";
                break;
            case 5: // Language

                switch (Language) {
                    case "deutsch":
                        // Platzhalter für Spracheinstellungen
                        break;
                }

        }
        // update the listview data
        SettingsOverviewAdapter.notifyDataSetChanged();
    }


}
