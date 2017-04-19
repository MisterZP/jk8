package com.jk.demo;

import com.jk.demo.domain.XmlAttribute;
import com.jk.demo.domain.XmlElement;
import com.jk.demo.enums.ApkAttrEnum;
import com.jk.demo.enums.ApkAttrValueEnum;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zengping on 2017/3/10.
 */
public class AaptParse {
    private static final String DEAFULTPATH = "AndroidManifest.xml";
    private static final String CHARSET = "UTF-8";
    private static final String DEFAULT_NAME = "unresolved_a";
    private static final String AAPT_SCRIPT = "aapt dump xmltree {0} {1}";
    private static final String ELEMENT_MARK = "E: ";
    private static final String ATTRBUT_MARK = "A: ";
    private static final String ELE_PREFIX = "android:";
    private static final int STEP_BASE = 2;
    private static final Pattern MATCHER_TYPE = Pattern.compile("\\(0x[0-9a-fA-F]*\\)");
    private static final Pattern VALUE_TYPE = Pattern.compile("\\(type 0x[0-9a-fA-F]*\\)");
    private static final Pattern VALUE_CONTENT = Pattern.compile("\"[$:@0-9a-zA-z._-\\u4e00-\\u9fa5]*\"");

    public static String loadApk4Path(String filePath, String apkXmlResource) {
        Process process = null;
        BufferedReader proBufferReader = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"cmd", "/C", MessageFormat.format(AAPT_SCRIPT, filePath, StringUtils.isNotBlank(apkXmlResource) ? apkXmlResource : DEAFULTPATH)});
            proBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), CHARSET));
            String line;
            StringBuilder aaptRes = new StringBuilder();
            while (null != (line = proBufferReader.readLine())) {
                aaptRes.append(line).append("\r\n");
            }
            return aaptRes.append("\r\n").toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != process)
                process.destroy();
            if (null != proBufferReader)
                try {
                    proBufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        //File fileDir = new File("E:\\测试相关\\apk\\");
        File fileDir = new File("E:\\apkUrl\\");
        if (!fileDir.isDirectory() || fileDir.list().length < 1)
            return;
        for (File file : fileDir.listFiles((pathname) -> {
            return pathname.getName().endsWith(".apk");
        })) {
            try (OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4) + ".txt"), "UTF-8")) {
                ow.write(loadApk4Path(file.getAbsolutePath(), null));
                ow.flush();
            } catch (Exception e) {
                System.out.println(file.getName());
            }
        }


        /*try (RandomAccessFile accessFile = new RandomAccessFile("E:\\apkurls.csv", "r")) {
            String line;
            int start  = 1000;
            int end = 1500;
            int index = 0;
            while (null != (line = accessFile.readLine())) {
                index++;
                if(index < start)
                    continue;
                if(index > end)
                    break;
                File file = null;
                try {
                    System.out.println(index);
                    file = downLoadFile4URI(line.substring(1, line.length() - 1));
                    if (null != file) {
                        Document doc = DocumentHelper.parseText(loadApk4Path(file.getAbsolutePath(), null));
                        Element ele = doc.getRootElement();
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    if (null != file) {
                        System.out.println(line + ": " + file.getName());
                        file = null;
                        //file.delete();
                    }
                } finally {
                    if (null != file)
                        file.delete();
                }
            }
        }*/

        //System.out.println(loadApk4Path("E:\\测试相关\\apk\\Apk1_nosign.apk", null));
        //System.out.println(loadApk4Path("E:\\com.gaea.wdzz.leshi.213.23.apk", null));
        //System.out.println(loadApk4Path("C:\\Users\\zengping\\Downloads\\com.Nekcom.DYING_Reborn_VR_0727-1477463080176.apk", null));
        //System.out.println(loadApk4Path("E:\\com.iplay.assistant.terrariabox-1488519316863.apk", null));
    }

}
