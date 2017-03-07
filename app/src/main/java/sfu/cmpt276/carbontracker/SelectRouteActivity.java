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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Route;

public class SelectRouteActivity extends AppCompatActivity {

    //position of route in list to be edited
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        //REMOVE THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //add pladeholder routes
        if (CarbonModel.getInstance().countRoutes() == 0) {
            CarbonModel.getInstance().addRoute(new Route("test route", 10, 25));
            CarbonModel.getInstance().addRoute(new Route("test route2", 15, 35));
            CarbonModel.getInstance().addRoute(new Route("test route3", 5, 55));
        }

        listRoutes();
        onListClick();
        setupButtons();
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
        final ListView route_list = (ListView) findViewById(R.id.listViewRoutes);
        route_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Route clicked_route = CarbonModel.getInstance().getRoute(position);

                Intent intent = new Intent(SelectRouteActivity.this, AddNameActivity.class);
                intent.putExtra("route_index", position);
                //intent.putExtra("route_name", clicked_route.getName());
                //intent.putExtra("route_city", clicked_route.getCity());
                //intent.putExtra("route_hwy", clicked_route.getHwy());
                startActivity(intent);
            }
        });
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
                Route clicked_route = CarbonModel.getInstance().getRoute(info.position);
                index = info.position;
                Intent intent = new Intent(SelectRouteActivity.this, AddRouteActivity.class);
                intent.putExtra("name", clicked_route.getName());
                intent.putExtra("city", String.valueOf(clicked_route.getCity()));
                intent.putExtra("hwy", String.valueOf(clicked_route.getHwy()));
                startActivityForResult(intent, 24);
                break;
            case "Delete":
                CarbonModel.getInstance().hideRoute(info.position);
                //does not actually delete
                //CarbonModel.getInstance().removeRoute(info.position);
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
                startActivityForResult(intent, 32);
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

    //activity callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //add new route
            case 32: {
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra("name");
                    String city = data.getStringExtra("city");
                    String hwy = data.getStringExtra("hwy");
                    float int_city = Float.parseFloat(city);
                    float int_hwy = Float.parseFloat(hwy);
                    CarbonModel.getInstance().addRoute(new Route(name, int_city, int_hwy));
                    listRoutes();
                    break;
                }
                else
                    break;
            }
            //edit route
            case 24: {
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra("name");
                    String city = data.getStringExtra("city");
                    String hwy = data.getStringExtra("hwy");
                    float num_city = Float.parseFloat(city);
                    float num_hwy = Float.parseFloat(hwy);
                    CarbonModel.getInstance().editRoute(new Route(name, num_city, num_hwy), index);
                    listRoutes();
                    break;
                }
                else
                    break;
            }
        }
    }

    //navigation back button
    @Override
    public void onBackPressed () {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

}
