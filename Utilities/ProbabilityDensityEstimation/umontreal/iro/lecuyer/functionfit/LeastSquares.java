

/*
 * Class:        LeastSquares
 * Description:  General linear regression with the least squares method
 * Environment:  Java
 * Software:     SSJ
 * Copyright (C) 2013  Pierre L'Ecuyer and Universite de Montreal
 * Organization: DIRO, Universite de Montreal
 * @author       Richard Simard
 * @since        April 2013

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

package umontreal.iro.lecuyer.functionfit; 

import java.io.Serializable;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.QRDecomposition;
import cern.colt.matrix.linalg.SingularValueDecomposition;
import cern.colt.matrix.linalg.Algebra;


/**
 * This class implements different <SPAN  CLASS="textit">linear regression</SPAN> models, using the
 * least squares method to estimate the regression coefficients. Given
 * input data <SPAN CLASS="MATH"><I>x</I><SUB>ij</SUB></SPAN> and response <SPAN CLASS="MATH"><I>y</I><SUB>i</SUB></SPAN>, one want to find the
 * coefficients <SPAN CLASS="MATH"><I>&#946;</I><SUB>j</SUB></SPAN> that minimize the residuals of the form
 * (using matrix notation)
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>r</I> = min<SUB><I>&#946;</I></SUB>| <I>Y</I> - <I>X&#946;</I>|<SUB>2</SUB>,
 * </DIV><P></P>
 * where the <SPAN CLASS="MATH"><I>L</I><SUB>2</SUB></SPAN> norm is used. Particular cases are
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>r</I> = min<SUB><I>&#946;</I></SUB>&sum;<SUB>i</SUB>(<I>y</I><SUB>i</SUB> - <I>&#946;</I><SUB>0</SUB> - &sum;<SUB>j=1</SUB><SUP>k</SUP><I>&#946;</I><SUB>j</SUB><I>x</I><SUB>ij</SUB>)<SUP>2</SUP>.
 * </DIV><P></P>
 * for <SPAN CLASS="MATH"><I>k</I></SPAN> regressor variables <SPAN CLASS="MATH"><I>x</I><SUB>j</SUB></SPAN>. The well-known case of the single
 * variable <SPAN CLASS="MATH"><I>x</I></SPAN> is
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>r</I> = min<SUB><I>&#945;</I>, <I>&#946;</I></SUB>&sum;<SUB>i</SUB>(<I>y</I><SUB>i</SUB> - <I>&#945;</I> - <I>&#946;x</I><SUB>i</SUB>)<SUP>2</SUP>.
 * </DIV><P></P>
 * 
 * <P>
 * Sometimes, one wants to use a basis of general functions <SPAN CLASS="MATH"><I>&#968;</I><SUB>j</SUB>(<I>t</I>)</SPAN>
 * with a minimization of the form
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <I>r</I> = min<SUB><I>&#946;</I></SUB>&sum;<SUB>i</SUB>(<I>y</I><SUB>i</SUB> - &sum;<SUB>j=1</SUB><SUP>k</SUP><I>&#946;</I><SUB>j</SUB><I>&#968;</I><SUB>j</SUB>(<I>t</I><SUB>i</SUB>))<SUP>2</SUP>.
 * </DIV><P></P>
 * For example, we could have 
 * <SPAN CLASS="MATH"><I>&#968;</I><SUB>j</SUB>(<I>t</I>) = <I>e</I><SUP>-<I>&#955;</I><SUB>j</SUB>t</SUP></SPAN> or some other
 * functions.
 * In that case, one has to choose the points <SPAN CLASS="MATH"><I>t</I><SUB>i</SUB></SPAN> at which to
 * compute the basis functions, and use a method below with
 * 
 * <SPAN CLASS="MATH"><I>x</I><SUB>ij</SUB> = <I>&#968;</I><SUB>j</SUB>(<I>t</I><SUB>i</SUB>)</SPAN>.
 * 
 */
public class LeastSquares {

   private static double[] solution (DoubleMatrix2D X, DoubleMatrix2D Y, int k) {
      // Solve X * Beta = Y for Beta
      // Only the first column of Y is used
      // k is number of beta coefficients

      QRDecomposition qr = new QRDecomposition(X);

      if (qr.hasFullRank()) {
         DoubleMatrix2D B = qr.solve(Y);
         return B.viewColumn(0).toArray();

      } else {
         DoubleMatrix1D Y0 = Y.viewColumn(0);   // first column of Y
         SingularValueDecomposition svd = new SingularValueDecomposition(X);
         DoubleMatrix2D S = svd.getS();
         DoubleMatrix2D V = svd.getV();
         DoubleMatrix2D U = svd.getU();
         Algebra alg = new Algebra();
         DoubleMatrix2D Ut = alg.transpose(U);
         DoubleMatrix1D g = alg.mult(Ut, Y0);    // Ut*Y0

         for (int j = 0; j < k; j++) {
            // solve S*p = g for p;  S is a diagonal matrix
            double x = S.getQuick(j, j);
            if (x > 0.) {
               x = g.getQuick(j) / x;   // p[j] = g[j]/S[j]
               g.setQuick(j, x);        // overwrite g by p
            } else
               g.setQuick(j, 0.);
         }
         DoubleMatrix1D beta = alg.mult(V, g);   // V*p
         return beta.toArray();
      }
} 


   /**
    * Computes the regression coefficients using the
    * least squares method. This is a simple linear regression with
    *  2 regression coefficients, <SPAN CLASS="MATH"><I>&#945;</I></SPAN> and <SPAN CLASS="MATH"><I>&#946;</I></SPAN>. The model is
    * 
    * <P></P>
    * <DIV ALIGN="CENTER" CLASS="mathdisplay">
    * <I>y</I> = <I>&#945;</I> + <I>&#946;x</I>.
    * </DIV><P></P>
    * Given the <SPAN CLASS="MATH"><I>n</I></SPAN> data points 
    * <SPAN CLASS="MATH">(<I>X</I><SUB>i</SUB>, <I>Y</I><SUB>i</SUB>)</SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;,(<I>n</I> - 1)</SPAN>,
    *  the method computes and returns the array
    * 
    * <SPAN CLASS="MATH">[<I>&#945;</I>, <I>&#946;</I>]</SPAN>.
    * 
    * @param X the regressor variables
    * 
    *    @param Y the response
    * 
    *    @return the regression coefficients
    * 
    */
   public static double[] calcCoefficients (double[] X, double[] Y) {
      if (X.length != Y.length)
         throw new IllegalArgumentException ("Lengths of X and Y are not equal");
      final int n = X.length;
      double[][] Xa = new double[n][1];
      for (int i = 0; i < n; i++)
         Xa[i][0] = X[i];

      return calcCoefficients0 (Xa, Y);
   }


   /**
    * Computes the regression coefficients using the
    * least squares method. This is a linear regression with a polynomial of
    * degree <TT>deg</TT> <SPAN CLASS="MATH">= <I>k</I></SPAN> and <SPAN CLASS="MATH"><I>k</I> + 1</SPAN> regression coefficients <SPAN CLASS="MATH"><I>&#946;</I><SUB>j</SUB></SPAN>.
    * The model is
    * 
    * <P></P>
    * <DIV ALIGN="CENTER" CLASS="mathdisplay">
    * <I>y</I> = <I>&#946;</I><SUB>0</SUB> + &sum;<SUB>j=1</SUB><SUP>k</SUP><I>&#946;</I><SUB>j</SUB><I>x</I><SUP>j</SUP>.
    * </DIV><P></P>
    * Given the <SPAN CLASS="MATH"><I>n</I></SPAN> data points 
    * <SPAN CLASS="MATH">(<I>X</I><SUB>i</SUB>, <I>Y</I><SUB>i</SUB>)</SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;,(<I>n</I> - 1)</SPAN>,
    *  the method computes and returns the array
    * 
    * <SPAN CLASS="MATH">[<I>&#946;</I><SUB>0</SUB>, <I>&#946;</I><SUB>1</SUB>,&#8230;, <I>&#946;</I><SUB>k</SUB>]</SPAN>. Restriction: <SPAN CLASS="MATH"><I>n</I> &gt; <I>k</I></SPAN>.
    * 
    * @param X the regressor variables
    * 
    *    @param Y the response
    * 
    *    @return the regression coefficients
    * 
    */
   public static double[] calcCoefficients (double[] X, double[] Y, int deg)  {
      final int n = X.length;
      if (n != Y.length)
         throw new IllegalArgumentException ("Lengths of X and Y are not equal");
      if (n < deg + 1)
         throw new IllegalArgumentException ("Not enough points");

      final double[] xSums = new double[2 * deg + 1];
      final double[] xySums = new double[deg + 1];
      xSums[0] = n;
      for (int i = 0; i < n; i++) {
         double xv = X[i];
         xySums[0] += Y[i];
         for (int j = 1; j <= 2 * deg; j++) {
            xSums[j] += xv;
            if (j <= deg)
               xySums[j] += xv * Y[i];
            xv *= X[i];
         }
      }
      final DoubleMatrix2D A = new DenseDoubleMatrix2D (deg + 1, deg + 1);
      final DoubleMatrix2D B = new DenseDoubleMatrix2D (deg + 1, 1);
      for (int i = 0; i <= deg; i++) {
         for (int j = 0; j <= deg; j++) {
            final int d = i + j;
            A.setQuick (i, j, xSums[d]);
         }
         B.setQuick (i, 0, xySums[i]);
      }

      return solution(A, B, deg + 1);
   }


   /**
    * Computes the regression coefficients using the
    * least squares method. This is a model for multiple linear regression.
    * There are <SPAN CLASS="MATH"><I>k</I> + 1</SPAN> regression coefficients <SPAN CLASS="MATH"><I>&#946;</I><SUB>j</SUB></SPAN>, and
    * <SPAN CLASS="MATH"><I>k</I></SPAN> regressors variables <SPAN CLASS="MATH"><I>x</I><SUB>j</SUB></SPAN>. The model is
    * 
    * <P></P>
    * <DIV ALIGN="CENTER" CLASS="mathdisplay">
    * <I>y</I> = <I>&#946;</I><SUB>0</SUB> + &sum;<SUB>j=1</SUB><SUP>k</SUP><I>&#946;</I><SUB>j</SUB><I>x</I><SUB>j</SUB>.
    * </DIV><P></P>
    * There are <SPAN CLASS="MATH"><I>n</I></SPAN> data points <SPAN CLASS="MATH"><I>Y</I><SUB>i</SUB></SPAN>, <SPAN CLASS="MATH"><I>X</I><SUB>ij</SUB></SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;,(<I>n</I> - 1)</SPAN>, and each
    * <SPAN CLASS="MATH"><I>X</I><SUB>i</SUB></SPAN> is a <SPAN CLASS="MATH"><I>k</I></SPAN>-dimensional point.
    * Given the response <TT>Y[i]</TT> and the regressor variables <TT>X[i][j]</TT>,
    * 
    * <SPAN CLASS="MATH"><texttt>i</texttt> = 0, 1,&#8230;,(<I>n</I> - 1)</SPAN>, 
    * <SPAN CLASS="MATH"><texttt>j</texttt> = 0, 1,&#8230;,(<I>k</I> - 1)</SPAN>, the method
    * computes and returns the array 
    * <SPAN CLASS="MATH">[<I>&#946;</I><SUB>0</SUB>, <I>&#946;</I><SUB>1</SUB>,&#8230;, <I>&#946;</I><SUB>k</SUB>]</SPAN>.
    * Restriction: <SPAN CLASS="MATH"><I>n</I> &gt; <I>k</I> + 1</SPAN>.
    * 
    * @param X the regressor variables
    * 
    *    @param Y the response
    * 
    *    @return the regression coefficients
    * 
    */
   public static double[] calcCoefficients0 (double[][] X, double[] Y) {
      if (X.length != Y.length)
         throw new IllegalArgumentException ("Lengths of X and Y are not equal");
      if (Y.length <= X[0].length + 1)
         throw new IllegalArgumentException ("Not enough points");

      final int n = Y.length;
      final int k = X[0].length;

      DoubleMatrix2D Xa = new DenseDoubleMatrix2D(n, k+1);
      DoubleMatrix2D Ya = new DenseDoubleMatrix2D(n, 1);

      for (int i = 0; i < n; i++) {
         Xa.setQuick (i, 0, 1.);
         for (int j = 1; j <= k; j++) {
            Xa.setQuick (i, j, X[i][j-1]);
         }
         Ya.setQuick (i, 0, Y[i]);
      }

      return solution(Xa, Ya, k + 1);
   }



   /**
    * Computes the regression coefficients using the
    * least squares method. This is a model for multiple linear regression.
    * There are <SPAN CLASS="MATH"><I>k</I></SPAN> regression coefficients <SPAN CLASS="MATH"><I>&#946;</I><SUB>j</SUB></SPAN>, 
    * <SPAN CLASS="MATH"><I>j</I> = 0, 1,&#8230;,(<I>k</I> - 1)</SPAN> and
    * <SPAN CLASS="MATH"><I>k</I></SPAN> regressors variables <SPAN CLASS="MATH"><I>x</I><SUB>j</SUB></SPAN>. The model is
    * 
    * <P></P>
    * <DIV ALIGN="CENTER" CLASS="mathdisplay">
    * <I>y</I> = &sum;<SUB>j=0</SUB><SUP>k-1</SUP><I>&#946;</I><SUB>j</SUB><I>x</I><SUB>j</SUB>.
    * </DIV><P></P>
    * There are <SPAN CLASS="MATH"><I>n</I></SPAN> data points <SPAN CLASS="MATH"><I>Y</I><SUB>i</SUB></SPAN>, <SPAN CLASS="MATH"><I>X</I><SUB>ij</SUB></SPAN>, 
    * <SPAN CLASS="MATH"><I>i</I> = 0, 1,&#8230;,(<I>n</I> - 1)</SPAN>, and each
    * <SPAN CLASS="MATH"><I>X</I><SUB>i</SUB></SPAN> is a <SPAN CLASS="MATH"><I>k</I></SPAN>-dimensional point.
    * Given the response <TT>Y[i]</TT> and the regressor variables <TT>X[i][j]</TT>,
    * 
    * <SPAN CLASS="MATH"><texttt>i</texttt> = 0, 1,&#8230;,(<I>n</I> - 1)</SPAN>, 
    * <SPAN CLASS="MATH"><texttt>j</texttt> = 0, 1,&#8230;,(<I>k</I> - 1)</SPAN>, the method
    * computes and returns the array 
    * <SPAN CLASS="MATH">[<I>&#946;</I><SUB>0</SUB>, <I>&#946;</I><SUB>1</SUB>,&#8230;, <I>&#946;</I><SUB>k-1</SUB>]</SPAN>.
    * Restriction: <SPAN CLASS="MATH"><I>n</I> &gt; <I>k</I></SPAN>.
    * 
    * @param X the regressor variables
    * 
    *    @param Y the response
    * 
    *    @return the regression coefficients
    * 
    */
   public static double[] calcCoefficients (double[][] X, double[] Y) {
      if (X.length != Y.length)
         throw new IllegalArgumentException ("Lengths of X and Y are not equal");
      if (Y.length <= X[0].length + 1)
         throw new IllegalArgumentException ("Not enough points");

      final int n = Y.length;
      final int k = X[0].length;

      DoubleMatrix2D Xa = new DenseDoubleMatrix2D(n, k);
      DoubleMatrix2D Ya = new DenseDoubleMatrix2D(n, 1);

      for (int i = 0; i < n; i++) {
         for (int j = 0; j < k; j++) {
            Xa.setQuick (i, j, X[i][j]);
         }
         Ya.setQuick (i, 0, Y[i]);
      }

      return solution(Xa, Ya, k);
   }


}
