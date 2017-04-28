package modelo.cruces;

import modelo.cromosomas.Cromosoma;

public abstract class Cruce 
{
	public abstract void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2);
}
