model tree_1 : InTree {
   t1 : Tree(nodes = Node[
      n1 : Node(label = "Root"),
      n2 : Node(label = "Branch1", parent = n1),
      n3 : Node(label = "Branch2", parent = n1),
      n4 : Node(label = "Leaf1", parent = n2),
      n5 : Node(label = "Leaf2", parent = n2),
      n6 : Node(label = "Leaf3", parent = n3)
   ])
}
