package modelo.cromosomas;

import modelo.genes.Gen;
import modelo.genes.factoria.FactoriaGenes;

public abstract class CromosomaEntero extends Cromosoma
{

	@Override
	public void resuelveFenotipo() 
	{
		fenotipo = new double[nVar];
		
		for(int i=0; i < nVar; ++i)
			fenotipo[i] = this.genes[i].fenotipo();
	}
	
	public abstract Cromosoma copia();
	
	public abstract double evalua();
	
	public abstract void aniadeGen(double xMax, double xMin);
	
	@Override
	public String toString()
	{
		String s = "";
		s += "Fen: ";
		for(Double f : fenotipo)
			s += f + ", ";
		
		return s;
	}

}
