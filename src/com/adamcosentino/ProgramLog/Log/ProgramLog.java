package com.adamcosentino.ProgramLog.Log;

import com.adamcosentino.ProgramLog.Utilities.DisplayLevel;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 13, 2016  00:25
 * Project: ProgramLogger
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public class ProgramLog {
  
  private static ProgramLog instance = null;
  
  private Map<ILogViewer, Integer> viewers;
  private String logPath, progTitle;
  private ArrayList<Entry> log;
  private SimpleDateFormat fileDF;

  private ProgramLog() {
    viewers = new HashMap<ILogViewer, Integer>();
    logPath = progTitle = "";
    log = new ArrayList<Entry>();
    fileDF = new SimpleDateFormat("yyyyMMdd_HHmmss");
  }

  public static ProgramLog getInstance(){
    if(instance == null) instance = new ProgramLog();
    return instance;
  }

  public void setupLogFile(@NotNull String progTitle, @NotNull String logPath){
    this.progTitle = progTitle;
    this.logPath = logPath + "_" + fileDF.format(new Date()) + ".log";
  }

  public void registerLogViewer(@NotNull ILogViewer viewer, int levels){
    viewers.put(viewer, levels);
    info("Registered " + viewer.getClass().getName());
  }
  public void unregisterViewer(@NotNull ILogViewer viewer){
    if(viewers.containsKey(viewer)) viewers.remove(viewer);
    info("Unregistered " + viewer.getClass().getName());
  }
  public void toggleLevel(ILogViewer viewer, int level){
    int newLev = viewers.get(viewer) ^ level;
    viewers.put(viewer, newLev);
    info(viewer.getClass().getName() + " new level set: " + newLev);
  }
  public void setLevel(ILogViewer viewer, int level){
    viewers.put(viewer, level);
  }
  public int getLevel(ILogViewer viewer) { return viewers.get(viewer); }
  
  public String getFullLog(){
    StringBuilder output = new StringBuilder();
    for(Entry entry : log){
      output.append(entry.toString() + "\n");
    }
    return output.toString();
  }
  public String getLog(ILogViewer viewer){
    StringBuilder output = new StringBuilder();
    for(Entry entry : log){
      if(entry.compareLevel(viewers.get(viewer)) <= 0) output.append(entry.toString() + "\n");
    }
    return output.toString();
  }
  
  public void deleteLog(){ log = new ArrayList<>(); }

  public void broadcast(DisplayLevel level, String message){
    Entry entry = new Entry(level, new Date(), message + "\n");
    log.add(entry);
    for(ILogViewer viewer : viewers.keySet()){
      if(entry.compareLevel(viewers.get(viewer)) <= 0) viewer.updateLog(entry.toString());
    }
  }

  public void info(String msg){
    broadcast(DisplayLevel.INFO, msg);
  }

  public void warning(String msg){
    broadcast(DisplayLevel.WARN, msg);
  }

  public void error(String msg){
    broadcast(DisplayLevel.ERR, msg);
  }

  public void fatal(String msg){
    broadcast(DisplayLevel.FATAL, msg);
  }

  public void debug(String msg){
    broadcast(DisplayLevel.DEBUG, msg);
  }
  
  public void close() throws IOException{
    if(!logPath.equals("")){
      String output = progTitle + "\n\n" + log.toString();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(logPath))));
      writer.write(output);
      writer.flush();
      writer.close();
    }
  }
}
