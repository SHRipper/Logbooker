package de.logbooker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SelectTripActivity extends ActionBarActivity {

    // Intent messages
    public static final String TRIPS_EXIST = "de.logbooker";
    public static final String SELECTED_TRIP = "de.logbooker";
    public static final String ORIGIN = "de.logbooker.SelectTripActivitiy";

    public String[] TripsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trip);

        // fill String-array
        TripsArray = new String[]{"++ neuer Törn ++", "Törn 1", "Törn 2"};

        // get the listview and set the adapter
        ListView TripListView = (ListView) findViewById(R.id.TripListView);
        ArrayAdapter<String> TripListViewAdapter = new ArrayAdapter<String>(this,
                R.layout.selecttriplist,
                R.id.tvSelectTripList,
                TripsArray);
        TripListView.setAdapter(TripListViewAdapter);

        TripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { // new Trip selected
                    Intent newTripIntent = new Intent(getApplicationContext(), createTripActivity.class);
                    newTripIntent.putExtra(TRIPS_EXIST, TripsArray.length);
                    startActivity(newTripIntent);
                } else { // existing Trip selected
                    Intent selectTripIntent = new Intent(getApplicationContext(), TripOverviewActivity.class);
                    selectTripIntent.putExtra(SELECTED_TRIP, TripsArray[position]);
                    startActivity(selectTripIntent);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { // go to settings
            Intent SettingsIntent = new Intent(getApplicationContext(), AppSettings.class);
            SettingsIntent.putExtra(ORIGIN,"SelectTripActivtiy");
            startActivity(SettingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
