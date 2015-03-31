

/*
 * Class:        JohnsonSystem
 * Description:  Johnson system of distributions
 * Environment:  Java
 * Software:     SSJ
 * Copyright (C) 2001  Pierre L'Ecuyer and Universite de Montreal
 * Organization: DIRO, Universite de Montreal
 * @author       Richard Simard
 * @since        july 2012

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


/**
 * This class contains common parameters and methods for
 * the <EM>Johnson</EM> system of distributions
 * with shape parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN> and 
 * <SPAN CLASS="MATH"><I>&#948;</I> &gt; 0</SPAN>, location parameter <SPAN CLASS="MATH"><I>&#958;</I></SPAN>,
 * and scale parameter <SPAN CLASS="MATH"><I>&#955;</I> &gt; 0</SPAN>.
 * Denoting 
 * <SPAN CLASS="MATH"><I>T</I> = (<I>X</I> - <I>&#958;</I>)/<I>&#955;</I></SPAN>, the variable 
 * <SPAN CLASS="MATH"><I>Z</I> = <I>&#947;</I> + <I>&#948;f</I> (<I>T</I>)</SPAN>
 * is a standard normal variable, where <SPAN CLASS="MATH"><I>f</I> (<I>t</I>)</SPAN> is one of the following transformations:
 * 
 * <P>
 * <DIV ALIGN="CENTER">
 * <TABLE CELLPADDING=3 BORDER="1">
 * <TR><TD ALIGN="CENTER">Family</TD>
 * <TD ALIGN="CENTER"><SPAN CLASS="MATH"><I>f</I> (<I>t</I>)</SPAN></TD>
 * <TD ALIGN="CENTER">&nbsp;</TD>
 * </TR>
 * <TR><TD ALIGN="CENTER"><SPAN CLASS="MATH"><I>S</I><SUB>L</SUB></SPAN></TD>
 * <TD ALIGN="CENTER"><SPAN CLASS="MATH">ln(<I>t</I>)</SPAN></TD>
 * <TD ALIGN="CENTER">&nbsp;</TD>
 * </TR>
 * <TR><TD ALIGN="CENTER"><SPAN CLASS="MATH"><I>S</I><SUB>B</SUB></SPAN></TD>
 * <TD ALIGN="CENTER">
 * <SPAN CLASS="MATH">ln(<I>t</I>/(1 - <I>t</I>))</SPAN></TD>
 * <TD ALIGN="CENTER">&nbsp;</TD>
 * </TR>
 * <TR><TD ALIGN="CENTER"><SPAN CLASS="MATH"><I>S</I><SUB>U</SUB></SPAN></TD>
 * <TD ALIGN="CENTER">
 * <SPAN CLASS="MATH">ln(<I>t</I> + (1 + t^2)<SUP>1/2</SUP>)</SPAN></TD>
 * <TD ALIGN="CENTER">&nbsp;</TD>
 * </TR>
 * </TABLE>
 * </DIV>
 * 
 */
abstract class JohnsonSystem extends ContinuousDistribution {
   protected double gamma;
   protected double delta;
   protected double xi;
   protected double lambda;


   /**
    * Constructs a <TT>JohnsonSystem</TT> object
    *    with shape parameters 
    * <SPAN CLASS="MATH"><I>&#947;</I> = <texttt>gamma</texttt></SPAN> and 
    * <SPAN CLASS="MATH"><I>&#948;</I> = <texttt>delta</texttt></SPAN>,
    *    location parameter 
    * <SPAN CLASS="MATH"><I>&#958;</I> = <texttt>xi</texttt></SPAN>, and scale parameter
    *    
    * <SPAN CLASS="MATH"><I>&#955;</I> = <texttt>lambda</texttt></SPAN>.
    * 
    */
   protected JohnsonSystem (double gamma, double delta, double xi,
                            double lambda) {
      setParams0 (gamma, delta, xi, lambda);
   }


   /**
    * Returns the value of <SPAN CLASS="MATH"><I>&#947;</I></SPAN>.
    * 
    */
   public double getGamma() {
      return gamma;
   }


   /**
    * Returns the value of <SPAN CLASS="MATH"><I>&#948;</I></SPAN>.
    * 
    */
   public double getDelta() {
      return delta;
   }


   /**
    * Returns the value of <SPAN CLASS="MATH"><I>&#958;</I></SPAN>.
    * 
    */
   public double getXi() {
      return xi;
   }


   /**
    * Returns the value of <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    */
   public double getLambda() {
      return lambda;
   }



   /**
    * Sets the value of the parameters <SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN> and
    *   <SPAN CLASS="MATH"><I>&#955;</I></SPAN>.
    * 
    */
   protected void setParams0(double gamma, double delta, double xi,
                             double lambda) {
      if (lambda <= 0)
         throw new IllegalArgumentException ("lambda <= 0");
      if (delta <= 0)
         throw new IllegalArgumentException ("delta <= 0");
      this.gamma = gamma;
      this.delta = delta;
      this.xi = xi;
      this.lambda = lambda;
   }


   /**
    * Return an array containing the parameters of the current distribution.
    *    This array is put in regular order: [<SPAN CLASS="MATH"><I>&#947;</I></SPAN>, <SPAN CLASS="MATH"><I>&#948;</I></SPAN>, <SPAN CLASS="MATH"><I>&#958;</I></SPAN>, <SPAN CLASS="MATH"><I>&#955;</I></SPAN>].
    * 
    * 
    */
   public double[] getParams () {
      double[] retour = {gamma, delta, xi, lambda};
      return retour;
   }


   public String toString () {
      return getClass().getSimpleName() + " : gamma = " + gamma + ", delta = " + delta + ", xi = " + xi + ", lambda = " + lambda;
   }

}
