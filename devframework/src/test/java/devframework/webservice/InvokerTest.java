package devframework.webservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import devframework.utils.Utils;

public class InvokerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Invoker invoker = null;

	private Object[] params;

	@Before
	public void setUp() throws IOException {
		Utils.getInstance().setProperty("upload.dir", "/diretorio");
		this.invoker = new Invoker();
		File diretorio = new File("/diretorio");
		if (diretorio.exists()) {
			FileUtils.deleteDirectory(diretorio);
		}
		params = new Object[2];
		params[0] = "teste";
		params[1] = Integer.toString(12);
	}

	@Test
	public void testeCallcomDiretorioNull() throws Exception {
		Utils.getInstance().setProperty("upload.dir", "");
		thrown.expect(IllegalArgumentException.class);
		invoker.call(null, "devframework.domain.Agenda", "getNome", params);
	}

	@Test
	public void testeCallcomDiretorioInvalido() throws Exception {
		File diretorio = new File("/diretorioInvalido");
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
		Utils.getInstance().setProperty("upload.dir", diretorio.getAbsolutePath());
		thrown.expect(Exception.class);
		invoker.call("devframework.domain.Agenda", "getNome", params);
	}

	@Test
	public void testeCallcomClassNameNull() throws Exception {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		thrown.expect(NullPointerException.class);
		invoker.call(null, "getNome", params);
	}

	@Test
	public void testeCallcomClassNameInvalido() throws Exception {
		File diretorio = new File("/diretorio");
		diretorio.mkdir();
		thrown.expect(FileNotFoundException.class);
		invoker.call("NomeFicticio", "getNome", params);
	}

	@Test
	public void testeCallcomNomeMetodoNull() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("devframework.domain.Agenda", null, params);
	}

	@Test
	public void testeCallcomNomeMetodoInvalido() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("devframework.domain.Agenda", "getNaoExiste", params);
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNaClasse() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("devframework.domain.TarefaInvalida", "getNome", params);
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNoMetodo() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("devframework.domain.Agenda", "getPessoaSemAnotacao", params);
	}

	@Test
	public void testeCallcomClasseValida() throws Exception {
		params = new Object[0];
		Object object = invoker.call("devframework.domain.Agenda", "getPessoa", params);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaERetornoJson() throws Exception {
		params = new Object[0];
		Object object = invoker.call("devframework.domain.Agenda", "getPessoaJson", params);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(object);
		Assert.assertTrue(jsonString.contains("nome"));
	}

	@Test
	public void testeCallcomClasseValidaERetornoPrimitivo() throws Exception {
		params = new Object[0];
		Object object = invoker.call("devframework.domain.Agenda", "getPessoaPrimitivo", params);
		Assert.assertTrue(ClassUtils.isPrimitiveOrWrapper(object.getClass()));
	}

	@Test
	public void testeCallcomClasseValidaEClasseVoidAnotada() throws Exception {
		params = new Object[0];
		Object object = invoker.call("devframework.domain.Agenda", "getPessoaSemRetorno", params);
		Assert.assertNull(object);
	}

	@Test
	public void testeCallcomClasseValidaEDoisParametros() throws Exception {
		Object object = invoker.call("devframework.domain.Agenda", "getPessoaComParametro", params);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaEDoisParametrosEmOverload() throws Exception {
		params[1] = "teste";
		params[0] = Integer.toString(12);
		Object object = invoker.call("devframework.domain.Agenda", "getPessoaComParametro", params);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaEParametrosInvalidos() throws Exception {
		params[1] = "teste2";
		thrown.expect(Exception.class);
		invoker.call("devframework.domain.Agenda", "getPessoaComParametro", params);
	}

	@Test
	public void testeCallcomClasseValidaEChamadaPeloAlias() throws Exception {
		Object object = invoker.call("devframework.domain.Agenda", "getPessoaComParametroStringLong", params);
		Assert.assertNotNull(object);
	}

}
