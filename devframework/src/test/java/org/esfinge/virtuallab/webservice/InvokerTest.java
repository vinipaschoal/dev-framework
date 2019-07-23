package org.esfinge.virtuallab.webservice;

import java.io.File;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.domain.Agenda;
import org.esfinge.virtuallab.domain.AgendaInvalida;
import org.esfinge.virtuallab.domain.Pessoa;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaInvalida;
import org.esfinge.virtuallab.utils.Utils;
import org.esfinge.virtuallab.webservice.Invoker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InvokerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Invoker invoker = null;

	private Object[] params;

	@BeforeClass
	public static void setUpClass() throws Exception {
		TestUtils.copyToTestDir(Agenda.class, AgendaInvalida.class, Tarefa.class, TarefaInvalida.class, Pessoa.class);
		TestUtils.createJar("test.jar", Agenda.class, Tarefa.class, Pessoa.class);
	}
	
	
	@Before
	public void setUp() throws Exception {
		Utils.getInstance().setProperty("upload.dir", TestUtils.TEST_DIR);
		this.invoker = new Invoker();
		params = new Object[2];
		params[0] = "teste";
		params[1] = Integer.toString(12);
	}

	@Test
	public void testeCallcomDiretorioNull() throws Exception {
		Utils.getInstance().setProperty("upload.dir", "");
		thrown.expect(IllegalArgumentException.class);
		invoker.call(null, "org.esfinge.virtuallab.domain.Agenda", "getNome", params);
	}

	@Test
	public void testeCallcomDiretorioInvalido() throws Exception {
		File diretorio = new File("/diretorioInvalido");
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
		Utils.getInstance().setProperty("upload.dir", diretorio.getAbsolutePath());
		thrown.expect(Exception.class);
		invoker.call("org.esfinge.virtuallab.domain.Agenda", "getNome", params);
	}

	@Test
	public void testeCallcomClassNameNull() throws Exception {
		thrown.expect(Exception.class);
		invoker.call(null, "getNome", params);
	}

	@Test
	public void testeCallcomClassNameInvalido() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("NomeFicticio", "getNome", params);
	}

	@Test
	public void testeCallcomNomeMetodoNull() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("org.esfinge.virtuallab.domain.Agenda", null, params);
	}

	@Test
	public void testeCallcomNomeMetodoInvalido() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("org.esfinge.virtuallab.domain.Agenda", "getNaoExiste", params);
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNaClasse() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("org.esfinge.virtuallab.domain.TarefaInvalida", "getNome", params);
	}

	@Test
	public void testeCallcomClasseSemAnotacaoNoMetodo() throws Exception {
		thrown.expect(Exception.class);
		invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaSemAnotacao", params);
	}

	@Test
	public void testeCallcomClasseValida() throws Exception {
		params = new Object[0];
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoa", params);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaERetornoJson() throws Exception {
		params = new Object[0];
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaJson", params);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(object);
		Assert.assertTrue(jsonString.contains("nome"));
	}
	
	@Test
	public void testeCallcomClasseValidaERetornoHtmlTable() throws Exception {
		params = new Object[0];
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaHtml2", params);
		Assert.assertTrue(((String)object).contains("<table"));
	}
	
	@Test
	public void testeCallcomClasseValidaERetornoHtmlTableSemObjetos() throws Exception {
		params = new Object[0];
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaHtml3", params);
		Assert.assertTrue(((String)object).contains("<table"));
	}
	
	

	@Test
	public void testeCallcomClasseValidaERetornoPrimitivo() throws Exception {
		params = new Object[0];
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaPrimitivo", params);
		Assert.assertTrue(ClassUtils.isPrimitiveOrWrapper(object.getClass()));
	}

	@Test
	public void testeCallcomClasseValidaEClasseVoidAnotada() throws Exception {
		params = new Object[0];
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaSemRetorno", params);
		Assert.assertNull(object);
	}

	@Test
	public void testeCallcomClasseValidaEDoisParametros() throws Exception {
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaComParametro", params);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaEDoisParametrosEmOverload() throws Exception {
		params[1] = "teste";
		params[0] = Integer.toString(12);
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaComParametro", params);
		Assert.assertNotNull(object);
	}

	@Test
	public void testeCallcomClasseValidaEParametrosInvalidos() throws Exception {
		params[1] = "teste2";
		thrown.expect(Exception.class);
		invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaComParametro", params);
	}

	@Test
	public void testeCallcomClasseValidaEChamadaPeloAlias() throws Exception {
		Object object = invoker.call("org.esfinge.virtuallab.domain.Agenda", "getPessoaComParametroStringLong", params);
		Assert.assertNotNull(object);
	}

}
