package com.qiniu.www.android_qiniu_tools;

/**
 * Created by Yuting on 2015/9/29.
 */

import java.io.Serializable;
public class TracerouteContainer implements Serializable {
    private static final long serialVersionUID = 1034744411998219581L;

    private String hostname;
    private String ip;
    private float ms;
    private boolean isSuccessful;

    public TracerouteContainer(String hostname, String ip, float ms,boolean isSuccessful) {
        this.hostname = hostname;
        this.ip = ip;
        this.ms = ms;
        this.isSuccessful = isSuccessful;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public float getMs() {
        return ms;
    }

    public void setMs(float ms) {
        this.ms = ms;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    @Override
    public String toString() {
        return "Traceroute : \nHostname : " + hostname + "\nip : " + ip + "\nMilliseconds : " + ms;
    }
}
