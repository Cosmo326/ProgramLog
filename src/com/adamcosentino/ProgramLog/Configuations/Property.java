package com.adamcosentino.ProgramLog.Configuations;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 31, 2016  14:19
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public class Property<T> {
  
  private String name, comment, type;
  private T value;
  
  public Property(String name, T value) {
    this(name, value, "");
  }
  
  public Property(String name, T value, String comment) {
    this.name = name;
    this.value = value;
    this.comment = comment;
    
    if(value instanceof Boolean) type = "B";
    else if (value instanceof Integer || value instanceof Byte || value instanceof Long) type = "N";
    else if (value instanceof Double || value instanceof Float) type = "D";
    else if (value instanceof String || value instanceof Character) type = "S";
  }
  
  public boolean equals(Property property) { return name.equalsIgnoreCase(property.name); }
  public String getName() { return name; }
  public T getValue() { return value; }
  public String getComment() { return comment; }
  public void setName(String name) { this.name = name; }
  public void setValue(T value) { this.value = value; }
  public void setComment(String comment) { this.comment = comment; }
  
  @Override
  public String toString(){
    if(comment.equals("")) return type + ":" + name + "=" + value;
    else return "#" + comment + "\n" + type + ":" + name + "=" + value;
  }
}
