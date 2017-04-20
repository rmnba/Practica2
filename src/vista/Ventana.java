package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.math.plot.Plot2DPanel;

import modelo.Cruce;
import modelo.Funcion;
import modelo.Observador;
import modelo.Poblacion;
import modelo.Select;
import modelo.cromosomas.Cromosoma;
import modelo.cromosomas.funcion1.CromosomaF1;
import modelo.cromosomas.funcion3.CromosomaF3;
import controlador.Controlador;

public class Ventana extends JFrame implements Observador, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4601737718372115147L;
	
	private Controlador c;
	private JComboBox<Funcion> cbFuncion; 
	private JComboBox<Cruce> cbCruce;
	private JComboBox<String> cbSeleccion;
	private JTextField tfTolerancia;
	private JTextField tfPoblacion;
	private JTextField tfGeneraciones;
	private JTextField tfprobCruces;
	private JTextField tfprobMutacion;
	private JTextField tfSemilla;
	private JLabel lbN;
	private JTextField tfN;
	private JCheckBox cbElitismo;
	
	private JButton run;
	private JButton reRun;
	private JButton stop;
	
	private static Funcion[] funciones = {Funcion.FUNCION1, Funcion.FUNCION2,Funcion.FUNCION3,Funcion.FUNCION4, Funcion.FUNCION4R, Funcion.FUNCION5};
	private static Cruce[] crucesB = {Cruce.MONOPUNTO, Cruce.MULTIPUNTO, Cruce.UNIFORME};
	private static String[] selecciones = {"RULETA", "TORNEO DETERMINISTA", "TORNEO PROBABILISTA", "ESTOCASTICO"};
	
	private Plot2DPanel plot;
	private JTextArea taResultados;
	private double[] ejeGeneracion;
	private double[] valorMedia;
	private double[] valorMejorGen;
	private double[] valorElMejor;
	private int iteracion;
	
	public Ventana()
	{
		this.c = new Controlador();;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setTitle("Practica 1");
		
		cbFuncion = new JComboBox<Funcion>(funciones);
		cbFuncion.setSelectedIndex(0);
		cbFuncion.addActionListener(this);
		cbCruce = new JComboBox<Cruce>(crucesB);
		cbCruce.setSelectedIndex(0);
		cbSeleccion = new JComboBox<String>(selecciones);
		cbSeleccion.setSelectedIndex(0);
		
		tfTolerancia = new JTextField("0.001", 6);
		tfPoblacion = new JTextField("100", 6);
		tfGeneraciones = new JTextField("100", 6);
		tfprobCruces = new JTextField("60", 6);
		tfprobMutacion = new JTextField("5", 6);
		tfSemilla = new JTextField("0", 6);
		tfN = new JTextField("1", 6);
		lbN = new JLabel("n");
		
		tfN.setVisible(false);
		lbN.setVisible(false);
		
		cbElitismo = new JCheckBox();
		
		run = new JButton("Ejecuta este AG");
		reRun = new JButton("Volver a ejecutar este mismo AG");
		stop = new JButton("Elimina este AG");
		
		run.addActionListener(this);
		reRun.addActionListener(this);
		stop.addActionListener(this);
		
		plot = new Plot2DPanel();
		plot.setPreferredSize(new Dimension(800,600));
		plot.setAxisLabel(0, "Generacion");
		plot.setAxisLabel(1, "Valor");
		plot.setLegendOrientation("SOUTH");
		
		taResultados = new JTextArea();
		taResultados.setPreferredSize(new Dimension(800,200));
		taResultados.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Resultados"));
		taResultados.setEditable(false);
		JPanel panelFunciones = new JPanel();
		panelFunciones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Funcion"));
		panelFunciones.setLayout(new GridLayout(2,2));
		
		JPanel panelParametros = new JPanel();
		panelParametros.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Parametros"));
		panelParametros.setLayout(new GridLayout(9,2));
		
		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Acciones"));
		panelAcciones.setLayout(new GridLayout(3,1));
		panelAcciones.setPreferredSize(new Dimension(200,200));

		JPanel panelDatos = new JPanel();
		panelDatos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Datos"));
		panelDatos.setLayout(new BorderLayout());
		
		panelFunciones.add(new JLabel("Funcion"));
		panelFunciones.add(cbFuncion);
		panelFunciones.add(lbN);
		panelFunciones.add(tfN);
		
		panelParametros.add(new JLabel("Precision"));
		panelParametros.add(tfTolerancia);
		panelParametros.add(new JLabel("Poblacion"));
		panelParametros.add(tfPoblacion);
		panelParametros.add(new JLabel("Iteraciones"));
		panelParametros.add(tfGeneraciones);
		panelParametros.add(new JLabel("% Cruces"));
		panelParametros.add(tfprobCruces);
		panelParametros.add(new JLabel("% Mutacion"));
		panelParametros.add(tfprobMutacion);
		panelParametros.add(new JLabel("Semilla"));
		panelParametros.add(tfSemilla);
		panelParametros.add(new JLabel("Cruce"));
		panelParametros.add(cbCruce);
		panelParametros.add(new JLabel("Seleccion"));
		panelParametros.add(cbSeleccion);
		panelParametros.add(new JLabel("Elitismo"));
		panelParametros.add(cbElitismo);
		
		panelAcciones.add(run);
		panelAcciones.add(reRun);
		panelAcciones.add(stop);
		
		
		JPanel panelControles = new JPanel(new BorderLayout());
		
		panelControles.setLayout(new BorderLayout());
		panelControles.add(panelFunciones, BorderLayout.NORTH);
		panelControles.add(panelParametros, BorderLayout.CENTER);
		panelControles.add(panelAcciones, BorderLayout.SOUTH);
		
		panelDatos.add(plot,BorderLayout.CENTER);
		panelDatos.add(taResultados, BorderLayout.SOUTH);
		
		this.add(panelControles, BorderLayout.WEST);
		this.add(panelDatos, BorderLayout.CENTER);
		
		this.pack();
	}

	@Override
	public void onGeneracionTerminada(Poblacion pob, Cromosoma mejorGen, Cromosoma elMejor) 
	{
		double media = 0;
		for(int i=0; i < pob.getTam(); ++i)
			media += pob.getIndividuos()[i].evalua();
		
		media /= pob.getTam();
		ejeGeneracion[iteracion] = iteracion;
		valorMedia[iteracion] = media;
		valorMejorGen[iteracion] = mejorGen.evalua();
		valorElMejor[iteracion] = elMejor.evalua();
		iteracion++;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Select seleccion = null;
		switch ((String) cbSeleccion.getSelectedItem())
		{
			case "RULETA":
				seleccion = Select.RULETA;
				break;
			case "TORNEO DETERMINISTA": 
				seleccion = Select.TORNEO_D;
				break;
			case "TORNEO PROBABILISTA":
				seleccion = Select.TORNEO_P;
				break;
			case "ESTOCASTICO":
				seleccion = Select.ESTOCASTICO;
				break;
		}
		
		Object source = e.getSource();
		if(run == source)
		{
			try
			{
				c.setParametersRun((Funcion)cbFuncion.getSelectedItem(), Integer.parseInt(tfN.getText()), Double.parseDouble(tfTolerancia.getText()), Integer.parseInt(tfPoblacion.getText()), Integer.parseInt(tfGeneraciones.getText()), Double.parseDouble(tfprobCruces.getText())/100, Double.parseDouble(tfprobMutacion.getText())/100, Long.parseLong(tfSemilla.getText()), (Cruce)cbCruce.getSelectedItem(), seleccion, cbElitismo.isSelected());
				taResultados.setForeground(Color.BLACK);
			}
			catch(NumberFormatException ex)
			{
				if (tfN.getText().equals(null))
				{
					taResultados.setText("RELLENAR CAMPO N");
					taResultados.setForeground(Color.RED);
				}
				else
				{
					taResultados.setText("DATOS ERRONEOS");
					taResultados.setForeground(Color.RED);
				}
				tfN.setText(null);
				return;
			}
			catch(NegativeArraySizeException ex)
			{
				taResultados.setText("DATOS NEGATIVOS");
				taResultados.setForeground(Color.RED);
				return;
			}
			iteracion = 0;
			ejeGeneracion = new double[Integer.parseInt(tfGeneraciones.getText())];
			valorMedia = new double[Integer.parseInt(tfGeneraciones.getText())];
			valorMejorGen = new double[Integer.parseInt(tfGeneraciones.getText())];
			valorElMejor = new double[Integer.parseInt(tfGeneraciones.getText())];
			plot.removeAllPlots();
			c.addObserver(this);
			c.lanzaAGS();
			
		}
		else if(reRun == source)
		{
			try
			{
				c.setParametersReRun((Funcion)cbFuncion.getSelectedItem(), Integer.parseInt(tfN.getText()), Double.parseDouble(tfTolerancia.getText()), Integer.parseInt(tfPoblacion.getText()), Integer.parseInt(tfGeneraciones.getText()), Double.parseDouble(tfprobCruces.getText())/100, Double.parseDouble(tfprobMutacion.getText())/100, Long.parseLong(tfSemilla.getText()), (Cruce)cbCruce.getSelectedItem(), seleccion, cbElitismo.isSelected());
				taResultados.setForeground(Color.BLACK);
			}
			catch(NumberFormatException ex)
			{
				if (tfN.getText().equals(null))
				{
					taResultados.setText("RELLENAR CAMPO N");
					taResultados.setForeground(Color.RED);
				}
				else
				{
					taResultados.setText("DATOS ERRONEOS");
					taResultados.setForeground(Color.RED);
				}
				tfN.setText(null);
				return;
			}
			catch(NegativeArraySizeException ex)
			{
				taResultados.setText("DATOS NEGATIVOS");
				taResultados.setForeground(Color.RED);
				return;
			}
			iteracion = 0;
			ejeGeneracion = new double[Integer.parseInt(tfGeneraciones.getText())];
			valorMedia = new double[Integer.parseInt(tfGeneraciones.getText())];
			valorMejorGen = new double[Integer.parseInt(tfGeneraciones.getText())];
			valorElMejor = new double[Integer.parseInt(tfGeneraciones.getText())];
			plot.removeAllPlots();
			c.addObserver(this);
			c.lanzaAGS();
			
		}
		else if(stop == source)
		{
			plot.removeAllPlots();
			taResultados.setText(null);
		}
		else if(cbFuncion == source)
		{
			if(cbFuncion.getSelectedItem() == Funcion.FUNCION4)
			{
				lbN.setVisible(true);
				tfN.setVisible(true);
			}
			else if(cbFuncion.getSelectedItem() == Funcion.FUNCION4R)
			{
				lbN.setVisible(true);
				tfN.setVisible(true);
				cbCruce.addItem(Cruce.ARITMETICO);
				cbCruce.addItem(Cruce.SBX);
			}
			else
			{
				lbN.setVisible(false);
				tfN.setVisible(false);
				if(cbCruce.getItemCount() > 3)
				{
					cbCruce.removeItem(Cruce.ARITMETICO);
					cbCruce.removeItem(Cruce.SBX);
				}
			}
		}
		
	}

	@Override
	public void onAGSTerminado(Cromosoma elMejor) 
	{
		String s = "Genes:\n";
		if (elMejor instanceof CromosomaF3)
		{
			s += "  x = " + elMejor.getFenotipo()[0] + "\n";
			s += "  y = " + elMejor.getFenotipo()[1] + "\n";
		}
		else if (elMejor instanceof CromosomaF1)
		{
			s += "  x = " + elMejor.getFenotipo()[0] + "\n";
		}
		else
			for(int i=0; i < elMejor.getnVar(); ++i)
				s += "   x(" + (i + 1) + ") = " + elMejor.getFenotipo()[i] + "\n";
		
		s += "Maximo/Minimo obtenido:\n";
		s += "   f = " + elMejor.evalua() + "\n";
		taResultados.setText(s);
		
		plot.addLinePlot("Media Poblacion", Color.GREEN, ejeGeneracion, valorMedia);
		plot.addLinePlot("Mejor Generacion", Color.RED, ejeGeneracion, valorMejorGen);
		plot.addLinePlot("Mejor Absoluto", Color.BLUE, ejeGeneracion, valorElMejor);
	}

}
