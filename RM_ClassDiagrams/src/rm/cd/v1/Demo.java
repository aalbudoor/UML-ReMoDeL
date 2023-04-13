
package rm.cd.v2;

import java.io.File;
import java.io.IOException;

import meta.umlcd.Diagram;
import rm.cd.v2.DocumentToDiagramConverter;
import uk.ac.sheffield.jast.XMLReader;
import uk.ac.sheffield.jast.xml.Document;

public class Demo {

	public static void main(String[] args) {
		try {

			File file = new File("xmi/RM_CD7.xml"); // Or whatever file
			XMLReader reader = new XMLReader(file); // Uses UTF-8
			Document document = reader.readDocument();

			DocumentToDiagramConverter converter = new DocumentToDiagramConverter();

			Diagram diagram = converter.convert(document);
			File associationFile = new File("xmi/RM_CD7.xml");
			XMLReader reader1 = new XMLReader(associationFile);
			Document associationDocument = reader1.readDocument();
			// comment when extracting generalisation
			RelationshipConverter relationshipConverter = new RelationshipConverter(converter);
			relationshipConverter.addRelationshipData(diagram, associationDocument);

			GeneralizationConverter generalizationConverter = new GeneralizationConverter(converter);
			generalizationConverter.addGeneralizationData(diagram, associationDocument);

			reader1.close();
			reader.close();

		} catch (IOException ex) {

			ex.printStackTrace();

		}

	}
}
