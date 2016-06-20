/**
 * Problema de la mochila 0-1
 * mediante Branch&Bound
 * 
 * @author Teguayco Gutiérrez González
 * @date 18/06/2016
 * 
 * Grado en Ingeniería Informática
 * Escuela Técnica Superior de Ingeniería y Tecnología
 * Universidad de La Laguna
 */

package knapsackproblem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Problem {
	private static final String DEFAULT_PATH_FILES = "./data/";
	private static final int N_OR_W_SIZE = 1;
	private static final int TUPLA_SIZE = 3;
	
	private int n;							// Número de objetos
	private int w;							// Capacidad total de la mochila
	private ArrayList<Integer> values;		// Valor de los objetos
	private ArrayList<Integer> weights;		// Pesos de los objetos
	
	public Problem(String fileName) {
		n = -1;
		w = -1;
		values = new ArrayList<Integer>();
		weights = new ArrayList<Integer>();
		readFromFile(fileName);
	}
	
	public Problem(Problem aProblem) {
		n = aProblem.getN();
		w = aProblem.getW();
		values = new ArrayList<Integer>(aProblem.getValues());
		weights = new ArrayList<Integer>(aProblem.getWeights());
	}
	
	private void readFromFile(String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		String line;
		String[] tokens;
		
		if (!fileName.contains(DEFAULT_PATH_FILES)) {
			fileName = DEFAULT_PATH_FILES + fileName;
		}
		
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			line = br.readLine();
			
			while (line != null) {
				// Leemos si la línea no es un comentario (;;)
				if (!line.matches(";;;*.*") && !line.isEmpty()) {
					tokens = line.split("\\s+");
					if (tokens.length == N_OR_W_SIZE) {
						setN(Integer.parseInt(tokens[0]));
						line = br.readLine();
						setW(Integer.parseInt(line));
						
					} else if (tokens.length == TUPLA_SIZE) {
						getValues().add(Integer.parseInt(tokens[1]));
						getWeights().add(Integer.parseInt(tokens[2]));
					}
				}
				
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the problem file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO exception was thrown");
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String result = "";
		result += getN() + "\n";
		result += getW() + "\n";
		result += getValues() + "\n";
		result += getWeights() + "\n";
		return result;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public ArrayList<Integer> getValues() {
		return values;
	}

	public void setValues(ArrayList<Integer> values) {
		this.values = values;
	}

	public ArrayList<Integer> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Integer> weights) {
		this.weights = weights;
	}
}
