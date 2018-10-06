package com.example.schirmer.myapplication;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        final TextView text = (TextView) findViewById(R.id.textView);

        TimeInterpolator timeInterpolator = new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return 0;
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              text.setText("Jetzt geht es so richtig los");
              //text.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));

                ObjectAnimator anim = ObjectAnimator.ofFloat(text, "rotation", 0f, 90f);
                anim.setDuration(2000);
                anim.start();


            }
        });
    }
}
