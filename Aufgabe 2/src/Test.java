public class Test {

	/*************************************************************/
	/* WICHTIG: Das bestehen dieser Tests sagt nahezu gar nichts */
	/* ueber das korrekte Funktionieren ihres Programms */
	/* aus. Es dient einzig und allein als Rahmen zur */
	/* leichteren Implementierung eigener Tests! */

	/*
	 * Nach Durchfuehrung der Tests startet der Crawler mit GUI. Mit ihm koennen
	 * neue LinkMatrizen erstellt werden.
	 */
	/*************************************************************/
	public static void main(String[] args) throws Exception {

		System.out.println("WICHTIG: Das bestehen dieser Tests sagt ALLES "
						+ "ueber das korrekte Funktionieren ihres Programms aus.\n"
						+ "Es dient nicht nur als Rahmen zurleichteren Implementierung eigener Tests!");

		boolean test_gauss = true;
		boolean test_pagerank = true;
		boolean test_crawler = true;

		double b[] = {1, 2, 3, 4, 5};
		double C[][] = {
			{1, 0, 3, 4, -2}, 
			{0, -1, 4, 5, 1},
			{0, 0, 4, -9, 2},
			{0, 0, 0, 1, 0},
			{0, 0, 0, 0, 2}
		};
		double B[][] = {
			{1, 2, 3, 4, 5}, 
			{5, 1, 2, 3, 4},
			{4, 5, 1, 2, 3},
			{3, 4, 5, 1, 2},
			{2, 3, 4, 5, 1}
		};
		double A[][] = {
			{3, 0, 3, 1, 0}, 
			{-4, 4, 4, -7, 1},
			{5, 0, 4, 9, 2},
			{0, 0, 4, -9, 2},
			{0, -6, 6, 0, 2}
		};
		double Ax[];
		double Bx[];
		double Cx[];
		double x[];
		double nullvektor[] = new double[5];

		/******************************/
		/* Test der Klasse Gauss */
		/******************************/
		if (test_gauss) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse Gauss");

			System.out.println("  Test der Methode backSubst");
			
			x = Gauss.backSubst(C, b);
			Cx = Gauss.matrixVectorMult(C, x);
			if (Util.vectorCompare(Cx, b)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis x:");
				Util.printVector(x);
				System.out.println("            Ergebnis von C * x:");
				Util.printVector(Cx);
				System.out.println("            soll geich b sein:");
				Util.printVector(b);
			}

			System.out.println("  Test der Methode solve mit oberer Dreiecksmatrix C");
			x = Gauss.solve(C, b);
			Cx = Gauss.matrixVectorMult(C, x);
			if (Util.vectorCompare(Cx, b)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis x:");
				Util.printVector(x);
				System.out.println("            Ergebnis von C * x:");
				Util.printVector(Cx);
				System.out.println("            soll geich b sein:");
				Util.printVector(b);
			}

			System.out.println("  Test der Methode solve mit komischer Matrix B");
			x = Gauss.solve(B, b);
			Bx = Gauss.matrixVectorMult(B, x);
			if (Util.vectorCompare(Bx, b)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis x:");
				Util.printVector(x);
				System.out.println("            Ergebnis von B * x:");
				Util.printVector(Bx);
				System.out.println("            soll geich b sein:");
				Util.printVector(b);
			}

			System.out.println("  Test der Methode solve mit beliebiger Matrix A");
			x = Gauss.solve(A, b);
			Ax = Gauss.matrixVectorMult(A, x);
			if (Util.vectorCompare(Ax, b)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis x:");
				Util.printVector(x);
				System.out.println("            Ergebnis von A * x:");
				Util.printVector(Ax);
				System.out.println("            soll geich b sein:");
				Util.printVector(b);
			}

			System.out.println("  Test der Methode solveSing mit beliebiger Matrix A");
			x = Gauss.solveSing(A);
			Ax = Gauss.matrixVectorMult(A, x);
			/*double lambda = xA[0] / x[0];
			for (int i = 0; i < x.length; i++) {
				x[i] *= lambda;
			}*/
			if (Util.vectorCompare(Ax, nullvektor)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis x:");
				Util.printVector(x);
				System.out.println("            Ergebnis von A * x:");
				Util.printVector(Ax);
				System.out.println("            soll geich Nullvektor sein:");
				Util.printVector(nullvektor);
			}
		}

		/******************************/
		/* Test der Klasse PageRank */
		/******************************/
		if (test_pagerank) {
			System.out.println("-----------------------------------------");
			System.out.println("Test der Klasse PageRank");

			LinkMatrix lm = new LinkMatrix();
			/*
			 * Es koennte sein, dass in Eclipse die Datei nicht gefunden wird.
			 * Sie muessen entweder den gesamten absoluten Pfad angeben oder die
			 * Umgebung entsprechend einrichten.
			 */
			lm.read("src/webseiten/irgendwo.txt");

			System.out.println("  Test der Methode buildMatrix");

			A = PageRank.buildProbabilityMatrix(lm.L, 0.15);
			double A0[][] = { { 0.5, 0.5 }, { 0.5, 0.5 } };
			if (Util.matrixCompare(A, A0)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis:");
				Util.printMatrix(A);
				System.out.println("            richtiges Ergebnis:");
				Util.printMatrix(A0);
			}

			System.out.println(" Test der Methode rank");
			String r[] = PageRank.getSortedURLs(lm.urls, lm.L, 0.15);

			String r0[] = { "http://www.irgendwo.de", "http://www.nirgendwo.de" };
			String r1[] = { "http://www.nirgendwo.de", "http://www.irgendwo.de" };
			if (Util.rankingCompare(r, r0)) {
				System.out.println("    Richtiges Ergebnis");
			} else if (Util.rankingCompare(r, r1)) {
				System.out.println("    Richtiges Ergebnis");
			} else {
				System.out.println("    FEHLER: falsches Ergebnis:");
				Util.printStringArray(r);
				System.out.println("            richtiges Ergebnis:");
				Util.printStringArray(r0);
			}
		}

		if (test_crawler) {
			(new GUI()).setVisible(true);
		}
	}
}
