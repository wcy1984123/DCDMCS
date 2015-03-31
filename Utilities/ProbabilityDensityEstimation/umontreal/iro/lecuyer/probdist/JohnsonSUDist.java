

/*
 * Class:        JohnsonSUDist
 * Description:  Johnson S_U distribution
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
 * the <EM>Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN></EM> distribution.
 * It  has shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and 
 * <SPAN CLASS="MATH"><I>&#948;</I> &gt; 0</SPAN>, location parameter
 * <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, and scale parameter 
 * <SPAN CLASS="MATH"><I>&#955;</I> &gt; 0</SPAN>.
 * Denoting 
 * <SPAN CLASS="MATH"><I>t</I> = (<I>x</I> - <I>&#958;</I>)/<I>&#955;</I></SPAN> and
 * 
 * <SPAN CLASS="MATH"><I>z</I> = <I>&#947;</I> + <I>&#948;</I>ln(<I>t</I> + (t^2 + 1)<SUP>1/2</SUP>)</SPAN>,
 * the distribution has density
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>f</I> (<I>x</I>) = <I>&#948;e</I><SUP>-z<SUP>2</SUP>/2</SUP>/(<I>&#955;</I>(2&pi;(t^2 + 1))<SUP>1/2</SUP>),&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for  - &#8734; &lt; <I>x</I> &lt; &#8734;,
 * </DIV><P></P>
 * and distribution function
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>F</I>(<I>x</I>) = <I>&#934;</I>(<I>z</I>),&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for  - &#8734; &lt; <I>x</I> &lt; &#8734;,
 * </DIV><P></P>
 * where <SPAN CLASS="MATH"><I>&#934;</I></SPAN> is the standard normal distribution function.
 * The inverse distribution function is
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>F</I><SUP>-1</SUP>(<I>u</I>) = <I>&#958;</I> + <I>&#955;</I>sinh(<I>v</I>(<I>u</I>)),&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;for 0&nbsp;&lt;=&nbsp;<I>u</I>&nbsp;&lt;=&nbsp;1,
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
 *    {@link NormalDist#inverseF01 NormalDist.inverseF01} of {@link NormalDist} to
 *   approximate <SPAN CLASS="MATH"><I>&#934;</I></SPAN> and <SPAN CLASS="MATH"><I>&#934;</I><SUP>-1</SUP></SPAN>.
 * 
 */
public class JohnsonSUDist extends JohnsonSystem {

   private static double calcR (double a, double b, double x) {
      /* ** cette fonction calcule
                 r = z + sqrt(z*z + 1)
           en utilisant un algorithme stable
       ***/

      double z = (x - a)/b;

      double s = Math.abs(z);
      if (s < 1.0e20)
         s = Math.sqrt (z * z + 1.0);

      // compute r = z + sqrt (z * z + 1)
      double r;
      if (z >= 0.0)
         r = s + z;
      else
         r = 1.0/(s - z);

      return r;
   }



   /**
    * Same as {@link #JohnsonSUDist(double,double,double,double) JohnsonSUDist}
    *     <TT>(gamma, delta, 0, 1)</TT>.
    * 
    */
   public JohnsonSUDist (double gamma, double delta) {
      this (gamma, delta, 0, 1);
   }


   /**
    * Constructs a <TT>JohnsonSUDist</TT> object
    *    with shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and <SPAN CLASS="MATH"><I>&#948;</I></SPAN>,
    *    location parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, and scale parameter <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    */
   public JohnsonSUDist (double gamma, double delta,
                         double xi, double lambda) {
      super (gamma, delta, xi, lambda);
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
      return JohnsonSUDist.getMean (gamma, delta, xi, lambda);
   }

   public double getVariance() {
      return JohnsonSUDist.getVariance (gamma, delta, xi, lambda);
   }

   public double getStandardDeviation() {
      return JohnsonSUDist.getStandardDeviation (gamma, delta, xi, lambda);
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
      double r = calcR (xi, lambda, x);
      if (r <= 0.0)
         return 0.0;
      double z = gamma + delta*Math.log (r);
      double y = (x - xi)/lambda;
      if (z >= 1.0e10)
         return 0;
      return delta/(lambda*Math.sqrt (2.0*Math.PI)*Math.sqrt (y*y + 1.0))*
           Math.exp (-z*z/2.0);
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
      double r = calcR (xi, lambda, x);
      if (r > 0.0)
         return NormalDist.cdf01 (gamma + delta*Math.log (r));
      else
         return 0.0;
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

      double r = calcR (xi, lambda, x);
      if (r > 0.0)
         return NormalDist.barF01 (gamma + delta * Math.log (r));
      else
         return 1.0;
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
         return Double.NEGATIVE_INFINITY;

      double z = NormalDist.inverseF01 (u);
      double v = (z - gamma)/delta;
      if (v >= Num.DBL_MAX_EXP*Num.LN2)
         return Double.POSITIVE_INFINITY;
      if (v <= Num.LN2*Num.DBL_MIN_EXP)
         return Double.NEGATIVE_INFINITY;

      return xi + lambda * Math.sinh(v);
   }


   /**
    * Returns the mean
    * of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN> distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the mean of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN> distribution
    *     
    * <SPAN CLASS="MATH"><I>E</I>[<I>X</I>] = <I>&#958;</I> - <I>&#955;</I>exp<SUP>1/(2<I>&#948;</I><SUP>2</SUP>)</SUP><I>sinh</I>(<I>&#947;</I>/<I>&#948;</I>)</SPAN>
    * 
    */
   public static double getMean (double gamma, double delta,
                                 double xi, double lambda) {
      if (lambda <= 0.0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0.0)
         throw new IllegalArgumentException ("delta <= 0");

      return (xi - (lambda * Math.exp(1.0 / (2.0 * delta * delta)) *
                             Math.sinh(gamma / delta)));
   }


   /**
    * Returns the variance
    * of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN> distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the variance of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN> distribution
    *     
    * <SPAN CLASS="MATH">Var[<I>X</I>] = (<I>&#955;</I><SUP>2</SUP>/2)(exp<SUP>1/<I>&#948;</I><SUP>2</SUP></SUP> -1)(exp<SUP>1/<I>&#948;</I><SUP>2</SUP></SUP><I>cosh</I>(2<I>&#947;</I>/<I>&#948;</I>) + 1)</SPAN>
    * 
    */
   public static double getVariance (double gamma, double delta,
                                     double xi, double lambda) {
      if (lambda <= 0.0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0.0)
         throw new IllegalArgumentException ("delta <= 0");

      double omega2 = Math.exp(1 / (delta * delta));
      return ((omega2 - 1) * (omega2 * Math.cosh(2 * gamma / delta) + 1) / 2 * lambda * lambda);
   }


   /**
    * Returns the standard deviation of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN>
    *    distribution with parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    * @return the standard deviation of the Johnson <SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN> distribution
    * 
    */
   public static double getStandardDeviation (double gamma, double delta,
                                              double xi, double lambda) {
      return Math.sqrt (JohnsonSUDist.getVariance (gamma, delta, xi, lambda));
   }


   /**
    * Sets the value of the parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and
    *   <SPAN CLASS="MATH"><I>&#955;</I></SPAN> for this object.
    * 
    */
   public void setParams (double gamma, double delta,
                          double xi, double lambda) {
      setParams0(gamma, delta, xi, lambda);
   }

}
