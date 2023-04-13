package rm.cd.v2;

import java.util.HashMap;
import java.util.List;

import meta.umlcd.ClassType;
import meta.umlcd.Diagram;
import meta.umlcd.EndRole;
import meta.umlcd.Generalisation;
import uk.ac.sheffield.jast.xml.Attribute;
import uk.ac.sheffield.jast.xml.Content;
import uk.ac.sheffield.jast.xml.Document;
import uk.ac.sheffield.jast.xpath.XPath;

public class GeneralizationConverterv2 extends RelationshipConverterv2{

	public GeneralizationConverterv2(Diagram diagram, HashMap<String, ClassType> classTypesMap) {
		super(diagram, classTypesMap);
		
	}
	
	
	/**
	 * @param document
	 */
	public void findAllGeneralization(Document document) {
		Generalisation generalisation = null;
		EndRole source= null;
		EndRole target = null;
		
		XPath findClassTypes = new XPath("/uml:Model/packagedElement/packagedElement[@xmi:type=uml:Class]");
		List<Content> classTypes = findClassTypes.match(document);
		for (Content content : classTypes) {
			
			
			for (Content subContent : content.getContents()) {
				
				if (subContent.getIdentifier().equals("generalization")) {
					
					source = new EndRole();
					target = new EndRole();
					String sourceId = getValue(content.getAttributes(), "id");
					String targetId = getValue(subContent.getAttributes(), "general");
					
					source.setName(classTypesMap.get(sourceId).getName());
					source.setType(classTypesMap.get(sourceId));
					target.setName(classTypesMap.get(targetId).getName());
					target.setType(classTypesMap.get(targetId));
					generalisation = new Generalisation();
					generalisation.setSource(source);
					generalisation.setTarget(target);
					
					
					diagram.getGeneralisations().add(generalisation);
					
					
				}
				
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


