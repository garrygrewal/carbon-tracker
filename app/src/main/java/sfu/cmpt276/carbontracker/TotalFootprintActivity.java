package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import sfu.cmpt276.carbontracker.model.CarbonModel;

public class TotalFootprintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_footprint);
        setupButtons();
        listJourneys();
    }

    private void listJourneys() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_journey, CarbonModel.getInstance().getJourneyInfo());
        ListView journey_list = (ListView) findViewById(R.id.listViewTotalFootprint);

        //List Adapter
        journey_list.setAdapter(adapter);

        //Context Menu for long press
        registerForContextMenu(journey_list);
    }

    private void setupButtons() {
        //back to main menu button
        Button btn_cancel = (Button) findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        Button btn_graph = (Button) findViewById(R.id.buttonPieGraph);
        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalFootprintActivity.this, PieGraphActivity.class);
                startActivity(intent);
            }
        });
    }


    //CONTEXT MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Journey Options");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getTitle().toString()) {
            case "Edit":
                // sends relevant information to AddVehicleActivity
                Intent intent = new Intent(TotalFootprintActivity.this, SelectVehicleActivity.class);
                intent.putExtra("journey_index", info.position);
                startActivity(intent);
                break;
            case "Delete":
                CarbonModel.getInstance().deleteJourney(info.position);
                listJourneys();
                break;
            default:
                return false;
        }
        return true;
    }

    //navigation back button
    @Override
    public void onBackPressed() {
        finish();
    }
}
