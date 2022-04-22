package it.app;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.xml.transform.TransformerFactory;

import org.apache.fop.apps.FopFactory;

import it.bean.Persona;
import it.util.XmlConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
//	private FopFactory fopFactory = FopFactory.newInstance();
//	private TransformerFactory tFactory = TransformerFactory.newInstance();
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		Persona persona = new Persona(request.getParameter("nome"),request.getParameter("cognome"),request.getParameter("email"));
//		request.setAttribute("persona", persona);
		XmlConverter converter = new XmlConverter();
		File template = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/template.xsl");
		File htmlFile = converter.creaHtml(converter.getXmlSource(persona),template);
		
		request.getRequestDispatcher(htmlFile.getName()).forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//Persona persona = new Persona(request.getParameter("nome"),request.getParameter("cognome"),request.getParameter("email"));
		XmlConverter converter = new XmlConverter();
	//	converter.creaPdf(converter.getXmlSource(persona));
		File xmlFile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
		File pdfFile = converter.creaPdf(xmlFile);//new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/Doc/persona.pdf");
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
		response.setContentLength((int)pdfFile.length());
		OutputStream responseOutputStream = response.getOutputStream();
		byte[] fileContent = Files.readAllBytes(pdfFile.toPath());
		 //byte[] content = new byte[size];
//		 int bytesRead;
//		 while ((bytesRead = fileInputStream.read(content)) != -1) // read, then assign, then compare
//		 {
//		     responseOutputStream.write(content, 0, bytesRead);
//		 }
		 responseOutputStream.write(fileContent);
		 responseOutputStream.flush();
		 responseOutputStream.close();
		
	}
}
