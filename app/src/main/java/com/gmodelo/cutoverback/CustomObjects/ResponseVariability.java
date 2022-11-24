package com.gmodelo.cutoverback.CustomObjects;

import com.gmodelo.cutoverback.beans.ServerBean;

import java.util.HashMap;

public class ResponseVariability {


    public static final int SUCCESSFULL = 1;
    public static final int NOTFOUND = 2;
    public static final int CREATED = 3;
    public static final int EXISTING = 4;
    public static final int STORED = 5;
    public static final int REMOVED = 6;
    public static final int UPDATED = 7;
    public static final int ERROR = 9;
    public static final int IPASSWORDNOTMATCH = -101;
    public static final int SESSIONNOTFOUND = -106;
    public static final int IUSERNOTTASK = -107;
    public static final int ITASKCLOSED = -109;
    public static final int IREPKEYNOTFOUND = -405;
    public static final int NOSELECTEDTARIMA = -987;
    public static final String NOSELECTEDTARIMAS = "-987";
    public static final String S_ERROR = "!ERROR";
    public static final int LASTNONINDEX = -1;
    public static final Integer EXCEPTION = 10405;
    public static final Integer SCANREQUEST = 100;
    public static final Integer NOVALUESELECTED = -257;
    public static final String SCAN_RESULT = "SCAN_RESULT";
    public static final String SCAN_TEXT = "Escanear Codigo";
    public static final Integer CUSTOMUPDATEDAYS = 7;
    public static final Integer CURRENTSERVERMAP = 3;
    public static final String INITIAL_OFFSET_VAL = "150000";
    public static final Integer TIMEOUTINMILLIS  = 150000;
    public static final String INITIAL_OFFSET = "INITIAL_OFFSET";
    public static final String ROW_COUNT = "ROW_COUNT";

    //Operations

    public static final String INV_E_MATERIALS = "MATNR";
    public static final String INV_E_TARIMAS = "VHILM";
    public static final String INV_E_CLASS_SYSTEM = "CLASS";
    public static final String INV_E_CLASS_VAL = "CVAL";


    // Recovery Variables

    public static final Integer DEV_INV_E_ID = 1;
    public static final Integer PRD_INV_E_ID = 2;


    public static final HashMap<Integer, ServerBean> RELATIONINVESERVER;

    static {
        RELATIONINVESERVER = new HashMap<Integer, ServerBean>();
        RELATIONINVESERVER.put(DEV_INV_E_ID, new ServerBean("https://carosolutions-dev.ab-inbev.com", "444", "Inventarios Ciclicos DEV"));
        RELATIONINVESERVER.put(PRD_INV_E_ID, new ServerBean("https://carosolutions.ab-inbev.com", "444", "Inventarios Ciclicos PRD"));
    }


    public static final HashMap<Integer, String> ADMINISTRATOROPERATION;

    static {
        ADMINISTRATOROPERATION = new HashMap<Integer, String>();
        ADMINISTRATOROPERATION.put(1, "RESTAURAR DATOS");
        ADMINISTRATOROPERATION.put(2, "ENVIAR LOGS");
        ADMINISTRATOROPERATION.put(3, "ID DISPOSITIVO");
        ADMINISTRATOROPERATION.put(4, "TABLE FILLED");
    }


    public static final HashMap<String, Integer> INV_E_COUNTED_VALUES;

    static {
        INV_E_COUNTED_VALUES = new HashMap<String, Integer>();
        INV_E_COUNTED_VALUES.put(INV_E_MATERIALS, 0);
        INV_E_COUNTED_VALUES.put(INV_E_TARIMAS, 0);
        INV_E_COUNTED_VALUES.put(INV_E_CLASS_SYSTEM, 0);
        INV_E_COUNTED_VALUES.put(INV_E_CLASS_VAL, 0);
    }


}
