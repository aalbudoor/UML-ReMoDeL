package rm.cd;

import java.util.HashMap;
import java.util.List;

import meta.umlcd.BasicType;
import meta.umlcd.ClassType;
import meta.umlcd.Diagram;
import meta.umlcd.Type;
import meta.umlcd.Variable;
import uk.ac.sheffield.jast.xml.Attribute;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xml.Element;
import uk.ac.sheffield.jast.xpath.XPath;

/*
 * CLass responsible for converting document to diagram 
 */
public class DocumentToDiagramConverter {

	HashMap<String, BasicType> basicTypesMap = new HashMap<>();
	public HashMap<String, ClassType> classTypesMap = new HashMap<>();

	public Diagram convert(Document document) {

		Diagram diagram = new Diagram();
		
		
		setBasicTypes(diagram, document);
		setClassTypes(diagram, document);

		return diagram;

	}

	/**
	 * 
	 * @param diagram
	 * @param document
	 * 
	 *                 Method sets class types for a given document
	 * 
	 */
	private void setClassTypes(Diagram diagram, Document document) {
		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(document);
		for (Content content : classTypes) {
			Element classElem = (Element) content;
			String className = classElem.getValue("name");
			String id = classElem.getValue("id");

//			String className = getValue(content.getAttributes(), "name");
			ClassType classType = new ClassType();
			classType.setName(className);
			classTypesMap.put(id, classType);
			diagram.getClassTypes().add(classType);
		}
//			for (Element attribElem : classElem.getChildren("ownedAttribute")) {
//				meta.umlcd.Attribute attrib = new meta.umlcd.Attribute();
//				attrib.setName(attribElem.getValue("name"));
//				Element typeElem = attribElem.getChild("type");
//				BasicType type = getBasicType(typeElem);
//				attrib.setType(type);
//				classType.getAttributes().add(attrib);
//			}
	for (Content content : classTypes) {
		Element classElem = (Element) content;
		String id = classElem.getValue("id");
		ClassType classType = classTypesMap.get(id);

			for (Content classContent : content.getContents()) {
				if (classContent.getIdentifier().equals("ownedAttribute")) {
					setAttributes(classContent, classType);
				}

				if (classContent.getIdentifier().equals("ownedOperation")) {
					setOperations(classContent, classType);
				}
			}
			
		}
}

	

	private BasicType getBasicType(Element elem) {
		String hrefValue = elem.getValue("href");
		String typeName = hrefValue.split("#")[1];
		return basicTypesMap.get(typeName);
	}

	/**
	 * @param classContent
	 * @param classType
	 * 
	 *                     Method sets Attribute for a given class Type
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
	 * @param classType
	 * 
	 *                     Method sets Operations for a given ClassType
	 */
	private void setOperations(Content classContent, ClassType classType) {

		meta.umlcd.Operation oper = new meta.umlcd.Operation();
		String typeValue = getValue(classContent.getAttributes(), "type");
		String name = getValue(classContent.getAttributes(), "name");
		Type type = new Type();
		type.setName(typeValue);
		oper.setType(type);
		oper.setName(name);
		String parameterName = null;
		String direction = null;
		for (Content layerTypeContent : classContent.getContents()) {

			if (layerTypeContent.getIdentifier().equals("ownedParameter")) {
				parameterName = getValue(layerTypeContent.getAttributes(), "name");
				//if direction is return dont add parameter as argument
				direction = getValue(layerTypeContent.getAttributes(), "direction");
				
				String value = getValue(layerTypeContent.getAttributes(), "type");
				Type parameterType = classTypesMap.get(value);
				
				
				if (parameterType != null && !direction.equals("return")) {
				Variable variable = new Variable();
				variable.setName(parameterName);
				variable.setType(parameterType);
				oper.getArguments().add(variable);
				
				}
				 
			}
			for (Content typeContent : layerTypeContent.getContents()) {
				String basicTypeValue = getValue(typeContent.getAttributes(), "href");
				if (basicTypeValue != null && !basicTypeValue.equals("")) {
					String basicTypeName = basicTypeValue.split("#")[1];
					Type parameterType = basicTypesMap.get(basicTypeName);
					
					if (parameterType != null && !direction.equals("return")) {
						Variable variable = new Variable();
						variable.setName(parameterName);
						variable.setType(parameterType);
						oper.getArguments().add(variable);
						
						}
					
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
	 *                 Method sets basic type for given document
	 */
	private void setBasicTypes(Diagram diagram, Document document) {
		XPath findBasicTypes = new XPath("//type[@xmi:type=uml:PrimitiveType]/@href");

		List<Content> basicTypes = findBasicTypes.match(document);

		for (Content content : basicTypes) {
			Attribute attribute = (Attribute) content;
			String basicTypeName = attribute.getValue().split("#")[1];
			BasicType basicType1 = new BasicType();
			basicType1.setName(basicTypeName);
			BasicType result = basicTypesMap.put(basicTypeName, basicType1);
			if (result == null) {

				diagram.getBasicTypes().add(basicType1);

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
	public String getValue(List<Attribute> attributes, String attributeName) {
		for (uk.ac.sheffield.jast.xml.Attribute attribute : attributes) {
			if (attribute.getName().equals(attributeName)) {
				return attribute.getValue(); // Returns the value of this Attribute.
			}
		}
		return "";

	}

}
