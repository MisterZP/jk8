package com.jk.demo.domain;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Manifest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PropertyDescriptor[] PROPERTIES = null;
    public static final String PACKAGE = "package";
    public static final String MANIFEST = "manifest";
    public static final String USES_SDK = "uses-sdk";
    public static final String USES_PERMISSION = "uses-permission";
    public static final String APPLICATION = "application";
    public static final String QUALIFIED_PREFIX = "android:";
    
	private String versionCode;
	private String versionName;
	private String xmlPackage;
	private Application application;
	private UsesSdk sdk;
	private String nameSpace;
	private List<UsesPermission> permissions = new ArrayList<>();
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getXmlPackage() {
		return xmlPackage;
	}
	public void setXmlPackage(String xmlPackage) {
		this.xmlPackage = xmlPackage;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public UsesSdk getSdk() {
		return sdk;
	}
	public void setSdk(UsesSdk sdk) {
		this.sdk = sdk;
	}
	public List<UsesPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<UsesPermission> permissions) {
		this.permissions = permissions;
	}
	
	public void addPermissions(UsesPermission permission){
		this.permissions.add(permission);
	}


	public static void main(String[] args) {
		System.out.println(Integer.parseInt("8C",16));
		System.out.println(Float.intBitsToFloat(Integer.valueOf("0x55.77", 16)));
	}
}
