package devframework.services;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import devframework.TestUtils;
import devframework.annotations.container.MethodContainer;
import devframework.domain.Agenda;
import devframework.domain.AgendaInvalida;
import devframework.domain.Pessoa;
import devframework.domain.Tarefa;
import devframework.domain.TarefaInvalida;

public class InformationClassServiceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		TestUtils.copyToTestDir(Agenda.class, AgendaInvalida.class, Tarefa.class, TarefaInvalida.class, Pessoa.class);
		TestUtils.createJar("test.jar", Agenda.class, Tarefa.class, Pessoa.class);
	}

	@Test
	public void testAliasFromServiceMethod() throws Exception {
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(Agenda.class)) {
			if ("getPessoaComParametroStringLong".equals(methodContainer.getAliasMethod())) {
				return;
			}
		}
		Assert.fail("deveria ter encontrado o alias");
	}

	@Test
	public void testAliasInexistenteFromServiceMethod() throws Exception {
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(Agenda.class)) {
			if ("aliasInexistente".equals(methodContainer.getAliasMethod())) {
				Assert.fail("não deveria ter encontrado o alias");
			}
		}
	}

	@Test
	public void testServiceMethods() throws Exception {
		Assert.assertEquals(10, InformationClassService.getInstance().getServiceMethods(Agenda.class).size());
	}
	
	@Test
	public void testServiceMethodsVazio() throws Exception {
		Assert.assertEquals(0, InformationClassService.getInstance().getServiceMethods(Pessoa.class).size());
	}
	
	@Test
	public void testServiceFields() throws Exception {
		Assert.assertTrue(InformationClassService.getInstance().getFields(Agenda.class).size()>0);
	}
	
	@Test
	public void testHtmlTableReturnPresent() throws Exception {
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(Agenda.class)) {
			if ("getPessoaHtml1".equals(methodContainer.getNomeMethod())&& (methodContainer.isTemAnotacaoHtmlTableReturn())) {
				return;
			}
		}
		Assert.fail("deveria ter encontrado o método com a anotação de htmlTable");
	}
	
	@Test
	public void testHtmlTableReturnNotPresent() throws Exception {
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(Agenda.class)) {
			if ("getPessoaSemRetorno".equals(methodContainer.getNomeMethod())&& (methodContainer.isTemAnotacaoHtmlTableReturn())) {
				Assert.fail("não deveria ter encontrado o método com a anotação de htmlTable");
			}
		}
	}
	
	@Test
	public void testJsoneReturnPresent() throws Exception {
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(Agenda.class)) {
			if ("getPessoaJson".equals(methodContainer.getNomeMethod())&& (methodContainer.isTemAnotacaoJsonReturn())) {
				return;
			}
		}
		Assert.fail("deveria ter encontrado o método com a anotação de jsonreturn");
	}
	
	@Test
	public void testJsonReturnNotPresent() throws Exception {
		for (MethodContainer methodContainer : InformationClassService.getInstance().getServiceMethods(Agenda.class)) {
			if ("getPessoaSemRetorno".equals(methodContainer.getNomeMethod())&& (methodContainer.isTemAnotacaoJsonReturn())) {
				Assert.fail("não deveria ter encontrado o método com a anotação de jsonreturn");
			}
		}
	}
	
	@Test
	public void testClassName() throws Exception {
		Assert.assertEquals("devframework.domain.Agenda", InformationClassService.getInstance().getClassName(Agenda.class));
	}
	
	@Test
	public void testClassNameComLabel() throws Exception {
		Assert.assertEquals("Pessoa com Label", InformationClassService.getInstance().getClassName(Pessoa.class));
	}
	

}
