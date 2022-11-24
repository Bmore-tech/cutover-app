package com.gmodelo.cutoverback.Services;

import android.app.Activity;
import android.content.Context;

import com.gmodelo.cutoverback.StoredBeans.LoginStored;
import com.gmodelo.cutoverback.Views.DownloadViewModel;
import com.google.gson.Gson;

public class DownloadService {

    Gson gson;
    Context context;
    DownloadViewModel downloadViewModel;
    LoginStored storedLogin;
    Activity activity;

    public DownloadService(Gson gson, Context context, DownloadViewModel downloadViewModel, LoginStored storedLogin, Activity activity) {
        this.gson = gson;
        this.context = context;
        this.downloadViewModel = downloadViewModel;
        this.storedLogin = storedLogin;
        this.activity = activity;
    }


}


