package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.RouteModel;

public class SelectRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        listRoutes();
        onListClick();
        setupButtons();

        //add pladeholder routes
        CarbonModel.getInstance().addRoute(new RouteModel("test route", 10, 25));
        CarbonModel.getInstance().addRoute(new RouteModel("test route2", 15, 35));
        CarbonModel.getInstance().addRoute(new RouteModel("test route3", 5, 55));
    }

    //listView existing routes
    private void listRoutes () {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_route, CarbonModel.getInstance().getRouteInfo());
        ListView route_list = (ListView) findViewById(R.id.listViewRoutes);

        //List Adapter
        route_list.setAdapter(adapter);

        //Context Menu for long press
        registerForContextMenu(route_list);
    }

    //clicking on a route in the list
    private void onListClick() {
        //ListView route_list = (ListView) findViewById(R.id.route_list);
        //routeCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        RouteModel clicked_route = RouteCollection.getRoute(position);

                Intent intent = new Intent(SelectRouteActivity.this, AddNameActivity.class);
        //        intent.putExtra("info", RouteModel.getInfo());
                startActivity(intent);
    }

    //CONTEXT MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Route Options");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getTitle().toString()) {
            case "Edit":
                // sends relevant information to AddRouteActivity
                RouteModel clicked_route = CarbonModel.getInstance().getRoute(info.position);
                Intent intent = new Intent(SelectRouteActivity.this, AddRouteActivity.class);
                intent.putExtra("name", clicked_route.getName());
                intent.putExtra("city", clicked_route.getCity());
                intent.putExtra("hwy", clicked_route.getHwy());
                intent.putExtra("position", info.position);
                startActivityForResult(intent, 24);
                break;
            case "Delete":
                CarbonModel.getInstance().removeRoute(info.position);
                listRoutes();
                break;
            default:
                return false;
        }

        return true;
    }

    private void setupButtons() {
        //add new route button
        Button btn_new = (Button) findViewById(R.id.buttonAddRoute);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRouteActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });

        //cancel button
        Button btn_cancel = (Button) findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }


    //navigation back button
    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

}
