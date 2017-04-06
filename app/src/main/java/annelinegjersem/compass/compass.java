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
import android.os.Vibrator;


public class compass extends AppCompatActivity implements SensorEventListener {
private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor aSensor;
    private ImageView img;
    private TextView tex;
    private Vibrator vibrator;
    private float[] mlastacc= new float[3];
    private float []mlastmag=new float[3];
    private float [] ac = new float[9];
    private float [] ma = new float[9];
    private float[] mOrientation= new float[3];
    private float currentDegree = 0f;
    private float aziDegree = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // create a sensormanager
        aSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// sensor for accelerometer
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);// sensor for magnetic field
        img= (ImageView) findViewById(R.id.img_back);// imageview
        tex = (TextView)findViewById(R.id.TD);// textview
        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);// vibrator

     }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, aSensor ,SensorManager.SENSOR_DELAY_UI);// register sensor
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
    }
  protected void OnPause(){
        super.onPause();
        mSensorManager.unregisterListener(this,aSensor); // unregister sensor
        mSensorManager.unregisterListener(this,mSensor);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float ALPHA = 0.97f;
        synchronized (this) {
            if (event.sensor == aSensor) { // if sensor is accelerometer
                mlastacc[0] = ALPHA * mlastacc[0] + (1 - ALPHA) * event.values[0];// low pass filter
                mlastacc[1] = ALPHA * mlastacc[1] + (1 - ALPHA) * event.values[1];
                mlastacc[2] = ALPHA * mlastacc[2] + (1 - ALPHA) * event.values[2];

                }
            if (event.sensor == mSensor) {// if sensor is type of magneticfield
                mlastmag[0] = ALPHA * mlastmag[0] + (1 - ALPHA) * event.values[0];// low pass filter
                mlastmag[1] = ALPHA * mlastmag[1] + (1 - ALPHA) * event.values[1];
                mlastmag[2] = ALPHA * mlastmag[2] + (1 - ALPHA) * event.values[2];

                }

            boolean sucess = SensorManager.getRotationMatrix(ac, ma, mlastacc, mlastmag);
            if (sucess) {
                SensorManager.getOrientation(ac, mOrientation);
                float azi = mOrientation[0];
                float aziDegree = (float) (Math.toDegrees(azi) + 360) % 360;
                RotateAnimation ra = new RotateAnimation(currentDegree, -aziDegree, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ra.setDuration(500);
                ra.setFillAfter(true);
                img.startAnimation(ra);
                tex.setText("Current degree: " + Integer.toString((int) -currentDegree));
                currentDegree = -aziDegree;
                if ((int) currentDegree == 0) {
                         vibrator.vibrate(250);
                         vibrator.cancel();

                }
            }
        }
    }
         @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){
   //// TODO: Auto-generated method sub
        }
    }
