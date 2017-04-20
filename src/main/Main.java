package main;

import java.awt.EventQueue;

import vista.Ventana;

public class Main 
{

	public static void main(String[] args) 
	{
		final Ventana v = new Ventana();
		
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run() 
			{
				v.setVisible(true);						
			}
			
		});
		
	}

}
