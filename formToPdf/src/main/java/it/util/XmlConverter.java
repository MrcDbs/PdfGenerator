package it.util;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;

import javax.xml.bind.JAXBContext; 
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import it.bean.Persona;


public class XmlConverter { 
//	public static void main(String[]args) throws IOException {
//	XmlConverter c = new XmlConverter();
//	Persona p = new Persona("pippo","verdi","rss@l.it");
//	File template = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/prova.xml");
//	
//	try {
//		c.creaForm(c.generaByteArray(template));
//	} catch (SAXException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ParserConfigurationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (TransformerFactoryConfigurationError e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (TransformerException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//	}
	
	public ByteArrayOutputStream getXmlSource(Persona person){
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Persona.class);
			
			Marshaller m = jaxbContext.createMarshaller();
			
//			FileInputStream file = new FileInputStream("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
//			FileOutputStream file = new FileOutputStream("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
			//file = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
//			FileWriter file = new FileWriter("src/main/resources/person.xml");
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(person, out);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			
		} 
		 return out;
		
	}
	
	
	public File getXmlFile(Persona person){
		
		//ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		File file = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Persona.class);
			
			Marshaller m = jaxbContext.createMarshaller();
			
//			FileInputStream file = new FileInputStream("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
//			FileOutputStream file = new FileOutputStream("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
			file = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
//			FileWriter file = new FileWriter("src/main/resources/person.xml");
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			 m.marshal(person, file);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			
		} 
		 return file;
		
	}
	
	public File creaPdf(File xmlFile) {
		try {
		  
		   File xsltfile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/pdfTemplate.xsl");
		   File pdfDir = new File("./Doc/pdf");
		   pdfDir.mkdirs();
		   File pdfFile = new File(pdfDir, "persona.pdf");
		   		   
		   FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		   //FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		   OutputStream out = new FileOutputStream(pdfFile);
		   out = new BufferedOutputStream(out);
		   try {
			   // SI COSTRUISCE L'OGGETTO FOP CON IL FORMATO CHE SI NECESSITA
		             
			   Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
						
		        // SETUP XSLT
		        TransformerFactory factory = TransformerFactory.newInstance();
		        Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
							
		        // SETUP INPUT PER LA TRASFORMAZIONE XSLT
		        Source src = new StreamSource(xmlFile);
		        
		       
		        Result res = new SAXResult(fop.getDefaultHandler());
				    		
		        
		        transformer.transform(src, res);
		        return pdfFile;
		   }
		   catch(FOPException | TransformerException e) {
			   
			   e.printStackTrace();
		      } finally {
		          out.close();
		      }
		}catch(IOException exp){
		      exp.printStackTrace();
	    }
		return null;
	}
	
	public ByteArrayOutputStream generaPdfFromSource(ByteArrayOutputStream xmlSource) {
		File xsltfile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/pdfTemplate.xsl");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
	
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			
			//Setup Transformer
			Source xsltSrc = new StreamSource(xsltfile);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			
			
			Transformer transformer = tFactory.newTransformer(xsltSrc);

		
			Result res = new SAXResult(fop.getDefaultHandler());
			
	
			//Setup input
			Source src = new StreamSource(new ByteArrayInputStream(xmlSource.toByteArray()));
	
			//Start the transformation and rendering process
			
			transformer.transform(src, res);
		}
		 catch (FOPException|TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	public ByteArrayOutputStream generaPdfFromFile(File xmlFile) {
		File xsltfile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/pdfTemplate.xsl");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
	
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			
			//Setup Transformer
			Source xsltSrc = new StreamSource(xsltfile);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			
			
			Transformer transformer = tFactory.newTransformer(xsltSrc);

		
			Result res = new SAXResult(fop.getDefaultHandler());
			
	
			//Setup input
			Source src = new StreamSource(xmlFile);
	
			//Start the transformation and rendering process
			
			transformer.transform(src, res);
		}
		 catch (FOPException|TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	public File creaHtml(File xmlFile,File xsltFile) throws FOPException, IOException {
		Source xml = new StreamSource(xmlFile);
		Source xslt = new StreamSource(xsltFile);
		StringWriter sw = new StringWriter();
		
		try {

			File f = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/webapp/WEB-INF/views","stampa.html");
			
			FileWriter fw = new FileWriter(f);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer trasform = tFactory.newTransformer(xslt);
			trasform.transform(xml, new StreamResult(sw));
			fw.write(sw.toString());
			fw.close();
			System.out.println("File generato con successo in (WEB-INF/views)");
			return f;
			

		} catch ( TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ByteArrayOutputStream generaHtml(File xmlFile,File xsltFile) throws FOPException {
		Source xml = new StreamSource(xmlFile);
		Source xslt = new StreamSource(xsltFile);
		//StringWriter sw = new StringWriter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
			
			
			Fop fop = fopFactory.newFop("text/html", out);
			
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer trasform = tFactory.newTransformer(xslt);
			Result res = new SAXResult(fop.getDefaultHandler());
			trasform.transform(xml, res);
		
			return out;
			

		} catch ( TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ByteArrayOutputStream  creaHtml3(ByteArrayOutputStream xmlSource) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		//System.out.println("."+xmlFile.getPath());
		ClassLoader classloader = XmlConverter.class.getClassLoader();
		//System.out.println(classloader==null);
	
		//InputStream xmlData = classloader.getResourceAsStream(xmlFile.getName());
		InputStream xmlData =new ByteArrayInputStream(xmlSource.toByteArray());
		//System.out.println(xmlData==null);
	
		//System.out.println();
	
		URL xsltURL = classloader.getResource("template.xsl");
		//System.out.println(xsltURL==null);
		
		Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
		Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
		Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
	
		//StringWriter stringWriter = new StringWriter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Result res = new StreamResult(out) ;
		transformer.transform(new DOMSource(xmlDocument),res );
		//System.out.println(out.toByteArray().length);
		return out;
	
	}
	
	public ByteArrayOutputStream  creaForm(ByteArrayOutputStream xmlSource) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		
		ClassLoader classloader = XmlConverter.class.getClassLoader();

		InputStream xmlData =new ByteArrayInputStream(xmlSource.toByteArray());
	
		URL xsltURL = classloader.getResource("formTemplate.xslt");
		
		Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
		Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
		Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
	
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Result res = new StreamResult(out) ;
		transformer.transform(new DOMSource(xmlDocument),res );
		//System.out.println("ByteArray è "+(out==null));
		return out;
	
	}
	
	public ByteArrayOutputStream generaByteArray(File xmlFile) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] bytes = Files.readAllBytes(xmlFile.toPath());
			out.write(bytes);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return out;
	}
	
}
