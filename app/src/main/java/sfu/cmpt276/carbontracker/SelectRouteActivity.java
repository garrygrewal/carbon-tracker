package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class SelectRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        listRoutes();
        onListClick();
        setupButtons();
    }

    //listView existing routes
    private void listRoutes () {
        //
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

    private void setupButtons() {
        //add new route button
        Button btn_new = (Button) findViewById(R.id.buttonAddRoute);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SelectRouteActivity.this, AddRouteActivity.class);
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
