package com.qiniu.www.android_qiniu_tools;

/**
 * Created by Yuting on 2015/9/18.
 */
public abstract class Observer {
    public void pingPageChange(String str, int status) {
    };

    public void cleanScreen() {
    };

    public void urlPageChange(int status) {
    };

    public void traceroutePageChange(String str, int status) {
    }

}