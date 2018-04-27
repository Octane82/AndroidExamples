package com.everlapp.androidexamples.concurrency;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class AsyncTaskExampleActivity extends AppCompatActivity {

    private AsyncTaskExample asyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asyncTask = new AsyncTaskExample();
        asyncTask.execute();
    }


    @Override
    protected void onStop() {
        super.onStop();
        asyncTask.cancel(true);
    }

    private static class AsyncTaskExample extends AsyncTask<Void, Integer, Void> {

        int progress_status;

        // Выполняется перед doInBackground имеет доступ к UI
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Будет выполнен в новом потоке, здесь решаем все тяжелые задачи. Не имеет доступа к UI
        @Override
        protected Void doInBackground(Void... voids) {
            if (isCancelled()) {
                // return result
            }

            return null;
        }

        // вызывается в потоке пользовательского интерфейса. Этот метод используется дляотображения любых форм прогресса в пользовательском интерфейсе, пока идут вычисления в фоновом режиме
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        // выполняется после doInBackground (несрабатываетвслучае, если AsyncTask былотменен), имеетдоступк UI
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}
