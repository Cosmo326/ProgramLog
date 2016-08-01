package com.adamcosentino.ProgramLog.Utilities;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 30, 2016  14:43
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public enum DisplayLevel {
  INFO(0x01,"Information","INFO","Displays informational logs"),
  WARN(0x02,"Warning","WARN","Displays warning logs"),
  ERR(0x04,"Error","ERROR","Displays error logs"),
  FATAL(0x08,"Fatal Error","FATAL","Displays fatal error logs"),
  DEBUG(0x16,"Debug","DEBUG","Displays debug information logs");
  
  public final int Value;
  public final String Label;
  public final String Tag;
  public final String Description;
  
  DisplayLevel(int Value, String Label, String Tag, String Description){
    this.Value = Value;
    this.Label = Label;
    this.Tag = Tag;
    this.Description = Description;
  }
  
}
