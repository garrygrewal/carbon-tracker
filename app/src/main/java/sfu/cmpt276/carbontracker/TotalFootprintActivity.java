package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sfu.cmpt276.carbontracker.model.Bill;
import sfu.cmpt276.carbontracker.model.CarbonModel;

public class TotalFootprintActivity extends AppCompatActivity {

    final int J_EDIT = 0;
    final int J_DELETE = 1;
    final int B_EDIT = 2;
    final int B_DELETE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_footprint);
        setupButtons();
        listJourneys();
        listBills();
    }

    private void listJourneys() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_journey, CarbonModel.getInstance().getJourneyInfo());
        ListView journey_list = (ListView) findViewById(R.id.listViewTotalFootprint);

        //List Adapter
        journey_list.setAdapter(adapter);

        //Context Menu for long press
        registerForContextMenu(journey_list);
    }

    private void listBills(){
        ListView bill_list = (ListView) findViewById(R.id.listViewBills);
        ArrayList billList = getBillList();

        bill_list.setAdapter(new BillAdapter(this, billList));

        //context menu for long press here
        registerForContextMenu(bill_list);
    }

    public ArrayList getBillList() {
        ArrayList<Bill> result = new ArrayList<>();
        for (int i=0; i<CarbonModel.getInstance().countBills(); i++) {
            result.add(CarbonModel.getInstance().getBill(i));
        }

        return result;
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
        switch(v.getId()) {
            case R.id.listViewTotalFootprint:
                menu.setHeaderTitle("Journey Options");
                menu.add(0, J_EDIT, 0, "Edit");
                menu.add(0, J_DELETE, 0, "Delete");
                break;
            case R.id.listViewBills:
                menu.setHeaderTitle("Bill Options");
                menu.add(0, B_EDIT, 0, "Edit");
                menu.add(0, B_DELETE, 0, "Delete");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case J_EDIT:
                // sends relevant information to AddVehicleActivity
                Intent intentJourney = new Intent(TotalFootprintActivity.this, SelectVehicleActivity.class);
                intentJourney.putExtra("journey_index", info.position);
                startActivity(intentJourney);
                break;
            case J_DELETE:
                CarbonModel.getInstance().deleteJourney(info.position);
                listJourneys();
                break;
            case B_EDIT:
                Intent  intentBill = new Intent(TotalFootprintActivity.this, AddBillActivity.class);
                intentBill.putExtra("bill_index", info.position);
                startActivity(intentBill);
                break;
            case B_DELETE:
                CarbonModel.getInstance().deleteBill(info.position);
                listBills();
                break;
            default:
                return false;
        }
        return true;
    }


    public class BillAdapter extends BaseAdapter {
        private ArrayList<Bill> listData;
        private LayoutInflater layoutInflater;

        public BillAdapter(Context aContext, ArrayList<Bill> listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }

        @Override
        public int getCount() {
            return CarbonModel.getInstance().countBills();
        }

        @Override
        public Object getItem(int position) {
            return CarbonModel.getInstance().getBill(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TotalFootprintActivity.BillAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_bill, null);
                holder = new TotalFootprintActivity.BillAdapter.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.bill_type);
                holder.details = (TextView) convertView.findViewById(R.id.bill_details);
                holder.period = (TextView) convertView.findViewById(R.id.bill_period);
                convertView.setTag(holder);
            } else {
                holder = (TotalFootprintActivity.BillAdapter.ViewHolder) convertView.getTag();
            }

            Bill bill = listData.get(position);
            holder.name.setText(bill.getType());
            if(bill.getType().equals("Electricity")){
                holder.details.setText(bill.getElectricityEmissions()+" kg of CO2, with " + bill.getElectricityUse() +"kWh, From: " + bill.getStartDate().getString() + ", To: " + bill.getEndDate().getString() + ", with " +bill.getNumberOfPeople() +" person(s)");
            }
            else if(bill.getType().equals("Natural Gas")){
                holder.details.setText(bill.getNaturalGasEmissions()+" kg of CO2, with " +bill.getNaturalGasUse() + "GJ, From: " + bill.getStartDate().getString() + ", To: " + bill.getEndDate().getString() + ", with " +bill.getNumberOfPeople() +" person(s)");

            }
            holder.period.setText("Total Days: " + bill.getPeriod());
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView details;
            TextView period;
        }
    }

    //navigation back button
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(TotalFootprintActivity.this, MainMenuActivity.class);
        setResult(Activity.RESULT_CANCELED, intent);
        startActivity(intent);
    }
}
