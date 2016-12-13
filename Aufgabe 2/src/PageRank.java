import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

	/**
	 * Diese Methode erstellt die Matrix A~ fuer das PageRank-Verfahren
	 * PARAMETER: 
	 * L: die Linkmatrix (s. Aufgabenblatt) 
	 * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
	 *      zufaellig irgendeine Seite zu besuchen
	 */
	public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
		int n = L.length;
		double[][] A = new double[n][n];
        for (int j = 0; j < n; j++) {
            // count outgoing links from j
            int links = 0;
            for (int i = 0; i < n; i++) {
                links += L[i][j];
            }

            // calculate a-tilde
            for (int i = 0; i < n; i++) {
                double a_ij = (double) 1 / links * L[i][j];
                A[i][j] = (1 - rho) * a_ij  + rho / n;
            }
        }
		return A;
	}

	/**
	 * Diese Methode berechnet die PageRanks der einzelnen Seiten,
	 * also das Gleichgewicht der Aufenthaltswahrscheinlichkeiten.
	 * (Entspricht dem p-Strich aus der Angabe)
	 * Die Ausgabe muss dazu noch normiert sein.
	 * PARAMETER:
	 * L: die Linkmatrix (s. Aufgabenblatt) 
	 * rho: Wahrscheinlichkeit, zufaellig irgendeine Seite zu besuchen
	 * ,anstatt einem Link zu folgen.
	 *      
	 */
	public static double[] rank(int[][] L, double rho) {
		//TODO: Diese Methode ist zu implementieren
		double[][] A = buildProbabilityMatrix(L, rho);

        // A-tilde - I
        for (int i = 0; i < A.length; i++) {
            A[i][i] -= 1;
        }

        // solve (A-tilde - I) * p = 0 with p != 0
        double[] p = Gauss.solveSing(A);

        // calc lambda
        double p_sum = 0;
        for (double p_j: p) {
            p_sum += p_j;
        }
        double lambda = (double) 1 / p_sum;

        // calc p-strich
        for (int j = 0; j < p.length; j++) {
            p[j] *= lambda;
        }
        return p;
	}

	/**
	 * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
	 * absteigendem PageRank. 
 	 * PARAMETER:
 	 * urls: Die URLs der betrachteten Seiten
 	 * L: die Linkmatrix (s. Aufgabenblatt) 
 	 * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
 	 *      zufaellig irgendeine Seite zu besuchen
	 */ 
	public static String[] getSortedURLs(String[] urls, int[][] L, double rho) {
		int n = L.length;

		double[] p = rank(L, rho);

		RankPair[] sortedPairs = new RankPair[n];
		for (int i = 0; i < n; i++) {
			sortedPairs[i] = new RankPair(urls[i], p[i]);
		}

		Arrays.sort(sortedPairs, new Comparator<RankPair>() {

			@Override
			public int compare(RankPair o1, RankPair o2) {
				return -Double.compare(o1.pr, o2.pr);
			}
		});

		String[] sortedUrls = new String[n];
		for (int i = 0; i < n; i++) {
			sortedUrls[i] = sortedPairs[i].url;
		}

		return sortedUrls;
	}

	/**
	 * Ein RankPair besteht aus einer URL und dem zugehoerigen Rang, und dient
	 * als Hilfsklasse zum Sortieren der Urls
	 */
	private static class RankPair {
		public String url;
		public double pr;

		public RankPair(String u, double p) {
			url = u;
			pr = p;
		}
	}
}
