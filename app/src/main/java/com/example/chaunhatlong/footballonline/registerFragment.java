package com.example.chaunhatlong.footballonline;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

public class registerFragment extends Fragment {

    static DatabaseHelper databaseHelper;
    static Button btn_register, btn_time_start, btn_time_end, btn_day;
    static EditText usernameEt;
    static Spinner fieldEt;
    static TextView time_startEt, time_endEt, day_matchEt;

    public registerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container,
                false);

        databaseHelper = new DatabaseHelper(this.getActivity());
        databaseHelper.openDatabase();

        usernameEt = (EditText)v.findViewById(R.id.user);

        fieldEt = (Spinner)v.findViewById(R.id.fields);

        time_startEt = (TextView)v.findViewById(R.id.time_start);
        time_endEt = (TextView)v.findViewById(R.id.time_end);
        day_matchEt = (TextView)v.findViewById(R.id.day_match);

        btn_time_start = (Button)v.findViewById(R.id.btn_time_start);
        btn_time_end = (Button)v.findViewById(R.id.btn_time_end);
        btn_day = (Button)v.findViewById(R.id.btn_day);
        btn_register = (Button)v.findViewById(R.id.btn_register);

        //Get field name
        getChooseField();

        //Get time Start of Match
        getTimeStart();

        //Get time End of Match
        getTimeEnd();

        //get day of Match
        getDateOfMatch();


        return v;
    }

    public void registerField(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEt.getText().toString();
                String fieldname = fieldEt.getSelectedItem().toString();
                String timeStart = time_startEt.getText().toString();
                String timeEnd  = time_endEt.getText().toString();
                String dayMatch = day_matchEt.getText().toString();

                btn_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


            }
        });
    }

    public void getChooseField(){
        String arr[] = databaseHelper.getField_name();

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr);

        fieldEt.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fieldEt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(getContext(), item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Selected",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getTimeStart(){
        btn_time_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour < 10){
                            time_startEt.setText( "0" + selectedHour + ":" + selectedMinute);
                        }
                        if(selectedMinute < 10){
                            time_startEt.setText( selectedHour + ":" + "0" + selectedMinute);
                        }else{
                            time_startEt.setText( selectedHour + ":" + selectedMinute);
                        }

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time Start");
                mTimePicker.show();
            }

        });
    }

    public void getTimeEnd(){
        btn_time_end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour < 10){
                            time_endEt.setText( "0" + selectedHour + ":" + selectedMinute);
                        }
                        if(selectedMinute < 10){
                            time_endEt.setText( selectedHour + ":" + "0" + selectedMinute);
                        }else{
                            time_endEt.setText( selectedHour + ":" + selectedMinute);
                        }

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time End");
                mTimePicker.show();
            }

        });
    }

    public void getDateOfMatch(){
        btn_day.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        day_matchEt.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
    }
}
