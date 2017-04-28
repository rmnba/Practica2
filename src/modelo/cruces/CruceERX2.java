package modelo.cruces;

import java.util.ArrayList;

import modelo.cromosomas.Cromosoma;

public class CruceERX2 extends Cruce
{

	@Override
	public void cruza(Cromosoma padre, Cromosoma madre, Cromosoma hijo1, Cromosoma hijo2) 
	{
		ArrayList<ArrayList<Integer>> conexionesP = new ArrayList<ArrayList<Integer>>(padre.getnVar());		
		ArrayList<ArrayList<Integer>> conexionesM = new ArrayList<ArrayList<Integer>>(padre.getnVar());
		rellenaConexiones(conexionesP, padre, madre);
		rellenaConexiones(conexionesM, padre, madre);
		
		hijo1.getGenes()[0] = padre.getGenes()[0].copia();
		for(int k=0; k < padre.getnVar(); ++k)
			if(conexionesP.get(k).contains( hijo1.getGenes()[0].fenotipo()))
				conexionesP.get(k).remove(hijo1.getGenes()[0].fenotipo());
			
		int i=1;
		boolean bloqueo = false;
		while(i < padre.getnVar() && !bloqueo)
		{
			int col = (int)hijo1.getGenes()[i-1].fenotipo();
			int nConexiones = conexionesP.get(col).size(); 
			int mejor, posMejor = 0, actual, ciudad;
			mejor = Integer.MAX_VALUE;
			for(int fila=0; fila < nConexiones; ++fila)
			{
				ciudad = conexionesP.get(col).get(fila);
				actual = conexionesP.get(ciudad).size();
				if(actual < mejor)
				{
					mejor = actual;
					posMejor = fila;
				}
			}
			if(nConexiones == 0 || (mejor == 0 && i != padre.getnVar()-1))
				bloqueo = true;
			
			else
			{
				ciudad = conexionesP.get(col).get(posMejor);
				hijo1.getGenes()[i].setAlelo(ciudad);
				for(int k=0; k < padre.getnVar(); ++k)
					if(conexionesP.get(k).contains((Integer) ciudad))
						conexionesP.get(k).remove((Integer) ciudad);
					
				
				++i;
			}
		}
		
		hijo2 = madre.copia();
	}
	
	private void rellenaConexiones(ArrayList<ArrayList<Integer>> conexiones, Cromosoma padre, Cromosoma madre)
	{
		for(int i=0; i < padre.getnVar(); ++i)
			conexiones.add(new ArrayList<Integer>());
		
		for(int i=0; i < padre.getnVar(); ++i)
		{
			if(i == 0)
			{
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i+1].getAlelo()))
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i+1].getAlelo());
				
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i+1].getAlelo()))
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i+1].getAlelo());
				
			}
			else if(i == padre.getnVar()-1)
			{
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i-1].getAlelo()))
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i-1].getAlelo());
				
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i-1].getAlelo()))
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i-1].getAlelo());
				
			}
			else
			{
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i+1].getAlelo()))
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i+1].getAlelo());
				
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i+1].getAlelo()))
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i+1].getAlelo());
				
				if(!conexiones.get((int)padre.getGenes()[i].getAlelo()).contains((Integer)padre.getGenes()[i-1].getAlelo()))
					conexiones.get((int)padre.getGenes()[i].getAlelo()).add((Integer)padre.getGenes()[i-1].getAlelo());
				
				if(!conexiones.get((int)madre.getGenes()[i].getAlelo()).contains((Integer)madre.getGenes()[i-1].getAlelo()))
					conexiones.get((int)madre.getGenes()[i].getAlelo()).add((Integer)madre.getGenes()[i-1].getAlelo());
				
			}
		}
	}

}
