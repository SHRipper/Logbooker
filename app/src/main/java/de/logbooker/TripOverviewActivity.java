package de.logbooker;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;


public class TripOverviewActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    Intent selectedTripIntent;
    public String[] DaysArray;
    ActionBar myactionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_overview);

        selectedTripIntent = getIntent();
        String selectedTrip = selectedTripIntent.getStringExtra(SelectTripActivity.SELECTED_TRIP);

        // fill the DaysArray
        if (false) {
            // get the saved days here
        } else {
            DaysArray = new String[]{"keine Aufzeichnung", "Beispieltag 1"};
        }

        // implement the TabHost
        TabHost TabHostTripOverview = (TabHost) findViewById(R.id.tabHostTripOverview);

        // setup the TabHost with two tabs, one for the DaysOverview and one for the Map
        TabHostTripOverview.setup();
        TabHost.TabSpec tabSpec = TabHostTripOverview.newTabSpec("TripOverview");
        tabSpec.setContent(R.id.DaysOverviewTab);
        tabSpec.setIndicator(selectedTrip);
        TabHostTripOverview.addTab(tabSpec);

        tabSpec = TabHostTripOverview.newTabSpec("Map");
        tabSpec.setContent(R.id.MapTab);
        tabSpec.setIndicator("Karte");
        TabHostTripOverview.addTab(tabSpec);

        // implement other stuff
        ListView ListViewDays = (ListView) findViewById(R.id.listViewDays);


        ArrayAdapter<String> ListViewDaysAdapter = new ArrayAdapter<String>(this,
                R.layout.customlist,
                R.id.tvCustomList,
                DaysArray);
        ListViewDays.setAdapter(ListViewDaysAdapter);

        ListViewDays.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_selectedTrip) {
            Intent intent = new Intent(TripOverviewActivity.this, createTripActivity.class);
            // Extra anfügen damit beim bearbeiten schon erstellte daten drin stehen
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                // placeholder
            case 1:
                Intent intent = new Intent(TripOverviewActivity.this, null);
                startActivity(intent);
        }
    }
}
