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


public class compass extends AppCompatActivity implements SensorEventListener {
private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor aSensor;
    private ImageView img;

    private float[] mlastacc= new float[3];
    private float []mlastmag=new float[3];
    private float [] mr = new float[9];
    private float[] mOrientation= new float[3];
    private boolean lastAcc = false;
    private boolean lastmag = false;
    private float currentDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        aSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        img= (ImageView) findViewById(R.id.img_back);


     }


    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, aSensor ,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
    }
  protected void OnPause(){
        super.onPause();
        mSensorManager.unregisterListener(this,aSensor);
        mSensorManager.unregisterListener(this,mSensor);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == aSensor) {
            System.arraycopy(event.values, 0, mlastacc, 0, event.values.length);
            lastAcc = true;
        } else if (event.sensor == mSensor) {
            System.arraycopy(event.values, 0, mlastmag, 0, event.values.length);
            lastmag = true;
        }
        if (lastAcc && lastmag) {
            SensorManager.getRotationMatrix(mr, null, mlastacc, mlastmag);
            SensorManager.getOrientation(mr, mOrientation);
            float azi = mOrientation[0];
            float aziDegree = (float) (Math.toDegrees(azi) + 360) % 360;

            RotateAnimation ra = new RotateAnimation(currentDegree,-aziDegree,Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            ra.setDuration(250);
            ra.setFillAfter(true);
            img.startAnimation(ra);
            currentDegree= - aziDegree;

        }
    }

         @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

   //// TODO: Auto-generated method sub
        }
    }
