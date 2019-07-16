

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import devframework.services.ClassValidationServiceTest;
import devframework.services.PersistenceServiceTest;
import devframework.utils.ClassLoaderUtilsTest;
import devframework.webservice.InvokerTest;


@RunWith(Suite.class)
@SuiteClasses({ ClassValidationServiceTest.class, ClassLoaderUtilsTest.class, InvokerTest.class, PersistenceServiceTest.class})
public class AllTests {

}
