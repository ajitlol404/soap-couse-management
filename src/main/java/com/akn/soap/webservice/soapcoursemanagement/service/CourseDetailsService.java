package com.akn.soap.webservice.soapcoursemanagement.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.akn.soap.webservice.soapcoursemanagement.bean.Course;
import com.akn.soap.webservice.soapcoursemanagement.service.CourseDetailsService.Status;

@Component
public class CourseDetailsService {

	public enum Status {
		SUCCESS, FAILURE
	}

	private static List<Course> courses = new ArrayList<>();
	static {
		Course course1 = new Course(1, "Spring", "10 Step");
		courses.add(course1);

		Course course2 = new Course(2, "Spring MVC", "10 Step");
		courses.add(course2);

		Course course3 = new Course(3, "Spring Boot", "10 Step");
		courses.add(course3);
	}

	// course by id
	public Course findbyId(int id) {
		for (Course course : courses) {
			if (course.getId() == id) {
				return course;
			}
		}
		return null;
	}

	// all courses
	public List<Course> findAll() {
		return courses;
	}

	// delete course by id
	public Status deleteById(int id) {
		Iterator<Course> iterator = courses.iterator();
		while (iterator.hasNext()) {
			Course course = (Course) iterator.next();
			if (course.getId() == id) {
				iterator.remove();
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}

	// updating course and new course
}
