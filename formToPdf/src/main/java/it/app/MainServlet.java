package it.app;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;
import java.io.OutputStream;



import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;


import org.xml.sax.SAXException;

import it.bean.Persona;
import it.util.XmlConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

	private XmlConverter converter = new XmlConverter();
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		Persona persona = new Persona(request.getParameter("nome"),request.getParameter("cognome"),request.getParameter("email"));
		//request.setAttribute("persona", persona);

//		} converter.getXmlSource(persona)
		File prova = converter.getXmlFile(persona);
		
		ByteArrayOutputStream htmlOut = null;
		try {
			htmlOut = converter.creaHtml3(converter.getXmlSource(persona));
		} catch (SAXException | ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e) {
			
			e.printStackTrace();
		}
		response.setContentType("text/html");
//		response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
		response.setContentLength(htmlOut.size());
//		byte[] fileContent = Files.readAllBytes(htmlFile.toPath());
//		response.setContentLength(fileContent.length);
		OutputStream responseOutputStream = response.getOutputStream();
		
//		 htmlOut.writeTo(responseOutputStream);
//		 responseOutputStream.flush();
		 
		 responseOutputStream.write(htmlOut.toByteArray());
		 responseOutputStream.flush();
		 responseOutputStream.close();
		//request.getRequestDispatcher("/WEB-INF/views/stampa.html").forward(request, response);
		//response.sendRedirect("/WEB-INF/views/stampa.html");
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		
	//	converter.creaPdf(converter.getXmlSource(persona));
		File xmlFile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
		//File pdfFile = converter.creaPdf(xmlFile);//new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/Doc/persona.pdf");
		ByteArrayOutputStream out = converter.generaPdfFromFile(xmlFile);
		
//		if(request.getParameter("pdf").equals("visualizza")) {
//			response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
//		}
//		else {
//			response.addHeader("Content-Disposition", "attachment; filename=/persona.pdf/");
//		}
		//response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
		
		
		response.setContentType("application/pdf");
		response.setContentLength(out.size());
		OutputStream responseOutputStream = response.getOutputStream();
		

		 responseOutputStream.write(out.toByteArray());
		 responseOutputStream.flush();
		 responseOutputStream.close();
		
	}
}
