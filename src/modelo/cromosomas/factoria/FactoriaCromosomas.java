package modelo.cromosomas.factoria;

import modelo.cromosomas.factoria.imp.FactoriaCromosomasImp;
import modelo.cromosomas.funcion1.CromosomaF1;
import modelo.cromosomas.funcion2.CromosomaF2;
import modelo.cromosomas.funcion3.CromosomaF3;
import modelo.cromosomas.funcion4.CromosomaF4;
import modelo.cromosomas.funcion4real.CromosomaF4real;
import modelo.cromosomas.funcion5.CromosomaF5;
import modelo.cromosomas.hospital.CromosomaHospitales;

public abstract class FactoriaCromosomas 
{

	protected double[] xMax;
	protected double[] xMin;
	protected double tol;
	protected int nVar;
	
	protected long seed;

	private static FactoriaCromosomas instancia;
	
	public static FactoriaCromosomas getInstancia()
	{
		if (instancia == null)
			instancia = new FactoriaCromosomasImp();
		return instancia;
	}
	
	public abstract CromosomaF1 creaCromosomaF1();
	
	public abstract CromosomaF2 creaCromosomaF2();
	
	public abstract CromosomaF3 creaCromosomaF3();
	
	public abstract CromosomaF4 creaCromosomaF4();
	
	public abstract CromosomaF4real creaCromosomaF4real();
	
	public abstract CromosomaF5 creaCromosomaF5();
	
	public abstract CromosomaHospitales creaCromosomaHospitales();
	
	public abstract void setxMax(double[] xMax);
	
	public abstract void setxMin(double[] xMin);
	
	public abstract void setTol(double tol);

	public abstract void setnVar(int nVar);
	
	public abstract void setSeed(long seed);
}
