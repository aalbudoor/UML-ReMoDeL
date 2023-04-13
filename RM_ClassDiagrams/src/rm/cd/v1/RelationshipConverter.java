package rm.cd.v1;

import java.util.List;

import meta.umlcd.Aggregation;
import meta.umlcd.Association;
import meta.umlcd.ClassType;
import meta.umlcd.Composition;
import meta.umlcd.Diagram;
import meta.umlcd.EndRole;
import meta.umlcd.Relationship;
import rm.cd.v2.DocumentToDiagramConverter;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xpath.XPath;

public class RelationshipConverter {

	DocumentToDiagramConverter parentConverter;

	public RelationshipConverter(DocumentToDiagramConverter parentConverter) {
		super();
		this.parentConverter = parentConverter;
	}

	public void addRelationshipData(Diagram diagram, Document associationDocument) {

		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(associationDocument);
		for (Content content : classTypes) {

			ClassType classType1 = new ClassType();
			String id = parentConverter.getValue(content.getAttributes(), "id");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			classType1.setName(name);
			parentConverter.classTypesMap.put(id, classType1);

		}
		boolean aggregationFlag = false;
		boolean compositionFlag = false;
		boolean associationFlag = false;
		String sourceId = null;

		XPath findEndRoles = new XPath("/uml:Model/packagedElement/ownedEnd");
		List<Content> endRoles = findEndRoles.match(associationDocument);
		for (Content content : endRoles) {

			String aggregationIdentifier = parentConverter.getValue(content.getAttributes(), "aggregation");

			if ("shared".equals(aggregationIdentifier)) {

				aggregationFlag = true;
				String type = parentConverter.getValue(content.getAttributes(), "type");
				sourceId = type; // assigning source

			}

			else if ("composite".equals(aggregationIdentifier)) {

				compositionFlag = true;
				String type = parentConverter.getValue(content.getAttributes(), "type");
				sourceId = type; // assigning source

			}

		}
		String target = null;
		XPath findDirectedAssociation = new XPath("/uml:Model/packagedElement[@xmi:type=uml:Association]");
		List<Content> directedAssociation = findDirectedAssociation.match(associationDocument);

		Relationship relationship = null;
		if (aggregationFlag) {
			relationship = new Aggregation();
		} else if (compositionFlag) {
			relationship = new Composition();

		}

		else {
			relationship = new Association();
		}
		// putting owned ends here and making use of new attr aggregation to either
		// create a composition or asscoiation object

		for (Content content : directedAssociation) {

			String name = parentConverter.getValue(content.getAttributes(), "name");
			target = parentConverter.getValue(content.getAttributes(), "navigableOwnedEnd");

			String[] targetParts = target.split(" ");

			if (targetParts.length > 1) {
				target = targetParts[0];
			}

			relationship.setName(name);

		}

		for (Content content : endRoles) {

			EndRole endRole1 = new EndRole();
			String id = parentConverter.getValue(content.getAttributes(), "id");
			String type = parentConverter.getValue(content.getAttributes(), "type");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			endRole1.setName(name);
			endRole1.setType(parentConverter.classTypesMap.get(type));

			if (sourceId != null && sourceId.equals(type)) { // if type matches with source, true
				relationship.setSource(endRole1);
			}

			if (sourceId != null && !sourceId.equals(type)) {
				relationship.setTarget(endRole1);

			}

			if (target.equals("") && relationship.getTarget() == null && relationship.getSource() != null
					&& !relationship.getSource().equals(endRole1)) { // becasue taget was showing empty string in case
																		// of non-directed graphs
				relationship.setTarget(endRole1);

			}
			if (id.equals(target)) { // will not be the case that the id will be null but taget may be
				relationship.setTarget(endRole1);

			}

			if (sourceId == null && relationship.getSource() == null) {
				relationship.setSource(endRole1);
			}

		}

		if (relationship instanceof Association) {

			diagram.getAssociations().add((Association) relationship);
		}

		if (relationship instanceof Aggregation) {

			diagram.getAggregations().add((Aggregation) relationship);
		}
		if (relationship instanceof Composition) {

			diagram.getCompositions().add((Composition) relationship);
		}

	}

}