package devframework.op.uploadfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import devframework.utils.Utils;
import junit.framework.TestCase;

public class PersistFileTest extends TestCase {

	private PersistFile persistFile;

	@Before
	public void setUp() {
		persistFile = new PersistFile();
		File diretorio = new File("/diretorio");
		if(diretorio.exists()) {
			try {
				FileUtils.deleteDirectory(diretorio);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testeListComUploadDirInvalido() {
		Utils.getInstance().setProperty("upload.dir", "/aaa/bbb/");
		try {
			persistFile.list();
			fail("Deveria ter lançado FileNotFoundException");
		} catch (FileNotFoundException e) {

		}
	}

	@Test
	public void testeListComUploadDirVazio() {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(0, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeListComUploadDirComDoisArquivosClass() {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
	    try {
			Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"), Paths.get("/diretorio/Agenda.class"));
			Files.copy(getClass().getClassLoader().getResourceAsStream("Tarefa.class"), Paths.get("/diretorio/Tarefa.class"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}		    
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(2, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testeListComUploadDirComUmArquivoClassEOutroGenerico() {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
	    try {
			Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"), Paths.get("/diretorio/Agenda.class"));
			File arquivo2 = new File("/diretorio/arquivo1");
			arquivo2.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	    
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(1, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeListComUploadDirComDoisArquivosGenericos() {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
	    try {
	    	File arquivo1 = new File("/diretorio/arquivo1");
			File arquivo2 = new File("/diretorio/arquivo1");
			arquivo1.createNewFile();
			arquivo2.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	    
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(0, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeListComUploadDirComDoisDiretoriosVazios() {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(0, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeListComUploadDirComArquivoClassNoSubdiretorio() {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
	    try {
			Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"), Paths.get("/diretorio/subdiretorio/Agenda.class"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}		    
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(1, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeListComUploadDirComClassSemAnotacaoNecessaria() {
		File diretorio = new File("/diretorio/subdiretorio");
		diretorio.mkdirs();
	    try {
			Files.copy(getClass().getClassLoader().getResourceAsStream("Pessoa.class"), Paths.get("/diretorio/Pessoa.class"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}		    
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		try {
			List<String> arquivos = persistFile.list();
			assertEquals(0, arquivos.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
