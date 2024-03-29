/**
 * ReMoDeL (Reusable Model Design Languages) tools for developing
 * and compiling metamodels, models and model transformations.
 *
 * Copyright (c) 2020-2022 Anthony J H Simons
 * University of Sheffield, UK.  All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This software is proprietary software, whose IP is owned by
 * Anthony J H Simons and the University of Sheffield.  All use of
 * this software must be covered by a license drawn up with the
 * owners.
 *
 * Please contact the Department of Computer Science, University of
 * Sheffield, Regent Court, 211 Portobello, Sheffield S1 4DP, UK or
 * visit www.sheffield.ac.uk/dcs if you need additional information
 * or have any questions.
 */

package meta.umlcd;


/**
 * Generalisation is derived from the ReMoDeL concept UmlCd_Generalisation.
 * Generated by ReMoDeL on Tue Nov 01 14:39:19 GMT 2022.
 * 
 * @author ReMoDeL by Anthony J H Simons
 * @version 3.1
 */
public class Generalisation extends Relationship {

   /**
    * Method getName() is derived from the operation getName.
    */
   @Override
   public String getName() {
      if (! name.equals("")) 
         return name;
      else 
         return source.getType().getName().concat(
            "KindOf").concat(
            target.getType().getName());
   }

   /**
    * Redefined method setSource() is derived from the component source.
    */
   @Override
   public Generalisation setSource(EndRole source) {
      return (Generalisation) super.setSource(source);
   }

   /**
    * Redefined method setTarget() is derived from the component target.
    */
   @Override
   public Generalisation setTarget(EndRole target) {
      return (Generalisation) super.setTarget(target);
   }

   /**
    * Redefined method setName() is derived from the attribute name.
    */
   @Override
   public Generalisation setName(String name) {
      return (Generalisation) super.setName(name);
   }

   /**
    * Default constructor Generalisation() is derived from the concept Generalisation.
    */
   public Generalisation() {
   }

}
