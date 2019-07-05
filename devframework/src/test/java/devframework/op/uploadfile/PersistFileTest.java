package devframework.op.uploadfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import devframework.utils.Utils;

public class PersistFileTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private PersistFile persistFile;

	@Before
	public void setUp() throws IOException {
		persistFile = new PersistFile();
		File diretorio = new File("/diretorio");
		if (diretorio.exists()) {
			FileUtils.deleteDirectory(diretorio);
		}
	}

	@Test
	public void testeListComUploadDirInvalido() throws FileNotFoundException {
		Utils.getInstance().setProperty("upload.dir", "/aaa/bbb/");
		thrown.expect(FileNotFoundException.class);
		persistFile.list();
	}

	@Test
	public void testeListComUploadDirVazio() throws FileNotFoundException {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(0, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComDoisArquivosClass() throws IOException {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/Agenda.class"));
		Files.copy(getClass().getClassLoader().getResourceAsStream("Tarefa.class"),
				Paths.get("/diretorio/Tarefa.class"));
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(2, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComUmArquivoClassEOutroGenerico() throws IOException {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/Agenda.class"));
		File arquivo2 = new File("/diretorio/arquivo1");
		arquivo2.createNewFile();
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(1, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComDoisArquivosGenericos() throws IOException {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		File arquivo1 = new File("/diretorio/arquivo1");
		File arquivo2 = new File("/diretorio/arquivo1");
		arquivo1.createNewFile();
		arquivo2.createNewFile();
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(0, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComDoisDiretoriosVazios() throws FileNotFoundException {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(0, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComArquivoClassNoSubdiretorio() throws IOException {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/subdiretorio/Agenda.class"));
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(1, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComClassSemAnotacaoNecessaria() throws IOException {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Pessoa.class"),
				Paths.get("/diretorio/Pessoa.class"));
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(0, arquivos.size());
	}

}
