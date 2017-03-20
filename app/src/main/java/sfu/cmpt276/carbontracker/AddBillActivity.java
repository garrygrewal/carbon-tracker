package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
        showEmissions();
    }

    private void premakeBill() {
        new_bill_index = CarbonModel.getInstance().newBillIndex();
        CarbonModel.getInstance().newBill();
    }

    private void setupButtons() {
        final TextView emissionsStartDate = (TextView) findViewById(R.id.textEmissionsStartDate);
        final TextView emissionsEndDate = (TextView) findViewById(R.id.textEmissionsEndDate);
        final EditText editStartDate = (EditText) findViewById(R.id.startDate);
        final EditText editEndDate = (EditText) findViewById(R.id.endDate);

        editStartDate.setOnClickListener(new View.OnClickListener() {
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
                        editStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getBill(new_bill_index).setStartDate(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dateStartEntered = true;
            }
        });

        editStartDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String startDate = editStartDate.getText().toString();
                emissionsStartDate.setText(startDate);
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
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
                        editEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getBill(new_bill_index).setEndDate(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                dateEndEntered = true;
            }
        });

        editEndDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String endDate = editEndDate.getText().toString();
                emissionsEndDate.setText(endDate);
            }
        });


        Button btn_ok = (Button) findViewById(R.id.buttonAddBill);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput() == 0) {
                    setResult(Activity.RESULT_CANCELED);
                } else {
                    CarbonModel.getInstance().getBill(new_bill_index).setPeriod();

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
        if (!dateStartEntered || !dateEndEntered) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a date.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        if (in_electricity.getText().toString().trim().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your electricity usage", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if (in_naturalGas.getText().toString().trim().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your natural gas usage", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if (in_numberOfPeople.getText().toString().trim().isEmpty() || Integer.parseInt(in_numberOfPeople.getText().toString()) == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter the number of people in your household", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        } else if (in_dateStart.equals("Select Date") || in_dateEnd.equals("Select Date")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select date.", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        } else {
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

    private void showEmissions() {
        final EditText editElectricityUse = (EditText) findViewById(R.id.editTextElectricity);
        final EditText editNaturalGasUse = (EditText) findViewById(R.id.editTextNaturalGas);
        final EditText editNumberOfPeople = (EditText) findViewById(R.id.editTextNumberOfPeople);
        final TextView displayElectricityEmissions = (TextView) findViewById(R.id.textUserElectricityEmissions);
        final TextView displayNaturalGasEmissions = (TextView) findViewById(R.id.textUserNaturalGasEmissions);

        editElectricityUse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String electricityUse = editElectricityUse.getText().toString();
                String numberOfPeople = editNumberOfPeople.getText().toString();
                try {
                    CarbonModel.getInstance().getBill(new_bill_index).setNumberOfPeople(Integer.parseInt(numberOfPeople));
                    if (electricityUse.length() == 0 || numberOfPeople.length() == 0) {
                        displayElectricityEmissions.setText("");
                    } else {
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricity(Float.parseFloat((editElectricityUse.getText().toString())));
                        float electricity = CarbonModel.getInstance().getBill(new_bill_index).calculateElectricityPerPerson();
                        displayElectricityEmissions.setText("" + electricity + "kg of CO2");
                    }
                } catch (NumberFormatException e) {
                }
            }
        });

        editNaturalGasUse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String naturalGasUse = editNaturalGasUse.getText().toString();
                String numberOfPeople = editNumberOfPeople.getText().toString();
                try {
                    CarbonModel.getInstance().getBill(new_bill_index).setNumberOfPeople(Integer.parseInt(numberOfPeople));
                    if (naturalGasUse.length() == 0 || numberOfPeople.length() == 0) {
                        displayNaturalGasEmissions.setText("");
                    } else {
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGas(Float.parseFloat((editNaturalGasUse.getText().toString())));
                        float naturalGas = CarbonModel.getInstance().getBill(new_bill_index).calculateNaturalGasPerPerson();
                        displayNaturalGasEmissions.setText("" + naturalGas + "kg of CO2");
                    }
                } catch (NumberFormatException e) {
                }
            }
        });

        editNumberOfPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String naturalGasUse = editNaturalGasUse.getText().toString();
                String electricityUse = editElectricityUse.getText().toString();
                String numberOfPeople = editNumberOfPeople.getText().toString();
                try {
                    CarbonModel.getInstance().getBill(new_bill_index).setNumberOfPeople(Integer.parseInt(numberOfPeople));
                    if (naturalGasUse.length() == 0 || numberOfPeople.length() == 0) {
                        displayNaturalGasEmissions.setText("");
                    } else {
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGas(Float.parseFloat((editNaturalGasUse.getText().toString())));
                        float naturalGas = CarbonModel.getInstance().getBill(new_bill_index).calculateNaturalGasPerPerson();
                        displayNaturalGasEmissions.setText("" + naturalGas + "kg of CO2");
                    }
                } catch (NumberFormatException e) {
                }
                try {
                    CarbonModel.getInstance().getBill(new_bill_index).setNumberOfPeople(Integer.parseInt(numberOfPeople));
                    if (electricityUse.length() == 0 || numberOfPeople.length() == 0) {
                        displayElectricityEmissions.setText("");
                    } else {
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricity(Float.parseFloat((editElectricityUse.getText().toString())));
                        float electricity = CarbonModel.getInstance().getBill(new_bill_index).calculateElectricityPerPerson();
                        displayElectricityEmissions.setText("" + electricity + "kg of CO2");
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
    }
}
