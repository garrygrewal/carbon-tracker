package sfu.cmpt276.carbontracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import sfu.cmpt276.carbontracker.model.CarbonModel;

/*
Bill Activity allows user to add monthly utilities
*/

public class AddBillActivity extends AppCompatActivity {

    private int new_bill_index;
    //for edit
    private int bill_index;

    boolean dateStartEntered = false;
    boolean dateEndEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        premakeBill();
        setupButtons();
        showEmissions();
        showRadioButtons();
        UiChangeListener();
    }

    private void premakeBill() {
        Intent intent = getIntent();
        bill_index = intent.getIntExtra("bill_index", -1);
        if (bill_index != -1) {
            final EditText startDate = (EditText) findViewById(R.id.startDate);
            final EditText endDate = (EditText) findViewById(R.id.endDate);
            final EditText numbPeople = (EditText) findViewById(R.id.editTextNumberOfPeople);
            final EditText electricityUse = (EditText) findViewById(R.id.editTextElectricity);
            final EditText naturalGasUse = (EditText) findViewById(R.id.editTextNaturalGas);
            final TextView electricityEmissions = (TextView) findViewById(R.id.textUserElectricityEmissions);
            final TextView naturalGasEmissions = (TextView) findViewById(R.id.textUserNaturalGasEmissions);

            startDate.setText(CarbonModel.getInstance().getBill(bill_index).getStartDate().getActualDate());
            endDate.setText(CarbonModel.getInstance().getBill(bill_index).getEndDate().getActualDate());
            numbPeople.setText(String.valueOf(CarbonModel.getInstance().getBill(bill_index).getNumberOfPeople()));

            if (CarbonModel.getInstance().getBill(bill_index).getType().equals("Electricity")) {
                electricityUse.setText(String.valueOf(CarbonModel.getInstance().getBill(bill_index).getElectricityUse()));
                electricityEmissions.setText(String.valueOf(CarbonModel.getInstance().getBill(bill_index).getElectricityEmissions()));
            } else if (CarbonModel.getInstance().getBill(bill_index).getType().equals("Natural Gas")) {
                naturalGasUse.setText(String.valueOf(CarbonModel.getInstance().getBill(bill_index).getNaturalGasUse()));
                naturalGasEmissions.setText(String.valueOf(CarbonModel.getInstance().getBill(bill_index).getNaturalGasEmissions()));
            }
            new_bill_index = bill_index;
        } else {
            new_bill_index = CarbonModel.getInstance().newBillIndex();
            CarbonModel.getInstance().newBill();
        }
    }

    private void setupButtons() {
        final TextView emissionsStartDate = (TextView) findViewById(R.id.textEmissionsStartDate);
        final TextView emissionsEndDate = (TextView) findViewById(R.id.textEmissionsEndDate);
        final EditText editStartDate = (EditText) findViewById(R.id.startDate);
        final EditText editEndDate = (EditText) findViewById(R.id.endDate);
        final TextView emissionsElectricity = (TextView) findViewById(R.id.textUserElectricityEmissions);
        final TextView emissionsNaturalGas = (TextView) findViewById(R.id.textUserNaturalGasEmissions);
        final EditText useElectricity = (EditText) findViewById(R.id.editTextElectricity);
        final EditText useNaturalGas = (EditText) findViewById(R.id.editTextNaturalGas);

        //start Date
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        //end Date
        final Calendar c2 = Calendar.getInstance();
        final int mYear2 = c2.get(Calendar.YEAR); // current year
        final int mMonth2 = c2.get(Calendar.MONTH); // current month
        final int mDay2 = c2.get(Calendar.DAY_OF_MONTH); // current day

        if (bill_index != -1) {
            dateEndEntered = true;
            dateStartEntered = true;
        }

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                Dialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        editStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getBill(new_bill_index).setStartDate(year, monthOfYear + 1, dayOfMonth);
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
                // date picker dialog
                Dialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        editEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        CarbonModel.getInstance().getBill(new_bill_index).setEndDate(year, monthOfYear + 1, dayOfMonth);
                    }
                }, mYear2, mMonth2, mDay2);
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
                } else if (bill_index != -1) {
                    CarbonModel.getInstance().getBill(bill_index).setPeriod();
                    if (CarbonModel.getInstance().getBill(bill_index).getType().equals("Electricity")) {
                        CarbonModel.getInstance().getBill(bill_index).setElectricityUse(Float.parseFloat(useElectricity.getText().toString()));
                        CarbonModel.getInstance().getBill(bill_index).setElectricityEmissions(Float.parseFloat(emissionsElectricity.getText().toString()));
                        CarbonModel.getInstance().getBill(bill_index).setNaturalGasUse(0);
                        CarbonModel.getInstance().getBill(bill_index).setNaturalGasEmissions(0);
                        CarbonModel.getInstance().getTipsArray().generateElectricityTip(CarbonModel.getInstance().getBill(new_bill_index).getElectricityEmissions());

                        displayTips();
                    } else if (CarbonModel.getInstance().getBill(bill_index).getType().equals("Natural Gas")) {
                        CarbonModel.getInstance().getBill(bill_index).setNaturalGasUse(Float.parseFloat(useNaturalGas.getText().toString()));
                        CarbonModel.getInstance().getBill(bill_index).setElectricityEmissions(Float.parseFloat(emissionsNaturalGas.getText().toString()));
                        CarbonModel.getInstance().getBill(bill_index).setElectricityUse(0);
                        CarbonModel.getInstance().getBill(bill_index).setElectricityEmissions(0);
                        CarbonModel.getInstance().getTipsArray().generateNaturalGasTip(CarbonModel.getInstance().getBill(new_bill_index).getNaturalGasEmissions());

                        displayTips();

                    }

                    Intent intent = new Intent(AddBillActivity.this, TotalFootprintActivity.class);
                    startActivity(intent);


                    //save data
                    CarbonModel.getInstance().SaveData();
                } else {
                    CarbonModel.getInstance().getBill(new_bill_index).setPeriod();
                    if (CarbonModel.getInstance().getBill(new_bill_index).getType().equals("Electricity")) {
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityUse(Float.parseFloat(useElectricity.getText().toString()));
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(Float.parseFloat(emissionsElectricity.getText().toString()));
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasUse(0);
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasEmissions(0);
                        CarbonModel.getInstance().getTipsArray().generateElectricityTip(CarbonModel.getInstance().getBill(new_bill_index).getElectricityEmissions());

                        displayTips();

                    } else if (CarbonModel.getInstance().getBill(new_bill_index).getType().equals("Natural Gas")) {
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasUse(Float.parseFloat(useNaturalGas.getText().toString()));
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(Float.parseFloat(emissionsNaturalGas.getText().toString()));
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityUse(0);
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(0);
                        CarbonModel.getInstance().getTipsArray().generateNaturalGasTip(CarbonModel.getInstance().getBill(new_bill_index).getNaturalGasEmissions());

                        displayTips();
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Bill added to Carbon Footprint", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(AddBillActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    //save data
                    CarbonModel.getInstance().SaveData();
                }
            }
        });

        //cancel button
        Button btn_cancel = (Button) findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bill_index != -1) {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_CANCELED, intent);
                    finish();
                } else {
                    //delete premade bill
                    CarbonModel.getInstance().deleteBill(new_bill_index);

                    Intent intent = new Intent();
                    setResult(Activity.RESULT_CANCELED, intent);
                    finish();
                }
            }
        });

    }

    private void displayTips() {
        String message = CarbonModel.getInstance().getTipsArray().getNextTipInfo();
        if(message != null && !message.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private int checkInput() {
        EditText in_dateStart = (EditText) findViewById(R.id.startDate);
        EditText in_dateEnd = (EditText) findViewById(R.id.endDate);
        EditText in_electricity = (EditText) findViewById(R.id.editTextElectricity);
        EditText in_naturalGas = (EditText) findViewById(R.id.editTextNaturalGas);
        EditText in_numberOfPeople = (EditText) findViewById(R.id.editTextNumberOfPeople);

        //check if input is valid
        if (!dateStartEntered || !dateEndEntered) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if (in_electricity.getVisibility() == View.VISIBLE && in_electricity.getText().toString().trim().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your electricity usage", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if (in_naturalGas.getVisibility() == View.VISIBLE && in_naturalGas.getText().toString().trim().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your natural gas usage", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }

        if (in_numberOfPeople.getText().toString().trim().isEmpty() || Integer.parseInt(in_numberOfPeople.getText().toString()) == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter the number of people in your household", Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        } else if (in_dateStart.equals("Select Date") || in_dateEnd.equals("Select Date")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select date", Toast.LENGTH_SHORT);
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

    private void showRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroupType);


        String[] billsType = getResources().getStringArray(R.array.type_bill);

        for (int i = 0; i < billsType.length; i++) {
            final String type = billsType[i];

            RadioButton button = new RadioButton(this);
            button.setText(type);
            button.setPadding(0, 0, 100, 0);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarbonModel.getInstance().getBill(new_bill_index).setType(type);
                    if (type.equals("Electricity")) {
                        electricityBill();
                    } else if (type.equals("Natural Gas")) {
                        naturalGasBill();
                    }
                }
            });
            group.addView(button);
        }

        if (bill_index != -1) {
            if (CarbonModel.getInstance().getBill(bill_index).getType().equals("Electricity")) {
                electricityBill();
            } else if (CarbonModel.getInstance().getBill(bill_index).getType().equals("Natural Gas")) {
                naturalGasBill();
            }
        }
    }

    private void electricityBill() {
        final TextView emissionsNaturalGas = (TextView) findViewById(R.id.textNaturalGasEmission);
        final TextView emissionsElectricity = (TextView) findViewById(R.id.textElectricityEmission);
        final EditText editNaturalGasUse = (EditText) findViewById(R.id.editTextNaturalGas);
        final TextView textNaturalGas = (TextView) findViewById(R.id.textNaturalGas);
        final EditText editElectricityUse = (EditText) findViewById(R.id.editTextElectricity);
        final TextView textElectricity = (TextView) findViewById(R.id.textElectricity);
        final TextView displayElectricityEmissions = (TextView) findViewById(R.id.textUserElectricityEmissions);
        final TextView displayNaturalGasEmissions = (TextView) findViewById(R.id.textUserNaturalGasEmissions);

        displayElectricityEmissions.setVisibility(TextView.VISIBLE);
        displayNaturalGasEmissions.setVisibility(TextView.INVISIBLE);
        textElectricity.setVisibility(TextView.VISIBLE);
        editElectricityUse.setVisibility(EditText.VISIBLE);
        emissionsElectricity.setVisibility(TextView.VISIBLE);
        textNaturalGas.setVisibility(TextView.INVISIBLE);
        editNaturalGasUse.setVisibility(EditText.INVISIBLE);
        emissionsNaturalGas.setVisibility(TextView.INVISIBLE);
    }

    private void naturalGasBill() {
        final TextView emissionsNaturalGas = (TextView) findViewById(R.id.textNaturalGasEmission);
        final TextView emissionsElectricity = (TextView) findViewById(R.id.textElectricityEmission);
        final EditText editNaturalGasUse = (EditText) findViewById(R.id.editTextNaturalGas);
        final TextView textNaturalGas = (TextView) findViewById(R.id.textNaturalGas);
        final EditText editElectricityUse = (EditText) findViewById(R.id.editTextElectricity);
        final TextView textElectricity = (TextView) findViewById(R.id.textElectricity);
        final TextView displayElectricityEmissions = (TextView) findViewById(R.id.textUserElectricityEmissions);
        final TextView displayNaturalGasEmissions = (TextView) findViewById(R.id.textUserNaturalGasEmissions);

        displayElectricityEmissions.setVisibility(TextView.INVISIBLE);
        displayNaturalGasEmissions.setVisibility(TextView.VISIBLE);
        textNaturalGas.setVisibility(TextView.VISIBLE);
        editNaturalGasUse.setVisibility(EditText.VISIBLE);
        emissionsNaturalGas.setVisibility(TextView.VISIBLE);
        emissionsElectricity.setVisibility(TextView.INVISIBLE);
        textElectricity.setVisibility(TextView.INVISIBLE);
        editElectricityUse.setVisibility(EditText.INVISIBLE);
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
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityUse(Float.parseFloat(electricityUse));
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(Float.parseFloat(electricityUse));
                        float electricity = CarbonModel.getInstance().getBill(new_bill_index).calculateElectricityPerPerson();
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(electricity);
                        displayElectricityEmissions.setText("" + electricity);
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
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasUse(Float.parseFloat(naturalGasUse));
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasEmissions(Float.parseFloat(naturalGasUse));
                        float naturalGas = CarbonModel.getInstance().getBill(new_bill_index).calculateNaturalGasPerPerson();
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasEmissions(naturalGas);
                        displayNaturalGasEmissions.setText("" + naturalGas);
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
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasUse(Float.parseFloat(naturalGasUse));
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasEmissions(Float.parseFloat(naturalGasUse));
                        float naturalGas = CarbonModel.getInstance().getBill(new_bill_index).calculateNaturalGasPerPerson();
                        CarbonModel.getInstance().getBill(new_bill_index).setNaturalGasEmissions(naturalGas);
                        displayNaturalGasEmissions.setText("" + naturalGas);
                    }
                } catch (NumberFormatException e) {
                }
                try {
                    CarbonModel.getInstance().getBill(new_bill_index).setNumberOfPeople(Integer.parseInt(numberOfPeople));
                    if (electricityUse.length() == 0 || numberOfPeople.length() == 0) {
                        displayElectricityEmissions.setText("");
                    } else {
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityUse(Float.parseFloat(electricityUse));
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(Float.parseFloat(electricityUse));
                        float electricity = CarbonModel.getInstance().getBill(new_bill_index).calculateElectricityPerPerson();
                        CarbonModel.getInstance().getBill(new_bill_index).setElectricityEmissions(electricity);
                        displayElectricityEmissions.setText("" + electricity);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
    }

    //action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.reset:
            Intent back = new Intent();
            setResult(Activity.RESULT_CANCELED, back);
            finish();
            return(true);
        case R.id.about:
            startActivity(new Intent(AddBillActivity.this, AboutActivity.class));
            return(true);
        case R.id.exit:
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
    public void UiChangeListener()
    {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
    }
}
