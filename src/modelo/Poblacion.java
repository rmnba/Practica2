package modelo;

import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.funcion1.CromosomaF1;
import modelo.cromosomas.funcion2.CromosomaF2;
import modelo.cromosomas.funcion3.CromosomaF3;
import modelo.cromosomas.funcion4.CromosomaF4;
import modelo.cromosomas.funcion4real.CromosomaF4real;
import modelo.cromosomas.funcion5.CromosomaF5;

public class Poblacion 
{
	private Cromosoma[] individuos;
	private int tam;
	private long seed;

	public Poblacion(int n, Cromosoma cromosoma)
	{
		tam = n;
		if (cromosoma instanceof CromosomaF1)
			this.individuos = new CromosomaF1[tam];
		else if (cromosoma instanceof CromosomaF2)
			this.individuos = new CromosomaF2[tam];
		else if (cromosoma instanceof CromosomaF3)
			this.individuos = new CromosomaF3[tam];
		else if (cromosoma instanceof CromosomaF4)
			this.individuos = new CromosomaF4[tam];
		else if (cromosoma instanceof CromosomaF4real)
			this.individuos = new CromosomaF4real[tam];
		else
			this.individuos = new CromosomaF5[tam];
	}

	public void inicializa(Cromosoma cromosoma)
	{
		for(int i=0; i < tam; ++i)
		{
			individuos[i] = cromosoma;
			individuos[i].setSeed(this.seed);
		}
		
	}
	
	public int getTam() 
	{
		return tam;
	}

	public void setIndividuos(Cromosoma[] individuos) 
	{
		this.individuos = individuos;
	}

	public Cromosoma[] getIndividuos() 
	{
		return individuos;
	}
	
	public void setSeed(long seed) 
	{
		this.seed = seed;
	}

}
