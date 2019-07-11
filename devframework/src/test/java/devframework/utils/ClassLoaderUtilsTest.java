package devframework.utils;

<<<<<<< HEAD
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClassLoaderUtilsTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();


	@Before
	public void setUp() throws IOException {
		File diretorio = new File("/diretorio");
		if (diretorio.exists()) {
			FileUtils.deleteDirectory(diretorio);
		}
	}

	@Test
	public void testLoadClassUmaClasseJaCarregada() throws Exception {
		ClassLoaderUtils.getInstance().loadClass(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources"+File.separator+"devframework"+File.separator+"domain"+File.separator+"Agenda.class");
	}

	@Test
	public void testLoadClassCarregarDuasVezesMesmaClasseJaCarregada() throws Exception {
		ClassLoaderUtils.getInstance().loadClass(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources"+File.separator+"devframework"+File.separator+"domain"+File.separator+"Agenda.class");
		ClassLoaderUtils.getInstance().loadClass(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources"+File.separator+"devframework"+File.separator+"domain"+File.separator+"Agenda.class");
	}

=======
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import devframework.TestUtils;
import devframework.domain.Agenda;
import devframework.domain.Pessoa;
import devframework.domain.Tarefa;
import devframework.junit4.RepeatTest;
import devframework.junit4.RepeatTestRule;

/**
 * Testes unitarios para a classe ClassLoaderUtils.
 */
public class ClassLoaderUtilsTest 
{
	// numero de repeticoes dos testes
	private static final int REP_NUM = 3;

	@Rule
	// habilita a repeticao de testes
	public RepeatTestRule rule = new RepeatTestRule();

	
	@BeforeClass
	public static void setUp()
	{
		// copia as classes que serao utilizadas para o diretorio de teste
		TestUtils.copyToTestDir(Agenda.class, Pessoa.class);
		
		// cria o arquivo jar de teste
		TestUtils.createJar("test.jar", Agenda.class, Tarefa.class, Pessoa.class);
	}
	
	@AfterClass
	public static void cleanUp()
	{
		// apaga os arquivos do diretorio de teste
		TestUtils.cleanTestDir();
	}
	
	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromPath() throws Exception
	{
		// classe: Agenda.class
		String classPath = TestUtils.pathFromTestDir("Agenda.class");		
		
		Class<?> clazz = ClassLoaderUtils.getInstance().loadClass(classPath);
		assertEquals(Agenda.class, clazz);
	}
	
	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromStream() throws Exception
	{
		// classe: Pessoa.class
		InputStream classStream = TestUtils.streamFromTestDir("Pessoa.class");
		
		Class<?> clazz = ClassLoaderUtils.getInstance().loadClass(classStream, "Pessoa");
		assertEquals(Pessoa.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromJarPath() throws Exception
	{
		// jar: test.jar
		// classe: Tarefa.class
		String jarPath = TestUtils.pathFromTestDir("test.jar");
		String className = Tarefa.class.getName() + ".class";

		Class<?> clazz = ClassLoaderUtils.getInstance().loadClassFromJar(jarPath, className);
		assertEquals(Tarefa.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromJarStream() throws Exception
	{
		// jar: test.jar
		// classe: Agenda.class
		InputStream jarStream = TestUtils.streamFromTestDir("test.jar");
		String className = Agenda.class.getName();
		
		Class<?> clazz = ClassLoaderUtils.getInstance().loadClassFromJar(jarStream, className);
		assertEquals(Agenda.class, clazz);
	}
	
	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadJarFromPath() throws Exception
	{
		// jar: test.jar
		String jarPath = TestUtils.pathFromTestDir("test.jar");
		
		List<Class<?>> classList = ClassLoaderUtils.getInstance().loadJar(jarPath);
		assertEquals(3, classList.size());
		assertTrue(classList.contains(Agenda.class));
		assertTrue(classList.contains(Pessoa.class));
		assertTrue(classList.contains(Tarefa.class));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadJarFromStream() throws Exception
	{
		// jar: test.jar
		InputStream jarStream = TestUtils.streamFromTestDir("test.jar");
		
		List<Class<?>> classList = ClassLoaderUtils.getInstance().loadJar(jarStream);
		assertEquals(3, classList.size());
		assertTrue(classList.contains(Agenda.class));
		assertTrue(classList.contains(Pessoa.class));
		assertTrue(classList.contains(Tarefa.class));
	}
>>>>>>> c84eb54cee8a7049076ca84c248058c422200e69
}
