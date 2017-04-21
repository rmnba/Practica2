package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parseador {
	
	public Datos parsear(String archivo) {
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File(archivo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int n = sc.nextInt();
		
		int distancia[][] = new int [n][n];
		int flujo[][] = new int[n][n];
		sc.nextLine();
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				distancia[i][j] = sc.nextInt();
			}
			
		}
		sc.nextLine();
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				flujo[i][j] = sc.nextInt();
			}
			
		}
				
		return new Datos(distancia, flujo, n);
	}
}
