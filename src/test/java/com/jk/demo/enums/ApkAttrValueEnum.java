package com.jk.demo.enums;

/**
 * Created by zengping on 2017/3/10.
 */
public enum ApkAttrValueEnum {
    /**
     * // Contains no data(不包含数据).
     * TYPE_NULL = 0x00,
     * // The 'data' holds a ResTable_ref, a reference to another resource
     * // table entry.(引用类型，对另一个资源的引用)
     * TYPE_REFERENCE = 0x01,
     * // The 'data' holds an attribute resource identifier.(属性资源标识符)
     * TYPE_ATTRIBUTE = 0x02,
     * // The 'data' holds an index into the containing resource table's(资源表的索引)
     * // global value string pool.
     * TYPE_STRING = 0x03,
     * // The 'data' holds a single-precision floating point number.（单精度浮点数 float）
     * TYPE_FLOAT = 0x04,
     * // The 'data' holds a complex number encoding a dimension value,(“data”包含对维值进行编码的复数)
     * // such as "100in".
     * TYPE_DIMENSION = 0x05,
     * // The 'data' holds a complex number encoding a fraction of a (“数据”保存编码a的一部分的复数)
     * // container.
     * TYPE_FRACTION = 0x06,
     * // Beginning of integer flavors...(整数)
     * TYPE_FIRST_INT = 0x10,
     * // The 'data' is a raw integer value of the form n..n.(整数)
     * TYPE_INT_DEC = 0x10,
     * // The 'data' is a raw integer value of the form 0xn..n.(整数)
     * TYPE_INT_HEX = 0x11,
     * // The 'data' is either 0 or 1, for input "false" or "true" respectively.(布尔类型，true,false)
     * TYPE_INT_BOOLEAN = 0x12,
     * // Beginning of color integer flavors...
     * TYPE_FIRST_COLOR_INT = 0x1c,
     * // The 'data' is a raw integer value of the form #aarrggbb.
     * TYPE_INT_COLOR_ARGB8 = 0x1c,
     * // The 'data' is a raw integer value of the form #rrggbb.
     * TYPE_INT_COLOR_RGB8 = 0x1d,
     * // The 'data' is a raw integer value of the form #argb.
     * TYPE_INT_COLOR_ARGB4 = 0x1e,
     * // The 'data' is a raw integer value of the form #rgb.
     * TYPE_INT_COLOR_RGB4 = 0x1f,
     * // ...end of integer flavors.
     * TYPE_LAST_COLOR_INT = 0x1f,
     * // ...end of integer flavors.
     * TYPE_LAST_INT = 0x1f
     */
    TYPE_NULL("0x00"),
    TYPE_REFERENCE("0x01"),
    TYPE_ATTRIBUTE("0x02"),
    TYPE_STRING("0x03"),
    TYPE_FLOAT("0x04"),
    TYPE_DIMENSION("0x05"),
    TYPE_FRACTION("0x06"),
    TYPE_FIRST_INT("0x10"),
    TYPE_INT_DEC("0x10"),
    TYPE_INT_HEX("0x11"),
    TYPE_INT_BOOLEAN("0x12"),
    TYPE_FIRST_COLOR_INT("0x1c"),
    TYPE_INT_COLOR_ARGB8("0x1c"),
    TYPE_INT_COLOR_RGB8("0x1d"),
    TYPE_INT_COLOR_ARGB4("0x1e"),
    TYPE_INT_COLOR_RGB4("0x1f"),
    TYPE_LAST_COLOR_INT("0x1f"),
    TYPE_LAST_INT("0x1f");

    ApkAttrValueEnum(String code) {
        this.code = code;
    }

    public static ApkAttrValueEnum getBean4Code(String code) {
        for (ApkAttrValueEnum bean : values()) {
            if (bean.getCode().equals(code) || bean.getCode().equals(code.replace("0x", "0x0")))
                return bean;
        }
        return null;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public Integer getCodeV() {
        return Integer.parseInt(this.code.replace("0x", ""), 16);
    }

    public static String getAttributeValue(String code, String data) {
        try {
            if (null == data || null == code)
                return null;
            ApkAttrValueEnum aave = getBean4Code(code);
            if(data.contains("Raw: "))
                data  = data.split(" ")[0].trim();
            if (null == aave)
                return data;
            Integer code_v = aave.getCodeV();
            if (!data.startsWith("0x")) {
                if (TYPE_ATTRIBUTE == aave) {
                    return data.startsWith("?0x") ? data.replace("0x", "") : data;
                }
                if (TYPE_REFERENCE == aave) {
                    return data.startsWith("@0x") ? data.replace("0x", "") : data;
                }
                if (TYPE_FIRST_COLOR_INT.getCodeV().compareTo(code_v) <= 0 && TYPE_LAST_COLOR_INT.getCodeV().compareTo(code_v) >= 0) {
                    return data.startsWith("#0x") ? data.replace("0x", "") : data;
                }
            }
            if (TYPE_FLOAT == aave || TYPE_DIMENSION == aave || TYPE_FRACTION == aave) {
                return data.startsWith("0x") ? Float.intBitsToFloat(Integer.valueOf(data.replace("0x", "").trim(), 16)) + "" : data;
            }
            if (TYPE_INT_BOOLEAN == aave) {
                return Long.parseLong(data.replace("0x", ""), 16) > 0 ? "true" : "false";
            }
            if (TYPE_INT_HEX == aave || (TYPE_FIRST_INT.getCodeV().compareTo(code_v) <= 0 && TYPE_LAST_INT.getCodeV().compareTo(code_v) >= 0)) {
                return Long.parseLong(data.replace("0x", ""), 16) + "";
            }
            return data;
        } catch (Exception e) {
            return data;
        }
    }
}
