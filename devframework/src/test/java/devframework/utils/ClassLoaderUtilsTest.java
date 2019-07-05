package devframework.utils;

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
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		ClassLoaderUtils.getInstance().loadClass("/diretorio/devframework/domain/Agenda.class");
	}

	@Test
	public void testLoadClassCarregarDuasVezesMesmaClasseJaCarregada() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		ClassLoaderUtils.getInstance().loadClass("/diretorio/devframework/domain/Agenda.class");
		ClassLoaderUtils.getInstance().loadClass("/diretorio/devframework/domain/Agenda.class");
	}

	@Test
	public void testLoadClassCarregarClasseNova() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		thrown.expect(FileNotFoundException.class);
		ClassLoaderUtils.getInstance().loadClass("/diretorio/devframework/domain/Teste.class");
	}

}
