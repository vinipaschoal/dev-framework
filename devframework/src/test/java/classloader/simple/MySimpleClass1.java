package classloader.simple;

public class MySimpleClass1
{
	private final float version = 2.0f;
	
	public String method()
	{
		return ( String.format("SimpleClass version %.1f", version));
	}
}
