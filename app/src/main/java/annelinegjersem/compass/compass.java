package annelinegjersem.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class compass extends AppCompatActivity implements SensorEventListener {
private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor aSensor;
    private ImageView img;
    private TextView text;
    private float[] mGravity;
    private float[] mGeomagnetic;
    private float am = 0f;
    private float current_am = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        aSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, aSensor ,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        img= (ImageView) findViewById(R.id.img_back);
        text = (TextView)findViewById(R.id.tv_id);
        mGravity  = new float[3];
        mGeomagnetic=new float[3];


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;

        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1];

                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2];

            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

            }

            float[] R = new float[9];
            float[] I = new float[9];
            boolean sucess = SensorManager.getRotationMatrix(R, I, mGeomagnetic, mGravity);

            if (sucess) {
                float[] orientation = new float[3];
                mSensorManager.getOrientation(R, orientation);
                am = (float) Math.toDegrees(orientation[0]);
                am =(am+360)%360;
                Animation animation = new RotateAnimation(current_am,-am,Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0.5f);
              current_am=am;
               animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setFillAfter(true);
               img.startAnimation(animation);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
