package com.brioal.webservicetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 命名空间
                String nameSpace = "http://WebXml.com.cn/";
                // 调用方法的名称
                String methodName = "getMobileCodeInfo";
                // EndPoint
                String endPoint = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx";
                // SOAP Action
                String soapAction = "http://WebXml.com.cn/getMobileCodeInfo";
                // 指定WebService的命名空间和调用方法
                SoapObject soapObject = new SoapObject(nameSpace, methodName);
                // 设置需要调用WebService接口的两个参数mobileCode UserId
                soapObject.addProperty("mobileCode", "18293107086");
                soapObject.addProperty("userId", "");
                // 生成调用WebService方法调用的soap信息，并且指定Soap版本
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER10);
                envelope.bodyOut = soapObject;
                // 是否调用DotNet开发的WebService
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);
                HttpTransportSE transport = new HttpTransportSE(endPoint);
                try {
                    transport.call(soapAction, envelope);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 获取返回的数据
                SoapObject object = (SoapObject) envelope.bodyIn;
                // 获取返回的结果
                System.out.println(object.getProperty(0).toString());
            }
        }).start();
    }
}
