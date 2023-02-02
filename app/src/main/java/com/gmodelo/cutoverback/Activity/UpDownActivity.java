package com.gmodelo.cutoverback.Activity;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.COUNTMAPPER;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.LASTFETCHED;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.LOGMAPPER;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDLOGIN;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TASKCOMPLETED;
import static com.gmodelo.cutoverback.CustomObjects.ResponseVariability.CUSTOMUPDATEDAYS;
import static com.gmodelo.cutoverback.CustomObjects.ResponseVariability.INITIAL_OFFSET_VAL;
import static com.gmodelo.cutoverback.CustomObjects.ResponseVariability.INV_E_MATERIALS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.Api.IResponse;
import com.gmodelo.cutoverback.Api.ResponseILogin;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.CommunicationObjects;
import com.gmodelo.cutoverback.CustomObjects.GlobalConstants;
import com.gmodelo.cutoverback.CustomObjects.LoginBean;
import com.gmodelo.cutoverback.CustomObjects.Request;
import com.gmodelo.cutoverback.CustomObjects.Response;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DataAccess.InstanceOfDB;
import com.gmodelo.cutoverback.Instances.RetrofitClient;
import com.gmodelo.cutoverback.Services.DownloadService;
import com.gmodelo.cutoverback.StoredBeans.CountBean;
import com.gmodelo.cutoverback.StoredBeans.LoginStored;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.Views.DownloadViewModel;
import com.gmodelo.cutoverback.Views.RouteViewModel;
import com.gmodelo.cutoverback.beans.AsyncDbProg;
import com.gmodelo.cutoverback.beans.DownloadDataBean;
import com.gmodelo.cutoverback.beans.MaterialTarimasBean;
import com.gmodelo.cutoverback.beans.MobileDataFetch;
import com.gmodelo.cutoverback.beans.ResponseAllData;
import com.gmodelo.cutoverback.beans.ResponseCount;
import com.gmodelo.cutoverback.beans.ResponseLogin;
import com.gmodelo.cutoverback.beans.ResponseMobileData;
import com.gmodelo.cutoverback.beans.ResponseRoute;
import com.gmodelo.cutoverback.beans.RouteUserBean;
import com.gmodelo.cutoverback.beans.TaskCompleted;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import at.markushi.ui.CircleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UpDownActivity extends AppCompatActivity {

    View decorView;
    Gson gson;
    Context context;
    Activity activity;
    InstanceOfDB dbInstance;
    DownloadViewModel downloadViewModel;
    RouteViewModel routeModel;
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    DownloadService downloadService;
    ImageButton settingUpDown;
    LoginStored storedLogin;
    TaskCompleted isCompleted;
    CircleButton download, upload, autoTask, cancelSession;
    CardView requestTask, downloadTask, uploadTask, closeTask;
    Runnable serverRunnable;
    LoginBean loginObject;
    Handler mHandler = new Handler();
    TextView proccessMessage;
    Integer rowCount = 0;
    Integer retryVal = 0;
    HashMap<String, Integer> countMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        context = UpDownActivity.this;
        activity = UpDownActivity.this;
        gson = new Gson();
        download = findViewById(R.id.downloadButton);
        download.setEnabled(false);
        download.setClickable(false);
        upload = findViewById(R.id.uploadButton);
        upload.setEnabled(false);
        upload.setClickable(false);
        autoTask = findViewById(R.id.autoEvent);
        autoTask.setEnabled(false);
        autoTask.setClickable(false);
        cancelSession = findViewById(R.id.cancelSessionBtn);
        cancelSession.setEnabled(false);
        cancelSession.setClickable(false);
        proccessMessage = findViewById(R.id.loadingDownloadValue);
        progressBarHolder = findViewById(R.id.upDownFrame);
        requestTask = findViewById(R.id.solicitarCard);
        downloadTask = findViewById(R.id.descargarCard);
        uploadTask = findViewById(R.id.cargarCard);
        closeTask = findViewById(R.id.cerrarSesionCard);
        settingUpDown = findViewById(R.id.settingUpDown);
        recoverStoredData();

        dbInstance = InstanceOfDB.getInstanceOfDB(context);
        downloadViewModel = ViewModelProviders.of(this).get(DownloadViewModel.class);
        routeModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        downloadService = new DownloadService(gson, context, downloadViewModel, storedLogin, activity);

        settingUpDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(serverRunnable, 3500);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(serverRunnable);
                        break;

                }
                return true;
            }
        });

        serverRunnable = new Runnable() {
            @Override
            public void run() {
                new CommonUtilities().onPasswordRequest(activity, new CommonUtilities.CustomCallBack<String>() {
                    @Override
                    public void customCallBack(String ret) {
                        if (!ret.equalsIgnoreCase(ResponseVariability.S_ERROR)) {
                            closeKeyboard();
                            loginObject = new LoginBean();
                            loginObject.setLoginId(storedLogin.getLoginBean().getLoginId());
                            loginObject.setLoginPass(ret);
                            loginObject.setLoginLang("ES");
                            loginObject.setRelationUUID(UUID.randomUUID().toString());
                            initiateLoading();
                            new MyLoginRequest().execute(gson.toJson(loginObject), CommonUtilities.getBaseServer(context) + CommunicationObjects.LOGINMODULE);
                        } else {
                            closeKeyboard();
                        }
                    }
                }, context);

            }
        };

    }

    public void cleanCountMapper() {
        CommonUtilities.PopGsonVariable(COUNTMAPPER, context);
    }


    public void recoverStoredData() {
        String loginString = CommonUtilities.PushGsonVariable(STOREDLOGIN, context);
        Log.e("LoginActivity", "loginString: " + loginString);
        if (loginString != null) {
            storedLogin = gson.fromJson(loginString, LoginStored.class);
            String sTask = CommonUtilities.PushGsonVariable(TASKCOMPLETED, context);
            Log.e("LoginActivity", "sTask: " + sTask);
            if (sTask != null) {
                isCompleted = gson.fromJson(sTask, TaskCompleted.class);
                if (isCompleted.isTaskInitiated() && !isCompleted.isTaskCompleted()) {
                    initiateLoading();
                    startActivity(new Intent(context, BeginCountActivity.class));
                } else {
                    applyRestriction();
                }
            } else {
                uploadTask.setAlpha(.3F);
                uploadTask.setEnabled(false);
                String lastUpdated = CommonUtilities.PushGsonVariable(LASTFETCHED, context);
                if (lastUpdated == null) {
                    requestTask.setAlpha(.3F);
                    requestTask.setEnabled(false);
                }
            }
        } else {
            CommonUtilities.cancelSession(context, activity);
        }
    }

    public void applyRestriction() {
        if (isCompleted.isTaskCompleted()) {
            downloadTask.setAlpha(.3F);
            downloadTask.setEnabled(false);
            requestTask.setAlpha(.3F);
            requestTask.setEnabled(false);
            if (isCompleted.isTaskUploaded()) {
                uploadTask.setEnabled(false);
                uploadTask.setAlpha(.3F);
            }
        } else if (!isCompleted.isTaskInitiated()) {
            uploadTask.setAlpha(.3F);
            uploadTask.setEnabled(false);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); commented this line in order to disable back press
        //cancelLoading();
    }

    public void cancelLoading() {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void initiateLoading() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void executeAutoTask(View view) {
        if (CommonUtilities.hasNetwork(context)) {
            String getLastUpdated = CommonUtilities.PushGsonVariable(LASTFETCHED, context);
            MobileDataFetch dataFetch = gson.fromJson(getLastUpdated, MobileDataFetch.class);
            if (getLastUpdated != null && TimeUnit.DAYS.convert((new Date().getTime() - dataFetch.getLastMobileDataFetch()), TimeUnit.MILLISECONDS) < CUSTOMUPDATEDAYS) {

                proccessMessage.setText("...");
                initiateLoading();
                Request request = new Request();
                request.setTokenObject(CommonUtilities.getStructureToSend(context));

                new GetRouteAutoData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.AUTOTASKMODULE);
            } else {
                CommonUtilities.CustomConfirmDialog("Advertencia!", "La actualizacion de datos maestros\n no se ha realizado en mas de 7 días.\n¿Desea Continuar?",
                        activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                if (ret == ResponseVariability.SUCCESSFULL) {
                                    proccessMessage.setText("...");
                                    initiateLoading();
                                    Request request = new Request();
                                    request.setTokenObject(CommonUtilities.getStructureToSend(context));
                                    new GetRouteAutoData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.AUTOTASKMODULE);
                                }
                            }
                        }, R.drawable.ic_exclamation_white_48dp, context);
            }
        } else {
            CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "No dispone de conectividad a internet...\nFavor de validar su conectividad!", activity, null, null
                    , null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                        }
                    }, R.drawable.ic_warning_holo_dark, context);
        }
    }

    public void executeUploadTask(View view) {
        if (CommonUtilities.hasNetwork(context)) {
            initiateLoading();
            Request<RouteUserBean> request = new Request<>();
            request.setTokenObject(CommonUtilities.getStructureToSend(context));
            RouteUserBean routeUserBean = gson.fromJson(CommonUtilities.PushGsonVariable(CommunicationObjects.STOREDROUTE, context), RouteUserBean.class);
            request.setLsObject(routeUserBean);
            Log.e("executeUploadTask", request.toString());
            new UploadFinalRoute().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.UPLOADROUTEMODULE);
        } else {
            CommonUtilities.CustomColorWarningDialog("ADVERTENCIA!", "No dispone de conectividad a internet...\nFavor de validar su conectividad!", activity, null, null
                    , null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                        }
                    }, null, context, "#41A802");
        }
    }

    /**
     * Método principal para la ejecución de la descarga de Datos Maestros.
     * @param view Vista origen.
     */
    public void executeFetch(View view) {
        // Valída si hay conexión a Internet.
        if (CommonUtilities.hasNetwork(context)) {
            CommonUtilities.CustomConfirmDialog("Advertencia!", "La actualización de datos maestros\npodria tardar.\n¿Desea Continuar?",
                    activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            if (ret == ResponseVariability.SUCCESSFULL) {
                                initiateLoading();
                                CommonUtilities.PopGsonVariable(LASTFETCHED, context);
                                routeModel.deleteAdminMode();
                                cleanCountMapper();
                                rowCount = 0;
                                retryVal = 0;
                                proccessMessage.setText("...");
                                new FetchMobileData().execute();
                            }
                        }
                    }, R.drawable.ic_exclamation_white_48dp, context);


        } else {
            CommonUtilities.CustomColorWarningDialog("ADVERTENCIA!", "No dispone de conectividad a internet...\nFavor de validar su conectividad!", activity, null, null
                    , null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                        }
                    }, null, context, "#FF0000");
        }
    }

    public void executeFectchRetry() {
        initiateLoading();
        cleanCountMapper();
        rowCount = 0;
        proccessMessage.setText("...");
        new FetchMobileData().execute();
    }


    public void cancelSessionUpDown(View view) {
        if (isCompleted != null && !isCompleted.isTaskUploaded()) {
            CommonUtilities.CustomConfirmDialog("Advertencia!", "Al realizar esta accion perdera la tarea guardada. \n¿Desea Continuar?",
                    activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            if (ret == ResponseVariability.SUCCESSFULL) {
                                CommonUtilities.CustomConfirmDialog("Advertencia!", "Esta accion cancelara la sesion actual. \n¿Desea Continuar?",
                                        activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                            @Override
                                            public void customCallBack(Integer ret) {
                                                if (ret == ResponseVariability.SUCCESSFULL) {
                                                    initiateLoading();
                                                    CommonUtilities.loggerAPI(new Exception("No se completo el flujo y se cerro la sesion previo a enviar la tarea:\n" +
                                                            "Por el usuario: " + storedLogin.getLoginBean().getLoginId() + "\n" +
                                                            storedLogin.getLoginBean().getLsObjectLB().getGenInf().getName() + " " +
                                                            storedLogin.getLoginBean().getLsObjectLB().getGenInf().getLastName() + "\n" +
                                                            CommonUtilities.PushGsonVariable(CommunicationObjects.STOREDROUTE, context)), context);
                                                    CommonUtilities.cancelSession(context, activity);
                                                }
                                            }
                                        }, R.drawable.ic_exclamation_white_48dp, context);
                            }
                        }
                    }, R.drawable.ic_exclamation_white_48dp, context);
        } else {
            CommonUtilities.CustomConfirmDialog("Advertencia!", "Esta accion cancelara la sesion actual, se perderá el progeso del conteo actual. \n ¿Desea Continuar?",
                    activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            if (ret == ResponseVariability.SUCCESSFULL) {
                                initiateLoading();
                                CommonUtilities.cancelSession(context, activity);
                            }
                        }
                    }, R.drawable.ic_exclamation_white_48dp, context);
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class FetchMobileData extends AsyncTask<Void, Void, AbstractResults> {

        @Override
        protected AbstractResults doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                Thread.currentThread().interrupt();
            }
            return null;
        }

        @Override
        protected void onPostExecute(AbstractResults abstractResults) {
            getCountData();
        }
    }

    public AbstractResults getCountData() {
        AbstractResults result = new AbstractResults();
        Request request = new Request();
        request.setTokenObject(CommonUtilities.getStructureToSend(context));
        try {
            proccessMessage.setText("Ejecutando Peticion al Servidor");
            new AsyncGetMobileCountData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.GETCOUNTEDVALUES);
            //doExtractData(request);

        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            cancelLoading();
            Date date = new Date();
            result.setResultId(ResponseVariability.EXCEPTION);
            result.setResultMsgAbs("Ocurrio un Error al tratar de descargar los Datos Maestros al dispositivo" +
                    "\n  Log de Error: " + date.getTime() + " Contacte Administrador del sistema");
        }
        return result;

    }

    public AbstractResults getMobileData() {
        AbstractResults result = new AbstractResults();
        Request<HashMap<String, String>> request = new Request<>();
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(ResponseVariability.ROW_COUNT, String.valueOf(rowCount));
        requestMap.put(ResponseVariability.INITIAL_OFFSET, ResponseVariability.INITIAL_OFFSET_VAL);
        request.setLsObject(requestMap);
        request.setTokenObject(CommonUtilities.getStructureToSend(context));
        try {
            proccessMessage.setText("Ejecutando Peticion al Servidor");
            new AsyncGetMobileData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.DOWNLOADMOBILEDATA);
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            cancelLoading();
            result = new AbstractResults();
            Date date = new Date();
            result.setResultId(ResponseVariability.EXCEPTION);
            result.setResultMsgAbs("Ocurrio un Error al tratar de descargar los Datos Maestros al dispositivo" +
                    "\n  Log de Error: " + date.getTime() + " Contacte Administrador del sistema");
        }
        return result;
    }


    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            onWindowFocusChanged(true);
        }
    }

    public void updateMobileData() {
        AbstractResults result = new AbstractResults();
        Request request = new Request();
        request.setTokenObject(CommonUtilities.getStructureToSend(context));
        String lastRequest = CommonUtilities.PushGsonVariable(LASTFETCHED, context);
        MobileDataFetch mobileFetch = null;
        if (lastRequest != null) {
            mobileFetch = gson.fromJson(lastRequest, MobileDataFetch.class);
        } else {
            mobileFetch = new MobileDataFetch();
            mobileFetch.setLastMobileDataFetch(new Date().getTime());
        }
        request.setLsObject(mobileFetch.getLastMobileDataFetch());
        proccessMessage.setText("Ejecutando Peticion al Servidor");
        new AsyncUpdateMobileData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.UPDATEMOBILEDATA);
    }

    public void getOffAssetMaterials() {
        //count materiales del cel  < consola  notificar y empezar de vuelta.
        AbstractResults result = new AbstractResults();
        Request request = new Request();
        request.setTokenObject(CommonUtilities.getStructureToSend(context));
        proccessMessage.setText("Ejecutando Peticion al Servidor");
        new AsyncGetExtraMobileData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.GETEXTRAMOBILEDADA);
    }


    public AbstractResults downloadRoute() {
        Request request = new Request();
        request.setTokenObject(CommonUtilities.getStructureToSend(context));
        AbstractResults result = new AbstractResults();
        try {
            String responseString = new GetRouteData().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.DOWNLOADROUTEMODULE).get();
            ResponseRoute response = gson.fromJson(responseString, ResponseRoute.class);
            result = response.getAbstractResult();
            if (result.getResultId() == ResponseVariability.SUCCESSFULL) {

                RouteUserBean user = gson.fromJson(gson.toJson(response.getLsObject()), RouteUserBean.class);
                if (user.getPositions().isEmpty() || user.getPositions().size() == 0) {
                    CommonUtilities.CustomWarningDialog("Advertencia!", "La Ruta obtenida para la tarea:\n" + user.getTaskId() + "\n" +
                            "\nFavor de Contactar Administrador", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            CommonUtilities.cancelPreviewValues(context, null);
                            startActivity(new Intent(activity, UpDownActivity.class));
                        }
                    }, R.drawable.ic_exclamation_white_36dp, context);
                } else {
                    CommonUtilities.UpdateStoreGSonVariable(CommunicationObjects.STOREDROUTE, gson.toJson(user), context);
                    startActivity(new Intent(context, BeginCountActivity.class));
                }
            } else if (result.getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                CommonUtilities.cancelSession(context, activity);
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            result = new AbstractResults();
            Date date = new Date();
            result.setResultId(ResponseVariability.EXCEPTION);
            result.setResultMsgAbs("Ocurrio un Error al tratar de descargar la Ruta para el usuario: " + storedLogin.getLoginBean().getLoginId() +
                    "\n  Log de Error: " + date.getTime() + " Contacte Administrador del sistema");
            Log.e("DownloadRoute:", String.valueOf(date.getTime()), e);
        }
        return result;
    }


    //Retrofit Section

    private void doExtractData(Request request) {
        Retrofit retrofit = RetrofitClient.getClient(CommonUtilities.getBaseServer(context));
        IResponse iResponse = retrofit.create(IResponse.class);
        Call<ResponseAllData> call = iResponse.getAllData(request);
        call.enqueue(new Callback<ResponseAllData>() {
            @Override
            public void onResponse(Call<ResponseAllData> call, retrofit2.Response<ResponseAllData> response) {
                cancelLoading();
                if (response.body() != null) {
                    if (response.body().getAbstractResult() != null) {
                        if (response.body().getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                            Toast.makeText(context, "Data Extracted Matnr: " + response.body().getLsObject().getListMobileMaterial().size(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, response.body().getAbstractResult().getResultMsgAbs(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "[doExtractData] - Ocurrio un error el procesar la solicitud", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "[doExtractData] - Ocurrio un error el procesar la solicitud", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseAllData> call, Throwable t) {
                cancelLoading();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    ///CLASS SECTION

    public interface AbstractResultCallBack {
        void abstractCallBack(AbstractResults results);
    }

    public class GetRouteData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... values) {
            publishProgress("Solicitando Ruta");
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn.isEmpty()) {
                return null;
            } else {
                return toReturn;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }
    }


    public class GetRouteAutoData extends AsyncTask<String, Void, ResponseRoute> {

        @Override
        protected ResponseRoute doInBackground(String... values) {
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn.isEmpty()) {
                return null;
            } else {
                return gson.fromJson(toReturn, ResponseRoute.class);
            }
        }

        @Override
        protected void onPostExecute(ResponseRoute response) {
            cancelLoading();
            try {
                AbstractResults result = result = response.getAbstractResult();
                if (result.getResultId() == ResponseVariability.SUCCESSFULL) {
                    Log.i("Ruta Obtenida:", response.toString());
                    Log.i("Ruta Obtenida:", response.getLsObject().toString());
                    CommonUtilities.UpdateStoreGSonVariable(CommunicationObjects.STOREDROUTE, gson.toJson(response.getLsObject()), context);
                    startActivity(new Intent(context, BeginCountActivity.class));
                } else if (result.getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                    CommonUtilities.cancelSession(context, activity);
                } else {
                    CommonUtilities.CustomWarningDialog("ADVERTENCIA!", result.getResultMsgAbs(), activity, null, null
                            , null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    //CommonUtilities.cancelData(context);
                                }
                            }, null, context);
                    //Toast.makeText(context, result.getResultMsgAbs(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "Ocurrió un error al solicitar la tarea", activity, null, null
                        , null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                //CommonUtilities.cancelData(context);
                            }
                        }, null, context);
                //Toast.makeText(context, "GetRouteAutoData: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private class UploadFinalRoute extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... values) {
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn.isEmpty()) {
                return null;
            } else {
                return gson.fromJson(toReturn, Response.class);
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            cancelLoading();
            if (response != null) {
                if (response.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                    RouteUserBean routeUserBean = gson.fromJson(CommonUtilities.PushGsonVariable(CommunicationObjects.STOREDROUTE, context), RouteUserBean.class);
                    Log.e("logger", routeUserBean.toString());

                    CommonUtilities.CustomColorWarningDialog("EXITO", "Tarea " + routeUserBean.getTaskId() + " \n cargada de forma exitosa. \n \n La sesión se cerrará",
                            activity, null, "Continuar", null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    CommonUtilities.cancelSession(context, activity);
                                }
                            }
                            , R.drawable.ic_exclamation_white_36dp, context, "#41A802");

                } else if (response.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL_RECOUNT) {
                    RouteUserBean routeUserBean = gson.fromJson(CommonUtilities.PushGsonVariable(CommunicationObjects.STOREDROUTE, context), RouteUserBean.class);
                    Log.e("logger", routeUserBean.toString());

                    CommonUtilities.CustomColorWarningDialog("EXITO, RECONTEO", "Tarea " + routeUserBean.getTaskId() + " \n cargada de forma exitosa. \n Diferencias encontradas. \n La sesión se cerrará. \n Tienes una tarea nueva: \n RECONTAR diferencias.",
                            activity, null, "Continuar", null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    CommonUtilities.cancelSession(context, activity);
                                }
                            }
                            , R.drawable.ic_exclamation_white_36dp, context, "#41A802");

                } else if (response.getAbstractResult().getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                    startActivity(new Intent(activity, LoginActivity.class));
                } else if (response.getAbstractResult().getResultId() == ResponseVariability.ITASKCLOSED) {
                    // Enviamos el log al server
                    RouteUserBean routeUserBean = gson.fromJson(CommonUtilities.PushGsonVariable(CommunicationObjects.STOREDROUTE, context), RouteUserBean.class);
                    Log.e("prueba", routeUserBean.toString());
                    //new UpdateApplicationLogs().execute(gson.toJson(routeUserBean), CommonUtilities.getBaseServer(context) + CommunicationObjects.UPDATELOGSTOSERVER);

                    CommonUtilities.CustomColorWarningDialog("¡ERROR!", "Conteo no válido \n al continuar el conteo NO SERÁ envíado.\n El error será enviado al servidor.",
                            activity, null, "Continuar", null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {

                                    // Segúndo mensaje de adevertencia.
                                    /*CommonUtilities.CustomColorWarningDialog("¡ERROR!", "Envíando error al servidor",
                                            activity, null, "Continuar", null, new CommonUtilities.CustomCallBack<Integer>() {
                                                @Override
                                                public void customCallBack(Integer ret) {
                                                    CommonUtilities.cancelSession(context, activity);
*/
                                                    // Nuevo código encapsulado
                                                    cancelSession.setEnabled(true);
                                                    upload.setEnabled(false);
                                                    isCompleted.setTaskUploaded(true);
                                                    isCompleted.setTaskInitiated(false);
                                                    CommonUtilities.UpdateStoreGSonVariable(TASKCOMPLETED, gson.toJson(isCompleted), context);
                                                    CommonUtilities.cancelSession(context, activity);
                                                    startActivity(new Intent(context, LoginActivity.class));

/*
                                                    // Imprimimos al LOG del teléfono
                                                    CommonUtilities.loggerAPI(new Exception("La tarea fue previamente concluida por otro usuario." +
                                                            "Usuario que intento cargar la tarea: " + storedLogin.getLoginBean().getLoginId() + "\n" +
                                                            storedLogin.getLoginBean().getLsObjectLB().getGenInf().getName() + " " +
                                                            storedLogin.getLoginBean().getLsObjectLB().getGenInf().getLastName() + "\n" +
                                                            CommonUtilities.PushGsonVariable(CommunicationObjects.STOREDROUTE, context)), context);


                                                }
                                            }
                                            , R.drawable.ic_exclamation_white_36dp, context, "#41A802");*/


                                }
                            }
                            , R.drawable.ic_exclamation_white_36dp, context, "#FF0000");


                } else {
                    Toast.makeText(context, response.getAbstractResult().getResultMsgAbs(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Ocurrio un Error al Cargar la Informacion \n Intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Download Mobile Data

    private class AsyncGetMobileData extends AsyncTask<String, String, ResponseMobileData> {

        @Override
        protected ResponseMobileData doInBackground(String... values) {
            publishProgress("Descargando Informacion...");
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);

                publishProgress("Procesando Informacion...");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn != null) {
                try {
                    return gson.fromJson(toReturn, ResponseMobileData.class);
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }

        @Override
        protected void onPostExecute(ResponseMobileData response) {
            AbstractResults result = new AbstractResults();
            try {
                if (response.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                    proccessMessage.setText("Procesando Datos Maestros");
                    new AsyncDBOffsetValues().execute(response.getLsObject());
                } else if (response.getAbstractResult().getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                    CommonUtilities.cancelSession(context, activity);
                } else {
                    cancelLoading();
                    result = response.getAbstractResult();
                    Toast.makeText(context, "[AsyncGetMobileData] - Ocurrio un error el procesar la solicitud " + result.getResultMsgAbs(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                cancelLoading();
                Toast.makeText(context, "[AsyncGetMobileData] - Ocurrio un error el procesar la solicitud", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Asyng get Count Data

    private class AsyncGetMobileCountData extends AsyncTask<String, String, ResponseCount> {

        @Override
        protected ResponseCount doInBackground(String... values) {
            publishProgress("Descargando Informacion...");
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);

                publishProgress("Procesando Informacion...");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn != null) {
                try {
                    return gson.fromJson(toReturn, ResponseCount.class);
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }

        @Override
        protected void onPostExecute(ResponseCount response) {
            AbstractResults result = new AbstractResults();
            try {
                if (response.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                    HashMap<String, Integer> countValues;
                    countMap = response.getLsObject();
                    countValues = response.getLsObject();
                    CountBean cBean = new CountBean();
                    cBean.setCountMap(countValues);
                    cBean.setValueOf(countValues.toString());
                    CommonUtilities.UpdateStoreGSonVariable(COUNTMAPPER, gson.toJson(cBean), context);
                    CommonUtilities.loggerAPI(countMap.toString(), context);
                    if (countMap != null && countMap.get(INV_E_MATERIALS) != null && countMap.get(INV_E_MATERIALS) != 0) {
                        getMobileData();
                    } else {
                        CommonUtilities.CustomWarningDialog("Advertencia!", "No se pudo completar esta solicitud",
                                activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                    @Override
                                    public void customCallBack(Integer ret) {
                                        if (ret == ResponseVariability.SUCCESSFULL) {
                                            cancelLoading();
                                        }
                                    }
                                }, R.drawable.ic_exclamation_white_48dp, context);
                    }
                } else if (response.getAbstractResult().getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                    CommonUtilities.cancelSession(context, activity);
                } else {
                    CommonUtilities.loggerAPI("Something went wrong AsyncGetMobileCountData, on request for " + CommunicationObjects.GETCOUNTEDVALUES
                            + " : " + response.toString(), context);
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                cancelLoading();
                Toast.makeText(context, "[AsyncGetMobileData] - Ocurrio un error el procesar la solicitud", Toast.LENGTH_LONG).show();
            }
        }
    }


    // Data for all else

    private class AsyncGetExtraMobileData extends AsyncTask<String, String, ResponseMobileData> {

        @Override
        protected ResponseMobileData doInBackground(String... values) {
            publishProgress("Descargando Informacion...");
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);

                publishProgress("Procesando Informacion...");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn != null) {
                try {

                    return gson.fromJson(toReturn, ResponseMobileData.class);
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }

        @Override
        protected void onPostExecute(ResponseMobileData response) {
            AbstractResults result = new AbstractResults();
            try {
                if (response.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                    proccessMessage.setText("Procesando Datos Maestros");
                    new AsyncStoreDBValues().execute(response.getLsObject());
                } else if (response.getAbstractResult().getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                    CommonUtilities.cancelSession(context, activity);
                } else {
                    cancelLoading();
                    result = response.getAbstractResult();
                    Toast.makeText(context, "[AsyncGetMobileData] - Ocurrio un error el procesar la solicitud " + result.getResultMsgAbs(), Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                cancelLoading();
                Toast.makeText(context, "[AsyncGetMobileData] - Ocurrio un error el procesar la solicitud", Toast.LENGTH_LONG).show();
            }
        }
    }


    //Async Task used Correctly

    private class AsyncUpdateMobileData extends AsyncTask<String, String, ResponseMobileData> {

        @Override
        protected ResponseMobileData doInBackground(String... values) {
            publishProgress("Descargando Informacion...");
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);

                publishProgress("Procesando Informacion...");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn != null) {
                try {
                    return gson.fromJson(toReturn, ResponseMobileData.class);
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ResponseMobileData response) {
            AbstractResults result = new AbstractResults();
            try {
                if (response.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                    proccessMessage.setText("Procesando Datos Maestros");
                    new AsyncStoreDBValues().execute(response.getLsObject());
                } else if (response.getAbstractResult().getResultId() == ResponseVariability.SESSIONNOTFOUND) {
                    cancelLoading();
                    CommonUtilities.cancelSession(context, activity);
                } else {
                    cancelLoading();
                    Toast.makeText(context, result.getResultMsgAbs(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                cancelLoading();
                Toast.makeText(context, "[AsyncUpdateMobileData] - Ocurrio un problema con la solicitud al servidor", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AsyncDBOffsetValues extends AsyncTask<String, String, AsyncDbProg> {


        @Override
        protected AsyncDbProg doInBackground(String... lsObject) {
            AbstractResults result = new AbstractResults();
            AsyncDbProg asyncDbProg = new AsyncDbProg();
            byte[] data = Base64.decode(lsObject[0], Base64.DEFAULT);
            ObjectInputStream ois;
            DownloadDataBean beanOf = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(data));
                if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                    beanOf = (DownloadDataBean) ois.readObject();
                }
                ois.close();
                asyncDbProg.setDownloadDataBean(beanOf);
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                result.setResultId(ResponseVariability.EXCEPTION);
                result.setResultMsgAbs("[AsyncStoreDBValues]- See Log For Detail Exception: " + e.getMessage());
                asyncDbProg.setDownloadDataBean(null);
            }
            publishProgress("Ingresando Informacion en el Dispositivo");
            asyncDbProg.setResults(result);
            return asyncDbProg;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }

        @Override
        protected void onPostExecute(AsyncDbProg result) {
            try {
                AbstractResults newResult = new AbstractResults();
                if (result.getDownloadDataBean() != null) {
                    new AsyncPublishUpdates().execute("Cargando Datos de Materiales");
                    if (!result.getDownloadDataBean().getListMobileMaterial().isEmpty()) {
                        // Aquí se guarda BBDD del teléfono
                        newResult = downloadViewModel.loadMobileData(result.getDownloadDataBean().getListMobileMaterial());
                        if (newResult.getResultId() == ResponseVariability.SUCCESSFULL) {
                            rowCount = rowCount + Integer.parseInt(INITIAL_OFFSET_VAL);
                            getMobileData();
                        } else {
                            newResult.setResultMsgAbs("Ocurrio un Error al Cargar Datos Maestros");
                        }
                    } else {
                        HashMap<String, Integer> getCounted = downloadViewModel.getCountedMasterData();
                        //CrashReporter.logException(new Exception(getCounted.toString() + "" + countMap.toString()));
                        if (countMap.get(INV_E_MATERIALS).toString().equalsIgnoreCase(getCounted.get(INV_E_MATERIALS).toString())) {
                            getOffAssetMaterials();
                        } else {
                            cancelLoading();
                            if (retryVal < 3) {
                                CommonUtilities.CustomWarningDialog("¡Advertencia!", "Los Materiales no se descargaron\ncorrectamente.\nSe ejecutara un reintento.",
                                        activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                            @Override
                                            public void customCallBack(Integer ret) {
                                                if (ret.equals(ResponseVariability.SUCCESSFULL)) {
                                                    retryVal++;
                                                    executeFectchRetry();
                                                }
                                            }
                                        }
                                        , R.drawable.ic_exclamation_white_36dp, context);
                            } else {
                                CommonUtilities.CustomWarningDialog("¡Advertencia!", "Los Materiales no se descargaron\ncorrectamente.\nContacte al Admin del Aplicativo.",
                                        activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                            @Override
                                            public void customCallBack(Integer ret) {
                                                if (ret.equals(ResponseVariability.SUCCESSFULL)) {
                                                    routeModel.deleteAdminMode();
                                                    finishAndRemoveTask();
                                                    startActivity(new Intent(context, UpDownActivity.class));
                                                }
                                            }
                                        }
                                        , R.drawable.ic_exclamation_white_36dp, context);
                            }
                        }
                    }
                } else {
                    cancelLoading();
                    Toast.makeText(context, "Ocurrio un error en el llenado de base de datos del dispositivo", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e1) {
                CommonUtilities.loggerAPI(e1, context);
                CrashReporter.logException(e1);
                cancelLoading();
                Toast.makeText(context, "Ocurrio un error en el llenado de base de datos del dispositivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AsyncStoreDBValues extends AsyncTask<String, String, AsyncDbProg> {


        @Override
        protected AsyncDbProg doInBackground(String... lsObject) {
            AbstractResults result = new AbstractResults();
            AsyncDbProg asyncDbProg = new AsyncDbProg();
            byte[] data = Base64.decode(lsObject[0], Base64.DEFAULT);
            ObjectInputStream ois;
            DownloadDataBean beanOf = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(data));
                if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                    beanOf = (DownloadDataBean) ois.readObject();
                }
                ois.close();
                asyncDbProg.setDownloadDataBean(beanOf);
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                result.setResultId(ResponseVariability.EXCEPTION);
                result.setResultMsgAbs("[AsyncStoreDBValues]- See Log For Detail Exception: " + e.getMessage());
                asyncDbProg.setDownloadDataBean(null);
            }
            publishProgress("Ingresando Informacion en el Dispositivo");
            asyncDbProg.setResults(result);
            return asyncDbProg;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }

        @Override
        protected void onPostExecute(AsyncDbProg result) {
            try {
                AbstractResults newResult = new AbstractResults();
                if (result.getDownloadDataBean() != null) {
                    new AsyncPublishUpdates().execute("Cargando Datos de Tarimas");
                    newResult = downloadViewModel.loadMaterialCrossTarimas(result.getDownloadDataBean().getListMaterialTarimas());
                    if (newResult.getResultId() == ResponseVariability.SUCCESSFULL) {
                        new AsyncPublishUpdates().execute("Cargando Datos de Sistema de Clasificación");
                        try {
                            routeModel.deleteClass();
                        } catch (Exception e) {
                            CommonUtilities.loggerAPI(e, context);
                            CrashReporter.logException(e);
                        }
                        newResult = downloadViewModel.loadClassSystem(result.getDownloadDataBean().getZiacmf_I360_EXT_SIS_CLAS());
                        if (newResult.getResultId() == ResponseVariability.SUCCESSFULL) {
                            new AsyncPublishUpdates().execute("Cargando datos de clase de valoración");
                            try {
                                routeModel.deleteClassValuation();
                            } catch (Exception e) {
                                CommonUtilities.loggerAPI(e, context);
                                CrashReporter.logException(e);
                            }
                            newResult = downloadViewModel.loadValuationSystem(result.getDownloadDataBean().getZiacfm_CLVAL());
                            if (newResult.getResultId() == ResponseVariability.SUCCESSFULL) {
                                cancelLoading();
                                MobileDataFetch mobile = new MobileDataFetch(new Date().getTime());
                                CommonUtilities.UpdateStoreGSonVariable(CommunicationObjects.LASTFETCHED, gson.toJson(mobile), context);
                                CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "Datos Maestros Actualizados Correctamente", activity, null, null
                                        , null, new CommonUtilities.CustomCallBack<Integer>() {
                                            @Override
                                            public void customCallBack(Integer ret) {
                                                requestTask.setAlpha(1F);
                                                requestTask.setEnabled(true);
                                            }
                                        }, null, context);
                            }
                        } else {
                            newResult.setResultMsgAbs("Ocurrio un Error al Cargar Sistema de Clasificacion");
                        }
                    } else {
                        newResult.setResultMsgAbs("Ocurrio un Error al Cargar Datos Maestros");
                    }
                } else {
                    cancelLoading();
                    Toast.makeText(context, "Ocurrio un error en el llenado de base de datos del dispositivo", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e1) {
                CommonUtilities.loggerAPI(e1, context);
                CrashReporter.logException(e1);
                cancelLoading();
                Toast.makeText(context, "Ocurrio un error en el llenado de base de datos del dispositivo", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class MyLoginRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... values) {
            String toReturn = null;
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                toReturn = sb.toString();
            } catch (MalformedURLException e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
            } catch (IOException e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
            } catch (NullPointerException e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
            }
            return toReturn;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    cancelLoading();
                    ResponseLogin result = gson.fromJson(s, ResponseLogin.class);
                    switch (result.getAbstractResult().getResultId()) {
                        case GlobalConstants.ISUCCESS:
                            LoginBean loginReturn = result.getLsObject();
                            loginObject.setRelationUUID(loginReturn.getRelationUUID());
                            loginObject.setActiveInterval(loginReturn.getActiveInterval());
                            loginObject.setLsObjectLB(loginReturn.getLsObjectLB());
                            loginObject.setLoginLang(result.getAbstractResult().getStrCom3());
                            CommonUtilities.StoreLoginValues(loginObject, context);
                            startActivity(new Intent(context, UpDownActivity.class));
                            break;
                        case GlobalConstants.IPASSWORDNOTMATCH:
                            Toast.makeText(context, result.getAbstractResult().getResultMsgAbs(), Toast.LENGTH_LONG).show();
                            break;
                        case GlobalConstants.IUSERNOTEXISTS:
                            Toast.makeText(context, result.getAbstractResult().getResultMsgAbs(), Toast.LENGTH_LONG).show();
                            break;
                        case GlobalConstants.ILDAPTIMEOUT:
                            Toast.makeText(context, result.getAbstractResult().getResultMsgAbs(), Toast.LENGTH_LONG).show();
                            break;
                        case GlobalConstants.IEXCEPTION:
                            Toast.makeText(context, result.getAbstractResult().getResultMsgAbs(), Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context, result.getAbstractResult().getResultMsgAbs(), Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    cancelLoading();
                }
            } else {
                cancelLoading();
                Toast.makeText(context, "Ocurrio un error al procesar la solicitud", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private class AsyncPublishUpdates extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            publishProgress(strings[0]);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            proccessMessage.setText(values[0]);
        }
    }

    private class UpdateApplicationLogs extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... values) {
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {

                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, context, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(CommonUtilities.AUTH)) {
                        wr.write(postData);
                    }
                }
                InputStreamReader reader = new InputStreamReader(connect.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn.isEmpty()) {
                return null;
            } else {
                return gson.fromJson(toReturn, Response.class);
            }


        }

        @Override
        protected void onPostExecute(Response abstractResults) {
            try {
                if (abstractResults.getAbstractResult().getResultId() == ResponseVariability.SUCCESSFULL) {
                    cancelLoading();
                    CommonUtilities.PopGsonVariable(LOGMAPPER, context);

                    CommonUtilities.CustomColorWarningDialog("ADVERTENCIA!", "Logs Enviados con exito", activity, null, null
                            , null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    //CommonUtilities.cancelData(context);
                                    //finishAndRemoveTask();
                                    //startActivity(new Intent(context, LoginActivity.class));

                                }
                            }, null, context, null);
                } else {
                    cancelLoading();
                    CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "Ocurrio un error durante el envio de los logs.", activity, null, null
                            , null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    //CommonUtilities.cancelData(context);
                                }
                            }, null, context);
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                cancelLoading();
                CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "Ocurrio un error durante el envio de los logs.", activity, null, null
                        , null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                //CommonUtilities.cancelData(context);
                            }
                        }, null, context);

            }
        }
    }
}
