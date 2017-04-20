package modelo.cromosomas.funcion4;

import modelo.cromosomas.CromosomaBoolean;
import modelo.genes.GenBoolean;

public class CromosomaF4 extends CromosomaBoolean 
{

	public CromosomaF4(double tol)
	{
		this.tol = tol;
		this.nVar = 0;
		this.genes = new GenBoolean[0];
		this.fenotipo = new double[0];
	}
	
	public CromosomaBoolean copia() 
	{
		CromosomaF4 ret = new CromosomaF4(this.lCrom);
		ret.aptitud = this.aptitud;
		ret.puntAcum = this.puntAcum;
		ret.puntuacion = this.puntuacion;
		ret.tol = this.tol;
		ret.lCrom = this.lCrom;
		ret.nVar = this.nVar;
		ret.fenotipo = new double[nVar];
		for(int i=0; i < this.nVar;++i)
			ret.fenotipo[i] = this.fenotipo[i];
		
		ret.genes = new GenBoolean[nVar];
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
