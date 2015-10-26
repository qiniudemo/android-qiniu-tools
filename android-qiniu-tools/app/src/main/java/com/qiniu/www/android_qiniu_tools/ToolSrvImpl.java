package com.qiniu.www.android_qiniu_tools;


import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.*;


/**
 * Created by Yuting on 2015/9/18.
 */
public class ToolSrvImpl  implements ToolSrv{
    private static List<Observer> observerList = new ArrayList<Observer>();

    @Override
    public void addObserver(Observer observer) {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    public void cleanOldData() {
        for (Observer observer : observerList) {
            observer.cleanScreen();
        }
    }

    public void changePingPage(String str, int status) {
        for (Observer observer : observerList) {
            observer.pingPageChange(str, status);
            Log.i("ping", str);
        }
    }

    @Override
    public void ping(final String ipAddress) {
        Log.i("ping", "进入ping方法");
        new Thread(new Runnable() {
            @Override
            public void run() {
                cleanOldData();// 调用观察者，清除屏幕上的数据
                StringBuffer result = new StringBuffer();
                String str;
                try {
                    String cmdPing = "/system/bin/ping -c 5 -w 15   " + ipAddress;
                    Log.i("ping", "执行命令前"+ipAddress);
                   Process p = Runtime.getRuntime().exec(cmdPing);
                    Log.i("ping", "执行命令后");
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((str = bufferReader.readLine()) != null) {
                        Log.i("ping", str);
                        changePingPage(str, 0);
                    }
                    int status = p.waitFor();// 只有status=0时，正常
                    if (status == 0) {
                        str = "success";
                        Log.i("ping", str);
                        Log.i("ping", "执行成功");
                        email(MainActivity.result.toString(),ipAddress);
                    } else {
                        Log.i("ping", "执行失败");
                        Log.i("ping", "全局变量获取数据" );
                        email(MainActivity.result.toString(),ipAddress);

                    }
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void chanUrlPage(int status) {
        for (Observer observer : observerList) {
            observer.urlPageChange(status);
        }
    }

    @Override
    public void callUrl() {
        chanUrlPage(0);
    }

    public void changeTraceroutePage(String str, int status) {
        for (Observer observer : observerList) {
            observer.traceroutePageChange(str, status);
        }
    }

    @Override
    public void ip() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer result = new StringBuffer();
                String str;
                URL infoUrl = null;
                InputStream inStream = null;
                try
                {
                    //http://iframe.ip138.com/ic.asp
                    //infoUrl = new URL("http://city.ip138.com/city0.asp");
                    long now = System.currentTimeMillis();
                    Random ra =new Random();

                    int rb=ra.nextInt(900)+100;
                    Log.i("ping", "ip" +now);
                    infoUrl = new URL("http://7563614540466"+rb+".testns.cdnunion.net/?callback=jQuery18102853321498259902_1442981784438&_="+now);
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection)connection;
                    int responseCode = httpConnection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK)
                    {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));
                        StringBuilder strber = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null)
                            strber.append(line + "\n");
                        inStream.close();
                        //从反馈的结果中提取出IP地址
                        Log.i("ping", "ip" +strber);
                       // int start = strber.indexOf("ip：");
                       // int end = 13;
                     //   line = strber.substring(start + 1, end);
                        changePingPage(strber.toString(), 0);

                    }
                }
                catch(MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void email( final String s, final String title) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("mail", "进入发送邮件");
                StringBuffer result = new StringBuffer();
                String str;
                URL infoUrl = null;
                InputStream inStream = null;

                    MailSenderInfo mailInfo = new MailSenderInfo();
                    mailInfo.setMailServerHost("smtp.exmail.qq.com");
                    mailInfo.setMailServerPort("25");
                    mailInfo.setValidate(true);
                    //填写发送的邮箱的地址
                    mailInfo.setUserName("");
                   //您的邮箱密码
                    mailInfo.setPassword("");
                    mailInfo.setFromAddress("");
                    mailInfo.setToAddress("");
                    mailInfo.setSubject("七牛网络测试-"+title);
                    mailInfo.setContent(s);
                Log.i("mail", "设置邮件配置成功");
                    //这个类主要来发送邮件
                    SimpleMailSender sms = new SimpleMailSender();
                    sms.sendTextMail(mailInfo);//发送文体格式
                   // sms.sendHtmlMail(mailInfo);//发送html格式
                }

        }).start();
    }
    @Override
    public void traceroute(final String ipAddress) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer result = new StringBuffer();
                String str;
                try {
                    Log.i("traceroute", "执行traceroute");
                    String cmdTraceroute = "/system/xbin/traceroute " + "liuhanlin-work.qiniudn.com";
                    Log.i("traceroute", "执行命令前"+ipAddress);
                    Process p = Runtime.getRuntime().exec(cmdTraceroute);
                    Log.i("traceroute", "执行命令后"+ipAddress);
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    Log.i("traceroute", "执行读取"+ipAddress);
                    while ((str = bufferReader.readLine()) != null) {
                        changeTraceroutePage(str, 0);
                    }
                    int status = p.waitFor();// 只有status=0时，正常
                    Log.i("traceroute", "执行traceroute失败status"+status);
                    bufferReader.close();
                } catch (IOException e) {
                    Log.i("traceroute", "执行traceroute失败1");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("traceroute", "执行traceroute失败2");
                }
            }
        }).start();
    }

}

