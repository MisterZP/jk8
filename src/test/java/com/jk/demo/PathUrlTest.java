package com.jk.demo;

import java.io.File;
import java.text.MessageFormat;

/**
 * Created by zengping on 2017/4/11.
 */
public class PathUrlTest {

    public static void main(String[] args) {
        class CdnCallBackResp{
            private String status;
            private String msg;
            private String path;

            public CdnCallBackResp(String status, String msg, String path) {
                this.status = status;
                this.msg = msg;
                this.path = path;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
        String downloadRoot = "http://api.open.letvstore.com/apk-web/download/";
        String fileRealPath = "/apk/{0}";
        CdnCallBackResp cbr = new CdnCallBackResp("SUCCESS", "ok", "http://api.open.letvstore.com/apk-web/download/230/package/2017/04/11/com.pafinancialtech.huaruibank-1491842005305.apk");
        String interim_path = cbr.getPath().replace(downloadRoot, "");
        String fileProxyPort = interim_path.substring(0, interim_path.indexOf('/'));
        String fileProxyReal = MessageFormat.format(fileRealPath, fileProxyPort);
        File locale_file = new File(cbr.getPath().replace(downloadRoot + fileProxyPort, fileProxyReal));
        if(locale_file.exists() && locale_file.isFile())
            locale_file.delete();
        System.out.println("delete local file url: " + locale_file.getAbsolutePath());
    }
}
