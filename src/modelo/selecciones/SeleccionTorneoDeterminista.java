package modelo.selecciones;

import java.util.Random;

import modelo.Poblacion;
import modelo.cromosomas.Cromosoma;

public class SeleccionTorneoDeterminista extends Seleccion
{
	private boolean maximizar;
	
	public SeleccionTorneoDeterminista(Poblacion pob, Random generador, boolean maximizar) 
	{
		super(pob, generador);
		this.maximizar = maximizar;
	}

	@Override
	public void selecciona(Cromosoma cromosoma) 
	{
		int[] seleccionados = new int[this.pob.getTam()];
		int posMejor;
		int indexA, indexB, indexC;
		for(int i=0; i < this.pob.getTam(); ++i)
		{
			indexA = (int) (generador.nextDouble() * this.pob.getTam());
			do
			{
				indexB =  (int) (generador.nextDouble() * this.pob.getTam());
			} while(indexB == indexA);
			
			do
			{
				indexC = (int) (generador.nextDouble() * this.pob.getTam());
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

}
