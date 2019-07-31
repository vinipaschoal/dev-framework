
import org.esfinge.virtuallab.services.ValidationServiceTest;
import org.esfinge.virtuallab.services.ClassLoaderServiceTest;
import org.esfinge.virtuallab.services.InvokerServiceTest;
import org.esfinge.virtuallab.services.PersistenceServiceTest;
import org.esfinge.virtuallab.services.TransformationServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ValidationServiceTest.class, ClassLoaderServiceTest.class, InvokerServiceTest.class,
		PersistenceServiceTest.class, TransformationServiceTest.class })
public class AllTests {

}


