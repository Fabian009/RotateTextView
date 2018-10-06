package com.example.schirmer.asynchrontask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Button button = (Button)findViewById(R.id.button);
        final TextView text = (TextView)findViewById(R.id.textView2);


        final TaskHandling[] task = {null};
        final int[] count = {0};

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (task[0] == null)
                {
                    task[0] = new TaskHandling(progressBar, text);
                    text.setText("Thread gestartet");
                    task[0].execute();
                }
                else
                {
                    if (count[0] ==0)
                    {
                        Toast.makeText(getApplicationContext(), "Thread wurde bereits gestartet, " +
                                        "nochmaliges Klicken beendet den Vorgang",
                                Toast.LENGTH_LONG).show();
                        count[0]++;
                    }else{
                        if (count[0]==1)
                        {
                            count[0]=0;
                            task[0].cancel(true);
                            Toast.makeText(getApplicationContext(), "Beendet", Toast.LENGTH_SHORT).show();
                            progressBar.setProgress(0);
                            task[0]=null;
                        }


                    }



                }

            }
        });

    }
}
