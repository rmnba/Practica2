package modelo.genes.factoria;

import java.util.Random;

import modelo.genes.GenBoolean;
import modelo.genes.GenEntero;
import modelo.genes.GenReal;
import modelo.genes.factoria.imp.FactoriaGenesImp;

public abstract class FactoriaGenes 
{
	private static FactoriaGenes instancia;
	
	public static FactoriaGenes getInstancia()
	{
		if (instancia == null)
			instancia = new FactoriaGenesImp();
		return instancia;
	}
	
	public abstract GenBoolean creaGenBoolean(int tam, double xMax, double xMin, double tol, Random generator);
	
	public abstract GenReal creaGenReal(int tam, double xMax, double xMin, double tol, Random generator);
	
	public abstract GenEntero creaGenEntero(int tam, double xMax, double xMin, double tol, Random generator);
}
