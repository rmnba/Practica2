package modelo;

import java.util.ArrayList;
import java.util.Random;

import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.factoria.FactoriaCromosomas;
import modelo.cromosomas.funcion1.CromosomaF1;
import modelo.cromosomas.funcion2.CromosomaF2;
import modelo.cromosomas.funcion3.CromosomaF3;
import modelo.cromosomas.funcion4.CromosomaF4;
import modelo.cromosomas.funcion4real.CromosomaF4real;
import modelo.cromosomas.funcion5.CromosomaF5;
import modelo.cruces.Cruce;
import modelo.cruces.CruceAritmetico;
import modelo.cruces.CruceCX;
import modelo.cruces.CruceERX2;
import modelo.cruces.CruceMonopunto;
import modelo.cruces.CruceMultipunto;
import modelo.cruces.CruceOX;
import modelo.cruces.CruceOXPosiciones;
import modelo.cruces.CrucePMX;
import modelo.cruces.CruceSBX;
import modelo.cruces.CruceUniforme;
import modelo.cruces.Cruzar;
import modelo.mutaciones.Mutacion;
import modelo.mutaciones.MutacionBinaria;
import modelo.mutaciones.MutacionHeuristica;
import modelo.mutaciones.MutacionInsercion;
import modelo.mutaciones.MutacionIntercambio;
import modelo.mutaciones.MutacionInversion;
import modelo.mutaciones.Mutar;
import modelo.selecciones.Seleccion;
import modelo.selecciones.SeleccionEstocastica;
import modelo.selecciones.SeleccionRuleta;
import modelo.selecciones.SeleccionTorneoDeterminista;
import modelo.selecciones.SeleccionTorneoProbabilista;
import modelo.selecciones.Select;

public class AGS 
{
	private Poblacion pob;
	private int maxGeneraciones;
	private int numGeneracion; 
	private Cromosoma elMejor;
	private int indexElMejor;
	private double probCruce;
	private double probMutacion;
	
	private Mutar metodoMut;
	
	private Select metodoSel;
	private boolean elitismo;
	private Cruzar metodoCorte;
	private boolean maximizar;
	
	private Mutacion mut;
	private Seleccion sel;
	private Cruce cru;
	
	private Random generator;
	
	private ArrayList<Observador> obs = new ArrayList<Observador>();
	
	public AGS(int tam, Cromosoma cromosoma, int maxGen, double probCruce, double probMutacion, Select metodo, Cruzar metodoCor, boolean elitismo, boolean maximizar, long seed, Mutar mutacion)
	{
		this.pob = new Poblacion(tam, cromosoma);
		this.pob.setSeed(seed);
		this.maxGeneraciones = maxGen;
		this.probCruce = probCruce;
		this.probMutacion = probMutacion;
		this.numGeneracion = 0;
		this.elMejor = null;
		this.indexElMejor = 0;
		this.metodoSel = metodo;
		this.metodoCorte = metodoCor;
		this.elitismo = elitismo;
		this.maximizar = maximizar;
		this.metodoMut = mutacion;
		this.generator = new Random(seed);
		this.inicializaOperadores(metodoSel, metodoMut, metodoCorte);
	}
	
	public void addObserver(Observador o)
	{
		if(!obs.contains(o))
			obs.add(o);
		
	}
	
	public void removeObserver(Observador o)
	{
		if(obs.contains(o))
			obs.remove(o);
		
	}
	
	public Cromosoma ejecuta(Cromosoma cromosoma)
	{
		pob.inicializa(cromosoma);
		this.evaluarPoblacion();
		while(!this.terminado())
		{
			this.numGeneracion++;
			int nElite = (int)(this.pob.getTam() * 0.02);
			Cromosoma[] elite = null;
			if(elitismo)
			{
				elite = new Cromosoma[nElite];
				this.quicksort(this.pob.getIndividuos(), 0, this.pob.getTam()-1);
				for(int i=0; i < nElite; ++i)
					if(!maximizar)
						elite[i] = this.pob.getIndividuos()[i].copia();
					
					else
						elite[i] = this.pob.getIndividuos()[this.pob.getTam()-1-i].copia();
					
				
			}
			this.sel.selecciona(cromosoma);
			this.reproduccion();
			this.mut.muta();
			if(elitismo)
			{
				this.quicksort(this.pob.getIndividuos(), 0, this.pob.getTam()-1);
				for(int i=0; i < nElite; ++i)
					// Elitismo: reemplazando por los peores 
					if(!maximizar)
						this.pob.getIndividuos()[this.pob.getTam()-1-i] = elite[i].copia();
					
					else
						this.pob.getIndividuos()[i] = elite[i].copia();
					
				
			}
			this.evaluarPoblacion();
			notifyGeneracionTerminada(this.pob.getIndividuos()[this.indexElMejor], elMejor);
		}
		notifyAGSTerminado(elMejor);
		this.elMejor.resuelveFenotipo();
		return this.elMejor;
	}
	
	private void inicializaOperadores(Select metodoSel, Mutar metodoMut, Cruzar metodoCorte)
	{
		switch(metodoSel)
		{
			case RULETA:
				sel = new SeleccionRuleta(pob, generator);
				break;
			case TORNEO_D:
				sel = new SeleccionTorneoDeterminista(pob, generator, maximizar);
				break;
			case TORNEO_P:
				sel = new SeleccionTorneoProbabilista(pob, generator, maximizar);
				break;
			case ESTOCASTICO:
				sel = new SeleccionEstocastica(pob, generator);
				break;
			default:
				sel = new SeleccionRuleta(pob, generator);
				break;
		}
		
		switch(metodoMut)
		{
			case BINARIA:
				mut = new MutacionBinaria (pob, generator, probMutacion);
				break;
			case HEURISTICA:
				mut = new MutacionHeuristica(pob, generator, probMutacion);
				break;
			case INSERCION:
				mut = new MutacionInsercion(pob, generator, probMutacion);
				break;
			case INTERCAMBIO:
				mut = new MutacionIntercambio(pob, generator, probMutacion);
				break;
			case INVERSION:
				mut = new MutacionInversion(pob, generator, probMutacion);
				break;
			default:
				break;			
		}
		
		switch (metodoCorte)
		{
			case ERX:
				cru = new CruceERX2();
				break;
			case CX:
				cru = new CruceCX();
				break;
			case OX_POSICIONES:
				cru = new CruceOXPosiciones();
				break;
			case OX:
				cru = new CruceOX();
				break;
			case PMX:
				cru = new CrucePMX();
				break;
			case SBX:
				cru = new CruceSBX(this.generator);
				break;
			case ARITMETICO:
				cru = new CruceAritmetico();
				break;
			case UNIFORME:
				cru = new CruceUniforme(this.generator);
				break;
			case MONOPUNTO:
				cru = new CruceMonopunto(this.generator);
				break;
			case MULTIPUNTO:
				cru = new CruceMultipunto(this.generator);
				break;
		}
	}
	
	private void notifyAGSTerminado(Cromosoma c) 
	{
		for(Observador o : obs)
			o.onAGSTerminado(c);
	}

	private void notifyGeneracionTerminada(Cromosoma c,	Cromosoma elMejor) 
	{
		for(Observador o : obs)
			o.onGeneracionTerminada(pob, c, elMejor);
		
	}

	private boolean terminado()
	{
		return (numGeneracion >= maxGeneraciones);
	}
	
	private void reproduccion()
	{
		int[] seleccionados = new int[this.pob.getTam()];
		int numSeleCruce = 0;
		double prob;
		Cromosoma hijo1, hijo2;
		
		if (pob.getIndividuos()[0] instanceof CromosomaF1)
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaF1();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaF1();
		}
		else if (pob.getIndividuos()[0] instanceof CromosomaF2)
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaF2();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaF2();
		}
		else if (pob.getIndividuos()[0] instanceof CromosomaF3)
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaF3();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaF3();
		}
		else if (pob.getIndividuos()[0] instanceof CromosomaF4)
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaF4();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaF4();
		}
		else if (pob.getIndividuos()[0] instanceof CromosomaF4real)
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaF4real();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaF4real();
		}
		else if (pob.getIndividuos()[0] instanceof CromosomaF5)
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaF5();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaF5();
		}
		else
		{
			hijo1 = FactoriaCromosomas.getInstancia().creaCromosomaHospitales();
			hijo2 = FactoriaCromosomas.getInstancia().creaCromosomaHospitales();
		}
		
		for(int i=0; i < this.pob.getTam(); ++i)
		{
			prob = generator.nextDouble();
			if(prob < this.probCruce)
			{
				seleccionados[numSeleCruce] = i;
				numSeleCruce++;
			}
		}
		
		if(numSeleCruce % 2 == 1)
			numSeleCruce--;
		
		for(int i=0; i < numSeleCruce; i+=2)
		{
			cru.cruza(pob.getIndividuos()[seleccionados[i]],pob.getIndividuos()[seleccionados[i+1]], hijo1, hijo2);
			if(hijo1.esValido())
				pob.getIndividuos()[i] = hijo1;
			if(hijo2.esValido())
				pob.getIndividuos()[i+1] =  hijo2;		
		}
		
	}

	private void ajustaAptitud()
	{
		// Metodo de Escalado
		double fmin = this.pob.getIndividuos()[0].evalua();
		for (int i=1; i < this.pob.getTam(); ++i)
			if(fmin > this.pob.getIndividuos()[i].evalua())
				fmin = this.pob.getIndividuos()[i].evalua();
					
		for (int i=0; i < this.pob.getTam(); ++i)
			this.pob.getIndividuos()[i].setAptitud(this.pob.getIndividuos()[i].evalua() + Math.abs(fmin));
		
	}
	
	private void evaluarPoblacion()
	{
		double maximo = Double.MIN_VALUE;
		double minimo = Double.MAX_VALUE;
		double aux = 0;
		double sumaAptitud = 0;
		double puntAcum = 0;
		
		// Se ajustan todos los valores por Escalado
		ajustaAptitud();
		
		// Buscamos el maximo
		for(int i=0; i < pob.getTam(); ++i)
		{
			aux = pob.getIndividuos()[i].getAptitud();
			sumaAptitud += aux;
			if(maximo < aux)
			{
				maximo = aux;
				indexElMejor = i;
			}	
		}
		for(int i=0; i < pob.getTam(); ++i)
		{
			pob.getIndividuos()[i].setPuntuacion(pob.getIndividuos()[i].getAptitud()/sumaAptitud);
			pob.getIndividuos()[i].setPuntAcum(pob.getIndividuos()[i].getPuntuacion() + puntAcum);
			puntAcum += pob.getIndividuos()[i].getPuntuacion();
		}
		// Si se quiere minimizar, se busca el minimo (la aptitud es igual a la aptitud del maximo - aptitud individuo)
		if(!maximizar)
		{
			// Se reajustan las aptitudes 
			sumaAptitud = 0;
			for(int i=0;  i < pob.getTam(); ++i)
			{
				aux = maximo - pob.getIndividuos()[i].getAptitud();
				sumaAptitud += aux;
			}
			// Se busca el minimo
			minimo = maximo;
			for(int i=0; i < pob.getTam(); ++i)
				if(pob.getIndividuos()[i].getAptitud() < minimo)
				{
					minimo = pob.getIndividuos()[i].getAptitud();
					indexElMejor = i;
				}
			
			puntAcum = 0;
			for(int i=0; i < pob.getTam(); ++i)
			{
				pob.getIndividuos()[i].setPuntuacion((maximo - pob.getIndividuos()[i].getAptitud())/sumaAptitud);
				pob.getIndividuos()[i].setPuntAcum(pob.getIndividuos()[i].getPuntuacion() + puntAcum);
				puntAcum += pob.getIndividuos()[i].getPuntuacion();
			}
		}
		if(elMejor == null)
			elMejor = pob.getIndividuos()[indexElMejor].copia();
		else
		{
			double evaluacionElMejor = this.elMejor.evalua();
			double evaluacionMejorActual = this.pob.getIndividuos()[indexElMejor].evalua();
			
			if(maximizar && (evaluacionMejorActual > evaluacionElMejor)) // maximo
				elMejor = pob.getIndividuos()[indexElMejor].copia();
			
			else if(!maximizar && (evaluacionMejorActual < evaluacionElMejor))// minimo
				elMejor = pob.getIndividuos()[indexElMejor].copia();
			
		}
	}
	
	private void quicksort(Cromosoma A[], int izq, int der) 
	{

		  Cromosoma pivote= A[izq]; // tomamos primer elemento como pivote
		  int i=izq; // i realiza la b�squeda de izquierda a derecha
		  int j=der; // j realiza la b�squeda de derecha a izquierda
		  Cromosoma aux;
		 
		  while(i<j)
		  {            // mientras no se crucen las b�squedas
		     while(A[i].evalua() <= pivote.evalua() && i<j) 
		    	 i++; // busca elemento mayor que pivote
		     while(A[j].evalua() > pivote.evalua()) 
		    	 j--;         // busca elemento menor que pivote
		     if (i<j) 
		     {                      // si no se han cruzado                      
		         aux= A[i];                  // los intercambia
		         A[i]=A[j];
		         A[j]=aux;
		     }
		   }
		   A[izq]=A[j]; // se coloca el pivote en su lugar de forma que tendremos
		   A[j]=pivote; // los menores a su izquierda y los mayores a su derecha
		   if(izq<j-1)
		      quicksort(A,izq,j-1); // ordenamos subarray izquierdo
		   if(j+1 <der)
		      quicksort(A,j+1,der); // ordenamos subarray derecho
		}
}
