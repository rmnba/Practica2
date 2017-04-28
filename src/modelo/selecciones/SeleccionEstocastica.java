package modelo.selecciones;

import java.util.Random;

import modelo.Poblacion;
import modelo.cromosomas.Cromosoma;

public class SeleccionEstocastica extends Seleccion
{

	public SeleccionEstocastica(Poblacion pob, Random generador) 
	{
		super(pob, generador);
	}

	@Override
	public void selecciona(Cromosoma cromosoma) 
	{
		int[] seleccionados = new int[this.pob.getTam()];
		double prob;
		int posSuper;
		double seg = 1 / this.pob.getTam();	// Tamaño del segmento (1/N)
		prob = generador.nextDouble() * seg;		// Primera marca (entre 0 y seg)
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

}
