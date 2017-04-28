package modelo.cruces;

import java.util.ArrayList;

import modelo.Corte;
import modelo.cromosomas.Cromosoma;
import modelo.genes.Gen;

public class CruceOX extends Cruce
{

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		ArrayList<Integer> cortes = Corte.creaCortes(2, padre.getnVar());
		
		int indexH;
		
		for (int i = cortes.get(0); i <= cortes.get(1); i++) 
		{
			hijo1.getGenes()[i] = madre.getGenes()[i].copia();
			hijo2.getGenes()[i] = padre.getGenes()[i].copia();
		}
		// Para el hijo 1
		indexH = cortes.get(1)+1;
		for(int i=cortes.get(1)+1; i < padre.getnVar(); ++i)
			// Bucle desde 2o punto de corte hasta el final
			if(!contiene(hijo1, cortes.get(0), cortes.get(1), padre.getGenes()[i]))
			{
				hijo1.getGenes()[indexH] = padre.getGenes()[i].copia();
				indexH++;
			}
		
		if(indexH >= padre.getnVar()) 
			indexH = 0;
		for(int i=0; i <= cortes.get(1); ++i)
			// Bucle desde principio hasta 1er punto de corte
			if(!contiene(hijo1, cortes.get(0), cortes.get(1), padre.getGenes()[i]))
			{
				hijo1.getGenes()[indexH] = padre.getGenes()[i].copia();
				indexH++;
				if(indexH >= padre.getnVar()) indexH = 0;
			}
		
		// Para el hijo 2
		indexH = cortes.get(1)+1;
		for(int i=cortes.get(1)+1; i < padre.getnVar(); ++i)
			// Bucle desde 2o punto de corte hasta el final
			if(!contiene(hijo2, cortes.get(0), cortes.get(1), madre.getGenes()[i]))
			{
				hijo2.getGenes()[indexH] = madre.getGenes()[i].copia();
				indexH++;
			}
		
		if(indexH >= padre.getnVar()) indexH = 0;
		
		for(int i=0; i <= cortes.get(1); ++i)
			// Bucle desde principio hasta 1er punto de corte
			if(!contiene(hijo2, cortes.get(0), cortes.get(1), madre.getGenes()[i]))
			{
				hijo2.getGenes()[indexH] = madre.getGenes()[i].copia();
				indexH++;
				if(indexH >= padre.getnVar()) indexH = 0;
			}
		
	}

	private boolean contiene(Cromosoma c, int ini, int fin, Gen valor)
	{
		for(int i=ini; i <=fin; ++i)
			if(c.getGenes()[i].fenotipo() == valor.fenotipo())
				return true;
			
		return false;
	}

}
