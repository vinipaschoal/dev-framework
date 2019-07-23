package org.esfinge.virtuallab.services;

import java.io.InputStream;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.domain.Agenda;
import org.esfinge.virtuallab.domain.AgendaInvalida;
import org.esfinge.virtuallab.domain.Pessoa;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaInvalida;
import org.esfinge.virtuallab.junit4.RepeatTest;
import org.esfinge.virtuallab.junit4.RepeatTestRule;
import org.esfinge.virtuallab.services.ClassValidationService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Testes unitarios para a classe ClassValidationService.
 */
public class ClassValidationServiceTest{
	// numero de repeticoes dos testes
	private static final int REP_NUM = 3;

	@Rule
	// habilita a repeticao de testes
	public RepeatTestRule rule = new RepeatTestRule();

	@BeforeClass
	public static void setUp() {
		// copia as classes que serao utilizadas para o diretorio de teste
		TestUtils.copyToTestDir(Agenda.class, AgendaInvalida.class, Tarefa.class, TarefaInvalida.class, Pessoa.class);

		// cria os arquivos jar de teste
		TestUtils.createJar("jarValido.jar", Agenda.class, AgendaInvalida.class, Tarefa.class, TarefaInvalida.class,
				Pessoa.class);
		TestUtils.createJar("jarInvalido.jar", AgendaInvalida.class, TarefaInvalida.class, Pessoa.class);
	}

	/*@AfterClass
	public static void cleanUp() {
		// apaga os arquivos do diretorio de teste
		TestUtils.cleanTestDir();
	}*/

	@Test
	@RepeatTest(times = REP_NUM)
	public void testValidClassFromPath() throws Exception {
		// classe: Agenda.class
		String classPath = TestUtils.pathFromTestDir("Agenda.class");

		Class<?> clazz = ClassValidationService.getInstance().isValidClass(classPath);
		Assert.assertEquals(Agenda.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testValidClassFromStream() throws Exception {
		// classe: Tarefa.class
		InputStream classStream = TestUtils.streamFromTestDir("Tarefa.class");

		Class<?> clazz = ClassValidationService.getInstance().isValidClass(classStream, "Tarefa");
		Assert.assertEquals(Tarefa.class, clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testInvalidClassFromPath() throws Exception {
		// classe: Pessoa.class
		String classPath = TestUtils.pathFromTestDir("Pessoa.class");

		Class<?> clazz = ClassValidationService.getInstance().isValidClass(classPath);
		Assert.assertNull(clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testInvalidClassFromStream() throws Exception {
		// classe: AgendaInvalida.class
		InputStream classStream = TestUtils.streamFromTestDir("AgendaInvalida.class");

		Class<?> clazz = ClassValidationService.getInstance().isValidClass(classStream, "AgendaInvalida.class");
		Assert.assertNull(clazz);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testValidJarFromPath() throws Exception {
		// jar: jarValido.jar
		String jarPath = TestUtils.pathFromTestDir("jarValido.jar");

		List<Class<?>> classList = ClassValidationService.getInstance().isValidJar(jarPath);
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(Agenda.class));
		Assert.assertTrue(classList.contains(Tarefa.class));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testValidJarFromStream() throws Exception {
		// jar: jarValido.jar
		InputStream jarStream = TestUtils.streamFromTestDir("jarValido.jar");

		List<Class<?>> classList = ClassValidationService.getInstance().isValidJar(jarStream, "jarValido.jar");
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(Agenda.class));
		Assert.assertTrue(classList.contains(Tarefa.class));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testInvalidJarFromPath() throws Exception {
		// jar: jarInvalido.jar
		String jarPath = TestUtils.pathFromTestDir("jarInvalido.jar");

		List<Class<?>> classList = ClassValidationService.getInstance().isValidJar(jarPath);
		Assert.assertNull(classList);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testInvalidJarFromStream() throws Exception {
		// jar: jarInvalido.jar
		InputStream jarStream = TestUtils.streamFromTestDir("jarInvalido.jar");

		List<Class<?>> classList = ClassValidationService.getInstance().isValidJar(jarStream, "jarInvalido.jar");
		Assert.assertNull(classList);
	}
}
