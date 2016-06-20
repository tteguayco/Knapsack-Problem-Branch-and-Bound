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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BranchAndBound {
	private Problem problem;
	private double lowerBound;
	private ArrayList<TreeNode> activeNodes;
	private Solution bestSolution;
	
	public BranchAndBound(Problem problem) {
		this.problem = problem;
		this.lowerBound = -1;
		activeNodes = new ArrayList<TreeNode>();
		bestSolution = null;
	}
	
	/**
	 * Se van insertando objetos en la mochila hasta 
	 * que se llegue a un objeto que no quepa.
	 */
	private void calculateLowerBound() {
		Solution sol = new Solution(getProblem());
		
		for (int i = 0; i < getProblem().getN(); i++) {
			sol.includeObject(i);
			if (sol.totalWeight() > getProblem().getW()) {
				sol.extractObject(i);
				break;
			}
		}
		
		setLowerBound(sol.totalValue());
	}
	
	/**
	 * Ejecutar algoritmo B&B
	 */
	public void execute() {
		TreeNode root = new TreeNode(getProblem());
		getActiveNodes().add(root);
		double maxUB;
		TreeNode nodeWithBiggestUB = new TreeNode(getProblem());
		
		calculateLowerBound();
		while (getActiveNodes().size() != 0) {
			// Ramificar el nodo más prometedor: aquel con mayor cota superior
			maxUB = -1;
			for (int i = 0; i < getActiveNodes().size(); i++) {
				if (getActiveNodes().get(i).getUpperBound() > maxUB) {
					maxUB = getActiveNodes().get(i).getUpperBound();
					nodeWithBiggestUB = getActiveNodes().get(i);
				}
			}
			
			branch(nodeWithBiggestUB);
		}
	}
	
	/**
	 * Ramifica el nodo node
	 * @param node
	 */
	public void branch(TreeNode node) {
		// Bucle con dos iteraciones, una para ramificar la opción de incluir
		// el siguiente objeto; otra para ramificar la opción de no incluirlo
		for (int i = 0; i < 2; i++) {
			TreeNode child = new TreeNode(getProblem());
			child.setK(node.getK() + 1);
			child.setPartialSolution(new Solution(node.getPartialSolution()));
			if (i % 2 == 0 && child.getK() < getProblem().getN()) 
				child.getPartialSolution().includeObject(child.getK());
			
			child.calculateUpperBound();
			if (child.getUpperBound() > getLowerBound()) {
				getActiveNodes().add(child);
				if (child.getK() == getProblem().getN() - 1) {
					if (updateSolution(child.getPartialSolution())) {
						setLowerBound(child.getUpperBound());
						// Podar los nodos activos cuya UB sea menor o igual que la nueva LB
						for (int j = 0; j < getActiveNodes().size(); j++) {
							if (getActiveNodes().get(j).getUpperBound() <= getLowerBound()) {
								getActiveNodes().remove(getActiveNodes().get(j));
							}
						}
					}
					
					getActiveNodes().remove(child);
				}
			}
		}
		getActiveNodes().remove(node);
	}
	
	/**
	 * Se actualiza la mejor solución.
	 * Se devuelve true si se actualizó.
	 */
	public boolean updateSolution(Solution sol) {
		if (getBestSolution() == null  && !sol.isOverWeight()) {
			setBestSolution(new Solution(sol));
			return true;
		} else if (getBestSolution() != null) {
			if ((sol.totalValue() > getBestSolution().totalValue()) && !sol.isOverWeight()) {
				setBestSolution(new Solution(sol));
				return true;
			}
		}
		return false;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public ArrayList<TreeNode> getActiveNodes() {
		return activeNodes;
	}

	public void setActiveNodes(ArrayList<TreeNode> activeNodes) {
		this.activeNodes = activeNodes;
	}

	public Solution getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Solution bestSolution) {
		this.bestSolution = bestSolution;
	}
}
