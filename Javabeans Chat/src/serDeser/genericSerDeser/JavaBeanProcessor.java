package serDeser.genericSerDeser;

import serDeser.util.MyBean;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

// These imports includes asterisks because there are too many classes to be imported
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public abstract class JavaBeanProcessor {
	
	/**
	 * Serialize bean into String
	 * @param beanObject Object to be serialized
	 * @param serializationFormat Serialization format
	 * @return XML string serialized from MyBean object
	 */
	public static String serializeBean(MyBean beanObject, String serializationFormat)
	{
		String retVal = ""; // This will be returned from the method.
		Class<?> cls = beanObject.getClass();
		Field[] fields = cls.getDeclaredFields();
		
		// Since XMLSTD format is not valid XML we have to create XML by hand
		if(serializationFormat.equalsIgnoreCase("xmlstd"))
		{
			StringBuilder xmlString = new StringBuilder();
			xmlString.append("<xmlStd>\n");
			// Class name is hard coded because it is always a String
			xmlString.append("<className xsd:String>" + beanObject.getClass().getName() + "</className>\n");
			for(Field f : fields)
			{
				try {
					xmlString.append("<" + f.getName() + " xsd:" + f.getType().getSimpleName() + ">" + cls.getMethod(getterForField(f.getName()), (Class[])null).invoke(beanObject, (Object[])null) + "</" + f.getName() + ">\n");
				} catch (Exception e) {
					System.exit(-1);
				}
			}
			xmlString.append("</xmlStd>\n");
			retVal = xmlString.toString();
		}
		else // now we can create the XML with DOM
		{
			try 
			{
	            /////////////////////////////
	            //Creating an empty XML Document

	            //We need a Document
	            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	            Document doc = docBuilder.newDocument();

	            ////////////////////////
	            //Creating the XML tree
	            
	            if(serializationFormat.equalsIgnoreCase("xmlnew"))
				{
	            	//create the root element and add it to the document
		            Element root = doc.createElement("xmlNew");
		            doc.appendChild(root);
		            
		            Element className = doc.createElement("className");
		            Text classNameText = doc.createTextNode(cls.getName());
		            className.appendChild(classNameText);
		            root.appendChild(className);
		            
		            for(Field f : fields)
		            {
		            	Element child = doc.createElement(f.getName());
		            	Element child2 = doc.createElement(f.getType().getName());
		            	Text text = doc.createTextNode(cls.getMethod(getterForField(f.getName()), (Class[])null).invoke(beanObject, (Object[])null).toString());
		            	child2.appendChild(text);
		            	child.appendChild(child2);
		            	root.appendChild(child);
		            }
				}
				else if(serializationFormat.equalsIgnoreCase("xmlverbose"))
				{
					//create the root element and add it to the document
		            Element root = doc.createElement("xmlVerbose");
		            doc.appendChild(root);
		            
		            Element className = doc.createElement("className");
		            root.appendChild(className);
		            
		            Element elementLength = doc.createElement("elementLength");
		            className.appendChild(elementLength);
		            
		            Text elementLengthText = doc.createTextNode(Integer.toString(cls.getName().length()));
		            elementLength.appendChild(elementLengthText);
		            
		            Element elementType = doc.createElement("elementType");
		            className.appendChild(elementType);
		            
		            // Class name is always a String
		            Text elementTypeText = doc.createTextNode(String.class.getName());
		            elementType.appendChild(elementTypeText);
		            
		            Element elementValue = doc.createElement("elementValue");
		            className.appendChild(elementValue);
		            
		            Text elementValueText = doc.createTextNode(cls.getName());
		            elementValue.appendChild(elementValueText);
		            
		            for(Field f : fields)
		            {
		            	Element field = doc.createElement(f.getName());
		            	root.appendChild(field);
			            
			            Element elementLength2 = doc.createElement("elementLength");
			            field.appendChild(elementLength2);
			            
			            String elementVal = cls.getMethod("get" + capitalizeString(f.getName()), (Class[])null).invoke(beanObject, (Object[])null).toString();
			            
			            Text elementLengthText2 = doc.createTextNode(new Integer(elementVal.length()).toString());
			            elementLength2.appendChild(elementLengthText2);
			            
			            Element elementType2 = doc.createElement("elementType");
			            field.appendChild(elementType2);
			            
			            Text elementTypeText2 = doc.createTextNode(f.getType().getName());
			            elementType2.appendChild(elementTypeText2);
			            
			            Element elementValue2 = doc.createElement("elementValue");
			            field.appendChild(elementValue2);
			            
			            Text elementValueText2 = doc.createTextNode(elementVal);
			            elementValue2.appendChild(elementValueText2);
		            }
				}

	            /////////////////
	            //Output the XML

	            //set up a transformer
	            TransformerFactory transfac = TransformerFactory.newInstance();
	            Transformer trans = transfac.newTransformer();
	            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            trans.setOutputProperty(OutputKeys.INDENT, "yes");

	            //create string from XML tree
	            StringWriter sw = new StringWriter();
	            StreamResult result = new StreamResult(sw);
	            DOMSource source = new DOMSource(doc);
	            trans.transform(source, result);
	            retVal = sw.toString();
	        } 
			catch (Exception e) 
			{
	            System.out.println(e.getMessage());
	        }
		}
        
        return retVal;
	}
	
	/**
	 * Deserialize XML into MyBean object
	 * @param serializedString String to be deserialized
	 * @return MyBean object constructed from XML
	 */
	public static MyBean deserializeBean(String serializedString)
	{
		MyBean myBean = null; // this will be returned from the method
		Class<?> beanClass = null;
		
		// if format is XMLSTD (which is not valid XML format)
		if(serializedString.substring(serializedString.indexOf("<") + 1, serializedString.indexOf(">")).equalsIgnoreCase("xmlstd"))
		{
			try {
				int index = 0;
				index = serializedString.indexOf("xsd:String") + 11;
				String className = serializedString.substring(index, serializedString.indexOf("</className>"));
				beanClass = Class.forName(className);
				myBean = (MyBean)beanClass.newInstance();
				
				while((index = serializedString.indexOf("xsd:", index) + 4) != 3)
				{
					String fieldName = serializedString.substring(serializedString.lastIndexOf("<", index)+1, index-5);
					String fieldType = serializedString.substring(index, serializedString.indexOf(">", index));
					String fieldValue = serializedString.substring(index+fieldType.length()+1, serializedString.indexOf("<", index));
					
					// Mapping standard types to Java types
					if(fieldType.equalsIgnoreCase("integer"))
					{
						fieldType = "java.lang.Integer";
					}
					else if(fieldType.equalsIgnoreCase("float"))
					{
						fieldType = "java.lang.Float";
					}
					else if(fieldType.equalsIgnoreCase("double"))
					{
						fieldType = "java.lang.Double";
					}
					else if(fieldType.equalsIgnoreCase("string"))
					{
						fieldType = "java.lang.String";
					}
				
					Class<?> fieldClass = Class.forName(fieldType);
					Constructor<?> stringConstructor = fieldClass.getConstructor(String.class);
					Object fieldObject = stringConstructor.newInstance(fieldValue.trim());

					Method setterMethod = beanClass.getMethod(setterForField(fieldName), fieldClass);
					setterMethod.invoke(myBean, fieldObject);
				}
			} catch (Exception e) {
				System.out.println("Error while parsing XML.");
				System.exit(-1);
			}
		}
		else // Deserialization code for valid XML
		{
			//get the factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {

				//Using factory get an instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();

				ByteArrayInputStream bais = new ByteArrayInputStream(
						serializedString.getBytes());

				//parse using builder to get DOM representation of the XML file
				Document dom = db.parse(bais);

				Element root = dom.getDocumentElement();
				root.normalize();
				String format = root.getNodeName();

				if (format.equalsIgnoreCase("xmlnew")) {
					for (Node childNode = root.getFirstChild(); childNode != null; childNode = childNode
							.getNextSibling()) {
						if (childNode instanceof Element) {
							Element childElement = (Element) childNode;
							if (childElement.getNodeName().equalsIgnoreCase(
									"classname")) {
								String className = childElement
										.getTextContent();
								beanClass = Class.forName(className);
								myBean = (MyBean) beanClass.newInstance(); // Create instance of the class
							} else {
								String fieldName = childElement.getNodeName();
								String fieldValue = childElement
										.getTextContent();
								String fieldType = childElement.getFirstChild()
										.getNextSibling().getNodeName();
								Class<?> fieldClass = Class.forName(fieldType);

								Constructor<?> stringConstructor = fieldClass
										.getConstructor(String.class);
								Object fieldObject = stringConstructor
										.newInstance(fieldValue.trim());

								Method setterMethod = beanClass.getMethod(
										setterForField(fieldName), fieldClass);
								setterMethod.invoke(myBean, fieldObject);
							}
						}
					}
				} else if (format.equalsIgnoreCase("xmlverbose")) {
					NodeList elementTypes = root
							.getElementsByTagName("elementType");
					NodeList elementValues = root
							.getElementsByTagName("elementValue");
					Vector<Element> elementNames = new Vector<Element>();

					for (Node childNode = root.getFirstChild(); childNode != null; childNode = childNode
							.getNextSibling()) {
						if (childNode instanceof Element) {
							Element childElement = (Element) childNode;
							elementNames.add(childElement);
						}
					}

					beanClass = Class.forName(elementValues.item(0)
							.getTextContent());
					myBean = (MyBean) beanClass.newInstance();

					int length = elementTypes.getLength();
					for (int i = 1; i < length; i++) {
						String fieldType = elementTypes.item(i)
								.getTextContent();
						String fieldValue = elementValues.item(i)
								.getTextContent();
						String fieldName = elementNames.elementAt(i)
								.getNodeName();

						Class<?> fieldClass = Class.forName(fieldType);
						Constructor<?> stringConstructor = fieldClass
								.getConstructor(String.class);
						Object fieldObject = stringConstructor
								.newInstance(fieldValue.trim());

						Method setterMethod = beanClass.getMethod(
								setterForField(fieldName), fieldClass);
						setterMethod.invoke(myBean, fieldObject);
					}
				}
			} catch (Exception e) {
				System.out.println("Error while parsing XML.");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return myBean;
	}
	
	// Helper methods
	
	private static String capitalizeString(String str)
	{
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}
	
	private static String getterForField(String fieldName){
		return "get" + capitalizeString(fieldName);
	}
	
	private static String setterForField(String fieldName){
		return "set" + capitalizeString(fieldName);
	}
}
