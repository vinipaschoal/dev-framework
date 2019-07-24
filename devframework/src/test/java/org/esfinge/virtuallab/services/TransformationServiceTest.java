package org.esfinge.virtuallab.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.domain.Agenda;
import org.esfinge.virtuallab.domain.AgendaInvalida;
import org.esfinge.virtuallab.domain.Pessoa;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaInvalida;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TransformationServiceTest {
	private static TransformationService transformationService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void setUpClass() throws Exception {
		TestUtils.copyToTestDir(Agenda.class, AgendaInvalida.class, Tarefa.class, TarefaInvalida.class, Pessoa.class);
		transformationService = new TransformationService();
	}

	@Test
	public void testTransformToJson() throws JsonProcessingException {
		Pessoa pessoa = new Pessoa("José", 50, "rua das acacias",new Date());
		String jsonReturn = transformationService.transformToJson(pessoa);
		Assert.assertTrue(jsonReturn.contains("\"idade\":50"));
	}
	
	@Test
	public void testTransformToJsonNull() throws JsonProcessingException{
		String jsonReturn = transformationService.transformToJson(null);
		Assert.assertEquals("null",jsonReturn);
	}
	
	
	@Test
	public void testTransformHtmlTable() throws Exception  {
		Pessoa pessoa = new Pessoa("José", 50, "rua das acacias",new Date());
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		pessoas.add(pessoa);
		String html = transformationService.transformToHtml(pessoas);
		Assert.assertTrue(html.contains("<table"));
	}
	
	@Test
	public void testTransformToHtmlTableNull() throws Exception{
		thrown.expect(NullPointerException.class);
		transformationService.transformToHtml(null);
	}
	
	@Test
	public void testwrapperPrimitiveBoolean() throws Exception {
		Assert.assertEquals(true,transformationService.wrapperPrimitive(boolean.class, "true"));
	}
	
	@Test
	public void testwrapperPrimitiveBooleanInvalid() throws Exception{
		Assert.assertEquals(false, transformationService.wrapperPrimitive(boolean.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveByte() throws Exception {
		byte x = 1;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(byte.class, "1"));
	}
	
	@Test
	public void testwrapperPrimitiveByteInvalid() throws Exception{
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(byte.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveShort() throws Exception {
		short x = 12;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(short.class, "12"));
	}
	
	@Test
	public void testwrapperPrimitiveShortInvalid() throws Exception{
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(short.class, "teste"));
	}

	@Test
	public void testwrapperPrimitiveInt() throws Exception {
		int x = 1342;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(int.class, "1342"));
	}
	
	@Test
	public void testwrapperPrimitiveIntInvalid() throws Exception{
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(int.class, "teste"));
	}

	@Test
	public void testwrapperPrimitiveLong() throws Exception {
		long x = 13422;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(long.class, "13422"));
	}
	
	@Test
	public void testwrapperPrimitiveLongInvalid() throws Exception {
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(long.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveFloat() throws Exception {
		float x = 13.1f;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(float.class, "13.1"));
	}
	
	@Test
	public void testwrapperPrimitiveFloatInvalid() throws Exception {
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(float.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveDouble() throws Exception {
		double x = 13.2;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(double.class, "13.2"));
	}
	
	@Test
	public void testwrapperPrimitiveDoubleInvalid() throws Exception {
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(double.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveInvalid() throws Exception {
		thrown.expect(Exception.class);
		Assert.assertEquals("teste", transformationService.wrapperPrimitive(Pessoa.class, "teste"));
	}
	
}
