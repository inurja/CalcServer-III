package com.calcserver.intz.calcserver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private UOW uow;
    private OperationsAdapter operationsAdapter;
    private OperationTypesAdapter operationTypesAdapter;
    private StatisticsAdapter statisticsAdapter;
    private ListView listView;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = (TextView) findViewById(R.id.inputOutput);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.dbResultsListView);
        textView = (TextView) findViewById(R.id.dbHeaderTextView);

        uow = new UOW(getApplicationContext());
        //uow.DropCreateDatabase();
        uow.setOperationTypes();
        //display();
        displayOperations();
    }

    /*private void display() {
        //statisticsAdapter = new StatisticsAdapter(this, uow.statisticsRepo.getCursorAll(), uow);
        operationTypesAdapter = new OperationTypesAdapter(this, uow.operationTypesRepo.getCursorAll(), uow);
        ListView listView = (ListView) findViewById(R.id.dbResultsListView);
        listView.setAdapter(operationTypesAdapter);
    }*/

    private void displayOperations() {
        String heading = "Operations";
        operationsAdapter = new OperationsAdapter(this, uow.operationsRepo.getCursorAll(), uow);

        textView.setText(heading);
        listView.setAdapter(operationsAdapter);
    }

    private void displayOperationTypes() {
        String heading = "Overall used Operands";
        operationTypesAdapter = new OperationTypesAdapter(this, uow.operationTypesRepo.getCursorAll(), uow);

        textView.setText(heading);
        listView.setAdapter(operationTypesAdapter);
    }

    private void displayStatistics() {
        String heading = "Daily statistics";
        statisticsAdapter = new StatisticsAdapter(this, uow.statisticsRepo.getCursorAll(), uow);

        textView.setText(heading);
        listView.setAdapter(statisticsAdapter);
    }

    private void deleteAll() {
        new AlertDialog.Builder(this) //start new alert
                .setTitle("Caution ples")
                .setMessage("Empty the whole database?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { //yes button
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uow.DropCreateDatabase(); //drop database when clicked
                        Toast.makeText(MainActivity.this, "KABOOM", Toast.LENGTH_SHORT).show(); //show msg that its done
                        refreshAll(); //refresh views
                    }
                })
                .setNegativeButton(android.R.string.no, null).show(); //no button, dont do nothing
    }

    private void refreshAll() {
        displayOperations();
        displayOperationTypes();
        displayStatistics();
    }

    private void resetCounters() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_history) {
            displayOperations();
        } else if (id == R.id.action_show_statistics) {
            displayStatistics();
        } else if (id == R.id.action_show_operationTypes) {
            displayOperationTypes();
        } else if (id == R.id.action_delete_all) {
            deleteAll();
        } else if (id == R.id.action_refresh_all) {
            refreshAll();
        } else if (id == R.id.action_reset_counters) {
            resetCounters();
        }
        return super.onOptionsItemSelected(item);
    }
}
