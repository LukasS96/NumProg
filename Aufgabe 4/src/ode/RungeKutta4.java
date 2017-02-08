package ode;

import java.util.Arrays;

/**
 * Der klassische Runge-Kutta der Ordnung 4
 * 
 * @author braeckle
 * 
 */
public class RungeKutta4 implements Einschrittverfahren {

	@Override
	/**
	 * {@inheritDoc}
	 * Bei der Umsetzung koennen die Methoden addVectors und multScalar benutzt werden.
	 */
	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {

		double[] f = ode.auswerten(t, y_k);
		double[] k1 = multScalar(f, delta_t);

		double[] k2 = multScalar(ode.auswerten(t + delta_t / 2.0, addVectors(y_k, multScalar(k1, 0.5))), delta_t);

		double[] k3 = multScalar(ode.auswerten(t + delta_t / 2.0, addVectors(y_k, multScalar(k2, 0.5))), delta_t);

		double[] k4 = multScalar(ode.auswerten(t + delta_t, addVectors(y_k, k3)), delta_t);

		double[] result = addVectors(y_k, multScalar(addVectors(k4, addVectors(k1, addVectors(multScalar(k2, 2.0), multScalar(k3, 2.0)))), 1/6.0));

		return result;
	}

	/**
	 * addiert die zwei Vektoren a und b
	 */
	private double[] addVectors(double[] a, double[] b) {
		double[] erg = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			erg[i] = a[i] + b[i];
		}
		return erg;
	}

	/**
	 * multipliziert den Skalar scalar auf den Vektor a
	 */
	private double[] multScalar(double[] a, double scalar) {
		double[] erg = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			erg[i] = scalar * a[i];
		}
		return erg;
	}

}
