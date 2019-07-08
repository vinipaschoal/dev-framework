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
		File diretorio = new File(System.getProperty("user.dir")+File.separator+"diretorio");
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
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "devframework" + File.separator + "domain" + File.separator
				+ "Agenda.class"), Paths.get("/diretorio/Agenda.class"));
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "devframework" + File.separator + "domain" + File.separator
				+ "Tarefa.class"),
				Paths.get("/diretorio/Tarefa.class"));
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(2, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComUmArquivoClassEOutroGenerico() throws IOException {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "devframework" + File.separator + "domain" + File.separator
				+ "Agenda.class"), Paths.get("/diretorio/Agenda.class"));
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
		File diretorio = new File(File.separator+System.getProperty("user.dir") + File.separator +"diretorio"+File.separator+"subdiretorio");
		diretorio.mkdirs();
		Utils.getInstance().setProperty("upload.dir", System.getProperty("user.dir") + File.separator +"diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(0, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComArquivoClassNoSubdiretorio() throws IOException {
		File diretorio = new File(File.separator+System.getProperty("user.dir") + File.separator +"diretorio"+File.separator+"subdiretorio");
		diretorio.mkdirs();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "devframework" + File.separator + "domain" + File.separator
				+ "Agenda.class"), Paths.get(System.getProperty("user.dir")+ File.separator +"diretorio"+File.separator+"Agenda.class"));
		Utils.getInstance().setProperty("upload.dir", System.getProperty("user.dir") + File.separator +"diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(1, arquivos.size());
	}

	@Test
	public void testeListComUploadDirComClassSemAnotacaoNecessaria() throws IOException {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
		Files.copy(Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "devframework" + File.separator + "domain" + File.separator
				+ "Pessoa.class"), Paths.get("/diretorio/Pessoa.class"));
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		List<String> arquivos = persistFile.list();
		Assert.assertEquals(0, arquivos.size());
	}

}
