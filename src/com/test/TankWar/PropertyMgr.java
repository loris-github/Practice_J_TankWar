package com.test.TankWar;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
	static Properties props = new Properties();
	static{
		try{
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));	
		}
		catch (IOException e1){
			e1.printStackTrace();
		}
	}
	
	private PropertyMgr(){}; //Ë­Ò²²»ÄÜnew PropertyMgr
	
	public static String getProperty(String key){
		return props.getProperty(key);		
	}
		
	public static int getPropertyValue(String key){		
		return Integer.parseInt(props.getProperty(key));		
		}
	
}
