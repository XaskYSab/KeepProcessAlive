package com.xaskysab.keep_scheduler;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static android.app.job.JobInfo.NETWORK_TYPE_ANY;

@SuppressLint("NewApi")
public class JobSchedulerService extends JobService {

    private Integer jobId = 0x12321;
    private Map<Integer, String> jobservie = new HashMap<>();

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.e("MyLog","onStartJob");

        Integer jobId = params.getJobId();

        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        Optional<String> optional = activityManager.getRunningServices(Integer.MAX_VALUE).stream()
//                .map((x) -> x.service.getClassName().toString())
//                .filter(x -> x.equals(jobservie.get(jobId))).findFirst();
//        if (!optional.isPresent()) {
//            System.out.println("begin recover needkeepservice!");
//            Log.e("MyLog", "begin recover needkeepservice!22");
//            startService(new Intent(this, NeedKeepService.class));
//
//        }

        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(128)) {
            if (serviceInfo.service.getClassName().equals(jobservie.get(jobId))) {
                return true;
            }
        }


        Log.e("MyLog", "begin recover needkeepservice!");
        startService(new Intent(this, NeedKeepService.class));


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        schedulejob(getJob(jobId));
        return true;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        jobservie.put(jobId, NeedKeepService.class.getCanonicalName());

        schedulejob(getJob(jobId));


        Log.e("MyLog", "onStartCommand");

        return START_NOT_STICKY;
    }

    @SuppressLint("NewApi")
    public void schedulejob(JobInfo job) {


        JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(job);

    }

    @SuppressLint("NewApi")
    public JobInfo getJob(Integer jobId) {

        JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(this, JobSchedulerService.class));
        builder.setPersisted(true).setPeriodic(100).setRequiredNetworkType(NETWORK_TYPE_ANY)
                .setRequiresCharging(false).setRequiresDeviceIdle(false);

        return builder.build();
    }

}
