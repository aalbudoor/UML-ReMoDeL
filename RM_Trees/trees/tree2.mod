model outTree : OutTree {
   t1 : Tree(root = 
      n1 : Node(label = "Root", children = Node[
         n2 : Node(label = "Branch1", children = Node[
            n3 : Node(label = "Leaf1"), 
            n4 : Node(label = "Leaf2")
         ]), 
         n5 : Node(label = "Branch2", children = Node[
            n6 : Node(label = "Leaf3")
         ])
      ])
   )
}
