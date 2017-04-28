package modelo.cruces;

import modelo.cromosomas.Cromosoma;
import modelo.genes.GenReal;

public class CruceAritmetico extends Cruce
{

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		GenReal auxH, auxM, auxP;
		
		for(int i=0; i < padre.getnVar(); ++i)
		{
			auxH = (GenReal) hijo1.getGenes()[i].copia();
			auxP = (GenReal) padre.getGenes()[i];
			auxM = (GenReal) madre.getGenes()[i];
			auxH.setAlelo((auxP.fenotipo() + auxM.fenotipo()) /2);
			hijo1.getGenes()[i] = auxH;
		}
	}

}
