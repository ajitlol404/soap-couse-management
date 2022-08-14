package com.akn.soap.webservice.soapcoursemanagement;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enable Spring web services
@EnableWs
//Spring Configuration
@Configuration
public class WebServiceConfig {

	@Bean
	ServletRegistrationBean messageDispacterServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();

		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);

		return new ServletRegistrationBean<>(messageDispatcherServlet, "/ws/*");
	}

	// /ws/courses.wsdl

	@Bean(name = "courses")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		// PortType -> CoursePort
		definition.setPortTypeName("CoursePort");
		// NameSpace -> http://akn.com/courses
		definition.setTargetNamespace("http://akn.com/courses");
		// /ws
		definition.setLocationUri("/ws");
		// schema
		definition.setSchema(coursesSchema);
		return definition;
	}

	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}

}
