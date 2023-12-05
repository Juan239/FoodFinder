package com.example.foodfinder_02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.foodfinder_02.clases.rHorario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class establecerHorarioActivity extends AppCompatActivity {
    private Button btnGuardarHorario;
    private TextView btnInicio, btnFinal, txtHoraInicio, txtHoraFinal;
    private String horaInicio, horaFinal;
    private CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    private boolean estadoLunes, estadoMartes, estadoMiercoles, estadoJueves, estadoViernes, estadoSabado, estadoDomingo;
    private FirebaseDatabase database;
    private DatabaseReference referenceHorario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecer_horario);

        btnInicio = findViewById(R.id.btnHoraInicio);
        btnFinal = findViewById(R.id.btnHoraFinal);
        btnGuardarHorario = findViewById(R.id.btnGuardarHorario);
        txtHoraInicio = findViewById(R.id.txtHoraInicio);
        txtHoraFinal = findViewById(R.id.txtHoraFinal);
        lunes = findViewById(R.id.checkboxLunes);
        martes = findViewById(R.id.checkboxMartes);
        miercoles = findViewById(R.id.checkboxMiercoles);
        jueves = findViewById(R.id.checkboxJueves);
        viernes = findViewById(R.id.checkboxViernes);
        sabado = findViewById(R.id.checkboxSabado);
        domingo = findViewById(R.id.checkboxDomingo);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarHoraInicio();
            }
        });
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarHoraFinal();
            }
        });
        btnGuardarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoLunes = lunes.isChecked();
                estadoMartes = martes.isChecked();
                estadoMiercoles = miercoles.isChecked();
                estadoJueves = jueves.isChecked();
                estadoViernes = viernes.isChecked();
                estadoSabado = sabado.isChecked();
                estadoDomingo = domingo.isChecked();

                rHorario horario = new rHorario(estadoLunes, estadoMartes, estadoMiercoles, estadoJueves, estadoViernes, estadoSabado, estadoDomingo, horaInicio, horaFinal);
                database = FirebaseDatabase.getInstance();
                referenceHorario = database.getReference().child("horarios");
                referenceHorario.child(currentUser.getUid()).setValue(horario);
                Toast.makeText(establecerHorarioActivity.this, "Horario establecido con exito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //----------------------------------------------Seleccion de horas----------------------------------------------------------------
    public void seleccionarHoraInicio() {
        // Obtener la hora actual
        final Calendar c = Calendar.getInstance();
        int horaActual = c.get(Calendar.HOUR_OF_DAY);
        int minutoActual = c.get(Calendar.MINUTE);

        // Crear un cuadro de diálogo de selección de hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Procesar la hora seleccionada
                        horaInicio= formatearNumeroConCero(hourOfDay) + ":" + formatearNumeroConCero(minute);
                        txtHoraInicio.setText(horaInicio);
                        Toast.makeText(establecerHorarioActivity.this, "Hora seleccionada: " + horaInicio, Toast.LENGTH_SHORT).show();
                    }
                }, horaActual, minutoActual, false);

        // Mostrar el cuadro de diálogo
        timePickerDialog.show();
    }
    public void seleccionarHoraFinal() {
        // Obtener la hora actual
        final Calendar c = Calendar.getInstance();
        int horaActual = c.get(Calendar.HOUR_OF_DAY);
        int minutoActual = c.get(Calendar.MINUTE);

        // Crear un cuadro de diálogo de selección de hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Procesar la hora seleccionada
                        horaFinal= formatearNumeroConCero(hourOfDay) + ":" + formatearNumeroConCero(minute);
                        txtHoraFinal.setText(horaFinal);
                        Toast.makeText(establecerHorarioActivity.this, "Hora seleccionada: " + horaFinal, Toast.LENGTH_SHORT).show();
                    }
                }, horaActual, minutoActual, false);

        // Mostrar el cuadro de diálogo
        timePickerDialog.show();
    }
    private String formatearNumeroConCero(int numero) {
        // Utilizar String.format para agregar un cero delante si el número es menor que 10
        return String.format("%02d", numero);
    }

}