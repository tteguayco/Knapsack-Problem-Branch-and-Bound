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

public class Main {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Indique el fichero que contiene la especificación del problema");
			System.out.println("Dicho fichero debe encontrarse en el directorio 'data'");
			System.exit(-1);
			
		} else {
			double elapsedTime;
			Clock clock = new Clock();
			Problem problem = new Problem(args[0]);
			BranchAndBound bb = new BranchAndBound(problem);
			clock.start();
			bb.execute();
			elapsedTime = clock.getElapsedTime();
			System.out.println("Solución del problema");
			System.out.println(bb.getBestSolution());
			System.out.println("Tiempo de cómputo: " + elapsedTime + " ms");
			
			// Si se especifica fichero para volcado de la solución
			if (args.length > 1) {
				bb.getBestSolution().exportSolutionToFile(args[1]);
			}
		}
	}
}
