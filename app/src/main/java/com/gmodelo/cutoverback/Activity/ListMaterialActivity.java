package com.gmodelo.cutoverback.Activity;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.PREVIEWVALUES;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDROUTE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.StoredBeans.RouteStoredBean;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.beans.LgplaValuesBean;
import com.gmodelo.cutoverback.beans.RouteUserBean;
import com.gmodelo.cutoverback.beans.RouteUserPositionBean;
import com.gmodelo.cutoverback.beans.ZoneUserPositionsBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListMaterialActivity extends AppCompatActivity {

    View decorView;
    Gson gson;
    Context context;
    Activity activity;
    TextView areatext, carriltext;
    ImageButton backMaterial;
    RouteUserBean routeUser;
    RouteStoredBean prevValues;
    TableLayout materialListTable;
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_material);


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
        progressBarHolder = findViewById(R.id.materialListProg);
        materialListTable = findViewById(R.id.material_table_list);
        areatext = findViewById(R.id.materialListAreaTxt);
        carriltext = findViewById(R.id.materialListCarrilTxt);
        backMaterial = findViewById(R.id.material_operation_back);
        backMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateLoading();
                startActivity(new Intent(context, RouteActivity.class));
            }
        });
        gson = new Gson();
        String routeUserS = CommonUtilities.PushGsonVariable(STOREDROUTE, context);
        String prevValuesS = CommonUtilities.PushGsonVariable(PREVIEWVALUES, context);
        routeUser = gson.fromJson(routeUserS, RouteUserBean.class);
        prevValues = gson.fromJson(prevValuesS, RouteStoredBean.class);
        initiateFill();
    }


    public void createDinamicMaterialListTable(final String keyMap, final LgplaValuesBean lgplaValuesBean) {
        TableRow supRow = new TableRow(context);
        ImageView leftIcon = new ImageView(context);
        RelativeLayout inRelativeLayout = new RelativeLayout(context);
        TableLayout inTableLayout = new TableLayout(context);
        if (lgplaValuesBean.getTotalConverted() != null) {
            leftIcon.setImageResource(R.drawable.ic_check_white_36dp);
            leftIcon.setEnabled(false);
        } else {
            leftIcon.setImageResource(R.drawable.ic_exclamation_white_36dp);
            leftIcon.setEnabled(true);
            inRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initiateLoading();
                    startActivity(new Intent(activity, RouteActivity.class).putExtra("MaterialDescription", gson.toJson(new MaterialDescrptionBean(lgplaValuesBean.getMatnr(), lgplaValuesBean.getMatkx()))));
                }
            });
            leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initiateLoading();
                    startActivity(new Intent(activity, RouteActivity.class).putExtra("MaterialDescription", gson.toJson(new MaterialDescrptionBean(lgplaValuesBean.getMatnr(), lgplaValuesBean.getMatkx()))));
                }
            });
        }
        supRow.addView(leftIcon, 0);

        if (isFromConsole) {
            if (!lgplaValuesBean.isLocked()) {
                supRow.setBackgroundColor(Color.parseColor("#ffb653"));
            }
        }
        TableRow inTableRelRow = new TableRow(context);
        TextView matnrTextView = new TextView(context);
        matnrTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        matnrTextView.setTypeface(matnrTextView.getTypeface(), Typeface.BOLD);
        matnrTextView.setText(lgplaValuesBean.getMatnr());
        inTableRelRow.addView(matnrTextView);
        TableRow inTableRelRow2 = new TableRow(context);
        TextView maktxrTextView = new TextView(context);
        maktxrTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        maktxrTextView.setText(lgplaValuesBean.getMatkx().length() >= 20 ? lgplaValuesBean.getMatkx().substring(0, 20) : lgplaValuesBean.getMatkx());
        maktxrTextView.setTypeface(maktxrTextView.getTypeface(), Typeface.BOLD);
        inTableRelRow2.addView(maktxrTextView);
        inTableLayout.addView(inTableRelRow);
        inTableLayout.addView(inTableRelRow2);
        if (lgplaValuesBean.getSec() != null) {
            TableRow inTableRelRow3 = new TableRow(context);
            TextView secTextView = new TextView(context);
            secTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            secTextView.setText("Sec: " + lgplaValuesBean.getSec());
            inTableRelRow3.addView(secTextView);
            inTableLayout.addView(inTableRelRow3);
        }

        inRelativeLayout.addView(inTableLayout);
        supRow.addView(inRelativeLayout, 1);
        ImageView rightIcon = new ImageView(context);
        rightIcon.setImageResource(R.drawable.ic_trash_can_outline_white_36dp);
        rightIcon.setEnabled(true);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilities.CustomConfirmDialog("Advertencia...", "Se eliminara ese material de la lista... \n ¿Desea continuar?",
                        activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                if (ret == ResponseVariability.SUCCESSFULL) {
                                    if (!lgplaValuesBean.isLocked()) {
                                        removeFromMapTheKey(keyMap);
                                    } else {
                                        removeValuesFromMap(keyMap);
                                    }
                                }
                            }
                        }, R.drawable.ic_exclamation_white_24dp, context);
            }
        });
        if (lgplaValuesBean.isLocked()) {
            if (lgplaValuesBean.getTotalConverted() != null) {
                rightIcon.setEnabled(true);
            } else {
                rightIcon.setEnabled(false);
                rightIcon.setAlpha(.5f);
            }
        }

        supRow.addView(rightIcon, 2);
        materialListTable.addView(supRow);
    }


    public void removeFromMapTheKey(String mapKey) {
        initiateLoading();
        RouteUserPositionBean routeOper = routeUser.getPositions().get(prevValues.getiPosRoute());
        ZoneUserPositionsBean zoneOper = null;
        String material = "";
        if (prevValues.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosLgpla());
            LinkedHashMap<String, LgplaValuesBean> popMap = zoneOper.getLgplaValues();
            material = popMap.get(mapKey).getMatnr();
            popMap.remove(mapKey);
            routeUser.getPositions().get(prevValues.getiPosRoute()).getZone().getPositionsB().get(prevValues.getiPosLgpla()).setLgplaValues(popMap);
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            reEstructureMap(material);
            startActivity(new Intent(activity, ListMaterialActivity.class));
        } else {
            //Toast.makeText(context, "Ocurrio un error el tratar de eliminar la información", Toast.LENGTH_LONG).show();
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosZone());
            LinkedHashMap<String, LgplaValuesBean> popMap = zoneOper.getLgplaValues();
            material = popMap.get(mapKey).getMatnr();
            popMap.remove(mapKey);
            routeUser.getPositions().get(prevValues.getiPosRoute()).getZone().getPositionsB().get(prevValues.getiPosZone()).setLgplaValues(popMap);
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            reEstructureMap(material);
            startActivity(new Intent(activity, ListMaterialActivity.class));
        }
    }

    public void removeValuesFromMap(String mapKey) {
        initiateLoading();
        RouteUserPositionBean routeOper = routeUser.getPositions().get(prevValues.getiPosRoute());
        ZoneUserPositionsBean zoneOper = null;

        //Case when a lane is selected, not from de the current value.
        if (prevValues.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosLgpla());
            LinkedHashMap<String, LgplaValuesBean> popMap = zoneOper.getLgplaValues();
            LgplaValuesBean prevBean = popMap.get(mapKey);
            LgplaValuesBean newBean = new LgplaValuesBean();
            newBean.setLocked(prevBean.isLocked());
            newBean.setMatkx(prevBean.getMatkx());
            newBean.setMatnr(prevBean.getMatnr());
            newBean.setSec(prevBean.getSec());
            popMap.put(mapKey, newBean);
            routeUser.getPositions().get(prevValues.getiPosRoute()).getZone().getPositionsB().get(prevValues.getiPosLgpla()).setLgplaValues(popMap);
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            reEstructureMap(newBean.getMatnr());
            startActivity(new Intent(activity, ListMaterialActivity.class));

        } else {
            //Case when is selected,  from de the current value.
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosZone());
            LinkedHashMap<String, LgplaValuesBean> popMap = zoneOper.getLgplaValues();
            LgplaValuesBean prevBean = popMap.get(mapKey);
            LgplaValuesBean newBean = new LgplaValuesBean();
            newBean.setLocked(prevBean.isLocked());
            newBean.setMatkx(prevBean.getMatkx());
            newBean.setMatnr(prevBean.getMatnr());
            newBean.setSec(prevBean.getSec());
            popMap.put(mapKey, newBean);
            routeUser.getPositions().get(prevValues.getiPosRoute()).getZone().getPositionsB().get(prevValues.getiPosZone()).setLgplaValues(popMap);
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            reEstructureMap(newBean.getMatnr());
            startActivity(new Intent(activity, ListMaterialActivity.class));
        }
    }

    boolean isFromConsole = false;

    public void reEstructureMap(String material) {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(prevValues.getiPosRoute());
        ZoneUserPositionsBean zoneOper = null;
        Integer sec = 1;
        Integer toUse = null;
        if (prevValues.getiPosLgpla() != null) {
            toUse = prevValues.getiPosLgpla();
        } else {
            toUse = prevValues.getiPosZone();
        }
        zoneOper = routeOper.getZone().getPositionsB().get(toUse);
        HashMap<String, LgplaValuesBean> reMap = zoneOper.getLgplaValues();
        if (reMap != null && !reMap.isEmpty()) {
            Iterator it = reMap.entrySet().iterator();
            LinkedHashMap<String, LgplaValuesBean> pivotMap = new LinkedHashMap<>();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String keyMap = pair.getKey().toString();
                LgplaValuesBean keyValue = (LgplaValuesBean) pair.getValue();
                if (keyValue.getMatnr().equals(material)) {
                    if (keyMap.contains("_")) {
                        String pivValue = keyMap.substring(0, keyMap.indexOf("_"));
                        pivValue += "_" + sec;
                        keyValue.setSec(String.valueOf(sec));
                        pivotMap.put(pivValue, keyValue);
                    } else {
                        keyValue.setSec(String.valueOf(sec));
                        pivotMap.put(keyMap, keyValue);
                    }
                    sec++;
                } else {
                    pivotMap.put(keyMap, keyValue);
                }
            }
            routeUser.getPositions().get(prevValues.getiPosRoute()).getZone().getPositionsB().get(toUse).setLgplaValues(pivotMap);
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
        }

    }

    public void initiateFill() {
        identifyConsole();
        RouteUserPositionBean routeOper = routeUser.getPositions().get(prevValues.getiPosRoute());
        ZoneUserPositionsBean zoneOper = null;
        if (prevValues.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosLgpla());
        } else {
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosZone());
        }
        areatext.setText(routeOper.getZone().getZdesc());
        areatext.setTooltipText(routeOper.getZone().getZdesc());
        carriltext.setText(zoneOper.getLgpla());
        Iterator it = zoneOper.getLgplaValues().entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry lgplaMap = (Map.Entry) it.next();
            LgplaValuesBean lgplaValuesBean = (LgplaValuesBean) lgplaMap.getValue();
            createDinamicMaterialListTable(lgplaMap.getKey().toString(), lgplaValuesBean);
        }
    }

    public void identifyConsole() {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(prevValues.getiPosRoute());
        ZoneUserPositionsBean zoneOper = null;
        if (prevValues.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosLgpla());
        } else {
            zoneOper = routeOper.getZone().getPositionsB().get(prevValues.getiPosZone());
        }
        areatext.setText(routeOper.getZone().getZdesc());
        carriltext.setText(zoneOper.getLgpla());
        Iterator it = zoneOper.getLgplaValues().entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry lgplaMap = (Map.Entry) it.next();
            LgplaValuesBean lgplaValuesBean = (LgplaValuesBean) lgplaMap.getValue();
            if (lgplaValuesBean.isLocked()) {
                isFromConsole = true;
            }
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
}