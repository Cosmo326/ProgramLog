package com.adamcosentino.ProgramLog.Log;

import com.adamcosentino.ProgramLog.Utilities.DisplayLevel;
import com.sun.istack.internal.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 31, 2016  16:27
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public class Entry {
  private DisplayLevel level;
  private Date stamp;
  private String message;
  private final SimpleDateFormat df;
  
  public Entry(DisplayLevel level, Date stamp, String message) {
    this.level = level;
    this.stamp = stamp;
    this.message = message;
    df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
  }
  
  public int compareLevel(@NotNull DisplayLevel level) {
    return compareLevel(level.Value);
  }
  
  public int compareLevel(@NotNull int level) {
    if (level < this.level.Value) return 1;
    else if (level == this.level.Value) return 0;
    else return -1;
  }
  
  public int compareDate(@NotNull Date date){
    return stamp.compareTo(date);
  }
  
  @Override
  public String toString(){
    return df.format(stamp) + "[" + level.Tag + "]: " + message;
  }
}
