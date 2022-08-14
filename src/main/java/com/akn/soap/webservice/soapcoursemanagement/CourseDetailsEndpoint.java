package com.akn.soap.webservice.soapcoursemanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.akn.courses.CourseDetails;
import com.akn.courses.DeleteCourseDetailsRequest;
import com.akn.courses.DeleteCourseDetailsResponse;
import com.akn.courses.GetAllCourseDetailsRequest;
import com.akn.courses.GetAllCourseDetailsResponse;
import com.akn.courses.GetCourseDetailsRequest;
import com.akn.courses.GetCourseDetailsResponse;
import com.akn.soap.webservice.soapcoursemanagement.bean.Course;
import com.akn.soap.webservice.soapcoursemanagement.exception.CourseNotFoundException;
import com.akn.soap.webservice.soapcoursemanagement.service.CourseDetailsService;
import com.akn.soap.webservice.soapcoursemanagement.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;

	// method
	// input -> GetCourseDetailsRequest
	// output -> GetCourseDetailsResponse

	@PayloadRoot(namespace = "http://akn.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		Course course = service.findbyId(request.getId());

		if (course == null) {
			throw new CourseNotFoundException("Invalid Course ID " + request.getId());
		}

		return mapCourseDetails(course);
	}

	@PayloadRoot(namespace = "http://akn.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {
		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);
	}

	@PayloadRoot(namespace = "http://akn.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
		Status status = service.deleteById(request.getId());

		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));

		return response;
	}

	private com.akn.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE) {
			return com.akn.courses.Status.FAILURE;
		}
		return com.akn.courses.Status.SUCCESS;
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));

		return response;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}

		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

}
