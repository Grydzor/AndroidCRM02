package com.grydzor.androidcrm02.settings;


public class Settings {
    public static String url = "192.168.1.198";
    public static String port = "8085";
    public static String fullUrl = "http://" + url + ":" + port + "/";

    public static void ctreateUrl(String url1, String port1){
        Settings.fullUrl = "http://" + url1 + ":" + port1 + "/";
    }
}
