package controlador;

import modelo.AGS;
import modelo.Observador;
import modelo.Parseador;
import modelo.Cruce;
import modelo.Datos;
import modelo.Funcion;
import modelo.Mutacion;
import modelo.Select;
import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.factoria.FactoriaCromosomas;

public class Controlador 
{
	private Cromosoma cromosoma;
	private boolean maximizar; 
	private AGS alg;
	private long lastSeed;
	
	public Controlador ()
	{
	}
	
	public void addObserver(Observador o)
	{
		alg.addObserver(o);
	}
	
	public void setParametersRun(Funcion funcion, int n,  double tol, int pob, int generaciones, double pCruce, double pMutacion, long seed, Cruce cruce, Select seleccion, Mutacion mutacion, boolean elitismo)
	{
		if(seed == 0)
			lastSeed = System.currentTimeMillis();
		
		else
			lastSeed = seed;
		
		this.generaFuncion(funcion, n, tol);
		alg = new AGS(pob, this.cromosoma, generaciones, pCruce, pMutacion, seleccion, cruce, elitismo, maximizar, lastSeed, mutacion);
	}
	
	public void setParametersReRun(Funcion funcion, int n,  double tol, int pob, int generaciones, double pCruce, double pMutacion, long seed, Cruce cruce, Select seleccion, Mutacion mutacion, boolean elitismo)
	{
		if(seed != 0)
			lastSeed = seed;
		
		this.generaFuncion(funcion, n, tol);
		alg = new AGS(pob, this.cromosoma, generaciones, pCruce, pMutacion, seleccion, cruce, elitismo, maximizar, lastSeed, mutacion);
	}
	
	public void lanzaAGS()
	{
		alg.ejecuta(this.cromosoma, lastSeed);
	}

	private void generaFuncion(Funcion f, int n, double tol)
	{
		
		switch(f)
		{
			case FUNCION1:
				double[] xMax1 = {250};
				double[] xMin1 = {-250};
				FactoriaCromosomas.getInstancia().setxMax(xMax1);
				FactoriaCromosomas.getInstancia().setxMin(xMin1);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(1);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaF1();
				maximizar = false;
				break;
			case FUNCION2:
				double[] xMax2 = {512,512};
				double[] xMin2 = {-512,-512};
				FactoriaCromosomas.getInstancia().setxMax(xMax2);
				FactoriaCromosomas.getInstancia().setxMin(xMin2);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(2);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaF2();
				maximizar = false;
				break;
			case FUNCION3:
				double[] xMax3 = {12.1, 5.8};
				double[] xMin3 = {-3, 4.1};
				FactoriaCromosomas.getInstancia().setxMax(xMax3);
				FactoriaCromosomas.getInstancia().setxMin(xMin3);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(2);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaF3();
				maximizar = true;
				break;
			case FUNCION4:
				double[] xMax4 = new double[n];
				double[] xMin4 = new double[n];
				for(int i=0; i < n; ++i)
				{
					xMax4[i] = Math.PI;
					xMin4[i] = 0;
				}
				FactoriaCromosomas.getInstancia().setxMax(xMax4);
				FactoriaCromosomas.getInstancia().setxMin(xMin4);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(n);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaF4();
				maximizar = false;
				break;
			case FUNCION4R:
				double[] xMax4r = new double[n];
				double[] xMin4r = new double[n];
				for(int i=0; i < n; ++i)
				{
					xMax4r[i] = Math.PI;
					xMin4r[i] = 0;
				}
				FactoriaCromosomas.getInstancia().setxMax(xMax4r);
				FactoriaCromosomas.getInstancia().setxMin(xMin4r);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(n);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaF4real();
				maximizar = false;
				break;
			case FUNCION5:
				double[] xMax5 = {10,10};
				double[] xMin5 = {-10,-10};
				FactoriaCromosomas.getInstancia().setxMax(xMax5);
				FactoriaCromosomas.getInstancia().setxMin(xMin5);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(2);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaF5();
				maximizar = false;
				break;
			case HOSPITAL:
				double[] xMaxHosp = new double[n];
				double[] xMinHosp = new double[n];
				Datos datos = Parseador.parsear("Archivosdatos/ajuste.dat");
				for(int i=0; i < n; ++i)
				{
					xMaxHosp[i] = datos.getN()-1;
					xMinHosp[i] = 0;
				}
				FactoriaCromosomas.getInstancia().setxMax(xMaxHosp);
				FactoriaCromosomas.getInstancia().setxMin(xMinHosp);
				FactoriaCromosomas.getInstancia().setTol(tol);
				FactoriaCromosomas.getInstancia().setnVar(n);
				
				this.cromosoma = FactoriaCromosomas.getInstancia().creaCromosomaHospitales();
				maximizar = false;
				break;
			default:
				break;
		}
		
	}
	
}
