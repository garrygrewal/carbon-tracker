package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
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
import java.util.List;

import sfu.cmpt276.carbontracker.model.Bill;
import sfu.cmpt276.carbontracker.model.CarbonModel;
import sfu.cmpt276.carbontracker.model.Journey;

/*
displays all known journeys and bills
 */

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
        ListView journey_list = (ListView) findViewById(R.id.listViewTotalFootprint);
        ArrayList journeyList = getJourneyList();

        //List Adapter
        journey_list.setAdapter(new JourneyAdapter(this, journeyList));

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

    public ArrayList getJourneyList(){
        ArrayList<Journey> result = new ArrayList<>();
        for (int i=0; i<CarbonModel.getInstance().countJourneys(); i++) {
            result.add(CarbonModel.getInstance().getJourney(i));
        }

        return result;
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
                menu.setHeaderTitle(R.string.journey_options);
                menu.add(0, J_EDIT, 0, R.string.edit);
                menu.add(0, J_DELETE, 0, R.string.delete);
                break;
            case R.id.listViewBills:
                menu.setHeaderTitle(R.string.bill_options);
                menu.add(0, B_EDIT, 0, R.string.edit);
                menu.add(0, B_DELETE, 0, R.string.delete);
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
                //save data
                CarbonModel.getInstance().SaveData();
                break;
            case B_EDIT:
                Intent  intentBill = new Intent(TotalFootprintActivity.this, AddBillActivity.class);
                intentBill.putExtra("bill_index", info.position);
                startActivity(intentBill);
                break;
            case B_DELETE:
                CarbonModel.getInstance().deleteBill(info.position);
                listBills();
                //save data
                CarbonModel.getInstance().SaveData();
                break;
            default:
                return false;
        }
        return true;
    }

    public class JourneyAdapter extends  BaseAdapter{
        private ArrayList<Journey> listData;
        private LayoutInflater layoutInflater;

        public JourneyAdapter(Context aContext, ArrayList<Journey> listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }
        @Override
        public int getCount() {
            return CarbonModel.getInstance().countJourneys();
        }

        @Override
        public Object getItem(int position) {
            return CarbonModel.getInstance().getJourney(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TotalFootprintActivity.JourneyAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_journey, null);
                holder = new TotalFootprintActivity.JourneyAdapter.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.journey_name);
                holder.details = (TextView) convertView.findViewById(R.id.journey_details);
                holder.date = (TextView) convertView.findViewById(R.id.journey_date);
                convertView.setTag(holder);
            } else {
                holder = (TotalFootprintActivity.JourneyAdapter.ViewHolder) convertView.getTag();
            }

            Journey journey = listData.get(position);
            holder.name.setText(journey.getJourneyName());
            if (!CarbonModel.getInstance().getHumanRelatableUnitEnabled()) {
                holder.details.setText(journey.getCo2PerCity() + " " + getString(R.string.kgC02City) + "\n" + journey.getCo2PerHighway() + " " + getString(R.string.kgC02highway));
            }
            else {
                holder.details.setText(journey.getTreesPerCity() + " " + getString(R.string.trees_by_city) + "\n" + journey.getTreesPerCity() + " " + getString(R.string.trees_by_highway));
            }
            holder.date.setText(journey.getStringDate());

            //vehicle icon
            TextView icon = (TextView) convertView.findViewById(R.id.iconView);
            int i = Integer.parseInt(CarbonModel.getInstance().getVehicleIcon((journey.getVehicleIndex())));
            switch(i) {
                case 0:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.coupe, 0, 0, 0);
                    break;
                case 1:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.hatch, 0, 0, 0);
                    break;
                case 2:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.suv, 0, 0, 0);
                    break;
                case 3:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.van, 0, 0, 0);
                    break;
                case 4:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.truck, 0, 0, 0);
                    break;
                case 5:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.bike, 0, 0, 0);
                    break;
                case 6:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.bus, 0, 0, 0);
                    break;
                case 7:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.train, 0, 0, 0);
                    break;
                default:
                    icon.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.hatch, 0, 0, 0);
                    break;
            }
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView details;
            TextView date;
        }

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
                if (!CarbonModel.getInstance().getHumanRelatableUnitEnabled()) {
                    holder.details.setText(bill.getElectricityEmissions() + " " + getString(R.string.kgOfCO2by) + " " + bill.getElectricityUse() + " " + getString(R.string.kwhElectricityFrom) + " " + bill.getStartDate().getActualDate() + ", " + getString(R.string.too) + " " + bill.getEndDate().getActualDate() + ", " + getString(R.string.with) + " " + bill.getNumberOfPeople() + " " + getString(R.string.persons));
                }
                else {
                    holder.details.setText(bill.getElectricityTreesEmissions() + " " + getString(R.string.trees_by) + " " + bill.getElectricityUse() + " " + getString(R.string.kwhElectricityFrom) + " " + bill.getStartDate().getActualDate() + ", " + getString(R.string.too) + " " + bill.getEndDate().getActualDate() + ", " + getString(R.string.with) + " " + bill.getNumberOfPeople() + " " + getString(R.string.persons));
                }
            }
            else if(bill.getType().equals("Natural Gas")){
                if (!CarbonModel.getInstance().getHumanRelatableUnitEnabled()) {
                    holder.details.setText(bill.getNaturalGasEmissions() + " " + getString(R.string.kgOfCO2by) + " " + bill.getNaturalGasUse() + "\n" + getString(R.string.GJofNaturalGasFrom) + " " + bill.getStartDate().getActualDate() + ", " + getString(R.string.too) + " " + bill.getEndDate().getActualDate() + ", " + getString(R.string.with) + " " + bill.getNumberOfPeople() + " " + getString(R.string.persons));
                }
                else {
                    holder.details.setText(bill.getNaturalGasTreesEmissions() + " " + getString(R.string.trees_by) + " " + bill.getNaturalGasUse() + " " + getString(R.string.GJofNaturalGasFrom) + " " + bill.getStartDate().getActualDate() + ", " + getString(R.string.too) + " " + bill.getEndDate().getActualDate() + ", " + getString(R.string.with) + " " + bill.getNumberOfPeople() + " " + getString(R.string.persons));
                }
            }
            holder.period.setText(getString(R.string.totalDays) +" "+ bill.getPeriod());
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView details;
            TextView period;
        }
    }

    //action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Intent intent = new Intent(TotalFootprintActivity.this, SelectVehicleActivity.class);
            startActivity(intent);
            return(true);
        case R.id.reset:
            Intent back = new Intent();
            setResult(Activity.RESULT_CANCELED, back);
            finish();
            return(true);
        case R.id.about:
            startActivity(new Intent(TotalFootprintActivity.this, AboutActivity.class));
            return(true);
        case R.id.exit:
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);
            exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    //hide navigation bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View mDecorView = getWindow().getDecorView();
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
