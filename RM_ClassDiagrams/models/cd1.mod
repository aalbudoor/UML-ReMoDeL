model cd1 : UmlCd {
   d1 : Diagram(name = "RM_TestCase2", basicTypes = BasicType{
      b1 : BasicType(name = "String"), 
      b2 : BasicType(name = "Integer"), 
      b3 : BasicType(name = "Real"), 
      b4 : BasicType(name = "Boolean")
   }, classTypes = ClassType{
      c1 : ClassType(name = "Class1", attributes = Attribute{
         a1 : Attribute(name = "name", type = 
            t1 : Type(name = "String")
         ), 
         a2 : Attribute(name = "class2", type = 
            t2 : Type(name = "_C1ca8NKoEe2GbMNhqqApLg")
         )
      }, operations = Operation{
         o1 : Operation(name = "getName", type = 
            t3 : Type(name = "String")
         )
      }), 
      c2 : ClassType(name = "Class2", attributes = Attribute{
         a3 : Attribute(name = "age", type = 
            t4 : Type(name = "Integer")
         ), 
         a4 : Attribute(name = "weight", type = 
            t5 : Type(name = "Real")
         )
      }, operations = Operation{
         o2 : Operation(name = "setAge", type = 
            t6 : Type(name = "Integer"), arguments = Variable[
            v1 : Variable(name = "age", type = 
               b5 : BasicType(name = "Integer")
            )
         ]), 
         o3 : Operation(name = "isActive", type = 
            t7 : Type(name = "Boolean"), arguments = Variable[
            v2 : Variable(name = "activity", type = b4)
         ])
      }), 
      c3 : ClassType(name = "Class3"), 
      c4 : ClassType(name = "Class4", operations = Operation{
         o4 : Operation(name = "setClassType2", type = 
            t8 : Type(name = "uml:Operation"), arguments = Variable[
            v3 : Variable(type = c1)
         ])
      }), 
      c5 : ClassType(name = "Class5", operations = Operation{
         o5 : Operation(name = "setClassType1", type = 
            t9 : Type(name = "uml:Operation")
         )
      })
   }, generalisations = Generalisation{
      g1 : Generalisation(source = 
         e1 : EndRole(name = "Class4", type = c4), target = 
         e2 : EndRole(name = "Class2", type = c2)
      ), 
      g2 : Generalisation(source = 
         e3 : EndRole(name = "Class5", type = c5), target = 
         e4 : EndRole(name = "Class2", type = c2)
      )
   }, associations = Association{
      a5 : Association(source = 
         e5 : EndRole(name = "class1", type = c1, range = "1"), target = 
         e6 : EndRole(name = "class2", type = c2, range = "0..1")
      ), 
      a6 : Association(source = 
         e7 : EndRole(name = "class3", type = c3, range = "1"), target = 
         e8 : EndRole(name = "class2", type = c2, range = "1")
      )
   })
}
