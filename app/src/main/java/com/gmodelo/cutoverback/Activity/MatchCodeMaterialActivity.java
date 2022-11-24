package com.gmodelo.cutoverback.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.balsikandar.crashreporter.CrashReporter;
import com.gmodelo.cutoverback.CustomObjects.ResponseVariability;
import com.gmodelo.cutoverback.DaoBeans.MaterialDescrptionBean;
import com.gmodelo.cutoverback.DataAccess.InstanceOfDB;
import com.gmodelo.cutoverback.Utilities.CommonUtilities;
import com.gmodelo.cutoverback.Views.RouteViewModel;
import com.google.gson.Gson;

import java.util.List;

public class MatchCodeMaterialActivity extends AppCompatActivity {

    View decorView;

    FrameLayout progressBarHolder;
    EditText materialTextEdit;
    ImageButton searchMaterialBtn, materiales_operation_back;
    TableLayout materialTableWrapper;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    Gson gson;
    Context context;
    Activity activity;
    RouteViewModel routeModel;
    InstanceOfDB dbInstance;


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

        setContentView(R.layout.activity_match_code_material);
        context = this;
        activity = this;
        gson = new Gson();
        dbInstance = InstanceOfDB.getInstanceOfDB(context);
        routeModel = ViewModelProviders.of(this).get(RouteViewModel.class);
        materialTextEdit = findViewById(R.id.materialTextEdit);
        searchMaterialBtn = findViewById(R.id.searchMaterialBtn);
        materialTableWrapper = findViewById(R.id.materialTableWrapper);

        materialTextEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    materialTextEdit.setSelection(materialTextEdit.getText().length());
                }
            }
        });


        searchMaterialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!materialTextEdit.getText().toString().isEmpty() && !materialTextEdit.getText().toString().equals("")) {
                    doSearch(materialTextEdit.getText().toString());
                } else {
                    CommonUtilities.CustomConfirmDialog("Advertencia", "La busqueda retorna muchos registros \n¿Desea Continuar?", activity, null,
                            null, null, new CommonUtilities.CustomCallBack<Integer>() {
                                @Override
                                public void customCallBack(Integer ret) {
                                    if (ret == ResponseVariability.SUCCESSFULL) {
                                        doSearch("");
                                    }
                                }
                            }, null, context);
                }
            }
        });

        progressBarHolder = findViewById(R.id.marerialProgLayout);
        materiales_operation_back = findViewById(R.id.materiales_operation_back);
        materiales_operation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RouteActivity.class));
            }
        });


    }

    public void doSearch(String materialSearch) {
        initiateLoading();
        new doMaterialSearch().execute(materialSearch);
    }

    private class doMaterialSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String material) {
            List<MaterialDescrptionBean> materialDescrptionBeans = routeModel.getMaterialByMatch(material);
            recoverMaterialList(materialDescrptionBeans);
        }
    }

    public void recoverMaterialList(List<MaterialDescrptionBean> descrptionBeans) {
        try {
            materialTableWrapper.removeAllViews();
            if (!descrptionBeans.isEmpty()) {
                LayoutInflater inflater = LayoutInflater.from(context);
                for (final MaterialDescrptionBean singleBean : descrptionBeans) {
                    TableRow rowWrapper = new TableRow(context);
                    View mView = inflater.inflate(R.layout.material_search_layout, null);
                    TextView materialId = mView.findViewById(R.id.materialListId);
                    TextView materialDesc = mView.findViewById(R.id.materialListDesc);
                    CardView cardConfirm = mView.findViewById(R.id.cardMaterialClickZone);
                    materialId.setText(singleBean.getMatnr());
                    materialDesc.setText(singleBean.getMaktx());
                    cardConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            initiateLoading();
                            startActivity(new Intent(activity, RouteActivity.class).putExtra("MaterialDescription",
                                    gson.toJson(new MaterialDescrptionBean(singleBean.getMatnr(), singleBean.getMaktx()))));
                        }
                    });
                    rowWrapper.addView(mView);
                    materialTableWrapper.addView(rowWrapper);
                }
                cancelLoading();
                eventCloseKeyboard(null);
            } else {
                cancelLoading();
                eventCloseKeyboard(null);
                Toast.makeText(context, "No se encontraron materiales con su busqueda", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            CommonUtilities.loggerAPI(e,context);
            CrashReporter.logException(e);
            Toast.makeText(context, "No se encontraron materiales con su busqueda", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        //Do Notghing
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
        closeKeyboard();
        onWindowFocusChanged(true);
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