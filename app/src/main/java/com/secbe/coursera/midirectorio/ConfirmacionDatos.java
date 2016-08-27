package com.secbe.coursera.midirectorio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmacionDatos extends AppCompatActivity {

    private TextView tvNombreCompleto;
    private TextView tvFechaNacimiento;
    private TextView tvTelefono;
    private TextView tvEmail;
    private TextView tvDescripcion;
    private Button btnEditarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_datos);

        Bundle parametros = getIntent().getExtras();

        String nombre = parametros.getString(getResources().getString(R.string.pNombre));
        String fechaNacimiento = parametros.getString(getResources().getString(R.string.pFechaNacimiento));
        String telefono = parametros.getString(getResources().getString(R.string.pTelefono));
        String email = parametros.getString(getResources().getString(R.string.pEmail));
        String descripcionContacto = parametros.getString(getResources().getString(R.string.pDescripcion));

        tvNombreCompleto = (TextView) findViewById(R.id.tvNombreCompleto);
        tvFechaNacimiento = (TextView) findViewById(R.id.tvFechaNacimiento);
        tvTelefono = (TextView) findViewById(R.id.tvTelefono);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        btnEditarDatos = (Button) findViewById(R.id.btnEditarDatos);

        tvNombreCompleto.setText(nombre);
        tvFechaNacimiento.setText(fechaNacimiento);
        tvTelefono.setText(telefono);
        tvEmail.setText(email);
        tvDescripcion.setText(descripcionContacto);

        btnEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editaDatos();
            }
        });
    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){

        if(keyCode==KeyEvent.KEYCODE_BACK){
            editaDatos();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void editaDatos(){

        Intent intent = new Intent(ConfirmacionDatos.this, MainActivity.class);
        intent.putExtra(getResources().getString(R.string.pNombre), tvNombreCompleto.getText().toString().trim());
        intent.putExtra(getResources().getString(R.string.pFechaNacimiento), tvFechaNacimiento.getText().toString());
        intent.putExtra(getResources().getString(R.string.pTelefono), tvTelefono.getText().toString().trim());
        intent.putExtra(getResources().getString(R.string.pEmail), tvEmail.getText().toString().trim());
        intent.putExtra(getResources().getString(R.string.pDescripcion), tvDescripcion.getText().toString().trim());
        startActivity(intent);
        finish();

    }

    public void llamar(View v) {
        String telefono = tvTelefono.getText().toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono)));
    }

    public void enviarMail(View v){
        String email = tvEmail.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setData(Uri.parse("mailto:"+email));
        //emailIntent.setData(Uri.parse("cc:"+"nirvko@hotmail.com"));
        //emailIntent.putExtra(Intent.EXTRA_EMAIL,email);
        emailIntent.putExtra(Intent.EXTRA_CC, "nirvko@hotmail.com");
        //emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent,"Email"));
    }
}
