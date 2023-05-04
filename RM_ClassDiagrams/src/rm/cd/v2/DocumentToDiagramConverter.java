package rm.cd.v2;

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
 * Class responsible for converting document to diagram 
 */
public class DocumentToDiagramConverter {

	HashMap<String, BasicType> basicTypesMap = new HashMap<>();
	public HashMap<String, ClassType> classTypesMap = new HashMap<>();
	HashMap<String, BasicType> dataTypesMap = new HashMap<>();
	
	public Diagram convert(Document document) {

		Diagram diagram = new Diagram();

		setDiagramName(diagram, document);
		setBasicTypes(diagram, document);
		setDataTypes(diagram, document);
		setClassTypes(diagram, document);
		return diagram;

	}

	private void setDataTypes(Diagram diagram, Document document) {
		XPath findDataTypes = new XPath("//packagedElement[@xmi:type=uml:DataType]");

		List<Content> dataTypes = findDataTypes.match(document);

		for (Content content : dataTypes) {
			String id = getValue(content.getAttributes(), "id");
			String name = getValue(content.getAttributes(), "name");
			BasicType dataType = new BasicType();
			dataType.setName(name);
			if (! dataTypesMap.containsKey(id)) {
				dataTypesMap.put(id, dataType);
				diagram.getBasicTypes().add(dataType);
				}

		}

	}	

	/**
	 * @param diagram
	 * @param document
	 */
	private void setDiagramName(Diagram diagram, Document document) {
		XPath findDiagramName = new XPath("/uml:Model");
		List<Content> matches = findDiagramName.match(document);
		Element umlModel = (Element) matches.get(0);
		diagram.setName(umlModel.getValue("name"));
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

			ClassType classType = new ClassType();
			classType.setName(className);
			classTypesMap.put(id, classType);
			diagram.getClassTypes().add(classType);
		}// closing for-loop #1

		for (Content content : classTypes) {
			Element classElem = (Element) content;
			String id = classElem.getValue("id");
			ClassType classType = classTypesMap.get(id);

			for (Content classContent : content.getContents()) {
				if (classContent.getIdentifier().equals("ownedAttribute")) {
					setAttributes(classContent, classType);
				}//closing if-statement

				if (classContent.getIdentifier().equals("ownedOperation")) {
					setOperations(classContent, classType);
				}//closing if-statement
				
			}// closing for-loop #3

		} // closing for-loop #2
		
	}//Closing method 

	/**
	 * @param classContent
	 * @param classType
	 * 
	 *                     Method sets Attribute for a given class Type
	 */
	private void setAttributes(Content classContent, ClassType classType) {
		meta.umlcd.Attribute attr = new meta.umlcd.Attribute();
		String name = getValue(classContent.getAttributes(), "name");
		//collect type property not node
		String type = getValue(classContent.getAttributes(), "type");
		BasicType attributeType = dataTypesMap.get(type);
		attr.setName(name);
		
		if (attributeType != null) {
			attr.setType(attributeType);
			
		} else {
			

		for (Content typeContent : classContent.getContents()) {
			String basicTypeValue = getValue(typeContent.getAttributes(), "href");
			if (basicTypeValue != null && !basicTypeValue.equals("")) {
				String basicTypeName = basicTypeValue.split("#")[1];
				attr.setType(basicTypesMap.get(basicTypeName));
			}

		}
		
		} //closing else block

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
		String name = getValue(classContent.getAttributes(), "name");
		oper.setName(name);
		String parameterName = null;
		String direction = null;
		for (Content layerTypeContent : classContent.getContents()) {

			if (layerTypeContent.getIdentifier().equals("ownedParameter")) {
				parameterName = getValue(layerTypeContent.getAttributes(), "name");

				direction = getValue(layerTypeContent.getAttributes(), "direction");

				String value = getValue(layerTypeContent.getAttributes(), "type");
				Type parameterType = classTypesMap.get(value);

				if (parameterType != null && !direction.equals("return")) {
					//setting Classtype arguments
					Variable variable = new Variable();
					variable.setName(parameterName);
					variable.setType(parameterType);
					oper.getArguments().add(variable);

				} else {
	
					for (Content typeContent : layerTypeContent.getContents()) {
						String basicTypeValue = getValue(typeContent.getAttributes(), "href");
						// this condition is for setting the whole operation to ClassType and 
						//not the arguments
						if (basicTypeValue != null && !basicTypeValue.equals("")) {
							String basicTypeName = basicTypeValue.split("#")[1];
							 parameterType = basicTypesMap.get(basicTypeName);
							 
							 
							if (parameterType != null && !direction.equals("return")) {
								//setting BasicType arguments
								Variable variable = new Variable();
								variable.setName(parameterName);
								variable.setType(parameterType);
								oper.getArguments().add(variable);

							}
							//set type of entire operation
							// if direction is return then don't add parameter as argument
							if (direction.equals("return")) {
							Type operationType = new Type();
							operationType.setName(basicTypeName);
							oper.setType(operationType);
							}//closing if-statement
							
						}//closing if-statement
						
				}//closing for-loop #2
					
					// Sets the type of the operation
					if (direction.equals("return")) {
						oper.setType(parameterType);
					}//closing if-statement

			}//closing else block 
			
			}//closing if-statement
			
		}//closing for-loop #1
		
		classType.getOperations().add(oper);

	}//closing method

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
			if (! basicTypesMap.containsKey(basicTypeName)) {
				basicTypesMap.put(basicTypeName, basicType1);
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
