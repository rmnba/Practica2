package modelo;

public class Datos 
{
	private int distancia[][];
	private int flujo[][];
	private int n;
	
	public Datos(int distancia[][], int flujo[][], int n) 
	{
		this.distancia = distancia;
		this.flujo = flujo;
		this.n = n;
	}
	
	public int [][] getDistancia()
	{
		return this.distancia;
	}
	
	public int [][] getFlujo()
	{
		return this.flujo;
	}
	
	public int getN()
	{
		return this.n;
	}
	
}
