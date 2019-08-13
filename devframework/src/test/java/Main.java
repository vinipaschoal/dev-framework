import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.api.annotations.CustomReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.api.annotations.TableReturn;
import org.esfinge.virtuallab.domain.Matematica;
import org.esfinge.virtuallab.domain.Ponto;
import org.esfinge.virtuallab.domain.Tarefa;
import org.esfinge.virtuallab.domain.TarefaService;
import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

public class Main
{
	public static void main(String... args) throws Exception
	{
		TestUtils.createJar("tarefa.jar", TarefaService.class, Tarefa.class);
		TestUtils.createJar("matematica.jar", Matematica.class, Ponto.class);
	}	
	
	/**
	 * Retorna uma String com as informacoes de bytecode de uma classe.
	 * 
	 * @param classBytecode os bytecodes da classe 
	 * @return as informacoes de bytecode da classe informada
	 */
	public static String inspectToString(byte[] classBytecode)
	{
		ClassReader classReader = new ClassReader(classBytecode);
		StringWriter buffer = new StringWriter();
		TraceClassVisitor tracer = new TraceClassVisitor(new PrintWriter(buffer));
		classReader.accept(tracer, 0);
		
		return ( buffer.toString() );
	}
	
	@ServiceClass
	public static class Service
	{
		@ServiceMethod
		@TableReturn(fields={"endereco", "idade", "nome"})
		public List<Pessoa> getPessoas()
		{
			return new ArrayList<Pessoa>();
		}
		
		@ServiceMethod
		@TableReturn
		@CustomReturn
		public List<String> doThing()
		{
			return new ArrayList<>();
		}
		
//		@ServiceMethod
		protected int getThing()
		{
			return 1;
		}
	}
	
	public static class Pessoa
	{
		private String nome;
		private int idade;
		private Date nascimento;
		private Endereco endereco;
		public String getNome()
		{
			return nome;
		}
		public void setNome(String nome)
		{
			this.nome = nome;
		}
		public int getIdade()
		{
			return idade;
		}
		public void setIdade(int idade)
		{
			this.idade = idade;
		}
		public Endereco getEndereco()
		{
			return endereco;
		}
		public void setEndereco(Endereco endereco)
		{
			this.endereco = endereco;
		}
		
		public void setNascimento(Date data)
		{
			this.nascimento = data;
		}
		
		public Date getNascimento()
		{
			return nascimento;
		}
	}
	
	public static class Endereco
	{
		private String cep;
		private String rua;
		private int numero;
		private Regiao regiao;		
		public String getCep()
		{
			return cep;
		}
		public void setCep(String cep)
		{
			this.cep = cep;
		}
		public String getRua()
		{
			return rua;
		}
		public void setRua(String rua)
		{
			this.rua = rua;
		}
		public int getNumero()
		{
			return numero;
		}
		public void setNumero(int numero)
		{
			this.numero = numero;
		}
		public Regiao getRegiao()
		{
			return regiao;
		}		
		public void setRegiao(Regiao regiao)
		{
			this.regiao  = regiao;
		}
	}
	
	public static class Regiao
	{
		private String cidade;
		protected String estado;
		
		public String getCidade()
		{
			return cidade;
		}
		public void setCidade(String cidade)
		{
			this.cidade = cidade;
		}
		/*
		public String getEstado()
		{
			return estado;
		}
		public void setEstado(String estado)
		{
			this.estado = estado;
		}
		*/
	}
	
	public static class RegiaoExtension extends Regiao
	{
		private boolean rural;

		/*
		public boolean isRural()
		{
			return rural;
		}

		public void setRural(boolean rural)
		{
			this.rural = rural;
		}
		*/
	}
}
