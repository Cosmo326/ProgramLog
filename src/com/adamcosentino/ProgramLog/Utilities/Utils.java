package com.adamcosentino.ProgramLog.Utilities;

import java.util.Arrays;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 30, 2016  14:42
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public abstract class Utils {
  
  public static final String repeatChar(char character, int repitions){
    char[] arr = new char[repitions];
    Arrays.fill(arr,character);
    return new String(arr);
  }
}
