package modelo.cruces;

import java.util.ArrayList;

import modelo.Corte;
import modelo.cromosomas.Cromosoma;
import modelo.genes.Gen;

public class CruceOXPosiciones extends Cruce
{

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		ArrayList<Integer> posiciones = Corte.creaCortes(4, padre.getnVar());
		ArrayList<Integer> posMadre = new ArrayList<Integer>(4);
		for(int i=0; i<padre.getnVar(); ++i)
		{
			hijo1.getGenes()[i] = padre.getGenes()[i].copia();
			hijo2.getGenes()[i] = madre.getGenes()[i].copia();
		}
		for(int i=0; i < 4; ++i)
			posMadre.add(busca(madre, 0, padre.getnVar(), padre.getGenes()[posiciones.get(i)]));
		
		for(int i=0; i < 4; ++i)
		{
			hijo1.getGenes()[posiciones.get(i)] = madre.getGenes()[posMadre.get(i)].copia();
			hijo2.getGenes()[posMadre.get(i)] = padre.getGenes()[posiciones.get(i)].copia();
		}
	}

	private int busca(Cromosoma c, int ini, int fin, Gen valor)
	{
		for(int i=ini; i <=fin; ++i)
			if(c.getGenes()[i].fenotipo() == valor.fenotipo())
				return i;
	
		return -1;
	}

}
