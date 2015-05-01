package de.logbooker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class setCrewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_crew);


    }

    public void onButtonSaveCrew_Click(View view){
        Toast.makeText(this,"Gespeichert!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, createTripActivity.class);
        startActivity(intent);
    }
}
