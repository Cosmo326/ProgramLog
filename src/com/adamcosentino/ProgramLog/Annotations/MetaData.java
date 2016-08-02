package com.adamcosentino.ProgramLog.Annotations;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Aug 01, 2016  15:12
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public @interface MetaData {
  
  String ProgramName();
  String Author() default "";
  String Date() default "";
  String Version() default "";
  
}
