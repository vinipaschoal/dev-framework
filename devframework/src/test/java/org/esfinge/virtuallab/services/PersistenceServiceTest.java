package org.esfinge.virtuallab.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.domain.Agenda;
import org.esfinge.virtuallab.domain.AgendaInvalida;
import org.esfinge.virtuallab.domain.ClassDescriptor;
import org.esfinge.virtuallab.domain.Pessoa;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaInvalida;
import org.esfinge.virtuallab.junit4.RepeatTest;
import org.esfinge.virtuallab.junit4.RepeatTestRule;
import org.esfinge.virtuallab.services.PersistenceService;
import org.esfinge.virtuallab.utils.Utils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Testes unitarios para a classe PersistenceService.
 */
public class PersistenceServiceTest {
	// numero de repeticoes dos testes
	private static final int REP_NUM = 1;

	// diretorio padrao de upload
	private static final String UPLOAD_DIR = Utils.getInstance().getUploadDir();

	@Rule
	// habilita a repeticao de testes
	public RepeatTestRule rule = new RepeatTestRule();

	@BeforeClass
	public static void setUp() {
		TestUtils.cleanTestDir();
		// atribui o diretorio de testes como diretorio de upload
		Utils.getInstance().setProperty("upload.dir", TestUtils.TEST_DIR);
	}

	@AfterClass
	public static void cleanUp() throws IOException {
		// apaga os arquivos do diretorio de teste
		TestUtils.cleanTestDir();
		// volta o diretorio de upload original
		Utils.getInstance().setProperty("upload.dir", UPLOAD_DIR);
	}

	@Before
	public void cleanUploadDir() throws IOException {
		TestUtils.cleanTestDir();
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirVazio() {
		Assert.assertEquals(0, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());
		Assert.assertEquals(0, PersistenceService.getInstance().list().size());
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComDoisArquivosClass() {
		// copia 2 classes validas para o diretorio de upload
		TestUtils.copyToTestDir(Agenda.class, Tarefa.class);
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(new ClassDescriptor(Agenda.class)));
		Assert.assertTrue(classList.contains(new ClassDescriptor(Tarefa.class)));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComUmArquivoClassEOutroGenerico() throws Exception {
		// copia 1 classe valida e 1 arquivo generico para o diretorio de upload
		TestUtils.copyToTestDir(Agenda.class);
		FileUtils.touch(Paths.get(TestUtils.TEST_DIR, "FakeClass.txt").toFile());
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(1, classList.size());
		Assert.assertTrue(classList.contains(new ClassDescriptor(Agenda.class)));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComDoisArquivosGenericos() throws Exception {
		// copia 2 arquivos generico para o diretorio de upload
		FileUtils.touch(Paths.get(TestUtils.TEST_DIR, "FakeClass1.txt").toFile());
		FileUtils.touch(Paths.get(TestUtils.TEST_DIR, "FakeClass2.txt").toFile());
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		// copia 2 classes invalidas para o diretorio de upload
		TestUtils.copyToTestDir(Pessoa.class, TarefaInvalida.class);

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(0, classList.size());
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComArquivoClassNoSubdiretorio() throws Exception {
		// cria um subdiretorio com 1 classe valida dentro
		TestUtils.copyToTestDir(Agenda.class);
		FileUtils.moveFileToDirectory(Paths.get(TestUtils.TEST_DIR, "Agenda.class").toFile(),
				Paths.get(TestUtils.TEST_DIR, "subdir").toFile(),true);
		Assert.assertEquals(3, FileUtils
				.listFilesAndDirs(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).size());

		// deve listar classes de subdiretorios
		//o list() esta listando todas as classes de subdiretorios atualmente
		Assert.assertEquals(1, PersistenceService.getInstance().list().size());
		
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComClassSemAnotacaoNecessaria() {
		// copia 2 classes invalidas para o diretorio de upload
		TestUtils.copyToTestDir(AgendaInvalida.class, TarefaInvalida.class);
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(0, classList.size());
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComJarValido() {
		// cria o arquivo jar de teste com 2 classes validas e 3 invalidas
		TestUtils.createJar("jarValido.jar", Agenda.class, AgendaInvalida.class, Tarefa.class, TarefaInvalida.class,
				Pessoa.class);
		Assert.assertEquals(1, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(new ClassDescriptor(Agenda.class)));
		Assert.assertTrue(classList.contains(new ClassDescriptor(Tarefa.class)));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComJarInvalido() {
		// cria o arquivo jar de teste com 3 classes invalidas
		TestUtils.createJar("jarInvalido.jar", AgendaInvalida.class, TarefaInvalida.class, Pessoa.class);
		Assert.assertEquals(1, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(0, classList.size());
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComJarEClassValidos() {
		// cria o arquivo jar de teste com 1 classe valida
		// copia 1 classe valida para o diretorio de upload
		TestUtils.createJar("jarValido.jar", Agenda.class);
		TestUtils.copyToTestDir(Tarefa.class);
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(new ClassDescriptor(Agenda.class)));
		Assert.assertTrue(classList.contains(new ClassDescriptor(Tarefa.class)));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComJarEClassInvalidos() {
		// cria o arquivo jar de teste com 2 classes invalidas
		// copia 1 classe invalida para o diretorio de upload
		TestUtils.createJar("jarInvalido.jar", AgendaInvalida.class, TarefaInvalida.class);
		TestUtils.copyToTestDir(Pessoa.class);
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(0, classList.size());
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComJarValidoEClassInvalido() {
		// cria o arquivo jar de teste com 2 classes validas
		// copia 1 classe invalida para o diretorio de upload
		TestUtils.createJar("jarValido.jar", Agenda.class, Tarefa.class, AgendaInvalida.class, TarefaInvalida.class);
		TestUtils.copyToTestDir(Pessoa.class);
		Assert.assertEquals(2, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(new ClassDescriptor(Agenda.class)));
		Assert.assertTrue(classList.contains(new ClassDescriptor(Tarefa.class)));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeListComUploadDirComJarInvalidoEClassValido() {
		// cria o arquivo jar de teste com 1 classe invalida
		// copia 2 classes validas para o diretorio de upload
		TestUtils.createJar("jarInvalido.jar", Pessoa.class);
		TestUtils.copyToTestDir(Agenda.class, Tarefa.class);
		Assert.assertEquals(3, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		List<ClassDescriptor> classList = PersistenceService.getInstance().list();
		Assert.assertEquals(2, classList.size());
		Assert.assertTrue(classList.contains(new ClassDescriptor(Agenda.class)));
		Assert.assertTrue(classList.contains(new ClassDescriptor(Tarefa.class)));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeSaveComClassValido() throws Exception {
		// classe: Agenda.class
		InputStream classStream = this.createStreamForUploadClass(Agenda.class);

		Assert.assertTrue(PersistenceService.getInstance().save(classStream, "Agenda.class"));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeSaveComClassInvalida() throws Exception {
		// classe: Pessoa.class
		InputStream classStream = this.createStreamForUploadClass(Pessoa.class);

		Assert.assertFalse(PersistenceService.getInstance().save(classStream, "Pessoa.class"));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeSaveComJarValido() throws Exception {
		// jar: jarValido.jar
		InputStream jarStream = this.createStreamForUploadJar("jarValido.jar", Agenda.class, TarefaInvalida.class,
				Pessoa.class);

		Assert.assertTrue(PersistenceService.getInstance().save(jarStream, "jarValido.jar"));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeSaveComJarInvalido() throws Exception {
		// jar: jarInvalido.jar
		InputStream jarStream = this.createStreamForUploadJar("jarInvalido.jar", AgendaInvalida.class,
				TarefaInvalida.class, Pessoa.class);

		Assert.assertFalse(PersistenceService.getInstance().save(jarStream, "jarInvalido.jar"));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeGetClassComClassValido() {
		// copia 3 classes para o diretorio de upload
		TestUtils.copyToTestDir(Agenda.class, AgendaInvalida.class, Tarefa.class);
		Assert.assertEquals(3, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(Agenda.class.getCanonicalName());
		Assert.assertNotNull(classDesc);
		Assert.assertEquals(new ClassDescriptor(Agenda.class), classDesc);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeGetClassComClassInvalido() {
		// copia 3 classes para o diretorio de upload
		TestUtils.copyToTestDir(Agenda.class, AgendaInvalida.class, Tarefa.class);
		Assert.assertEquals(3, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(AgendaInvalida.class.getCanonicalName());
		Assert.assertNull(classDesc);
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeGetClassComClassInexistente() {
		Assert.assertEquals(0, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		Assert.assertNull(PersistenceService.getInstance().getClass("my.FakeClass"));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeGetClassComJarValido() {
		// cria o arquivo jar de teste com 2 classes validas
		TestUtils.createJar("jarValido.jar", Agenda.class, Tarefa.class, AgendaInvalida.class, TarefaInvalida.class);
		Assert.assertEquals(1, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		ClassDescriptor classDesc = PersistenceService.getInstance().getClass(Agenda.class.getCanonicalName());
		Assert.assertNotNull(classDesc);
		Assert.assertEquals(new ClassDescriptor(Agenda.class), classDesc);

		classDesc = PersistenceService.getInstance().getClass(Tarefa.class.getCanonicalName());
		Assert.assertNotNull(classDesc);
		Assert.assertEquals(new ClassDescriptor(Tarefa.class), classDesc);

		Assert.assertNull(PersistenceService.getInstance().getClass(AgendaInvalida.class.getCanonicalName()));
		Assert.assertNull(PersistenceService.getInstance().getClass(TarefaInvalida.class.getCanonicalName()));
	}

	@Test
	@RepeatTest(times = REP_NUM)
	public void testeGetClassComJarInvalido() {
		// cria o arquivo jar de teste com 3 classes invalidas
		TestUtils.createJar("jarInvalido.jar", AgendaInvalida.class, TarefaInvalida.class, Pessoa.class);
		Assert.assertEquals(1, FileUtils.listFiles(TestUtils.TEST_DIR_FILE, TrueFileFilter.INSTANCE, null).size());

		Assert.assertNull(PersistenceService.getInstance().getClass(AgendaInvalida.class.getCanonicalName()));
		Assert.assertNull(PersistenceService.getInstance().getClass(TarefaInvalida.class.getCanonicalName()));
		Assert.assertNull(PersistenceService.getInstance().getClass(Pessoa.class.getCanonicalName()));
	}

	/**
	 * Metodo utilitario para testar os metodos save() da classe PersistenceService.
	 */
	private InputStream createStreamForUploadClass(Class<?> clazz) throws Exception {
		// cria um subdiretorio temporario e move a classe para la
		TestUtils.copyToTestDir(clazz);
		FileUtils.moveFileToDirectory(Paths.get(TestUtils.TEST_DIR, clazz.getSimpleName() + ".class").toFile(),
				Paths.get(TestUtils.TEST_DIR, "subdir").toFile(), true);

		return FileUtils
				.openInputStream(Paths.get(TestUtils.TEST_DIR, "subdir", clazz.getSimpleName() + ".class").toFile());
	}

	/**
	 * Metodo utilitario para testar os metodos save() da classe PersistenceService.
	 */
	private InputStream createStreamForUploadJar(String jarName, Class<?>... classes) throws Exception {
		// cria o jar no diretorio de teste
		TestUtils.createJar(jarName, classes);

		// cria um subdiretorio temporario e move o jar para la
		FileUtils.moveFileToDirectory(Paths.get(TestUtils.TEST_DIR, jarName).toFile(),
				Paths.get(TestUtils.TEST_DIR, "subdir").toFile(), true);

		return FileUtils.openInputStream(Paths.get(TestUtils.TEST_DIR, "subdir", jarName).toFile());
	}
}
