package com.gmodelo.cutoverback.Activity;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.CURRENTSERVER;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.DEVICEID;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.LOGMAPPER;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SERVERMAPPING;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SERVERVALUE;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDLOGIN;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDROUTE;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TASKCOMPLETED;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TOKEN_KEY;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.Api.ResponseILogin;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.CommunicationObjects;
import com.gmodelo.cutoverback.CustomObjects.GlobalConstants;
import com.gmodelo.cutoverback.CustomObjects.LoginBean;
import com.gmodelo.cutoverback.CustomObjects.Request;
import com.gmodelo.cutoverback.CustomObjects.Response;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DataAccess.InstanceOfDB;
import com.gmodelo.cutoverback.Instances.RetrofitClient;
import com.gmodelo.cutoverback.StoredBeans.LoginStored;
import com.gmodelo.cutoverback.Utilities.AESHelper;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.Utilities.LogginAPI;
import com.gmodelo.cutoverback.Utilities.LogginMap;
import com.gmodelo.cutoverback.Views.DownloadViewModel;
import com.gmodelo.cutoverback.Views.RouteViewModel;
import com.gmodelo.cutoverback.beans.MapServerBean;
import com.gmodelo.cutoverback.beans.ResponseLogin;
import com.gmodelo.cutoverback.beans.ServerBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.internal.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {


    View decorView;
    LoginBean loginObject;
    EditText username, password;
    Gson gson;
    ImageButton togglePass;
    Activity activity;
    Context context;
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    Integer version = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    TextView lastCompiledText, serverDesc;
    Boolean isAppUpdated;
    CardView serverCard, navinLineAdmin;
    Runnable serverRunnable, adminRunnable;
    Handler mHandler = new Handler();
    InstanceOfDB dbInstance;
    RouteViewModel routeModel;
    DownloadViewModel downloadViewModel;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        progressBarHolder = findViewById(R.id.loaginLayout);
        username = findViewById(R.id.userId);

        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    password.setFocusableInTouchMode(true);
                    password.requestFocus();
                    return true;
                }
                return false;
            }
        });


        password = findViewById(R.id.userPass);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    LogInAction(view);
                    return true;
                }
                return false;
            }
        });

        togglePass = findViewById(R.id.togglePass);
        lastCompiledText = findViewById(R.id.lastCompiledText);
        isAppUpdated = false;
        gson = new Gson();
        context = LoginActivity.this;
        activity = LoginActivity.this;
        dbInstance = InstanceOfDB.getInstanceOfDB(context);
        routeModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        downloadViewModel = ViewModelProviders.of(this).get(DownloadViewModel.class);

        //GetBaseServer or Get from static map
        String baseDataServer = CommonUtilities.PushGsonVariable(SERVERVALUE, context);

        if (baseDataServer == null) {
            CommonUtilities.UpdateStoreGSonVariable(SERVERVALUE, gson.toJson(ResponseVariability.RELATIONINVESERVER.get(ResponseVariability.DEV_INV_E_ID)), context);
            CommonUtilities.UpdateStoreGSonVariable(DEVICEID, UUID.randomUUID().toString(), context);
            MapServerBean mapServer = new MapServerBean(ResponseVariability.RELATIONINVESERVER);
            CommonUtilities.UpdateStoreGSonVariable(SERVERMAPPING, gson.toJson(mapServer), context);
            CommonUtilities.UpdateStoreGSonVariable(CURRENTSERVER, gson.toJson(ResponseVariability.CURRENTSERVERMAP), context);
        }

        //Set Description over base Server
        serverDesc = findViewById(R.id.severDesciptionLabel);
        ServerBean svrbean = CommonUtilities.getInfoServer(context);
        if (svrbean != null) {
            serverDesc.setText(svrbean.getCommonName());
        }

        serverCard = findViewById(R.id.navinLineServer);
        serverCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(serverRunnable, 2500);
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
                new CommonUtilities().onAdminRequest(activity, new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        if (ret == ResponseVariability.SUCCESSFULL) {
                            closeKeyboard();
                            new CommonUtilities().onServerPasswordOk(activity, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret != null) {
                                        if (ret == ResponseVariability.SUCCESSFULL) {
                                            new CommonUtilities().executeAdminTask(1, activity, context);
                                            routeModel.deleteAdminMode();
                                            finishAndRemoveTask();
                                            startActivity(new Intent(context, LoginActivity.class));
                                        }
                                    } else {
                                        Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(context, LoginActivity.class));
                                    }
                                }
                            }, context);
                        } else {
                            closeKeyboard();
                        }
                    }
                }, context);

            }
        };

        navinLineAdmin = findViewById(R.id.navinLineAdmin);
        navinLineAdmin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(adminRunnable, 2500);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(adminRunnable);
                        break;
                }
                return true;
            }
        });

        adminRunnable = new Runnable() {
            @Override
            public void run() {
                new CommonUtilities().onAdminRequest(activity, new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        if (ret == ResponseVariability.SUCCESSFULL) {
                            closeKeyboard();
                            new CommonUtilities().onAdminPasswordOk(activity, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret != null) {
                                        switch (ret) {
                                            case 1:
                                                new CommonUtilities().executeAdminTask(2, activity, context);
                                                routeModel.deleteAdminMode();
                                                finishAndRemoveTask();
                                                startActivity(new Intent(context, LoginActivity.class));
                                                break;
                                            case 2:
                                                updateLogs();
                                                break;

                                            case 3:
                                                CommonUtilities.CustomWarningDialog("DEVICE ID", CommonUtilities.PushGsonVariable(DEVICEID, context), activity, null, null,
                                                        null, new CommonUtilities.CustomCallBack<Integer>() {
                                                            @Override
                                                            public void customCallBack(Integer ret) {
                                                                //finishAndRemoveTask();
                                                                //startActivity(new Intent(context, LoginActivity.class));
                                                            }
                                                        }, null, context);
                                                break;
                                            case 4:
                                                HashMap<String, Integer> getCounted = downloadViewModel.getCountedMasterData();
                                                new CommonUtilities().onAdminCountedData(activity, new CommonUtilities.CustomCallBack<Integer>() {
                                                    @Override
                                                    public void customCallBack(Integer ret) {
                                                        //finishAndRemoveTask();
                                                        //startActivity(new Intent(context, LoginActivity.class));
                                                    }
                                                }, context, getCounted);

                                                break;
                                        }
                                    } else {
                                        startActivity(new Intent(context, LoginActivity.class));
                                    }
                                }
                            }, context);
                        } else {
                            closeKeyboard();
                        }
                    }
                }, context);

            }
        };

        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        togglePass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        password.setSelection(password.getText().length());
                        break;

                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        password.setSelection(password.getText().length());
                        break;

                    case MotionEvent.ACTION_BUTTON_RELEASE:
                        password.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setSelection(password.getText().length());
                        break;

                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setSelection(password.getText().length());

                        break;
                }
                return true;
            }
        });
        lastCompiledText.setText("V_" + versionName);

        if (!CommonUtilities.hasNetwork(context)) {
            CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "No dispone de conectividad a internet...\nFavor de validar e ingresar nuevamente!",
                    activity, null, null
                    , null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            System.exit(0);
                        }
                    }, null, context);
        } else {
            checkAppVersion();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
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
        //progressBar.setVisibility(View.VISIBLE);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void LogInAction(View view) {
        if (CommonUtilities.hasNetwork(context)) {
            if (isAppUpdated) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user == null || user.equals("") || user.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "El usuario no puede ir Vacio", Toast.LENGTH_LONG).show();
                    username.setFocusableInTouchMode(true);
                    username.requestFocus();
                } else if (pass == null || pass.equals("") || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "La contraseña no puede ir Vacio", Toast.LENGTH_LONG).show();
                    password.setFocusableInTouchMode(true);
                    password.requestFocus();
                } else {
                    loginObject = new LoginBean();
                    loginObject.setLoginId(user);
                    loginObject.setLoginPass(pass);
                    loginObject.setLoginLang("ES");
                    loginObject.setRelationUUID(UUID.randomUUID().toString());
                    initiateLoading();
                    doLoginRetrofit();
                }
            }
        } else {
            CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "No dispone de conectividad a internet...\nFavor de validar e ingresar nuevamente!", activity, null, null
                    , null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                        }
                    }, null, context);
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
                            try {
                                if (loginObject.getLsObjectLB().getAccInf().getLockPass() == 1) {
                                    CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "La contraseña del usuario esta por caducar\n" +
                                                    "Favor de realizar la actualizacion\n e ingresar posteriormente a la aplicación",
                                            activity, null, null
                                            , null, new CommonUtilities.CustomCallBack<Integer>() {
                                                @Override
                                                public void customCallBack(Integer ret) {
                                                }
                                            }, null, context);
                                } else {
                                    startActivity(new Intent(context, UpDownActivity.class));
                                }
                            } catch (Exception e) {
                                CommonUtilities.loggerAPI(e, context);
                                CrashReporter.logException(e);
                                CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "La contraseña del usuario esta por caducar\n" +
                                                "Favor de realizar la actualizacion\n e ingresar posteriormente a la aplicación",
                                        activity, null, null
                                        , null, new CommonUtilities.CustomCallBack<Integer>() {
                                            @Override
                                            public void customCallBack(Integer ret) {
                                            }
                                        }, null, context);
                            }
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
            }

        }
    }

    private void doLoginRetrofit() {
        Retrofit retrofit = RetrofitClient.getClient(CommonUtilities.getBaseServer(context));
        ResponseILogin iLogin = retrofit.create(ResponseILogin.class);

        loginObject.setLoginPass(new AESHelper().encrypt(loginObject.getLoginPass(), getString(R.string.codeWord), getString(R.string.algo)));
        Call<ResponseLogin> call = iLogin.login(loginObject);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {

                if (response.body() != null) {
                    try {
                        cancelLoading();
                        ResponseLogin result = response.body();
                        switch (result.getAbstractResult().getResultId()) {
                            case GlobalConstants.ISUCCESS:
                                LoginBean loginReturn = result.getLsObject();
                                loginObject.setRelationUUID(loginReturn.getRelationUUID());
                                loginObject.setActiveInterval(loginReturn.getActiveInterval());
                                loginObject.setLsObjectLB(loginReturn.getLsObjectLB());
                                loginObject.setLoginLang(result.getAbstractResult().getStrCom3());
                                // Guardamos el token en memoria
                                Log.e(this.getClass().getName(), "getToken(): " + loginReturn.getToken());
                                CommonUtilities.UpdateStoreGSonVariable(TOKEN_KEY, loginReturn.getToken(), context);
                                CommonUtilities.StoreLoginValues(loginObject, context);
                                try {
                                    if (loginObject.getLsObjectLB().getAccInf().getLockPass() == 1) {
                                        CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "La contraseña del usuario esta por caducar\n" +
                                                        "Favor de realizar la actualizacion\n e ingresar posteriormente a la aplicación",
                                                activity, null, null
                                                , null, new CommonUtilities.CustomCallBack<Integer>() {
                                                    @Override
                                                    public void customCallBack(Integer ret) {
                                                    }
                                                }, null, context);
                                    } else {
                                        // Si el inicio de sesión fue exitoso
                                        if (CommonUtilities.hasNetwork(context)) {
                                            if (CommonUtilities.PushGsonVariable(STOREDROUTE, context) != null) {
                                                if (CommonUtilities.PushGsonVariable(TASKCOMPLETED, context) != null) {
                                                    initiateLoading();
                                                    startActivity(new Intent(context, UpDownActivity.class));
                                                } else {
                                                    initiateLoading();
                                                    startActivity(new Intent(context, BeginCountActivity.class));
                                                }
                                            } else {
                                                startActivity(new Intent(context, UpDownActivity.class));
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    CommonUtilities.loggerAPI(e, context);
                                    CrashReporter.logException(e);
                                    CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "La contraseña del usuario esta por caducar\n" +
                                                    "Favor de realizar la actualizacion\n e ingresar posteriormente a la aplicación",
                                            activity, null, null
                                            , null, new CommonUtilities.CustomCallBack<Integer>() {
                                                @Override
                                                public void customCallBack(Integer ret) {
                                                }
                                            }, null, context);
                                }
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

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                cancelLoading();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void showPopUp(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        final CommonUtilities common = new CommonUtilities();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            Intent menuIntent = null;

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popup.inflate(R.menu.loginmenu);
        popup.show();
    }


    public void checkAppVersion() {
        initiateLoading();
        Request request = new Request();
        AbstractResults results = new AbstractResults();
        results.setIntCom1(version);
        results.setStrCom1(versionName);
        request.setLsObject(results);
        new IsAppUpdated().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.UPDATEAPPMODULE);
    }

    private class IsAppUpdated extends AsyncTask<String, Void, Response> {

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
                    String loginString = CommonUtilities.PushGsonVariable(STOREDLOGIN, context);
                    isAppUpdated = true;
                    cancelLoading();
                    if (loginString != null) {
                        initiateLoading();
                        LoginStored stored = new LoginStored();
                        stored = gson.fromJson(loginString, LoginStored.class);
                        loginObject = stored.getLoginBean();
                        cancelLoading();
                        //new MyLoginRequest().execute(gson.toJson(loginObject), CommonUtilities.getBaseServer(context) + CommunicationObjects.LOGINMODULE);
                    }
                } else {
                    cancelLoading();
                    isAppUpdated = false;
                    CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "Version no habilitada para su uso. ¡Contacte al Administrador!", activity, null, null
                            , null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    CommonUtilities.cancelData(context);
                                }
                            }, null, context);
                }
            } catch (Exception e) {
                CommonUtilities.loggerAPI(e, context);
                CrashReporter.logException(e);
                cancelLoading();
                isAppUpdated = false;
                CommonUtilities.CustomWarningDialog("ADVERTENCIA!", "Sin conectividad, por favor valide su conexión e intente nuevamente...", activity, null, null
                        , null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                //CommonUtilities.cancelData(context);
                            }
                        }, null, context);
            }
        }
    }


    public void updateLogs() {
        initiateLoading();
        Request<List<LogginAPI>> request = new Request();
        AbstractResults results = new AbstractResults();

        String logString = CommonUtilities.PushGsonVariable(LOGMAPPER, context);
        if (logString != null) {

            LogginMap logMap = gson.fromJson(logString, LogginMap.class);
            Iterator it = logMap.getLogMap().entrySet().iterator();
            List<LogginAPI> apiList = new ArrayList<>();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                apiList.add((LogginAPI) entry.getValue());
            }
            if (!apiList.isEmpty()) {
                request.setLsObject(apiList);
                new UpdateApplicationLogs().execute(gson.toJson(request), CommonUtilities.getBaseServer(context) + CommunicationObjects.UPDATELOGSTOSERVER);
            } else {
                CommonUtilities.CustomColorWarningDialog("ADVERTENCIA!", "No cuenta con Logs para enviarlos al servidor", activity, null, null
                        , null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                //CommonUtilities.cancelData(context);
                                cancelLoading();
                            }
                        }, null, context, null);
            }


        } else {
            CommonUtilities.CustomColorWarningDialog("ADVERTENCIA!", "No cuenta con Logs para enviarlos al servidor", activity, null, null
                    , null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            cancelLoading();
                        }
                    }, null, context, null);
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

    public void showCerMsg(View view) {
        Toast.makeText(context, "Versión de certificado 2022", Toast.LENGTH_SHORT).show();
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            onWindowFocusChanged(true);
        }
    }
}
