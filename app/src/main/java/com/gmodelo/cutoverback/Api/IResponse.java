package com.gmodelo.cutoverback.Api;

import com.gmodelo.cutoverback.CustomObjects.CommunicationObjects;
import com.gmodelo.cutoverback.CustomObjects.Request;
import com.gmodelo.cutoverback.beans.ResponseAllData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IResponse {

    @POST(CommunicationObjects.DOWNLOADALLMOBILEDATA)
    Call<ResponseAllData> getAllData(@Body Request request);
}
