package org.esfinge.virtuallab.services;

import java.io.InputStream;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.domain.Agenda;
import org.esfinge.virtuallab.domain.Pessoa;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.junit4.RepeatTest;
import org.esfinge.virtuallab.junit4.RepeatTestRule;
import org.esfinge.virtuallab.services.ClassLoaderService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Testes unitarios para a classe ClassLoaderService.
 */
public class ClassLoaderServiceTest
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

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromPath() throws Exception
	{
		// classe: Agenda.class
		String classPath = TestUtils.pathFromTestDir("Agenda.class");

		Class<?> clazz = ClassLoaderService.getInstance().loadClass(classPath);
		Assert.assertEquals(Agenda.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromStream() throws Exception
	{
		// classe: Pessoa.class
		InputStream classStream = TestUtils.streamFromTestDir("Pessoa.class");

		Class<?> clazz = ClassLoaderService.getInstance().loadClass(classStream, "Pessoa");
		Assert.assertEquals(Pessoa.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromJarPath() throws Exception
	{
		// jar: test.jar
		// classe: Tarefa.class
		String jarPath = TestUtils.pathFromTestDir("test.jar");
		String className = Tarefa.class.getName() + ".class";

		Class<?> clazz = ClassLoaderService.getInstance().loadClassFromJar(jarPath, className);
		Assert.assertEquals(Tarefa.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadClassFromJarStream() throws Exception
	{
		// jar: test.jar
		// classe: Agenda.class
		InputStream jarStream = TestUtils.streamFromTestDir("test.jar");
		String className = Agenda.class.getName();

		Class<?> clazz = ClassLoaderService.getInstance().loadClassFromJar(jarStream, className);
		Assert.assertEquals(Agenda.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadJarFromPath() throws Exception
	{
		// jar: test.jar
		String jarPath = TestUtils.pathFromTestDir("test.jar");

		List<Class<?>> classList = ClassLoaderService.getInstance().loadJar(jarPath);
		Assert.assertEquals(3, classList.size());
		Assert.assertTrue(classList.contains(Agenda.class));
		Assert.assertTrue(classList.contains(Pessoa.class));
		Assert.assertTrue(classList.contains(Tarefa.class));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testLoadJarFromStream() throws Exception
	{
		// jar: test.jar
		InputStream jarStream = TestUtils.streamFromTestDir("test.jar");

		List<Class<?>> classList = ClassLoaderService.getInstance().loadJar(jarStream);
		Assert.assertEquals(3, classList.size());
		Assert.assertTrue(classList.contains(Agenda.class));
		Assert.assertTrue(classList.contains(Pessoa.class));
		Assert.assertTrue(classList.contains(Tarefa.class));
	}

	@Test
	public void testLoadAll() throws Exception
	{
		ClassLoaderService.getInstance().loadAll();
	}
}
