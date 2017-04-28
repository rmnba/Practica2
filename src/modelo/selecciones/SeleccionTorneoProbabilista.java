package modelo.selecciones;

import java.util.Random;

import modelo.Poblacion;
import modelo.cromosomas.Cromosoma;

public class SeleccionTorneoProbabilista extends Seleccion
{
	
	private boolean maximizar;

	public SeleccionTorneoProbabilista(Poblacion pob, Random generador, boolean maximizar) 
	{
		super(pob, generador);
		this.maximizar = maximizar;
	}

	@Override
	public void selecciona(Cromosoma cromosoma) 
	{
		int[] seleccionados = new int[this.pob.getTam()];
		int posMejor;
		int indexA, indexB;
		double aleatorio;
		for (int i = 0; i < this.pob.getTam(); ++i)
		{
			indexA = (int) (generador.nextDouble() * this.pob.getTam());
			indexB = (int) (generador.nextDouble() * this.pob.getTam());
			aleatorio = (generador.nextDouble() * this.pob.getTam());
			
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

}
