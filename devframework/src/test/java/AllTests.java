

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import devframework.services.ClassValidationServiceTest;
import devframework.services.PersistenceServiceTest;
import devframework.utils.ClassLoaderUtilsTest;
import devframework.webservice.InvokerTest;

@RunWith(Suite.class)
@SuiteClasses({ InvokerTest.class, PersistenceServiceTest.class, ClassLoaderUtilsTest.class, ClassValidationServiceTest.class })
public class AllTests {

}
