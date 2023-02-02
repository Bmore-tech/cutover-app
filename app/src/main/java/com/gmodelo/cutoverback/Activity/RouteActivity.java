package com.gmodelo.cutoverback.Activity;

import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.LASTCOUNTEDLGPLA;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.NOTTARIMA;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.NOTVALUATION;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.PREVIEWVALUES;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.SAPSPECIALCOUNT;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDLOGIN;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.STOREDROUTE;
import static com.gmodelo.cutoverback.CustomObjects.CommunicationObjects.TASKCOMPLETED;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.CustomObjects.AbstractResults;
import com.gmodelo.cutoverback.CustomObjects.CommunicationObjects;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DaoBeans.TarimasDescriptionBean;
import com.gmodelo.cutoverback.DataAccess.InstanceOfDB;
import com.gmodelo.cutoverback.StoredBeans.LoginStored;
import com.gmodelo.cutoverback.StoredBeans.RouteStoredBean;
import com.gmodelo.cutoverback.StoredBeans.SpecialSapCountBean;
import com.gmodelo.cutoverback.StoredBeans.ZoneStoredBean;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.Views.RouteViewModel;
import com.gmodelo.cutoverback.beans.E_ClassVal_SapEntity;
import com.gmodelo.cutoverback.beans.LastCountedLgpla;
import com.gmodelo.cutoverback.beans.LgplaValuesBean;
import com.gmodelo.cutoverback.beans.MobileMaterialBean;
import com.gmodelo.cutoverback.beans.RouteUserBean;
import com.gmodelo.cutoverback.beans.RouteUserPositionBean;
import com.gmodelo.cutoverback.beans.TaskCompleted;
import com.gmodelo.cutoverback.beans.ZIACST_I360_OBJECTDATA_SapEntity;
import com.gmodelo.cutoverback.beans.ZoneUserPositionsBean;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RouteActivity extends AppCompatActivity {

    View decorView;
    Gson gson;
    Context context;
    Activity activity;
    TextView camSpin, cajSpin, pzSpin, classSpin, ptSpin;
    ImageButton idCajSpinnerBtn, idCamSpinnerBtn, idPzSpinnerBtn;
    AutoCompleteTextView material, idLoteTxt;
    TextView descripcionText;
    TextView cantidadTarima, cantidadCama, cantidadCaja, cantidadTotalTarima, cantidadTotalCama, cantidadTotalCaja, cantidadTotal, cantidadTotalPz, secuencia, spinAlmacen, spinZone;
    TextView carrilText, camaInfoLabel, secuenciInfoLbl, idUnitBoxTblLbl, idTopBoxTblLbl, idUnitEaTblLbl;
    TextView textProduction, textPrdLbl;
    Button btnfnCarril, btnfnMaterial, btnfnZona, btnfnAlmacen, btnMatnr;
    TableRow tableTarimaRow, tableCamaRow, tablePzRow;
    TableLayout tableQuanLayout;
    LoginStored storedLogin;
    RouteUserBean routeUser;
    RouteStoredBean routeStoredBean;
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    RouteViewModel routeModel;
    LastCountedLgpla lastCounted;
    GridLayout tableHandler4, tableHandler5, tableHandler7, tableHandler8, tableHandler12, tableHandler13;
    InstanceOfDB dbInstance;
    ImageView cameraButt, noteButt, datePickButt, materialMatch;
    SpecialSapCountBean specialCount;
    boolean flagMaterial = false;
    List<ZIACST_I360_OBJECTDATA_SapEntity> listCPCCCP;
    HashMap<ZIACST_I360_OBJECTDATA_SapEntity, List<ZIACST_I360_OBJECTDATA_SapEntity>> CPCMAPZia;
    List<E_ClassVal_SapEntity> e_classVal_sapEntities;
    List<ZIACST_I360_OBJECTDATA_SapEntity> CPPMAPZia;
    List<ZIACST_I360_OBJECTDATA_SapEntity> PPCMAPZia;
    LinkedHashMap<String, LgplaValuesBean> prevCarr;
    LgplaValuesBean currentValue;
    LoginStored login;
    Integer matSec = 1;
    String mapLgplaKey;
    TaskCompleted isCompleted;
    Integer newIposRoute;
    MaterialDescrptionBean descriptionIntent;
    ZIACST_I360_OBJECTDATA_SapEntity camStoredBeanValue;
    E_ClassVal_SapEntity eClassValSapEntity;
    String estatusPt;
    String lote;
    String loteTxt;
    String prodDate;

    String zoneDisp = "MX";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_route);

        context = RouteActivity.this;
        activity = RouteActivity.this;
        material = findViewById(R.id.idMatnrTxt);
        idLoteTxt = findViewById(R.id.idLoteTxt);
        idTopBoxTblLbl = findViewById(R.id.idTopBoxTblLbl);
        idUnitBoxTblLbl = findViewById(R.id.idUnitBoxTblLbl);
        idUnitEaTblLbl = findViewById(R.id.idUnitEaTblLbl);
        carrilText = findViewById(R.id.idLgplaTxt);
        spinAlmacen = findViewById(R.id.idLgnumSpin);
        spinZone = findViewById(R.id.idLgtypSpin);
        descripcionText = findViewById(R.id.idDescMatnrTxt);
        cantidadTarima = findViewById(R.id.idQuaHUTbltxt);
        cantidadCama = findViewById(R.id.idQuaBedTbltxt);
        cantidadCaja = findViewById(R.id.idQuaBoxTbltxt);
        cantidadTotalTarima = findViewById(R.id.idBoxHUTblLbl);
        cantidadTotalCama = findViewById(R.id.idBoxBedTblLbl);
        cantidadTotalCaja = findViewById(R.id.idBoxBoxTblLbl);
        cantidadTotalPz = findViewById(R.id.idTotEaTblLbl);
        cantidadTotal = findViewById(R.id.idBoxTotTblLbl);
        btnfnCarril = findViewById(R.id.idBtnCarEnd);
        btnfnMaterial = findViewById(R.id.idBtnMatEnd);
        btnfnZona = findViewById(R.id.idBtnZoneEnd);
        btnfnAlmacen = findViewById(R.id.idBtnAlmacenEnd);
        btnMatnr = findViewById(R.id.idMatnrBtn);
        secuencia = findViewById(R.id.idSeqValLbl);
        secuenciInfoLbl = findViewById(R.id.idSeqLbl);
        cameraButt = findViewById(R.id.idScanMatnrBtn);
        camSpin = findViewById(R.id.idCamSpin);
        cajSpin = findViewById(R.id.idCajSpin);
        classSpin = findViewById(R.id.idClassSpin);
        ptSpin = findViewById(R.id.idPtSpin);
        pzSpin = findViewById(R.id.idPzSpin);
        camaInfoLabel = findViewById(R.id.idCamLbl);
        tableQuanLayout = findViewById(R.id.idTableQuantity);
        progressBarHolder = findViewById(R.id.routeProgLayout);
        noteButt = findViewById(R.id.idNoteAdd);
        datePickButt = findViewById(R.id.idProdDateIco);
        textProduction = findViewById(R.id.idProductionDateText);
        textPrdLbl = findViewById(R.id.textProdDate);
        materialMatch = findViewById(R.id.idMaterialMatch);
        idCamSpinnerBtn = findViewById(R.id.idCamSpinnerBtn);
        idCajSpinnerBtn = findViewById(R.id.idCajSpinnerBtn);
        idPzSpinnerBtn = findViewById(R.id.idPzSpinnerBtn);
        tableHandler4 = findViewById(R.id.tableHandler4);
        tableHandler5 = findViewById(R.id.tableHandler5);
        tableHandler7 = findViewById(R.id.tableHandler7);
        tableHandler8 = findViewById(R.id.tableHandler8);
        tableHandler12 = findViewById(R.id.tableHandler12);
        tableHandler13 = findViewById(R.id.tableHandler13);
        dbInstance = InstanceOfDB.getInstanceOfDB(context);
        tableTarimaRow = findViewById(R.id.tableTarimaRow);
        tableCamaRow = findViewById(R.id.tableCamaRow);
        tablePzRow = findViewById(R.id.tablePzRow);
        routeModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        login = CommonUtilities.getLoginStructure(context);
        Log.e("debug","Login:" + login.toString());
        mapLgplaKey = null;
        gson = new Gson();
        try {
            System.gc();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
        }

        try {
            if (login != null && (login.getLoginBean().getLoginLang() != null && login.getLoginBean().getLoginLang().equalsIgnoreCase("MX"))) {
                material.setInputType(InputType.TYPE_CLASS_NUMBER);
                material.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() >= 0 && s.length() < 7) {
                            flagMaterial = false;
                        } else if (s.length() >= 7 && !flagMaterial) {
                            initiateLoading();
                            flagMaterial = true;
                            MaterialDescrptionBean bean = new MaterialDescrptionBean();
                            bean.setMatnr(s.toString());
                            new doSearchMaterialBackground(routeModel).execute(bean);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                });

                material.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            if (!material.getText().toString().isEmpty() && !flagMaterial) {
                                MaterialDescrptionBean bean = new MaterialDescrptionBean();
                                bean.setMatnr(material.getText().toString().toUpperCase());
                                new doSearchMaterialBackground(routeModel).execute(bean);
                                flagMaterial = true;
                                return true;
                            } else {
                                return false;
                            }
                        }
                        return false;

                    }
                });

            } else if (login != null && (login.getLoginBean().getLoginLang().equalsIgnoreCase("CO") || login.getLoginBean().getLoginLang().equalsIgnoreCase("RD"))) {
                material.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                material.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        flagMaterial = false;
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                });


                material.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            if (!material.getText().toString().isEmpty() && !flagMaterial) {
                                MaterialDescrptionBean bean = new MaterialDescrptionBean();
                                bean.setMatnr(material.getText().toString().toUpperCase());
                                new doSearchMaterialBackground(routeModel).execute(bean);
                                flagMaterial = true;
                                return true;
                            } else {
                                return false;
                            }
                        }
                        return false;
                    }
                });
            }
        } catch (Exception e) {

            material.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if (!material.getText().toString().isEmpty()) {
                            MaterialDescrptionBean bean = new MaterialDescrptionBean();
                            bean.setMatnr(material.getText().toString().toUpperCase());
                            new doSearchMaterialBackground(routeModel).execute(bean);
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
            });
        }

        String special = CommonUtilities.PushGsonVariable(SAPSPECIALCOUNT, context);
        if (special != null) {
            specialCount = gson.fromJson(special, SpecialSapCountBean.class);
        }

        Intent intent = getIntent();
        String description = intent.getStringExtra("MaterialDescription");
        if (description != null) {
            descriptionIntent = gson.fromJson(description, MaterialDescrptionBean.class);
        }

        String loginString = CommonUtilities.PushGsonVariable(STOREDLOGIN, context);
        if (loginString != null) {
            String routeString = CommonUtilities.PushGsonVariable(STOREDROUTE, context);
            storedLogin = gson.fromJson(loginString, LoginStored.class);
            if (routeString != null) {
                routeUser = gson.fromJson(routeString, RouteUserBean.class);

                Log.i("Ruta: ", routeString);
                String sTask = CommonUtilities.PushGsonVariable(TASKCOMPLETED, context);
                if (sTask != null) {
                    isCompleted = gson.fromJson(sTask, TaskCompleted.class);
                    if (isCompleted.isTaskCompleted()) {
                        startActivity(new Intent(activity, UpDownActivity.class));
                    } else {
                        String prevValues = CommonUtilities.PushGsonVariable(PREVIEWVALUES, context);
                        if (prevValues != null) {
                            routeStoredBean = gson.fromJson(prevValues, RouteStoredBean.class);
                            getNextOperation();
                        } else {
                            fillFirstRunForRoute();
                        }
                    }
                } else {
                    CommonUtilities.cancelSession(context, activity);
                }
            } else {
                CommonUtilities.cancelSession(context, activity);
            }
        } else {
            CommonUtilities.cancelSession(context, activity);
        }
    }

    public void disableCamxCaj() {
        //camSpin.setEnabled(true);
        camSpin.setAlpha(.8F);
        camSpin.setClickable(false);
        cajSpin.setAlpha(.8F);
        //cajSpin.setEnabled(true);
        pzSpin.setAlpha(.8F);
        //pzSpin.setEnabled(true);
        pzSpin.setClickable(false);
        idCajSpinnerBtn.setEnabled(false);
        idCajSpinnerBtn.setClickable(false);
        idCamSpinnerBtn.setEnabled(false);
        idCamSpinnerBtn.setClickable(false);
        idPzSpinnerBtn.setEnabled(false);
        idPzSpinnerBtn.setClickable(false);
    }

    public void onClickMatchMaterial(View view) {
        startActivity(new Intent(context, MatchCodeMaterialActivity.class));
    }

    private class doSearchMaterialBackground extends AsyncTask<MaterialDescrptionBean, Void, MaterialDescrptionBean> {

        RouteViewModel asyncModel;

        public doSearchMaterialBackground(RouteViewModel asyncModel) {
            this.asyncModel = asyncModel;
        }

        @Override
        protected MaterialDescrptionBean doInBackground(MaterialDescrptionBean... materialDescrptionBeans) {
            MaterialDescrptionBean listBean;
            return materialDescrptionBeans[0];
        }

        @Override
        protected void onPostExecute(MaterialDescrptionBean materialDescrptionBeans) {
            List<MaterialDescrptionBean> listBean;
            try {
                listBean = routeModel.getMaterialByValues(materialDescrptionBeans);
            } catch (Exception e) {
                listBean = new ArrayList<>();
            }
            setMaterialDataByList(listBean);
        }
    }

    public void fillFirstRunForRoute() {
        try {
            routeStoredBean = new RouteStoredBean();
            getSetSpinnerStorage();
            getSetSpinnerZone();
            routeStoredBean.setiPosRoute(0);
            routeStoredBean.setiPosZone(0);
            routeStoredBean.setiMaxPosRoute(routeUser.getPositions().size() - 1);
            routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(0).getZone().getPositionsB().size() - 1);
            CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);

            if (specialCount != null && specialCount.getSpecial()) {
                HashMap<String, LgplaValuesBean> mapLgpla = routeUser.getPositions().get(0).getZone().getPositionsB().get(0).getLgplaValues();
                List<String> materialCount = new ArrayList<>();
                Iterator it = mapLgpla.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    LgplaValuesBean value = (LgplaValuesBean) pair.getValue();
                    if (value.isLocked()) {
                        materialCount.add(value.getMatnr());
                    }
                }
                specialCount.setPermitedList(materialCount);
                CommonUtilities.UpdateStoreGSonVariable(SAPSPECIALCOUNT, gson.toJson(specialCount), context);
            }

            getNextOperation();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);

            if (routeUser.getPositions() == null || routeUser.getPositions().isEmpty() || routeUser.getPositions().size() == 0) {
                CommonUtilities.CustomWarningDialog("Advertencia!", "Error en la Ruta obtenida para la tarea: " + routeUser.getTaskId() +
                        "\nFavor de Contactar Administrador", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        CommonUtilities.cancelPreviewValues(context, null);
                        startActivity(new Intent(activity, UpDownActivity.class));
                    }
                }, R.drawable.ic_exclamation_white_36dp, context);
            }
            if (routeUser.getPositions().get(routeStoredBean.getiPosZone()).getZone() == null || routeUser.getPositions().get(routeStoredBean.getiPosZone()).getZone().getPositionsB() == null
                    || routeUser.getPositions().get(routeStoredBean.getiPosZone()).getZone().getPositionsB().isEmpty()) {
                CommonUtilities.CustomWarningDialog("Advertencia!", "Error en la Ruta obtenida para la tarea: " + routeUser.getTaskId() +
                        "\nFavor de Contactar Administrador", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        CommonUtilities.cancelPreviewValues(context, null);
                        finishAndRemoveTask();
                        startActivity(new Intent(activity, UpDownActivity.class));
                    }
                }, R.drawable.ic_exclamation_white_36dp, context);
            }

        }
    }

    public void getNextOperation() {
        getNextClean();
        disableCamxCaj();
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());

        spinAlmacen.setText(routeOper.getGdesc());
        spinZone.setText(routeOper.getZone().getZdesc());
        spinZone.setTooltipText(routeOper.getZone().getZdesc());
        if (descriptionIntent != null) {
            //material.setText(descriptionIntent.getMatnr());
            new doSearchMaterialBackground(routeModel).execute(descriptionIntent);
        }
        try {
            if (routeStoredBean.getiPosLgpla() == null) {
                if (routeStoredBean.getiPosZone() > routeStoredBean.getiMaxPosZone()) {
                    if (checkIfZoneEnd()) {
                        forceZoneEnd();
                    } else {
                        recoverPendingLgpla();
                    }
                } else {
                    try {
                        ZoneUserPositionsBean zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosZone());
                        carrilText.setText(zoneOper.getLgpla());
                        if (zoneOper.getIdDone()) {
                            //zonePositionDone(zoneOper.getLgpla(), routeStoredBean.getiPosZone());
                            operationCarrilEnd();
                        }
                    } catch (Exception e) {
                        forceZoneEnd();
                    }
                }
            } else {
                ZoneUserPositionsBean zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosLgpla());
                carrilText.setText(zoneOper.getLgpla());
                if (zoneOper.getIdDone()) {
                    zonePositionDone(zoneOper.getLgpla(), routeStoredBean.getiPosLgpla());
                }
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            routeStoredBean.setiPosLgpla(null);
            CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
            finishAndRemoveTask();
            startActivity(new Intent(activity, RouteActivity.class));
        }
    }

    public void operationCarrilEnd() {
        if (routeStoredBean.getiPosLgpla() != null) {
            routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().get(routeStoredBean.getiPosLgpla()).setIdDone(true);
            if (routeStoredBean.getiPosZone() == routeStoredBean.getiPosLgpla()) {
                routeStoredBean.setiPosZone(routeStoredBean.getiPosZone() + 1);
            }
            routeStoredBean.setiPosLgpla(null);
        } else {
            routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().get(routeStoredBean.getiPosZone()).setIdDone(true);
            routeStoredBean.setiPosZone(routeStoredBean.getiPosZone() + 1);
        }

        if (lastCounted != null) {
            if (lastCounted.getLastCounted() != null && !lastCounted.getLastCounted().isEmpty()) {
                CommonUtilities.UpdateStoreGSonVariable(LASTCOUNTEDLGPLA, gson.toJson(lastCounted), context);
            } else {
                CommonUtilities.PopGsonVariable(LASTCOUNTEDLGPLA, context);
            }
        } else {
            CommonUtilities.PopGsonVariable(LASTCOUNTEDLGPLA, context);
        }

        CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
        CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
        finishAndRemoveTask();
        startActivity(new Intent(activity, RouteActivity.class));
    }

    public void finalizarCarril(View view) {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        lastCounted = new LastCountedLgpla();
        ZoneUserPositionsBean zoneOperV = null;
        if (routeStoredBean.getiPosLgpla() != null) {
            zoneOperV = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosLgpla());
        } else {
            zoneOperV = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosZone());
        }
        final ZoneUserPositionsBean zoneOper = zoneOperV;
        CommonUtilities.CustomConfirmDialog("Advertencia!", "Esta por finalizar carril... \n ¿Desea continuar?",
                activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        if (ret == ResponseVariability.SUCCESSFULL) {
                            if (zoneOper.getLgplaValues() != null && !zoneOper.getLgplaValues().isEmpty()) {
                                Iterator it = zoneOper.getLgplaValues().entrySet().iterator();
                                boolean isLockedFromConsole = false;
                                while (it.hasNext()) {
                                    Map.Entry pair = (Map.Entry) it.next();
                                    LgplaValuesBean lgpla = (LgplaValuesBean) pair.getValue();
                                    if (lgpla.isLocked()) {
                                        if (lgpla.getTotalConverted() == null) {
                                            isLockedFromConsole = true;
                                        }
                                    }
                                }
                                if (isLockedFromConsole) {
                                    CommonUtilities.CustomConfirmDialog("Advertencia!", "Tiene materiales pendientes por contar... \n ¿Desea Finalizar Carril?",
                                            activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                                @Override
                                                public void customCallBack(Integer ret) {
                                                    if (ret.equals(ResponseVariability.SUCCESSFULL)) {
                                                        lastCounted.setLastCounted(zoneOper.getLgplaValues());
                                                        operationCarrilEnd();
                                                    }
                                                }
                                            }, R.drawable.ic_exclamation_white_36dp, context);
                                } else {
                                    lastCounted.setLastCounted(zoneOper.getLgplaValues());
                                    operationCarrilEnd();
                                }
                            } else {
                                lastCounted.setLastCounted(zoneOper.getLgplaValues());
                                operationCarrilEnd();
                            }
                        }
                    }
                }, R.drawable.ic_exclamation_white_36dp, context);

    }

    public void finalizarMaterial(View view) {
        currentValue.setSec(secuencia.getText().toString());
        BigDecimal valuesTR = new BigDecimal(cantidadTarima.getText().toString().equals("") ? "0" : cantidadTarima.getText().toString());
        currentValue.setTarimas(String.valueOf(valuesTR));
        BigDecimal valuesCM = new BigDecimal(cantidadCama.getText().toString().equals("") ? "0" : cantidadCama.getText().toString());
        currentValue.setCamas(String.valueOf(valuesCM));
        BigDecimal valuesCJ = new BigDecimal(cantidadCaja.getText().toString().equals("") ? "0" : cantidadCaja.getText().toString());
        currentValue.setUm(String.valueOf(valuesCJ));
        BigDecimal cantTot = new BigDecimal(cantidadTotal.getText().toString().equals("") ? "0" : cantidadTotal.getText().toString());
        currentValue.setTotalConverted(String.valueOf(cantTot));
        currentValue.setDateEnd(new Date().getTime());
        getNumberOfTarima();
        boolean ok = true;

        // Validación de fecha frescura y Lote
        if (routeUser.getType().equals("4")) {
            if (idLoteTxt.getText().toString() == null ||
                    idLoteTxt.getText().toString().toUpperCase().isEmpty() ||
                    textProduction.getText().toString() == null ||
                    textProduction.getText().toString().isEmpty()) {
                CommonUtilities.CustomWarningDialog("Advertencia!", "Los campos de Fecha frescura y Lote son obligatorios", activity, null,
                        null, null, null, R.drawable.ic_exclamation_white_36dp, context);
                ok = false;
            } else {
                // Asignamos el prodDate
                currentValue.setProdDate(textProduction.getText().toString());
                // Asignamos el lote
                currentValue.setLote(idLoteTxt.getText().toString().toUpperCase());
                ok = true;
            }
        }

        enviarMaterial(ok);
    }

    private void enviarMaterial(boolean ok) {
        if(ok) {
            if (eClassValSapEntity != null && eClassValSapEntity.getBwtar() != null) {
                currentValue.setCval(eClassValSapEntity.getBwtar());
            }

            if (routeStoredBean.getiPosLgpla() != null) {
                routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().get(routeStoredBean.getiPosLgpla()).getLgplaValues().put(mapLgplaKey, currentValue);
            } else {
                routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().get(routeStoredBean.getiPosZone()).getLgplaValues().put(mapLgplaKey, currentValue);
            }
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            finishAndRemoveTask();
            startActivity(new Intent(activity, RouteActivity.class));
        }
    }

    public void getNumberOfTarima() {
        BigDecimal cantTot = new BigDecimal(currentValue.getTotalConverted());
        if (cantTot.compareTo(BigDecimal.ZERO) > 0) {
            currentValue.setCpc(cajSpin.getText().toString().equals("") ? "0" : cajSpin.getText().toString());
            currentValue.setCpp(camSpin.getText().toString().equals("") ? "0" : camSpin.getText().toString());
            if (currentValue.getVhilm() != null && !currentValue.getVhilm().equalsIgnoreCase(ResponseVariability.NOSELECTEDTARIMAS) && !currentValue.getVhilm().isEmpty()) {
                if (!currentValue.getCpc().equals("0") && !currentValue.getCpp().equals("0")) {
                    try {
                        BigDecimal cpp = new BigDecimal(currentValue.getCpp());
                        BigDecimal cpc = new BigDecimal(currentValue.getCpc());
                        BigDecimal cpcXcpp = cpc.multiply(cpp);
                        BigDecimal totalQuan = cantTot.divide(cpcXcpp, 0, RoundingMode.HALF_UP);
                        currentValue.setVhilmQuan(totalQuan.toString());
                    } catch (Exception e) {
                        if (!currentValue.getTarimas().equals("0")) {
                            currentValue.setVhilmQuan(currentValue.getTarimas());
                        }
                    }
                } else {
                    if (!currentValue.getTarimas().equals("0")) {
                        currentValue.setVhilmQuan(currentValue.getTarimas());
                    }
                }
            } else {
                currentValue.setVhilm(ResponseVariability.NOSELECTEDTARIMAS);
            }
        } else {
            currentValue.setVhilm(ResponseVariability.NOSELECTEDTARIMAS);
            currentValue.setCpc("0");
            currentValue.setCpp("0");
        }

    }

    public void finalizarZona(View view) {
        CommonUtilities.CustomConfirmDialog("Advertencia", "Esta por finalizar la zona\n ¿Desea Continuar?", activity, null,
                null, null, new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        if (ret == ResponseVariability.SUCCESSFULL) {
                            routeUser.getPositions().get(routeStoredBean.getiPosRoute()).setDone(true);
                            routeStoredBean.setiPosZone(0);
                            routeStoredBean.setiPosLgpla(null);
                            routeStoredBean.setiPosRoute(newIposRoute);
                            routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().size() - 1);
                            CommonUtilities.PopGsonVariable(LASTCOUNTEDLGPLA, context);
                            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
                            CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
                            finishAndRemoveTask();
                            startActivity(new Intent(activity, RouteActivity.class));
                        }
                    }
                }, R.drawable.ic_exclamation_white_36dp, context);
    }

    public void finalizarAlmacen(View view) {
        if (!getNextLgort()) {
            if (isCompleted == null) {
                isCompleted = new TaskCompleted();
            }
            isCompleted.setTaskCompleted(true);
            isCompleted.setTaskUploaded(false);
            routeUser.setDateEnd(new Date().getTime());
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            CommonUtilities.UpdateStoreGSonVariable(TASKCOMPLETED, gson.toJson(isCompleted), context);
            Toast.makeText(context, "Conteo Finalizado!", Toast.LENGTH_SHORT);
            finishAndRemoveTask();
            startActivity(new Intent(activity, UpDownActivity.class));
        } else {
            routeUser.getPositions().get(routeStoredBean.getiPosRoute()).setDone(true);
            routeStoredBean.setiPosRoute(newIposRoute);
            routeStoredBean.setiPosLgpla(null);
            routeStoredBean.setiPosZone(0);
            routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(newIposRoute).getZone().getPositionsB().size() - 1);
            CommonUtilities.PopGsonVariable(LASTCOUNTEDLGPLA, context);
            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
            CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
            finishAndRemoveTask();
            startActivity(new Intent(activity, RouteActivity.class));
        }
    }


    public boolean getNextLgort() {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        String lgort = routeOper.getLgort();
        int pos = 0;
        boolean nextLgort = false;
        for (RouteUserPositionBean routePos : routeUser.getPositions()) {
            if (!routePos.getLgort().equals(lgort)) {
                if (!routePos.getDone()) {
                    nextLgort = true;
                    newIposRoute = pos;
                    break;
                }
            }
            pos++;
        }
        return nextLgort;
    }

    public boolean isFinishedLgort(String lgort) {
        boolean nextLgort = true;
        for (RouteUserPositionBean routePos : routeUser.getPositions()) {
            if (routePos.getLgort().equals(lgort)) {
                if (!routePos.getDone()) {
                    nextLgort = false;
                    break;
                }
            }
        }
        return nextLgort;
    }

    public boolean isFinishedZone(String zoneId) {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        boolean isClosed = false;
        for (RouteUserPositionBean value : routeUser.getPositions()) {
            if (value.getZone().getZoneId().equals(zoneId)) {
                if (value.getDone()) {
                    isClosed = true;
                    break;
                }
            }
        }
        return isClosed;
    }


    public void zonePositionDone(String lgpla, final int posicion) {
        CommonUtilities.CustomConfirmDialog("Advertencia!", "El Carril: " + lgpla + " fue contado anteriormente \n ¿Desea editarlo?", activity,
                null, "Editar", "Finalizar", new CommonUtilities.CustomCallBack<Integer>() {
                    @Override
                    public void customCallBack(Integer ret) {
                        if (ret == ResponseVariability.SUCCESSFULL) {
                            //Abrir carril
                            routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().get(posicion).setIdDone(false);
                            CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
                        } else {
                            operationCarrilEnd();
                        }
                    }
                }, R.drawable.ic_exclamation_white_48dp, context);
    }


    public boolean checkIfZoneEnd() {
        boolean isZoneEnded = true;
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        for (ZoneUserPositionsBean position : routeOper.getZone().getPositionsB()) {
            if (!position.getIdDone()) {
                isZoneEnded = false;
                break;
            }
        }
        return isZoneEnded;
    }

    public void recoverPendingLgpla() {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        int lgplaPos = 0;
        for (ZoneUserPositionsBean position : routeOper.getZone().getPositionsB()) {
            if (!position.getIdDone()) {
                routeStoredBean.setiPosLgpla(lgplaPos);
                CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
                break;
            }
            lgplaPos++;
        }
    }

    public void forceZoneEnd() {
        material.setEnabled(false);
        material.setAlpha(.3f);
        carrilText.setText("N/A");
        descripcionText.setEnabled(false);
        cameraButt.setEnabled(false);
        cameraButt.setAlpha(.3F);
        btnMatnr.setEnabled(false);
        btnMatnr.setAlpha(.3F);
        materialMatch.setEnabled(false);
        materialMatch.setAlpha(.3F);
        btnfnCarril.setVisibility(View.GONE);
        if (hasMoreZonesLgort()) {
            btnfnZona.setVisibility(View.VISIBLE);
            btnfnAlmacen.setVisibility(View.GONE);
        } else {
            btnfnZona.setVisibility(View.GONE);
            btnfnAlmacen.setVisibility(View.VISIBLE);
        }
    }


    public boolean hasMoreZonesLgort() {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        String lgort = routeOper.getLgort();
        String zoneid = routeOper.getZone().getZoneId();
        int pos = 0;
        boolean hasMoreZone = false;
        for (RouteUserPositionBean routePos : routeUser.getPositions()) {
            if (routePos.getLgort().equals(lgort) && !routePos.getZone().getZoneId().equals(zoneid)) {
                if (!routePos.getDone()) {
                    hasMoreZone = true;
                    break;
                }
            }
            pos++;
        }
        if (hasMoreZone) {
            newIposRoute = pos;
        }
        return hasMoreZone;
    }


    public void getNextClean() {
        material.setText("");
        descripcionText.setText("");
        cantidadTarima.setText("");
        cantidadCama.setText("");
        cantidadCaja.setText("");
        cantidadTotalTarima.setText("0");
        cantidadTotalCaja.setText("0");
        cantidadTotalCama.setText("0");
        cantidadTotalPz.setText("0");
        cantidadTotal.setText("0");
        textProduction.setText("");
        secuencia.setText("#");
        cameraButt.setEnabled(true);
        datePickButt.setVisibility(View.INVISIBLE);
        datePickButt.setEnabled(false);
        noteButt.setVisibility(View.INVISIBLE);
        noteButt.setEnabled(false);
        tableQuanLayout.setVisibility(View.INVISIBLE);
        tableCamaRow.setVisibility(View.GONE);
        tablePzRow.setVisibility(View.GONE);
        tableTarimaRow.setVisibility(View.GONE);
        secuencia.setVisibility(View.INVISIBLE);
        secuenciInfoLbl.setVisibility(View.INVISIBLE);
        textPrdLbl.setVisibility(View.INVISIBLE);
        textProduction.setVisibility(View.INVISIBLE);
        btnfnCarril.setVisibility(View.VISIBLE);
        tableHandler4.setVisibility(View.GONE);
        tableHandler5.setVisibility(View.GONE);
        tableHandler7.setVisibility(View.GONE);
        tableHandler8.setVisibility(View.GONE);
        // Reset de los campos
        tableHandler12.setVisibility(View.GONE);
        tableHandler13.setVisibility(View.GONE);
    }


    AlertDialog spinnerDialog;

    public void showSpinnerStorage(View view) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View mLgort = layoutInflater.inflate(R.layout.almacenes_pop_up, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutAlmacen);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.almacenes_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewAlmacen);
            headerView.setText("ALMACENES");
            Iterator it = routeStoredBean.getLgortMapValue().entrySet().iterator();
            while (it.hasNext()) {
                View zonLView = layoutInflater.inflate(R.layout.zone_lgort_linear_layout, null);
                ImageButton iconBtn = zonLView.findViewById(R.id.zoneLgortImagebutton);
                iconBtn.setClickable(false);
                CardView cardPress = zonLView.findViewById(R.id.cardZoneLgortClickZone);
                TextView zoneValue = zonLView.findViewById(R.id.ZoneLgortListId);


                final Map.Entry pair = (Map.Entry) it.next();
                if (isFinishedLgort(pair.getKey().toString())) {
                    iconBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_check_circle_white_36));
                } else {
                    iconBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_error_white_36));
                    cardPress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reviewStorageOperation(pair.getKey().toString());
                        }
                    });
                }

                if (new String(pair.getKey().toString() + " - " + pair.getValue()).length() > 20) {
                    zoneValue.setText(new String(pair.getKey().toString() + " - " + pair.getValue()).substring(0, 20));
                } else {
                    zoneValue.setText(pair.getKey().toString() + " - " + pair.getValue());
                }
                zoneValue.setTooltipText(pair.getKey().toString() + " - " + pair.getValue());
                tableLgort.addView(zonLView);
            }
            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });

            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            //dialogBuilder.show();
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }

    public void reviewStorageOperation(final String lgort) {
        if (!routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getLgort().equalsIgnoreCase(lgort)) {
            if (routeStoredBean.getiPosZone() > 0 || checkIfZoneHasStarted()) {
                CommonUtilities.CustomWarningDialog("Advertencia!", "Para cambiar de Almacen, complete la zona actual \n " +
                                "Posterior seleccione uno de los almacenes disponibles \n previo a cerrar algun carril", activity, null,
                        null, null, null, R.drawable.ic_exclamation_white_36dp, context);
            } else {
                CommonUtilities.CustomConfirmDialog("Advertencia", "Esta por cambiar su area de trabajo.\n" +
                                "Almacen: " + routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getLgort() +
                                "\n" + "por Almacen:" + lgort + "\n¿Desea Continuar?", activity,
                        null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                if (ret == ResponseVariability.SUCCESSFULL) {
                                    Integer position = getLgortPosition(lgort);
                                    routeStoredBean.setiPosRoute(position);
                                    routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(position).getZone().getPositionsB().size() - 1);
                                    routeStoredBean.setiPosZone(0);
                                    routeStoredBean.setiPosLgpla(null);
                                    CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
                                    startActivity(new Intent(activity, RouteActivity.class));
                                }
                            }
                        }, R.drawable.ic_exclamation_white_36dp, context);
            }
        }

    }

    public Integer getLgortPosition(String lgort) {
        Integer position = 0;
        for (RouteUserPositionBean routeOper : routeUser.getPositions()) {
            if (routeOper.getLgort().equalsIgnoreCase(lgort)) {
                if (!routeOper.getDone()) {
                    break;
                }
            } else {
                position++;
            }
        }
        return position;
    }

    public void showSpinnerZone(View view) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
            View mLgort = layoutInflater.inflate(R.layout.almacenes_pop_up, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutAlmacen);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.almacenes_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewAlmacen);
            headerView.setText("ZONAS");
            for (final ZoneStoredBean zoneId : (List<ZoneStoredBean>) routeStoredBean.getZoneByLgort().get(routeOper.getLgort())) {
                View zonLView = layoutInflater.inflate(R.layout.zone_lgort_linear_layout, null);
                ImageButton iconBtn = zonLView.findViewById(R.id.zoneLgortImagebutton);
                iconBtn.setClickable(false);
                CardView cardPress = zonLView.findViewById(R.id.cardZoneLgortClickZone);
                TextView zoneValue = zonLView.findViewById(R.id.ZoneLgortListId);

                if (isFinishedZone(zoneId.getZoneID())) {
                    iconBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_check_circle_white_36));
                } else {
                    iconBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_error_white_36));
                    cardPress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reviewZoneOperation(zoneId);
                        }
                    });
                }
                if (new String(zoneId.getZoneID() + " - " + zoneId.getZoneD()).length() > 20) {
                    zoneValue.setText(new String(zoneId.getZoneID() + " - " + zoneId.getZoneD()).substring(0, 20));
                } else {
                    zoneValue.setText(zoneId.getZoneID() + " - " + zoneId.getZoneD());
                }

                zoneValue.setTooltipText(zoneId.getZoneID() + " - " + zoneId.getZoneD());
                tableLgort.addView(zonLView);
            }
            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }

    public void reviewZoneOperation(final ZoneStoredBean zoneId) {
        if (zoneId.getCurrentPosition() != routeStoredBean.getiPosRoute()) {

            if (routeStoredBean.getiPosZone() > 0 || checkIfZoneHasStarted()) {
                CommonUtilities.CustomWarningDialog("Advertencia!", "Para cambiar de Zona, complete la actual \n " +
                                "Posterior seleccione una de las zonas disponibles \n previo a cerrar algun carril", activity, null,
                        null, null, null, R.drawable.ic_exclamation_white_36dp, context);
            } else {
                CommonUtilities.CustomConfirmDialog("Advertencia", "Esta por cambiar su area de trabajo.\n" +
                                "Zona: " + routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getZdesc() +
                                "\n" + "por Zona:" + zoneId.getZoneD() + "\n¿Desea Continuar?", activity,
                        null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                            @Override
                            public void customCallBack(Integer ret) {
                                if (ret == ResponseVariability.SUCCESSFULL) {
                                    routeStoredBean.setiPosRoute(zoneId.getCurrentPosition());
                                    routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(zoneId.getCurrentPosition()).getZone().getPositionsB().size() - 1);
                                    routeStoredBean.setiPosZone(0);
                                    routeStoredBean.setiPosLgpla(null);
                                    CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
                                    startActivity(new Intent(activity, RouteActivity.class));
                                }
                            }
                        }, R.drawable.ic_exclamation_white_36dp, context);
            }
        }
    }

    public boolean checkIfZoneHasStarted() {
        boolean isZoneEnded = false;
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        for (ZoneUserPositionsBean position : routeOper.getZone().getPositionsB()) {
            if (position.getIdDone()) {
                isZoneEnded = true;
                break;
            }
        }
        return isZoneEnded;
    }

    public void getSetSpinnerStorage() {
        routeStoredBean.setLgortMapValue(new HashMap<String, String>());
        for (RouteUserPositionBean routePos : routeUser.getPositions()) {
            if (!routeStoredBean.getLgortMapValue().containsKey(routePos.getLgort())) {
                routeStoredBean.getLgortMapValue().put(routePos.getLgort(), routePos.getGdesc());
            }
        }
    }

    public void getSetSpinnerZone() {
        routeStoredBean.setZoneByLgort(new HashMap<String, List<ZoneStoredBean>>());
        for (RouteUserPositionBean routePos : routeUser.getPositions()) {
            if (!routeStoredBean.getZoneByLgort().containsKey(routePos.getLgort())) {
                List<ZoneStoredBean> zoneByLgortList = new ArrayList<>();
                int position = 0;
                for (RouteUserPositionBean routeUserSubPos : routeUser.getPositions()) {
                    if (routeUserSubPos.getLgort().equals(routePos.getLgort())) {
                        ZoneStoredBean singleBean = new ZoneStoredBean();
                        singleBean.setZoneID(routeUserSubPos.getZone().getZoneId());
                        singleBean.setZoneD(routeUserSubPos.getZone().getZdesc());
                        singleBean.setCurrentPosition(position);
                        zoneByLgortList.add(singleBean);
                    }
                    position++;
                }
                routeStoredBean.getZoneByLgort().put(routePos.getLgort(), zoneByLgortList);
            }
        }
    }


    public void matrnButtonOnClick(View view) {
        initiateLoading();
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        ZoneUserPositionsBean zoneOper = null;
        if (routeStoredBean.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosLgpla());
        } else {
            zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosZone());
        }
        if (zoneOper.getLgplaValues() != null) {
            cancelLoading();
            startActivity(new Intent(context, ListMaterialActivity.class));
        } else {
            cancelLoading();
            Toast.makeText(context, "No hay Materiales registrados para el Carril: " + zoneOper.getLgpla(), Toast.LENGTH_LONG).show();
        }
    }

    public void lgplaButtonOnClick(View view) {
        initiateLoading();
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        if (routeOper.getZone().getPositionsB() != null && !routeOper.getZone().getPositionsB().isEmpty()) {
            startActivity(new Intent(context, ListCarrilesActivity.class));
        } else {
            cancelLoading();
            Toast.makeText(context, "No hay Carriles registrados para la Zona: " + routeOper.getZone().getZdesc(), Toast.LENGTH_LONG).show();
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
        // Do nothing
    }


    public void materialScanner(View view) {
        material.clearFocus();
        CommonUtilities.utilitiesScanner(activity, ResponseVariability.SCAN_TEXT, ResponseVariability.SCANREQUEST);
    }


    public void setMaterialData(MaterialDescrptionBean beanMaterial) {

        if (specialCount != null && specialCount.getSpecial()) {
            if (beanMaterial != null) {
                if (specialCount.getPermitedList().contains(beanMaterial.getMatnr())) {
                    cantidadTarima.setText("");
                    cantidadCama.setText("");
                    cantidadCaja.setText("");
                    cantidadTotalTarima.setText("0");
                    cantidadTotalCaja.setText("0");
                    cantidadTotalCama.setText("0");
                    cantidadTotal.setText("0");
                    material.setText(beanMaterial.getMatnr());
                    material.setEnabled(true);
                    descripcionText.setText(beanMaterial.getMaktx());
                    descripcionText.setEnabled(true);
                    cameraButt.setEnabled(true);
                    onWindowFocusChanged(true);
                    currentValue = new LgplaValuesBean();
                    currentValue.setDateStart(new Date().getTime());
                    matSec = 1;
                    datePickButt.setVisibility(View.VISIBLE);
                    datePickButt.setEnabled(true);
                    noteButt.setVisibility(View.VISIBLE);
                    noteButt.setEnabled(true);
                    if (isTarimaEnabled()) {
                        getTarimaForMaterial();
                    } else {
                        cancelLoading();
                        currentValue.setVhilm(ResponseVariability.NOSELECTEDTARIMAS);
                    }
                    getSecuencia(beanMaterial);
                    checkFillLgplaValue();
                    fillSpinnerValuation();
                    fillSpinnerCPCCCP(beanMaterial);
                    onWindowFocusChanged(true);
                    closeKeyboard();
                } else {
                    Toast.makeText(context, "Material: " + beanMaterial.getMatnr() + " no permitido detro de este conteo especial por material " +
                            "\n Favor de validar lista de materiales permitidos", Toast.LENGTH_LONG).show();
                    setEraseMaterialDataSpecial();
                }
            } else {
                setEraseMaterialData();
            }
        } else {
            if (beanMaterial != null) {
                cantidadTarima.setText("");
                cantidadCama.setText("");
                cantidadCaja.setText("");
                cantidadTotalTarima.setText("0");
                cantidadTotalCaja.setText("0");
                cantidadTotalCama.setText("0");
                cantidadTotal.setText("0");
                material.setText(beanMaterial.getMatnr());
                material.setEnabled(true);
                descripcionText.setText(beanMaterial.getMaktx());
                descripcionText.setEnabled(true);
                cameraButt.setEnabled(true);
                onWindowFocusChanged(true);
                currentValue = new LgplaValuesBean();
                currentValue.setDateStart(new Date().getTime());
                matSec = 1;
                datePickButt.setVisibility(View.VISIBLE);
                datePickButt.setEnabled(true);
                noteButt.setVisibility(View.VISIBLE);
                noteButt.setEnabled(true);
                if (isTarimaEnabled()) {
                    getTarimaForMaterial();
                } else {
                    cancelLoading();
                    currentValue.setVhilm(ResponseVariability.NOSELECTEDTARIMAS);
                }
                fillSpinnerValuation();
                getSecuencia(beanMaterial);
                checkFillLgplaValue();
                fillSpinnerCPCCCP(beanMaterial);
                onWindowFocusChanged(true);
                closeKeyboard();
            } else {
                setEraseMaterialData();
            }
        }
    }


    public void getSecuencia(MaterialDescrptionBean materialDescrptionBean) {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        ZoneUserPositionsBean zoneOper;
        if (routeStoredBean.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosLgpla());
        } else {
            zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosZone());
        }
        try {
            Iterator it = zoneOper.getLgplaValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                LgplaValuesBean lgplaValue = (LgplaValuesBean) pair.getValue();
                if (lgplaValue.getMatnr().equals(materialDescrptionBean.getMatnr()) && lgplaValue.getTotalConverted() != null) {
                    matSec++;
                }
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Log.i("getSecuencia", "Lgplavalues is NUll");
        }
        fillSecuencia();
    }

    public void fillSecuencia() {
        secuencia.setText(String.valueOf(matSec));
        secuencia.setVisibility(View.VISIBLE);
        secuenciInfoLbl.setVisibility(View.VISIBLE);


    }

    public void checkFillLgplaValue() {
        RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
        ZoneUserPositionsBean zoneOper;
        if (routeStoredBean.getiPosLgpla() != null) {
            zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosLgpla());
        } else {
            zoneOper = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosZone());
        }
        if (matSec > 1) {
            mapLgplaKey = String.valueOf(zoneOper.getPkAsgId()) + material.getText() + "_" + matSec;
        } else {
            mapLgplaKey = String.valueOf(zoneOper.getPkAsgId()) + material.getText();
        }
        if (zoneOper.getLgplaValues().containsKey(mapLgplaKey)) {
            currentValue = zoneOper.getLgplaValues().get(mapLgplaKey);
            currentValue.setDateStart(new Date().getTime());
        } else {
            currentValue.setMatnr(material.getText().toString());
            currentValue.setMatkx(descripcionText.getText().toString());
            currentValue.setDateStart(new Date().getTime());
        }

    }

    public void fillSpinnerValuation() {
        e_classVal_sapEntities = routeModel.getEClassVal();
    }

    public void fillSpinnerCPCCCP(MaterialDescrptionBean requestMat) {
        listCPCCCP = routeModel.getCCPCPCbyMaterial(requestMat);
        CPPMAPZia = new ArrayList<>();
        CPCMAPZia = new HashMap<>();
        PPCMAPZia = new ArrayList<>();
        if (!listCPCCCP.isEmpty()) {
            try {


                for (ZIACST_I360_OBJECTDATA_SapEntity singleValue : listCPCCCP) {
                    if (singleValue.getAtnam().contains("ZCPP")) {
                        CPPMAPZia.add(singleValue);
                    } else if (singleValue.getAtnam().contains("ZPPC")) {
                        PPCMAPZia.add(singleValue);
                    }
                }
                for (ZIACST_I360_OBJECTDATA_SapEntity singleFat : CPPMAPZia) {
                    String numberToEval = singleFat.getAtnam().replaceAll("ZCPP", "");
                    List<ZIACST_I360_OBJECTDATA_SapEntity> mapFillList = new ArrayList<>();
                    for (ZIACST_I360_OBJECTDATA_SapEntity singleValue : listCPCCCP) {
                        if (singleValue.getAtnam().contains("ZCPC")) {
                            if (singleValue.getAtnam().contains("ZCPC" + numberToEval + "_")) {
                                mapFillList.add(singleValue);
                            }
                        }

                    }
                    CPCMAPZia.put(singleFat, mapFillList);
                }
                fillSpinnersFirstTry();
            } catch (Exception e) {
                showTableOffQuantitys();
            }
        } else {
            showTableOffQuantitys();
        }
    }

    public void fillSpinnersFirstTry() {
        if (!CPCMAPZia.isEmpty()) {
            try {
                // Por pallet
                camSpin.setText(CPPMAPZia.get(0).getAtflv());
                if (CPPMAPZia.size() > 1) {
                    camSpin.setClickable(true);
                    //camSpin.setEnabled(true);
                    camSpin.setAlpha(1F);
                    idCamSpinnerBtn.setClickable(true);
                    idCamSpinnerBtn.setEnabled(true);
                }
                //cajStoreValue.setText(gson.toJson(CPCMAPZia.get(0)));
                camStoredBeanValue = CPPMAPZia.get(0);

                // Por cajas
                if (CPCMAPZia.get(CPPMAPZia.get(0)) != null && !CPCMAPZia.get(CPPMAPZia.get(0)).isEmpty()) {
                    cajSpin.setText(CPCMAPZia.get(CPPMAPZia.get(0)).get(0).getAtflv());
                    if (CPCMAPZia.get(CPPMAPZia.get(0)).size() > 1) {
                        cajSpin.setClickable(true);
                        cajSpin.setAlpha(1F);
                        //cajSpin.setEnabled(true);
                        idCajSpinnerBtn.setClickable(true);
                        idCajSpinnerBtn.setEnabled(true);
                    }
                }

                // Por piezas
                if (!PPCMAPZia.isEmpty()) {
                    pzSpin.setText(PPCMAPZia.get(0).getAtflv());
                    if (PPCMAPZia.size() > 1) {
                        pzSpin.setClickable(true);
                        pzSpin.setAlpha(1F);
                        //pzSpin.setEnabled(true);
                        idPzSpinnerBtn.setEnabled(true);
                        idPzSpinnerBtn.setClickable(true);
                        tableHandler7.setVisibility(View.VISIBLE);
                    } else {
                        tableHandler7.setVisibility(View.INVISIBLE);
                    }
                } else {
                    tableHandler7.setVisibility(View.INVISIBLE);
                }

                showTableQuantitys();
            } catch (Exception e) {
                showTableOffQuantitys();
            }
        } else {
            showTableOffQuantitys();
        }
    }

    public void reselectSpinnerCaj(View view) {
        ZIACST_I360_OBJECTDATA_SapEntity objectKey = camStoredBeanValue;
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View mLgort = layoutInflater.inflate(R.layout.ccp_cpp_selection_pop, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutSelection);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.Selection_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewSelection);
            headerView.setText("SELECCIONE CPC");

            for (final ZIACST_I360_OBJECTDATA_SapEntity entity : CPCMAPZia.get(objectKey)) {
                TableRow firstRow = new TableRow(context);
                LayoutInflater inInflater = LayoutInflater.from(context);
                View mSelection = inInflater.inflate(R.layout.selection_layout, null);
                final TextView selectionCurrentValue = mSelection.findViewById(R.id.selectionCurrentValue);
                selectionCurrentValue.setText(entity.getAtflv());
                final TextView selectionJsonObject = mSelection.findViewById(R.id.selectionJsonObject);
                selectionJsonObject.setText(gson.toJson(entity));
                CardView cardConfirm = mSelection.findViewById(R.id.cardSelectionClickZone);
                cardConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cajSpin.setText(selectionCurrentValue.getText().toString());
                        updateTableValues();
                        spinnerDialog.dismiss();
                    }
                });
                firstRow.addView(mSelection);
                tableLgort.addView(firstRow);
            }

            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });
            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }


    public void reselectSpinnerCam(View view) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View mLgort = layoutInflater.inflate(R.layout.ccp_cpp_selection_pop, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutSelection);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.Selection_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewSelection);
            headerView.setText("SELECCIONE CPP");
            for (final ZIACST_I360_OBJECTDATA_SapEntity entity : CPPMAPZia) {
                TableRow firstRow = new TableRow(context);
                LayoutInflater inInflater = LayoutInflater.from(context);
                View mSelection = inInflater.inflate(R.layout.selection_layout, null);
                final TextView selectionCurrentValue = mSelection.findViewById(R.id.selectionCurrentValue);
                selectionCurrentValue.setText(entity.getAtflv());

                CardView cardConfirm = mSelection.findViewById(R.id.cardSelectionClickZone);
                cardConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        camSpin.setText(selectionCurrentValue.getText().toString());
                        camStoredBeanValue = entity;
                        forceSetSpinnerCaj(entity);
                        updateTableValues();
                        spinnerDialog.dismiss();
                    }
                });
                firstRow.addView(mSelection);
                tableLgort.addView(firstRow);
            }

            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });


            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }

    public void reselectSpinnerPza(View view) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View mLgort = layoutInflater.inflate(R.layout.ccp_cpp_selection_pop, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutSelection);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.Selection_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewSelection);
            headerView.setText("SELECCIONE PPC");
            for (final ZIACST_I360_OBJECTDATA_SapEntity entity : PPCMAPZia) {
                TableRow firstRow = new TableRow(context);
                LayoutInflater inInflater = LayoutInflater.from(context);
                View mSelection = inInflater.inflate(R.layout.selection_layout, null);
                final TextView selectionCurrentValue = mSelection.findViewById(R.id.selectionCurrentValue);
                selectionCurrentValue.setText(entity.getAtflv());

                CardView cardConfirm = mSelection.findViewById(R.id.cardSelectionClickZone);
                cardConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pzSpin.setText(selectionCurrentValue.getText().toString());
                        updateTableValues();
                        spinnerDialog.dismiss();
                    }
                });
                firstRow.addView(mSelection);
                tableLgort.addView(firstRow);
            }

            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });


            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }

    public void forceSetSpinnerCaj(ZIACST_I360_OBJECTDATA_SapEntity entity) {
        try {
            if (CPCMAPZia.get(entity) != null && !CPCMAPZia.get(entity).isEmpty()) {
                cajSpin.setText(CPCMAPZia.get(entity).get(0).getAtflv());
                if (CPCMAPZia.get(entity).size() > 1) {
                    cajSpin.setClickable(true);
                    cajSpin.setEnabled(true);
                    idCajSpinnerBtn.setClickable(true);
                    idCajSpinnerBtn.setEnabled(true);
                }
            }
        } catch (Exception e) {
            showTableOffQuantitys();
        }
    }

    public void updateTableValues() {
        if (!cantidadTarima.getText().toString().equals("")) {
            cantidadTotalTarima.setText(String.valueOf((new BigDecimal(cantidadTarima.getText().toString()).
                    multiply(new BigDecimal(cajSpin.getText().toString())).multiply(new BigDecimal(camSpin.getText().toString()))).setScale(3, RoundingMode.HALF_UP)));
        }
        if (!cantidadCama.getText().toString().equals("")) {
            cantidadTotalCama.setText(String.valueOf((new BigDecimal(cantidadCama.getText().toString())
                    .multiply(new BigDecimal(cajSpin.getText().toString()))).setScale(3, RoundingMode.HALF_UP)));
        }
        sumPrevQuan();
    }

    public void reselectValuation(View view) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View mLgort = layoutInflater.inflate(R.layout.ccp_cpp_selection_pop, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutSelection);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.Selection_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewSelection);
            headerView.setText("SELECCIONE VALORACION");
            for (final E_ClassVal_SapEntity entity : e_classVal_sapEntities) {
                TableRow firstRow = new TableRow(context);
                LayoutInflater inInflater = LayoutInflater.from(context);
                View mSelection = inInflater.inflate(R.layout.selection_layout, null);
                final TextView selectionCurrentValue = mSelection.findViewById(R.id.selectionCurrentValue);
                selectionCurrentValue.setText(entity.getBwtar());

                CardView cardConfirm = mSelection.findViewById(R.id.cardSelectionClickZone);
                cardConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        classSpin.setText(selectionCurrentValue.getText().toString());
                        eClassValSapEntity = entity;
                        spinnerDialog.dismiss();
                    }
                });
                firstRow.addView(mSelection);
                tableLgort.addView(firstRow);
            }

            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });

            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }

    public void reselecEstatusPt(View view) {
        try {
            Map<String, String> mapStatus = new HashMap<>();
            mapStatus.put("fuera", "FUERA DE ESP.");
            mapStatus.put("riesgo", "EN RIESGO");
            mapStatus.put("en_tiempo", "EN TIEMPO");

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View mLgort = layoutInflater.inflate(R.layout.ccp_cpp_selection_pop, null);
            TableLayout tableLgort = mLgort.findViewById(R.id.tableLayoutSelection);
            ImageButton btnLgortCancel = mLgort.findViewById(R.id.Selection_operation_back);
            TextView headerView = mLgort.findViewById(R.id.headerTextViewSelection);
            headerView.setText("ESTATUS PT");

            mapStatus.entrySet().stream().forEach(e -> {
                TableRow firstRow = new TableRow(context);
                LayoutInflater inInflater = LayoutInflater.from(context);
                View mSelection = inInflater.inflate(R.layout.selection_layout, null);
                final TextView selectionCurrentValue = mSelection.findViewById(R.id.selectionCurrentValue);
                selectionCurrentValue.setText(e.getValue());

                CardView cardConfirm = mSelection.findViewById(R.id.cardSelectionClickZone);
                cardConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ptSpin.setText(selectionCurrentValue.getText().toString());
                        spinnerDialog.dismiss();
                        estatusPt = selectionCurrentValue.getText().toString();
                    }
                });
                firstRow.addView(mSelection);
                tableLgort.addView(firstRow);
            });

            btnLgortCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialog.dismiss();
                }
            });

            dialogBuilder.setView(mLgort);
            dialogBuilder.setCancelable(false);
            spinnerDialog = dialogBuilder.create();
            spinnerDialog.show();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al procesar su solicitud", Toast.LENGTH_LONG).show();
        }
    }

    public void showTableQuantitys() {
        onWindowFocusChanged(true);
        btnfnCarril.setVisibility(View.INVISIBLE);
        tableQuanLayout.setVisibility(View.VISIBLE);
        tableCamaRow.setVisibility(View.VISIBLE);
        tableTarimaRow.setVisibility(View.VISIBLE);
        if (!PPCMAPZia.isEmpty()) {
            tablePzRow.setVisibility(View.VISIBLE);
        }
        btnfnMaterial.setVisibility(View.VISIBLE);
        tableHandler4.setVisibility(View.VISIBLE);
        tableHandler5.setVisibility(View.VISIBLE);

        // Ocultar EstatusPt si es documento de frescura
        if (routeUser.getType().equals("4")) {
            tableHandler12.setVisibility(View.GONE);
            tableHandler13.setVisibility(View.VISIBLE);
        } else {
            tableHandler12.setVisibility(View.GONE);
            tableHandler13.setVisibility(View.GONE);
        }

        if (!PPCMAPZia.isEmpty()) {
            tableHandler7.setVisibility(View.VISIBLE);
        }
        btnfnCarril.setVisibility(View.GONE);
        activateDeactivateClassVal();
        MobileMaterialBean bean = routeModel.getSingleMaterialValue(new MaterialDescrptionBean(currentValue.getMatnr(), currentValue.getMatkx()));
        if (bean != null && bean.getMeins() != null) {
            if (PPCMAPZia.isEmpty()) {
                idUnitBoxTblLbl.setText(bean.getMeins());
                idTopBoxTblLbl.setText(bean.getMeins());
            } else {
                idUnitBoxTblLbl.setText("CAJA");
                idUnitEaTblLbl.setText(bean.getMeins());
                idTopBoxTblLbl.setText(bean.getMeins());
            }
        } else {
            idUnitBoxTblLbl.setText("UMB");
            idTopBoxTblLbl.setText("UMB");
        }


    }

    public void showTableOffQuantitys() {
        onWindowFocusChanged(true);
        tableQuanLayout.setVisibility(View.VISIBLE);
        btnfnMaterial.setVisibility(View.VISIBLE);
        tableHandler4.setVisibility(View.GONE);
        tableHandler5.setVisibility(View.GONE);
        tableHandler7.setVisibility(View.GONE);

        // Ocultar EstatusPt si es documento de frescura
        if (routeUser.getType().equals("4")) {
            tableHandler12.setVisibility(View.GONE);
            tableHandler13.setVisibility(View.VISIBLE);
        } else {
            tableHandler12.setVisibility(View.GONE);
            tableHandler13.setVisibility(View.GONE);
        }

        activateDeactivateClassVal();

        btnfnCarril.setVisibility(View.GONE);
        tableCamaRow.setVisibility(View.GONE);
        tablePzRow.setVisibility(View.GONE);
        tableTarimaRow.setVisibility(View.GONE);
        MobileMaterialBean bean = routeModel.getSingleMaterialValue(new MaterialDescrptionBean(currentValue.getMatnr(), currentValue.getMatkx()));
        if (bean != null && bean.getMeins() != null) {
            idUnitBoxTblLbl.setText(bean.getMeins());
            idTopBoxTblLbl.setText(bean.getMeins());
        } else {
            idUnitBoxTblLbl.setText("UMB");
            idTopBoxTblLbl.setText("UMB");

        }
    }

    public void activateDeactivateClassVal() {
        try {
            String valuation = CommonUtilities.PushGsonVariable(NOTVALUATION, context);
            if (valuation != null) {
                AbstractResults result = gson.fromJson(valuation, AbstractResults.class);
                if (result.getBooleanResult()) {
                    tableHandler8.setVisibility(View.VISIBLE);
                } else {
                    tableHandler8.setVisibility(View.GONE);
                }
            } else {
                tableHandler8.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            tableHandler8.setVisibility(View.GONE);
        }
    }

    public void setEraseMaterialData() {
        cancelLoading();
        cantidadTarima.setText("");
        cantidadCama.setText("");
        cantidadCaja.setText("");
        cantidadTotalTarima.setText("0");
        cantidadTotalCaja.setText("0");
        cantidadTotalCama.setText("0");
        cantidadTotalPz.setText("0");
        cantidadTotal.setText("0");
        flagMaterial = false;
        material.setEnabled(true);
        descripcionText.setText("");
        descripcionText.setEnabled(true);
        cameraButt.setEnabled(true);
        getNextClean();
        Toast.makeText(context, "Material no encontrado, ¡Favor de Validar!", Toast.LENGTH_LONG).show();
    }

    public void setEraseMaterialDataSpecial() {
        cancelLoading();
        cantidadTarima.setText("");
        cantidadCama.setText("");
        cantidadCaja.setText("");
        cantidadTotalTarima.setText("0");
        cantidadTotalCaja.setText("0");
        cantidadTotalCama.setText("0");
        cantidadTotalPz.setText("0");
        cantidadTotal.setText("0");
        flagMaterial = false;
        material.setEnabled(true);
        descripcionText.setText("");
        descripcionText.setEnabled(true);
        cameraButt.setEnabled(true);
        getNextClean();
    }


    public void setEraseMaterialDataNoConfirm() {
        cancelLoading();
        flagMaterial = false;
        material.setEnabled(true);
        descripcionText.setText("");
        descripcionText.setEnabled(true);
        cameraButt.setEnabled(true);
        getNextClean();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == ResponseVariability.SCANREQUEST && RESULT_OK == resultCode) {
            if (data != null) {
                try {
                    String materialValue = data.getStringExtra(ResponseVariability.SCAN_RESULT);
                    String[] materialValues = materialValue.split("\\,");
                    if (materialValues.length > 1) {
                        showMaterialSelector(materialValues);
                    } else {
                        if (materialValue.length() > 14) {
                            Toast.makeText(context, "Etiqueta no contiene informacion de Material", Toast.LENGTH_LONG).show();
                        } else {
                            List<MaterialDescrptionBean> listBean = routeModel.getMaterialByValues(new MaterialDescrptionBean(materialValue, ""));
                            setMaterialDataByList(listBean);
                        }
                    }
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    material.setText(data.getStringExtra(ResponseVariability.SCAN_RESULT));
                }
            } else {
                Log.i("Scanned Object: ", "Scan Failed");
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showMaterialSelector(String[] materialValues) {
        List<MaterialDescrptionBean> showList = new ArrayList<>();
        // Validamos por el lote
        if(materialValues[4] != null) {
            idLoteTxt.setText(materialValues[4].toUpperCase());
            lote = materialValues[4].toUpperCase();
        } else {
            idLoteTxt.setText("");
            lote = "";
        }

        // Validamos prodDate
        if(materialValues[4] != null) {
            prodDate = materialValues[5];
        } else {
            prodDate = "";
        }

        for (String materialString : materialValues) {
            if (materialString.length() == 7) {
                List<MaterialDescrptionBean> recoveredList = routeModel.getMaterialByValues(new MaterialDescrptionBean(materialString, ""));
                if (!recoveredList.isEmpty()) {
                    showList.addAll(recoveredList);
                } else {
                    showList.add(new MaterialDescrptionBean(materialString, ""));
                }
            } else if (materialString.length() == 8) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
                    Date date = sdf.parse(materialString);
                    textPrdLbl.setVisibility(View.VISIBLE);
                    textProduction.setVisibility(View.VISIBLE);
                    textProduction.setText(sdf2.format(date));
                } catch (Exception e) {
                    //Catching Ex - Nothing to chow
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                }

            } else if (materialString.length() == 10) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
                    Date date = sdf.parse(materialString);
                    textPrdLbl.setVisibility(View.VISIBLE);
                    textProduction.setVisibility(View.VISIBLE);
                    textProduction.setText(sdf2.format(date));
                } catch (Exception e) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
                        Date date = sdf.parse(materialString);
                        textPrdLbl.setVisibility(View.VISIBLE);
                        textProduction.setVisibility(View.VISIBLE);
                        textProduction.setText(sdf2.format(date));
                    } catch (Exception e1) {
                        //Catching Ex - Nothing to chow
                        CommonUtilities.loggerAPI(e, context);
                        CrashReporter.logException(e);
                    }

                }
            }
        }
        if (!showList.isEmpty()) {
            setMaterialDataByList(showList);
        } else {
            Toast.makeText(context, "La lectura no retorno un ID correcto de Material", Toast.LENGTH_LONG).show();
        }
    }


    boolean isTarima = false;

    public void getTarimaForMaterial() {
        cancelLoading();
        if (!isTarima) {
            MaterialDescrptionBean singleBean = new MaterialDescrptionBean();
            singleBean.setMatnr(material.getText().toString());
            List<TarimasDescriptionBean> beanList = routeModel.getTarimaByMaterial(singleBean);
            if (!beanList.isEmpty()) {
                isTarima = true;
                new CommonUtilities().requestHumanTarimasSelection(context, activity, "TARIMA", "SELECCIONE TARIMA:", new CommonUtilities.CustomCallBack<String>() {
                    @Override
                    public void customCallBack(String ret) {
                        if (ret != null) {
                            if (ret.equals(ResponseVariability.NOSELECTEDTARIMAS)) {
                                currentValue.setVhilm(ret);
                                isTarima = false;
                            } else {
                                TarimasDescriptionBean miniBean = gson.fromJson(ret, TarimasDescriptionBean.class);
                                currentValue.setVhilm(miniBean.getVhilm());
                                isTarima = false;
                            }
                        } else {
                            isTarima = false;
                        }
                    }
                }, beanList);
            } else {
                // No tiene Armado Posiblemente, Marcar para visualizar menos datos
            }
        }
    }


    public void setMaterialDataByList(List<MaterialDescrptionBean> beanList) {
        if (!beanList.isEmpty()) {
            if (beanList.size() == 1) {
                if (!beanList.get(0).getMaktx().isEmpty()) {
                    setMaterialData(beanList.get(0));
                } else {
                    setEraseMaterialData();
                }
            } else {
                new CommonUtilities().requestHumanMaterialSelection(context, activity, "Materiales...", "Seleccione Material Deseado: ", new CommonUtilities.CustomCallBack<String>() {
                    @Override
                    public void customCallBack(String ret) {
                        if (ret != null) {
                            MaterialDescrptionBean descriptionBean = gson.fromJson(ret, MaterialDescrptionBean.class);
                            if (descriptionBean.getMaktx() != null && !descriptionBean.getMaktx().isEmpty()) {
                                setMaterialData(descriptionBean);
                            } else {
                                setEraseMaterialData();
                            }
                        } else {
                            setEraseMaterialDataNoConfirm();
                        }
                    }
                }, beanList);
            }
        } else {
            setEraseMaterialData();
        }

    }


    public void showPopUpCalcTar(View view) {
        final TextView txtView = findViewById(R.id.idQuaHUTbltxt);
        final String oldValue = txtView.getText().toString().equals("") ? "0" : txtView.getText().toString();
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        material.clearFocus();

        new CommonUtilities().showCalcCount(oldValue, new CommonUtilities.CustomCallBack<String>() {
            @Override
            public void customCallBack(String ret) {
                if (ret != null) {
                    Log.i("Calculator: Not Null", ret);
                    txtView.setText(ret);
                    if (ret.equals("0")) {
                        cantidadTotalTarima.setText("0");
                        sumPrevQuan();
                    } else {
                        //MobileMaterialBean singleBean = cpcMap.get(camSpin.getText().toString());
                        cantidadTotalTarima.setText(String.valueOf((new BigDecimal(ret).multiply(new BigDecimal(cajSpin.getText().toString()))
                                .multiply(new BigDecimal(camSpin.getText().toString()))).setScale(3, RoundingMode.HALF_UP)));
                        sumPrevQuan();
                    }
                } else {
                    Log.i("Calculator: Null", oldValue);
                    txtView.setText(oldValue);
                }
            }
        }, activity, context, display);
    }


    public void showPopUpCalcBed(View view) {
        final TextView txtView = findViewById(R.id.idQuaBedTbltxt);
        final String oldValue = txtView.getText().toString().equals("") ? "0" : txtView.getText().toString();
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        material.clearFocus();

        new CommonUtilities().showCalcCount(oldValue, new CommonUtilities.CustomCallBack<String>() {
            @Override
            public void customCallBack(String ret) {
                if (ret != null) {
                    Log.i("Calculator: Not Null", ret);
                    txtView.setText(ret);
                    if (ret.equals("0")) {
                        cantidadTotalCama.setText("0");
                        sumPrevQuan();
                    } else {
                        cantidadTotalCama.setText(String.valueOf(new BigDecimal(ret).multiply(new BigDecimal(cajSpin.getText().toString())).setScale(3, RoundingMode.HALF_UP)));
                        sumPrevQuan();
                    }
                } else {
                    Log.i("Calculator: Null", oldValue);
                    txtView.setText(oldValue);
                }
            }
        }, activity, context, display);
    }

    public void showPopUpCalcUM(View view) {
        final TextView txtView = findViewById(R.id.idQuaBoxTbltxt);
        final String oldValue = txtView.getText().toString().equals("") ? "0" : txtView.getText().toString();
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        material.clearFocus();

        new CommonUtilities().showCalcCount(oldValue, new CommonUtilities.CustomCallBack<String>() {
            @Override
            public void customCallBack(String ret) {
                if (ret != null) {
                    Log.i("Calculator: Not Null", ret);
                    txtView.setText(ret);
                    cantidadTotalCaja.setText(ret);
                    sumPrevQuan();
                } else {
                    Log.i("Calculator: Null", oldValue);
                    txtView.setText(oldValue);
                }
            }
        }, activity, context, display);
    }


    public void showPopUpCalcPZ(View view) {
        final TextView txtView = findViewById(R.id.idQuaEaTbltxt);
        final String oldValue = txtView.getText().toString().equals("") ? "0" : txtView.getText().toString();
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        material.clearFocus();

        new CommonUtilities().showCalcCount(oldValue, new CommonUtilities.CustomCallBack<String>() {
            @Override
            public void customCallBack(String ret) {
                if (ret != null) {
                    Log.i("Calculator: Not Null", ret);
                    txtView.setText(ret);
                    cantidadTotalPz.setText(ret);
                    sumPrevQuan();
                } else {
                    Log.i("Calculator: Null", oldValue);
                    txtView.setText(oldValue);
                }
            }
        }, activity, context, display);
    }


    public void sumPrevQuan() {
        if (PPCMAPZia.isEmpty()) {
            cantidadTotal.setText(String.valueOf(new BigDecimal(cantidadTotalTarima.getText().toString()).
                    add(new BigDecimal(cantidadTotalCaja.getText().toString()).add(new BigDecimal(cantidadTotalCama.getText().toString()))).setScale(3, RoundingMode.HALF_UP)));
        } else {
            String value = String.valueOf(new BigDecimal(cantidadTotalTarima.getText().toString()).
                    add(new BigDecimal(cantidadTotalCaja.getText().toString()).add(new BigDecimal(cantidadTotalCama.getText().toString()))));
            BigDecimal mult = new BigDecimal(value).multiply(new BigDecimal(pzSpin.getText().toString())).setScale(3, RoundingMode.HALF_UP);
            cantidadTotal.setText(mult.add(new BigDecimal(cantidadTotalPz.getText().toString())).toString());
        }
    }

    public void onClickNote(View view) {
        material.clearFocus();
        try {
            new CommonUtilities().showPopUpNotes(activity, new CommonUtilities.CustomCallBack<String>() {
                @Override
                public void customCallBack(String ret) {
                    if (ret != null) {
                        currentValue.setMaterialNotes(ret);
                        closeKeyboard();
                    } else {
                        currentValue.setMaterialNotes(null);
                        closeKeyboard();
                    }
                }
            }, context, currentValue.getMaterialNotes());
            closeKeyboard();
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un problema: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickCalendar(View view) {
        new CommonUtilities().showPopDatePicker(activity, new CommonUtilities.CustomCallBack<String>() {
            @Override
            public void customCallBack(String ret) {
                if (ret != null) {
                    textProduction.setText(ret);
                    currentValue.setProdDate(ret);
                    textPrdLbl.setVisibility(View.VISIBLE);
                    textProduction.setVisibility(View.VISIBLE);
                } else {
                    textProduction.setText("");
                    currentValue.setProdDate("");
                    textPrdLbl.setVisibility(View.INVISIBLE);
                }
            }
        }, context);
    }


    public void showPopUpRoute(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refreshRoute:
                        startActivity(new Intent(context, RouteActivity.class));
                        break;
                    case R.id.informationRoute:
                        getPendingInformation();
                        break;
                    case R.id.cancelRoute:
                        cancelCurrentRoute();
                        break;
                    case R.id.zoneFinish:
                        finishCurrentZone();
                        break;
                    case R.id.notTarima:
                        globalNotTarima();
                        break;
                    case R.id.repeatLgpla:
                        repeatPreviousLgpla();
                        break;
                    case R.id.notClassVal:
                        globalNotClassValuation();
                        break;

                }
                return false;
            }
        });
        popup.inflate(R.menu.routemenu);
        popup.show();
    }


    public void repeatPreviousLgpla() {
        String prevValue = CommonUtilities.PushGsonVariable(LASTCOUNTEDLGPLA, context);
        if (prevValue != null) {
            if (!prevValue.isEmpty()) {
                try {
                    LastCountedLgpla lgpla = gson.fromJson(prevValue, LastCountedLgpla.class);
                    RouteUserPositionBean routeOper = routeUser.getPositions().get(routeStoredBean.getiPosRoute());
                    ZoneUserPositionsBean zoneOperV;
                    if (routeStoredBean.getiPosLgpla() != null) {
                        zoneOperV = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosLgpla());
                    } else {
                        zoneOperV = routeOper.getZone().getPositionsB().get(routeStoredBean.getiPosZone());
                    }
                    zoneOperV.setLgplaValues(lgpla.getLastCounted());
                    CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
                    finishAndRemoveTask();
                    startActivity(new Intent(activity, RouteActivity.class));
                } catch (Exception e) {
                    CommonUtilities.loggerAPI(e, context);
                    CrashReporter.logException(e);
                    Toast.makeText(context, "Ocurrio un error al tratar de replicar el conteo anterior", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void globalNotTarima() {

        if (isTarimaEnabled()) {
            CommonUtilities.CustomConfirmDialog("Advertencia!", "Realizar esta accion eliminara la solicitud de tarima " +
                    "\n ¿Desea Continuar?", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                @Override
                public void customCallBack(Integer ret) {
                    if (ret == ResponseVariability.SUCCESSFULL) {
                        CommonUtilities.CustomConfirmDialog("Precaucion!", "¿Esta seguro de realizar esta acción?",
                                activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                    @Override
                                    public void customCallBack(Integer ret) {
                                        if (ret == ResponseVariability.SUCCESSFULL) {
                                            AbstractResults result = new AbstractResults();
                                            result.setBooleanResult(false);
                                            CommonUtilities.UpdateStoreGSonVariable(NOTTARIMA, gson.toJson(result), context);
                                        }
                                    }
                                }, R.drawable.ic_exclamation_white_36dp, context);
                    }
                }
            }, R.drawable.ic_exclamation_white_36dp, context);
        } else {

            CommonUtilities.CustomConfirmDialog("Advertencia!", "Realizar esta accion reactivara la solicitud de tarima " +
                    "\n ¿Desea Continuar?", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                @Override
                public void customCallBack(Integer ret) {
                    if (ret == ResponseVariability.SUCCESSFULL) {
                        CommonUtilities.CustomConfirmDialog("Precaucion!", "¿Esta seguro de realizar esta acción?",
                                activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                    @Override
                                    public void customCallBack(Integer ret) {
                                        if (ret == ResponseVariability.SUCCESSFULL) {
                                            AbstractResults result = new AbstractResults();
                                            result.setBooleanResult(true);
                                            CommonUtilities.UpdateStoreGSonVariable(NOTTARIMA, gson.toJson(result), context);
                                        }
                                    }
                                }, R.drawable.ic_exclamation_white_36dp, context);
                    }
                }
            }, R.drawable.ic_exclamation_white_36dp, context);

        }


    }


    public Boolean isTarimaEnabled() {
        String isTarima = CommonUtilities.PushGsonVariable(CommunicationObjects.NOTTARIMA, context);
        Boolean tarEnabled = true;
        if (isTarima != null && !isTarima.isEmpty()) {
            AbstractResults tarima = gson.fromJson(isTarima, AbstractResults.class);
            tarEnabled = tarima.getBooleanResult();
        }
        return tarEnabled;
    }


    public void globalNotClassValuation() {
        if (!isValuationEnabled()) {
            CommonUtilities.CustomConfirmDialog("Advertencia!", "Realizar esta accion activara los objetos de valorización " +
                    "\n ¿Desea Continuar?", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                @Override
                public void customCallBack(Integer ret) {
                    if (ret == ResponseVariability.SUCCESSFULL) {
                        CommonUtilities.CustomConfirmDialog("Precaucion!", "¿Esta seguro de realizar esta acción?",
                                activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                    @Override
                                    public void customCallBack(Integer ret) {
                                        if (ret == ResponseVariability.SUCCESSFULL) {
                                            AbstractResults result = new AbstractResults();
                                            result.setBooleanResult(true);
                                            CommonUtilities.UpdateStoreGSonVariable(NOTVALUATION, gson.toJson(result), context);
                                            finishAndRemoveTask();
                                            startActivity(new Intent(activity, RouteActivity.class));
                                        }
                                    }
                                }, R.drawable.ic_exclamation_white_36dp, context);
                    }
                }
            }, R.drawable.ic_exclamation_white_36dp, context);
        } else {
            CommonUtilities.CustomConfirmDialog("Advertencia!", "Realizar esta desactivara los objetos de valorización " +
                    "\n ¿Desea Continuar?", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                @Override
                public void customCallBack(Integer ret) {
                    if (ret == ResponseVariability.SUCCESSFULL) {
                        CommonUtilities.CustomConfirmDialog("Precaucion!", "¿Esta seguro de realizar esta acción?",
                                activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                    @Override
                                    public void customCallBack(Integer ret) {
                                        if (ret == ResponseVariability.SUCCESSFULL) {
                                            AbstractResults result = new AbstractResults();
                                            result.setBooleanResult(false);
                                            CommonUtilities.UpdateStoreGSonVariable(NOTVALUATION, gson.toJson(result), context);
                                            finishAndRemoveTask();
                                            startActivity(new Intent(activity, RouteActivity.class));
                                        }
                                    }
                                }, R.drawable.ic_exclamation_white_36dp, context);
                    }
                }
            }, R.drawable.ic_exclamation_white_36dp, context);

        }
    }

    public Boolean isValuationEnabled() {
        String isValuation = CommonUtilities.PushGsonVariable(CommunicationObjects.NOTVALUATION, context);
        Boolean tarEnabled = false;
        if (isValuation != null && !isValuation.isEmpty()) {
            AbstractResults tarima = gson.fromJson(isValuation, AbstractResults.class);
            tarEnabled = tarima.getBooleanResult();
        }
        return tarEnabled;
    }

    public void getPendingInformation() {

        CommonUtilities.CustomPendientesDialog("Conteo Pendiente!", getPendingData(), activity, null,
                null, null, null, null, context);
    }

    public String getPendingData() {
        StringBuilder data = new StringBuilder();
        for (RouteUserPositionBean routePosition : routeUser.getPositions()) {
            data.append(routePosition.getZone().getZdesc() + "\n");
            int totalPosition = routePosition.getZone().getPositionsB().size();
            for (ZoneUserPositionsBean zonePostion : routePosition.getZone().getPositionsB()) {
                if (zonePostion.getIdDone()) {
                    totalPosition = totalPosition - 1;
                }
            }
            data.append("\tCarriles Pendientes: " + totalPosition + "\n");
        }
        return data.toString();
    }

    public void finishCurrentZone() {

        CommonUtilities.CustomConfirmDialog("Advertencia!", "Se concluira la zona actual" +
                "\n ¿Desea Continuar?", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
            @Override
            public void customCallBack(Integer ret) {
                if (ret == ResponseVariability.SUCCESSFULL) {
                    CommonUtilities.CustomConfirmDialog("Precaucion!", "¿Esta seguro de cancelar la zona actual?" +
                            "\nUna vez finalizada no podra volver", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                        @Override
                        public void customCallBack(Integer ret) {
                            if (ret == ResponseVariability.SUCCESSFULL) {
                                finishCurrentZoneProc();
                            }
                        }
                    }, R.drawable.ic_exclamation_white_36dp, context);
                }
            }
        }, R.drawable.ic_exclamation_white_36dp, context);
    }

    public void cancelCurrentRoute() {

        CommonUtilities.CustomConfirmDialog("Advertencia!", "Se perderan todos los avances de la tarea actual" +
                "\n ¿Desea Continuar?", activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
            @Override
            public void customCallBack(Integer ret) {
                if (ret == ResponseVariability.SUCCESSFULL) {
                    CommonUtilities.CustomConfirmDialog("Precaucion!", "¿Esta seguro de cancelar la tarea actual?"
                            , activity, null, null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        CommonUtilities.cancelPreviewValues(context, null);
                                        startActivity(new Intent(activity, UpDownActivity.class));
                                    }
                                }
                            }, R.drawable.ic_exclamation_white_36dp, context);
                }
            }
        }, R.drawable.ic_exclamation_white_36dp, context);
    }

    public void finishCurrentZoneProc() {
        try {
            Long finishedZone = new Date().getTime();
            routeUser.getPositions().get(routeStoredBean.getiPosRoute()).setDone(true);
            for (ZoneUserPositionsBean position : routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB()) {
                if (!position.getIdDone()) {
                    position.setIdDone(true);
                    if (position.getLgplaValues() != null && !position.getLgplaValues().isEmpty()) {
                        for (Map.Entry<String, LgplaValuesBean> map : position.getLgplaValues().entrySet()) {
                            if (map.getValue().getTotalConverted() != null && !map.getValue().getTotalConverted().isEmpty()) {
                                //DoNothing
                            } else {
                                map.getValue().setDateEnd(finishedZone);
                                map.getValue().setDateStart(finishedZone);
                            }
                        }
                    }
                }
            }
            if (hasMoreZonesLgort()) {
                routeStoredBean.setiPosZone(0);
                routeStoredBean.setiPosLgpla(null);
                routeStoredBean.setiPosRoute(newIposRoute);
                routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(routeStoredBean.getiPosRoute()).getZone().getPositionsB().size() - 1);
                CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
                CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
                startActivity(new Intent(activity, RouteActivity.class));
            } else {
                if (!getNextLgort()) {
                    if (isCompleted == null) {
                        isCompleted = new TaskCompleted();
                    }
                    isCompleted.setTaskCompleted(true);
                    isCompleted.setTaskUploaded(false);
                    routeUser.setDateEnd(new Date().getTime());
                    CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
                    CommonUtilities.UpdateStoreGSonVariable(TASKCOMPLETED, gson.toJson(isCompleted), context);
                    Toast.makeText(context, "Conteo Finalizado!", Toast.LENGTH_SHORT);
                    startActivity(new Intent(activity, UpDownActivity.class));
                } else {
                    routeUser.getPositions().get(routeStoredBean.getiPosRoute()).setDone(true);
                    routeStoredBean.setiPosRoute(newIposRoute);
                    routeStoredBean.setiPosZone(0);
                    routeStoredBean.setiPosLgpla(null);
                    routeStoredBean.setiMaxPosZone(routeUser.getPositions().get(newIposRoute).getZone().getPositionsB().size() - 1);
                    CommonUtilities.UpdateStoreGSonVariable(STOREDROUTE, gson.toJson(routeUser), context);
                    CommonUtilities.UpdateStoreGSonVariable(PREVIEWVALUES, gson.toJson(routeStoredBean), context);
                    startActivity(new Intent(activity, RouteActivity.class));
                }
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
            Toast.makeText(context, "Ocurrio un error al ejecutar la finalizacion de zona... Contacte administrador", Toast.LENGTH_LONG).show();
        }
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

    public void eventCloseKeyboard(View view) {
        try {
            closeKeyboard();
            onWindowFocusChanged(true);
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
        }
    }

    public void closeKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                onWindowFocusChanged(true);
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e, context);
            CrashReporter.logException(e);
        }
    }


    //Interface Clases


}


