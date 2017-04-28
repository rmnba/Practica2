package modelo.selecciones;

import java.util.Random;

import modelo.Poblacion;
import modelo.cromosomas.Cromosoma;

public abstract class Seleccion 
{
	protected Poblacion pob;
	
	protected Random generador;
	
	public Seleccion(Poblacion pob, Random generador)
	{
		this.pob = pob;
		this.generador = generador;
	}
	
	public abstract void selecciona(Cromosoma cromosoma);
}
