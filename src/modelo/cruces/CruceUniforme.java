package modelo.cruces;

import java.util.Random;

import modelo.cromosomas.Cromosoma;

public class CruceUniforme extends Cruce
{
	private Random generador;
	
	public CruceUniforme(Random generador)
	{
		this.generador = generador;
	}

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		double prob;
		for(int i=0; i < padre.getnVar(); ++i)
		{
			prob = generador.nextDouble();
			if(prob < 0.5)
			{
				hijo1.getGenes()[i] = padre.getGenes()[i];
				hijo2.getGenes()[i] = madre.getGenes()[i];
			}
			else
			{
				hijo1.getGenes()[i] = madre.getGenes()[i];
				hijo2.getGenes()[i] = padre.getGenes()[i];
			}
		}
	}
}
