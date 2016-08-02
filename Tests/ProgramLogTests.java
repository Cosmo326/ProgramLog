import com.adamcosentino.ProgramLog.Configuations.Property;
import com.adamcosentino.ProgramLog.Log.Entry;
import com.adamcosentino.ProgramLog.Log.ILogViewer;
import com.adamcosentino.ProgramLog.Log.ProgramLog;
import com.adamcosentino.ProgramLog.Utilities.DisplayLevel;
import org.junit.*;

import java.util.ArrayList;

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
public class ProgramLogTests {
  
  
  @Before
  public void setup() {
  }
  
  @Test
  public void PropertyTest() {
    ArrayList<Property> list = new ArrayList<>();
    list.add(new Property("Boolean", true, "Boolean Test"));
    list.add(new Property("Numeric",75));
    list.add(new Property("Decimal",0.75,""));
    list.add(new Property("String","Test","Test String"));
    
    String expected = "#Boolean Test\nB:Boolean=true\nN:Numeric=75\nD:Decimal=0.75\n#Test String\nS:String=Test\n";
    StringBuilder actual = new StringBuilder();
    list.forEach(property -> { actual.append(property.toString() +"\n"); });
    
    assertEquals(expected, actual.toString());
  }
}