package com.akn.soap.webservice.soapcoursemanagement.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
//For creating custom faultcode
//@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://akn.com/courses}COURSE_NOT_FOUND")
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CourseNotFoundException(String message) {
		super(message);

	}

}
