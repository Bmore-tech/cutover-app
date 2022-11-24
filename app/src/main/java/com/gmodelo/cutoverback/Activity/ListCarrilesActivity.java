package com.gmodelo.cutoverback.Activity;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.PREVIEWVALUES;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDROUTE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.Adapters.CarrilListAdapter;
import com.gmodelo.cutoverback.Activity.R;
import com.gmodelo.cutoverback.StoredBeans.RouteStoredBean;
import com.gmodelo.cutoverback.Utilities.CarrilesListBean;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.beans.RouteUserBean;
import com.gmodelo.cutoverback.beans.RouteUserPositionBean;
import com.gmodelo.cutoverback.beans.ZoneUserBean;
import com.gmodelo.cutoverback.beans.ZoneUserPositionsBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListCarrilesActivity extends AppCompatActivity {

    View decorView;
    Context context;
    Activity activity;
    Gson gson;
    ImageButton carrilesBackBtn;
    RouteUserBean routeUser;
    RouteStoredBean prevValues;
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    ListView carrilList;
    TextView areaText;
    ImageButton searchCarrilBtn;
    EditText busquedaCarril;
    RouteUserPositionBean rup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_carriles);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        context = this;
        activity = this;
        gson = new Gson();
        areaText = findViewById(R.id.carrilesListAreaTxt);
        busquedaCarril = findViewById(R.id.busquedaCarril);
        progressBarHolder = findViewById(R.id.carrilesListProg);
        carrilList = findViewById(R.id.carrilList);
        carrilesBackBtn = findViewById(R.id.carriles_operation_back);
        searchCarrilBtn = findViewById(R.id.searchCarrilBtn);
        carrilesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateLoading();
                startActivity(new Intent(context, RouteActivity.class));
            }
        });

        String routeUserS = CommonUtilities.PushGsonVariable(STOREDROUTE, context);
        String prevValuesS = CommonUtilities.PushGsonVariable(PREVIEWVALUES, context);
        routeUser = gson.fromJson(routeUserS, RouteUserBean.class);
        prevValues = gson.fromJson(prevValuesS, RouteStoredBean.class);
        rup = routeUser.getPositions().get(prevValues.getiPosRoute());
        int position = 0;

        for (ZoneUserPositionsBean zup : rup.getZone().getPositionsB()) {
            zup.setSecuencia(position);
            position++;
            Log.i("Zup", zup.toString());
        }

        fillLgpla(rup);




        // Botón de búsqueda
        searchCarrilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String keyWord = busquedaCarril.getText().toString();
                    RouteUserPositionBean rupAux = new RouteUserPositionBean();
                    rupAux.setZone(new ZoneUserBean());
                    rupAux.getZone().setPositionsB(new ArrayList<ZoneUserPositionsBean>());
                    rupAux.getZone().getPositionsB().addAll(rup.getZone().getPositionsB());
                    List<ZoneUserPositionsBean> positionsB = new ArrayList<>();

                    // Busqeuda de  carrile en la lista
                    if (!keyWord.isEmpty()) {
                        for (ZoneUserPositionsBean zup : rupAux.getZone().getPositionsB()) {
                            Log.i("Zup", zup.toString());
                            if(zup.getLgpla().toLowerCase().contains(keyWord.toLowerCase())) {
                                positionsB.add(zup);
                            }
                        }

                        rupAux.getZone().setPositionsB(positionsB);
                        fillLgpla(rupAux);
                    } else {
                        fillLgpla(rup);
                    }
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                }
            }
        });

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void fillLgpla( RouteUserPositionBean routeOper) {
        // routeOper = routeUser.getPositions().get(prevValues.getiPosRoute());
        areaText.setText(routeOper.getZone().getZdesc());
        areaText.setTooltipText(routeOper.getZone().getZdesc());
        List<CarrilesListBean> carrilesList = new ArrayList<>();
        //int position = 0;

        for (ZoneUserPositionsBean beanZone : routeOper.getZone().getPositionsB()) {
            CarrilesListBean beanCarril = new CarrilesListBean();
            beanCarril.setLgplaPosition(beanZone.getSecuencia());
            // position++;
            beanCarril.setLgpla(beanZone.getLgpla());
            if (beanZone.getIdDone()) {
                beanCarril.setLeftIcon(R.drawable.ic_check_white_36dp);
            } else {
                beanCarril.setLeftIcon(R.drawable.ic_exclamation_white_36dp);
            }
            carrilesList.add(beanCarril);
        }
        final CarrilListAdapter carrilListAdapter = new CarrilListAdapter(context, R.layout.carriles_linear_layout, carrilesList);
        carrilList.setAdapter(carrilListAdapter);
        carrilList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarrilesListBean bean = (CarrilesListBean) carrilList.getItemAtPosition(i);
                Log.i("Selected Bean", bean.toString());
                prevValues.setiPosLgpla(bean.getLgplaPosition());
                CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(prevValues), context);
                startActivity(new Intent(activity, RouteActivity.class));

            }
        });
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
}