package modelo;

import modelo.cromosomas.Cromosoma;

public interface Observador 
{
	void onGeneracionTerminada(Poblacion pob, Cromosoma mejorGen, Cromosoma elMejor);
	
	void onAGSTerminado(Cromosoma elMejor);
}
