package hm.flickr.tag.crawler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHelper {
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder ;
	private Document doc;
	
	public XMLHelper(String xmlStr) {

		
		try {
			factory.setNamespaceAware(true); // never forget this!
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	}
	public List<String> extractElementAttribute(String element, String attr){
		
		List<String> attrValues = new ArrayList<>();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile("//"+element+"/@"+attr);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				attrValues.add( nodes.item(i).getNodeValue());
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return attrValues;
	}
	public String extractUniqueElementAttribute(String element, String attr){
		 XPathFactory xPathfactory = XPathFactory.newInstance();
		 XPath xpath = xPathfactory.newXPath();
		 XPathExpression expr;
		try {
			expr = xpath.compile("//"+element+"/@"+attr);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			
			if(nodes.item(0) != null)
				return nodes.item(0).getNodeValue();
			else{
//				System.err.println("No " + element + ": " + attr);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	public String extractElementValue(String element){
		 XPathFactory xPathfactory = XPathFactory.newInstance();
		 XPath xpath = xPathfactory.newXPath();
		 XPathExpression expr;
		try {
			expr = xpath.compile("//"+element+"/text()");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			if(nodes.item(0)!=null)
			 return nodes.item(0).getNodeValue();
			else{
//				System.err.println("No " + element);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}
	
	
   
	public Document getDoc() {
		return doc;
	}


}
