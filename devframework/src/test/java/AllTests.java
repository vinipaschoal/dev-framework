

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

<<<<<<< HEAD
import devframework.op.uploadfile.PersistFileTest;
=======
import devframework.services.ClassValidationServiceTest;
import devframework.services.PersistenceServiceTest;
>>>>>>> c84eb54cee8a7049076ca84c248058c422200e69
import devframework.utils.ClassLoaderUtilsTest;
import devframework.webservice.InvokerTest;

@RunWith(Suite.class)
<<<<<<< HEAD
@SuiteClasses({ InvokerTest.class, PersistFileTest.class,ClassLoaderUtilsTest.class })
=======
@SuiteClasses({ InvokerTest.class, PersistenceServiceTest.class, ClassLoaderUtilsTest.class, ClassValidationServiceTest.class })
>>>>>>> c84eb54cee8a7049076ca84c248058c422200e69
public class AllTests {

}
