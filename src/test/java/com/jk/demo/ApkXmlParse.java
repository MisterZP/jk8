package com.jk.demo;

import com.jk.demo.domain.XmlAttribute;
import com.jk.demo.domain.XmlElement;
import com.jk.demo.enums.ApkAttrEnum;
import com.jk.demo.enums.ApkAttrValueEnum;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zengping on 2017/3/10.
 */
public class ApkXmlParse {
    private static final String DEAFULTPATH = "AndroidManifest.xml";
    private static final String CHARSET = "UTF-8";
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
            //StringBuilder aaptRes = new StringBuilder();
            while (null != (line = proBufferReader.readLine())) {
                //aaptRes.append(line).append("\r\n");
                if(index < 1 && line.startsWith("N: ")){
                    String[] n_v = line.substring(3).split("=");
                    name_space.append(n_v[0].trim()).append("=").append("\"").append(n_v[1]).append("\"");
                    name_space_tag = n_v[0];
                }

                int space_number = getLeftTrimSpace(line);
                int prex_dom_index = space_number - STEP_BASE;
                if (line.substring(space_number).startsWith(ELEMENT_MARK)) {
                    XmlElement xe = new XmlElement();
                    xe.setName(getElementName(line));
                    xmlDom.put(space_number, xe);
                    XmlElement preE;
                    if (prex_dom_index > 0 && null != (preE = xmlDom.get(prex_dom_index)))
                        preE.addElements(xe);
                }
                if (line.substring(space_number).startsWith(ATTRBUT_MARK)) {
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
                    String aName = null != aae ? name_space_tag + ":" + aae.getAttr() : a_Array[0].replaceAll(ATTRBUT_MARK /*+ "|" + ELE_PREFIX*/, "").trim();
                    if (null == aName)
                        continue;
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
                        }else{
                            xmlAttribute.setValue(a_Array[1].startsWith("\"") ? a_Array[1].substring(1, a_Array[1].indexOf("\"", 1)) : a_Array[1].trim());
                        }
                    }
                }
            }
            return /*aaptRes.append("\r\n").append(*/xml_title.append(printXmlDomStr(xmlDom.get(2), new StringBuilder()).insert(12, name_space))/*)*/.toString();
        } catch (IOException e) {
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

    public static StringBuilder printXmlDomStr(XmlElement xmlElement, StringBuilder xmlBuilder) {
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

    public static int getLeftTrimSpace(String value) {
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();
        while ((st < len) && (val[st] <= ' '))
            st++;
        return st > 0 ? st : 0;
    }

    public static String getElementName(String line) {
        if (null == line)
            return null;
        return line.replaceAll(ELEMENT_MARK, "").replaceAll("\\(line=\\d*\\)", "").replaceAll(ELE_PREFIX, "").trim();
    }

    public static void main(String[] args) {
        System.out.println(loadApk4Path("E:\\com.gaea.wdzz.leshi.213.23.apk", null));
        //System.out.println(loadApk4Path("C:\\Users\\zengping\\Downloads\\com.Nekcom.DYING_Reborn_VR_0727-1477463080176.apk", null));
        //System.out.println(loadApk4Path("E:\\com.iplay.assistant.terrariabox-1488519316863.apk", null));

    }
}
