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
import jakarta.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {

	private XmlConverter converter = new XmlConverter();
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		Persona persona = new Persona(request.getParameter("nome"),request.getParameter("cognome"),request.getParameter("email"));
		request.getSession().setAttribute("persona", persona);

		ByteArrayOutputStream htmlOut = null;
		try {
			htmlOut = converter.creaHtml3(converter.getXmlSource(persona));
		} catch (SAXException | ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e) {
			
			e.printStackTrace();
		}
		response.setContentType("text/html");

		response.setContentLength(htmlOut.size());
		OutputStream responseOutputStream = response.getOutputStream();
		 responseOutputStream.write(htmlOut.toByteArray());
		 responseOutputStream.flush();
		 responseOutputStream.close();
		 
		//File prova = converter.getXmlFile(persona);
//		byte[] fileContent = Files.readAllBytes(htmlFile.toPath());
//		response.setContentLength(fileContent.length);
		
//		 htmlOut.writeTo(responseOutputStream);
//		 responseOutputStream.flush();
		//request.getRequestDispatcher("/WEB-INF/views/stampa.html").forward(request, response);
		//response.sendRedirect("/WEB-INF/views/stampa.html");
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		if(!request.getParameter("start").equals("true")) {
			HttpSession session = request.getSession();
			Persona persona = (Persona)session.getAttribute("persona");
	
			ByteArrayOutputStream out = converter.generaPdfFromSource(converter.getXmlSource(persona));
			session.invalidate();
			response.setContentType("application/pdf");
			response.setContentLength(out.size());
			OutputStream responseOutputStream = response.getOutputStream();
			
			responseOutputStream.write(out.toByteArray());
			responseOutputStream.flush();
			responseOutputStream.close();
		}else {
			ByteArrayOutputStream htmlOut = null;
			try {
				htmlOut = converter.creaForm(converter.generaByteArray(new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/prova.xml")));
			} catch (SAXException | ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e) {
				
				e.printStackTrace();
			}
			response.setContentType("text/html");

			response.setContentLength(htmlOut.size());
			OutputStream responseOutputStream = response.getOutputStream();
			 responseOutputStream.write(htmlOut.toByteArray());
			 responseOutputStream.flush();
			 responseOutputStream.close();
		}
		
//		converter.creaPdf(converter.getXmlSource(persona));
			//File xmlFile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
//		if(request.getParameter("pdf").equals("visualizza")) {
//			response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
//		}
//		else {
//			response.addHeader("Content-Disposition", "attachment; filename=/persona.pdf/");
//		}
		//response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
		
		
	
		
	}
}
