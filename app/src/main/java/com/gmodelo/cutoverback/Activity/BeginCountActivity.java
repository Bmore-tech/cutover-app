package com.gmodelo.cutoverback.Activity;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SAPSPECIALCOUNT;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDLOGIN;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDROUTE;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TASKCOMPLETED;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.Activity.R;
import com.gmodelo.cutoverback.StoredBeans.LoginStored;
import com.gmodelo.cutoverback.StoredBeans.SpecialSapCountBean;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.beans.RouteUserBean;
import com.gmodelo.cutoverback.beans.TaskCompleted;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeginCountActivity extends AppCompatActivity {


    View decorView;
    TextView sociedad, centro, fechaDescarga, fechaInicio, tipoInventario, tipoConteo, usuario, idtaskTypetxt;
    Context context;
    Activity activity;
    Gson gson;
    LoginStored storedLogin;
    RouteUserBean userRoute;
    ImageButton bgnCountActv;
    ProgressBar progressBar;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    FrameLayout progressBarHolder;
    TaskCompleted isCompleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_count);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        gson = new Gson();
        activity = this;
        context = this;
        sociedad = findViewById(R.id.sociedadTxt);
        centro = findViewById(R.id.werksTxt);
        fechaInicio = findViewById(R.id.dateBeginCounttxt);
        tipoInventario = findViewById(R.id.invTypetxt);
        tipoConteo = findViewById(R.id.opeationTypetxt);
        usuario = findViewById(R.id.userInfotxt);
        idtaskTypetxt = findViewById(R.id.idtaskTypetxt);
        bgnCountActv = findViewById(R.id.beginCountBtn);
        bgnCountActv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCompleted = new TaskCompleted();
                isCompleted.setTaskInitiated(true);
                userRoute.setDateIni(new Date().getTime());
                CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(userRoute), context);
                CommonUtilities.UpdateStoreGSonVariable(TASKCOMPLETED, gson.toJson(isCompleted), context);
                startActivity(new Intent(context, RouteActivity.class));
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss");

        String loginString = CommonUtilities.PushGsonVariable(STOREDLOGIN, context);
        if (loginString != null) {
            Log.i("Login Token", loginString);
            storedLogin = gson.fromJson(loginString, LoginStored.class);
            String route = CommonUtilities.PushGsonVariable(STOREDROUTE, context);
            if (route != null) {
                String isCompletedString = CommonUtilities.PushGsonVariable(TASKCOMPLETED, context);
                if (isCompletedString != null) {
                    isCompleted = gson.fromJson(isCompletedString, TaskCompleted.class);
                    if (isCompleted.isTaskInitiated()) {
                        startActivity(new Intent(context, RouteActivity.class));
                    }
                } else {
                    userRoute = gson.fromJson(route, RouteUserBean.class);
                    Log.e("tipod documento", userRoute.getType());
                    sociedad.setText(userRoute.getBdesc());
                    centro.setText(userRoute.getWdesc());
                    fechaInicio.setText(sdf.format(new Date()));

                    // Validación de tipo de conteo
                    if (userRoute.getType().equals("1"))
                        tipoInventario.setText("Cíclico");
                    if (userRoute.getType().equals("2"))
                        tipoInventario.setText("Mensual");
                    if (userRoute.getType().equals("3"))
                        tipoInventario.setText("Especial");
                    if (userRoute.getType().equals("4"))
                        tipoInventario.setText("Frescura");

                    // Agregado Id Task
                    idtaskTypetxt.setText(userRoute.getTaskId());
                    Log.e("userRoute:", userRoute.toString());
                    if (userRoute.getSapSpecial()) {
                        tipoInventario.setText("Conteo Especial Material");
                        SpecialSapCountBean sapBean = new SpecialSapCountBean();
                        sapBean.setSpecial(true);
                        CommonUtilities.UpdateStoreGSonVariable(SAPSPECIALCOUNT, gson.toJson(sapBean), context);
                    }else{
                        try{
                            CommonUtilities.PopGsonVariable(SAPSPECIALCOUNT,context);
                        }catch(Exception e){
                            CrashReporter.logException(e);
                        }

                    }
                    usuario.setText(storedLogin.getLoginBean().getLsObjectLB().getGenInf().getName() + " " + storedLogin.getLoginBean().getLsObjectLB().getGenInf().getLastName());
                }
            } else {
                CommonUtilities.cancelSession(context, activity);
            }
        } else {
            CommonUtilities.cancelSession(context, activity);
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
    }
}