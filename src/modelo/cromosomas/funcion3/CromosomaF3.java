package modelo.cromosomas.funcion3;

import modelo.cromosomas.CromosomaBoolean;
import modelo.genes.GenBoolean;

public class CromosomaF3 extends CromosomaBoolean 
{

	public CromosomaF3(double tol)
	{
		this.tol = tol;
		this.nVar = 0;
		this.genes = new GenBoolean[0];
		this.fenotipo = new double[0];
	}
	
	public CromosomaBoolean copia() 
	{
		CromosomaF3 ret = new CromosomaF3(this.lCrom);
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
		double ret;
		double x = fenotipo[0];
		double y= fenotipo[1];
		ret = 21.5 + x * Math.sin(4 * Math.PI * x) + y * Math.sin(20 * Math.PI * y);
		return ret;
	}


}
