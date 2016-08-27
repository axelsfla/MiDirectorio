package com.secbe.coursera.midirectorio;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TextView tvFechaNacimiento;

    private TextInputEditText ietNombreCompleto, ietTelefono, ietEmail, ietDescripcionContacto;
    private TextInputLayout tilNombreCompleto, tilTelefono, tilEmail;
    private Button btnSiguiente;
    private Button btnFechaNacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilNombreCompleto = (TextInputLayout) findViewById(R.id.tilNombreCompleto);
        tilTelefono = (TextInputLayout) findViewById(R.id.tilTelefono);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        ietNombreCompleto = (TextInputEditText) findViewById(R.id.ietNombreCompleto);
        ietTelefono = (TextInputEditText) findViewById(R.id.ietTelefono);
        ietEmail = (TextInputEditText) findViewById(R.id.ietEmail);
        ietDescripcionContacto = (TextInputEditText) findViewById(R.id.ietDescripcionContacto);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnFechaNacimiento = (Button) findViewById(R.id.btnFechaNacimiento);
        tvFechaNacimiento = (TextView) findViewById(R.id.tvFechaNacimiento);

        ietNombreCompleto.addTextChangedListener(new MyTextWatcher(ietNombreCompleto));
        ietTelefono.addTextChangedListener(new MyTextWatcher(ietTelefono));
        ietEmail.addTextChangedListener(new MyTextWatcher(ietEmail));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFormulario();
            }
        });

        Bundle parametros = getIntent().getExtras();
        if(parametros != null && !parametros.isEmpty()){
            String nombre = parametros.getString(getResources().getString(R.string.pNombre));
            String fechaNacimiento = parametros.getString(getResources().getString(R.string.pFechaNacimiento));
            String telefono = parametros.getString(getResources().getString(R.string.pTelefono));
            String email = parametros.getString(getResources().getString(R.string.pEmail));
            String descripcionContacto = parametros.getString(getResources().getString(R.string.pDescripcion));

            ietNombreCompleto.setText(nombre);
            tvFechaNacimiento.setText(fechaNacimiento);
            ietTelefono.setText(telefono);
            ietEmail.setText(email);
            ietDescripcionContacto.setText(descripcionContacto);
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void submitFormulario() {
        if (!validaNombreCompleto()) {
            return;
        }

        if (!validaFechaNacimiento()) {
            return;
        }

        if (!validaTelefono()) {
            return;
        }

        if (!validaEmail()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Gracias!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this,ConfirmacionDatos.class);
        intent.putExtra(getResources().getString(R.string.pNombre), ietNombreCompleto.getText().toString().trim());
        intent.putExtra(getResources().getString(R.string.pFechaNacimiento), tvFechaNacimiento.getText().toString());
        intent.putExtra(getResources().getString(R.string.pTelefono), ietTelefono.getText().toString().trim());
        intent.putExtra(getResources().getString(R.string.pEmail), ietEmail.getText().toString().trim());
        intent.putExtra(getResources().getString(R.string.pDescripcion), ietDescripcionContacto.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    private boolean validaNombreCompleto() {
        if (ietNombreCompleto.getText().toString().trim().isEmpty()) {
            tilNombreCompleto.setError(getString(R.string.err_msg_nombreCompleto));
            requestFocus(ietNombreCompleto);
            return false;
        } else {
            tilNombreCompleto.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validaFechaNacimiento() {
        if (tvFechaNacimiento.getText().toString().trim() == getString(R.string.fechaNacimiento)) {
            //tilNombreCompleto.setError(getString(R.string.err_msg_fechaNacimiento));
            Toast.makeText(getApplicationContext(), getString(R.string.err_msg_fechaNacimiento), Toast.LENGTH_SHORT).show();
            requestFocus(btnFechaNacimiento);
            return false;
        } else {
            tilNombreCompleto.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validaEmail() {
        String email = ietEmail.getText().toString().trim();

        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.err_msg_email));
            requestFocus(ietEmail);
            return false;
        }
        else if(!isValidEmail(email)){
            tilEmail.setError(getString(R.string.err_msg_email_valido));
            requestFocus(ietEmail);
            return false;
        }
        else {
            tilEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validaTelefono() {
        String telefono = ietTelefono.getText().toString().trim();
        if (telefono.isEmpty()) {
            tilTelefono.setError(getString(R.string.err_msg_telefono));
            requestFocus(ietTelefono);
            return false;
        }else if(!isValidPhone(telefono)){
            tilTelefono.setError(getString(R.string.err_msg_telefono_valido));
            requestFocus(ietTelefono);
            return false;
        } else {
            tilTelefono.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.ietNombreCompleto:
                    validaNombreCompleto();
                    break;
                case R.id.ietTelefono:
                    validaTelefono();
                    break;
                case R.id.ietEmail:
                    validaEmail();
                    break;
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private CharSequence title;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            String fechaSeleccionada = ((TextView) getActivity().findViewById(R.id.tvFechaNacimiento)).getText().toString();
            if(fechaSeleccionada != getResources().getString(R.string.fechaNacimiento)){
                String[] textoFecha = fechaSeleccionada.split(": ");
                String[] fecha = textoFecha[1].split("/");

                day = Integer.parseInt(fecha[0]);
                month = Integer.parseInt(fecha[1])-1;
                year = Integer.parseInt(fecha[2]);

            }

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dialogDatePicker = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, this, year, month, day);
            //android.R.style.
            //DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog, this, year,month, day);
            //dialogDatePicker.getDatePicker().set
            //dialogDatePicker.getDatePicker().setSpinnersShown(true);
            //dialogDatePicker.getDatePicker().setCalendarViewShown(false);
            dialogDatePicker.setTitle(getResources().getString(R.string.err_msg_fechaNacimiento));
            return dialogDatePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month + 1;
            String fechaNacimiento = day + "/" + month + "/" + year;
            ((TextView) getActivity().findViewById(R.id.tvFechaNacimiento)).setText(getResources().getText(R.string.fechaNacimiento) + ": " + fechaNacimiento);
        }
    }
}
