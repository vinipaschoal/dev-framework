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
		thrown.expect(FileNotFoundException.class);
		invoker.call(null, "devframework.domain.Agenda", "getNome", new Object[0]);
	}

	@Test
	public void testeCallcomDiretorioInvalido() throws Exception {
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
		thrown.expect(NullPointerException.class);
		invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources", "devframework.domain.Agenda", null, new Object[0]);
	}

	@Test
	public void testeCallcomNomeMetodoInvalido() throws Exception {
		thrown.expect(NoSuchMethodException.class);
		invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources", "devframework.domain.Agenda", "getNaoExiste", new Object[0]);
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNaClasse() throws Exception {
		Assert.assertNull(invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources", "devframework.domain.Pessoa", "getNome", new Object[0]));
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNoMetodo() throws Exception {
		Assert.assertNull(invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources", "devframework.domain.Agenda", "getPessoaSemAnotacao", new Object[0]));
	}

	@Test
	public void testeCallcomClasseValida() throws Exception {
		Object object = invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources", "devframework.domain.Agenda", "getPessoa", new Object[0]);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaERetornoJson() throws Exception {
		Object object = invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources", "devframework.domain.Agenda", "getPessoaJson", new Object[0]);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(object);
		Assert.assertTrue(jsonString.contains("nome"));
	}

	@Test
	public void testeCallcomClasseValidaERetornoPrimitivo() throws Exception {
		Object object = invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources", "devframework.domain.Agenda", "getPessoaPrimitivo", new Object[0]);
		Assert.assertTrue(ClassUtils.isPrimitiveOrWrapper(object.getClass()));
	}

	@Test
	public void testeCallcomClasseValidaEClasseVoidAnotada() throws Exception {
		Object object = invoker.call(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources", "devframework.domain.Agenda", "getPessoaSemRetorno", new Object[0]);
		Assert.assertNull(object);

	}

}
