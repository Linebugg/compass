package annelinegjersem.compass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void Compass_button(View view) {
        Intent intent = new Intent(this, compass.class);
        startActivity(intent);
    }

    public void Accelerometer_button (View view) {
        Intent intent = new Intent(this, acc.class);
        startActivity(intent);
        }
    }
