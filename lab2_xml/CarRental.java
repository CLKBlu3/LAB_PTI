import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.sax.XMLReaders;


public class CarRental {
	public static void main(String argv[]) {
		if(argv.length == 1) {
            String command = argv[0];
            if(command.equals("reset")) outputDocumentToFile(createEmptyDocument());
            else if(command.equals("new")) addNewRental();
            else if(command.equals("list")) listRentals();
            else if(command.equals("xslt")) xslt();
            else if(command.equals("validate")) validate();
            else {
                System.out.println(command + " is not a valid option.");
                printUsage();
            }
        } else {
            printUsage();
        }
	}
	
	public static void validate(){
		try{
			SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
			Document doc = builder.build(new File("carrental.xml"));
			System.out.println("Root: " + doc.getRootElement().getName());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Bad format in XML");
		}
	}
	
	public static void xslt(){
		executeXSLT(readDocument());
	}
	
	public static void executeXSLT(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("carrental.xslt"));
			//Make the output result for the finished document
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
	}
	
	public static void listRentals(){
		outputDocument(readDocument());
	}
	
	public static void outputDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
	
	public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File("carrental.xml"));
            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void addNewRental(){
		Element n = askData();
		Document doc = readDocument();
		doc.getRootElement().addContent(n);
		outputDocumentToFile(doc);
	}
	
	public static Document createEmptyDocument() {
        // Create the root element
        Element carElement = new Element("carrental");

        //create the document
        Document myDocument = new Document(carElement);
       
        return myDocument;
    }
    public static void printUsage() {
        System.out.println("Usage: Example [option] \n where option is one of the following:");
        System.out.println("  reset - create a new empty document in memory");
        System.out.println("  new - create a new rental and and add it to the document in XML");
        System.out.println("  list - show a list with the rentals in the XML file");
        System.out.println("  xslt    - create a new document and transform it to HTML with the XSLT stylesheet in example.xslt");
		System.out.println("  validate	- validates the document against the XSD");
    }
    
    public static void outputDocumentToFile(Document myDocument) {
        //setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter();

            //output to a file
            FileWriter writer = new FileWriter("carrental.xml");
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Element askData() {
		Element carElement = new Element("rental");
		System.out.print("Make:");
		String input = System.console().readLine();
		Element make = new Element("make");
		make.addContent(input);
		carElement.addContent(make);
		
		System.out.print("Model:");
		input = System.console().readLine();
		make = new Element("model");
		make.addContent(input);
		carElement.addContent(make);
		
		System.out.print("nofdays:");
		input = System.console().readLine();
		make = new Element("nofdays");
		make.addContent(input);
		carElement.addContent(make);
		
		
		System.out.print("nofunits:");
		input = System.console().readLine();
		make = new Element("nofunits");
		make.addContent(input);
		carElement.addContent(make);
		
		System.out.print("discount:");
		input = System.console().readLine();
		make = new Element("discount");
		make.addContent(input);
		carElement.addContent(make);
		
		return carElement;
	}  

}
