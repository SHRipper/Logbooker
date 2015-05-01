package de.logbooker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class TripOverviewActivity extends ActionBarActivity {

    Intent selectedTripIntent;
    public String[] DaysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_overview);

        selectedTripIntent = getIntent();

        // fill the DaysArray
        if(false){
            // get the saved days here
        }else{
            DaysArray = new String[]{"keine Aufzeichnung"};
        }


        TextView TripName = (TextView) findViewById(R.id.textViewTripName);
        ListView ListViewDays = (ListView) findViewById(R.id.listViewDays);


        // set the Title to the selected Trip name
        String selectedTripName = selectedTripIntent.getStringExtra(SelectTripActivity.SELECTED_TRIP);
        TripName.setText(selectedTripName);
        ArrayAdapter<String> ListViewDaysAdapter = new ArrayAdapter<String>(this,
                    R.layout.customlist,
                    R.id.tvCustomList,
                    DaysArray);
        ListViewDays.setAdapter(ListViewDaysAdapter);

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
            Intent intent = new Intent(TripOverviewActivity.this,createTripActivity.class);
            // Extra anfügen damit beim bearbeiten schon erstellte daten drin stehen
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
