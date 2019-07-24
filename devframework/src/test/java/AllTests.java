
import org.esfinge.virtuallab.services.ClassValidationServiceTest;
import org.esfinge.virtuallab.services.InvokerServiceTest;
import org.esfinge.virtuallab.services.PersistenceServiceTest;
import org.esfinge.virtuallab.services.TransformationServiceTest;
import org.esfinge.virtuallab.utils.ClassLoaderUtilsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClassValidationServiceTest.class, ClassLoaderUtilsTest.class, InvokerServiceTest.class,
		PersistenceServiceTest.class, TransformationServiceTest.class })
public class AllTests {

}


