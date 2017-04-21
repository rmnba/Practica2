package modelo.cromosomas.hospital;

import modelo.Datos;
import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.CromosomaEntero;
import modelo.genes.GenEntero;

public class CromosomaHospitales extends CromosomaEntero
{
	
	private Datos datos;
	
	public CromosomaHospitales(double tol)
	{
		this.tol = tol;
		this.nVar = 0;
		this.genes = new GenEntero[0];
		this.fenotipo = new double[0];
	}

	@Override
	public Cromosoma copia() 
	{
		CromosomaHospitales ret = new CromosomaHospitales(this.lCrom);
		ret.aptitud = this.aptitud;
		ret.puntAcum = this.puntAcum;
		ret.puntuacion = this.puntuacion;
		ret.tol = this.tol;
		ret.lCrom = this.lCrom;
		ret.nVar = this.nVar;
		ret.fenotipo = new double[nVar];
		for(int i=0; i < this.nVar;++i){
			ret.fenotipo[i] = this.fenotipo[i];
		}
		ret.genes = new GenEntero[nVar];
		for(int i=0; i < this.nVar;++i){
			ret.genes[i] = this.genes[i].copia();
		}
		return ret;
	}

	@Override
	public double evalua() 
	{
		resuelveFenotipo();
		double suma = 0;
		for (int i = 0; i < this.datos.getN(); ++i)
			for (int j = 0; j < this.datos.getN(); ++j)
				suma = suma + (this.datos.getDistanciaPunto(i, j) * this.datos.getFlujoPunto(i, j));
				
		return suma;
	}

}
