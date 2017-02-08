package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren von Heun
 * 
 * @author braeckle
 * 
 */
public class Heun implements Einschrittverfahren {

	@Override
	/**
	 * {@inheritDoc} 
	 * Nutzen Sie dabei geschickt den Expliziten Euler.
	 */
	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {

		double[] euler = new double[y_k.length];
		double[] f = ode.auswerten(t, y_k);
		for(int i = 0; i < euler.length; i++) {
			euler[i] = y_k[i] + delta_t * f[i];
		}

		double[] result = new double[y_k.length];
		double[] f1 = ode.auswerten(t, y_k);
		double[] f2 = ode.auswerten(t + delta_t, euler);
		for(int i = 0; i < result.length; i++) {
			result[i] = y_k[i] + (delta_t / 2.0) * (f1[i] + f2[i]);
		}

		return result;
	}

}
