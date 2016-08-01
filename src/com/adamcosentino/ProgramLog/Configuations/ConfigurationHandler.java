package com.adamcosentino.ProgramLog.Configuations;

import com.adamcosentino.ProgramLog.Log.ProgramLog;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 30, 2016  18:32
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public class ConfigurationHandler {
  
  private static ConfigurationHandler instance = null;
  
  private final ArrayList<Property> props;
  private final ProgramLog log;
  
  private ConfigurationHandler(String path){
    props = new ArrayList<>();
    log = ProgramLog.getInstance();
    File file = new File(path);
    if(file.exists()) {
      
    } else {
    }
  }
  
  public static ConfigurationHandler getInstance(String path) throws IOException{
    if(instance == null){
      if(path != null && path != "") instance = new ConfigurationHandler(path);
      else throw new FileNotFoundException();
    }
    return instance;
  }
  
  private void loadConfigFile(File file){
    
  }
  
  private void saveConfigFile(File file){
    
  }
  
  public int getPropIndex(String property){
    for(int i = 0; i < props.size(); i++){
      if(props.get(i).getName().equalsIgnoreCase(property)) return i;
    }
    return -1;
  }
  
  public boolean getBooleanProperty(String property){
    int index = getPropIndex(property);
    Object value = props.get(index).getValue();
    if(index != -1 && value instanceof Boolean) {
      return (boolean) value;
    }
    return false;
  }
  
  public double getNumericProperty(String property){
    int index = getPropIndex(property);
    Object value = props.get(index).getValue();
    if(index != -1 && value instanceof Double || value instanceof Integer || value instanceof Float || value instanceof Long) {
      return (double) value;
    }
    return -1;
  }
  
  public String getStringProperty(String property){
    int index = getPropIndex(property);
    Object value = props.get(index).getValue();
    if(index != -1 && value instanceof String || value instanceof Character) {
      return value.toString();
    }
    return "";
  }
  
  public void setBooleanProperty(String name, boolean value){ setBooleanProperty(name, value, ""); }
  public void setBooleanProperty(String name, boolean value, String comment){
    Property<Boolean> prop = new Property<Boolean>(name, value, comment);
    for (Property p : props){
      if(p.equals(prop)) props.remove(props.indexOf(p));
    }
    props.add(prop);
  }
  
}
