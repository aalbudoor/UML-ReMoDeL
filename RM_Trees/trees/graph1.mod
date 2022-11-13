model graph : Graph {
   g1 : Graph(vertices = Vertex[
      v1 : Vertex(label = "Root"), 
      v2 : Vertex(label = "Branch1"), 
      v3 : Vertex(label = "Branch2"), 
      v4 : Vertex(label = "Leaf1"), 
      v5 : Vertex(label = "Leaf2"), 
      v6 : Vertex(label = "Leaf3")
   ], edges = Edge[
      e1 : Edge(source = v2, target = v1), 
      e2 : Edge(source = v3, target = v1), 
      e3 : Edge(source = v4, target = v2), 
      e4 : Edge(source = v5, target = v2), 
      e5 : Edge(source = v6, target = v3)
   ])
}
