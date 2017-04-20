package modelo.cromosomas.factoria.imp;

import modelo.cromosomas.factoria.FactoriaCromosomas;
import modelo.cromosomas.funcion1.CromosomaF1;
import modelo.cromosomas.funcion2.CromosomaF2;
import modelo.cromosomas.funcion3.CromosomaF3;
import modelo.cromosomas.funcion4.CromosomaF4;
import modelo.cromosomas.funcion4real.CromosomaF4real;
import modelo.cromosomas.funcion5.CromosomaF5;

public class FactoriaCromosomasImp extends FactoriaCromosomas
{
	
	@Override
	public CromosomaF1 creaCromosomaF1() 
	{
		CromosomaF1 crom = new CromosomaF1(this.tol);
		crom.setSeed(this.seed);
		for(int i = 0; i < nVar; ++i)
			crom.aniadeGen(xMax[i], xMin[i]);
		
		return crom;
	}

	@Override
	public CromosomaF2 creaCromosomaF2() 
	{
		CromosomaF2 crom = new CromosomaF2(this.tol);
		crom.setSeed(this.seed);
		for(int i = 0; i < nVar; ++i)
			crom.aniadeGen(xMax[i], xMin[i]);
		
		return crom;
	}

	@Override
	public CromosomaF3 creaCromosomaF3() 
	{
		CromosomaF3 crom = new CromosomaF3(this.tol);
		crom.setSeed(this.seed);
		for(int i = 0; i < nVar; ++i)
			crom.aniadeGen(xMax[i], xMin[i]);
		
		return crom;
	}

	@Override
	public CromosomaF4 creaCromosomaF4() 
	{
		CromosomaF4 crom = new CromosomaF4(this.tol);
		crom.setSeed(this.seed);
		for(int i = 0; i < nVar; ++i)
			crom.aniadeGen(xMax[i], xMin[i]);
		
		return crom;
	}

	@Override
	public CromosomaF4real creaCromosomaF4real() 
	{
		CromosomaF4real crom = new CromosomaF4real(this.tol);
		crom.setSeed(this.seed);
		for(int i = 0; i < nVar; ++i)
			crom.aniadeGen(xMax[i], xMin[i]);
		
		return crom;
	}

	@Override
	public CromosomaF5 creaCromosomaF5() 
	{
		CromosomaF5 crom = new CromosomaF5(this.tol);
		crom.setSeed(this.seed);
		for(int i = 0; i < nVar; ++i)
			crom.aniadeGen(xMax[i], xMin[i]);
		
		return crom;
	}

	@Override
	public void setxMax(double[] xMax) 
	{
		this.xMax = xMax;
	}

	@Override
	public void setxMin(double[] xMin) 
	{
		this.xMin = xMin;
	}

	@Override
	public void setTol(double tol) 
	{
		this.tol = tol;
	}

	@Override
	public void setnVar(int nVar) 
	{
		this.nVar = nVar;
	}
}
