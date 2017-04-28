package modelo.cruces;

import java.util.Random;

import modelo.cromosomas.Cromosoma;
import modelo.genes.GenReal;

public class CruceSBX extends Cruce
{
	private Random generador;
	
	public CruceSBX(Random generador)
	{
		this.generador = generador;
	}

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		double beta;
		double alpha = generador.nextDouble();
		if(alpha < 0.5)
			beta = Math.pow(2 * alpha, (1/(padre.getnVar() + 1)));
		
		else
			beta = Math.pow(0.5 *(1-alpha), (1/(padre.getnVar() + 1)));
		
		double x;
		GenReal auxH1, auxH2, auxM, auxP;
		for(int i=0; i < padre.getnVar(); ++i)
		{
			auxH1 = (GenReal) hijo1.getGenes()[i].copia();
			auxH2 = (GenReal) hijo2.getGenes()[i].copia();
			auxP = (GenReal) padre.getGenes()[i];
			auxM = (GenReal) madre.getGenes()[i];
			x = (auxP.fenotipo() + auxM.fenotipo()) /2;
			auxH1.setAlelo(x - (0.5 * beta) * (Math.abs(auxP.fenotipo()-auxM.fenotipo())));
			auxH2.setAlelo(x + (0.5 * beta) * (Math.abs(auxP.fenotipo()-auxM.fenotipo())));
			hijo1.getGenes()[i] = auxH1;
			hijo2.getGenes()[i] = auxH2;
		}
	}

	
}
