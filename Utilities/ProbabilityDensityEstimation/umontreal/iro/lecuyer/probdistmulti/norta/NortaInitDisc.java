

/*
 * Class:        NortaInitDisc
 * Description:
 * Environment:  Java
 * Software:     SSJ
 * Copyright (C) 2001  Pierre L'Ecuyer and Université de Montréal
 * Organization: DIRO, Université de Montréal
 * @author       Nabil Channouf
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

package umontreal.iro.lecuyer.probdistmulti.norta;

import umontreal.iro.lecuyer.probdistmulti.*;
import umontreal.iro.lecuyer.probdist.*;


public abstract class NortaInitDisc  {
   protected double rX; // Target rank correlation
   // Marginal disttributions of r.variables X_1 and X_2
   protected DiscreteDistributionInt dist1;
   protected DiscreteDistributionInt dist2;
   protected double tr; /* Parameter for the quantile upper limit;
   		    used for truncation in the unbounded case. */
   protected double mu1, mu2, sd1, sd2; /* Means and standard deviations
   					    of F_1(X_1) and F_2(X_2). */

   private int m1, m2;    // Number of support points of the 2 marginals.
   private double[] p1;   // Probability masses of the marginal 1.
   private double[] p2;   // Probability masses of the marginal 2.
   /* Quantiles of the cumulative probability masses by the standard
      normal C.D.F for the 2 marginals */
   private double[] z1;
   private double[] z2;


   private String tabToString(double[] tab , String message)
   {
      String desc = message + "\n [";
      for (int i = 0; i < tab.length; i++)
         if (i == tab.length - 1)
            desc += "]\n";
         else
            desc += tab[i] + ",";
      return desc;
   }



   /**
    * Constructor of the abstract class. It can be called only by
    *  the constructors of subclasses of <TT>NortaInitDisc</TT>.
    * 
    */
   public NortaInitDisc (double rX, DiscreteDistributionInt dist1,
                         DiscreteDistributionInt dist2, double tr) {
      this.rX = rX;
      this.dist1 = dist1;
      this.dist2 = dist2;
      this.tr = tr;
   }


   /**
    * This method computes and returns the correlation <SPAN CLASS="MATH"><I>&#961;</I><SUB>Z</SUB></SPAN>.
    *      Every subclass of <TT>NortaInitDisc</TT> must implement it.
    * 
    */
   public abstract double computeCorr();


   /**
    * This method computes the following inputs of the two
    *     marginal distributions: <SPAN CLASS="MATH"><I>m</I><SUB>1</SUB></SPAN> and <SPAN CLASS="MATH"><I>m</I><SUB>2</SUB></SPAN>, mu1, mu2, sd1, sd2, and the vectors
    *      p1, p2, z1 and z2. Every subclass of <TT>NortaInitDisc</TT> must call it.
    * 
    */
   public void computeParams() 
   {
      m1 = dist1.inverseFInt (tr) + 1;
      m2 = dist2.inverseFInt (tr) + 1;
      // Support points of X_1 and X_2
      int[] y1 = new int[m1];
      int[] y2 = new int[m2];
      p1 = new double[m1];
      p2 = new double[m2];
      // Cumulative probability masses of X_1 and X_2
      double[] f1 = new double[m1];
      double[] f2 = new double[m2];
      z1 = new double[m1];
      z2 = new double[m2];
      double u11 = 0.0, u22 = 0.0;

      // Compute mu1, sd1, p1 and z1 of X_1
      for (int i = 0; i < m1; i++) {
         y1[i] = i;
         p1[i] = dist1.prob (y1[i]);
         f1[i] = dist1.cdf (y1[i]);
         z1[i] = NormalDist.inverseF01 (f1[i]);
         if (z1[i] == Double.NEGATIVE_INFINITY)
            z1[i] = NormalDist.inverseF01 (2.2e-308);
         if (z1[i] == Double.POSITIVE_INFINITY)
            z1[i] = NormalDist.inverseF01 (1.0 - Math.ulp (1.0));
         mu1 += f1[i] * p1[i];
         u11 += f1[i] * f1[i] * p1[i];
      }
      sd1 = Math.sqrt (u11 - mu1 * mu1);

      // Compute mu2, sd2, p2 and z2 of X_2
      for (int i = 0; i < m2; i++) {
         y2[i] = i;
         p2[i] = dist2.prob (y2[i]);
         f2[i] = dist2.cdf (y2[i]);
         z2[i] = NormalDist.inverseF01 (f2[i]);
         if (z2[i] == Double.NEGATIVE_INFINITY)
            z2[i] = NormalDist.inverseF01 (2.2e-308);
         if (z2[i] == Double.POSITIVE_INFINITY)
            z2[i] = NormalDist.inverseF01 (1.0 - Math.ulp (1.0));
         mu2 += f2[i] * p2[i];
         u22 += f2[i] * f2[i] * p2[i];
      }
      sd2 = Math.sqrt (u22 - mu2 * mu2);
   }


   /**
    * Computes the function value <SPAN CLASS="MATH"><I>g</I><SUB>r</SUB></SPAN> for each correlation.
    * 
    */
   public double integ (double r) 
   {
      double gr = 0.0; // The returned value.
      for (int i = 0; i < m1 - 1; i++) {
         double sum = 0.0;
         for (int j = 0; j < m2 - 1; j++) {
            sum += p2[j + 1]
                   * BiNormalDonnellyDist.barF (z1[i], z2[j], r);
         }
         gr += p1[i + 1] * sum;
      }
      return gr;
   }


   /**
    * Computes the first derivative of function <SPAN CLASS="MATH"><I>g</I><SUB>r</SUB></SPAN> for each correlation.
    * 
    */
   public double deriv (double r)
   {
      double c = Math.sqrt (1.0 - r * r);
      double c1 = 2 * c * c;
      double gp = 0.0; // The returned value

      for (int i = 0; i < m1 - 1; i++) {
         double z1sq = z1[i] * z1[i];
         double t1 = 2 * r * z1[i];
         double sum = 0.0;
         for (int j = 0; j < m2 - 1; j++) {
            sum += p2[j + 1]
                   * Math.exp ((t1 * z2[j] - z1sq - z2[j] * z2[j]) / c1);
         }
         gp += p1[i + 1] * sum;
      }
      gp = gp / (2.0 * Math.PI * c);
      return gp;
   }


   public String toString()
   {
      String desc = "";
      desc += "rX = " + rX + "\n";
      desc += "tr = " + tr + "\n";
      desc += "m1 = " + m1 + "\n";
      desc += "m2 = " + m2 + "\n";
      desc += "mu1 = " + mu1 + "\n";
      desc += "mu2 = " + mu2 + "\n";
      desc += "sd1 = " + sd1 + "\n";
      desc += "sd2 = " + sd2 + "\n";
      desc += tabToString(p1 , "Table p1 : ");
      desc += tabToString(z1 , "Table z1 : ");
      desc += tabToString(p2 , "Table p2 : ");
      desc += tabToString( z2, "Table z2 : ");
      return desc;
   }

}
