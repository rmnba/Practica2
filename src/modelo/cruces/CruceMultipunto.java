package modelo.cruces;

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

public class CruceMultipunto extends Cruce
{
	private Random generador;
	
	public CruceMultipunto(Random generador)
	{
		this.generador = generador;
	}

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		int nCortes = (int)generador.nextDouble()*(padre.getnVar() -1);
		Integer corte = 0;
		ArrayList<Integer> cortes = new ArrayList<Integer>(nCortes);
		for(int i=0; i < nCortes; ++i)
		{
			do
			{
				corte = (int)generador.nextDouble()*(padre.getnVar() -1);
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
			cruzAux(aux1, aux2, aux3,aux4, cortes.get(i));
			aux1 = aux3;
			aux2 = aux4;
		}
	}
	
	private void cruzAux(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2, int puntoCorte)
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

}
