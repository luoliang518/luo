package com.luo.tomcat;

import java.io.OutputStream;

public class LuoResponse {
    public OutputStream outputStream;
 
    public static final String responseBody="HTTP/1.1 200+\r\n"+"Content-Type：text/html+\r\n"
            +"\r\n";
    public LuoResponse(OutputStream outputStream){
        this.outputStream=outputStream;
    }
}