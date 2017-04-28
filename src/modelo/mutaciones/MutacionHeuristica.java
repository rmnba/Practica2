package modelo.mutaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modelo.Corte;
import modelo.Poblacion;
import modelo.cromosomas.Cromosoma;

public class MutacionHeuristica extends Mutacion
{
	public MutacionHeuristica(Poblacion pob, Random generador, double probMut)
	{
		super(pob, generador, probMut);
	}

	@Override
	public void muta() 
	{
		double prob;	
		int n= this.pob.getIndividuos()[0].getnVar();
		ArrayList<Integer> cortes = Corte.creaCortes(3, n);
		for (int i=0; i < this.pob.getTam(); ++i)
		{
			prob = generador.nextDouble();
			if(prob < probMutacion)
			{
				ArrayList<Integer> aux = new ArrayList<Integer>();
				ArrayList<List<Integer>> perm = new ArrayList<List<Integer>>();
				ArrayList<Cromosoma> cobayas = new ArrayList<Cromosoma>();
				aux.add((Integer) this.pob.getIndividuos()[i].getGenes()[cortes.get(0)].getAlelo());
				aux.add((Integer) this.pob.getIndividuos()[i].getGenes()[cortes.get(1)].getAlelo());
				aux.add((Integer) this.pob.getIndividuos()[i].getGenes()[cortes.get(2)].getAlelo());
				perm =  permute(aux);
				for(int j=0; j < 6; ++j)
					cobayas.add(this.pob.getIndividuos()[i].copia());
				
				for(int j=0; j < 6; ++j)
				{
					cobayas.get(j).getGenes()[cortes.get(0)].setAlelo(perm.get(j).get(0));
					cobayas.get(j).getGenes()[cortes.get(1)].setAlelo(perm.get(j).get(1));
					cobayas.get(j).getGenes()[cortes.get(2)].setAlelo(perm.get(j).get(2));
				}
				int mejor = 0;
				double ev, evMejor = Double.MAX_VALUE;
				for(int j=0; j < 6; ++j)
				{
					ev = cobayas.get(j).evalua();
					if(ev < evMejor)
					{
						evMejor = ev;
						mejor = j;
					}
				}
				this.pob.getIndividuos()[i] = cobayas.get(mejor).copia();
				this.pob.getIndividuos()[i].resuelveFenotipo();
			}
		}
	}
}
