
import java.util.Random;

public class Soldado {

	private String nombre;
	private int nivelAtaque;
	private int nivelDefensa;
	private int nivelVida;
	private int vidaActual;
	private int velocidad;
	private char actitud; // Tres posibles casos , puede ser char (D,O,H)
	private boolean vive;

	// atributos que concidero yo
	private int daño;
	private boolean atacar;
	private int fila;
	private int columna;
	private char simbolo;

	public Soldado(String nombre, int nivelVida, int fila, int columna, char simbolo) {
		this.nombre = nombre;
		this.nivelVida = nivelVida;
		this.fila = fila;
		this.columna = columna;
		this.simbolo = simbolo;
		this.actitud = 'd';
		vidaActual = (int) (Math.random() * 6);
	}

	public Soldado(String name, int nivelVida, int nivelAtaque, int nivelDefensa) {
		nombre = name;
		this.nivelVida = nivelVida;
		this.nivelAtaque = nivelAtaque;
		this.nivelDefensa = nivelDefensa;
		this.actitud = 'd';
	}

	public Soldado(String nombre, int nivelAtaque, int nivelDefensa, int nivelVida, boolean vive) {
		this.nombre = nombre;
		this.nivelAtaque = nivelAtaque;
		this.nivelDefensa = nivelDefensa;
		this.nivelVida = nivelVida;
		this.vive = vive;
		this.actitud = 'd';
	}

	public String toString() {
		return "Nombre: " + nombre + ", Nivel Vida: " + nivelVida + ", Actitud: " + actitud + ", Ejercito: " + simbolo
				+ ", Velocidad: " + velocidad;
	}

	public void atacar() {
		setActitud('o');
		avanzar(1);

		setAtacar(true);

	}

	public void defender() {
		setActitud('d');
		setVelocidad(0);

		setAtacar(false);
	}

	public void avanzar(int valor) { // podemos ponder un parametro
		velocidad += valor;
	}

	public void retroceder() {
		if (velocidad > 0) {
			setVelocidad(0);
			setActitud('d');
		} else {
			velocidad--; // retrocede con -1
		}
	}

	public void serAtacado(int ataque) { // pude contener parametro
		vidaActual -= ataque;
		if (vidaActual <= 0) {
			morir();
		}

	}

	public void hiur() {
		avanzar(2);
	}

	public void morir() {
		vive = false;
	}

	public void mover(int fila, int columna, int accion, int[][] tabla) {
		this.fila = fila;
		this.columna = columna;

		if (atacar) {

		} else {

		}

	}

	public static Soldado batallarGanador(Soldado s1, Soldado s2) {
		Random rd = new Random();
		int total = s1.getVidaActual() + s2.getVidaActual();
		Soldado[] todo = new Soldado[total];

		for (int i = 0; i < s1.getVidaActual(); i++) {
			todo[i] = s1;
		}
		for (int i = s1.getVidaActual(); i < todo.length; i++) {
			todo[i] = s2;
		}
		int ale = rd.nextInt(todo.length);

		System.out.println("El ganador es el ejercito " + todo[ale].getSimbolo());
		return todo[ale];

	}

	public static Soldado batallarPerdedor(Soldado s1, Soldado s2, Soldado ganador) {
		if (ganador == s1) {
			return s2;
		} else {
			return s1;
		}
	}

	// ---------------- get and set --------------------------

	public char getSimbolo() {
		return simbolo;
	}

	public int getNivelVida() {
		return nivelVida;
	}

	public void setNivelVida(int nivelVida) {
		this.nivelVida = nivelVida;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public void setActitud(char actitud) {
		this.actitud = actitud;
	}

	// use
	public void setAtacar(boolean atacar) {
		this.atacar = atacar;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public void setVidaActual(int vidaActual) {
		if (vidaActual <= nivelVida) {
			this.vidaActual = vidaActual;
		}

	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

}
