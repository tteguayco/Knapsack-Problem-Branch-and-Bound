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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Representación de la solución:
 * Se utilizará un array de enteros de tal forma 
 * que un valor de 1 en la posición i indica
 * que el objeto i se incluye en la mochila. 0 en 
 * caso contrario.
 * @author teguayco
 */
public class Solution {
	private static final String DEFAULT_PATH_FILES = "./data/";
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private Problem problem;
	private int[] includedObjects; 
	
	public Solution(Problem problem) {
		this.problem = problem;
		includedObjects = new int[problem.getN()];
	}
	
	public Solution(Solution aSolution) {
		problem = new Problem(aSolution.getProblem());
		includedObjects = new int[getProblem().getN()];
		for (int i = 0; i < includedObjects.length; i++) {
			includedObjects[i] = aSolution.getIncludedObjects()[i];
		}
	}
	
	/**
	 * Incluye al objeto ObjectIndex en la mochila.
	 * @param i
	 */
	public void includeObject(int ObjectIndex) {
		getIncludedObjects()[ObjectIndex] = 1;
	}
	
	/**
	 * Devuelve true si el peso de la mochila supera el de la restricción
	 */
	public boolean isOverWeight() {
		if (totalWeight() > getProblem().getW()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Extrae el objeto ObjectIndex de la mochila.
	 * @param ObjectIndex
	 */
	public void extractObject(int ObjectIndex) {
		getIncludedObjects()[ObjectIndex] = 0;
	}

	/**
	 * True si el objeto ObjectIndex está incluido 
	 * en la mochila.
	 * @param ObjectIndex
	 * @return
	 */
	public boolean isIncluded(int ObjectIndex) {
		if (getIncludedObjects()[ObjectIndex] == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Devuelve la suma de los valores de los objetos incluidos
	 * en la mochila
	 * @return
	 */
	public int totalValue() {
		int totalValue = 0;
		for (int i = 0; i < getProblem().getN(); i++) {
			if (isIncluded(i)) {
				totalValue += getProblem().getValues().get(i);
			}
		}
		return totalValue;
	}
	
	/**
	 * Devuelve la suma de los pesos de los objetos incluidos
	 * en la mochila
	 * @return
	 */
	public int totalWeight() {
		int totalWeight = 0;
		for (int i = 0; i < getProblem().getN(); i++) {
			if (isIncluded(i)) {
				totalWeight += getProblem().getWeights().get(i);
			}
		}
		return totalWeight;
	}
	
	/**
	 * Exporta la solución con el mismo formato que el 
	 * fichero de especificación de problema a un fichero 
	 * que se crea en el directorio data 
	 * @param fileName
	 */
	public void exportSolutionToFile(String fileName) {
		if (!fileName.contains(DEFAULT_PATH_FILES)) {
			fileName = DEFAULT_PATH_FILES + fileName;
		}
		
		try {
			PrintWriter writer = new PrintWriter(fileName, DEFAULT_ENCODING);
			writer.println(";; Solución generada por mediante el algoritmo de B&B");
			writer.println(";; para el problema de la mochila 0-1 (knapsack problem)");
			writer.print(this);
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < getIncludedObjects().length; i++) {
			result += getIncludedObjects()[i] + " | ";
		}
		
		result += "\nValue: " + totalValue();
		result += "\nWeight: " + totalWeight();
		return result;
	}
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public int[] getIncludedObjects() {
		return includedObjects;
	}

	public void setIncludedObjects(int[] includedObjects) {
		this.includedObjects = includedObjects;
	}
}
