package modelo.cromosomas.funcion2;

import modelo.cromosomas.CromosomaBoolean;
import modelo.genes.GenBoolean;

public class CromosomaF2 extends CromosomaBoolean 
{

	public CromosomaF2(double tol)
	{
		this.tol = tol;
		this.nVar = 0;
		this.genes = new GenBoolean[0];
		this.fenotipo = new double[0];
	}
	
	public CromosomaBoolean copia() 
	{
		CromosomaF2 ret = new CromosomaF2(this.lCrom);
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
		double x1 = fenotipo[0];
		double x2 = fenotipo[1];
		ret = (-(x2 + 47)*Math.sin(Math.sqrt(Math.abs(x2+(x1/2)+47)))-(x1*Math.sin(Math.sqrt(Math.abs(x1-(x2+47))))));
		return ret;
	}


}
