import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Map;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.demo.chart.lines.DaoXrayLow;
import org.esfinge.virtuallab.demo.chart.lines.LineChartDemo;
import org.esfinge.virtuallab.demo.chart.lines.XrayDataLow;
import org.esfinge.virtuallab.services.ClassLoaderService;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

public class Main
{
	public static void main(String... args) throws Exception
	{
//		TestUtils.createJar("tarefa.jar", TarefaService.class, Tarefa.class);
//		TestUtils.createJar("matematica.jar", MatematicaService.class, MatematicaInvokerProxy.class, Ponto.class);
//		TestUtils.createJar("chart.jar", ChartService.class, Temperatura.class);
//		TestUtils.createJar("temperaturaDAO.jar", TemperaturaService.class, TemperaturaServiceProxy.class, Temperatura.class);
//		TestUtils.createJar("topicDAO.jar", TopicService.class, Topic.class);
		/*
		TestUtils.createJar("TableDemo.jar", TableDemo.class, Task.class);
		TestUtils.createJar("ChartDemo.jar", ChartDemo.class, Votes.class);
		TestUtils.createJar("DaoDemo.jar", DaoDemo.class, Temperatura.class);
		TestUtils.createJar("ProxyDemo.jar", ProxyDemo.class, DaoDemo.class, Temperatura.class);
		TestUtils.createJar("MapDemo.jar", MapDemo.class, Cidade.class, Localidade.class, DaoDemo.class, Temperatura.class);
		TestUtils.createJar("JsonDemo.jar", JsonDemo.class, Disciplina.class);
		*/
		
//		MetadataHelper.getInstance().getClassMetadata(MySimpleService.class);
		
//		TestUtils.createJar("INPE_Imager.jar", DaoDemoM.class, StationM.class);
		TestUtils.createJar("INPE_Goes.jar", DaoXrayLow.class, XrayDataLow.class);
		TestUtils.createJar("LineChartDemo.jar", LineChartDemo.class);
		
		/*
		File jarFile = new File(TestUtils.pathFromTestDir("DaoDemo.jar"));
		Class<?> clazz1 = ClassLoaderService.getInstance().loadService(jarFile);
		System.out.println(clazz1);
		ClassMetadata metadata = MetadataHelper.getInstance().getClassMetadata(clazz1);
		
		Class<?> clazz2 = ClassLoaderService.getInstance().loadService(new File(TestUtils.pathFromTestDir("ProxyDemo.jar")));
		System.out.println(clazz2);		
		System.out.println(clazz2.getClassLoader());
				
		for ( Field f : clazz2.getDeclaredFields() )
			System.out.println(f.getType());
		
//		URLClassLoader cl = new URLClassLoader(new URL[] {jarFile.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
//		Thread.currentThread().setContextClassLoader(cl);
//		System.out.println(cl.loadClass("org.esfinge.virtuallab.demo.dao.DaoDemo"));
		
//		System.out.println("\n\n\n");
		metadata = MetadataHelper.getInstance().getClassMetadata(clazz2);
		System.out.println(metadata.isServiceClass());
		*/
		
		/*
		String version = "v3";
		
		System.out.println(Utils.createJar("simple_test.jar", String.format("%s//%s", TestUtils.TEST_DIR, version), 
				TestUtils.pathFromTestDir(String.format("%s//MySimpleClass.class", version)),
				TestUtils.pathFromTestDir(String.format("%s//MySimpleService.class", version))));
		*/
		
		/*
		System.out.println(Utils.createJar("dao_test.jar", String.format("%s//%s", TestUtils.TEST_DIR, version), 
				TestUtils.pathFromTestDir(String.format("%s//MyEntity.class", version)),
				TestUtils.pathFromTestDir(String.format("%s//MyServiceDAO.class", version))));
		*/
		
		// 1o teste - novas versoes de servicos simples
//		testDynamicLoadingV1();
		
		// 2o teste - novas versoes de servicos DAO
		
		
		// 3o teste - servicos que utilizam novas versoes de outros servicos via @Inject e @Invoker
		
		/*
		ClassParser cp = new ClassParser(TestUtils.pathFromTestDir("Inject.class"));
		JavaClass jc = cp.parse();
		System.out.println(String.format("%s - %s", jc.getPackageName(), jc.getClassName()));
		*/
		
		/*
		Map<String, URLClassLoader> clMap = new HashMap<>();
		Class<?> clazz = loadClass(TestUtils.pathFromTestDir("v1/"), "MyServiceClass", clMap);
		Method testMethod = clazz.getMethod("test");
		Object obj = clazz.newInstance();
		System.out.println(testMethod.invoke(obj));
		
		clazz = loadClass(TestUtils.pathFromTestDir("v2/"), "MyServiceClass", clMap);
		testMethod = clazz.getMethod("test");
		obj = clazz.newInstance();
		System.out.println("\n" + testMethod.invoke(obj));
		
		clazz = loadClass(TestUtils.pathFromTestDir("v3/"), "MyServiceClass", clMap);
		testMethod = clazz.getMethod("test");
		obj = clazz.newInstance();
		System.out.println("\n" + testMethod.invoke(obj));
		
		clMap.remove("MyServiceClass").close();
		clazz = null;
		testMethod = null;
		obj = null;
		
		clazz = Class.forName("MyServiceClass");
		*/
//		System.out.println(testMethod.invoke(obj));

		

		/*
		Temperatura t = new Temperatura(2l, "-23.5475","-46.63611111", 28.2, 19.3, "janeiro");
		
		JexlEngine jexl = new JexlBuilder().cache(512).silent(false).create();
		String rawText = "Temperatura do Mes '${temp.mes.toUpperCase()}' / '${temp.mes}'";
		JexlExpression exp = jexl.createExpression(parseEL(rawText));
		JexlContext context = new MapContext();
		context.set("temp", t);
		
		System.out.println(exp.evaluate(context));
		*/
		
		/*
		ELProcessor elp = new ELProcessor();
		elp.defineBean("temp", t);
		System.out.println(elp.getValue("'Temperatura do Mes \\'' + temp.mes + '\\''", String.class));
//		System.out.println(elp.eval(parseEL("Temperatura do Mes '${temp.mes}'")));
		*/
		
		/*
		Enhancer proxy = new Enhancer();
		proxy.setSuperclass(MatematicaService.class);
		proxy.setCallback(InvokerService.getInstance());
		
		MatematicaService matSvc = (MatematicaService) proxy.create();
		System.out.println(matSvc.calcularDistancia(new Ponto(1,2), new Ponto(4,6)));
		*/
		
		/*
		for ( Method m : MethodUtils.getMethodsWithAnnotation(Main.class, BarChartReturn.class) )
		{
			MethodReturnProcessor<?> processor = MethodReturnProcessorHelper.getInstance().findProcessor(m);
			JsonData data = processor.process(m.invoke(null, null));
			System.out.println(data);
			System.out.println();
		}
		*/
	}
	
	private static void testDynamicLoadingV1() throws Exception
	{
		// carregar o JAR da v1 do servico
		Class<?> service = ClassLoaderService.getInstance().loadService(
				Paths.get(TestUtils.TEST_DIR,  "v1", "simple_test.jar").toAbsolutePath().toString());
		
		// executa o servico
		Object obj = service.newInstance();
		System.out.println(service.getMethod("serviceMethod").invoke(obj));
		
		
		// carregar o JAR da v2 do servico
		ClassLoaderService.getInstance().loadService(
				Paths.get(TestUtils.TEST_DIR,  "v2", "simple_test.jar").toAbsolutePath().toString());
		service = ClassLoaderService.getInstance().getService(service.getCanonicalName());
		obj = service.newInstance();
		System.out.println(service.getMethod("serviceMethod").invoke(obj));
		
		
		// carregar o JAR da v3 do servico
		ClassLoaderService.getInstance().loadService(
				Paths.get(TestUtils.TEST_DIR,  "v3", "simple_test.jar").toAbsolutePath().toString());
		service = ClassLoaderService.getInstance().getService(service.getCanonicalName());
		obj = service.newInstance();
		System.out.println(service.getMethod("serviceMethod").invoke(obj));
	}
	
	private static Class<?> loadClass(String classPath, String className, Map<String, URLClassLoader> clMap) throws Exception
	{
		// caminho para o arquivo de classe
		File f = new File(classPath);
		URL url = f.toURI().toURL();
//		System.out.println(classPath);
//		System.out.println(f.exists());
//		System.out.println(url);
//		System.out.println(inspectToString(FileUtils.readFileToByteArray(f)));
		
		// novo classloader para a classe
		URLClassLoader cl = new URLClassLoader(new URL[] {url});
		Class<?> c = cl.loadClass(className);
		
		// verifica se a classe ja havia sido carregada
		URLClassLoader clPrev = clMap.remove(className);
		
		if ( clPrev != null )
		{
			// "descarrega a classe anterior"			
			clPrev.close();
		}
		
		// registra o novo classloader para a classe
		clMap.put(className, cl);
		return c;
	}
	
	private static String parseEL(String text)
	{
		// procura por ' e adiciona escape
		System.out.println("> TEXT: " + text);
		String str = text.trim().replaceAll("'", "\\\\'");
		System.out.println("> STEP 1: " + str);
		
		str = str.replaceAll("\\$\\{(.+?)\\}", "' + $1 + '");
		System.out.println("> STEP 2: " + str);

//		str = str.replaceAll("\\$\\{(.+)\\}$", "' + $1 + '");
//		System.out.println("> STEP 3: " + str);
		
		str = "'" + str + "'";		
		System.out.println("> STEP 4: " + str);

//		System.out.println(str);
		return str;
	}
	
	/**
	 * Retorna uma String com as informacoes de bytecode de uma classe.
	 * 
	 * @param classBytecode os bytecodes da classe 
	 * @return as informacoes de bytecode da classe informada
	 */
	public static String inspectToString(byte[] classBytecode)
	{
		ClassReader classReader = new ClassReader(classBytecode);
		StringWriter buffer = new StringWriter();
		TraceClassVisitor tracer = new TraceClassVisitor(new PrintWriter(buffer));
		classReader.accept(tracer, 0);
		
		return ( buffer.toString() );
	}
}
