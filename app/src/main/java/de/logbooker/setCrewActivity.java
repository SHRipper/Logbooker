package de.logbooker;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class setCrewActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    AlertDialog.Builder ad;
    EditText dialogInput;
    ViewGroup.LayoutParams lp;
    EditText editTextSkipperName;
    EditText editTextCoSkipperName;

    String[] Members;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_crew);

        Members = new String[]{"Mitglied hinzufügen", "neues Mitglied", "neues Mitglied"};

        // get the objects
        ListView listViewCrew = (ListView) findViewById(R.id.ListViewCrew);
        editTextSkipperName = (EditText) findViewById(R.id.editTextSkipper);
        editTextCoSkipperName = (EditText) findViewById(R.id.editTextCoSkipper);

        // set adapter for the listview
        ArrayAdapter<String> listViewCrewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Members);
        listViewCrew.setAdapter(listViewCrewAdapter);

        listViewCrew.setOnItemClickListener(this);

        // set up alertdialog for crew member input

        lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ad = new AlertDialog.Builder(this);
        ad.setCancelable(true);

    }

    public void onButtonSaveCrew_Click(View view) { // save crew settings

        if (editTextSkipperName.length() == 0 && editTextCoSkipperName.length() == 0) {
                ad.setMessage("Sie müssen zuerst einen Skipper und Co-Skipper angeben!");
        }else { // skipper and co-skipper field not empty
            Intent intent = new Intent(this, createTripActivity.class);
            intent.putExtra("Members", Members);
            intent.putExtra("Skipper", editTextSkipperName.getText().toString());
            intent.putExtra("CoSkipper", editTextCoSkipperName.getText().toString());
            startActivity(intent);
            Toast.makeText(this, "Crewzusammenstellung gespeichert!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (position == 0) {
            Members[Members.length] = "neues Mitglied";
        } else {
            // create the alertdialog on item click
            dialogInput = new EditText(this);
            dialogInput.setSingleLine();
            dialogInput.setLayoutParams(lp);
            dialogInput.setCursorVisible(false);
            ad.setView(dialogInput);
            ad.setMessage("neues Crewmitglied hinzufügen");

            ad.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            ad.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Members[position] = dialogInput.getText().toString();
                }
            });

        }
    }
}
