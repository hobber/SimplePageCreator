package test.data.json;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestJSONArray.class, TestJSONContainer.class, TestJSONSpecialValue.class, TestJSONParser.class })
public class AllJSONTests {

}
