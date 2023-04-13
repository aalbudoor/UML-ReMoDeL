package rm.cd.v1;

import java.util.List;

import meta.umlcd.ClassType;
import meta.umlcd.Diagram;
import meta.umlcd.EndRole;
import meta.umlcd.Generalisation;
import rm.cd.v2.DocumentToDiagramConverter;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xpath.XPath;

public class GeneralizationConverter extends RelationshipConverter {

	public GeneralizationConverter(DocumentToDiagramConverter parentConverter) {
		super(parentConverter);

	}

	public void addGeneralizationData(Diagram diagram, Document associationDocument) {

		Generalisation generalisation = null;
		EndRole source = null;
		EndRole target = null;

		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(associationDocument);
		for (Content content : classTypes) {

			boolean generalization = false;
			for (Content subContent : content.getContents()) {

				if (subContent.getIdentifier().equals("generalization")) {

					generalization = true;

					break;

				}

			}

			if (generalization) {
				generalisation = new Generalisation();

			}

			ClassType classType1 = new ClassType();
			String id = parentConverter.getValue(content.getAttributes(), "id");
			String name = parentConverter.getValue(content.getAttributes(), "name");
			classType1.setName(name);
			parentConverter.classTypesMap.put(id, classType1);

			EndRole endRole1 = new EndRole();
			endRole1.setName(name);
			endRole1.setType(classType1);

			if (generalization) {

				source = endRole1;

			}

			else {

				target = endRole1;

			}

		}

		if (generalisation != null) {
			generalisation.setSource(source);
			generalisation.setTarget(target);
			diagram.getGeneralisations().add(generalisation);
		}

	}

}
