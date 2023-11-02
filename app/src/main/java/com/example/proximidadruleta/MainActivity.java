package com.example.proximidadruleta;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    ConstraintLayout cl;
    TextView texto;
    SensorManager sm;
    Sensor sensor;
    ImageView ruleta;
    ImageView flecha;
    Random random;
    int angulo;
    boolean restart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ruleta = findViewById(R.id.Ruleta);
        flecha = findViewById(R.id.Flecha);
        texto = findViewById(R.id.txt_texto);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        random = new Random();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String texto1 = String.valueOf(event.values[0]);
        texto.setText(texto1);
        float valor = Float.parseFloat(texto1);
        if (valor == 0) {
            angulo = random.nextInt(3600) + 360;
            RotateAnimation rotate = new RotateAnimation(0, angulo,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotate.setFillAfter(true);
            rotate.setDuration(5000);
            rotate.setInterpolator(new AccelerateDecelerateInterpolator());
            ruleta.startAnimation(rotate);
            restart = false;
        } else {
            texto.setText("Pasa tu mano para Jugar");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
}
