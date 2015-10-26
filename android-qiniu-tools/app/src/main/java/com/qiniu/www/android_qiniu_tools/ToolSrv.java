package com.qiniu.www.android_qiniu_tools;

/**
 * Created by Yuting on 2015/9/18.
 */
import com.qiniu.www.android_qiniu_tools.Observer;
    public interface ToolSrv {
        public void addObserver(Observer observer);

        public void ping(String ipAddress);

        public void callUrl();

        public void traceroute(String ipAddress);
        public void ip();
    }

