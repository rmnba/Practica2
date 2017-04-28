package modelo.selecciones;

import java.util.Random;

import modelo.Poblacion;
import modelo.cromosomas.Cromosoma;

public class SeleccionRuleta extends Seleccion
{

	public SeleccionRuleta(Poblacion pob, Random generador) 
	{
		super(pob, generador);
	}

	@Override
	public void selecciona(Cromosoma cromosoma) 
	{
		int[] seleccionados = new int[this.pob.getTam()];
		double prob;
		int posSuper;
		for(int i=0; i < this.pob.getTam(); ++i)
		{
			prob = generador.nextDouble();
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

}
