package modelo.genes;

import java.util.Random;

public class GenEntero extends Gen
{
	
	private int alelo;
	
	public GenEntero(int tam, double xMax, double xMin, double tol, Random generator)
	{
		this.generator = generator;
		this.tam = tam;
		this.xMax = xMax;
		this.xMin = xMin;
		this.tol = tol;
		do
		{
			alelo = (int)(xMin + (generator.nextDouble()*xMax));
		} while(!esValido());
	}

	@Override
	public Gen copia() 
	{
		GenEntero copia = new GenEntero(tam, xMax, xMin, tol, generator);
		copia.alelo = this.alelo;
		return copia;
	}

	@Override
	public boolean esValido() 
	{
		return (xMin <= alelo && xMax >= alelo);
	}

	@Override
	public double fenotipo() 
	{
		return alelo;
	}

	@Override
	public void mutar(double probMutacion) 
	{
		double prob;
		prob = generator.nextDouble();
		if(prob < probMutacion){
			this.alelo = (int)(xMin + (generator.nextInt()*xMax));
		}
	}

	@Override
	public void setAlelo(Object alelo) 
	{
		this.alelo = (int) alelo;
	}	

	@Override
	public void setTam(int tam) 
	{
		this.tam = tam;
	}

	@Override
	public Object getAlelo() 
	{
		return this.alelo;
	}

}
