import java.util.*;

public class VideoJuego7 {

	public static void main(String[] args) {
		Soldado[][] tabla = new Soldado[10][10]; // Para imprimir, y saber si exisite ya un soldado en una posicion

		ArrayList<Soldado> ejercito1 = new ArrayList<Soldado>();
		ArrayList<Soldado> ejercito2 = new ArrayList<Soldado>();

		do {
			limpiar(ejercito1, ejercito2, tabla);
			llenarEjercito(ejercito1, 'A', tabla);
			llenarEjercito(ejercito2, 'B', tabla);

			mostrarTabla(tabla);

			char turno = 'A';

			do {
				System.out.println("Turno del ejercito " + turno);

				Soldado escogido = escogerSoldado(turno, tabla);
				Soldado ganador = escogido;
				Soldado perdedor = null;

				int[] valores = preguntarPosicion(turno, tabla); // fila, columna, (0=libre, 1 = lucha)

				if (valores[2] == 1) {// Batalla
					ganador = Soldado.batallarGanador(escogido, tabla[valores[0]][valores[1]]);
					perdedor = Soldado.batallarPerdedor(escogido, tabla[valores[0]][valores[1]], ganador);

				}

				int actual = ganador.getVidaActual();

				ganador.setVidaActual(actual + 1);

				actualizarTabla(escogido, ganador, valores, tabla);
				actualizarEjercitos(ejercito1, ejercito2, perdedor);

				mostrarTabla(tabla);

				turno = cambiarTurno(turno);

			} while (continuar(ejercito1, ejercito2));

			mostrarGanador(ejercito1, ejercito2);

		} while (preguntar());
	}

	public static void actualizarEjercitos(ArrayList<Soldado> ej1, ArrayList<Soldado> ej2, Soldado perdedor) {
		if (perdedor != null) {
			if (perdedor.getSimbolo() == 'A') {
				ej1.remove(perdedor);
			} else {
				ej2.remove(perdedor);
			}
		}
	}

	public static void actualizarTabla(Soldado escogido, Soldado ganador, int[] valores, Soldado[][] tabla) {
		tabla[escogido.getFila()][escogido.getColumna()] = null;
		tabla[valores[0]][valores[1]] = ganador;
		int fila = valores[0];
		int columna = valores[1];
		ganador.setFila(fila);
		ganador.setColumna(columna);
	}

	public static int[] preguntarPosicion(char turno, Soldado[][] tabla) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Ingrese la coordenada para mover al soldado: ");
		System.out.print("fila: ");
		int fila = sc.nextInt() - 1;
		System.out.print("Columna: ");
		int columna = sc.nextInt() - 1;

		if ((fila < 0 || fila > 9) || (columna < 0 || columna > 9)) {
			System.out.println("Posicion fuera del tablero");
		} else {
			if (tabla[fila][columna] == null) {
				int[] posiciones = { fila, columna, 0 };
				return posiciones;
			} else {
				if (tabla[fila][columna].getSimbolo() == turno) {
					System.out.println("la posicion esta ocupada por uno de tus soldados");
				} else {
					int[] posiciones = { fila, columna, 1 };
					return posiciones;
				}
			}
		}
		System.out.println("intentelo nuevamente");

		return preguntarPosicion(turno, tabla);
	}

	public static boolean preguntar() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nDesea simulr otra batalla (s/n) : ");
		String respuesta = sc.next();

		if (respuesta.equalsIgnoreCase("n")) {
			return false;
		}
		return true;
	}

	public static boolean continuar(ArrayList<Soldado> ej1, ArrayList<Soldado> ej2) {

		if (ej1.size() == 0 || ej2.size() == 0) {
			return false;
		}
		return true;

	}

	public static void mostrarGanador(ArrayList<Soldado> ej1, ArrayList<Soldado> ej2) {
		System.out.println("FIN DE LA BATALLA");
		if (ej1.size() == 0) {
			System.out.println("El ganador en el ejercito " + ej2.get(0).getSimbolo());
		} else {
			System.out.println("El ganador en el ejercito " + ej1.get(0).getSimbolo());
		}
	}

	public static Soldado escogerSoldado(char turno, Soldado[][] tabla) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Ingrese la coordenada del soldado que desea mover: ");
		System.out.print("fila: ");
		int fila = sc.nextInt() - 1;
		System.out.print("Columna: ");
		int columna = sc.nextInt() - 1;

		if ((fila < 0 || fila > 9) || (columna < 0 || columna > 9)) {
			System.out.println("Posicion fuera del tablero");
		} else {
			if (tabla[fila][columna] == null) {
				System.out.println("No hay un soldado en esa posicion");
			} else if (tabla[fila][columna].getSimbolo() == turno) {
				return tabla[fila][columna];
			} else {
				System.out.println("El soldado no pertenece a su ejercito");
			}
		}
		System.out.println("Intente nuevamente");
		return escogerSoldado(turno, tabla);
	}

	public static char cambiarTurno(char turno) {
		if (turno == 'A') {
			return 'B';
		}
		return 'A';
	}

	public static void llenarEjercito(ArrayList<Soldado> ejercito, char simbolo, Soldado[][] tabla) {
		Random rd = new Random();

		int fila, columna;
		int num = rd.nextInt(10) + 1;

		for (int i = 0; i < num; i++) {
			do {
				fila = rd.nextInt(10);
				columna = rd.nextInt(10);
			} while (!(tabla[fila][columna] == null)); // Vemos si hay un objeto acupando el lugar

			String name = "Soldado" + i;
			int vida = rd.nextInt(5) + 1;

			Soldado sol = new Soldado(name, vida, fila, columna, simbolo);
			ejercito.add(sol);
			tabla[fila][columna] = sol; // LLenamos en la tabla
		}
	}

	public static void mostrarTabla(Soldado[][] tabla) {
		System.out.println();

		for (int j = 1; j <= tabla.length; j++) {
			System.out.print(" " + j);
		}
		System.out.println();

		for (int j = 0; j < tabla.length; j++) {
			System.out.print(" _");
		}
		System.out.println();

		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				System.out.print("|");
				if (tabla[i][j] == null) {
					System.out.print("_");
				} else {
					System.out.print(tabla[i][j].getSimbolo());
				}
			}

			System.out.println("| " + (i + 1));
		}
	}

	public static void limpiar(ArrayList<Soldado> eje1, ArrayList<Soldado> eje2, Soldado[][] tabla) {
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				tabla[i][j] = null;
			}
		}
		eje1.clear();
		eje2.clear();
	}

	// Metodos de lab anteriores-----------------------------------------------
	public static Soldado mayorVida(ArrayList<Soldado> ejercito) {
		int mayor = 0;
		Soldado tem = null;
		for (Soldado sol : ejercito) {

			if (sol.getNivelVida() > mayor) {
				mayor = sol.getNivelVida();
				tem = sol;
			}
		}

		return tem;
	}

	public static double promedioVida(ArrayList<Soldado> ejercito) {
		int suma = 0;

		for (Soldado sol : ejercito) {
			suma += sol.getNivelVida();

		}

		return Math.round(((double) (suma) / ejercito.size()) * 10) / 10.0;

	}

	// Metodos de ordenamineto

	public static void ordenarBurbuja(ArrayList<Soldado> lista) {
		for (int i = 0; i < lista.size() - 1; i++) {
			for (int j = 0; j < lista.size() - 1; j++) {

				if (lista.get(j).getNivelVida() < lista.get(j + 1).getNivelVida()) {
					Soldado tmp = lista.get(j);
					lista.set(j, lista.get(j + 1));
					lista.set(j + 1, tmp);
				}
			}
		}

	}

	public static void ordenarInsercion(ArrayList<Soldado> lista) {
		for (int i = 0; i < lista.size(); i++) {
			int posicion = i;
			Soldado tem = lista.get(i);
			while (posicion > 0 && lista.get(posicion - 1).getNivelVida() < lista.get(posicion).getNivelVida()) {

				lista.set(posicion, lista.get(posicion - 1));
				lista.set(posicion - 1, tem);
				posicion--;
			}
		}
	}

	public static void mostrarEjercito(ArrayList<Soldado> ejercito) {
		for (Soldado item : ejercito) {
			System.out.println(item);
		}
	}

	// Ganador definido por en promedio de vida del ejercito
	public static void mostrarGanorBatalla(ArrayList<Soldado> ejercito1, ArrayList<Soldado> ejercito2) {

		double promedio1 = promedioVida(ejercito1);
		double promedio2 = promedioVida(ejercito2);
		if (promedio1 > promedio2) {
			System.out.println("\nEl ganador en el ejercito 1, con un promedio de vida de: " + promedio1);
		} else if (promedio1 < promedio2) {
			System.out.println("\nEl ganador en el ejercito 2, con un promedio de vida de: " + promedio2);
		} else {
			System.out.println("\nHay un empate entre los ejercitos, con un promedio de vida de: " + promedio1);
		}

	}

}
