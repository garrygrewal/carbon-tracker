package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import sfu.cmpt276.carbontracker.model.CarbonModel;

public class AddBillActivity extends AppCompatActivity {

    private int new_bill_index;
    boolean dateStartEntered = false;
    boolean dateEndEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        setupButtons();
        premakeBill();
    }

    private void addNewBill(){
        EditText editElectricityUse = (EditText) findViewById(R.id.editTextElectricity);
        Float electricityUse = Float.valueOf(editElectricityUse.getText().toString());
        CarbonModel.getInstance().getBill(new_bill_index).setElectricity(electricityUse);

        EditText editNaturalGasUse = (EditText) findViewById(R.id.editTextNaturalGas);
        Float naturalGasUse = Float.valueOf(editNaturalGasUse.getText().toString());
        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGas(naturalGasUse);

        EditText editNumberOfPeople = (EditText) findViewById(R.id.editTextNumberOfPeople);
        int numberOfPeople = Integer.parseInt(editNumberOfPeople.getText().toString());
        CarbonModel.getInstance().getBill(new_bill_index).setNumberOfPeople(numberOfPeople);


        CarbonModel.getInstance().getBill(new_bill_index).setPeriod();
        Log.d("my app", "Bill period in days: " + CarbonModel.getInstance().getBill(new_bill_index).getPeriod());
    }

    private void premakeBill(){
        new_bill_index = CarbonModel.getInstance().newBillIndex();
        CarbonModel.getInstance().newBill();
    }

    private void setupButtons(){
        final EditText startDate = (EditText) findViewById(R.id.startDate);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                Dialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getBill(new_bill_index).setStartDate(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dateStartEntered = true;
            }
        });

        final EditText endDate = (EditText) findViewById(R.id.endDate);

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                Dialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getBill(new_bill_index).setEndDate(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dateEndEntered = true;
            }
        });

        Button btn_ok = (Button) findViewById(R.id.buttonAddBill);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput() == 0) {
                    setResult(Activity.RESULT_CANCELED);
                } else {
                    addNewBill();
                    Intent intent = new Intent(AddBillActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
            }
        });

        //cancel button
        Button btn_cancel = (Button) findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete premade bill
                CarbonModel.getInstance().deleteBill(new_bill_index);

                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private int checkInput() {
        EditText in_dateStart = (EditText) findViewById(R.id.startDate);
        EditText in_dateEnd = (EditText) findViewById(R.id.endDate);
        EditText in_electricity = (EditText) findViewById(R.id.editTextElectricity);
        EditText in_naturalGas = (EditText) findViewById(R.id.editTextNaturalGas);
        EditText in_numberOfPeople = (EditText) findViewById(R.id.editTextNumberOfPeople);

        //check if input is valid
        if(dateStartEntered == false || dateEndEntered == false){
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a date." ,Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        if(in_electricity.getText().toString().trim().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your electricity usage" ,Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if(in_naturalGas.getText().toString().trim().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your natural gas usage" ,Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if(in_numberOfPeople.getText().toString().trim().isEmpty() || Integer.parseInt(in_numberOfPeople.getText().toString()) == 0){
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter the number of people in your household" ,Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        else if (in_dateStart.equals("Select Date") || in_dateEnd.equals("Select Date")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select date.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        else {
            return 1;
        }
    }

    //back button
    @Override
    public void onBackPressed() {
        CarbonModel.getInstance().deleteBill(new_bill_index);

        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
