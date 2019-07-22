package devframework.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;

import devframework.TestUtils;
import devframework.domain.Agenda;
import devframework.domain.AgendaInvalida;
import devframework.domain.Pessoa;
import devframework.domain.Tarefa;
import devframework.domain.TarefaInvalida;

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
		String html = transformationService.transformToHtml(null);
	}
	
	@Test
	public void testwrapperPrimitiveBoolean() {
		Assert.assertEquals(true,transformationService.wrapperPrimitive(boolean.class, "true"));
	}
	
	@Test
	public void testwrapperPrimitiveBooleanInvalid(){
		Assert.assertEquals(false, transformationService.wrapperPrimitive(boolean.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveByte() {
		byte x = 1;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(byte.class, "1"));
	}
	
	@Test
	public void testwrapperPrimitiveByteInvalid(){
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(byte.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveShort() {
		short x = 12;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(short.class, "12"));
	}
	
	@Test
	public void testwrapperPrimitiveShortInvalid(){
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(short.class, "teste"));
	}

	@Test
	public void testwrapperPrimitiveInt() {
		int x = 1342;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(int.class, "1342"));
	}
	
	@Test
	public void testwrapperPrimitiveIntInvalid(){
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(int.class, "teste"));
	}

	@Test
	public void testwrapperPrimitiveLong() {
		long x = 13422;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(long.class, "13422"));
	}
	
	@Test
	public void testwrapperPrimitiveLongInvalid(){
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(long.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveFloat() {
		float x = 13.1f;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(float.class, "13.1"));
	}
	
	@Test
	public void testwrapperPrimitiveFloatInvalid(){
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(float.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveDouble() {
		double x = 13.2;
		Assert.assertEquals(x,transformationService.wrapperPrimitive(double.class, "13.2"));
	}
	
	@Test
	public void testwrapperPrimitiveDoubleInvalid(){
		thrown.expect(NumberFormatException.class);
		Assert.assertEquals(false, transformationService.wrapperPrimitive(double.class, "teste"));
	}
	
	@Test
	public void testwrapperPrimitiveInvalid(){
		Assert.assertEquals("teste", transformationService.wrapperPrimitive(Pessoa.class, "teste"));
	}
	
}
