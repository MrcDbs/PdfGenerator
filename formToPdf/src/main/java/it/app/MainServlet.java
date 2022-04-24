package it.app;

import java.io.File;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;



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
		request.setAttribute("persona", persona);
		
//		File res = new File("WEB-INF/views/stampa.html");
//		if(res.exists()) {
//			PrintWriter writer = new PrintWriter(res);
//			writer.print("");
//			writer.close();
//		}
		File template = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/template.xsl");
		
		File htmlFile = converter.creaHtml(converter.getXmlSource(persona),template);
		
		request.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(request, response);
		//response.sendRedirect("/WEB-INF/views/stampa.html");
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		
	//	converter.creaPdf(converter.getXmlSource(persona));
		File xmlFile = new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/src/main/resources/person.xml");
		File pdfFile = converter.creaPdf(xmlFile);//new File("/Users/marcodebiase/LeonardoAssignment/formToPdf/Doc/persona.pdf");
		response.setContentType("application/pdf");
		if(request.getParameter("pdf").equals("visualizza")) {
			response.addHeader("Content-Disposition", "inline; filename=/persona.pdf/");
		}
		else {
			response.addHeader("Content-Disposition", "attachment; filename=/persona.pdf/");
		}
		
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
