package de.logbooker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import static android.view.ViewGroup.*;
import static android.widget.AdapterView.*;


public class createTripActivity extends ActionBarActivity implements OnItemClickListener {

    Intent newTripIntent;

    String[] CreateTripArray;
    ArrayAdapter<String> ListViewCreateTripAdapter;

    Integer i;

    // popup dialog objects and layouts
    EditText dialogInput;
    AlertDialog.Builder ad;
    LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        // noch nicht belegt!
        newTripIntent = getIntent();
        Integer existingTrips = newTripIntent.getIntExtra(SelectTripActivity.TRIPS_EXIST, -1);

        // fill String Array
        CreateTripArray = new String[]{"Schiffsname:   ", "Schiffstyp:   ",
                "Schiffsgröße:   ", "Starthafen:   ",
                "Törnart:   ", "Crewbesetzung"};

        // fill listview
        ListView ListViewCreateTrip = (ListView) findViewById(R.id.listViewCreateTrip);
        ListViewCreateTripAdapter = new ArrayAdapter<String>(this,
                R.layout.createtriplist,
                R.id.tvCreateTripList,
                CreateTripArray);
        ListViewCreateTrip.setAdapter(ListViewCreateTripAdapter);

        // Click listener for an item in the list view
        ListViewCreateTrip.setOnItemClickListener(this);

        // configure the input dialog
        ad = new AlertDialog.Builder(this);
        lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ad.setCancelable(true);

        // help variables
        i = 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        // create and set new popup dialog
        dialogInput = new EditText(this);
        dialogInput.setCursorVisible(false);
        dialogInput.setLayoutParams(lp);
        dialogInput.setSingleLine();
        ad.setView(dialogInput);

        // distinguish the list view item positions clicked
        switch (position) {
            case 0: // ship name
                ad.setMessage("Schiffsname");
                break;
            case 1: // ship type
                ad.setMessage("Schiffstyp");
                break;
            case 2: // ship size
                ad.setMessage("Schiffsgröße in Fuß");
                break;
            case 3: // start haven
                ad.setMessage("Starthafen");
                break;
            case 4: // what kind of trip
                ad.setMessage("Törnart");
                break;
            case 5: // crew selection
                Intent setCrewIntent = new Intent(createTripActivity.this, setCrewActivity.class);
                startActivity(setCrewIntent);
                break;
        }

        if (position != 5) { // everything but crew selection
            ad.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (position == 2) { // shipsize in foot
                        CreateTripArray[position] = "Schiffsgröße:   ";
                        CreateTripArray[position] += dialogInput.getText() + " [ft]";
                    } else {
                        CreateTripArray[position] += dialogInput.getText();
                    }
                }
            });
            // set negative button
            ad.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            //show dialog
            ad.show();

            // noticed if the CreateTripArray has changed
            ListViewCreateTripAdapter.notifyDataSetChanged();
        }
    }

    public void onButtonSaveTrip_Click(View view) {
        // save settings here

        Toast.makeText(createTripActivity.this, "Einstellungen gespeichert", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(createTripActivity.this, SelectTripActivity.class);
        startActivity(intent);

    }
}
