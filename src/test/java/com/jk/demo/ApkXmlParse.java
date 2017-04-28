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
public class ApkXmlParse {
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
            //process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", MessageFormat.format(AAPT_SCRIPT, filePath, StringUtils.isNotBlank(apkXmlResource) ? apkXmlResource : DEAFULTPATH)});
            process = Runtime.getRuntime().exec(new String[]{"cmd", "/C", MessageFormat.format(AAPT_SCRIPT, filePath, StringUtils.isNotBlank(apkXmlResource) ? apkXmlResource : DEAFULTPATH)});
            proBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), CHARSET));
            String line;
            Map<Integer, XmlElement> xmlDom = new HashMap<>();
            int index = 0;
            StringBuilder xml_title = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            StringBuilder name_space = new StringBuilder("xmlns:");
            String name_space_tag = "";
            boolean elementstart = false;
            StringBuilder aaptRes = new StringBuilder();
            int deafult_index = 0;
            int nameSpaceSetp = 0;
            int preNSpace = 0;
            int indexLine = 0;
            while (null != (line = proBufferReader.readLine())) {
                indexLine++;
                aaptRes.append(line).append("\r\n");
                int space_number = getLeftTrimSpace(line);
                String lineSub = line.substring(space_number);
                if (lineSub.startsWith("N: ")) {
                    if (!elementstart) {
                        if (lineSub.startsWith("N: android")) {
                            String[] n_v = line.substring(index + 3).split("=");
                            name_space.append(n_v[0].trim()).append("=").append("\"").append(n_v[1]).append("\"").append(" ");
                            name_space_tag = n_v[0];
                        }
                        index = index + 2;
                    } else {
                        if (space_number > preNSpace) {
                            preNSpace = space_number;
                            nameSpaceSetp += 2;
                        }
                    }
                }
                int prex_dom_index = space_number - STEP_BASE - nameSpaceSetp;
                if (lineSub.startsWith(ELEMENT_MARK)) {
                    if (space_number <= preNSpace) {
                        prex_dom_index += (preNSpace - space_number) + STEP_BASE;
                        nameSpaceSetp -= (preNSpace - space_number) + STEP_BASE;
                        preNSpace = space_number - STEP_BASE;
                    }
                    elementstart = true;
                    XmlElement xe = new XmlElement();
                    xe.setName(getElementName(line));
                    xmlDom.put(space_number - nameSpaceSetp, xe);
                    XmlElement preE;
                    if (prex_dom_index > 0 && null != (preE = xmlDom.get(prex_dom_index)))
                        preE.addElements(xe);
                }
                if (lineSub.startsWith(ATTRBUT_MARK)) {
                    String[] a_Array = line.split("=");
                    if (a_Array.length != 2)
                        continue;
                    Matcher name_type_ma = MATCHER_TYPE.matcher(line);
                    ApkAttrEnum aae = null;
                    if (name_type_ma.find()) {
                        String find = name_type_ma.group();
                        aae = ApkAttrEnum.getAttrValue4Code(find.substring(1, find.length() - 1));
                        a_Array[0] = a_Array[0].replaceAll(MATCHER_TYPE.toString(), "");
                    }
                    String aName = null != aae ? name_space_tag + ":" + aae.getAttr() : a_Array[0].replaceAll(ATTRBUT_MARK +  "|:" /*+ "|" + ELE_PREFIX*/, "").trim();
                    if (null == aName)
                        continue;
                    if (":".equals(aName)) {
                        aName = name_space_tag + ":" + DEFAULT_NAME + deafult_index;
                        deafult_index++;
                    }
                    XmlAttribute xmlAttribute = new XmlAttribute();
                    xmlAttribute.setName(aName);
                    xmlDom.get(prex_dom_index).addAttribute(xmlAttribute);
                    Matcher vmc = VALUE_TYPE.matcher(a_Array[1]);
                    if (vmc.find()) {
                        String type_code = vmc.group();
                        xmlAttribute.setValue(ApkAttrValueEnum.getAttributeValue(type_code.substring(6, type_code.length() - 1), a_Array[1].replaceAll(VALUE_TYPE.toString(), "")));
                    } else {
                        Matcher v_ma = VALUE_CONTENT.matcher(a_Array[1]);
                        if (v_ma.find()) {
                            String content = v_ma.group();
                            xmlAttribute.setValue(content.substring(1, content.length() - 1));
                        } else {
                            xmlAttribute.setValue(a_Array[1].startsWith("\"") ? a_Array[1].substring(1, a_Array[1].indexOf("\"", 1)) : a_Array[1].replaceAll("\"","").trim());
                        }
                    }
                }
            }
            return aaptRes.append("\r\n").append(xml_title.append(printXmlDomStr(xmlDom.get(index), new StringBuilder()).insert(12, name_space))).toString();
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

    private static StringBuilder printXmlDomStr(XmlElement xmlElement, StringBuilder xmlBuilder) {
        if (null != xmlElement) {
            xmlBuilder.append("\r\n").append("<").append(xmlElement.getName());
            for (XmlAttribute xa : xmlElement.getAttributes()) {
                xmlBuilder.append(" ").append(xa.getName()).append("=").append("\"").append(xa.getValue()).append("\"");
            }
            xmlBuilder.append(">");
            for (XmlElement xm : xmlElement.getElements()) {
                printXmlDomStr(xm, xmlBuilder);
            }
            xmlBuilder.append("</").append(xmlElement.getName()).append(">");
        }
        return xmlBuilder;
    }

    private static int getLeftTrimSpace(String value) {
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();
        while ((st < len) && (val[st] <= ' '))
            st++;
        return st > 0 ? st : 0;
    }

    private static String getElementName(String line) {
        if (null == line)
            return null;
        return line.replaceAll(ELEMENT_MARK, "").replaceAll("\\(line=\\d*\\)", "").replaceAll(ELE_PREFIX, "").trim();
    }

    public static void main(String[] args) throws IOException {
        //File fileDir = new File("E:\\测试相关\\apk\\");
        File fileDir = new File("E:\\apkUrl\\");
        if (!fileDir.isDirectory() || fileDir.list().length < 1)
            return;
        for (File file : fileDir.listFiles((pathname) -> {
            return pathname.getName().endsWith(".apk");
        })) {
            try (OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4) + ".xml"), "UTF-8")) {
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


    public static File downLoadFile4URI(String downloadURL) throws Exception {
        if (!downloadURL.startsWith("http://g3"))
            return null;
        InputStream in = null;
        File outTempFile;
        FileOutputStream out = null;
        try {
            URL url = new URL(downloadURL);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            conn.connect();
            in = conn.getInputStream();
            outTempFile = new File("E:\\apkUrl\\" + UUID.randomUUID().toString().replaceAll("-", "") + ".apk");
            out = new FileOutputStream(outTempFile);
            int len;
            byte[] by = new byte[1024];
            while ((len = in.read(by)) > 0) {
                out.write(by, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Exception ioE) {
                ioE.printStackTrace();
                return null;
            }
        }
        return outTempFile;
    }
}
