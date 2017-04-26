package modelo.cromosomas.hospital;

import modelo.Datos;
import modelo.Parseador;
import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.CromosomaEntero;
import modelo.genes.Gen;
import modelo.genes.GenEntero;
import modelo.genes.factoria.FactoriaGenes;

public class CromosomaHospitales extends CromosomaEntero
{
	
	private Datos datos = Parseador.parsear("Archivosdatos/datos30.dat");
	
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
		
		int distancia[][] = datos.getDistancia();
		int flujo[][] = datos.getFlujo();
		int n = this.nVar;
		int a,b;
		//int a = distancia[n - 1][(int) this.genes[0].fenotipo()];
		//int b = flujo[n - 1][(int) this.genes[0].fenotipo()];
		//suma = suma + (a*b);
		for (int j = 0; j < n; ++j)
			for (int i = 0; i < n; ++i)
			{

				a = (int) this.genes[i].fenotipo();
				b = (int) this.genes[j].fenotipo();
				int c = distancia[i][j];
				int d = flujo[a][b];
				suma += (c*d);
				
				
				
				
			}

		//a = distancia[nVar-1][(int) this.genes[n - 1].fenotipo()];
		//b = flujo[nVar-1][(int) this.genes[n - 1].fenotipo()];
		//suma += (a*b);
		
		return suma;
	}
	
	@Override
	public void aniadeGen(double xMax, double xMin) 
	{
		Gen nuevo = FactoriaGenes.getInstancia().creaGenEntero(1, xMax, xMin, tol, generator);
		while(this.contiene((GenEntero) nuevo))
		{
			nuevo.setAlelo((int) nuevo.getAlelo()+1);
			if((int) nuevo.getAlelo() > xMax)
				nuevo.setAlelo((int)xMin);
		}
		Gen[] nuevosGenes = new Gen[nVar + 1];
		for(int i=0; i< nVar; ++i)
			nuevosGenes[i] = genes[i];
		
		nuevosGenes[nVar] = nuevo;
		this.genes = nuevosGenes;
		this.nVar++;
		resuelveFenotipo();
	}
	
	private boolean contiene(GenEntero hospital)
	{
		boolean encontrado = false;
		int i=0;
		if((int) hospital.getAlelo() == nVar)
			return true;
		
		while(!encontrado && i < nVar)
			if(this.genes[i].fenotipo() == hospital.fenotipo())
				encontrado = true;
			
			else
				++i;
			
		
		return encontrado;
	}

}
