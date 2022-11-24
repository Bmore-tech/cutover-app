package com.gmodelo.cutoverback.Utilities;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.CURRENTSERVER;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.DEVICEID;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.LASTFETCHED;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.LOGMAPPER;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.NOTTARIMA;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.PREVIEWVALUES;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SAPSPECIALCOUNT;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SERVERMAPPING;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SERVERVALUE;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDLOGIN;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDROUTE;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TASKCOMPLETED;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TOKEN_KEY;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.VALIDATEUSABLESERVER;
import static com.gmodelo.cutoverback.CustomObjects.ResponseVariability.NOSELECTEDTARIMA;
import static com.gmodelo.cutoverback.CustomObjects.ResponseVariability.TIMEOUTINMILLIS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.Activity.LoginActivity;
import com.gmodelo.cutoverback.Activity.R;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.CommunicationObjects;
import com.gmodelo.cutoverback.CustomObjects.LoginBean;
import com.gmodelo.cutoverback.CustomObjects.Request;
import com.gmodelo.cutoverback.CustomObjects.Response;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DaoBeans.TarimasDescriptionBean;
import com.gmodelo.cutoverback.StoredBeans.LoginStored;
import com.gmodelo.cutoverback.beans.MapServerBean;
import com.gmodelo.cutoverback.beans.MaterialTarimasBean;
import com.gmodelo.cutoverback.beans.ServerBean;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import at.markushi.ui.CircleButton;

public class CommonUtilities {

    private static String contentType = "application/json";
    private static String disclamer = "Advertencia!";
    Context asyncContext;

    public static String AUTH = "AUTHORIZATION";

    public HttpsURLConnection CustomHttpsConnection(byte[] postData, Context context, String... values) {
        // MAZ Fix
        if (values[1].contains("\\\\")) {
            values[1] = values[1].replace("\\\\", "\\");
        }

        HttpsURLConnection httpsUrlConnection = null;
        int postDataLength = postData.length;
        try {
            URL url = new URL(values[1]);
            httpsUrlConnection = (HttpsURLConnection) url.openConnection();
            httpsUrlConnection.setRequestMethod("POST");
            httpsUrlConnection.setRequestProperty("Content-Type", contentType);
            httpsUrlConnection.setRequestProperty("charset", "utf-8");
            httpsUrlConnection.setRequestProperty("Accept", contentType);
            // Aquí va el Token
            httpsUrlConnection.setRequestProperty("Authorization", CommonUtilities.PushGsonVariable(TOKEN_KEY, context));
            httpsUrlConnection.getDoOutput();
            httpsUrlConnection.setInstanceFollowRedirects(false);
            httpsUrlConnection.setConnectTimeout(TIMEOUTINMILLIS);
            httpsUrlConnection.setReadTimeout(TIMEOUTINMILLIS);
            httpsUrlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            httpsUrlConnection.setUseCaches(false);
        } catch (MalformedURLException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            httpsUrlConnection = null;
        } catch (IOException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            httpsUrlConnection = null;
        } catch (NullPointerException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            httpsUrlConnection = null;
        } catch (Exception e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            httpsUrlConnection = null;
        }
        return httpsUrlConnection;
    }


    public HttpURLConnection CustomHttpConnection(byte[] postData, Context context, String... values) {
        HttpURLConnection connect = null;
        int postDataLength = postData.length;
        try {
            URL url = new URL(values[1]);
            connect = (HttpURLConnection) url.openConnection();
            connect.getDoOutput();
            connect.setInstanceFollowRedirects(false);
            connect.setConnectTimeout(TIMEOUTINMILLIS);
            connect.setReadTimeout(TIMEOUTINMILLIS);
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", contentType);
            connect.setRequestProperty("charset", "utf-8");
            connect.setRequestProperty("Accept", contentType);
            connect.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connect.setUseCaches(false);
        } catch (MalformedURLException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            connect = null;
        } catch (IOException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            connect = null;
        } catch (NullPointerException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            connect = null;
        } catch (Exception e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            connect = null;
        }
        return connect;
    }

    public static Boolean hasNetwork(Context context) {
        ConnectivityManager cm;
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public static Integer PopGsonVariable(String key, Context getBaseContext) {
        Integer response;
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext);
        SharedPreferences.Editor editor = shared.edit();
        if (shared.contains(key)) {
            editor.remove(key);
            editor.commit();
            response = ResponseVariability.REMOVED;
        } else {
            response = ResponseVariability.NOTFOUND;
        }
        return response;

    }

    public static String PushGsonVariable(String key, Context getBaseContext) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext);
        if (shared.contains(key)) {
            if (checkAuthorization(AUTH)) {
                return shared.getString(key, "");
            }
        } else {
            return null;
        }
        return null;
    }


    public static void cancelData(@NonNull Context context) {
        try {
            PopGsonVariable(STOREDLOGIN, context);
            PopGsonVariable(STOREDROUTE, context);
            PopGsonVariable(PREVIEWVALUES, context);
            PopGsonVariable(TASKCOMPLETED, context);
            PopGsonVariable(NOTTARIMA, context);
            //PopGsonVariable(NOTVALUATION, context);
        } catch (Exception e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            cancelData(context);
        }
    }


    public static void depuration(@NonNull Context context, @NonNull Activity activity) {
        try {
            PopGsonVariable(STOREDLOGIN, context);
            PopGsonVariable(STOREDROUTE, context);
            PopGsonVariable(PREVIEWVALUES, context);
            PopGsonVariable(TASKCOMPLETED, context);
            PopGsonVariable(SAPSPECIALCOUNT, context);
            PopGsonVariable(LASTFETCHED, context);
            PopGsonVariable(NOTTARIMA, context);

            activity.startActivity(new Intent(context, LoginActivity.class));
        } catch (Exception e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            depuration(context, activity);
        }
    }

    public static void depurationAdmin(@NonNull Context context, @NonNull Activity activity) {
        try {
            PopGsonVariable(STOREDLOGIN, context);
            PopGsonVariable(STOREDROUTE, context);
            PopGsonVariable(PREVIEWVALUES, context);
            PopGsonVariable(TASKCOMPLETED, context);
            PopGsonVariable(SAPSPECIALCOUNT, context);
            PopGsonVariable(LASTFETCHED, context);
            PopGsonVariable(SERVERVALUE, context);
            PopGsonVariable(SERVERMAPPING, context);
            PopGsonVariable(CURRENTSERVER, context);
            PopGsonVariable(NOTTARIMA, context);
            activity.startActivity(new Intent(context, LoginActivity.class));
        } catch (Exception e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            depuration(context, activity);
        }
    }

    public static void cancelSession(@NonNull Context context, @NonNull Activity activity) {
        try {
            PopGsonVariable(STOREDLOGIN, context);
            PopGsonVariable(STOREDROUTE, context);
            PopGsonVariable(PREVIEWVALUES, context);
            PopGsonVariable(TASKCOMPLETED, context);
            PopGsonVariable(SAPSPECIALCOUNT, context);
            PopGsonVariable(NOTTARIMA, context);
            // Borramos el Toekn
            PopGsonVariable(TOKEN_KEY, context);
            activity.startActivity(new Intent(context, LoginActivity.class));
        } catch (Exception e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            activity.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public static void cancelPreviewValues(@NonNull Context context, @Nullable Activity activity) {
        PopGsonVariable(STOREDROUTE, context);
        PopGsonVariable(PREVIEWVALUES, context);
        PopGsonVariable(TASKCOMPLETED, context);
        PopGsonVariable(SAPSPECIALCOUNT, context);
        PopGsonVariable(NOTTARIMA, context);
        if (activity != null) {
            activity.startActivity(new Intent(context, activity.getClass()));
        }
    }


    public static Integer UpdateStoreGSonVariable(String key, String storedValue, Context getBaseContext) {
        Integer response;
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, (storedValue));
        editor.commit();

        if (shared.contains(key))
            response = ResponseVariability.UPDATED;
        else
            response = ResponseVariability.CREATED;

        return response;
    }

    public static ServerBean getInfoServer(@NonNull Context getBaseContext) {
        String serverValue = PushGsonVariable(SERVERVALUE, getBaseContext);
        ServerBean base = null;
        if (serverValue != null) {
            base = new Gson().fromJson(serverValue, ServerBean.class);
        }
        return base;
    }

    @NotNull
    public static String getBaseServer(@NonNull Context getBaseContext) {
        String base = "";
        String serverValue = PushGsonVariable(SERVERVALUE, getBaseContext);
        if (serverValue != null) {
            base = new Gson().fromJson(serverValue, ServerBean.class).baseServer();
        }
        return base;
    }

    public static void loggerAPI(@NotNull String e, @NotNull Context context) {
        loggerAPI(new Exception(e), context);
    }


    public static void loggerAPI(@NotNull Exception e, @NotNull Context context) {
        try {
            new CommonUtilities().generateLog(e, context);
        } catch (Exception ex) {
            CrashReporter.logException(ex);
            CrashReporter.logException(e);
        }
    }


    public void generateLog(@NotNull Exception e, @NotNull Context context) {
        String logString = PushGsonVariable(LOGMAPPER, context);
        Long timeStamp = new Date().getTime();
        Gson gson = new Gson();
        LogginMap mapLog = null;
        if (logString != null) {
            LogginAPI toLog = new LogginAPI(e, timeStamp, PushGsonVariable(DEVICEID, context));
            mapLog = gson.fromJson(logString, LogginMap.class);
            mapLog.getLogMap().put(String.valueOf(timeStamp), toLog);
        } else {
            LogginAPI toLog = new LogginAPI(e, timeStamp, PushGsonVariable(DEVICEID, context));
            mapLog = new LogginMap();
            mapLog.getLogMap().put(String.valueOf(timeStamp), toLog);
        }
        UpdateStoreGSonVariable(LOGMAPPER, gson.toJson(mapLog), context);
        deleteOldLogs(context);
    }

    public void deleteOldLogs(@NotNull Context context) {
        String logString = PushGsonVariable(LOGMAPPER, context);
        Long timeStamp = new Date().getTime();
        Gson gson = new Gson();
        if (logString != null) {
            LogginMap mapLog = gson.fromJson(logString, LogginMap.class);
            LogginMap pivotMap = mapLog;
            Iterator it = mapLog.getLogMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (TimeUnit.DAYS.convert(timeStamp - Long.parseLong((String) entry.getKey()), TimeUnit.MILLISECONDS) > 7) {
                    pivotMap.getLogMap().remove(entry.getKey());
                }
            }
            UpdateStoreGSonVariable(LOGMAPPER, gson.toJson(pivotMap), context);
        }
    }


    public static void utilitiesScanner(Activity activity, String prompt, Integer requestCode) {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.setPrompt(prompt);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setRequestCode(requestCode);
        integrator.initiateScan();
    }


    public static void StoreLoginValues(LoginBean loginBean, Context context) {
        LoginStored stored = new LoginStored();
        loginBean.setLoginPass(new AESHelper().encrypt(loginBean.getLoginPass(), context.getString(R.string.codeWord), context.getString(R.string.algo)));
        stored.setLoginBean(loginBean);
        stored.setLastLogin(new Date());
        UpdateStoreGSonVariable(STOREDLOGIN, new Gson().toJson(stored), context);
    }


    @Nullable
    public static LoginBean getStructureToSend(@NonNull Context context) {
        LoginBean returnBean = new LoginBean();
        String storedLogin = null;
        storedLogin = PushGsonVariable(STOREDLOGIN, context);
        if (storedLogin != null) {
            LoginStored stored;
            stored = new Gson().fromJson(storedLogin, LoginStored.class);
            returnBean.setLoginId(stored.getLoginBean().getLoginId());
            returnBean.setLoginPass(stored.getLoginBean().getLoginPass());
            try {
                if (stored.getLoginBean().getRelationUUID() != null && !stored.getLoginBean().getRelationUUID().isEmpty()) {
                    returnBean.setRelationUUID(stored.getLoginBean().getRelationUUID());
                } else {
                    returnBean.setRelationUUID(UUID.randomUUID().toString());
                }
            } catch (Exception e) {
                loggerAPI(e, context);
                CrashReporter.logException(e);
                returnBean.setRelationUUID(UUID.randomUUID().toString());
            }
            returnBean.setActiveInterval(12000);
        } else {
            return null;
        }

        return returnBean;
    }

    @Nullable
    public static LoginStored getLoginStructure(@NotNull Context context) {
        LoginStored stored = null;
        String storedLogin = null;
        storedLogin = PushGsonVariable(STOREDLOGIN, context);
        if (storedLogin != null) {
            stored = new Gson().fromJson(storedLogin, LoginStored.class);
            stored.getLoginBean().setLoginPass(new AESHelper().decrypt(stored.getLoginBean().getLoginPass(), context.getString(R.string.codeWord), context.getString(R.string.algo)));
        }

        return stored;
    }

    public interface CustomCallBack<T> {
        void customCallBack(T ret);
    }

    static AlertDialog customConfirmDialog;
    static AlertDialog customAddServerDialog;
    static AlertDialog customAdminMenuDialog;

    public static void showPopDatePicker(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<String> callback, @NonNull Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.datepicker, null);
        final DatePicker datePicker = mCustom.findViewById(R.id.datepicker);
        datePicker.updateDate(y, m, d);
        ImageButton confirmButton = mCustom.findViewById(R.id.custom_picker_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (callback != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    String formatedDate = sdf.format(calendar.getTime());
                    callback.customCallBack(formatedDate);
                }
            }
        });
        ImageButton negativeButton = mCustom.findViewById(R.id.custom_picker_cancel);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (callback != null) {
                    callback.customCallBack(null);
                }
            }
        });
        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }

    public void onAdminRequest(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<Integer> callback, @NonNull final Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_request_value, null);
        final EditText adminPassValue = mCustom.findViewById(R.id.adminPassValue);
        CardView adminPassCardExit = mCustom.findViewById(R.id.adminPassCardExit);
        CardView adminPassCardAccept = mCustom.findViewById(R.id.adminPassCardAccept);
        final TextView warningAdminValue = mCustom.findViewById(R.id.warningAdminValue);

        adminPassCardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.customCallBack(ResponseVariability.ERROR);
                customConfirmDialog.dismiss();
            }
        });

        adminPassCardAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = adminPassValue.getText().toString();
                if (value != null && !value.isEmpty()) {
                    AbstractResults results = checkCorrectValueForAdmin(value, context);
                    if (results.getResultId() == ResponseVariability.SUCCESSFULL) {
                        customConfirmDialog.dismiss();
                        callback.customCallBack(ResponseVariability.SUCCESSFULL);
                    } else if (results.getResultId() == ResponseVariability.IPASSWORDNOTMATCH
                    || results.getResultId() == ResponseVariability.IREPKEYNOTFOUND) {
                        warningAdminValue.setText(results.getResultMsgAbs());
                    } else {
                        if (results.getResultMsgAbs() != null && !results.getResultMsgAbs().isEmpty()) {
                            warningAdminValue.setText(results.getResultMsgAbs());
                        } else {
                            warningAdminValue.setText("Ocurrio un problema al ejecutar su solicitud.");
                        }
                    }

                } else {
                    warningAdminValue.setText("El valor no puede ir vacio");
                }
            }
        });
        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }

    public void onPasswordRequest(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<String> callback, @NonNull final Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_password_value, null);
        final EditText adminPassValue = mCustom.findViewById(R.id.adminPassValue);
        CardView adminPassCardExit = mCustom.findViewById(R.id.adminPassCardExit);
        CardView adminPassCardAccept = mCustom.findViewById(R.id.adminPassCardAccept);
        final TextView warningAdminValue = mCustom.findViewById(R.id.warningAdminValue);

        adminPassCardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.customCallBack(ResponseVariability.S_ERROR);
                customConfirmDialog.dismiss();
            }
        });

        adminPassCardAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = adminPassValue.getText().toString();
                if (value != null && !value.isEmpty()) {
                    callback.customCallBack(value);
                    customConfirmDialog.dismiss();
                } else {
                    warningAdminValue.setText("El valor no puede ir vacio");
                }
            }
        });
        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }


    public AbstractResults checkCorrectValueForAdmin(String value, Context context) {
        Request req = new Request();
        AbstractResults result = new AbstractResults();
        result.setStrCom1(value);
        req.setLsObject(result);
        try {
            asyncContext = context;
            Response response = new IsAdminEnabled().execute(new Gson().toJson(req), getBaseServer(context) + CommunicationObjects.VALIDATEADMIN).get();
            if (response != null) {
                result = response.getAbstractResult();
            } else {
                result.setResultId(ResponseVariability.EXCEPTION);
            }
        } catch (InterruptedException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            result.setResultId(ResponseVariability.EXCEPTION);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            result.setResultId(ResponseVariability.EXCEPTION);
        }
        return result;
    }

    private class IsAdminEnabled extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... values) {
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {

                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, asyncContext, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (checkAuthorization(AUTH)) {
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
                loggerAPI(e, asyncContext);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn.isEmpty()) {
                return null;
            } else {
                return new Gson().fromJson(toReturn, Response.class);
            }
        }
    }


    public void onServerPasswordOk(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<Integer> callback, @NonNull final Context context) {
        String mapServer = PushGsonVariable(SERVERMAPPING, context);
        if (mapServer != null) {
            final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
            LayoutInflater inflater = LayoutInflater.from(context);
            View mCustom = inflater.inflate(R.layout.custom_server_selection, null);

            ImageButton backButton = mCustom.findViewById(R.id.custom_Server_back);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customConfirmDialog.dismiss();
                    callback.customCallBack(ResponseVariability.NOVALUESELECTED);
                }
            });
            TableLayout tableLgort = mCustom.findViewById(R.id.serverTableContainer);

            MapServerBean map = new Gson().fromJson(mapServer, MapServerBean.class);
            Iterator it = map.getServerBeanMap().entrySet().iterator();
            while (it.hasNext()) {
                TableRow firstRow = new TableRow(context);
                Map.Entry pair = (Map.Entry) it.next();
                final ServerBean bean = (ServerBean) pair.getValue();
                LayoutInflater inInflater = LayoutInflater.from(context);
                View mSelection = inInflater.inflate(R.layout.server_search_layout, null);
                TextView svrTextDesc = mSelection.findViewById(R.id.materialListId);
                TextView svrTextValue = mSelection.findViewById(R.id.materialListDesc);
                CardView cardConfirm = mSelection.findViewById(R.id.cardMaterialClickZone);

                svrTextDesc.setText(bean.getCommonName());
                svrTextValue.setText(bean.baseServer());
                cardConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customConfirmDialog.dismiss();
                        CommonUtilities.UpdateStoreGSonVariable(SERVERVALUE, new Gson().toJson(bean), context);
                        callback.customCallBack(ResponseVariability.SUCCESSFULL);
                    }
                });
                firstRow.addView(mSelection);
                tableLgort.addView(firstRow);
            }

            ImageButton addServerButton = mCustom.findViewById(R.id.custom_Server_confirm);
            addServerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onServerAdminAdd(parentActivity, new CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            if (ret == ResponseVariability.SUCCESSFULL) {
                                callback.customCallBack(ret);
                            } else if (ret == ResponseVariability.NOVALUESELECTED) {
                                //Do nothing
                            } else {
                                Toast.makeText(context, "Ocurrio un error al procesar la solicitud", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, context);
                }
            });

            customDialog.setView(mCustom);
            customDialog.setCancelable(false);
            customConfirmDialog = customDialog.create();
            customConfirmDialog.show();
        }
    }


    public void onAdminCountedData(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<Integer> callback, @NonNull final Context context, @NonNull final HashMap<String, Integer> map) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_admin_selection, null);
        TextView admin = mCustom.findViewById(R.id.descServerTextShow);
        admin.setText("Valores Actuales");
        ImageButton backButton = mCustom.findViewById(R.id.custom_Server_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                callback.customCallBack(0);
            }
        });
        TableLayout tableLgort = mCustom.findViewById(R.id.serverTableContainer);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            TableRow firstRow = new TableRow(context);
            Map.Entry pair = (Map.Entry) it.next();
            final String keyPair = (String) pair.getKey();
            final Integer keyValue = (Integer) pair.getValue();
            LayoutInflater inInflater = LayoutInflater.from(context);
            View mSelection = inInflater.inflate(R.layout.selection_layout, null);
            TextView materialListId = mSelection.findViewById(R.id.selectionCurrentValue);
            CardView cardSelectionClickZone = mSelection.findViewById(R.id.cardSelectionClickZone);
            materialListId.setText(keyPair + ": " + keyValue);
            cardSelectionClickZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customConfirmDialog.dismiss();
                    callback.customCallBack(0);
                }
            });
            firstRow.addView(mSelection);
            tableLgort.addView(firstRow);
        }
        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();

    }

    public void onAdminPasswordOk(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<Integer> callback, @NonNull final Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_admin_selection, null);
        ImageButton backButton = mCustom.findViewById(R.id.custom_Server_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAdminMenuDialog.dismiss();
                callback.customCallBack(0);
            }
        });
        TableLayout tableLgort = mCustom.findViewById(R.id.serverTableContainer);
        HashMap<Integer, String> map = ResponseVariability.ADMINISTRATOROPERATION;
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            TableRow firstRow = new TableRow(context);
            Map.Entry pair = (Map.Entry) it.next();
            final Integer keyPair = (Integer) pair.getKey();
            final String keyValue = pair.getValue().toString();
            LayoutInflater inInflater = LayoutInflater.from(context);
            View mSelection = inInflater.inflate(R.layout.selection_layout, null);
            TextView materialListId = mSelection.findViewById(R.id.selectionCurrentValue);
            CardView cardSelectionClickZone = mSelection.findViewById(R.id.cardSelectionClickZone);
            materialListId.setText(keyValue);
            cardSelectionClickZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.customCallBack(keyPair);
                }
            });
            firstRow.addView(mSelection);
            tableLgort.addView(firstRow);
        }
        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customAdminMenuDialog = customDialog.create();
        customAdminMenuDialog.show();

    }

    public void onServerAdminAdd(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<Integer> callback, @NonNull final Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);

        final Handler handler = new Handler();
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_server_aggregate, null);
        final EditText urlEditText = mCustom.findViewById(R.id.urlEditText);
        final EditText serverAddPort = mCustom.findViewById(R.id.serverAddPort);
        final EditText formDescriptText = mCustom.findViewById(R.id.formDescriptText);
        final RadioGroup protocolGroup = mCustom.findViewById(R.id.protocolGroup);
        final TextView formErrorValue = mCustom.findViewById(R.id.formErrorValue);
        final ImageButton custom_server_confirm = mCustom.findViewById(R.id.custom_server_confirm);
        ImageButton custom_server_validate = mCustom.findViewById(R.id.custom_server_validate);
        ImageButton custom_Server_back = mCustom.findViewById(R.id.custom_server_back);
        final ServerBean bean = new ServerBean();

        formErrorValue.addTextChangedListener(new TextWatcher() {
            /**
             * This method is empty because is a override of the father class and is not part of the business logic.
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) throws  UnsupportedOperationException {
                // This method is empty because is a override of the father class and is not part of the business logic.
            }

            /**
             * This method is empty because is a override of the father class and is not part of the business logic.
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) throws  UnsupportedOperationException {
                // This method is empty because is a override of the father class and is not part of the business logic.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            formErrorValue.setText("");
                        }
                    }, 3000);
                }
            }
        });


        urlEditText.addTextChangedListener(new TextWatcher() {
            /**
             * This method is empty because is a override of the father class and is not part of the business logic.
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) throws  UnsupportedOperationException {
                // This method is empty because is a override of the father class and is not part of the business logic.
            }

            /**
             * This method is empty because is a override of the father class and is not part of the business logic.
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) throws  UnsupportedOperationException {
                // This method is empty because is a override of the father class and is not part of the business logic.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                custom_server_confirm.setClickable(false);
                custom_server_confirm.setEnabled(false);
                custom_server_confirm.setAlpha(.5f);
            }
        });

        protocolGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                custom_server_confirm.setClickable(false);
                custom_server_confirm.setEnabled(false);
                custom_server_confirm.setAlpha(.5f);
            }
        });

        serverAddPort.addTextChangedListener(new TextWatcher() {
            /**
             * This method is empty because is a override of the father class and is not part of the business logic.
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) throws  UnsupportedOperationException {
                // This method is empty because is a override of the father class and is not part of the business logic.
            }

            /**
             * This method is empty because is a override of the father class and is not part of the business logic.
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) throws  UnsupportedOperationException {
                // This method is empty because is a override of the father class and is not part of the business logic.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                custom_server_confirm.setClickable(false);
                custom_server_confirm.setEnabled(false);
                custom_server_confirm.setAlpha(.5f);
            }
        });

        custom_Server_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAddServerDialog.dismiss();
                callback.customCallBack(ResponseVariability.NOVALUESELECTED);
            }
        });


        custom_server_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setUrl(urlEditText.getText().toString());
                bean.setCommonName(formDescriptText.getText().toString());
                bean.setPort(serverAddPort.getText().toString());

                if (bean.getPort().isEmpty()) {
                    if (bean.getCommonName() != null && !bean.getCommonName().isEmpty()) {
                        if (bean.getUrl() != null && !bean.getUrl().isEmpty()) {
                            switch (protocolGroup.getCheckedRadioButtonId()) {
                                case R.id.radioProt1:
                                    bean.setUrl("http://" + bean.getUrl());
                                    bean.setPort("80");
                                    break;
                                case R.id.radioProt2:
                                    bean.setUrl("https://" + bean.getUrl());
                                    bean.setPort("443");
                                    break;
                            }
                            AbstractResults result = validateInvServer(parentActivity, context, bean.baseServer());
                            if (result.getResultId() == ResponseVariability.SUCCESSFULL) {
                                custom_server_confirm.setClickable(true);
                                custom_server_confirm.setEnabled(true);
                                custom_server_confirm.setAlpha(1f);
                            } else {
                                formErrorValue.setText(result.getResultMsgAbs());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        formErrorValue.setText("");
                                    }
                                }, 3000);
                            }
                        } else {
                            formErrorValue.setText("El campo de URL no puede estar vacio");

                        }
                    } else {
                        formErrorValue.setText("El campo de Descripcion no puede estar vacio");

                    }
                } else if (bean.getPort().matches("\\d+")) {
                    if (bean.getCommonName() != null && !bean.getCommonName().isEmpty()) {
                        if (bean.getUrl() != null && !bean.getUrl().isEmpty()) {
                            switch (protocolGroup.getCheckedRadioButtonId()) {
                                case R.id.radioProt1:
                                    bean.setUrl("http://" + bean.getUrl());
                                    break;
                                case R.id.radioProt2:
                                    bean.setUrl("https://" + bean.getUrl());
                                    break;
                            }
                            AbstractResults result = validateInvServer(parentActivity, context, bean.baseServer());
                            if (result.getResultId() == ResponseVariability.SUCCESSFULL) {
                                custom_server_confirm.setClickable(true);
                                custom_server_confirm.setEnabled(true);
                                custom_server_confirm.setAlpha(1f);
                            } else {
                                formErrorValue.setText(result.getResultMsgAbs());

                            }
                        } else {
                            formErrorValue.setText("El campo de URL no puede estar vacio");

                        }
                    } else {
                        formErrorValue.setText("El campo de Descripcion no puede estar vacio");

                    }
                } else {
                    formErrorValue.setText("El campo de puerto solo puede contener numeros");

                }
            }
        });

        custom_server_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverCurrentMapping = PushGsonVariable(SERVERMAPPING, context);
                String serverCurrentId = PushGsonVariable(CURRENTSERVER, context);
                if (serverCurrentMapping != null) {
                    if (serverCurrentId != null) {
                        //Fill map
                        Gson gson = new Gson();
                        Integer value = gson.fromJson(serverCurrentId, Integer.class);
                        MapServerBean mapBean = gson.fromJson(serverCurrentMapping, MapServerBean.class);
                        mapBean.getServerBeanMap().put(value, bean);
                        value = value + 1;
                        //update Store
                        CommonUtilities.UpdateStoreGSonVariable(SERVERMAPPING, gson.toJson(mapBean), context);
                        CommonUtilities.UpdateStoreGSonVariable(CURRENTSERVER, gson.toJson(value), context);
                        CommonUtilities.UpdateStoreGSonVariable(SERVERVALUE, new Gson().toJson(bean), context);
                        customAddServerDialog.dismiss();
                        callback.customCallBack(ResponseVariability.SUCCESSFULL);
                    } else {
                        formErrorValue.setText("Ocurrio un error al recuperar datos, Contacte administrador.");
                    }
                } else {
                    formErrorValue.setText("Ocurrio un error al recuperar datos, Contacte administrador.");
                }
            }
        });

        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customAddServerDialog = customDialog.create();
        customAddServerDialog.show();
    }


    public AbstractResults validateInvServer(@NonNull final Activity parentActivity, @NonNull final Context context, String baseServer) {
        AbstractResults result = new AbstractResults();
        Request request = new Request();
        try {
            asyncContext = context;
            Response response = new IsValidServer().execute(new Gson().toJson(request), baseServer + VALIDATEUSABLESERVER).get();
            if (response != null) {
                if (response.getAbstractResult() != null) {
                    result = response.getAbstractResult();
                } else {
                    result.setResultId(ResponseVariability.EXCEPTION);
                    result.setResultMsgAbs("Ocurrio un problema al ejecutar la peticion en el servidor!");
                }
            } else {
                result.setResultId(ResponseVariability.EXCEPTION);
                result.setResultMsgAbs("No se puede completar su solicitud, valide la información");
            }
        } catch (ExecutionException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            result.setResultId(ResponseVariability.EXCEPTION);
            result.setResultMsgAbs(e.getMessage());
        } catch (InterruptedException e) {
            loggerAPI(e, context);
            CrashReporter.logException(e);
            result.setResultId(ResponseVariability.EXCEPTION);
            result.setResultMsgAbs(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return result;
    }

    private class IsValidServer extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... values) {
            String toReturn = "";
            StringBuilder sb = new StringBuilder();
            byte[] postData;
            try {
                // MAZ Fix
                if (values[0].contains("\\\\")) {
                    values[0] = values[0].replace("\\\\", "\\");
                }

                String urlParameters = values[0];
                postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connect = new CommonUtilities().CustomHttpsConnection(postData, asyncContext, values);
                try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                    if (CommonUtilities.checkAuthorization(AUTH)) {
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
                loggerAPI(e, asyncContext);
                CommonUtilities.loggerAPI(e, asyncContext);
                CrashReporter.logException(e);
                return null;
            }
            toReturn = sb.toString();
            if (toReturn.isEmpty()) {
                return null;
            } else {
                return new Gson().fromJson(toReturn, Response.class);
            }
        }
    }


    public void executeAdminTask(final Integer adminSwitch, final Activity activity, final Context context) {
        switch (adminSwitch) {
            case 1:
                try {
                    depuration(context, activity);
                } catch (Exception e) {
                    loggerAPI(e, context);
                    CrashReporter.logException(e);
                }
                break;

            default:
                try {
                    depurationAdmin(context, activity);
                } catch (Exception e) {
                    loggerAPI(e, context);
                    CrashReporter.logException(e);
                }
                break;
        }
    }


    public void showPopUpNotes(@NonNull final Activity parentActivity, @Nullable final CustomCallBack<String> callback, @NonNull Context context, @Nullable String oldValue) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.notes_layout_pop_up, null);
        ImageButton confirmButton = mCustom.findViewById(R.id.custom_notes_confirm);
        final EditText noteText = mCustom.findViewById(R.id.id_note_text);
        if (oldValue != null) {
            noteText.setText(oldValue);
        }
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textValue = "";
                if (noteText.getText().toString().isEmpty()) {
                    textValue = null;
                } else {
                    textValue = noteText.getText().toString();
                }
                callback.customCallBack(textValue);
                customConfirmDialog.dismiss();
            }
        });

        ImageButton negativeButton = mCustom.findViewById(R.id.custom_notes_cancel);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textValue = "";
                if (noteText.getText().toString().isEmpty()) {
                    textValue = null;
                } else {
                    textValue = noteText.getText().toString();
                }
                callback.customCallBack(textValue);
                customConfirmDialog.dismiss();
            }
        });
        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }


    public static void CustomPendientesDialog(@NonNull final String dialogTittle, @NonNull final String dialogMessage, @NonNull final Activity parentActivity,
                                              @Nullable final DialogInterface parentDialog, @Nullable final String customConfirmation, @Nullable final String negativeConfirmation,
                                              @Nullable final CustomCallBack<Integer> callback, @Nullable Integer iconId, @NonNull Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_pending_zone, null);
        TextView headerView = mCustom.findViewById(R.id.headerTextViewWarningConfirm);
        headerView.setText(dialogTittle);
        TextView bodyView = mCustom.findViewById(R.id.textViewWarningConfirm);
        bodyView.setText(dialogMessage);
        if (iconId != null) {
            ImageButton displayButton = mCustom.findViewById(R.id.custom_warning_back);
            displayButton.setBackground(context.getDrawable(iconId));
            displayButton.setVisibility(View.VISIBLE);
        }

        ImageButton confirmButton = mCustom.findViewById(R.id.custom_warning_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (parentDialog != null) {
                    parentDialog.dismiss();
                }
                if (callback != null) {
                    callback.customCallBack(ResponseVariability.SUCCESSFULL);
                }
            }
        });


        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }


    public static void CustomWarningDialog(@NonNull final String dialogTittle, @NonNull final String dialMs, @NonNull final Activity parentActivity,
                                           @Nullable final DialogInterface parentDialog, @Nullable final String customConfirmation, @Nullable final String negativeConfirmation,
                                           @Nullable final CustomCallBack<Integer> callback, @Nullable Integer iconId, @NonNull Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_warning_popup, null);
        TextView headerView = mCustom.findViewById(R.id.headerTextViewWarningConfirm);
        headerView.setText(dialogTittle);
        TextView bodyView = mCustom.findViewById(R.id.textViewWarningConfirm);
        bodyView.setText(dialMs);
        if (iconId != null) {
            ImageButton displayButton = mCustom.findViewById(R.id.custom_warning_back);
            displayButton.setBackground(context.getDrawable(iconId));
            displayButton.setVisibility(View.VISIBLE);
        }

        ImageButton confirmButton = mCustom.findViewById(R.id.custom_warning_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (parentDialog != null) {
                    parentDialog.dismiss();
                }
                if (callback != null) {
                    callback.customCallBack(ResponseVariability.SUCCESSFULL);
                }
            }
        });


        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }

    public static void CustomColorWarningDialog(@NonNull final String dialogTittle, @NonNull final String dialogMessage, @NonNull final Activity parentActivity,
                                                @Nullable final DialogInterface parentDialog, @Nullable final String customConfirmation, @Nullable final String negativeConfirmation,
                                                @Nullable final CustomCallBack<Integer> callback, @Nullable Integer iconId, @NonNull Context context, @Nullable String colorId) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_warning_popup_ico, null);
        TextView headerView = mCustom.findViewById(R.id.headerTextViewWarningConfirm);
        if (colorId != null) {
            headerView.setTextColor(Color.parseColor(colorId));
        }
        headerView.setText(dialogTittle);
        TextView bodyView = mCustom.findViewById(R.id.textViewWarningConfirm);
        bodyView.setText(dialogMessage);
        if (iconId != null) {
            ImageButton displayButton = mCustom.findViewById(R.id.custom_warning_back);
            displayButton.setBackground(context.getDrawable(iconId));
            displayButton.setVisibility(View.VISIBLE);
        }

        ImageButton confirmButton = mCustom.findViewById(R.id.custom_warning_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (parentDialog != null) {
                    parentDialog.dismiss();
                }
                if (callback != null) {
                    callback.customCallBack(ResponseVariability.SUCCESSFULL);
                }
            }
        });


        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }

    //CallbackObject Will return allways Successfull = 1 when requested and reply is OK else
    public static void CustomConfirmDialog(@NonNull final String dialogTittle, @NonNull final String dialogMessage, @NonNull final Activity parentActivity,
                                           @Nullable final DialogInterface parentDialog, @Nullable final String customConfirmation, @Nullable final String negativeConfirmation,
                                           @Nullable final CustomCallBack<Integer> callback, @Nullable Integer iconId, @NonNull Context context) {
        final AlertDialog.Builder customDialog = new AlertDialog.Builder(parentActivity);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mCustom = inflater.inflate(R.layout.custom_confirm_pop_up, null);
        TextView headerView = mCustom.findViewById(R.id.headerTextViewCustomConfirm);
        headerView.setText(dialogTittle);
        TextView bodyView = mCustom.findViewById(R.id.textViewCustomConfirm);
        bodyView.setText(dialogMessage);
        if (iconId != null) {
            ImageButton displayButton = mCustom.findViewById(R.id.custom_confirm_back);
            displayButton.setBackground(context.getDrawable(iconId));
            displayButton.setVisibility(View.VISIBLE);
        }

        ImageButton confirmButton = mCustom.findViewById(R.id.custom_confirm_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (parentDialog != null) {
                    parentDialog.dismiss();
                }
                if (callback != null) {
                    callback.customCallBack(ResponseVariability.SUCCESSFULL);
                }
            }
        });

        ImageButton negativeButton = mCustom.findViewById(R.id.custom_confirm_cancel);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customConfirmDialog.dismiss();
                if (callback != null) {
                    callback.customCallBack(ResponseVariability.ERROR);
                }
            }
        });


        customDialog.setView(mCustom);
        customDialog.setCancelable(false);
        customConfirmDialog = customDialog.create();
        customConfirmDialog.show();
    }

    AlertDialog materialSelectionDialog;


    public void requestHumanMaterialSelection(@NonNull final Context context, @NonNull final Activity activity, @NonNull String requestTitle, @NonNull String messageToShow,
                                              @NonNull final CustomCallBack<String> callback, List<MaterialDescrptionBean> materialBeans) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.material_tarimas_pop, null);
        TableLayout tableLgort = mView.findViewById(R.id.tableLayoutMaterialTarima);
        ImageButton btnLgortCancel = mView.findViewById(R.id.MatTar_operation_back);
        ImageButton btnLgortConfirm = mView.findViewById(R.id.MatTar_operation_confirm);
        TextView headerView = mView.findViewById(R.id.headerTextViewMatTar);
        TextView descView = mView.findViewById(R.id.descTextViewMatTar);
        headerView.setText("MATERIAL");
        descView.setText("SELECCIONE MATERIAL:");
        final RadioGroup radioGroup = new RadioGroup(context);
        final HashMap<Integer, MaterialDescrptionBean> mapMaterial = new HashMap<>();
        Integer matId = 1;
        for (MaterialDescrptionBean singleBean : materialBeans) {
            mapMaterial.put(matId, singleBean);
            matId++;
        }
        Iterator it = mapMaterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry map = (Map.Entry) it.next();
            MaterialDescrptionBean singleBean = (MaterialDescrptionBean) map.getValue();
            RadioButton radioMaterial = new RadioButton(context);
            radioMaterial.setId((Integer) map.getKey());
            radioMaterial.setHint(singleBean.getMatnr());
            radioMaterial.setText(singleBean.getMatnr() + "\n" + singleBean.getMaktx());
            radioGroup.addView(radioMaterial);
        }
        tableLgort.addView(radioGroup);
        btnLgortConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = radioGroup.getCheckedRadioButtonId();
                if (id != null && id > 0) {
                    callback.customCallBack(new Gson().toJson(mapMaterial.get(id)));
                    materialSelectionDialog.dismiss();
                } else {
                    CustomConfirmDialog(disclamer, "Se perderan los datos de material", activity, null,
                            null, null, new CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        callback.customCallBack(null);
                                        materialSelectionDialog.dismiss();
                                    }
                                }
                            }, null, context);
                }
            }
        });
        btnLgortCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialSelectionDialog.dismiss();
                callback.customCallBack(null);
            }
        });
        alert.setView(mView);
        alert.setCancelable(false);
        materialSelectionDialog = alert.create();
        materialSelectionDialog.show();
    }

    public void requestHumanTarimasSelection(@NonNull final Context context, @NonNull final Activity activity, @NonNull String requestTitle, @NonNull String messageToShow,
                                             @NonNull final CustomCallBack<String> callback, List<TarimasDescriptionBean> materialBeans) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.material_tarimas_pop, null);
        TableLayout tableLgort = mView.findViewById(R.id.tableLayoutMaterialTarima);
        ImageButton btnLgortCancel = mView.findViewById(R.id.MatTar_operation_back);
        ImageButton btnLgortConfirm = mView.findViewById(R.id.MatTar_operation_confirm);
        TextView headerView = mView.findViewById(R.id.headerTextViewMatTar);
        TextView descView = mView.findViewById(R.id.descTextViewMatTar);
        headerView.setText("TARIMA");
        descView.setText("SELECCIONE TARIMA:");
        final RadioGroup radioGroup = new RadioGroup(context);
        final HashMap<Integer, TarimasDescriptionBean> mapMaterial = new HashMap<>();
        Integer matId = 1;
        for (TarimasDescriptionBean singleBean : materialBeans) {
            mapMaterial.put(matId, singleBean);
            matId++;
        }
        Iterator it = mapMaterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry map = (Map.Entry) it.next();
            TarimasDescriptionBean singleBean = (TarimasDescriptionBean) map.getValue();
            //View tarView = inflater.inflate(R.layout.selection_layout_rad, null);
            //CardView clickZone = tarView.findViewById(R.id.cardSelectionRadClickZone);
            RadioButton radioMaterial = new RadioButton(context);
            radioMaterial.setId((Integer) map.getKey());
            radioMaterial.setHint(singleBean.getVhilm());
            radioMaterial.setText(singleBean.getVhilm() + "\n" + singleBean.getMaktx());
            radioGroup.addView(radioMaterial);
        }
        RadioButton radioMaterial = new RadioButton(context);
        radioMaterial.setId(ResponseVariability.NOSELECTEDTARIMA);
        radioMaterial.setHint("" + ResponseVariability.NOSELECTEDTARIMA);
        radioMaterial.setText("Desestimar Tarima");
        radioGroup.addView(radioMaterial);
        tableLgort.addView(radioGroup);
        btnLgortConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = radioGroup.getCheckedRadioButtonId();
                if (id != null && id != -1) {
                    if (id == NOSELECTEDTARIMA) {
                        callback.customCallBack(ResponseVariability.NOSELECTEDTARIMAS);
                        materialSelectionDialog.dismiss();
                    } else {
                        callback.customCallBack(new Gson().toJson(mapMaterial.get(id)));
                        materialSelectionDialog.dismiss();
                    }
                } else {
                    CustomConfirmDialog(disclamer, "Se perderan los datos de armado", activity, null,
                            null, null, new CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        callback.customCallBack(null);
                                        materialSelectionDialog.dismiss();
                                    }
                                }
                            }, null, context);
                }
            }
        });
        btnLgortCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialSelectionDialog.dismiss();
                callback.customCallBack(ResponseVariability.NOSELECTEDTARIMAS);
            }
        });
        alert.setView(mView);
        alert.setCancelable(false);
        materialSelectionDialog = alert.create();
        materialSelectionDialog.show();
    }


    public void requestHumanTarimasSelectionLegacy(@NonNull final Context context, @NonNull final Activity activity, @NonNull String requestTitle, @NonNull String messageToShow,
                                                   @NonNull final CustomCallBack<String> callback, List<MaterialTarimasBean> materialBeans) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(requestTitle);
        alert.setMessage(messageToShow);
        final RadioGroup radioGroup = new RadioGroup(context);
        final HashMap<Integer, MaterialTarimasBean> mapMaterial = new HashMap<>();
        Integer matId = 1;
        for (MaterialTarimasBean singleBean : materialBeans) {
            mapMaterial.put(matId, singleBean);
            matId++;
        }
        Iterator it = mapMaterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry map = (Map.Entry) it.next();
            MaterialDescrptionBean singleBean = (MaterialDescrptionBean) map.getValue();
            RadioButton radioMaterial = new RadioButton(context);
            radioMaterial.setId((Integer) map.getKey());
            radioMaterial.setHint(singleBean.getMatnr());
            radioMaterial.setText(singleBean.getMatnr() + "\n" + singleBean.getMaktx());
            radioGroup.addView(radioMaterial);
        }
        alert.setPositiveButton("Conf. Material", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface inDialog, int whichButton) {
                Integer id = radioGroup.getCheckedRadioButtonId();
                if (id != null && id > 0) {
                    callback.customCallBack(new Gson().toJson(mapMaterial.get(id)));
                    inDialog.dismiss();
                } else {
                    CustomConfirmDialog(disclamer, "Se perderan los datos de material", activity, null,
                            null, null, new CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        callback.customCallBack(null);
                                        inDialog.dismiss();
                                    }
                                }
                            }, null, context);
                }
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                callback.customCallBack(null);
                dialog.dismiss();
            }
        });
        alert.show();
    }


    AlertDialog calcDialog;
    TextView textCalc;

    public void showCalcCount(final String strTextCalc,
                              final CustomCallBack<String> callback, final Activity parentActivity,
                              final Context parentContext, final DisplayMetrics displayMetrics) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(parentActivity);
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
        View mCalc = layoutInflater.inflate(R.layout.calcpopup, null);
        textCalc = mCalc.findViewById(R.id.calc_Text);
        final TextView currentVal = mCalc.findViewById(R.id.calcCurr);
        final TextView operationVal = mCalc.findViewById(R.id.calcOp);
        if (new BigDecimal(strTextCalc).compareTo(BigDecimal.ZERO) != 0)
            currentVal.setText(strTextCalc);

        Button calcBtn1 = mCalc.findViewById(R.id.calc_1);
        calcBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("1");
            }
        });

        Button calcBtn2 = mCalc.findViewById(R.id.calc_2);
        calcBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("2");

            }
        });


        Button calcBtn3 = mCalc.findViewById(R.id.calc_3);
        calcBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("3");

            }
        });

        Button calcBtn4 = mCalc.findViewById(R.id.calc_4);
        calcBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("4");

            }
        });

        Button calcBtn5 = mCalc.findViewById(R.id.calc_5);
        calcBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("5");
            }
        });

        Button calcBtn6 = mCalc.findViewById(R.id.calc_6);
        calcBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("6");
            }
        });

        Button calcBtn7 = mCalc.findViewById(R.id.calc_7);
        calcBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("7");
            }
        });

        Button calcBtn8 = mCalc.findViewById(R.id.calc_8);
        calcBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("8");

            }
        });

        Button calcBtn9 = mCalc.findViewById(R.id.calc_9);
        calcBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("9");
            }
        });

        Button calcBtn0 = mCalc.findViewById(R.id.calc_0);
        calcBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrentValue("0");
            }
        });

        Button calcBtnDot = mCalc.findViewById(R.id.calc_dot);
        calcBtnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textCalc.getText().toString().equals("0")) {
                    textCalc.setText(".");
                } else {
                    textCalc.setText(textCalc.getText().toString() + ".");
                }

            }
        });

        Button calcBtnBack = mCalc.findViewById(R.id.calc_back);
        calcBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textCalc.getText().toString().length() == 1) {
                    textCalc.setText("0");
                } else if (textCalc.getText().toString().length() >= 2) {
                    textCalc.setText(textCalc.getText().toString().substring(0, textCalc.getText().toString().length() - 1));
                } else {
                    textCalc.setText("0");
                }
            }
        });

        Button calcBtnClear = mCalc.findViewById(R.id.calc_clear);
        calcBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textCalc.getText() != null && textCalc.getText().toString().equals("0")) {
                    operationVal.setText("");
                } else {
                    textCalc.setText("0");
                }
            }
        });


        Button calcPlus = mCalc.findViewById(R.id.calc_plus);
        calcPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastOf = 0;
                if (!textCalc.getText().toString().equals("0")) {
                    if (operationVal.getText().toString().isEmpty()) {
                        operationVal.setText(textCalc.getText().toString() + "+");
                        textCalc.setText("0");
                    } else {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            String operationValue = operationVal.getText().toString().substring(0, lastOf);
                            String operator = operationVal.getText().toString().substring(lastOf);
                            operationVal.setText(new CommonUtilities().applyCalcOperation(operationValue, operator, textCalc.getText().toString()) + "+");
                            textCalc.setText("0");
                        } else {
                            //DO NOTHING
                        }
                    }
                } else {
                    if (!operationVal.getText().toString().isEmpty()) {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            operationVal.setText(operationVal.getText().toString().substring(0, lastOf) + "+");
                            textCalc.setText("0");
                        }
                    } else {
                        //DO NOTHING
                    }
                }
            }
        });


        Button calcMinus = mCalc.findViewById(R.id.calc_minus);
        calcMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastOf = 0;
                if (!textCalc.getText().toString().equals("0")) {
                    if (operationVal.getText().toString().isEmpty()) {
                        operationVal.setText(textCalc.getText().toString() + "-");
                        textCalc.setText("0");
                    } else {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            String operationValue = operationVal.getText().toString().substring(0, lastOf);
                            String operator = operationVal.getText().toString().substring(lastOf);
                            operationVal.setText(new CommonUtilities().applyCalcOperation(operationValue, operator, textCalc.getText().toString()) + "-");
                            textCalc.setText("0");
                        } else {
                            //DO NOTHING
                        }
                    }
                } else {
                    if (!operationVal.getText().toString().isEmpty()) {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            operationVal.setText(operationVal.getText().toString().substring(0, lastOf) + "-");
                            textCalc.setText("0");
                        }
                    } else {
                        //DO NOTHING
                    }
                }
            }
        });


        Button calcMulti = mCalc.findViewById(R.id.calc_multi);
        calcMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastOf = 0;
                if (!textCalc.getText().toString().equals("0")) {
                    if (operationVal.getText().toString().isEmpty()) {
                        operationVal.setText(textCalc.getText().toString() + "X");
                        textCalc.setText("0");
                    } else {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            String operationValue = operationVal.getText().toString().substring(0, lastOf);
                            String operator = operationVal.getText().toString().substring(lastOf);
                            operationVal.setText(new CommonUtilities().applyCalcOperation(operationValue, operator, textCalc.getText().toString()) + "X");
                            textCalc.setText("0");
                        } else {
                            //DO NOTHING
                        }
                    }
                } else {
                    if (!operationVal.getText().toString().isEmpty()) {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            operationVal.setText(operationVal.getText().toString().substring(0, lastOf) + "X");
                            textCalc.setText("0");
                        }
                    } else {
                        //DO NOTHING
                    }
                }
            }
        });


        Button calcDivide = mCalc.findViewById(R.id.calc_divide);
        calcDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastOf = 0;
                if (!textCalc.getText().toString().equals("0")) {
                    if (operationVal.getText().toString().isEmpty()) {
                        operationVal.setText(textCalc.getText().toString() + "/");
                        textCalc.setText("0");
                    } else {
                        lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            String operationValue = operationVal.getText().toString().substring(0, lastOf);
                            String operator = operationVal.getText().toString().substring(lastOf);
                            operationVal.setText(new CommonUtilities().applyCalcOperation(operationValue, operator, textCalc.getText().toString()) + "/");
                            textCalc.setText("0");
                        } else {
                            //DO NOTHING
                        }
                    }
                } else {
                    if (!operationVal.getText().toString().isEmpty()) {
                        lastOf = getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            operationVal.setText(operationVal.getText().toString().substring(0, lastOf) + "/");
                            textCalc.setText("0");
                        }
                    } else {
                        //DO NOTHING
                    }
                }
            }
        });

        Button calcEquals = mCalc.findViewById(R.id.calc_equals);
        calcEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastOf = 0;
                if (!textCalc.getText().toString().equals("0")) {
                    if (!operationVal.getText().toString().isEmpty()) {
                        lastOf = getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            String operationValue = operationVal.getText().toString().substring(0, lastOf);
                            String operator = operationVal.getText().toString().substring(lastOf);
                            textCalc.setText(new CommonUtilities().applyCalcOperation(operationValue, operator, textCalc.getText().toString()));
                            operationVal.setText("");
                        } else {
                            //DO NOTHING
                        }
                    }
                } else {
                    if (!operationVal.getText().toString().isEmpty()) {
                        lastOf = getLastIndexOfOperation(operationVal.getText().toString());
                        if (lastOf != ResponseVariability.LASTNONINDEX) {
                            textCalc.setText(operationVal.getText().toString().substring(0, lastOf));
                            operationVal.setText("");
                        }
                    } else {
                        //DO NOTHING
                    }
                }
            }
        });


        CircleButton calcCorrect = mCalc.findViewById(R.id.calcCorrect);
        calcCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!operationVal.getText().toString().isEmpty() && textCalc.getText().toString().equals("0")) {
                    CustomConfirmDialog(disclamer, "Operacion Pendiente \n Si continua se tomara el valor previo a la operación",
                            parentActivity, calcDialog, null, null, new CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        String toReturn = operationVal.getText().toString().substring(0,
                                                getLastIndexOfOperation(operationVal.getText().toString()));
                                        callback.customCallBack(toReturn);
                                        calcDialog.dismiss();
                                    }
                                }
                            }, null, parentContext);
                } else if ((textCalc.getText().toString().equals("0") && !currentVal.getText().toString().equals("0"))) {
                    CustomConfirmDialog(disclamer, "Actualmente se tiene un Valor de: " + currentVal.getText().toString() +
                                    "\n Se remplazara por: 0 \n ¿Desea Continuar...?", parentActivity, calcDialog, null,
                            null, new CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        callback.customCallBack("0");
                                        calcDialog.dismiss();
                                    }
                                }
                            }, null, parentContext);
                } else if (!operationVal.getText().toString().isEmpty() && !textCalc.getText().toString().equals("0")) {
                    int lastOf = new CommonUtilities().getLastIndexOfOperation(operationVal.getText().toString());
                    if (lastOf != ResponseVariability.LASTNONINDEX) {
                        String operationValue = operationVal.getText().toString().substring(0, lastOf);
                        String operator = operationVal.getText().toString().substring(lastOf);
                        final String resultValue = new CommonUtilities().applyCalcOperation(operationValue, operator, textCalc.getText().toString());
                        CustomConfirmDialog(disclamer, "Tiene una operacion pendiente, \n si continua concluira la operacion pendiente. " +
                                "\n Resultado: " + resultValue, parentActivity, calcDialog, null, null, new CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                if (ret == ResponseVariability.SUCCESSFULL) {
                                    callback.customCallBack(resultValue);
                                    calcDialog.dismiss();
                                }
                            }
                        }, null, parentContext);
                    }
                } else if (new BigDecimal(textCalc.getText().toString()).compareTo(BigDecimal.ZERO)  < 0) {
                    CustomConfirmDialog(disclamer, "Tiene un valor negativo en el resultado. \n Desea Continuar...",
                            parentActivity, calcDialog, null, null, new CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        callback.customCallBack(textCalc.getText().toString());
                                        calcDialog.dismiss();
                                    }
                                }
                            }, null, parentContext);
                } else if (operationVal.getText().toString().isEmpty() &&
                        new BigDecimal(textCalc.getText().toString()).compareTo(BigDecimal.ZERO) > 0 &&
                        !currentVal.getText().toString().equals(textCalc.getText().toString())) {
                    callback.customCallBack(textCalc.getText().toString());
                    calcDialog.dismiss();
                } else {
                    callback.customCallBack(currentVal.getText().toString());
                    calcDialog.dismiss();
                }
            }
        });

        CircleButton calcExit = mCalc.findViewById(R.id.calcExit);
        calcExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder insideDialogBuilder = new AlertDialog.Builder(parentActivity);
                insideDialogBuilder.setTitle(disclamer);
                insideDialogBuilder.setMessage("¡Se perderan los datos registrados!");
                insideDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogConfirm, int which) {
                        dialogConfirm.dismiss();
                        calcDialog.dismiss();
                        if (new BigDecimal(strTextCalc).compareTo(BigDecimal.ZERO) > 0) {
                            callback.customCallBack(strTextCalc);
                        } else {
                            callback.customCallBack(null);
                        }
                    }
                });
                insideDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogConfirm, int which) {
                        dialogConfirm.dismiss();
                    }
                });
                insideDialogBuilder.create().show();
            }
        });


        dialogBuilder.setView(mCalc);
        dialogBuilder.setCancelable(false);
        calcDialog = dialogBuilder.create();
        calcDialog.show();

    }

    DecimalFormat currency = new DecimalFormat("###,###,###.###");

    public void changeCurrentValue(String usedValue) {
        if (textCalc.getText().length() < 12) {
            if (textCalc.getText().toString().equals("0")) {
                textCalc.setText(usedValue);
            } else {
                textCalc.setText(textCalc.getText().toString() + usedValue);
            }
        }
    }

    public String formatNumericString(String numericValue) {
        String formatValue = "";
        if (!numericValue.contains(".")) {
            formatValue = String.format(numericValue, "000,000,000");
        } else {
            formatValue = String.format(numericValue, "000,000,000.000");
        }

        return formatValue;
    }

    public Integer getLastIndexOfOperation(@NonNull String value) {
        int lastIndex = 0;
        if ((lastIndex = value.lastIndexOf("+")) != -1) {
            return lastIndex;
        } else if ((lastIndex = value.lastIndexOf("-")) != -1) {
            return lastIndex;
        } else if ((lastIndex = value.lastIndexOf("X")) != -1) {
            return lastIndex;
        } else if ((lastIndex = value.lastIndexOf("/")) != -1) {
            return lastIndex;
        } else {
            return ResponseVariability.LASTNONINDEX;
        }
    }

    public String applyCalcOperation(@NonNull String operationValue, @NonNull String
            operator, @NonNull String calcTextValue) {
        BigDecimal bOperation = new BigDecimal(operationValue);
        BigDecimal bCalcText = new BigDecimal(calcTextValue);
        if (operator.equals("+")) {
            return String.valueOf(bOperation.add(bCalcText));
        } else if (operator.equals("-")) {
            return String.valueOf(bOperation.subtract(bCalcText));
        } else if (operator.equals("X")) {
            return String.valueOf(bOperation.multiply(bCalcText));
        } else if (operator.equals("/")) {
            return String.valueOf(bOperation.divide(bCalcText, 3, RoundingMode.HALF_UP));
        } else {
            return ResponseVariability.S_ERROR;
        }

    }

    public static boolean checkAuthorization(String token) {
        return token.equals(AUTH);
    }
}
