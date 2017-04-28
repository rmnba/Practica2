package modelo.mutaciones;

import java.util.Random;

import modelo.Poblacion;
import modelo.genes.Gen;

public class MutacionInsercion extends Mutacion
{

	public MutacionInsercion(Poblacion pob, Random generador, double probMut) 
	{
		super(pob, generador, probMut);
	}

	@Override
	public void muta() 
	{
		double prob;
		int pos, elem;
		Gen aux;
		int n= this.pob.getIndividuos()[0].getnVar();
		for (int i=0; i < this.pob.getTam(); ++i)
		{
			prob = generador.nextDouble();
			if(prob < probMutacion){
				pos = generador.nextInt(n-1);
				do
				{
					elem = generador.nextInt(n);
				} while(elem <= pos);
				
				aux = this.pob.getIndividuos()[i].getGenes()[elem].copia();
				for(int j=elem; j > pos; --j)
					this.pob.getIndividuos()[i].getGenes()[j] = this.pob.getIndividuos()[i].getGenes()[j-1].copia();
	
				this.pob.getIndividuos()[i].getGenes()[pos] = aux;
			}
		}
	}

}
