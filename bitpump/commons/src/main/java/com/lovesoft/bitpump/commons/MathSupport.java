package com.lovesoft.bitpump.commons;

import com.google.common.base.Preconditions;

/**
 * 
 * @author Patryk Kaluzny Lovesoft Jan 2, 2017 in Milky Way Galaxy
 *
 */

public class MathSupport {

	private MathSupport () {
		// Well, you know why this fella is a private one?
	}

	public static double calculatePercentageOfXisY(double x, double y) {
		Preconditions.checkArgument(isNotZero(x));
		return (y * 100.0d) / x;
	}

	public static boolean isNotZero(double x) {
		return Math.abs(x) > 0.00000000001;
	}

	public static boolean isZero(double x) {
		return !isNotZero(x);
	}

	public static double calculateValueOfXPercentFromY(double x, double y) {
		return (x * y) / 100.0;
	}
}
