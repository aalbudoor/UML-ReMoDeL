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
 * Attribute is derived from the ReMoDeL concept UmlCd_Attribute.
 * Generated by ReMoDeL on Tue Nov 01 14:39:19 GMT 2022.
 * 
 * @author ReMoDeL by Anthony J H Simons
 * @version 3.1
 */
public class Attribute extends Typed {

   /**
    * Redefined method getType() is derived from the reference type.
    */
   @Override
   public BasicType getType() {
      return (BasicType) type;
   }

   /**
    * Field id is derived from the attribute id.
    */
   protected boolean id;

   /**
    * Method getId() is derived from the attribute id.
    * Has nothing to do with XMI file it a property of ReMoDel class for Attribute 
    * in class diagram you can put a constraint next to an attribute (used as a primary key)
    * the id is a boolean value attribute of the remodel attribute. 
    * which is set to true if the current remodel attribute is the identifying attribute in the uml sense
    */
   public boolean getId() {
      return id;
   }

   /**
    * Method setId() is derived from the attribute id.
    */
   public Attribute setId(boolean id) {
      this.id = id;
      return this;
   }

   /**
    * Redefined method setType() is derived from the reference type.
    */
   @Override
   public Attribute setType(Type type) {
      return (Attribute) super.setType(type);
   }

   /**
    * Redefined method setName() is derived from the attribute name.
    */
   @Override
   public Attribute setName(String name) {
      return (Attribute) super.setName(name);
   }

   /**
    * Default constructor Attribute() is derived from the concept Attribute.
    */
   public Attribute() {
   }

}
