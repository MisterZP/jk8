package com.jk.demo.domain;

import java.beans.PropertyDescriptor;
import java.io.Serializable;

public class UsesSdk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PropertyDescriptor[] PROPERTIES = null;
	private String minSdkVersion;
	private String targetSdkVersion;
	public String getMinSdkVersion() {
		return minSdkVersion;
	}
	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}
	public String getTargetSdkVersion() {
		return targetSdkVersion;
	}
	public void setTargetSdkVersion(String targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}
	
}
