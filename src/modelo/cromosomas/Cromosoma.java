package modelo.cromosomas;

import java.util.Random;

import modelo.genes.Gen;

public abstract class Cromosoma 
{

	protected Gen[] genes;
	protected double puntAcum;
	protected double puntuacion;
	protected double[] fenotipo;
	protected int nVar;			// numero de Genes
	protected double tol;
	protected double aptitud;
	protected int lCrom;		// numero total de bits que ocupa el cromosoma
	protected long seed;
	protected Random generator = new Random();
	
	public abstract void resuelveFenotipo();
	
	public abstract Cromosoma copia();
	
	public boolean esValido()
	{
		boolean valido = true;
		for(Gen g : genes)
			valido &= g.esValido();
		
		return valido;
	}
	
	public abstract double evalua();
	
	public abstract void aniadeGen(double xMax, double xMin);

	public Gen[] getGenes() 
	{
		return genes;
	}

	public void setGenes(Gen[] genes) 
	{
		this.genes = genes;
	}

	public double getPuntAcum() 
	{
		return puntAcum;
	}

	public void setPuntAcum(double puntAcum) 
	{
		this.puntAcum = puntAcum;
	}

	public double getPuntuacion() 
	{
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) 
	{
		this.puntuacion = puntuacion;
	}

	public double[] getFenotipo() 
	{
		return fenotipo;
	}

	public void setFenotipo(double[] fenotipo) 
	{
		this.fenotipo = fenotipo;
	}

	public int getnVar() 
	{
		return nVar;
	}

	public void setnVar(int nVar) 
	{
		this.nVar = nVar;
	}

	public double getTol() 
	{
		return tol;
	}

	public void setTol(double tol) 
	{
		this.tol = tol;
	}

	public double getAptitud() 
	{
		return aptitud;
	}

	public void setAptitud(double aptitud) 
	{
		this.aptitud = aptitud;
	}

	public int getlCrom() 
	{
		return lCrom;
	}

	public void setlCrom(int lCrom) 
	{
		this.lCrom = lCrom;
	}
	
	public void setSeed(long seed) 
	{
		this.seed = seed;
		generator = new Random(seed);
	}
	
	public long getSeed() 
	{
		return this.seed;
	}
	
}
