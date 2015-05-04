package de.logbooker;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import java.lang.reflect.Member;
import java.util.ArrayList;


public class setCrewActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    AlertDialog.Builder ad;
    EditText dialogInput;
    ViewGroup.LayoutParams lp;
    EditText editTextSkipperName;
    EditText editTextCoSkipperName;
    ArrayAdapter<String> listViewCrewAdapter;

    ArrayList<String> Members;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_crew);

        Members = new ArrayList<String>();
        Members.add(0, "Mitglied hinzufügen");


        // get the objects
        ListView listViewCrew = (ListView) findViewById(R.id.listViewCrew);
        editTextSkipperName = (EditText) findViewById(R.id.editTextSkipper);
        editTextCoSkipperName = (EditText) findViewById(R.id.editTextCoSkipper);

        // set adapter for the listview
        listViewCrewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Members);
        listViewCrew.setAdapter(listViewCrewAdapter);

        listViewCrew.setOnItemClickListener(this);
        listViewCrew.setOnItemLongClickListener(this);

        // set up alertdialog for crew member input

        lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ad = new AlertDialog.Builder(this);
        ad.setCancelable(true);


    }

    public void onButtonSaveCrew_Click(View view) { // save crew settings

        if (editTextSkipperName.getText().length() == 0 && editTextCoSkipperName.getText().length() == 0) {
            ad.setMessage("Sie müssen mindestens einen Skipper und Co-Skipper angeben!");
        } else { // skipper and co-skipper field not empty
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
            Members.add(Members.size(), "neues Mitglied");
            listViewCrewAdapter.notifyDataSetChanged();
        } else {
            // create the alertdialog on item click
            dialogInput = new EditText(this);
            dialogInput.setSingleLine();
            dialogInput.setLayoutParams(lp);
            dialogInput.setTextColor(Color.BLACK);
            ad.setView(dialogInput);
            ad.setTitle("Crewmitglied hinzufügen");
            ad.setMessage("Name:");


            // add negative and postitive button and show the dialog
            ad.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            ad.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Members.add(position, dialogInput.getText().toString());
                    listViewCrewAdapter.notifyDataSetChanged();
                }
            });
            ad.show();

        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        ad.setTitle("Wirklich löschen?");
        ad.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Members.remove(position);
            }
        });
        ad.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.show();

        return false;
    }
}
