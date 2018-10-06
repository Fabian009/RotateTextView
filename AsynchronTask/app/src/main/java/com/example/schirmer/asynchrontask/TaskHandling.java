package com.example.schirmer.asynchrontask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TaskHandling extends AsyncTask<Void, Integer, String> {

    private WeakReference<ProgressBar> progress;
    private WeakReference<TextView> textView;

    TaskHandling(ProgressBar progressBar, TextView text)
    {
        progress = new WeakReference<>(progressBar);
        textView = new WeakReference<>(text);
    }

    private int count = 0;


    @Override
    protected String doInBackground(Void ...voids) {

                while (count != 100)
                {
                    publishProgress(count);
                    count++;
                    if (isCancelled())
                    {
                        count=100;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                publishProgress(count);


         return "finished";

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        if (values[0] < 100)
        {
            textView.get().setText("Aktueller Stand: " + values[0] + " %");
            progress.get().setProgress(values[0]);
        }
        else
        {
            textView.get().setText("Berechnung abgeschlossen");
            progress.get().setProgress(100);
        }


    }

    @Override
    protected void onCancelled() {
        textView.get().setText("Task cancelled");
    }


    @Override
    protected void onPostExecute(String result) {

    }



}
