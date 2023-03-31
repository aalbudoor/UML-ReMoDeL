package rm.cd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meta.umlcd.Aggregation;
import meta.umlcd.Association;
import meta.umlcd.ClassType;
import meta.umlcd.Composition;
import meta.umlcd.Diagram;
import meta.umlcd.EndRole;
import uk.ac.sheffield.jast.xml.Attribute;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xml.Element;
import uk.ac.sheffield.jast.xpath.XPath;

public class RelationshipConverterv2 {
	
	protected Diagram diagram;
	protected Map <String, ClassType> classTypesMap;
	
	
	
	
	public RelationshipConverterv2(Diagram diagram, HashMap<String, ClassType> classTypesMap) {
		super();
		this.diagram = diagram;
		this.classTypesMap = classTypesMap;
	}

	public void findAllAssociations(Document document) {
		XPath xpath = new XPath("/uml:Model//packagedElement[@xmi:type='uml:Association']");
		XPath xpath1 = new XPath("/uml:Model//packagedElement[@xmi:type='uml:AssociationClass']");
			for (Content content : xpath.match(document)) {
					Element assocElem = (Element) content;
					String assocID = assocElem.getValue("xmi:id");
					List<Element> roleElems = assocElem.getChildren("ownedEnd");
					
					int initialRoleElementSize = roleElems.size();
					if (roleElems.size() < 2) 
						roleElems.addAll(findMemberEnds(document, assocID));
					boolean isAggregate = false;
					boolean isComposite = false;
					boolean isAssociation = false;
					for (Element roleElem : roleElems) {
						String value = roleElem.getValue("aggregation");
						if (value != null) {
							isAggregate |= value.equals("shared");
							isComposite |= value.equals("composite");
							//when ownedEnd is 1 then associatian and either aggregation or composition
							isAssociation = (isAggregate || isComposite) && (initialRoleElementSize == 1);
						}
					}
					if (isAggregate)
						createAggregation(assocElem, roleElems);
					else if (isComposite)
						createComposition(assocElem, roleElems);
					else
						createAssociation(assocElem, roleElems);
					if (isAssociation) {
						createAssociation(assocElem, roleElems);
					}
				}
			
			for (Content content : xpath1.match(document)) {
				Element assocElem = (Element) content;
				String assocID = assocElem.getValue("xmi:id");
				List<Element> roleElems = assocElem.getChildren("ownedEnd");
				if (roleElems.size() < 2) 
					roleElems.addAll(findMemberEnds(document, assocID));
					createAssociation(assocElem, roleElems);
				
			}
			}
	
	private ClassType findUMLType(Element roleElem) {
		
		String id = roleElem.getValue("type");
		
		return classTypesMap.get(id);
		
	}
			
			private void createAggregation(Element assocElem, List<Element> roleElems) {
				Aggregation aggregation = new Aggregation();
				aggregation.setName(assocElem.getValue("name"));
//				Aggregation aggregation = new Aggregation(
//						assocElem.getValue("xmi:id"),
//						assocElem.getValue("name"));
				for (Element roleElem : roleElems) {
					EndRole endRole = new EndRole();
					endRole.setName(roleElem.getValue("name"));
					endRole.setType(findUMLType(roleElem));
//					EndRole endRole = new EndRole(
//							roleElem.getValue("xmi:id"),
//							roleElem.getValue("name"),
//							findUMLType(roleElem));
					endRole.setRange(findMultiplicity(roleElem));
					if (roleElem.getValue("aggregation") != null)
						aggregation.setSource(endRole);
					else 
						aggregation.setTarget(endRole);
				}
				addAggregation(aggregation);
			}
			
			private void addAggregation(Aggregation aggregation) {
				diagram.getAggregations().add(aggregation);
				
			}

			private void createComposition(Element assocElem, List<Element> roleElems) {
				Composition composition = new Composition();
				composition.setName(assocElem.getValue("name"));
//				Composition composition = new Composition(
//						assocElem.getValue("xmi:id"),
//						assocElem.getValue("name"));
				for (Element roleElem : roleElems) {
					EndRole endRole = new EndRole();
					endRole.setName(roleElem.getValue("name"));
//					EndRole endRole = new EndRole(
//							roleElem.getValue("xmi:id"),
//							roleElem.getValue("name"),
//							findUMLType(roleElem));
					endRole.setRange(findMultiplicity(roleElem));
					endRole.setType(findUMLType(roleElem));
					if (roleElem.getValue("aggregation") != null)
						composition.setSource(endRole);
					else 
						composition.setTarget(endRole);
				}
				addComposition(composition);
			}
			
			protected String findMultiplicity(Element roleElem) {
			boolean isLower = false;
			boolean isUpper = false;
			String upperValue = null;
			
			
			
			for (Content content: roleElem.getContents()) {
				if (content.getIdentifier().equals("lowerValue")) {
					isLower = true;
					
				}

				
				if (content.getIdentifier().equals("upperValue")) {
					isUpper = true;
					upperValue = getValue(content.getAttributes(), "value");
					

					
				}
				
				
			}
				if (isLower && isUpper && upperValue.equals("1")) {
					
					return  "0..1";
					
				}
				
				if (isLower && isUpper && upperValue.equals("*")) {
					
					return  "0..*";
					
				}
				
				if (!isLower && !isUpper) {
					return "1";
				}
				
				if (isLower && !isUpper ) {
					return "0..1";
				}
				
				
				if (!isLower && isUpper) {
					return "1..*";
				}
				
				return "";
			}

			private void addComposition(Composition composition) {
				diagram.getCompositions().add(composition);
				
			}

			private void createAssociation(Element assocElem, List<Element> roleElems) {
				Association association = new Association();
				association.setName(assocElem.getValue("name"));
//				Association association = new Association(
//						assocElem.getValue("xmi:id"),
//						assocElem.getValue("name"));
				for (Element roleElem : roleElems) {
					EndRole endRole = new EndRole();
					endRole.setName(roleElem.getValue("name"));
//					EndRole endRole = new EndRole(
//							roleElem.getValue("xmi:id"),
//							roleElem.getValue("name"),
//							findUMLType(roleElem));
							endRole.setType(findUMLType(roleElem));
							if (association.getSource() == null ) {
							
							association.setSource(endRole);
							}
							else {
								association.setTarget(endRole);
							}
					endRole.setRange(findMultiplicity(roleElem));
							
//					association.addEndRole(endRole);
				}				
				addAssociation(association);
			}
			
		private void addAssociation(Association association) {
			diagram.getAssociations().add(association);
				
			}

		/** Returns a list of ownedAttribute elements, which have an association attribute, whose value  * is the same as the assocID.
		*/
			private List<Element> findMemberEnds(Document document, String assocID) {
				List<Element> result = new ArrayList<Element>();
				XPath xpath = new XPath("/uml:Model//ownedAttribute[@association]");
				for (Content content : xpath.match(document)) {
					Element attribElem = (Element) content;
					String assocID2 = attribElem.getValue("association");
					if (assocID.equals(assocID2)) {
						result.add(attribElem);
					}
				}
				return result;
			}
			
			
			public String getValue(List<Attribute> attributes, String attributeName) {
				for (uk.ac.sheffield.jast.xml.Attribute attribute : attributes) {
					if (attribute.getName().equals(attributeName)) {
						return attribute.getValue(); // Returns the value of this Attribute.
					}
				}
				return "";

			}


}
