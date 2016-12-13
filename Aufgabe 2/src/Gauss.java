

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		double[] x = new double[b.length];
		double buffer;
		for(int i = b.length - 1; i > -1; i--) {
			buffer = b[i];
			for(int j = b.length - 1; j > i; j--) {
				buffer -= R[i][j] * x[j];
			}
			x[i] = buffer/R[i][i];
		}
		return x;
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
		double[][] C = new double[A.length][A.length];
		for(int y = 0; y < A.length; y++) {
			for(int x = 0; x < A.length; x++) {
				C[y][x] = A[y][x];
			}
		}

		double[] d = b.clone();

		int rowCount = C.length;
		int columnCount = C[0].length;
		//System.out.println("Ursprüngliche Matrix:");
		//Util.printMatrix(A);
		//System.out.println("");

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
			double pivotD = d[pivot];
			d[pivot] = d[x];
			d[x] = pivotD;

			//Reihe von nachfolgenden Reihen multiplizieren und abziehen
			double pivotValue = C[x][x];
			for(int y = x+1; y < rowCount; y++) {
				double factor = C[y][x] / pivotValue;
				for(int xSub = x; xSub < columnCount; xSub++) {
					C[y][xSub] = C[y][xSub] - factor * C[x][xSub];
				}
				d[y] = d[y] - factor * d[x];
				//Util.printMatrix(C);
				//System.out.println("");
			}
		}

		double[] result = backSubst(C, d);
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
		double[][] C = new double[A.length][A.length];
		for(int y = 0; y < A.length; y++) {
			for(int x = 0; x < A.length; x++) {
				C[y][x] = A[y][x];
			}
		}

		int rowCount = C.length;
		int columnCount = C[0].length;

		boolean pivotZero = false;

		int x;
		for(x = 0; x < columnCount; x++) {
			int pivot = x;
			for(int y = 0; y < rowCount; y++) {
				if(Math.abs(C[y][x]) > Math.abs(C[pivot][x])) {
					pivot = y;
				}
			}
			if(C[pivot][x] == 0) {
				pivotZero = true;
				break;
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
					C[y][xSub] = C[y][xSub] - factor * C[x][xSub];
				}
			}
		}
		if(!pivotZero) {
			return new double[rowCount];
		}
		else {
			double[] mv = new double[x];
			for(int y = 0; y < x-1; y++) {
				mv[y] = -C[y][x];
			}
			double[] xVector = backSubst(C, mv);
			double[] result = new double[rowCount];
			for(int i = 0; i < xVector.length; i++) {
				result[i] = xVector[i];
			}
			result[xVector.length] = 1;
			for(int i = xVector.length+1; i < rowCount; i++) {
				result[i] = 0;
			}
			return result;
		}
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
