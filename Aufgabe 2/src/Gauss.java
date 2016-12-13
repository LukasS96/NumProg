

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		//TODO: Diese Methode ist zu implementieren
		return new double[2];
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
		//TODO: Diese Methode ist zu implementieren
		double[][] C = A.clone();
		double[] d = b.clone();

		int rowCount = C.length;
		int columnCount = C[0].length;
		printMatrix(A);

		for(int x = 0; x < columnCount-1; x++) {
			//Pivotisieren
			int pivot = x;
			for(int y = 0; y < rowCount; y++) {
				if(Math.abs(C[y][x]) > Math.abs(C[pivot][x])) {
					pivot = y;
				}
			}
			//Reihen tauschen
			double[] pivotRow = C[pivot];
			C[pivot] = C[x];
			C[x] = pivotRow;

			//Reihe von nachfolgenden Reihen multiplizieren und abziehen
			double pivotValue = C[x][x];
			for(int y = x+1; y < rowCount; y++) {
				double factor = C[y][x] / pivotValue;
				for(int xSub = x; xSub < columnCount; xSub++) {
					C[xSub][y] = C[xSub][y] - factor * C[xSub][x];
				}
				printMatrix(C);
			}
		}

		double[] result = backSubst(C, b);
		return result;
	}

	/**
	 * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
	 * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
	 * 
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): 
	 * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung 
	 *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 *  numerisch gleich 0 sind (d.h. <1E-10) 
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 *  loesen Sie Tx = -v durch Rueckwaertssubstitution 
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * 
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden. 
	 * PARAMETER: 
	 * A: Eine singulaere Matrix der Groesse n x n 
	 */
	public static double[] solveSing(double[][] A) {
		//TODO: Diese Methode ist zu implementieren
		return new double[2];
	}

	public static void printMatrix(double[][] A) {
		String output = "";
		for(int y = 0; y < A.length; y++) {
			output += "[";
			for(int x = 0; x < A[0].length-1; x++) {
				output += A[y][x] + ", ";
			}
			output += A[y][A[0].length-1];
			output += "]\n";
		}
		System.out.println(output);
	}

	/**
	 * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
	 * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
	 * Gauss-Loesung
	 */
	public static double[] matrixVectorMult(double[][] A, double[] x) {
		int n = A.length;
		int m = x.length;

		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			y[i] = 0;
			for (int j = 0; j < m; j++) {
				y[i] += A[i][j] * x[j];
			}
		}

		return y;
	}
}
