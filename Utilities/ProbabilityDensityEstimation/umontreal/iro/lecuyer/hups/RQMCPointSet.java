

/*
 * Class:        RQMCPointSet
 * Description:  randomized quasi-Monte Carlo simulations
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

/**
 * This class is used for <SPAN  CLASS="textit">randomized quasi-Monte Carlo</SPAN> (RQMC) simulations.
 *  The idea is to randomize a point set so that:
 * 
 * <UL>
 * <LI>it retains its high uniformity when taken as a set and
 * </LI>
 * <LI>each individual point is a random vector with the uniform
 * distribution over <SPAN CLASS="MATH">(0, 1)<SUP>s</SUP></SPAN>.
 * </LI>
 * </UL>
 *  A RQMC point set is one that satisfies these two conditions. One simple
 * randomization that satisfies these conditions for an arbirary point set <SPAN CLASS="MATH"><I>P</I><SUB>n</SUB></SPAN>
 *   is a random shift modulo 1:
 * Generate a single point 
 * <SPAN CLASS="MATH"><B>U</B></SPAN> uniformly over <SPAN CLASS="MATH">(0, 1)<SUP>s</SUP></SPAN> and add it
 *  to each point of <SPAN CLASS="MATH"><I>P</I><SUB>n</SUB></SPAN>, modulo 1, coordinate-wise.
 * Another one is a random digital shift in base <SPAN CLASS="MATH"><I>b</I></SPAN>: generate again 
 * <SPAN CLASS="MATH"><B>U</B></SPAN> uniformly over
 *  <SPAN CLASS="MATH">(0, 1)<SUP>s</SUP></SPAN>, expand each of its coordinates in base <SPAN CLASS="MATH"><I>b</I></SPAN>, and add the
 * digits, modulo <SPAN CLASS="MATH"><I>b</I></SPAN>, to the corresponding digits of each point of <SPAN CLASS="MATH"><I>P</I><SUB>n</SUB></SPAN>.
 * 
 */
public class RQMCPointSet  {
   private PointSet set;
   private PointSetRandomization rand;



   /**
    * Constructor with the point set <TT>set</TT> and the randomization <TT>rand</TT>.
    * 
    * @param set the point set
    * 
    *    @param rand the randomization
    * 
    */
   public RQMCPointSet (PointSet set, PointSetRandomization rand)  {
      this.rand = rand;
      this.set = set;
   }
   


   /**
    * Randomizes the point set. The randomization and the point set
    *  are those of this object.
    * 
    */
   public void randomize()  {
       rand.randomize(set);
   }
   


   /**
    * Returns a new point set iterator for the point set associated to this object.
    * 
    * @return point set iterator for the point set
    * 
    */
   public PointSetIterator iterator() {
      return set.iterator();
   }


   /**
    * Returns the point set associated to this object.
    * 
    * @return the point set associated to this object
    * 
    */
   public PointSet getPointSet() {
      return set;
   }


   /**
    * Returns the randomization associated to this object.
    * 
    * @return the randomization associated to this object
    * 
    */
   public PointSetRandomization getRandomization() {
      return rand;
   }

}
