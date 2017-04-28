package modelo.cruces;

import modelo.cromosomas.Cromosoma;

public class CruceCX extends Cruce
{

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		for (int i = 0; i < padre.getnVar(); i++) 
		{
			hijo1.getGenes()[i] = padre.getGenes()[0].copia();	// Simplemente para inicializar cada gen
			hijo2.getGenes()[i] = padre.getGenes()[0].copia();
			hijo1.getGenes()[i].setAlelo(-1);
			hijo2.getGenes()[i].setAlelo(-1);
		}
		
		hijo1.resuelveFenotipo();
		hijo2.resuelveFenotipo();
		int index = 0;
		
		while(hijo1.getGenes()[index].fenotipo() == -1)
		{
			hijo1.getGenes()[index] = padre.getGenes()[index].copia();
			index = (int) madre.getGenes()[index].fenotipo();
		}
		for (int i = 0; i < padre.getnVar(); i++) 
			if(hijo1.getGenes()[i].fenotipo() == -1)
				hijo1.getGenes()[i] = madre.getGenes()[i].copia();
			
		index = 0;
		while(hijo2.getGenes()[index].fenotipo() == -1)
		{
			hijo2.getGenes()[index] = madre.getGenes()[index].copia();
			index = (int) padre.getGenes()[index].fenotipo();
		}
		
		for (int i = 0; i < padre.getnVar(); i++) 
			if(hijo2.getGenes()[i].fenotipo() == -1)
				hijo2.getGenes()[i] = padre.getGenes()[i].copia();
			
		hijo1.resuelveFenotipo();
		hijo2.resuelveFenotipo();
	}
	
}
