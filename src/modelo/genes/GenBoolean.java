package modelo.genes;

import java.util.Random;

public class GenBoolean extends Gen
{
	
	private boolean[] alelo;
	
	public GenBoolean(int tam, double xMax, double xMin, double tol, Random generator)
	{
		this.generator = generator;
		this.alelo = new boolean[tam];
		this.tam = tam;
		this.xMax = xMax;
		this.xMin = xMin;
		this.tol = tol;
		do
		{
			for(int i=0; i < tam; ++i)
				alelo[i] = (this.generator.nextDouble() >= 0.5);
			
		} while(!esValido());
	}
	
	@Override
	public Gen copia()
	{
		GenBoolean copia = new GenBoolean(tam, xMax, xMin, tol, generator);
		for(int i=0; i < this.tam; ++i)
			copia.alelo[i] = this.alelo[i];
		
		return copia;
	}
	
	@Override
	public boolean esValido()
	{		
		double d = fenotipo();
		return (d >= xMin && d <= xMax);
	}
	
	@Override
	public double fenotipo()
	{
		double d = 0;
		int pot = 1;
		for(int i=0; i < this.tam; ++i)
		{
			d = d + pot * (this.alelo[i] ? 1 : 0);
			pot *= 2;
		}
		
		d *= this.tol;
		d += this.xMin;
		
		return d;
	}
	
	@Override
	public String toString()
	{
		double d = fenotipo();
		return Double.toString(d);
	}

	@Override
	public void mutar(double probMutacion) 
	{
		double prob;
		
		for(int k=0; k < this.tam; ++k)
		{
			// Para todos los bits de ese gen
			prob = generator.nextDouble();
			if(prob < probMutacion)
			{
				this.alelo[k] = !this.alelo[k];
				if(!this.esValido())
					this.alelo[k] = !this.alelo[k];			
			}
		}
	}

	@Override
	public void setTam(int tam) 
	{
		this.tam = tam;
	}

	public boolean[] getAlelo() 
	{
		return this.alelo;
	} 
}
