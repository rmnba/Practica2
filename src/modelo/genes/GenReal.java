package modelo.genes;

import java.util.Random;

public class GenReal extends Gen 
{
	
	private double alelo;
	
	public GenReal(int tam, double xMax, double xMin, double tol, Random generator)
	{
		this.generator = generator;
		this.tam = tam;
		this.xMax = xMax;
		this.xMin = xMin;
		this.tol = tol;
		do
		{
			alelo = xMin + (generator.nextDouble()*xMax);
		} while(!esValido());
	}

	@Override
	public Gen copia() 
	{
		GenReal copia = new GenReal(tam, xMax, xMin, tol, generator);
		copia.alelo = this.alelo;
		return copia;
	}

	@Override
	public boolean esValido()
	{
		return (alelo >= xMin && alelo <= xMax);
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
		if(prob < probMutacion)
			this.alelo = xMin + (generator.nextDouble()*xMax);
		
	}

	public void setAlelo(double alelo) 
	{
		this.alelo = alelo;
	}

	@Override
	public void setTam(int tam) 
	{
		this.tam = tam;
	}

}
