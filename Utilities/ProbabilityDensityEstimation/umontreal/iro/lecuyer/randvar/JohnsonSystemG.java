

/*
 * Class:        JohnsonSystemG
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

package umontreal.iro.lecuyer.randvar;
import umontreal.iro.lecuyer.rng.*;
import umontreal.iro.lecuyer.probdist.*;



/**
 * This class contains common parameters and methods for
 * the random variate generators associated with
 * the <EM>Johnson</EM> system of distributions.
 * See the definitions of
 * {@link umontreal.iro.lecuyer.probdist.JohnsonSLDist JohnsonSLDist},
 * {@link umontreal.iro.lecuyer.probdist.JohnsonSBDist JohnsonSBDist}, and
 * {@link umontreal.iro.lecuyer.probdist.JohnsonSUDist JohnsonSUDist}
 * in package <TT>probdist</TT>.
 * 
 */
abstract class JohnsonSystemG extends RandomVariateGen {
   protected double gamma;
   protected double delta;
   protected double xi;
   protected double lambda;


   /**
    * Constructs a <TT>JohnsonSystemG</TT> object
    *    with shape parameters 
    * <SPAN CLASS="MATH"><I>&#947;</I> = <texttt>gamma</texttt></SPAN> and 
    * <SPAN CLASS="MATH"><I>&#948;</I> = <texttt>delta</texttt></SPAN>,
    *    location parameter 
    * <SPAN CLASS="MATH"><I>&#958;</I> = <texttt>xi</texttt></SPAN>, and scale parameter
    *    
    * <SPAN CLASS="MATH"><I>&#955;</I> = <texttt>lambda</texttt></SPAN>.
    * 
    */
   protected JohnsonSystemG (RandomStream s, double gamma, double delta,
                             double xi, double lambda) {
      super (s, null);
      setParams (gamma, delta, xi, lambda);
   }


   /**
    * Constructs a <TT>JohnsonSystemG</TT> object with parameters
    *    obtained from distribution <TT>dist</TT>.
    * 
    */
   protected JohnsonSystemG (RandomStream s, ContinuousDistribution dist) {
      super (s, dist);
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
    *   <SPAN CLASS="MATH"><I>&#955;</I></SPAN> for this object.
    * 
    */
   protected void setParams (double gamma, double delta, double xi,
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

}
