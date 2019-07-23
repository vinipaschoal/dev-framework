package org.esfinge.virtuallab.junit4;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Permite a repeticao de testes no JUnit4.
 * Baseado em: https://github.com/cbismuth/junit-repeat-rule
 */
public class RepeatTestRule implements TestRule
{
	@Override
	public Statement apply(Statement statement, Description description)
	{
		Statement result = statement;
		RepeatTest repeat = description.getAnnotation(RepeatTest.class);
		if(repeat != null)
		{
			int times = repeat.times();
			boolean verbose = repeat.verbose();
			result = new RepeatTestStatement(times, verbose, statement);
		}
		return result;
	}
	
	private static class RepeatTestStatement extends Statement
	{
		private final int times;
		private final boolean verbose;
		private final Statement statement;
	 
		private RepeatTestStatement(int times, boolean verbose, Statement statement)
		{
			this.times = times;
			this.verbose = verbose;
			this.statement = statement;
		}
	 
		@Override
		public void evaluate() throws Throwable
		{
			for(int i = 0; i < times; i++)
			{
				if (verbose)
					System.out.printf("Running %d of %d..\n", i+1, times);
				
				statement.evaluate();
			}	
			if (verbose)
				System.out.println("Done!");
		}
	}
}
