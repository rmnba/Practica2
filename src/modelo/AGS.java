package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.factoria.FactoriaCromosomas;
import modelo.cromosomas.funcion1.CromosomaF1;
import modelo.cromosomas.funcion2.CromosomaF2;
import modelo.cromosomas.funcion3.CromosomaF3;
import modelo.cromosomas.funcion4.CromosomaF4;
import modelo.cromosomas.funcion4real.CromosomaF4real;
import modelo.cromosomas.funcion5.CromosomaF5;
import modelo.genes.Gen;
import modelo.genes.GenReal;

public class AGS 
{
	private Poblacion pob;
	private int maxGeneraciones;
	private int numGeneracion; 
	private Cromosoma elMejor;
	private int indexElMejor;
	private double probCruce;
	private double probMutacion;
	
	private Mutacion metodoMut;
	
	private Select metodoSel;
	private boolean elitismo;
	private Cruce metodoCorte;
	private boolean maximizar;
	
	private Random generator;
	
	private ArrayList<Observador> obs = new ArrayList<Observador>();
	
	public AGS(int tam, Cromosoma cromosoma, int maxGen, double probCruce, double probMutacion, Select metodo, Cruce metodoCorte, boolean elitismo, boolean maximizar, long seed)
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
		this.metodoCorte = metodoCorte;
		this.elitismo = elitismo;
		this.maximizar = maximizar;
		this.generator = new Random(seed);
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
			this.seleccion(cromosoma);
			this.reproduccion();
			this.mutacion();
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
	
	private boolean contiene(Cromosoma c, int ini, int fin, Gen valor)
	{
		for(int i=ini; i <=fin; ++i)
			if(c.getGenes()[i].fenotipo() == valor.fenotipo())
				return true;
			
		return false;
	}
	
	private int busca(Cromosoma c, int ini, int fin, Gen valor)
	{
		for(int i=ini; i <=fin; ++i)
			if(c.getGenes()[i].fenotipo() == valor.fenotipo())
				return i;
	
		return -1;
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
	
	private void seleccion(Cromosoma cromosoma)
	{
		switch(metodoSel)
		{
			case RULETA:
				this.seleccionRuleta(cromosoma);
				break;
			case TORNEO_D:
				this.seleccionTorneoDeterminista(cromosoma);
				break;
			case TORNEO_P:
				this.seleccionTorneoProbabilista(cromosoma);
				break;
			case ESTOCASTICO:
				this.seleccionEstocastica(cromosoma);
				break;
			default:
				this.seleccionRuleta(cromosoma);
				break;
		}
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
			cruce(pob.getIndividuos()[seleccionados[i]],pob.getIndividuos()[seleccionados[i+1]], hijo1, hijo2);
			if(hijo1.esValido())
				pob.getIndividuos()[i] = hijo1;
			if(hijo2.esValido())
				pob.getIndividuos()[i+1] =  hijo2;		
		}
		
	}
	
	private void cruce(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2)
	{
		if(metodoCorte == Cruce.MONOPUNTO)
		{
			int puntoCorte = (int)generator.nextDouble()*(padre.getnVar() -1);
			cruceMonopunto(padre,madre,hijo1,hijo2,puntoCorte);
		}
		else if(metodoCorte == Cruce.MULTIPUNTO)
		{
			int nCortes = (int)generator.nextDouble()*(padre.getnVar() -1);
			Integer corte = 0;
			ArrayList<Integer> cortes = new ArrayList<Integer>(nCortes);
			for(int i=0; i < nCortes; ++i)
			{
				do
				{
					corte = (int)generator.nextDouble()*(padre.getnVar() -1);
				} while(cortes.contains(corte));
				cortes.set(i, corte);
			}
			Cromosoma aux1 = padre.copia();
			Cromosoma aux2 = madre.copia();
			Cromosoma aux3, aux4;
			
			if (aux1 instanceof CromosomaF1)
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaF1();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaF1();
			}
			else if (aux1 instanceof CromosomaF2)
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaF2();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaF2();
			}
			else if (aux1 instanceof CromosomaF3)
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaF3();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaF3();
			}
			else if (aux1 instanceof CromosomaF4)
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaF4();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaF4();
			}
			else if (aux1 instanceof CromosomaF4real)
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaF4real();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaF4real();
			}
			else  if (aux1 instanceof CromosomaF5)
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaF5();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaF5();
			}
			else
			{
				aux3 = FactoriaCromosomas.getInstancia().creaCromosomaHospitales();
				aux4 = FactoriaCromosomas.getInstancia().creaCromosomaHospitales();
			}
			
			for(int i=0; i < nCortes; ++i)
			{
				cruceMonopunto(aux1, aux2, aux3,aux4, cortes.get(i));
				aux1 = aux3;
				aux2 = aux4;
			}
		}
		else if(metodoCorte == Cruce.UNIFORME)
			cruceUniforme(padre,madre,hijo1,hijo2);
		
		else if(metodoCorte == Cruce.ARITMETICO)
		{
			cruceAritmetico(padre, madre, hijo1);
			cruceAritmetico(madre, hijo1, hijo2);
		}
		else if(metodoCorte == Cruce.SBX)
			cruceSBX(padre,madre,hijo1,hijo2);
		
		else if(metodoCorte == Cruce.PMX)
				crucePMX(padre, madre, hijo1, hijo2);
		
		else if(metodoCorte == Cruce.OX)
				cruceOX(padre, madre, hijo1, hijo2);			
			
		else if(metodoCorte == Cruce.OX_POSICIONES)
				cruceOXPosiciones(padre, madre, hijo1, hijo2);
		
		else if(metodoCorte == Cruce.CX)
				cruceCX(padre, madre, hijo1, hijo2);
			
		else if(metodoCorte == Cruce.ERX)
				cruceERX2(padre, madre, hijo1, hijo2);
			
		
	}
	
	private ArrayList<Integer> creaCortes(int nCortes, int nVar)
	{
		Random ran = new Random();
		ArrayList<Integer> cortes = new ArrayList<Integer>();
		Integer corte = 0;
		for(int i=0; i < nCortes; ++i)
		{
			do
			{
				corte = ran.nextInt(nVar);
			}
			while(cortes.contains(corte));
			
			cortes.add(corte);
		}
		cortes.sort(new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1 < o2){
					return -1;
				}
				else if(o1 > o2){
					return 1;
				}
				else{
					return 0;
				}
			}
			
		});
		return cortes;
	}
	
	private void rellenaConexiones(ArrayList<ArrayList<Integer>> conexiones, Cromosoma padre, Cromosoma madre){
		for(int i=0; i < padre.getnVar(); ++i){
			conexiones.add(new ArrayList<Integer>());
		}
		for(int i=0; i < padre.getnVar(); ++i){
			if(i == 0){
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i+1].getAlelo())){
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i+1].getAlelo());
				}
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i+1].getAlelo())){
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i+1].getAlelo());
				}
			}
			else if(i == padre.getnVar()-1){
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i-1].getAlelo())){
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i-1].getAlelo());
				}
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i-1].getAlelo())){
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i-1].getAlelo());
				}
			}
			else{
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i+1].getAlelo())){
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i+1].getAlelo());
				}
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i+1].getAlelo())){
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i+1].getAlelo());
				}
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i-1].getAlelo())){
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i-1].getAlelo());
				}
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i-1].getAlelo())){
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i-1].getAlelo());
				}
			}
		}
	}
	
	private void cruceERX2(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) {
		ArrayList<ArrayList<Integer>> conexionesP = new ArrayList<ArrayList<Integer>>(padre.getnVar());		
		ArrayList<ArrayList<Integer>> conexionesM = new ArrayList<ArrayList<Integer>>(padre.getnVar());
		rellenaConexiones(conexionesP, padre, madre);
		rellenaConexiones(conexionesM, padre, madre);
		
		hijo1.getGenes()[0] = padre.getGenes()[0].copia();
		for(int k=0; k < padre.getnVar(); ++k){
			if(conexionesP.get(k).contains( hijo1.getGenes()[0].fenotipo())){
				conexionesP.get(k).remove(hijo1.getGenes()[0].fenotipo());
			}
		}
		int i=1;
		boolean bloqueo = false;
		while(i < padre.getnVar() && !bloqueo){
			int col = (int)hijo1.getGenes()[i-1].fenotipo();
			int nConexiones = conexionesP.get(col).size(); 
			int mejor, posMejor = 0, actual, ciudad;
			mejor = Integer.MAX_VALUE;
			for(int fila=0; fila < nConexiones; ++fila){
				ciudad = conexionesP.get(col).get(fila);
				actual = conexionesP.get(ciudad).size();
				if(actual < mejor){
					mejor = actual;
					posMejor = fila;
				}
			}
			if(nConexiones == 0 || (mejor == 0 && i != padre.getnVar()-1)){
				bloqueo = true;
			}
			else{
				ciudad = conexionesP.get(col).get(posMejor);
				hijo1.getGenes()[i].setAlelo(ciudad);
				for(int k=0; k < padre.getnVar(); ++k){
					if(conexionesP.get(k).contains((Integer) ciudad)){
						conexionesP.get(k).remove((Integer) ciudad);
					}
				}
				++i;
			}
		}
		/*if(bloqueo){
			Debugger.erxFallido++;
			hijo1 = padre.copia();
		}
		Debugger.erxEjecutado++;*/
		
		hijo2 = madre.copia();
	}
	
	/*private void cruceOrdinal(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2){
		ArrayList<Integer> dinamica = new ArrayList<Integer>();
		ArrayList<Integer> dinamicaP = new ArrayList<Integer>();
		ArrayList<Integer> dinamicaM = new ArrayList<Integer>();
		ArrayList<Integer> cPadre = new ArrayList<Integer>();
		ArrayList<Integer> cMadre = new ArrayList<Integer>();
		ArrayList<Integer> ordenPadre = new ArrayList<Integer>();
		ArrayList<Integer> ordenMadre = new ArrayList<Integer>();
		for(int i=0; i < padre.getnVar(); ++i){
			dinamica.add(i);
			cPadre.add((int) padre.getGenes()[i].fenotipo());
			cMadre.add((int) madre.getGenes()[i].fenotipo());
		}
		Collections.shuffle(dinamica);
		for(int i=0; i < padre.getnVar(); ++i){
			dinamicaP.add(dinamica.get(i));
			dinamicaM.add(dinamica.get(i));
			
		}
		int posP, posM, eP, eM;
		for(int i=0; i < padre.getnVar(); ++i){
			eP = cPadre.get(i);
			eM = cMadre.get(i);
			posP = dinamicaP.indexOf(eP);
			posM = dinamicaM.indexOf(eM);
			ordenPadre.add(posP);
			ordenMadre.add(posM);
			dinamicaP.remove(posP);
			dinamicaM.remove(posM);
		}
		for(int i=0; i < padre.getnVar(); ++i){
			dinamicaP.add(dinamica.get(i));
			dinamicaM.add(dinamica.get(i));
			
		}
		ArrayList<Integer> cortes = creaCortes(1, padre.getnVar());
		for(int i=0; i < cortes.get(0); ++i){
			hijo1.getGenes()[i].setAlelo(dinamicaP.get(ordenPadre.get(i)));
			hijo2.getGenes()[i].setAlelo(dinamicaM.get(ordenMadre.get(i)));
			dinamicaP.remove((int)ordenPadre.get(i));
			dinamicaM.remove((int)ordenMadre.get(i));
		}
		for(int i=cortes.get(0); i < padre.getnVar(); ++i){
			hijo1.getGenes()[i].setAlelo(dinamicaP.get(ordenMadre.get(i)));
			hijo2.getGenes()[i].setAlelo(dinamicaM.get(ordenPadre.get(i)));
			dinamicaP.remove((int)ordenMadre.get(i));
			dinamicaM.remove((int)ordenPadre.get(i));
		}
		hijo1.resuelveFenotipo();
		hijo2.resuelveFenotipo();
	}*/
	
	private void cruceCX(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2){
		for (int i = 0; i < padre.getnVar(); i++) {
			hijo1.getGenes()[i] = padre.getGenes()[0].copia();	// Simplemente para inicializar cada gen
			hijo2.getGenes()[i] = padre.getGenes()[0].copia();
			hijo1.getGenes()[i].setAlelo(-1);
			hijo2.getGenes()[i].setAlelo(-1);
		}
		hijo1.resuelveFenotipo();
		hijo2.resuelveFenotipo();
		int index = 0;
		while(hijo1.getGenes()[index].fenotipo() == -1){
			hijo1.getGenes()[index] = padre.getGenes()[index].copia();
			index = (int) madre.getGenes()[index].fenotipo();
		}
		for (int i = 0; i < padre.getnVar(); i++) {
			if(hijo1.getGenes()[i].fenotipo() == -1){
				hijo1.getGenes()[i] = madre.getGenes()[i].copia();
			}
		}
		index = 0;
		while(hijo2.getGenes()[index].fenotipo() == -1){
			hijo2.getGenes()[index] = madre.getGenes()[index].copia();
			index = (int) padre.getGenes()[index].fenotipo();
		}
		for (int i = 0; i < padre.getnVar(); i++) {
			if(hijo2.getGenes()[i].fenotipo() == -1){
				hijo2.getGenes()[i] = padre.getGenes()[i].copia();
			}
		}
		hijo1.resuelveFenotipo();
		hijo2.resuelveFenotipo();
	}
	private void cruceOXPosiciones(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2){
		ArrayList<Integer> posiciones = creaCortes(4, padre.getnVar());
		ArrayList<Integer> posMadre = new ArrayList<Integer>(4);
		for(int i=0; i<padre.getnVar(); ++i){
			hijo1.getGenes()[i] = padre.getGenes()[i].copia();
			hijo2.getGenes()[i] = madre.getGenes()[i].copia();
		}
		for(int i=0; i < 4; ++i){
			posMadre.add(busca(madre, 0, padre.getnVar(), padre.getGenes()[posiciones.get(i)]));
		}
		for(int i=0; i < 4; ++i){
			hijo1.getGenes()[posiciones.get(i)] = madre.getGenes()[posMadre.get(i)].copia();
			hijo2.getGenes()[posMadre.get(i)] = padre.getGenes()[posiciones.get(i)].copia();
		}
	}
	private void cruceOX(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2){
		ArrayList<Integer> cortes = creaCortes(2, padre.getnVar());
		
		int indexH;
		
		for (int i = cortes.get(0); i <= cortes.get(1); i++) {
			hijo1.getGenes()[i] = madre.getGenes()[i].copia();
			hijo2.getGenes()[i] = padre.getGenes()[i].copia();
		}
		// Para el hijo 1
		indexH = cortes.get(1)+1;
		for(int i=cortes.get(1)+1; i < padre.getnVar(); ++i){
			// Bucle desde 2o punto de corte hasta el final
			if(!contiene(hijo1, cortes.get(0), cortes.get(1), padre.getGenes()[i])){
				hijo1.getGenes()[indexH] = padre.getGenes()[i].copia();
				indexH++;
			}
		}
		if(indexH >= padre.getnVar()) indexH = 0;
		for(int i=0; i <= cortes.get(1); ++i){
			// Bucle desde principio hasta 1er punto de corte
			if(!contiene(hijo1, cortes.get(0), cortes.get(1), padre.getGenes()[i])){
				hijo1.getGenes()[indexH] = padre.getGenes()[i].copia();
				indexH++;
				if(indexH >= padre.getnVar()) indexH = 0;
			}
		}
		// Para el hijo 2
		indexH = cortes.get(1)+1;
		for(int i=cortes.get(1)+1; i < padre.getnVar(); ++i){
			// Bucle desde 2o punto de corte hasta el final
			if(!contiene(hijo2, cortes.get(0), cortes.get(1), madre.getGenes()[i])){
				hijo2.getGenes()[indexH] = madre.getGenes()[i].copia();
				indexH++;
			}
		}
		if(indexH >= padre.getnVar()) indexH = 0;
		for(int i=0; i <= cortes.get(1); ++i){
			// Bucle desde principio hasta 1er punto de corte
			if(!contiene(hijo2, cortes.get(0), cortes.get(1), madre.getGenes()[i])){
				hijo2.getGenes()[indexH] = madre.getGenes()[i].copia();
				indexH++;
				if(indexH >= padre.getnVar()) indexH = 0;
			}
		}
	}
	private void crucePMX(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2){
		ArrayList<Integer> cortes = creaCortes(2, padre.getnVar());

		Gen[] reemplazo1 = new Gen[padre.getnVar()];
		Gen[] reemplazo2 = new Gen[padre.getnVar()];

		for (int i = 0; i < padre.getnVar(); i++) {
			reemplazo1[i] = padre.getGenes()[0].copia();	// Simplemente para inicializar cada gen
			reemplazo2[i] = padre.getGenes()[0].copia();
			reemplazo1[i].setAlelo(-1);
			reemplazo2[i].setAlelo(-1);
		}

		for (int i = cortes.get(0); i <= cortes.get(1); i++) {
			hijo1.getGenes()[i] = madre.getGenes()[i].copia();
			hijo2.getGenes()[i] = padre.getGenes()[i].copia();

			reemplazo1[(int) madre.getGenes()[i].fenotipo()] = padre.getGenes()[i].copia();
			reemplazo2[(int) padre.getGenes()[i].fenotipo()] = madre.getGenes()[i].copia();
		}

		// fill in remaining slots with replacements
		for (int i = 0; i < padre.getnVar(); i++) {
			if ((i < cortes.get(0)) || (i > cortes.get(1))) {
				Gen n1 = padre.getGenes()[i].copia();
				Gen m1 = reemplazo1[(int) n1.fenotipo()];

				Gen n2 =madre.getGenes()[i].copia();
				Gen m2 = reemplazo2[(int) n2.fenotipo()];

				while (m1.fenotipo() != -1) {
					n1 = m1;
					m1 = reemplazo1[(int) m1.fenotipo()];
				}

				while (m2.fenotipo() != -1) {
					n2 = m2;
					m2 = reemplazo2[(int) m2.fenotipo()];
				}

				hijo1.getGenes()[i] = n1;
				hijo2.getGenes()[i] = n2;
			}
		}
	}
	
	private void cruceSBX(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2)
	{
		double beta;
		double alpha = generator.nextDouble();
		if(alpha < 0.5)
			beta = Math.pow(2 * alpha, (1/(padre.getnVar() + 1)));
		
		else
			beta = Math.pow(0.5 *(1-alpha), (1/(padre.getnVar() + 1)));
		
		double x;
		GenReal auxH1, auxH2, auxM, auxP;
		for(int i=0; i < padre.getnVar(); ++i)
		{
			auxH1 = (GenReal) hijo1.getGenes()[i].copia();
			auxH2 = (GenReal) hijo2.getGenes()[i].copia();
			auxP = (GenReal) padre.getGenes()[i];
			auxM = (GenReal) madre.getGenes()[i];
			x = (auxP.fenotipo() + auxM.fenotipo()) /2;
			auxH1.setAlelo(x - (0.5 * beta) * (Math.abs(auxP.fenotipo()-auxM.fenotipo())));
			auxH2.setAlelo(x + (0.5 * beta) * (Math.abs(auxP.fenotipo()-auxM.fenotipo())));
			hijo1.getGenes()[i] = auxH1;
			hijo2.getGenes()[i] = auxH2;
		}
	}
	
	private void cruceAritmetico(Cromosoma padre, Cromosoma madre, Cromosoma hijo1)
	{
		GenReal auxH, auxM, auxP;
		for(int i=0; i < padre.getnVar(); ++i)
		{
			auxH = (GenReal) hijo1.getGenes()[i].copia();
			auxP = (GenReal) padre.getGenes()[i];
			auxM = (GenReal) madre.getGenes()[i];
			auxH.setAlelo((auxP.fenotipo() + auxM.fenotipo()) /2);
			hijo1.getGenes()[i] = auxH;
		}
	}
	
	private void cruceUniforme(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2)
	{
		double prob;
		for(int i=0; i < padre.getnVar(); ++i)
		{
			prob = generator.nextDouble();
			if(prob < 0.5)
			{
				hijo1.getGenes()[i] = padre.getGenes()[i];
				hijo2.getGenes()[i] = madre.getGenes()[i];
			}
			else
			{
				hijo1.getGenes()[i] = madre.getGenes()[i];
				hijo2.getGenes()[i] = padre.getGenes()[i];
			}
		}
	}
	
	
	private void cruceMonopunto(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2, int puntoCorte)
	{
		for(int i=0; i < puntoCorte; ++i)
		{
			hijo1.getGenes()[i] = padre.getGenes()[i];
			hijo2.getGenes()[i] = madre.getGenes()[i];
		}
		for(int i=puntoCorte; i < padre.getnVar(); ++i)
		{
			hijo1.getGenes()[i] = madre.getGenes()[i];
			hijo2.getGenes()[i] = padre.getGenes()[i];
		}
	}
	
	/*private void mutacion()
	{
		for(int i=0; i < this.pob.getTam(); ++i)	
			// Para todos los individuos de la Pob
			for(int j=0; j < pob.getIndividuos()[i].getnVar(); ++j)
				// Para todos los genes de ese individuo (Cromosoma)
				pob.getIndividuos()[i].getGenes()[j].mutar(this.probMutacion);
			
	}*/
	
	private void mutacion(){
		switch(metodoMut){
		/*case BINARIO:
			for(int i=0; i < this.pob.tam; ++i){		
				// Para todos los individuos de la Pob
				for(int j=0; j < pob.individuos[i].getnVar(); ++j){
					// Para todos los genes de ese individuo (Cromosoma)
					pob.individuos[i].getGenes()[j].mutar(this.probMutacion);
				}
			}
			break;*/
		case HEURISTICA:
			mutacionHeuristica();
			break;
		case INSERCION:
			mutacionInsercion();
			break;
		case INTERCAMBIO:
			mutacionIntercambio();
			break;
		case INVERSION:
			mutacionInversion();
			break;
		default:
			break;
		
		}
	}
	
	 public ArrayList<List<Integer>> permute(Collection<Integer> input) {
	        ArrayList<List<Integer>> output = new ArrayList<List<Integer>>();
	        if (input.isEmpty()) {
	            output.add(new ArrayList<Integer>());
	            return output;
	        }
	        List<Integer> list = new ArrayList<Integer>(input);
	        Integer head = list.get(0);
	        List<Integer> rest = list.subList(1, list.size());
	        for (List<Integer> permutations : permute(rest)) {
	            List<List<Integer>> subLists = new ArrayList<List<Integer>>();
	            for (int i = 0; i <= permutations.size(); i++) {
	                List<Integer> subList = new ArrayList<Integer>();
	                subList.addAll(permutations);
	                subList.add(i, head);
	                subLists.add(subList);
	            }
	            output.addAll(subLists);
	        }
	        return output;
	    }
	
	private void mutacionHeuristica() {
		double prob;	
		int n= this.pob.getIndividuos()[0].getnVar();
		ArrayList<Integer> cortes = creaCortes(3, n);
		for (int i=0; i < this.pob.getTam(); ++i){
			prob = generator.nextDouble();
			if(prob < probMutacion){
				ArrayList<Integer> aux = new ArrayList<Integer>();
				ArrayList<List<Integer>> perm = new ArrayList<List<Integer>>();
				ArrayList<Cromosoma> cobayas = new ArrayList<Cromosoma>();
				aux.add((Integer) this.pob.getIndividuos()[i].getGenes()[cortes.get(0)].getAlelo());
				aux.add((Integer) this.pob.getIndividuos()[i].getGenes()[cortes.get(1)].getAlelo());
				aux.add((Integer) this.pob.getIndividuos()[i].getGenes()[cortes.get(2)].getAlelo());
				perm =  permute(aux);
				for(int j=0; j < 6; ++j){
					cobayas.add(this.pob.getIndividuos()[i].copia());
				}
				for(int j=0; j < 6; ++j){
					cobayas.get(j).getGenes()[cortes.get(0)].setAlelo(perm.get(j).get(0));
					cobayas.get(j).getGenes()[cortes.get(1)].setAlelo(perm.get(j).get(1));
					cobayas.get(j).getGenes()[cortes.get(2)].setAlelo(perm.get(j).get(2));
				}
				int mejor = 0;
				double ev, evMejor = Double.MAX_VALUE;
				for(int j=0; j < 6; ++j){
					ev = cobayas.get(j).evalua();
					if(ev < evMejor){
						evMejor = ev;
						mejor = j;
					}
				}
				this.pob.getIndividuos()[i] = cobayas.get(mejor).copia();
				this.pob.getIndividuos()[i].resuelveFenotipo();
			}
		}
	}

	private void mutacionIntercambio() {
		double prob;
		int aux;
		int n= this.pob.getIndividuos()[0].getnVar();
		ArrayList<Integer> cortes = creaCortes(2, n);
		for (int i=0; i < this.pob.getTam(); ++i){
			prob = generator.nextDouble();
			if(prob < probMutacion){
				int ini = cortes.get(0);
				int fin = cortes.get(1);
				aux = (int) this.pob.getIndividuos()[i].getGenes()[ini].getAlelo();
				this.pob.getIndividuos()[i].getGenes()[ini].setAlelo((int) this.pob.getIndividuos()[i].getGenes()[fin].getAlelo());
				this.pob.getIndividuos()[i].getGenes()[fin].setAlelo(aux);
				this.pob.getIndividuos()[i].resuelveFenotipo();
			}
		}
		
	}

	private void mutacionInversion() {
		double prob;
		int aux;
		int n= this.pob.getIndividuos()[0].getnVar();
		ArrayList<Integer> cortes = creaCortes(2, n);
		for (int i=0; i < this.pob.getTam(); ++i){
			prob = generator.nextDouble();
			if(prob < probMutacion){
				int ini = cortes.get(0);
				int fin = cortes.get(1);
				while(ini < fin){
					aux = (int) this.pob.getIndividuos()[i].getGenes()[ini].getAlelo();
					this.pob.getIndividuos()[i].getGenes()[ini].setAlelo((int) this.pob.getIndividuos()[i].getGenes()[fin].getAlelo());
					this.pob.getIndividuos()[i].getGenes()[fin].setAlelo(aux);
					ini++;
					fin--;
				}
				this.pob.getIndividuos()[i].resuelveFenotipo();
			}
		}
		
	}

	private void mutacionInsercion() {
		double prob;
		int pos, elem;
		Gen aux;
		int n= this.pob.getIndividuos()[0].getnVar();
		for (int i=0; i < this.pob.getTam(); ++i){
			prob = generator.nextDouble();
			if(prob < probMutacion){
				pos = generator.nextInt(n-1);
				do{
					elem = generator.nextInt(n);
				}while(elem <= pos);
				aux = this.pob.getIndividuos()[i].getGenes()[elem].copia();
				for(int j=elem; j > pos; --j){
					this.pob.getIndividuos()[i].getGenes()[j] = this.pob.getIndividuos()[i].getGenes()[j-1].copia();
				}
				this.pob.getIndividuos()[i].getGenes()[pos] = aux;
			}
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
	
	
	private void seleccionRuleta(Cromosoma cromosoma)
	{
		int[] seleccionados = new int[this.pob.getTam()];
		double prob;
		int posSuper;
		for(int i=0; i < this.pob.getTam(); ++i)
		{
			prob = generator.nextDouble();
			posSuper=0;
			while((posSuper < this.pob.getTam()) && (prob > this.pob.getIndividuos()[posSuper].getPuntAcum())) 
				posSuper++;
			
			seleccionados[i] = posSuper;
		}
		Cromosoma[] nuevaPob = new Cromosoma[this.pob.getTam()];
		for(int i=0; i < this.pob.getTam(); ++i)
			nuevaPob[i] = this.pob.getIndividuos()[seleccionados[i]].copia();
		
		
		Poblacion nuevaPob2 = new Poblacion(this.pob.getTam(), cromosoma);
		nuevaPob2.setIndividuos(nuevaPob);
		this.pob = nuevaPob2;
	}
	
	private void seleccionEstocastica(Cromosoma cromosoma)
	{
		int[] seleccionados = new int[this.pob.getTam()];
		double prob;
		int posSuper;
		double seg = 1 / this.pob.getTam();	// Tama�o del segmento (1/N)
		prob = generator.nextDouble() * seg;		// Primera marca (entre 0 y seg)
		for(int i=0; i < this.pob.getTam(); ++i)
		{
			posSuper = 0;
			while((posSuper < this.pob.getTam()) && (prob > this.pob.getIndividuos()[posSuper].getPuntAcum())) 
				posSuper++;
			seleccionados[i] = posSuper;
			prob += seg;
		}
		Cromosoma[] nuevaPob = new Cromosoma[this.pob.getTam()];
		for(int i=0; i < this.pob.getTam(); ++i)
			nuevaPob[i] = this.pob.getIndividuos()[seleccionados[i]].copia();
		
		
		Poblacion nuevaPob2 = new Poblacion(this.pob.getTam(), cromosoma);
		nuevaPob2.setIndividuos(nuevaPob);
		this.pob = nuevaPob2;
	}
	
	private void seleccionTorneoDeterminista(Cromosoma cromosoma)
	{
		int[] seleccionados = new int[this.pob.getTam()];
		int posMejor;
		int indexA, indexB, indexC;
		for(int i=0; i < this.pob.getTam(); ++i)
		{
			indexA = (int) (generator.nextDouble() * this.pob.getTam());
			do
			{
				indexB =  (int) (generator.nextDouble() * this.pob.getTam());
			} while(indexB == indexA);
			
			do
			{
				indexC = (int) (generator.nextDouble() * this.pob.getTam());
			} while(indexB == indexC || indexA == indexC);
			
			if(maximizar)
			
				if(this.pob.getIndividuos()[indexA].evalua() > this.pob.getIndividuos()[indexB].evalua())
					if(this.pob.getIndividuos()[indexA].evalua() > this.pob.getIndividuos()[indexC].evalua())
						posMejor = indexA;
					
					else
						posMejor = indexC;
					
				
				else
					if(this.pob.getIndividuos()[indexC].evalua() > this.pob.getIndividuos()[indexB].evalua())
						posMejor = indexC;
					
					else
						posMejor = indexB;
					
				
			
			else
				if(this.pob.getIndividuos()[indexA].evalua() < this.pob.getIndividuos()[indexB].evalua())
					if(this.pob.getIndividuos()[indexA].evalua() < this.pob.getIndividuos()[indexC].evalua())
						posMejor = indexA;
					
					else
						posMejor = indexC;
					
				
				else
					if(this.pob.getIndividuos()[indexC].evalua() < this.pob.getIndividuos()[indexB].evalua())
						posMejor = indexC;
					
					else
						posMejor = indexB;		
			
			seleccionados[i] = posMejor;
		}
		Cromosoma[] nuevaPob = new Cromosoma[this.pob.getTam()];
		for(int i=0; i < this.pob.getTam(); ++i)
			nuevaPob[i] = this.pob.getIndividuos()[seleccionados[i]].copia();
		
		
		Poblacion nuevaPob2 = new Poblacion(this.pob.getTam(), cromosoma);
		nuevaPob2.setIndividuos(nuevaPob);
		this.pob = nuevaPob2;
	}
	
	private void seleccionTorneoProbabilista(Cromosoma cromosoma)
	{
		int[] seleccionados = new int[this.pob.getTam()];
		int posMejor;
		int indexA, indexB;
		double aleatorio;
		for (int i = 0; i < this.pob.getTam(); ++i)
		{
			indexA = (int) (generator.nextDouble() * this.pob.getTam());
			indexB = (int) (generator.nextDouble() * this.pob.getTam());
			aleatorio = (generator.nextDouble() * this.pob.getTam());
			
			if (this.maximizar)
			{
				if (aleatorio > this.pob.getIndividuos()[i].getSeed())
				{
					if (indexA > indexB)
						posMejor = indexA;
					else
						posMejor = indexB;
				}
				else
				{
					if (indexA > indexB)
						posMejor = indexB;
					else
						posMejor = indexA;
				}
			}
			else
			{
				if (aleatorio < this.pob.getIndividuos()[i].getSeed())
				{
					if (indexA < indexB)
						posMejor = indexA;
					else
						posMejor = indexB;
				}
				else
				{
					if (indexA < indexB)
						posMejor = indexB;
					else
						posMejor = indexA;
				}
			}
			
			seleccionados[i] = posMejor;
		}
		
		Cromosoma[] nuevaPob = new Cromosoma[this.pob.getTam()];
		for(int i=0; i < this.pob.getTam(); ++i)
			nuevaPob[i] = this.pob.getIndividuos()[seleccionados[i]].copia();
				
		Poblacion nuevaPob2 = new Poblacion(this.pob.getTam(), cromosoma);
		nuevaPob2.setIndividuos(nuevaPob);
		this.pob = nuevaPob2;
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
