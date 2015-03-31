

/*
 * Class:        NI2a
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

import umontreal.iro.lecuyer.util.*;
import umontreal.iro.lecuyer.probdist.*;


/**
 * This class implements the algorithm NI2a wich uses the derivative and
 *   simple integration.
 * 
 */
public class NI2a extends NortaInitDisc 
{
   private double h; /* Predefined step size for the integration-grid
   			 spacing (also named h in the paper, paragraph
   			 "Method NI2" of section 3). */
   private double delta; /* Small positive parameter to make sure that
   			      rho_m is not too close to 1 or -1;
   			      (also named delta in the paper, paragraph "
   			      Method NI2" of section 3). */



   /**
    * Constructor of the class NI2a with the target rank correlation rX,
    *        the two discrete marginals dist1 and dist2,
    *        the parameter for truncation tr, and the specific
    *        parameters h and delta, which correspond to h and delta in the paper
    *        (paragraph "Method NI2" of section 3).
    * 
    */
   public NI2a(double rX, DiscreteDistributionInt dist1,
               DiscreteDistributionInt dist2, double tr, double h,
               double delta)
   {
      super(rX, dist1, dist2, tr);
      this.h = h;
      this. delta = delta;
      computeParams();
   }


   /**
    * Computes and returns the correlation <SPAN CLASS="MATH"><I>&#961;</I><SUB>Z</SUB></SPAN> using the algorithm NI2a.
    * 
    */
   public double computeCorr()
   {
      // x, y and c coefficients used for quadratic interpolation.
      double[] x = new double[3];
      double[] y = new double[3];
      double[] c = new double[3];
      double xtemp = 0.0, temp = 0.0; /* Values of rho and the recursive
      					   quantity I_k, given in paragraph
      					   "Method NI2" of section 3 in the
      					   paper, 2 iterations before the
      					   last one. */
      double xold, iold; /* Values of rho and I_k at one iteration before
      			      the last one.*/
      double xnew, inew; // Values of rho and I_k at the last iteration.
      double dold, dmid, dnew; /* Values of the derivative function g'
      				   at points xold, xmid (xold+h) and xnew.
      				   They correspond to g'(rho_0+2kh-2h),
      				   g'(rho_0+2kh-h) and g'(rho_0+2kh) in the
      				   formula of I_k in the paper
      				   (paragraph "Method NI2" of section 3). */

      double d = 0.0;   // Integration distance.
      int m;            // Number of iterations needed.
      /* * d and m correspond to d and m given in the third paragraph of
          section 4 in the paper. */
      double b = 0.0; // The returned solution.
      double h2 = 0.0, hd3 = 0.0; // Precompute constants.
      double lrx = (integ ( -1) - mu1 * mu2) / sd1 * sd2; // Min.correlation.
      double urx = (integ (1) - mu1 * mu2) / sd1 * sd2; // Max.correlation.
      double rho1 = 2 * Math.sin (Math.PI * rX / 6); // The initial guess.
      double intg1 = integ (rho1); // Computes g_r(rho1).
      double gr = rX * sd1 * sd2 + mu1 * mu2; /* Target value; integ(\rho)
      						   = gr is equivalent to
      						   \rho = the solution. */

      if (intg1 == gr)
         return rho1;

      if (intg1 < gr) {         // Orient the search from left to right
         if (0 < rX && rX < 1) // Do search between rh_0 and rho_m=1 - delta
            d = 1 - delta - rho1;
         if ( -1 < rX && rX < 0) // Do search between rh_0 and 0
            d = -rho1;
         m = (int) Math.ceil(d / (2 * h));
         h = d / (2 * m); // Readjust h
         hd3 = h / 3;
         h2 = 2 * h;
         xold = rho1;
         dold = deriv (xold);
         iold = intg1;

         for (int i = 1; i <= m; i++) { // Begin the search
            dmid = deriv (xold + h);
            xnew = xold + h2;
            dnew = deriv (xnew);
            inew = iold + hd3 * (dold + 4 * dmid + dnew);

            if (inew >= gr) { // The root is in current bracketing interval
               // Compute the parameters of quadratic interpolation
               x[0] = xtemp;
               x[1] = xold;
               x[2] = xnew;
               y[0] = temp;
               y[1] = iold;
               y[2] = inew;
               Misc.interpol (2, x, y, c);
               b = (c[2] * (xtemp + xold) - c[1] + Math.sqrt ((c[1]
                     - c[2] * (xtemp + xold)) * (c[1] - c[2]
                     * (xtemp + xold)) - 4 * c[2] * (c[0] - c[1]
                     * xtemp + c[2] * xtemp * xold - gr))) / (2 * c[2]);
               return b;
            }
            xtemp = xold;
            temp = iold;
            xold = xnew;
            dold = dnew;
            iold = inew;
         }
         b = 1.0 - delta / 2;    // The root is at the right of rho_m
      }

      if (intg1 > gr) {           // Orient the search from right to left
         if (0 < rX && rX < 1)   // Do search between 0 and rh_0
            d = rho1;
         if ( -1 < rX && rX < 0) // Do search between rho_m=-1+delta and rho_0
            d = rho1 + 1 - delta;
         m = (int) Math.ceil (d / (2 * h));
         h = d / (2 * m); // Readjust h
         hd3 = h / 3;     // Pre-compute constant
         h2 = 2 * h;      // Pre-compute constant
         xold = rho1;
         dold = deriv (xold);
         iold = intg1;
         for (int i = 1; i <= m; i++) { // Begin the search
            dmid = deriv (xold - h);
            xnew = xold - h2;
            dnew = deriv (xnew);
            inew = iold - hd3 * (dold + 4 * dmid + dnew);
            if (inew <= gr) { // The root is in current bracketing interval
               // Compute the parameters of quadratic interpolation
               x[0] = xnew;
               x[1] = xold;
               x[2] = xtemp;
               y[0] = inew;
               y[1] = iold;
               y[2] = temp;
               Misc.interpol (2, x, y, c);
               b = (c[2] * (xnew + xold) - c[1] + Math.sqrt ((c[1]
                   - c[2] * (xnew + xold)) * (c[1] - c[2]
                   * (xnew + xold)) - 4 * c[2] * (c[0] - c[1]
                   * xnew + c[2] * xnew * xold - gr))) / (2 * c[2]);
               return b;
            }
            xtemp = xold;
            temp = iold;
            xold = xnew;
            dold = dnew;
            iold = inew;
         }
         b = -1 + delta / 2; // The root is at the left of rho_m
      }
      return b;
   }


   public String toString()
   {
      String desc = super.toString();
      desc += "h :  " + h + "\n";
      desc += " delta : " + delta + "\n";
      return desc;
   }

}
