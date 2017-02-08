package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren "Expliziter Euler"
 * 
 * @author braeckle
 * 
 */
public class ExpliziterEuler implements Einschrittverfahren {

	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
		// TODO: diese Methode ist zu implementieren

		double[] result = new double[y_k.length];
		double[] f = ode.auswerten(t, y_k);
		for(int i = 0; i < result.length; i++) {
			result[i] = y_k[i] + delta_t * f[i];
		}
		return result;
		//return Arrays.copyOf(y_k, y_k.length);
	}

}
