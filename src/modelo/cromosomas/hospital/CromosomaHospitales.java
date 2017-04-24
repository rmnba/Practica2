package modelo.cromosomas.hospital;

import modelo.Datos;
import modelo.Parseador;
import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.CromosomaEntero;
import modelo.genes.GenEntero;

public class CromosomaHospitales extends CromosomaEntero
{
	
	private Datos datos = Parseador.parsear("Archivosdatos/datos30.dat");;
	
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
		/*
		suma += distancia[27][(int) this.genes[0].fenotipo()];
		for(int i=0; i < (this.nVar-1); ++i){
			int a = (int) this.genes[i].fenotipo();
			int b = (int) this.genes[i+1].fenotipo();
			suma += distancia[a][b];
		}
		suma += distancia[(int) this.genes[nVar-1].fenotipo()][27]; 
		*/
		//int sumas[] = new int[n];
		int a = distancia[n - 1][(int) this.genes[0].fenotipo()];
		int b = flujo[n - 1][(int) this.genes[0].fenotipo()];
		suma = suma + (a*b);
		for (int i = 0; i < n - 1; ++i)
		{
			a = (int) this.genes[i].fenotipo();
			b = (int) this.genes[i].fenotipo();
			suma += (a*b);
			
			a = (int) this.genes[i + 1].fenotipo();
			b = (int) this.genes[i + 1].fenotipo();
			suma += (a*b);
		}
		//int num = (int) this.generator.nextDouble() * n + 0;
		a = distancia[nVar-1][(int) this.genes[n - 1].fenotipo()];
		b = flujo[nVar-1][(int) this.genes[n - 1].fenotipo()];
		suma += (a*b);
		
		return suma;
	}

}
