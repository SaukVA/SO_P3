package Practica3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

class ElementoFichero{
	private int proceso;
	private int instanteLlegada;
	private int tamanyo;
	private int tiempoEjecucion;
	public int getProceso() {
		return proceso;
	}
	public void setProceso(int proceso) {
		this.proceso = proceso;
	}
	public int getInstanteLlegada() {
		return instanteLlegada;
	}
	public void setInstanteLlegada(int instanteLlegada) {
		this.instanteLlegada = instanteLlegada;
	}
	public int getTamanyo() {
		return tamanyo;
	}
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}
	public int getTiempoEjecucion() {
		return tiempoEjecucion;
	}
	public void setTiempoEjecucion(int tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
	public ElementoFichero(int proceso, int instanteLlegada, int tamanyo,
			int tiempoEjecucion) {
		super();
		this.proceso = proceso;
		this.instanteLlegada = instanteLlegada;
		this.tamanyo = tamanyo;
		this.tiempoEjecucion = tiempoEjecucion;
	}
	public ElementoFichero(String linea){
		String [] campos = null;
		
		// separo en 4 trozos por el espacio.
		campos = linea.split(" ");
		proceso = Integer.parseInt(campos[0]);
		instanteLlegada = Integer.parseInt(campos[1]);
		tamanyo = Integer.parseInt(campos[2]);
		tiempoEjecucion = Integer.parseInt(campos[3]);
	}
}

public class Principal{
	// En un arrayList de ElementoFichero almaceno el contenido del fichero.
	// Cada elemento fichero contiene la informacion que habia en una linea.
	// proceso instante de llegada tama√±o reservado y tiempo que estara en memoria.
	public static ArrayList<ElementoFichero> em = new ArrayList<ElementoFichero>(); 
	public static int opcion; // opcion elegida en el menu.
	public static Memoria memoria = new Memoria();
	
	public static void leerFichero(String nombreFichero)
	throws FileNotFoundException{
		FileReader fr = null;
		BufferedReader br = null;
		String linea = null;
		
		fr = new FileReader(nombreFichero);
		br = new BufferedReader(fr);
		try{
			linea = br.readLine();
			// mientras no llegue al final del fichero y no encuentre linea vacia.
			while(linea != null && !linea.equals("")){
				// construyo un ElementoFichero que construyo a partir de la linea
				// que he leido del fichero.
				em.add(new ElementoFichero(linea)); 
				linea = br.readLine();
			}
		}
		catch(IOException e){ }
		finally{
			try{
				fr.close();
				br.close();
			}catch(IOException e){ }
		}
	}
	public static void menu(){
		System.out.println("**GESTI”N DE MEMORIA**");
		System.out.println("1. PRIMER HUECO");
		System.out.println("2. MEJOR HUECO");
		System.out.println("Opcion: ");
	}
	
	public static void main(String [] args){
		Scanner sc = null;
		
		sc = new Scanner(System.in); // para leer del teclado.
		do{
			menu();
			opcion = sc.nextInt(); // recojo un entero.
			if(opcion < 1 || opcion > 2)
				System.out.println("Error en la opcion.");
		}while(opcion < 1 || opcion > 2);
		if(args.length == 1){ // si me han puesto el argumento.
			try{
				//leerEntrada();
				leerFichero(args[0]); // leo el fichero.
				Ventana v = new Ventana();
				v.setVisible(true);
			}
			catch(FileNotFoundException e){
				System.out.println("Error al abrir el fichero");
			}
		}
	}
}
