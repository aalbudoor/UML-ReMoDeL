package rm.cd;

import java.util.List;

import meta.umlcd.Association;
import meta.umlcd.ClassType;
import meta.umlcd.Diagram;
import meta.umlcd.EndRole;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xpath.XPath;

public class AssociationConverter {

	DocumentToDiagramConverter parentConverter;

	public AssociationConverter(DocumentToDiagramConverter parentConverter) {
		super();
		this.parentConverter = parentConverter;
	}

	public void addAssociationData(Diagram diagram, Document associationDocument) {

		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(associationDocument);
		for (Content content : classTypes) {

			ClassType classType1 = new ClassType();
			String id = parentConverter.getValue(content.getAttributes(), "id");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			classType1.setName(name);
			parentConverter.classTypesMap.put(id, classType1);

		}
		String target = null;
		XPath findDirectedAssociation = new XPath("/uml:Model/packagedElement[@xmi:type=uml:Association]");
		List<Content> directedAssociation = findDirectedAssociation.match(associationDocument);

		Association association = new Association();

		for (Content content : directedAssociation) {

			String name = parentConverter.getValue(content.getAttributes(), "name");
			target =parentConverter.getValue(content.getAttributes(), "navigableOwnedEnd");
			association.setName(name);

		}
		
		XPath findEndRoles = new XPath("/uml:Model/packagedElement/ownedEnd[@xmi:type=uml:Property]");
		List<Content> endRoles = findEndRoles.match(associationDocument);
		for (Content content : endRoles) {

			EndRole endRole1 = new EndRole();
			String id = parentConverter.getValue(content.getAttributes(), "id");
			String type = parentConverter.getValue(content.getAttributes(), "type");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			endRole1.setName(name);
			endRole1.setType(parentConverter.classTypesMap.get(type));
			
			if (target.equals(id)) {
				association.setTarget(endRole1);
				
				
			} else {
				association.setSource(endRole1);
			}

		}
		

	

		diagram.getAssociations().add(association);

	}

}
