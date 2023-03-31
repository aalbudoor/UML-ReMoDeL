package rm.cd;

import java.io.File;
import java.io.IOException;

import meta.umlcd.Diagram;
import uk.ac.sheffield.jast.XMLReader;
import uk.ac.sheffield.jast.xml.Document;

public class Demov2 {

	public static void main(String[] args) {
		try {

			File file = new File("xmi/RM_CDFinal.xml"); // Or whatever file
			XMLReader reader = new XMLReader(file); // Uses UTF-8
			Document document = reader.readDocument();

			DocumentToDiagramConverter converter = new DocumentToDiagramConverter();

			Diagram diagram = converter.convert(document);
			File associationFile = new File("xmi/RM_CDFinal.xml");
			XMLReader reader1 = new XMLReader(associationFile); 
			Document associationDocument = reader1.readDocument();
			//comment when extracting generalisation
			RelationshipConverterv2 relationshipConverter = new RelationshipConverterv2(diagram, converter.classTypesMap);
			relationshipConverter.findAllAssociations(associationDocument);
			
			GeneralizationConverterv2 generalizationConverter = new GeneralizationConverterv2(diagram, converter.classTypesMap);
			generalizationConverter.findAllGeneralization(document);
			
			reader1.close();
			reader.close();

		} catch (IOException ex) {

			ex.printStackTrace();

		}

	}

}
