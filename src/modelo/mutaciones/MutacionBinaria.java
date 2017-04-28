package modelo.mutaciones;

import java.util.Random;

import modelo.Poblacion;

public class MutacionBinaria extends Mutacion
{

	public MutacionBinaria(Poblacion pob, Random generador, double probMut) 
	{
		super(pob, generador, probMut);
	}

	@Override
	public void muta() 
	{
		for(int i=0; i < this.pob.getTam(); ++i)	
			// Para todos los individuos de la Pob
			for(int j=0; j < pob.getIndividuos()[i].getnVar(); ++j)
				// Para todos los genes de ese individuo (Cromosoma)
				pob.getIndividuos()[i].getGenes()[j].mutar(this.probMutacion);
		
	}

}
