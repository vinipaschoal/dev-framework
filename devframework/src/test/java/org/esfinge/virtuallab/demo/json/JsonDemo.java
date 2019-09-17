package org.esfinge.virtuallab.demo.json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.esfinge.virtuallab.api.annotations.CustomReturn;
import org.esfinge.virtuallab.api.annotations.ServiceClass;
import org.esfinge.virtuallab.api.annotations.ServiceMethod;
import org.esfinge.virtuallab.utils.Utils;


/*--------------------------------------------------------------------------
 * Demonstracao da anotacao @CustomReturn.
 *-------------------------------------------------------------------------*/
@ServiceClass(
		label = "JSON",
		description = "Demonstração da anotação @CustomReturn.")
public class JsonDemo
{
	private static final Map<Integer,List<Disciplina>> disciplinas_2019 = new HashMap<>();
	static {
		// disciplinas do 1 periodo:
		disciplinas_2019.put(1, Arrays.asList(
				new Disciplina("CAP-239-4", "Matemática Computacional I", "Reinaldo Roberto Rosa", 1, 4),
				new Disciplina("CAP-241-4", "Computação Aplicada I", "Gilberto Queiroz", 1, 4),
				new Disciplina("CAP-242-1", "Metodologia Científica em Computação Aplicada", "Pedro Ribeiro", 1, 1),
				new Disciplina("CAP-354-3", "Inteligência Artificial", "Lamartine Nogueira", 1, 3),
				new Disciplina("CAP-368-3", "Introdução à Teoria de Sistemas Dinâmicos", "Elbert Einstein", 1, 3),
				new Disciplina("CAP-390-1", "Fundamentos de Programação Estruturada", "Lúbia Vinhas", 1, 1),
				new Disciplina("CAP-392-3", "Padrões de Projeto", "Eduardo Guerra", 1, 3),
				new Disciplina("CAP-397-3", "Tópicos Especiais em Computação Aplicada I", "---", 1, 3),
				new Disciplina("CAP-406-3", "Análise de Wavelet II", "Margarete Oliveira Domingues", 1, 3)));
		
		// disciplinas do 2 periodo:
		disciplinas_2019.put(2, Arrays.asList(
				new Disciplina("CAP-235-4", "Matemática Computacional II", "---", 2, 4),
				new Disciplina("CAP-237-3", "Sistemas Caóticos", "Elbert Einstein", 2, 3),
				new Disciplina("CAP-240-4", "Processamento Digital de Imagens", "Leila Maria", 2, 4),
				new Disciplina("CAP-331-3", "Física Matemática", "Reinaldo Roberto Rosa", 2, 3),
				new Disciplina("CAP-349-3", "Banco de Dados Geográficos", "Gilberto Queiroz", 2, 3),
				new Disciplina("CAP-370-3", "Computação Aplicada à Física Ambiental", "Reinaldo Roberto Rosa", 2, 3),
				new Disciplina("CAP-372-3", "Processamento de Alto Desempenho", "Celso Luiz Mendes", 2, 3),
				new Disciplina("CAP-378-1", "Tópicos em Observação da Terra", "Antônio Miguel", 2, 1),
				new Disciplina("CAP-379-1", "Tópicos em Ciências Espaciais", "Reinaldo Roberto Rosa", 2, 1),
				new Disciplina("CAP-383-3", "Matemática Computacional III", "Haroldo Fraga", 2, 3),
				new Disciplina("CAP-385-3", "Desenvolvimento de Frameworks", "Eduardo Guerra", 2, 3),
				new Disciplina("CAP-387-3", "Tópicos Especiais em Computação Aplicada II", "---", 2, 3),
				new Disciplina("CAP-391-3", "Verificação Formal de Software", "Valdivino Alexandre", 2, 3),
				new Disciplina("CAP-394-3", "Introdução à Data Science", "Rafael Santos", 2, 3),
				new Disciplina("CAP-398-3", "Modelagem e Aplicações de Sistemas Reativos Complexos", "Nandamudi Lankalapalli", 2, 3),
				new Disciplina("CAP-408-3", "Computação Aplicada às Ciências Atmosféricas", "Alan Calheiros", 2, 3),
				new Disciplina("CAP-465-4", "Modelagem e Simulação de Sistemas Terrestres", "Pedro Ribeiro", 2, 4)));
		
		// disciplinas do 3 periodo:
		disciplinas_2019.put(3, Arrays.asList(
				new Disciplina("CAP-328-3", "Teoria do Controle Inteligente", "Lamartine Nogueira", 3, 3),
				new Disciplina("CAP-335-3", "Aprendizado Computacional e Reconhecimento de Padrões", "Luciano Dutra", 3, 3),
				new Disciplina("CAP-340-3", "Problemas Inversos", "Haroldo Fraga", 3, 3),
				new Disciplina("CAP-351-3", "Neurocomputação", "Marcos Gonçalves Quiles", 3, 3),
				new Disciplina("CAP-359-3", "Princípios e Aplicações de Mineração de Dados", "Rafael Santos", 3, 3),
				new Disciplina("CAP-373-3", "Processamento e Análise de Imagens de Radar", "Sidnei João Siqueira", 3, 3),
				new Disciplina("CAP-375-3", "Inteligência Computacional e Aplicações", "Lamartine Nogueira", 3, 3),
				new Disciplina("CAP-381-3", "Redes Complexas, Dinâmica e Aplicações", "Elbert Einstein", 3, 3),
				new Disciplina("CAP-382-1", "Tópicos em Tecnologias Espaciais", "Elbert Einstein", 3, 1),
				new Disciplina("CAP-384-3", "Análise Wavelet I", "Margarete Oliveira Domingues", 3, 3),
				new Disciplina("CAP-388-3", "Tópicos Especiais em Computação Aplicada III", "---", 3, 3),
				new Disciplina("CAP-389-3", "Projeto Ágil de Software", "Eduardo Guerra", 3, 3),
				new Disciplina("CAP-393-3", "Model Checking Probabilístico", "Valdivino Alexandre", 3, 3),
				new Disciplina("CAP-395-3", "Geoinformática", "Gilberto Queiroz", 3, 3),
				new Disciplina("CAP-399-3", "Programação de Sistemas Massivamente Paralelos", "Celso Luiz Mendes", 3, 3),
				new Disciplina("CAP-400-3", "Visualização e Análise Computacional de Séries Temporais", "Reinaldo Roberto Rosa", 3, 3),
				new Disciplina("CAP-460-3", "Ótica Computacional", "Haroldo Fraga", 3, 3)));
	}

	
	/*--------------------------------------------------------------------------
	 * Sem anotacao - JSON: valor simples.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Obter nome da disciplina", 
		description = "Demonstra o retorno JSON de um valor simples.")
	public String getNomeDisciplina(String codigo)
	{
		Disciplina d = this.getDisciplina(codigo);
		
		if ( d != null )
			return d.getNome();
		
		return String.format("Disciplina não encontrada: %s", codigo);
	}
	
	
	/*--------------------------------------------------------------------------
	 * Sem anotacao - JSON: objetos.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Obter disciplina atual", 
		description = "Demonstra o retorno JSON de um objeto.")
	public Disciplina getDisciplinaAtual()
	{
		return this.getDisciplina("CAP-385-3");
	}

	
	/*--------------------------------------------------------------------------
	 * Sem anotacao - JSON: array.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Listar disciplinas por período", 
		description = "Demonstra o retorno JSON de um array.")
	public List<Disciplina> getDisciplinasPorPeriodo(int periodo)
	{
		return disciplinas_2019.get(periodo);
	}

	
	/*--------------------------------------------------------------------------
	 * Demonstra a anotacao @CustomReturn.
	 *-------------------------------------------------------------------------*/
	@ServiceMethod(
		label = "Listar todas disciplinas", 
		description = "@CustomReturn especificando os campos a serem retornados.")
	@CustomReturn(
		fields = {"codigo", "nome"},
		labels = {"Código", "Título"})
	public Set<Disciplina> getDisciplinas()
	{
		Set<Disciplina> disciplinas = new TreeSet<>();
		disciplinas_2019.values().forEach(ld -> disciplinas.addAll(ld));
		
		return disciplinas;
	}
	

	/**
	 * Metodo interno utilitario para recuperar disciplina por codigo. 
	 */
	private Disciplina getDisciplina(String codigo)
	{
		for ( List<Disciplina> disciplinas : disciplinas_2019.values() )
		{
			Disciplina disciplina = Utils.getFromCollection(disciplinas, d -> d.getCodigo().equalsIgnoreCase(codigo));
			if ( disciplina != null )
				return disciplina;
		}
		
		return null;
	}
}
