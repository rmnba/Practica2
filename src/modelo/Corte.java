package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Corte 
{
	public static ArrayList<Integer> creaCortes(int nCortes, int nVar)
	{
		Random ran = new Random();
		ArrayList<Integer> cortes = new ArrayList<Integer>();
		Integer corte = 0;
		for(int i=0; i < nCortes; ++i)
		{
			do
			{
				corte = ran.nextInt(nVar);
			}
			while(cortes.contains(corte));
			
			cortes.add(corte);
		}
		cortes.sort(new Comparator<Integer>()
		{
			@Override
			public int compare(Integer o1, Integer o2) 
			{
				if(o1 < o2){
					return -1;
				}
				else if(o1 > o2){
					return 1;
				}
				else{
					return 0;
				}
			}
			
		});
		return cortes;
	}
}
