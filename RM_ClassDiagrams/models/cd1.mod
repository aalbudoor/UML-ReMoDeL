model cd1 : UmlCd {
   d1 : Diagram(name = "Cycle Shop", basicTypes = BasicType{
      b1 : BasicType(name = "String"), 
      b2 : BasicType(name = "Integer"), 
      b3 : BasicType(name = "Boolean")
   }, classTypes = ClassType{
      c1 : ClassType(name = "Customer", attributes = Attribute{
         a1 : Attribute(name = "forename", type = 
            t1 : Type(name = "String")
         ), 
         a2 : Attribute(name = "surname", type = 
            t2 : Type(name = "String")
         )
      }), 
      c2 : ClassType(name = "Order", attributes = Attribute{
         a3 : Attribute(name = "number", type = 
            t3 : Type(name = "Integer")
         )
      }, operations = Operation{
         o1 : Operation(name = "totalCost", type = 
            t4 : Type(name = "Integer")
         )
      }), 
      c3 : ClassType(name = "Line", attributes = Attribute{
         a4 : Attribute(name = "number", type = 
            t5 : Type(name = "Integer")
         ), 
         a5 : Attribute(name = "quantity", type = 
            t6 : Type(name = "Integer")
         )
      }), 
      c4 : ClassType(name = "Address", attributes = Attribute{
         a6 : Attribute(name = "house", type = 
            t7 : Type(name = "String")
         ), 
         a7 : Attribute(name = "road", type = 
            t8 : Type(name = "String")
         ), 
         a8 : Attribute(name = "city", type = 
            t9 : Type(name = "String")
         ), 
         a9 : Attribute(name = "postcode", type = 
            t10 : Type(name = "String")
         )
      }), 
      c5 : ClassType(name = "FrameSet", attributes = Attribute{
         a10 : Attribute(name = "size", type = 
            t11 : Type(name = "Integer")
         ), 
         a11 : Attribute(name = "shocks", type = 
            t12 : Type(name = "Boolean")
         )
      }), 
      c6 : ClassType(name = "Handlebar", attributes = Attribute{
         a12 : Attribute(name = "style", type = 
            t13 : Type(name = "String")
         )
      }), 
      c7 : ClassType(name = "Wheel", attributes = Attribute{
         a13 : Attribute(name = "diameter", type = 
            t14 : Type(name = "Integer")
         ), 
         a14 : Attribute(name = "tyre", type = 
            t15 : Type(name = "String")
         )
      }), 
      c8 : ClassType(name = "Product", attributes = Attribute{
         a15 : Attribute(name = "brand", type = 
            t16 : Type(name = "String")
         ), 
         a16 : Attribute(name = "name", type = 
            t17 : Type(name = "String")
         ), 
         a17 : Attribute(name = "serial", type = 
            t18 : Type(name = "Integer")
         )
      }), 
      c9 : ClassType(name = "Bicycle")
   }, generalisations = Generalisation{
      g1 : Generalisation(source = 
         e1 : EndRole(name = "Handlebar", type = c6), target = 
         e2 : EndRole(name = "Product", type = c8)
      ), 
      g2 : Generalisation(source = 
         e3 : EndRole(name = "Bicycle", type = c9), target = 
         e4 : EndRole(name = "Product", type = c8)
      )
   }, aggregations = Aggregation{
      a18 : Aggregation(source = 
         e5 : EndRole(name = "handlebar", type = c6, range = "1"), target = 
         e6 : EndRole(name = "bicycle", type = c9, range = "1")
      )
   }, compositions = Composition{
      c10 : Composition(name = "lineToOrderComposite", source = 
         e7 : EndRole(name = "line", type = c3, range = "1..*"), target = 
         e8 : EndRole(name = "order", type = c2, range = "1")
      )
   }, associations = Association{
      a19 : Association(name = "customerToOrderAssociation", source = 
         e9 : EndRole(name = "order", type = c2, range = "0..*"), target = 
         e10 : EndRole(name = "customer", type = c1, range = "1")
      ), 
      a20 : Association(name = "customerToAddressAssociation", source = 
         e11 : EndRole(name = "address", type = c4, range = "1"), target = 
         e12 : EndRole(name = "customer", type = c1, range = "1..*")
      ), 
      a21 : Association(name = "framesetToWheelAssociation1", source = 
         e13 : EndRole(name = "wheel", type = c7, range = "1"), target = 
         e14 : EndRole(name = "frameset", type = c5, range = "1")
      ), 
      a22 : Association(name = "frameSetToAssociation2", source = 
         e15 : EndRole(name = "wheel", type = c7, range = "1"), target = 
         e16 : EndRole(name = "frameset", type = c5, range = "1")
      ), 
      a23 : Association(name = "lineToProductAssociation", source = 
         e17 : EndRole(name = "product", type = c8, range = "1"), target = 
         e18 : EndRole(name = "line", type = c3, range = "0..*")
      )
   })
}
