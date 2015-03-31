

/*
 * Class:        KorobovLattice
 * Description:  Korobov lattice
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

package umontreal.iro.lecuyer.hups;

import umontreal.iro.lecuyer.util.PrintfFormat;


/**
 * This class implements <SPAN  CLASS="textit">Korobov lattices</SPAN>, which represents the same point
 * sets as in class {@link LCGPointSet}, but implemented differently.
 * The parameters are the modulus <SPAN CLASS="MATH"><I>n</I></SPAN> and the multiplier <SPAN CLASS="MATH"><I>a</I></SPAN>, for an arbitrary
 * integer 
 * <SPAN CLASS="MATH">1&nbsp;&lt;=&nbsp;<I>a</I> &lt; <I>n</I></SPAN>. [When  <SPAN CLASS="MATH"><I>a</I></SPAN> is outside the interval <SPAN CLASS="MATH">[1, <I>n</I>)</SPAN>, then we
 * replace  <SPAN CLASS="MATH"><I>a</I></SPAN> by (<SPAN CLASS="MATH"><I>a</I> mod <I>n</I></SPAN>) in all calculations.]
 * The number of points is <SPAN CLASS="MATH"><I>n</I></SPAN>, their dimension is
 * <SPAN CLASS="MATH"><I>s</I></SPAN>, and they are defined by
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <B>u</B><SUB>i</SUB> = (<I>i</I>/<I>n</I>)(1, <I>a</I>, <I>a</I><SUP>2</SUP>,&#8230;, <I>a</I><SUP>s-1</SUP>) mod 1
 * </DIV><P></P>
 * for 
 * <SPAN CLASS="MATH"><I>i</I> = 0,..., <I>n</I> - 1</SPAN>.
 * 
 * <P>
 * It is also possible to build a ``shifted'' Korobov lattice with the first
 * <SPAN CLASS="MATH"><I>t</I></SPAN> coordinates rejected. The <SPAN CLASS="MATH"><I>s</I></SPAN>-dimensionnal points are then defined as
 * 
 * <P></P>
 * <DIV ALIGN="CENTER" CLASS="mathdisplay">
 * <B>u</B><SUB>i</SUB> = (<I>i</I>/<I>n</I>)(<I>a</I><SUP>t</SUP>, <I>a</I><SUP>t+1</SUP>, <I>a</I><SUP>t+2</SUP>,&#8230;, <I>a</I><SUP>t+s-1</SUP>) mod 1
 * </DIV><P></P>
 * for 
 * <SPAN CLASS="MATH"><I>i</I> = 0,..., <I>n</I> - 1</SPAN> and fixed <SPAN CLASS="MATH"><I>t</I></SPAN>.
 * 
 */
public class KorobovLattice extends Rank1Lattice  {
   protected int genA;            // multiplier a
   private int genT;              // shift t

   private void initN (int n, int t) {
      int a = (genA % n) + (genA < 0 ? n : 0);
      genT = t;
      long[] B = new long[dim];
      B[0] = 1;
      int j;
      for (j = 0; j < t; j++)
         B[0] *= a;
      v[0] = B[0] * normFactor;
      for (j = 1; j < dim; j++) {
         B[j] = (a * B[j - 1]) % n;
         v[j] = normFactor * B[j];
      }
   }

   // Method modPower is inherited from Rank1Lattice.


   /**
    * Instantiates a Korobov lattice point set with modulus <SPAN CLASS="MATH"><I>n</I></SPAN> and
    *    multiplier <SPAN CLASS="MATH"><I>a</I></SPAN> in dimension <SPAN CLASS="MATH"><I>s</I></SPAN>.
    * 
    */
   public KorobovLattice (int n, int a, int s)  {
      super (n, null, 0);
      genA = a;
      dim = s;
      v = new double[s];
      initN(n, 0);
   }


   /**
    * Instantiates a shifted Korobov lattice point set with modulus <SPAN CLASS="MATH"><I>n</I></SPAN> and
    *    multiplier <SPAN CLASS="MATH"><I>a</I></SPAN> in dimension <SPAN CLASS="MATH"><I>s</I></SPAN>. The first <SPAN CLASS="MATH"><I>t</I></SPAN> coordinates of a
    *    standard Korobov lattice are dropped as described above.
    *    The case <SPAN CLASS="MATH"><I>t</I> = 0</SPAN> corresponds to the standard  Korobov lattice.
    * 
    */
   public KorobovLattice (int n, int a, int s, int t)  {
      super (n, null, 0);
      if (t < 1)
         throw new IllegalArgumentException
            ("KorobovLattice: must have 0 < t");
      dim = s;
      genA = a;
      v = new double[s];
      initN(n, t);
   }


   /**
    * Resets the number of points of the lattice to <SPAN CLASS="MATH"><I>n</I></SPAN>. The values of <SPAN CLASS="MATH"><I>s</I></SPAN>,
    *   <SPAN CLASS="MATH"><I>a</I></SPAN> and <SPAN CLASS="MATH"><I>t</I></SPAN> are unchanged.
    * 
    */
   public void setNumPoints (int n)  {
      initN(n, genT);
   }


   /**
    * Returns the multiplier <SPAN CLASS="MATH"><I>a</I></SPAN> of the lattice.
    * (The original one before it is reset to <SPAN CLASS="MATH"><I>a</I> mod <I>n</I></SPAN>).
    * 
    */
   public int getA()  {
      return genA;
   }



   public String toString() {
      StringBuffer sb = new StringBuffer ("KorobovLattice:" +
                                           PrintfFormat.NEWLINE);
      sb.append ("Multiplier a: " + genA + PrintfFormat.NEWLINE);
      sb.append (super.toString());
      return sb.toString();
   }
}
