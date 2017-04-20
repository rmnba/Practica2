package modelo.cromosomas.funcion4real;

import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.CromosomaReal;
import modelo.genes.Gen;

public class CromosomaF4real extends CromosomaReal 
{

	public CromosomaF4real(double tol)
	{
		this.tol = tol;
		this.nVar = 0;
		this.genes = new Gen[0];
		this.fenotipo = new double[0];
	}
	
	public Cromosoma copia() 
	{
		CromosomaF4real ret = new CromosomaF4real(this.lCrom);
		ret.aptitud = this.aptitud;
		ret.puntAcum = this.puntAcum;
		ret.puntuacion = this.puntuacion;
		ret.tol = this.tol;
		ret.lCrom = this.lCrom;
		ret.nVar = this.nVar;
		ret.fenotipo = new double[nVar];
		for(int i=0; i < this.nVar;++i)
			ret.fenotipo[i] = this.fenotipo[i];
		
		ret.genes = new Gen[nVar];
		for(int i=0; i < this.nVar;++i)
			ret.genes[i] = this.genes[i].copia();
		
		return ret;
	}
	
	@Override
	public double evalua() 
	{
		resuelveFenotipo();
		double ret = 0;
		double[] x = new double[this.nVar + 1];
		for(int i=0; i < nVar; ++i)
			x[i + 1] = fenotipo[i];
		
		for(int i=1; i <= nVar; ++i)
			ret += (Math.sin(x[i]) * Math.pow(Math.sin(((i + 1) * (x[i] * x[i])) / Math.PI),20));  
		
		return -ret;
	}

}
