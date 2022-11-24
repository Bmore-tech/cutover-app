package com.gmodelo.cutoverback.Api;

import com.gmodelo.cutoverback.CustomObjects.CommunicationObjects;
import com.gmodelo.cutoverback.CustomObjects.LoginBean;
import com.gmodelo.cutoverback.beans.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ResponseILogin {

    @POST(CommunicationObjects.LOGINMODULE)
    Call<ResponseLogin> login(@Body LoginBean bean);
}
