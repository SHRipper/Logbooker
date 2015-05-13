package de.logbooker;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;


public class setCrewActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    AlertDialog.Builder renameDialog;
    EditText dialogInput;
    ViewGroup.LayoutParams lp;
    EditText editTextSkipperName;
    EditText editTextCoSkipperName;
    ArrayAdapter<String> listViewCrewAdapter;
    String CrewMemberFile = "CrewMembers";

    // public variables for cross app action
    public ArrayList<String> Members;
    public String SkipperName;
    public String CoSkipperName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_crew);



        // on startup load saved members
        loadCrewMembers(CrewMemberFile);

        Members = new ArrayList<String>();
        Members.add(0, "Mitglied hinzufügen");

        // implement the objects
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
        renameDialog = new AlertDialog.Builder(this);
        renameDialog.setCancelable(true);


    }

    public void onButtonSaveCrew_Click(View view) { // save crew settings

        if (editTextSkipperName.getText().length() == 0 && editTextCoSkipperName.getText().length() == 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setMessage("Sie müssen mindestens einen Skipper und Co-Skipper angeben!");
            ad.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ad.show();

        } else { // skipper and co-skipper field not empty
            saveCrewMembers(CrewMemberFile);

            // change activity
            Intent intent = new Intent(this, createTripActivity.class);
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
            createInputAlertDialog();

            // add negative and postitive button and show the dialog
            renameDialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            renameDialog.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Members.set(position, dialogInput.getText().toString());
                    listViewCrewAdapter.notifyDataSetChanged();

                }
            });
            renameDialog.show();

        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);

        // confirmation if member should really be deleted
        ad.setMessage("Wirklich löschen?");
        ad.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // remove member at the clicked position and update the adapter
                Members.remove(position);
                listViewCrewAdapter.notifyDataSetChanged();
            }
        });
        ad.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();

        return true;
    }

    public void createInputAlertDialog() {
        // configure the text input field in the dialog
        lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        dialogInput = new EditText(this);
        dialogInput.setSingleLine();
        dialogInput.setLayoutParams(lp);
        dialogInput.setTextColor(Color.BLACK);

        // set up the dialog
        renameDialog = new AlertDialog.Builder(this);
        renameDialog.setCancelable(true);
        renameDialog.setView(dialogInput);
        renameDialog.setTitle("Crewmitglied hinzufügen");
        renameDialog.setMessage("Name:");
    }

    public void saveCrewMembers(String CrewMemberFile) {
        // crew member list is already public and updated
        SkipperName = editTextSkipperName.getText().toString();
        CoSkipperName = editTextCoSkipperName.getText().toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput(CrewMemberFile, MODE_PRIVATE);

            fileOutputStream.write((SkipperName + "\n").getBytes());
            fileOutputStream.write((CoSkipperName + "\n").getBytes());
            for (int i = 1; i <= Members.size()-1; i++) {
                fileOutputStream.write((Members.get(i) + "\n").getBytes());
            }
            fileOutputStream.write("end".getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void loadCrewMembers(String CrewMemberFile){
        int i = 1;

        try {
            FileInputStream fileInputStream = openFileInput(CrewMemberFile);

            if(fileInputStream != null){
                InputStreamReader isr = new InputStreamReader(fileInputStream);
                BufferedReader br = new BufferedReader(isr);

                SkipperName = br.readLine();
                CoSkipperName = br.readLine();

                while(!br.readLine().equals("end")){
                    Members.set(i , br.readLine());
                    i++;
                }

                isr.close();
                br.close();
                fileInputStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
