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
	
	public int getDistanciaPunto(int a, int b)
	{
		return this.distancia[a][b];
	}
	
	public int getFlujoPunto(int a, int b)
	{
		return this.flujo[a][b] ;
	}
	
	public int getN()
	{
		return this.n;
	}
	
}
