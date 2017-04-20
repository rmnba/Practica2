package modelo.cromosomas;

import modelo.genes.Gen;
import modelo.genes.factoria.FactoriaGenes;

public abstract class CromosomaBoolean extends Cromosoma
{
	
	public void resuelveFenotipo() 
	{
		fenotipo = new double[nVar];
		for(int i=0; i < nVar; ++i)
			fenotipo[i] = this.genes[i].fenotipo();
		
	}
	
	public abstract Cromosoma copia();
	
	public abstract double evalua();
	
	public void aniadeGen(double xMax, double xMin)
	{
		int lGen = (int) Math.round((Math.log10(1+((xMax-xMin)/tol))/Math.log10(2)) + 0.5);
		/*
		 * Para hacer el log base 2, usamos el cambio de base.
		 * Para hacer el redondeo superior, sumamos 0.5 y usamos el redondeo normal.
		 */
		lCrom += lGen;	
		Gen nuevo = FactoriaGenes.getInstancia().creaGenBoolean(lGen, xMax, xMin, tol, generator);
		
		Gen[] nuevosGenes = new Gen[nVar + 1];
		for(int i=0; i< nVar; ++i){
			nuevosGenes[i] = genes[i];
		}
		
		nuevosGenes[nVar] = nuevo;
		this.genes = nuevosGenes;
		this.nVar++;
		resuelveFenotipo();
	}
	
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
