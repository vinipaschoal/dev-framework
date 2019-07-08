package devframework.webservice;

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
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


public class InvokerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Invoker invoker = null;

	@Before
	public void setUp() throws IOException {
		this.invoker = new Invoker();
		File diretorio = new File("/diretorio");
		if (diretorio.exists()) {
			FileUtils.deleteDirectory(diretorio);
		}
	}

	@Test
	public void testeCallcomDiretorioNull() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		thrown.expect(FileNotFoundException.class);
		invoker.call(null, "devframework.domain.Agenda", "getNome", new Object[0]);
	}

	@Test
	public void testeCallcomDiretorioInvalido() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		thrown.expect(FileNotFoundException.class);
		invoker.call("/diretorioInvalido", "devframework.domain.Agenda", "getNome", new Object[0]);
	}

	@Test
	public void testeCallcomClassNameNull() throws Exception {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		thrown.expect(NullPointerException.class);
		invoker.call("/diretorio", null, "getNome", new Object[0]);
	}

	@Test
	public void testeCallcomClassNameInvalido() throws Exception {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		thrown.expect(FileNotFoundException.class);
		invoker.call("/diretorio", "NomeFicticio", "getNome", new Object[0]);
	}

	@Test
	public void testeCallcomNomeMetodoNull() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		thrown.expect(NullPointerException.class);
		invoker.call("/diretorio", "devframework.domain.Agenda", null, new Object[0]);
	}

	@Test
	public void testeCallcomNomeMetodoInvalido() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		thrown.expect(NoSuchMethodException.class);
		invoker.call("/diretorio", "devframework.domain.Agenda", "getNaoExiste", new Object[0]);
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNaClasse() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Pessoa.class"),
				Paths.get("/diretorio/devframework/domain/Pessoa.class"));
		Assert.assertNull(invoker.call("/diretorio", "devframework.domain.Pessoa", "getNome", new Object[0]));
	}
	
	@Test
	public void testeCallcomClasseSemAnotacaoNoMetodo() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		Assert.assertNull(invoker.call("/diretorio", "devframework.domain.Agenda", "getPessoaSemAnotacao", new Object[0]));
	}
	
	@Test
	public void testeCallcomClasseValida() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		Object object = invoker.call("/diretorio", "devframework.domain.Agenda", "getPessoa", new Object[0]);
		Assert.assertNotNull(object);
	}
	
	@Test
	public void testeCallcomClasseValidaERetornoJson() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		Object object = invoker.call("/diretorio", "devframework.domain.Agenda", "getPessoaJson", new Object[0]);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(object);
		Assert.assertTrue(jsonString.contains("nome"));
	}
	
	@Test
	public void testeCallcomClasseValidaERetornoPrimitivo() throws Exception {
		File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));
		Object object = invoker.call("/diretorio", "devframework.domain.Agenda", "getPessoaPrimitivo", new Object[0]);
		Assert.assertTrue(ClassUtils.isPrimitiveOrWrapper(object.getClass()));
	}
	
	@Test
	public void testeCallcomClasseValidaEClasseVoidAnotada() throws Exception {
	/*	File diretorio = new File("/diretorio/devframework/domain");
		diretorio.mkdirs();
		Files.copy(getClass().getClassLoader().getResourceAsStream("Agenda.class"),
				Paths.get("/diretorio/devframework/domain/Agenda.class"));*/

		Object object = invoker.call("src/test/resources", "devframework.domain.Agenda", "getPessoaSemRetorno", new Object[0]);
		Assert.assertNull(object);
	}


}
