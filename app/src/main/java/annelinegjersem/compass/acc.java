package annelinegjersem.compass;

import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.Context;
import android.widget.TextView;
import android.hardware.SensorEvent;



public class acc extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor maccel;
    TextView acceleration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        maccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, maccel ,SensorManager.SENSOR_DELAY_NORMAL);
        acceleration= (TextView) findViewById(R.id.acceleration);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acceleration.setText(" X:"+ event.values[0]+
                    "\n \n Y:" +  event.values[1]+
                    "\n \n Z:" +event.values[2]);

        }

    }

}


