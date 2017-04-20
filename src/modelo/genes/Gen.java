package modelo.genes;

import java.util.Random;

public abstract class Gen
{
	protected int tam;
	protected double xMin;
	protected double xMax;
	protected double tol;
	protected Random generator;
	
	public int getTam() 
	{
		return tam;
	}

	public abstract void setTam(int tam);
	
	public double getxMin() 
	{
		return xMin;
	}

	public void setxMin(double xMin) 
	{
		this.xMin = xMin;
	}

	public double getxMax() 
	{
		return xMax;
	}

	public void setxMax(double xMax) 
	{
		this.xMax = xMax;
	}

	public double getTol() 
	{
		return tol;
	}

	public void setTol(double tol) 
	{
		this.tol = tol;
	}

	public Random getGenerator() 
	{
		return generator;
	}

	public void setGenerator(Random generator) 
	{
		this.generator = generator;
	}
	
	public abstract Gen copia();
	
	public abstract boolean esValido();
	
	public abstract double fenotipo();
	
	public abstract void mutar(double probMutacion);

}
