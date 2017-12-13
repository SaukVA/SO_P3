package Practica3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Ventana extends Frame implements ActionListener{
	private int instante = 0;
	private Canvas canvas;
	private Button next, salir;	
	private PrintWriter pw;
	public Ventana() {
		///////////////////////////////////
		// fichero donde escribir./////////
		try {
			pw = new PrintWriter("particiones.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		////////////////////////////////////
		this.setSize(600,600);
		this.setLayout(new BorderLayout());
		canvas = new Canvas();
		Panel p = new Panel();
		next = new Button("Start");
		salir = new Button("Exit");
		this.add(canvas, BorderLayout.CENTER);
		p.setLayout(new GridLayout(1,2));
		p.add(next);
		p.add(salir);
		this.add(p, BorderLayout.SOUTH);
		next.addActionListener(this);
		salir.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == salir){
			System.out.println("Se presiono salir");
			System.exit(0);
		}
		else if(e.getSource() == next){
			if(instante == 0){
				next.setLabel("Next");
				Graphics gr = canvas.getGraphics();
				for(int i = 0; i < 20; i++){
					gr.drawRect(20*i, 30*instante, 15, 20);
				}
				instante = 1;
			}
			else{
				boolean insertado;
				System.out.println("Se presiono next");
				pw.write(instante + " " + Principal.memoria.toString());
				// cada vez que le doy al next es un instante nuevo,
				// recorro todo lo que he leido del fichero y lo intento meter en la memoria.
				for(int i = 0; i < Principal.em.size(); i++){
					if(Principal.em.get(i).getInstanteLlegada() <= instante){
						// quitais el if..else y llamais mejorHueco
						if(Principal.opcion == 1){
							// insertar mejor hueco
							insertado = Principal.memoria.primerHueco(Principal.em.get(i));
						}
						else{
							// insertar primer hueco.
							insertado = Principal.memoria.mejorHueco(Principal.em.get(i));
						}
						// si lo consigo meter, lo quito de la lista de entrada.
						if(insertado){
							// si lo consigo insertar lo quito de los elementos que he leido del fichero.
							Principal.em.remove(i);
							i--;
						}
					}
				}
				Principal.memoria.decrementar();
				//////////////////////
				System.out.println(Principal.memoria.toString());
				//////////////////////
				pw.write(Principal.memoria.toString());
				pw.write("\n");
				pw.flush();
				//////////////////////
				
				String mapa = Principal.memoria.toMapa();
				System.out.println(mapa);
				Graphics gr = canvas.getGraphics();
				for(int i = 0; i < 20; i++){
					if(mapa.charAt(i) == '0'){
						gr.setColor(Color.BLACK);
						gr.drawRect(20*i, 30*instante, 15, 20);
					}
					else{
						Color [] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.orange, Color.RED,
								Color.WHITE, Color.YELLOW, Color.CYAN, Color.PINK};
						gr.setColor(colores[mapa.charAt(i) - '0']);
						gr.fillRect(20*i, 30*instante, 15, 20);
						gr.setColor(Color.BLACK);
						gr.drawRect(20*i, 30*instante, 15, 20);
					}
				}
				instante++;
			}
		}
		else{
			System.out.println("Se presiono enter en el texto");
		}
	}
}

