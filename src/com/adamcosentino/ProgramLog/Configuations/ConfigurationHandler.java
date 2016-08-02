package com.adamcosentino.ProgramLog.Configuations;

import com.adamcosentino.ProgramLog.Log.ProgramLog;
import com.adamcosentino.ProgramLog.Utilities.Utils;

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
  
  private File file;
  private final ArrayList<Property> props;
  private final ProgramLog log;
  private final String programName;
  
  private ConfigurationHandler(String programName){
    this.programName = programName;
    file = new File("config\\" + programName + ".config");
    props = new ArrayList<>();
    log = ProgramLog.getInstance();
    if(file.exists()) loadConfigFile();
  }
  
  public static ConfigurationHandler getInstance(String programName){
    if(instance == null){
      instance = new ConfigurationHandler(programName);
    }
    return instance;
  }
  
  public void changeConfigLocation(String path){
    file = new File(path + "\\" + programName + ".config");
    if(file.exists()) loadConfigFile();
  }
  
  private void loadConfigFile(){
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      String line, comment = "";
      while((line = reader.readLine()) != null){
        if(!line.equals("") || !line.startsWith("*")){
          if(line.startsWith("#")) comment = line.substring(1);
          else {
            String[] arr = line.split("(:)|(=)");
            switch(arr[0]){
              case "B":
                props.add(new Property(arr[1], Boolean.parseBoolean(arr[2]), comment));
                break;
              case "N":
                props.add(new Property(arr[1], Long.parseLong(arr[2]), comment));
                break;
              case "D":
                props.add(new Property(arr[1], Double.parseDouble(arr[2]), comment));
                break;
              case "S":
                props.add(new Property(arr[1], arr[2], comment));
                break;
            }
          }
          comment = "";
        }
      }
    } catch (IOException ioe) {
      log.error(ioe.getMessage());
    }
  }
  
  private void saveConfigFile(){
    StringBuilder output = new StringBuilder(Utils.repeatChar('*' ,programName.length() + 4) +"\n");
    output.append("* " + programName + " *\n");
    output.append(Utils.repeatChar('*' ,programName.length() + 4) +"\n\n");
    props.forEach(prop -> {output.append(prop.toString() + "\n\n"); });
    
    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))){
      writer.write(output.toString());
      writer.flush();
    } catch (IOException ioe) {
      log.error(ioe.getMessage());
    }
    
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
    if(index != -1 && value instanceof Byte || value instanceof Integer || value instanceof Long) {
      return (long) value;
    }
    return -1;
  }
  
  public double getDecimalProperty(String property){
    int index = getPropIndex(property);
    Object value = props.get(index).getValue();
    if(index != -1 && value instanceof Double || value instanceof Float) {
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
    saveConfigFile();
  }
  
  public void setNumericProperty(String name, long value){ setNumericProperty(name, value, ""); }
  public void setNumericProperty(String name, long value, String comment){
    Property<Long> prop = new Property<Long>(name, value, comment);
    for (Property p : props){
      if(p.equals(prop)) props.remove(props.indexOf(p));
    }
    props.add(prop);
    saveConfigFile();
  }
  
  public void setDecimalProperty(String name, double value){ setDecimalProperty(name, value, ""); }
  public void setDecimalProperty(String name, double value, String comment){
    Property<Double> prop = new Property<Double>(name, value, comment);
    for (Property p : props){
      if(p.equals(prop)) props.remove(props.indexOf(p));
    }
    props.add(prop);
    saveConfigFile();
  }
  
  public void setStringProperty(String name, String value){ setStringProperty(name, value, ""); }
  public void setStringProperty(String name, String value, String comment){
    Property<String> prop = new Property<String>(name, value, comment);
    for (Property p : props){
      if(p.equals(prop)) props.remove(props.indexOf(p));
    }
    props.add(prop);
    saveConfigFile();
  }
}
