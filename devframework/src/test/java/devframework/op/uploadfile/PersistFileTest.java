package devframework.op.uploadfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

	private final String diretorioRaiz = File.separator+"tmp_test" + File.separator + "diretorio";

	@Before
	public void setUp() throws IOException {
		persistFile = new PersistFile();
		Utils.getInstance().setProperty("upload.dir", diretorioRaiz);
		File diretorio = new File(diretorioRaiz);
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
		File diretorio = new File(File.separator + diretorioRaiz);
		diretorio.mkdir();
		Assert.assertEquals(0, persistFile.list().size());
	}

	@Test
	public void testeListComUploadDirComDoisArquivosClass() throws IOException {
		File diretorio = new File(File.separator + diretorioRaiz);
		diretorio.mkdir();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "devframework" + File.separator + "domain"
				+ File.separator + "Agenda.class"), Paths.get(diretorioRaiz + File.separator + "Agenda.class"));
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "devframework" + File.separator + "domain"
				+ File.separator + "Tarefa.class"), Paths.get(diretorioRaiz + File.separator + "Tarefa.class"));
		Assert.assertEquals(2, persistFile.list().size());
	}

	@Test
	public void testeListComUploadDirComUmArquivoClassEOutroGenerico() throws IOException {
		File diretorio = new File(File.separator + diretorioRaiz);
		diretorio.mkdir();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "devframework" + File.separator + "domain"
				+ File.separator + "Agenda.class"), Paths.get(diretorioRaiz + File.separator + "Agenda.class"));
		File arquivo2 = new File(File.separator + diretorioRaiz + File.separator + "arquivo1");
		arquivo2.createNewFile();
		Assert.assertEquals(1, persistFile.list().size());
	}

	@Test
	public void testeListComUploadDirComDoisArquivosGenericos() throws IOException {
		File diretorio = new File(File.separator + diretorioRaiz);
		diretorio.mkdir();
		File arquivo1 = new File(File.separator + diretorioRaiz + File.separator + "arquivo1");
		File arquivo2 = new File(File.separator + diretorioRaiz + File.separator + "arquivo2");
		arquivo1.createNewFile();
		arquivo2.createNewFile();
		Assert.assertEquals(0, persistFile.list().size());
	}

	@Test
	public void testeListComUploadDirComDoisDiretoriosVazios() throws FileNotFoundException {
		File diretorio = new File(File.separator + diretorioRaiz + File.separator + "subdiretorio");
		diretorio.mkdirs();
		Assert.assertEquals(0, persistFile.list().size());
	}

	@Test
	public void testeListComUploadDirComArquivoClassNoSubdiretorio() throws IOException {
		File diretorio = new File(File.separator + diretorioRaiz + File.separator + "subdiretorio");
		diretorio.mkdirs();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "devframework" + File.separator + "domain"
				+ File.separator + "Agenda.class"), Paths.get(diretorioRaiz + File.separator + "Agenda.class"));
		Assert.assertEquals(1, persistFile.list().size());
	}

	@Test
	public void testeListComUploadDirComClassSemAnotacaoNecessaria() throws IOException {
		File diretorio = new File(File.separator + diretorioRaiz + File.separator + "subdiretorio");
		diretorio.mkdirs();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "devframework" + File.separator + "domain"
				+ File.separator + "Pessoa.class"), Paths.get(diretorioRaiz + File.separator + "Pessoa.class"));
		Assert.assertEquals(0, persistFile.list().size());
	}

}
