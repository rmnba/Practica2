package modelo.mutaciones;

import java.util.ArrayList;
import java.util.Random;

import modelo.Corte;
import modelo.Poblacion;

public class MutacionInversion extends Mutacion
{
	public MutacionInversion(Poblacion pob, Random generador, double probMut) 
	{
		super(pob, generador, probMut);
	}
	
	@Override
	public void muta() 
	{
		double prob;
		int aux;
		int n= this.pob.getIndividuos()[0].getnVar();
		ArrayList<Integer> cortes = Corte.creaCortes(2, n);
		for (int i=0; i < this.pob.getTam(); ++i)
		{
			prob = generador.nextDouble();
			if(prob < probMutacion)
			{
				int ini = cortes.get(0);
				int fin = cortes.get(1);
				while(ini < fin)
				{
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
}
