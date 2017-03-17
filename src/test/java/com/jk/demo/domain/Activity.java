package com.jk.demo.domain;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;

public class Activity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PropertyDescriptor[] PROPERTIES = null;
	/*private String theme;*/
	/*private String label;*/
	private String name;
	private String exported;
	private String screenOrientation;
	private String windowSoftInputMode;
	private String configChanges;
	/*private String launchMode;*/
	/*public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}*/
	/*public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExported() {
		return exported;
	}
	public void setExported(String exported) {
		this.exported = exported;
	}
	public String getScreenOrientation() {
		return screenOrientation;
	}
	public void setScreenOrientation(String screenOrientation) {
		this.screenOrientation = screenOrientation;
	}
	public String getWindowSoftInputMode() {
		return windowSoftInputMode;
	}
	public void setWindowSoftInputMode(String windowSoftInputMode) {
		this.windowSoftInputMode = windowSoftInputMode;
	}
	public String getConfigChanges() {
		return configChanges;
	}
	public void setConfigChanges(String configChanges) {
		this.configChanges = configChanges;
	}
	/*public String getLaunchMode() {
		return launchMode;
	}
	public void setLaunchMode(String launchMode) {
		this.launchMode = launchMode;
	}*/
	
	public static PropertyDescriptor[] getPropertyDescriptors(){
		if(null == PROPERTIES){
			try {
				PROPERTIES = Introspector.getBeanInfo(Activity.class).getPropertyDescriptors();
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
		}
		return PROPERTIES;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((configChanges == null) ? 0 : configChanges.hashCode());
		result = prime * result
				+ ((exported == null) ? 0 : exported.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((screenOrientation == null) ? 0 : screenOrientation
						.hashCode());
		result = prime
				* result
				+ ((windowSoftInputMode == null) ? 0 : windowSoftInputMode
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (configChanges == null) {
			if (other.configChanges != null)
				return false;
		} else if (!configChanges.equals(other.configChanges))
			return false;
		if (exported == null) {
			if (other.exported != null)
				return false;
		} else if (!exported.equals(other.exported))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (screenOrientation == null) {
			if (other.screenOrientation != null)
				return false;
		} else if (!screenOrientation.equals(other.screenOrientation))
			return false;
		if (windowSoftInputMode == null) {
			if (other.windowSoftInputMode != null)
				return false;
		} else if (!windowSoftInputMode.equals(other.windowSoftInputMode))
			return false;
		return true;
	}
	
}
