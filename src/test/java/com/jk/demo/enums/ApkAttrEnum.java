package com.jk.demo.enums;

/**
 * Created by zengping on 2017/3/10.
 */
public enum ApkAttrEnum {
    /**
     * LABEL_ATTR = 0x01010001
     ICON_ATTR = 0x01010002,
     NAME_ATTR = 0x01010003,
     PERMISSION_ATTR = 0x01010006,
     RESOURCE_ATTR = 0x01010025,
     DEBUGGABLE_ATTR = 0x0101000f,
     VERSION_CODE_ATTR = 0x0101021b, //versionCode
     VERSION_NAME_ATTR = 0x0101021c,
     SCREEN_ORIENTATION_ATTR = 0x0101001e,
     MIN_SDK_VERSION_ATTR = 0x0101020c,
     MAX_SDK_VERSION_ATTR = 0x01010271,
     REQ_TOUCH_SCREEN_ATTR = 0x01010227,
     REQ_KEYBOARD_TYPE_ATTR = 0x01010228,
     REQ_HARD_KEYBOARD_ATTR = 0x01010229,
     REQ_NAVIGATION_ATTR = 0x0101022a,
     REQ_FIVE_WAY_NAV_ATTR = 0x01010232,
     TARGET_SDK_VERSION_ATTR = 0x01010270,
     TEST_ONLY_ATTR = 0x01010272,
     ANY_DENSITY_ATTR = 0x0101026c,
     GL_ES_VERSION_ATTR = 0x01010281,
     SMALL_SCREEN_ATTR = 0x01010284,
     NORMAL_SCREEN_ATTR = 0x01010285,
     LARGE_SCREEN_ATTR = 0x01010286,
     XLARGE_SCREEN_ATTR = 0x010102bf,
     REQUIRED_ATTR = 0x0101028e,
     SCREEN_SIZE_ATTR = 0x010102ca,
     SCREEN_DENSITY_ATTR = 0x010102cb,
     REQUIRES_SMALLEST_WIDTH_DP_ATTR = 0x01010364,
     COMPATIBLE_WIDTH_LIMIT_DP_ATTR = 0x01010365,
     LARGEST_WIDTH_LIMIT_DP_ATTR = 0x01010366,
     PUBLIC_KEY_ATTR = 0x010103a6,
     CATEGORY_ATTR = 0x010103e8,
     */
    LABEL_ATTR("0x01010001", "label"),
    ICON_ATTR("0x01010002", "icon"),
    NAME_ATTR("0x01010003","name"),
    PERMISSION_ATTR("0x01010006","permission"),
    RESOURCE_ATTR("0x01010025","resource"),
    DEBUGGABLE_ATTR("0x0101000f","debuggable"),
    VERSION_CODE_ATTR("0x0101021b","versionCode"),
    VERSION_NAME_ATTR("0x0101021c","versionName"),
    SCREEN_ORIENTATION_ATTR("0x0101001e","screenOrientation"),
    MIN_SDK_VERSION_ATTR("0x0101020c","minSdkVersion"),
    MAX_SDK_VERSION_ATTR("0x01010271","maxSdkVersion"),
    REQ_TOUCH_SCREEN_ATTR("0x01010227","reqTouchScreen"),
    REQ_KEYBOARD_TYPE_ATTR("0x01010228","reqKeyboardType"),
    REQ_HARD_KEYBOARD_ATTR("0x01010229","reqHardKeyboard"),
    REQ_NAVIGATION_ATTR("0x0101022a","reqNavigation"),
    REQ_FIVE_WAY_NAV_ATTR("0x01010232","reqFiveWayNav"),
    TARGET_SDK_VERSION_ATTR("0x01010270","targetSdkVersion"),
    TEST_ONLY_ATTR("0x01010272","testOnly"),
    ANY_DENSITY_ATTR("0x0101026c","anyDensity"),
    GL_ES_VERSION_ATTR("0x01010281","glEsVersion"),
    SMALL_SCREEN_ATTR("0x01010284","smallScreen"),
    NORMAL_SCREEN_ATTR("0x01010285","normalScreen"),
    LARGE_SCREEN_ATTR("0x01010286","largeScreen"),
    XLARGE_SCREEN_ATTR("0x010102bf","xlargeScreen"),
    REQUIRED_ATTR("0x0101028e","required"),
    SCREEN_SIZE_ATTR("0x010102ca","screenSize"),
    SCREEN_DENSITY_ATTR("0x010102cb","screenDensity"),
    REQUIRES_SMALLEST_WIDTH_DP_ATTR("0x01010364","requiresSmallestWidthDp"),
    COMPATIBLE_WIDTH_LIMIT_DP_ATTR("0x01010365","compatibleWidthLimitDp"),
    LARGEST_WIDTH_LIMIT_DP_ATTR("0x01010366","largestWidthLimitDp"),
    PUBLIC_KEY_ATTR("0x010103a6","publicKey"),
    CATEGORY_ATTR("0x010103e8","category");

    ApkAttrEnum(String code, String attr){
          this.code = code;
          this. attr = attr;
    }

    public static ApkAttrEnum getAttrValue4Code(String code){
        for (ApkAttrEnum bean : values()) {
            if(bean.getCode().equals(code))
                return bean;
        }
        return null;
    }

    private String code, attr;

    public String getAttr() {
        return attr;
    }

    public String getCode() {
        return code;
    }
}
