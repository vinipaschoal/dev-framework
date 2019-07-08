

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import devframework.op.uploadfile.PersistFileTest;
import devframework.utils.ClassLoaderUtilsTest;
import devframework.webservice.InvokerTest;

@RunWith(Suite.class)
@SuiteClasses({ InvokerTest.class, PersistFileTest.class,ClassLoaderUtilsTest.class })
public class AllTests {

}
