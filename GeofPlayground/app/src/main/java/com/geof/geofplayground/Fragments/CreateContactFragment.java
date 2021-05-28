package com.geof.geofplayground.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geof.geofplayground.Activities.MainActivity;
import com.geof.geofplayground.Model.Contacts;
import com.geof.geofplayground.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class CreateContactFragment extends Fragment {

    private static TextInputEditText mFname;
    private static TextInputEditText mLname;
    private static EditText mBirthday;
    private static TextInputEditText mEmail;
    private static TextInputEditText mNumber;
    private static TextInputEditText mAddress;
    private static TextInputEditText mContactPerson;
    private static TextInputEditText mCpNumber;
    private TextInputLayout datePicker;
    public Button mCancelBtn;
    public Button mSaveBtn;
    public createContactFragmentListener mListener;
    public Context ctx;
    String age;

    int dy;
    int mth;
    int yr;
    DatePickerDialog.OnDateSetListener dateListener;

    public CreateContactFragment(Context ctx) {
        this.ctx = ctx;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_contact_fragment, container, false);
        mFname = (TextInputEditText)view.findViewById(R.id.ti_fname);
        mLname = (TextInputEditText)view.findViewById(R.id.ti_lname);
        mBirthday = (EditText)view.findViewById(R.id.et_dob);
        mEmail = (TextInputEditText)view.findViewById(R.id.ti_email);
        mNumber = (TextInputEditText)view.findViewById(R.id.ti_number);
        mAddress = (TextInputEditText)view.findViewById(R.id.ti_address);
        mContactPerson = (TextInputEditText)view.findViewById(R.id.ti_contactPerson);
        mCpNumber = (TextInputEditText)view.findViewById(R.id.ti_cpNumber);
        mSaveBtn = (Button)view.findViewById(R.id.btn_save);
        mCancelBtn = (Button)view.findViewById(R.id.btn_cancel);
        mListener = (createContactFragmentListener) ctx;
        datePicker = (TextInputLayout)view.findViewById(R.id.tl_datepicker);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        mBirthday.clearFocus();


        mBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    mBirthday.setInputType(InputType.TYPE_NULL);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String date = dayOfMonth + "/" + month + "/" + year;
                            dy = dayOfMonth;
                            mth = month;
                            yr=year;
                            mBirthday.setText(date);
                        }
                    },year,month,day);
                    datePickerDialog.show();
                } else {

                }
            }
        });
        mBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBirthday.setInputType(InputType.TYPE_NULL);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        mBirthday.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateContactFragment.clearInputCache();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = mFname.getText().toString();
                String lName = mLname.getText().toString();
                String birthday = mBirthday.getText().toString();
                String email = mEmail.getText().toString();
                String number = mNumber.getText().toString();
                String address = mAddress.getText().toString();
                String contactPerson = mContactPerson.getText().toString();
                String cpNumber = mCpNumber.getText().toString();
                if(fName.isEmpty() || lName.isEmpty() || birthday.isEmpty() || email.isEmpty() || number.isEmpty() || address.isEmpty() || contactPerson.isEmpty() || cpNumber.isEmpty()){
                    return;
                } else {
                    age = getAge(yr,mth,dy);
                    Contacts contacts = new Contacts(fName, lName, birthday, Integer.parseInt(age), email, number, address, contactPerson, cpNumber);
                    mListener.saveContact(contacts);
                }
            }
        });
        return view;
    }

    public interface createContactFragmentListener{
        void saveContact(Contacts contacts);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static void clearInputCache(){

        mFname.getText().clear();
        mLname.getText().clear();
        mBirthday.setText("");
        mEmail.getText().clear();
        mNumber.getText().clear();
        mAddress.getText().clear();
        mContactPerson.getText().clear();
        mCpNumber.getText().clear();
    }
}
