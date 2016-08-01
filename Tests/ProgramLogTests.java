import com.adamcosentino.ProgramLog.Log.ILogViewer;
import com.adamcosentino.ProgramLog.Log.ProgramLog;
import com.adamcosentino.ProgramLog.Utilities.DisplayLevel;
import org.junit.*;

import static junit.framework.Assert.*;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 31, 2016  17:15
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public class ProgramLogTests implements ILogViewer{
  
  private ProgramLog log;
  private String messages;
  
  @Before
  public void setup(){
    log = ProgramLog.getInstance();
    messages = "";
  }
  
  @Test
  public void registerTest(){
    log.registerLogViewer(this, DisplayLevel.INFO.Value);
  }
  
  @Override
  public void updateLog(String log) {
    messages = log;
  }
}
