package modelo.cruces;

import java.util.Random;

import modelo.cromosomas.Cromosoma;

public class CruceMonopunto extends Cruce
{
	private Random generador;
	
	public CruceMonopunto(Random generador)
	{
		this.generador = generador;
	}

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		int puntoCorte = (int)generador.nextDouble()*(padre.getnVar() -1);
		
		for(int i=0; i < puntoCorte; ++i)
		{
			hijo1.getGenes()[i] = padre.getGenes()[i];
			hijo2.getGenes()[i] = madre.getGenes()[i];
		}
		
		for(int i=puntoCorte; i < padre.getnVar(); ++i)
		{
			hijo1.getGenes()[i] = madre.getGenes()[i];
			hijo2.getGenes()[i] = padre.getGenes()[i];
		}
	}

}
