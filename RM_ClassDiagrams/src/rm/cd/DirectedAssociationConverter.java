package rm.cd;

import java.util.List;

import meta.umlcd.Association;
import meta.umlcd.ClassType;
import meta.umlcd.Diagram;
import meta.umlcd.EndRole;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xpath.XPath;

public class DirectedAssociationConverter {

	DocumentToDiagramConverter parentConverter;

	public DirectedAssociationConverter(DocumentToDiagramConverter parentConverter) {
		super();
		this.parentConverter = parentConverter;
	}

	public void addAssociationData(Diagram diagram, Document associationDocument) {

		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(associationDocument);
		for (Content content : classTypes) {

			EndRole endRole1 = new EndRole();
			ClassType classType1 = new ClassType();
			String id = parentConverter.getValue(content.getAttributes(), "id");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			classType1.setName(name);
			parentConverter.classTypesMap.put(id, classType1);
			endRole1.setType(classType1);

		}

		XPath findDirectedAssociation = new XPath("/uml:Model/packagedElement[@xmi:type=uml:Association]");
		List<Content> directedAssociation = findDirectedAssociation.match(associationDocument);

		Association association = new Association();

		for (Content content : directedAssociation) {

			String id = parentConverter.getValue(content.getAttributes(), "id");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			association.setName(name);

		}

		diagram.getAssociations().add(association);

	}

}
