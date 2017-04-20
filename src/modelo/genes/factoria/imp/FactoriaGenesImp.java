package modelo.genes.factoria.imp;

import java.util.Random;

import modelo.genes.GenBoolean;
import modelo.genes.GenEntero;
import modelo.genes.GenReal;
import modelo.genes.factoria.FactoriaGenes;

public class FactoriaGenesImp extends FactoriaGenes
{

	@Override
	public GenBoolean creaGenBoolean(int tam, double xMax, double xMin, double tol, Random generator) 
	{
		return new GenBoolean(tam, xMax, xMin, tol, generator);
	}

	@Override
	public GenReal creaGenReal(int tam, double xMax, double xMin, double tol, Random generator) 
	{
		return new GenReal(tam, xMax, xMin, tol, generator);
	}

	@Override
	public GenEntero creaGenEntero(int tam, double xMax, double xMin, double tol, Random generator) 
	{
		return new GenEntero(tam, xMax, xMin, tol, generator);
	}

}
