package rm.cd;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import meta.umlcd.BasicType;
import meta.umlcd.ClassType;
import meta.umlcd.Diagram;
import meta.umlcd.Type;
import uk.ac.sheffield.jast.xml.Attribute;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xpath.XPath;

/*
 * CLass responsible for converting document to diagram 
 */
public class DocumentToDiagramConverter {

	public Diagram convert(Document document) {

		Diagram diagram = new Diagram();

		setClassTypes(diagram, document);
		setBasicTypes(diagram, document);

		return diagram;

	}

	/**
	 * 
	 * @param diagram
	 * @param document
	 * 
	 *                 Method: sets class types for a given document
	 * 
	 */
	private void setClassTypes(Diagram diagram, Document document) {
		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(document);
		for (Content content : classTypes) {
			String className = getValue(content.getAttributes(), "name");
			ClassType classType = new ClassType();
			classType.setName(className);
			for (Content classContent : content.getContents()) {
				if (classContent.getIdentifier().equals("ownedAttribute")) {
					setAttributes(classContent, classType);
				}

				if (classContent.getIdentifier().equals("ownedOperation")) {
					setOperations(classContent, classType);
				}
			}

			diagram.getClassTypes().add(classType);
		}

	}

	/**
	 * @param classContent
	 * @param classType
	 * 
	 *                     method sets Attribute for a given class Type
	 */
	private void setAttributes(Content classContent, ClassType classType) {
		meta.umlcd.Attribute attr = new meta.umlcd.Attribute();
		String typeValue = getValue(classContent.getAttributes(), "type");
		String name = getValue(classContent.getAttributes(), "name");
		Type type = new Type();
		type.setName(typeValue);
		attr.setType(type);
		attr.setName(name);

		for (Content typeContent : classContent.getContents()) {
			String basicTypeValue = getValue(typeContent.getAttributes(), "href");
			if (basicTypeValue != null && !basicTypeValue.equals("")) {
				String basicTypeName = basicTypeValue.split("#")[1];
				Type attributeType = new Type();
				attributeType.setName(basicTypeName);
				attr.setType(attributeType);
			}

		}

		classType.getAttributes().add(attr);

	}

	/**
	 * @param classContent
	 * @param classType    Method sets Operations for a given ClassType
	 */
	private void setOperations(Content classContent, ClassType classType) {

		meta.umlcd.Operation oper = new meta.umlcd.Operation();
		String typeValue = getValue(classContent.getAttributes(), "type");
		String name = getValue(classContent.getAttributes(), "name");
		Type type = new Type();
		type.setName(typeValue);
		oper.setType(type);
		oper.setName(name);

		for (Content layerTypeContent : classContent.getContents()) {

			for (Content typeContent : layerTypeContent.getContents()) {
				String basicTypeValue = getValue(typeContent.getAttributes(), "href");

				//For the path String this filers out the 0 index and replaces with empty string, second index is taken for href
				if (basicTypeValue != null && !basicTypeValue.equals("")) {
					String basicTypeName = basicTypeValue.split("#")[1];
					Type operationType = new Type();
					operationType.setName(basicTypeName);
					oper.setType(operationType);

				}
			}
		}
		classType.getOperations().add(oper);

	}

	/**
	 * @param diagram
	 * @param document
	 * 
	 *                 method: sets basic type for given document
	 */
	private void setBasicTypes(Diagram diagram, Document document) {
		XPath findBasicTypes = new XPath("//type[@xmi:type=uml:PrimitiveType]/@href");
		List<Content> basicTypes = findBasicTypes.match(document);
		Set<String> basicTypeNames = new HashSet<>();
		for (Content content : basicTypes) {
			Attribute attribute = (Attribute) content;
			String basicTypeName = attribute.getValue().split("#")[1];
			if (!basicTypeNames.contains(basicTypeName)) {
				BasicType basicType1 = new BasicType();
				basicType1.setName(basicTypeName);
				diagram.getBasicTypes().add(basicType1);
				basicTypeNames.add(basicTypeName);
			}

		}

	}

	/**
	 * @param attributes
	 * @param attributeName
	 * @return
	 * 
	 *         Method returns attribute value for the given name
	 */
	private String getValue(List<Attribute> attributes, String attributeName) {
		for (uk.ac.sheffield.jast.xml.Attribute attribute : attributes) {
			if (attribute.getName().equals(attributeName)) {
				return attribute.getValue();
			}
		}
		return "";

	}

}
