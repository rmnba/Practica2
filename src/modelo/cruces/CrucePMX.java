package modelo.cruces;

import java.util.ArrayList;

import modelo.Corte;
import modelo.cromosomas.Cromosoma;
import modelo.genes.Gen;

public class CrucePMX extends Cruce
{

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		ArrayList<Integer> cortes = Corte.creaCortes(2, padre.getnVar());

		Gen[] reemplazo1 = new Gen[padre.getnVar()];
		Gen[] reemplazo2 = new Gen[padre.getnVar()];

		for (int i = 0; i < padre.getnVar(); i++) 
		{
			reemplazo1[i] = padre.getGenes()[0].copia();	// Simplemente para inicializar cada gen
			reemplazo2[i] = padre.getGenes()[0].copia();
			reemplazo1[i].setAlelo(-1);
			reemplazo2[i].setAlelo(-1);
		}

		for (int i = cortes.get(0); i <= cortes.get(1); i++) 
		{
			hijo1.getGenes()[i] = madre.getGenes()[i].copia();
			hijo2.getGenes()[i] = padre.getGenes()[i].copia();

			reemplazo1[(int) madre.getGenes()[i].fenotipo()] = padre.getGenes()[i].copia();
			reemplazo2[(int) padre.getGenes()[i].fenotipo()] = madre.getGenes()[i].copia();
		}

		for (int i = 0; i < padre.getnVar(); i++) 
		{
			if ((i < cortes.get(0)) || (i > cortes.get(1))) 
			{
				Gen n1 = padre.getGenes()[i].copia();
				Gen m1 = reemplazo1[(int) n1.fenotipo()];

				Gen n2 =madre.getGenes()[i].copia();
				Gen m2 = reemplazo2[(int) n2.fenotipo()];

				while (m1.fenotipo() != -1) 
				{
					n1 = m1;
					m1 = reemplazo1[(int) m1.fenotipo()];
				}

				while (m2.fenotipo() != -1) 
				{
					n2 = m2;
					m2 = reemplazo2[(int) m2.fenotipo()];
				}

				hijo1.getGenes()[i] = n1;
				hijo2.getGenes()[i] = n2;
			}
		}
	}

}
