package com.jk.demo.domain;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PropertyDescriptor[] PROPERTIES = null;
	public static final String ACTIVITY = "activity";
    public static final String META_DATA = "meta-data";
    private String label;
	private String icon;
	private String persistent;
	private String allowBackup;
	private List<MetaData> metaDatas = new ArrayList<>();
	private List<Activity> activities = new ArrayList<>();
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPersistent() {
		return persistent;
	}
	public void setPersistent(String persistent) {
		this.persistent = persistent;
	}
	public String getAllowBackup() {
		return allowBackup;
	}
	public void setAllowBackup(String allowBackup) {
		this.allowBackup = allowBackup;
	}
	public List<MetaData> getMetaDatas() {
		return metaDatas;
	}
	public void setMetaDatas(List<MetaData> metaDatas) {
		this.metaDatas = metaDatas;
	}
	public void addMetaData(MetaData metaData){
		this.metaDatas.add(metaData);
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public void addActivity(Activity activity){
		this.activities.add(activity);
	}
}
