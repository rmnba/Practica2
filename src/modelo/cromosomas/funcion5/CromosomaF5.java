package modelo.cromosomas.funcion5;

import modelo.cromosomas.CromosomaBoolean;
import modelo.genes.GenBoolean;

public class CromosomaF5 extends CromosomaBoolean 
{

	public CromosomaF5(double tol)
	{
		this.tol = tol;
		this.nVar = 0;
		this.genes = new GenBoolean[0];
		this.fenotipo = new double[0];
	}
	
	public CromosomaBoolean copia() 
	{
		CromosomaF5 ret = new CromosomaF5(this.lCrom);
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
		double a=0,b=0,ret;
		double x = fenotipo[0];
		double y = fenotipo[1];
		for(int i=1; i <= 5; ++i)
		{
			a += i * Math.cos((i+1) * x + i);
			b += i * Math.cos((i+1) * y + i);
		}
		ret = a*b;
		
		return ret;
	}


}
