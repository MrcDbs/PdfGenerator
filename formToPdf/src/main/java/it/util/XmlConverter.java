package it.util;


import java.io.*;


import it.bean.Persona;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;


import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlConverter { 
//	public static void main(String[]args) throws IOException {
//	XmlConverter c = new XmlConverter();
//	Persona p = new Persona("pippo","verdi","rss@l.it");
//	File template = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/template.xsl");
//	
//	c.creaHtml(c.getXmlSource(p),template);
//
//	}
	public File getXmlSource(Persona person){
		
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
//		   File pdfFile = new File("persona.pdf");
		   
		   final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		   FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		   OutputStream out = new FileOutputStream(pdfFile);
		   out = new BufferedOutputStream(out);
		   try {
			   // Construct FOP with desired output format
		        Fop fop;      
		        fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
						
		        // Setup XSLT
		        TransformerFactory factory = TransformerFactory.newInstance();
		        Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
							
		        // Setup input for XSLT transformation
		        Source src = new StreamSource(xmlFile);
		        
		        // Resulting SAX events (the generated FO) must be piped through to FOP
		        Result res = new SAXResult(fop.getDefaultHandler());
				    		
		        // Start XSLT transformation and FOP processing
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
	

//	public void creaHtml(File xmlFile) throws SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
//		System.out.println(xmlFile.getAbsolutePath());
//		ClassLoader classloader = XmlConverter.class.getClassLoader();
//		//InputStream xmlData = classloader.getResourceAsStream(xmlFile.getAbsolutePath());
//		InputStream xmlData = XmlConverter.class.getResourceAsStream("/src/main/resources/person.xml");
//		System.out.println(xmlData.toString());
//		URL xsltURL = classloader.getResource("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/template.xsl");
//
//		Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
//		Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
//		Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
//
//		StringWriter stringWriter = new StringWriter();
//		transformer.transform(new DOMSource(xmlDocument), new StreamResult(stringWriter));
//
//		// write to file
//		File file = new File("books.html");
//		if (!file.exists()) {
//			file.createNewFile();
//		}
//
//		FileWriter fw = new FileWriter(file);
//		BufferedWriter bw = new BufferedWriter(fw);
//		bw.write(stringWriter.toString());
//		bw.close();
//	
//	}
	
	public File creaHtml(File xmlFile,File xsltFile) {
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
			

		} catch (IOException | TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
