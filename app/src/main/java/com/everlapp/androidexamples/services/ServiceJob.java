package com.everlapp.androidexamples.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ServiceJob extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        // Вызывается при начале выполнения задачи
        // !!! Выполняется на MainThread !!!
        // если выполнение задачи завершено до вызова return
        // вернуть false, иначе true

        // Можно запустить еще какой то сервис
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Вызывается в случае остановки выполнения задачи
        // например, после вызова cancel или при изменении условий
        // если задачу необходимо перезапланиповать, вернуть true
        return false;
    }
}
