package modelo.mutaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import modelo.Poblacion;

public abstract class Mutacion 
{
	protected Poblacion pob;
	
	protected Random generador;
	
	protected double probMutacion;
	
	public Mutacion(Poblacion pob, Random generador, double probMut)
	{
		this.pob = pob;
		this.generador = generador;
		this.probMutacion = probMut;
	}
	
	protected ArrayList<List<Integer>> permute(Collection<Integer> input) 
	{
        ArrayList<List<Integer>> output = new ArrayList<List<Integer>>();
        if (input.isEmpty()) 
        {
            output.add(new ArrayList<Integer>());
            return output;
        }
        List<Integer> list = new ArrayList<Integer>(input);
        Integer head = list.get(0);
        List<Integer> rest = list.subList(1, list.size());
        for (List<Integer> permutations : permute(rest)) 
        {
            List<List<Integer>> subLists = new ArrayList<List<Integer>>();
            for (int i = 0; i <= permutations.size(); i++) 
            {
                List<Integer> subList = new ArrayList<Integer>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }
		
	public abstract void muta();
}
