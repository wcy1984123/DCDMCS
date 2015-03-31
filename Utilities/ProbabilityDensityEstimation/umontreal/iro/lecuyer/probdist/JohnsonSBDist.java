

/*
 * Class:        JohnsonSBDist
 * Description:  Johnson S_B distribution
 * Environment:  Java
 * Software:     SSJ
 * Copyright (C) 2001  Pierre L'Ecuyer and Universite de Montreal
 * Organization: DIRO, Universite de Montreal
 * @author
 * @since

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
import umontreal.iro.lecuyer.util.Num;


/**
 * Extends the class {@link ContinuousDistribution} for
 * the <EM>Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN></EM> distribution
 * with shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and 
 * <SPAN CLASS="MATH"><I>&#948;</I> &gt; 0</SPAN>, location parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN>,
 * and scale parameter <SPAN CLASS="MATH"><I>&#955;</I> &gt; 0</SPAN>.
 * Denoting 
 * <SPAN CLASS="MATH"><I>t</I> = (<I>x</I> - <I>&#958;</I>)/<I>&#955;</I></SPAN> and 
 * <SPAN CLASS="MATH"><I>z</I> = <I>&#947;</I> + <I>&#948;</I>ln(<I>t</I>/(1 - <I>t</I>))</SPAN>, the density is
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>f</I> (<I>x</I>) = <I>&#948;e</I><SUP>-z<SUP>2</SUP>/2</SUP>/(<I>&#955;t</I>(1-<I>t</I>)(2&pi;)<SUP>1/2</SUP>),&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for <I>&#958;</I> &lt; <I>x</I> &lt; <I>&#958;</I> + <I>&#955;</I>,
 * </DIV><P></P>
 * and 0 elsewhere.  The distribution function is
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>F</I>(<I>x</I>) = <I>&#934;</I>(<I>z</I>), for <I>&#958;</I> &lt; <I>x</I> &lt; <I>&#958;</I> + <I>&#955;</I>,
 * </DIV><P></P>
 * where <SPAN CLASS="MATH"><I>&#934;</I></SPAN> is the standard normal distribution function.
 * The inverse distribution function is
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>F</I><SUP>-1</SUP>(<I>u</I>) = <I>&#958;</I> + <I>&#955;</I>(1/(1 + <I>e</I><SUP>-v(u)</SUP>))&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for 0&nbsp;&lt;=&nbsp;<I>u</I>&nbsp;&lt;=&nbsp;1,
 * </DIV><P></P>
 * where
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>v</I>(<I>u</I>) = [<I>&#934;</I><SUP>-1</SUP>(<I>u</I>) - <I>&#947;</I>]/<I>&#948;</I>.
 * </DIV><P></P>
 * 
 * <P>
 * This class relies on the methods  {@link NormalDist#cdf01 NormalDist.cdf01} and
 *   {@link NormalDist#inverseF01 NormalDist.inverseF01}
 * of {@link NormalDist} to approximate <SPAN CLASS="MATH"><I>&#934;</I></SPAN> and <SPAN CLASS="MATH"><I>&#934;</I><SUP>-1</SUP></SPAN>.
 * 
 */
public class JohnsonSBDist extends JohnsonSystem {
   // m_psi is used in computing the mean and the variance
   private double m_psi = -1.0e100;


   private static double getMeanPsi (double gamma, double delta,
                           double xi, double lambda, double[] tpsi) {
      // Returns the theoretical mean of t = (x - xi)/lambda;
      // also compute psi and returns it in tpsi[0], since
      // it is used in computing the mean and the variance

      final int NMAX = 10000;
      final double EPS = 1.0e-15;

      double a1 = 1.0/(2*delta*delta);
      double a2 = (1.0 - 2*delta*gamma)/(2*delta*delta);
      double a3 = (gamma - 1./delta)/delta;
      int n = 0;
      double tem = 1;
      double sum = 0;
      double v;
      while (Math.abs(tem) > EPS* Math.abs(sum) && n < NMAX) {
         ++n;
         v = Math.exp(-n*gamma/delta) + Math.exp(n*a3);
         tem = Math.exp(-n*n*a1) * v / (1 + Math.exp(-2*n*a1));
      //   tem = Math.exp(-n*n*a1) * Math.cosh(n*a2) / Math.cosh(n*a1);
         sum += tem;
      }
      if (n >= NMAX)
         System.err.println ("JohnsonSBDist:  possible lack of accuracy on mean");
      double A = (0.5 + sum) / (delta);

      a1 = Math.PI * Math.PI * delta * delta;
      a2 = Math.PI * delta * gamma;
      int j;
      n = 0;
      tem = 1;
      sum = 0;
      while (Math.abs(tem) > EPS*Math.abs(sum) && n < NMAX) {
         ++n;
         j = 2*n - 1;
         tem = Math.exp(-j*j*a1/2.0) * Math.sin(j*a2) / Math.sinh(j*a1);
         sum += tem;
      }
      if (n >= NMAX)
         System.err.println ("JohnsonSBDist:  possible lack of accuracy on mean");
      double B = 2.0* Math.PI * delta * sum;

      a1 = 2*Math.PI * Math.PI * delta * delta;
      a2 = 2*Math.PI * delta * gamma;
      n = 0;
      tem = 1;
      sum = 0;
      while (Math.abs(tem) > EPS*Math.abs(sum) && n < NMAX) {
         ++n;
         tem = Math.exp(-n*n*a1) * Math.cos(n*a2);
         sum += tem;
      }
      if (n >= NMAX)
         System.err.println ("JohnsonSBDist:  possible lack of accuracy on mean");
      double C = 1 + 2.0 * sum;

      double D = Math.sqrt(2*Math.PI) * Math.exp(gamma* gamma / 2.0);
      double tmean = (A - B) / (C*D);
      tpsi[0] = C*D;
      return tmean;
   }



   /**
    * Constructs a <TT>JohnsonSBDist</TT> object
    *    with shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and <SPAN CLASS="MATH"><I>&#948;</I></SPAN>,
    *    location parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and scale parameter <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    */
   public JohnsonSBDist (double gamma, double delta,
                         double xi, double lambda) {
      super (gamma, delta, xi, lambda);
      setLastParams(xi, lambda);
   }


   private void setLastParams(double xi, double lambda) {
      supportA = xi;
      supportB = xi + lambda;
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

   public double inverseF (double u) {
      return inverseF (gamma, delta, xi, lambda, u);
   }

   public double getMean() {
      return JohnsonSBDist.getMean (gamma, delta, xi, lambda);
   }

   public double getVariance() {
      return JohnsonSBDist.getVariance (gamma, delta, xi, lambda);
   }

   public double getStandardDeviation() {
      return JohnsonSBDist.getStandardDeviation (gamma, delta, xi, lambda);
   }


   /**
    * Returns the density function.
    * 
    */
   public static double density (double gamma, double delta,
                                 double xi, double lambda, double x) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");
      if (x <= xi || x >= (xi+lambda))
         return 0.0;
      double y = (x - xi)/lambda;
      double z = gamma + delta*Math.log (y/(1.0 - y));
      return delta/(lambda*y*(1.0 - y)*Math.sqrt (2.0*Math.PI))*
           Math.exp (-z*z/2.0);
   }


   /**
    * Returns the distribution function.
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
      if (x >= xi+lambda)
         return 1.0;
      double y = (x - xi)/lambda;
      double z = gamma + delta*Math.log (y/(1.0 - y));
      return NormalDist.cdf01 (z);
   }


   /**
    * Returns the complementary distribution.
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
      if (x >= xi+lambda)
         return 0.0;
      double y = (x - xi)/lambda;
      double z = gamma + delta*Math.log (y/(1.0 - y));
      return NormalDist.barF01 (z);
   }


   /**
    * Returns the inverse of the distribution.
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

      if (u >= 1.0)    // if u == 1, in fact
          return xi+lambda;
      if (u <= 0.0)    // if u == 0, in fact
          return xi;

      double z = NormalDist.inverseF01 (u);
      double v = (z - gamma)/delta;

      if (v >= Num.DBL_MAX_EXP*Num.LN2)
            return xi + lambda;
      if (v <= Num.DBL_MIN_EXP*Num.LN2)
            return xi;

      v = Math.exp (v);
      return (xi + (xi+lambda)*v)/(1.0 + v);
   }


   /**
    * Estimates the parameters 
    * <SPAN CLASS="MATH">(<I>&#947;</I>, <I>&#948;</I>)</SPAN> of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN> distribution,
    *    using the maximum likelihood method, from the <SPAN CLASS="MATH"><I>n</I></SPAN> observations
    *    <SPAN CLASS="MATH"><I>x</I>[<I>i</I>]</SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;, <I>n</I> - 1</SPAN>.
    *    Parameters 
    * <SPAN CLASS="MATH"><I>&#958;</I> = <texttt>xi</texttt></SPAN> and 
    * <SPAN CLASS="MATH"><I>&#955;</I> = <texttt>lambda</texttt></SPAN> are known.
    *    The estimated parameters are returned in a two-element
    *     array  in the order: [<SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>].
    * 
    * @param x the list of observations to use to evaluate parameters
    * 
    *    @param n the number of observations to use to evaluate parameters
    * 
    *    @param xi parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN>
    * 
    *    @param lambda parameter <SPAN CLASS="MATH"><I>&#955;</I></SPAN>
    * 
    *    @return returns the parameters [
    * <SPAN CLASS="MATH">hat(&gamma;)</SPAN>, 
    * <SPAN CLASS="MATH">hat(&delta;)</SPAN>]
    * 
    */
   public static double[] getMLE (double[] x, int n,
                                  double xi, double lambda)  {
      if (n <= 0)
         throw new IllegalArgumentException ("n <= 0");
      final double LN_EPS = Num.LN_DBL_MIN - Num.LN2;
      double[] ftab = new double[n];
      double sum = 0.0;
      double t;

      for (int i = 0; i < n; i++) {
         t = (x[i] - xi) / lambda;
         if (t <= 0.)
            ftab[i] = LN_EPS;
         else if (t >= 1 - Num.DBL_EPSILON)
            ftab[i] = Math.log (1. / Num.DBL_EPSILON);
         else
            ftab[i] = Math.log (t/(1. - t));
         sum += ftab[i];
      }
      double empiricalMean = sum / n;

      sum = 0.0;
      for (int i = 0; i < n; i++) {
         t = ftab[i] - empiricalMean;
         sum += t * t;
      }
      double sigmaf = Math.sqrt(sum / n);

      double[] param = new double[2];
      param[0] = -empiricalMean / sigmaf;
      param[1] = 1.0 / sigmaf;

      return param;
   }


   /**
    * Creates a new instance of a <TT>JohnsonSBDist</TT> object
    *    using the maximum likelihood method based
    *    on the <SPAN CLASS="MATH"><I>n</I></SPAN> observations <SPAN CLASS="MATH"><I>x</I>[<I>i</I>]</SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;, <I>n</I> - 1</SPAN>.
    *    Given the parameters 
    * <SPAN CLASS="MATH"><I>&#958;</I> = <texttt>xi</texttt></SPAN> and 
    * <SPAN CLASS="MATH"><I>&#955;</I> = <texttt>lambda</texttt></SPAN>,
    *    the parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and <SPAN CLASS="MATH"><I>&#948;</I></SPAN> are estimated from the observations.
    * 
    * @param x the list of observations to use to evaluate parameters
    * 
    *    @param n the number of observations to use to evaluate parameters
    * 
    *    @param xi parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN>
    * 
    *    @param lambda parameter <SPAN CLASS="MATH"><I>&#955;</I></SPAN>
    * 
    * 
    */
   public static JohnsonSBDist getInstanceFromMLE (double[] x, int n,
                                                   double xi, double lambda) {
      double parameters[] = getMLE (x, n, xi, lambda);
      return new JohnsonSBDist (parameters[0], parameters[1], xi, lambda);
   }


   /**
    * Returns the mean 
    *    of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN> distribution with parameters
    *    <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    */
   public static double getMean (double gamma, double delta,
                                 double xi, double lambda) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");
      double[] tpsi = new double[1];
      double mu = getMeanPsi (gamma, delta, xi, lambda, tpsi);
      return xi + lambda * mu;
   }


   /**
    * Returns the variance 
    *    of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN> distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>
    *    and <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the variance of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN> distribution.
    * 
    */
   public static double getVariance (double gamma, double delta,
                                     double xi, double lambda) {
      if (lambda <= 0.0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0.0)
         throw new IllegalArgumentException ("delta <= 0");

      final int NMAX = 10000;
      final double EPS = 1.0e-15;

      double a1 = 1.0/(2.0*delta*delta);
      double a2 = (1.0 - 2.0*delta*gamma)/(2.0*delta*delta);
      double a3 = (gamma - 1./delta)/delta;
      double v;
      int n = 0;
      double tem = 1;
      double sum = 0;
      while (Math.abs(tem) > EPS*Math.abs(sum) && n < NMAX) {
         ++n;
         v = Math.exp(-n*gamma/delta) - Math.exp(n*a3);
         tem = n*Math.exp(-n*n*a1) * v / (1 + Math.exp(-2*n*a1));
       //  tem = n*Math.exp(-n*n*a1) * Math.sinh(n*a2) / Math.cosh(n*a1);
         sum += tem;
      }
      if (n >= NMAX)
         System.err.println ("JohnsonSBDist:  possible lack of accuracy on variance");
      double A = -sum / (delta*delta);

      a1 = Math.PI * Math.PI * delta * delta;
      a2 = Math.PI * delta * gamma;
      int j;
      n = 0;
      tem = 1;
      sum = 0;
      while (Math.abs(tem) > EPS*Math.abs(sum) && n < NMAX) {
         ++n;
         j = 2*n - 1;
         tem = j*Math.exp(-j*j*a1/2.0) * Math.cos(j*a2) / Math.sinh(j*a1);
         sum += tem;
      }
      if (n >= NMAX)
         System.err.println ("JohnsonSBDist:  possible lack of accuracy on variance");
      double B = 2.0* a1 * sum;

      a1 = 2*Math.PI * Math.PI * delta * delta;
      a2 = 2*Math.PI * delta * gamma;
      n = 0;
      tem = 1;
      sum = 0;
      while (Math.abs(tem) > EPS*Math.abs(sum) && n < NMAX) {
         ++n;
         tem = n * Math.exp(-n*n*a1) * Math.sin(n*a2);
         sum += tem;
      }
      if (n >= NMAX)
         System.err.println ("JohnsonSBDist:  possible lack of accuracy on variance");
      double C = - 4.0 * Math.PI * delta * sum;

      double D = Math.sqrt(2*Math.PI) * Math.exp(0.5 * gamma* gamma);
      double[] tpsi = new double[1];
      double mu = getMeanPsi (gamma, delta, xi, lambda, tpsi);

      double tvar = mu *(1 - delta * gamma) - mu *mu +
                    delta / tpsi[0] * (A - B - mu * C * D);
      return lambda*lambda*tvar;

   }


   /**
    * Returns the standard deviation of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN>
    *    distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the standard deviation of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN> distribution
    * 
    */
  public static double getStandardDeviation (double gamma, double delta,
                                             double xi, double lambda) {
      return Math.sqrt (JohnsonSBDist.getVariance (gamma, delta, xi, lambda));
   }


   /**
    * Sets the value of the parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and
    *   <SPAN CLASS="MATH"><I>&#955;</I></SPAN> for this object.
    * 
    */
   public void setParams (double gamma, double delta,
                          double xi, double lambda) {
      setParams0(gamma, delta, xi, lambda);
      setLastParams(xi, lambda);
   }

}
