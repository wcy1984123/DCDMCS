

/*
 * Class:        JohnsonSLDist
 * Description:  Johnson S_L distribution
 * Environment:  Java
 * Software:     SSJ
 * Copyright (C) 2001  Pierre L'Ecuyer and Universite de Montreal
 * Organization: DIRO, Universite de Montreal
 * @author       Richard Simard
 * @since        July 2012

 * SSJ is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License (GPL) as published by the
 * Free Software Foundation, either version 3 of the License, or
 * any later version.

 * SSJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * A copy of the GNU General Public License is available at
   <a href="http://www.gnu.org/licenses">GPL licence site</a>.
 */

package umontreal.iro.lecuyer.probdist;
import umontreal.iro.lecuyer.util.*;
import umontreal.iro.lecuyer.functions.MathFunction;
import optimization.*;


/**
 * Extends the class {@link ContinuousDistribution} for
 * the <EM>Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN></EM> distribution.
 * It  has shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and 
 * <SPAN CLASS="MATH"><I>&#948;</I> &gt; 0</SPAN>, location parameter
 * <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, and scale parameter 
 * <SPAN CLASS="MATH"><I>&#955;</I> &gt; 0</SPAN>.
 * Denoting 
 * <SPAN CLASS="MATH"><I>t</I> = (<I>x</I> - <I>&#958;</I>)/<I>&#955;</I></SPAN> and 
 * <SPAN CLASS="MATH"><I>z</I> = <I>&#947;</I> + <I>&#948;</I>ln(<I>t</I>)</SPAN>,
 * the distribution has density
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>f</I> (<I>x</I>) = <I>&#948;e</I><SUP>-z<SUP>2</SUP>/2</SUP>/(<I>&#955;t</I>(2&pi;)<SUP>1/2</SUP>),&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for <I>&#958;</I> &lt; <I>x</I> &lt; &#8734;,
 * </DIV><P></P>
 * and distribution function
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>F</I>(<I>x</I>) = <I>&#934;</I>(<I>z</I>),&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for <I>&#958;</I> &lt; <I>x</I> &lt; &#8734;,
 * </DIV><P></P>
 * where <SPAN CLASS="MATH"><I>&#934;</I></SPAN> is the standard normal distribution function.
 * The inverse distribution function is
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>F</I><SUP>-1</SUP>(<I>u</I>) = <I>&#958;</I> + <I>&#955;e</I><SUP>v(u)</SUP>,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for 0&nbsp;&lt;=&nbsp;<I>u</I>&nbsp;&lt;=&nbsp;1,
 * </DIV><P></P>
 * where
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>v</I>(<I>u</I>) = [<I>&#934;</I><SUP>-1</SUP>(<I>u</I>) - <I>&#947;</I>]/<I>&#948;</I>.
 * </DIV><P></P>
 * 
 * <P>
 * Without loss of generality, one may choose 
 * <SPAN CLASS="MATH"><I>&#947;</I> = 0</SPAN> or <SPAN CLASS="MATH"><I>&#955;</I> = 1</SPAN>.
 * 
 */
public class JohnsonSLDist extends JohnsonSystem {

   private static class Function implements MathFunction
   {
      // To find value of t > 0 in (Johnson 1949, eq. 16)
      protected double a;

      public Function (double sb1) {
         a = sb1;
      }

      public double evaluate (double t) {
         return (t*t*t - 3*t - a);
      }
   }


   private static double[] initPar (double[] x, int n, double xmin)
   {
      // Use moments to estimate initial values of params as input to MLE
      // (Johnson 1949, Biometrika 36, p. 149)
      int j;
      double sum = 0.0;
      for (j = 0; j < n; j++) {
         sum += x[j];
      }
      double mean = sum / n;

      double v;
      double sum3 = 0.0;
      sum = 0;
      for (j = 0; j < n; j++) {
         v = x[j] - mean;
         sum += v*v;
         sum3 += v*v*v;
      }
      double m2 = sum / n;
      double m3 = sum3 / n;

      v = m3 / Math.pow (m2, 1.5);
      Function f = new Function (v);
      double t0 = 0;
      double tlim = Math.cbrt (v);
      if (tlim <= 0) {
         t0 = tlim;
         tlim = 10;
      }
      double t = RootFinder.brentDekker (t0, tlim, f, 1e-5);
      if (t <= 0)
         throw new UnsupportedOperationException("t <= 0;   no MLE");
      double xi = mean - Math.sqrt(m2 / t);
      if (xi >= xmin)
         // throw new UnsupportedOperationException("xi >= xmin;   no MLE");
         xi = xmin - 1.0e-1;
      v = 1 + m2 / ((mean - xi)*(mean - xi));
      double delta = 1.0 / Math.sqrt((Math.log(v)));
      v = Math.sqrt(v);
      double lambda = (mean - xi) / v;
      double[] param = new double[3];
      param[0] = delta;
      param[1] = xi;
      param[2] = lambda;
      return param;
   }


   private static class Optim implements Uncmin_methods
   {
      // minimizes the loglikelihood function
      private int n;
      private double[] X;
      private double xmin;      // min{X_j}
      private static final double BARRIER = 1.0e100;

      public Optim (double[] X, int n, double xmin) {
         this.n = n;
         this.X = X;
         this.xmin = xmin;
      }

      public double f_to_minimize (double[] par) {
         // par = [0, delta, xi, lambda]
         // arrays in Uncmin starts at index 1; par[0] is unused
         double delta  = par[1];
         double xi     = par[2];
         double lambda = par[3];
         if (delta <= 0.0 || lambda <= 0.0)         // barrier at 0
            return BARRIER;
         if (xi >= xmin)
            return BARRIER;

         double loglam = Math.log(lambda);
         double v, z;
         double sumv = 0.0;
         double sumz = 0.0;
         for (int j = 0; j < n; j++) {
            v = Math.log (X[j] - xi);
            z = delta * (v - loglam);
            sumv += v;
            sumz += z*z;
         }

         return sumv + sumz / 2.0 - n *Math.log(delta);
      }

      public void gradient (double[] x, double[] g)
      {
      }

      public void hessian (double[] x, double[][] h)
      {
      }
   }



   /**
    * Same as {@link #JohnsonSLDist(double,double,double,double) JohnsonSLDist}
    *     <TT>(gamma, delta, 0, 1)</TT>.
    * 
    */
   public JohnsonSLDist (double gamma, double delta) {
      this (gamma, delta, 0, 1);
   }


   /**
    * Constructs a <TT>JohnsonSLDist</TT> object
    *    with shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and <SPAN CLASS="MATH"><I>&#948;</I></SPAN>,
    *    location parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, and scale parameter <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    */
   public JohnsonSLDist (double gamma, double delta,
                         double xi, double lambda) {
      super (gamma, delta, xi, lambda);
      setLastParams(xi);
   }


   private void setLastParams(double xi) {
      supportA = xi;
   }

   public double density (double x) {
      return density (gamma, delta, xi, lambda, x);
   }

   public double cdf (double x) {
      return cdf (gamma, delta, xi, lambda, x);
   }

   public double barF (double x) {
      return barF (gamma, delta, xi, lambda, x);
   }

   public double inverseF (double u){
      return inverseF (gamma, delta, xi, lambda, u);
   }

   public double getMean() {
      return JohnsonSLDist.getMean (gamma, delta, xi, lambda);
   }

   public double getVariance() {
      return JohnsonSLDist.getVariance (gamma, delta, xi, lambda);
   }

   public double getStandardDeviation() {
      return JohnsonSLDist.getStandardDeviation (gamma, delta, xi, lambda);
   }


   /**
    * Returns the density function <SPAN CLASS="MATH"><I>f</I> (<I>x</I>)</SPAN>.
    * 
    */
   public static double density (double gamma, double delta,
                                 double xi, double lambda, double x) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");

      if (x <= xi)
         return 0;
      double y = (x - xi)/lambda;
      double z = gamma + delta*Math.log (y);
      if (z >= 1.0e10)
         return 0;
      return delta * Math.exp (-z*z/2.0)/(lambda*y*Math.sqrt (2.0*Math.PI));
   }


   /**
    * Returns the  distribution function <SPAN CLASS="MATH"><I>F</I>(<I>x</I>)</SPAN>.
    * 
    */
   public static double cdf (double gamma, double delta,
                             double xi, double lambda, double x) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");

      if (x <= xi)
         return 0.0;
      double y = (x - xi)/lambda;
      double z = gamma + delta*Math.log (y);
      return NormalDist.cdf01 (z);
   }


   /**
    * Returns the complementary distribution function <SPAN CLASS="MATH">1 - <I>F</I>(<I>x</I>)</SPAN>.
    * 
    */
   public static double barF (double gamma, double delta,
                              double xi, double lambda, double x) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");

      if (x <= xi)
         return 1.0;
      double y = (x - xi)/lambda;
      double z = gamma + delta*Math.log (y);
      return NormalDist.barF01 (z);
   }


   /**
    * Returns the inverse distribution function <SPAN CLASS="MATH"><I>F</I><SUP>-1</SUP>(<I>u</I>)</SPAN>.
    * 
    */
   public static double inverseF (double gamma, double delta,
                                  double xi, double lambda, double u) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");
      if (u > 1.0 || u < 0.0)
          throw new IllegalArgumentException ("u not in [0,1]");

      if (u >= 1.0)
         return Double.POSITIVE_INFINITY;
      if (u <= 0.0)
         return xi;

      double z = NormalDist.inverseF01 (u);
      double t = (z - gamma)/delta;
      if (t >= Num.DBL_MAX_EXP*Num.LN2)
         return Double.POSITIVE_INFINITY;
      if (t <= Num.DBL_MIN_EXP*Num.LN2)
         return xi;

      return xi + lambda * Math.exp(t);
   }


   /**
    * Estimates the parameters <SPAN CLASS="MATH">(<I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I>)</SPAN> of the
    *    <SPAN  CLASS="textit">Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN></SPAN> distribution using the maximum likelihood method,
    *    from the <SPAN CLASS="MATH"><I>n</I></SPAN> observations <SPAN CLASS="MATH"><I>x</I>[<I>i</I>]</SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;, <I>n</I> - 1</SPAN>.
    *    The estimates are returned in a 4-element array in the order
    *    [0, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I></SPAN>] (with <SPAN CLASS="MATH"><I>&#947;</I></SPAN> always set to 0).
    * 
    * @param x the list of observations to use to evaluate parameters
    * 
    *    @param n the number of observations to use to evaluate parameters
    * 
    *    @return returns the parameters [0, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I></SPAN>]
    * 
    */
   public static double[] getMLE (double[] x, int n) {
      if (n <= 0)
         throw new IllegalArgumentException ("n <= 0");

      int j;
      double xmin = Double.MAX_VALUE;
      for (j = 0; j < n; j++) {
         if (x[j] < xmin)
            xmin = x[j];
      }
      double[] paramIn = new double[3];
      paramIn = initPar(x, n, xmin);
      double[] paramOpt = new double[4];
      for (int i = 0; i < 3; i++)
         paramOpt[i+1] = paramIn[i];

      Optim system = new Optim (x, n, xmin);

      double[] xpls = new double[4];
      double[] fpls = new double[4];
      double[] gpls = new double[4];
      int[] itrcmd = new int[2];
      double[][] a = new double[4][4];
      double[] udiag = new double[4];

      Uncmin_f77.optif0_f77 (3, paramOpt, system, xpls, fpls, gpls,
                             itrcmd, a, udiag);

      double[] param = new double[4];
      param[0] = 0;
      for (int i = 1; i <= 3; i++)
         param[i] = xpls[i];
      return param;
   }


   /**
    * Creates a new instance of a <SPAN  CLASS="textit">Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN></SPAN> distribution with
    *    parameters 0, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and
    *    <SPAN CLASS="MATH"><I>&#955;</I></SPAN> over the interval 
    * <SPAN CLASS="MATH">[<I>&#958;</I>,&#8734;]</SPAN> estimated using the maximum likelihood
    *     method based on the <SPAN CLASS="MATH"><I>n</I></SPAN> observations <SPAN CLASS="MATH"><I>x</I>[<I>i</I>]</SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;, <I>n</I> - 1</SPAN>.
    * 
    * @param x the list of observations to use to evaluate parameters
    * 
    *    @param n the number of observations to use to evaluate parameters
    * 
    * 
    */
   public static JohnsonSLDist getInstanceFromMLE (double[] x, int n) {
      double param[] = getMLE (x, n);
      return new JohnsonSLDist (0, param[0], param[1], param[2]);
   }


   /**
    * Returns the mean
    * of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN> distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>
    *    and <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the mean of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN> distribution
    *     
    * <SPAN CLASS="MATH"><I>E</I>[<I>X</I>] = <I>&#958;</I> + <I>&#955;e</I><SUP>1/2<I>&#948;</I><SUP>2</SUP>-<I>&#947;</I>/<I>&#948;</I></SUP></SPAN>
    * 
    */
   public static double getMean (double gamma, double delta,
                                 double xi, double lambda) {
      if (lambda <= 0.0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0.0)
         throw new IllegalArgumentException ("delta <= 0");

      double t = 1.0 / (2.0 * delta * delta) - gamma / delta;
      return (xi + lambda * Math.exp(t));
   }


   /**
    * Returns the variance
    * of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN> distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>
    *    and <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the variance of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN> distribution
    *     
    * <SPAN CLASS="MATH">Var[<I>X</I>] = <I>&#955;</I><SUP>2</SUP>(<I>e</I><SUP>1/<I>&#948;</I><SUP>2</SUP></SUP> -1)<I>e</I><SUP>1/<I>&#948;</I><SUP>2</SUP>-2<I>&#947;</I>/<I>&#948;</I></SUP></SPAN>
    * 
    */
   public static double getVariance (double gamma, double delta,
                                     double xi, double lambda) {
      if (lambda <= 0.0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0.0)
         throw new IllegalArgumentException ("delta <= 0");

      double t = 1.0 / (delta * delta) - 2 * gamma / delta;
      return lambda * lambda * Math.exp(t) * (Math.exp(1.0 / (delta * delta)) - 1);
   }


   /**
    * Returns the standard deviation of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN>
    *    distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the standard deviation of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN> distribution
    * 
    */
   public static double getStandardDeviation (double gamma, double delta,
                                              double xi, double lambda) {
      return Math.sqrt (JohnsonSLDist.getVariance (gamma, delta, xi, lambda));
   }


   /**
    * Sets the value of the parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and
    *   <SPAN CLASS="MATH"><I>&#955;</I></SPAN> for this object.
    * 
    */
   public void setParams (double gamma, double delta,
                          double xi, double lambda) {
      setParams0(gamma, delta, xi, lambda);
      setLastParams(xi);
   }

}
