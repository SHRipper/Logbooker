package de.logbooker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class AppSettings extends ActionBarActivity implements AdapterView.OnItemClickListener {


    // onClick variables
    public Integer onPos0_Click = 0;
    public Integer onPos1_Click = 0;
    public Integer onPos2_Click = 0;
    public Integer onPos3_Click = 0;
    public Integer onPos4_Click = 0;
    public Integer onPos5_Click = 0;


    // Settings
    public String Windspeed = "m/s";
    public String Temperature = "°C";
    public String Distance = "km";
    public String Boatspeed = "km/h";
    public Integer Interval = 30;
    public String Language = "deutsch";

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

        // get the Save Button
        ButtonSaveSettings = (Button) findViewById(R.id.ButtonSaveSettings);

        // fill SettingsArray with default
        SettingsArray = new String[]{"Windgeschwindigkeitseinheit [m/s]",
                "Temperatur [°C]", "Streckeneinheit [km]", "Bootsgeschwindigkeit [km/h]",
                "Eintragsintervall [30 min]", "Sprache [deutsch]"};

        // configure the listview
        ListViewSettingsOverview = (ListView) findViewById(R.id.listViewSettingsOverview);
        SettingsOverviewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, SettingsArray);
        ListViewSettingsOverview.setAdapter(SettingsOverviewAdapter);

        // set an on item click listener for the listview
        ListViewSettingsOverview.setOnItemClickListener(this);
    }

    public void onButtonSaveSettings_Click(View view) {
        Toast.makeText(AppSettings.this, "Erfolgreich gespeichert!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AppSettings.this,SelectTripActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // Windspeed

                onPos0_Click += 1;
                switch (onPos0_Click) {
                    case 1:
                        SettingsArray[0] = "Windgeschwindigkeitseinheit [bft]";
                        Windspeed = "bft";
                        break;
                    case 2:
                        SettingsArray[0] = "Windgeschwindigkeitseinheit [kn]";
                        Windspeed = "kn";
                        break;
                    case 3:
                        SettingsArray[0] = "Windgeschwindigkeitseinheit [m/s]";
                        Windspeed = "m/s";
                        onPos0_Click = 0;
                        break;
                }
                break;
            case 1: // Temperature

                onPos1_Click += 1;
                switch (onPos1_Click) {
                    case 1:
                        SettingsArray[1] = "Temperatur [°F]";
                        Temperature = "°F";
                        break;
                    case 2:
                        SettingsArray[1] = "Temperatur [°C]";
                        onPos1_Click = 0;
                        Temperature = "°C";
                        break;
                }
                break;
            case 2: // Distance

                onPos2_Click += 1;
                switch (onPos2_Click) {
                    case 1:
                        SettingsArray[2] = "Streckeneinheit [m]";
                        Distance = "m";
                        break;
                    case 2:
                        SettingsArray[2] = "Streckeneinheit [NM]";
                        Distance = "NM";
                        break;
                    case 3:
                        SettingsArray[2] = "Streckeneinheit [km]";
                        onPos2_Click = 0;
                        Distance = "km";
                        break;
                }
                break;
            case 3: // Boatspeed

                onPos3_Click += 1;
                switch (onPos3_Click) {
                    case 1:
                        SettingsArray[3] = "Bootsgeschwindigkeit [kn]";
                        Boatspeed = "kn";
                        break;
                    case 2:
                        SettingsArray[3] = "Bootsgeschwindigkeit [NM/h]";
                        Boatspeed = "NM/h";
                        break;
                    case 3:
                        SettingsArray[3] = "Bootsgeschwindigkeit [km/h]";
                        onPos3_Click = 0;
                        Boatspeed = "km/h";
                        break;
                }
                break;
            case 4: // Interval

                onPos4_Click += 1;
                switch (onPos4_Click) {
                    case 1:
                        SettingsArray[4] = "Eintragsintervall [60 min]";
                        Interval = 60;
                        break;
                    case 2:
                        SettingsArray[4] = "Eintragsintervall [120 min]";
                        Interval = 120;
                        break;
                    case 3:
                        SettingsArray[4] = "Eintragsintervall [30 min]";
                        onPos4_Click = 0;
                        Interval = 30;
                        break;
                }
            case 5: // Language

                onPos5_Click += 1;
                switch (onPos5_Click) {
                    case 1:
                        // Platzhalter für Spracheinstellungen
                        break;
                }

        }
        // update the listview data
        SettingsOverviewAdapter.notifyDataSetChanged();
    }


}
