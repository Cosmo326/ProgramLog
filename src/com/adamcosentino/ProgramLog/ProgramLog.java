package com.adamcosentino.ProgramLog;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.text.SimpleDateFormat;
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

  public static final int NONE = 0x00;
  public static final int INFO = 0x01;
  public static final int WARNING = 0x02;
  public static final int ERROR = 0x04;
  public static final int FATAL = 0x08;
  public static final int DEBUG = 0x10;
  public static final int ALL = INFO | WARNING | ERROR | FATAL;

  private static Map<ILogViewer, Integer> viewers;
  private static ProgramLog instance = null;

  private String logPath, progTitle;
  private final StringBuilder log;
  private SimpleDateFormat df;

  private ProgramLog() {
    viewers = new HashMap<ILogViewer, Integer>();
    logPath = "";
    progTitle = "";
    log = new StringBuilder("");
    df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
  }

  public static ProgramLog getInstance(){
    if(instance == null) instance = new ProgramLog();
    return instance;
  }

  public void setupLogFile(@NotNull String progTitle, @NotNull String logPath){
    this.progTitle = progTitle;
    this.logPath = logPath;
  }

  public static void registerLogViewer(@NotNull ILogViewer viewer){ registerLogViewer(viewer, ALL); }
  public static void registerLogViewer(@NotNull ILogViewer viewer, int... levels){ setLevel(viewer, levels); }
  public static void unregisterViewer(@NotNull ILogViewer viewer){ if(viewers.containsKey(viewer)) viewers.remove(viewer); }

  public static void setLevel(ILogViewer viewer, int... levels){
    int l = 0;
    for(int level : levels) {
      l = l | level;
    }
    viewers.put(viewer, l);
  }

  public String getLog(){ return log.toString(); }
  public void clearLog(){ log.replace(0,log.length()-1,""); }

  public void broadcast(int level, String message){
    log.append(message);
    for(ILogViewer viewer : viewers.keySet()){
      if((viewers.get(viewer) & level) == level)
        viewer.updateLog(message);
    }
  }

  public void info(String msg){
    String message = df.format(new Date()) + " [INFO]: " + msg + "\n";
    broadcast(INFO, message);
  }

  public void warning(String msg){
    String message = df.format(new Date()) + " [WARNING]: " + msg + "\n";
    broadcast(WARNING, message);
  }

  public void error(String msg){
    String message = df.format(new Date()) + " [ERROR]: " + msg + "\n";
    broadcast(ERROR, message);
  }

  public void fatal(String msg){
    String message = df.format(new Date()) + " [FATAL]: " + msg + "\n";
    broadcast(FATAL, message);
  }

  public void debug(String msg){
    String message = df.format(new Date()) + " [DEBUG]: " + msg + "\n";
    broadcast(DEBUG, message);
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
