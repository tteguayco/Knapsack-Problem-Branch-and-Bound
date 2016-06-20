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

public class TreeNode {
	private Problem problem;
	private Solution partialSolution;
	private double upperBound;
	private int k;
	
	public TreeNode(Problem problem) {
		this.problem = problem;
		this.partialSolution = new Solution(problem);
		k = -1;
	}
	
	/**
	 * Calcula la cota superior para este TreeNode
	 * Referencia: http://www.etsii.urjc.es/~edi/Sistemas/documentos/Tema4.3.4_RamificacionYPoda.pdf
	 */
	public void calculateUpperBound() {
		int bk = 0;
		int weightSum = 0; 
		double result = 0;
		
		if (getK() < getProblem().getN() - 1) {
			// Suma de los valores de los objetos incluidos en la mochila hasta el momento
			for (int i = 0; i <= getK(); i++) {
				bk += getPartialSolution().getIncludedObjects()[i] * getProblem().getValues().get(i);
			}
			
			for (int i = 0; i <= getK(); i++) {
				weightSum += getPartialSolution().getIncludedObjects()[i] * getProblem().getWeights().get(i);
			}
			
			result = (getProblem().getW() - weightSum);
			result *= ((double) getProblem().getValues().get(getK() + 1) / 
					(double) getProblem().getWeights().get(getK() + 1));
			result += bk;
			
		} else {
			result = getPartialSolution().totalValue();
		}
		
		setUpperBound(result);
	}

	public String toString() {
		String result = "";
		result += "K = " + getK() + "\n";
		result += getPartialSolution() + "\n";
		result += "UB: " + getUpperBound() + "\n";
		return result;
	}
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Solution getPartialSolution() {
		return partialSolution;
	}

	public void setPartialSolution(Solution partialSolution) {
		this.partialSolution = partialSolution;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
}
