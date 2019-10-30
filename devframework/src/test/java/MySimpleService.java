
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;

@ServiceClass
public class MySimpleService
{
//	private final float version = 3.0f;
//	private MySimpleClass1 simpleClass = new MySimpleClass1();
	
	@ServiceMethod
	public String serviceMethod()
	{
//		return String.format("Service version %.1f --> %s", version, simpleClass.method());
		return "testando";
	}
}
