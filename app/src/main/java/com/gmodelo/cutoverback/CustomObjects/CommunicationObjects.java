package com.gmodelo.cutoverback.CustomObjects;

public class CommunicationObjects {

    // Stored Cellphone Values
    public final static String STOREDLOGIN = "StoredLogin";
    public final static String STOREDROUTE = "StoredRoute";
    public final static String LASTFETCHED = "LastFetched";
    public final static String TASKCOMPLETED = "TaskCompleted";
    public final static String PREVIEWVALUES = "PreviewStoredRouteValues";
    public final static String SAPSPECIALCOUNT = "SapSpecialCount";
    public final static String SERVERVALUE = "ServerValue";
    public final static String SERVERMAPPING = "ServerMapping";
    public final static String CURRENTSERVER = "CurrentServerID";
    public final static String NOTTARIMA = "IsTarimaEnabled";
    public final static String LASTCOUNTEDLGPLA = "LastCountedLgpla";
    public final static String NOTVALUATION = "IsValuationEnabled";
    public final static String LOGMAPPER = "LogMapper";
    public final static String DEVICEID = "ApplicationRegisteredUUID";
    public final static String COUNTMAPPER = "CountedMap";
    public final static String TOKEN_KEY = "Token";

    //SERVICE REQUEST VALUES
    // /cutover-back
    // /cutover-back
    public static final String LOGINMODULE = "/cutover-back/LoginModule/login";
    public static final String UPDATEAPPMODULE = "/cutover-back/VersionModule/getVersion";
    public static final String DOWNLOADMOBILEDATA = "/cutover-back/services/DownloadService/OffsetMobileData";
    public static final String DOWNLOADALLMOBILEDATA = "/cutover-back/services/DownloadService/GetAllMobileData";
    public static final String UPDATEMOBILEDATA = "/cutover-back/services/DownloadService/GetDeltaMobileData";
    public static final String GETCOUNTEDVALUES = "/cutover-back/services/DownloadService/GetCountedValues";
    public static final String AUTOTASKMODULE = "/cutover-back/services/RouteService/autoTaskByUser";
    public static final String DOWNLOADROUTEMODULE = "/cutover-back/services/RouteService/getRoutesByUser";
    public static final String UPLOADROUTEMODULE = "/cutover-back/services/CountsService/addCount";
    public static final String VALIDATEADMIN = "/cutover-back/VersionModule/validateAdmin";
    public static final String VALIDATEUSABLESERVER = "/cutover-back/VersionModule/isServerUsable";
    public static final String GETEXTRAMOBILEDADA = "/cutover-back/services/DownloadService/GetMobileData";
    public static final String UPDATELOGSTOSERVER = "/cutover-back/VersionModule/saveExceptionApp";

}
