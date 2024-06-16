package com.cs544.project.service;

import com.cs544.project.domain.*;
import com.cs544.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DatabaseInitService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    public void addSampleData() {

        // Location Types
        LocationType lectureHall = new LocationType();
        lectureHall.setType("Lecture Hall");
        locationTypeRepository.save(lectureHall);

        LocationType lab = new LocationType();
        lab.setType("Lab");
        locationTypeRepository.save(lab);

        // Locations
        Location location1 = new Location();
        location1.setName("LH-101");
        location1.setCapacity(100);
        location1.setLocationType(lectureHall);
        locationRepository.save(location1);

        Location location2 = new Location();
        location2.setName("Lab-201");
        location2.setCapacity(50);
        location2.setLocationType(lab);
        locationRepository.save(location2);

        // Faculties
        Faculty faculty1 = new Faculty();
        faculty1.setFirstName("John");
        faculty1.setLastName("Doe");
        faculty1.setEmailAddress("john.doe@example.com");
        faculty1.setBirthdate(LocalDate.of(1980, 1, 1));
        faculty1.setGenderType(GenderType.MALE);
        faculty1.setSalutation("Dr.");
        List<String> hobbies1 = new ArrayList<>();
        hobbies1.add("Reading");
        hobbies1.add("Hiking");
        faculty1.setHobbies(hobbies1);
        facultyRepository.save(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setFirstName("Jane");
        faculty2.setLastName("Smith");
        faculty2.setEmailAddress("jane.smith@example.com");
        faculty2.setBirthdate(LocalDate.of(1985, 5, 15));
        faculty2.setGenderType(GenderType.FEMALE);
        faculty2.setSalutation("Prof.");
        List<String> hobbies2 = new ArrayList<>();
        hobbies2.add("Tennis");
        hobbies2.add("Cooking");
        faculty2.setHobbies(hobbies2);
        facultyRepository.save(faculty2);

        // Courses
        Course course1 = new Course();
        course1.setCourseName("Introduction to Computer Science");
        course1.setCourseCode("CS101");
        course1.setCredits(3.0f);
        course1.setCourseDescription(
                "An introduction to the fundamental concepts of computer science.");
        course1.setDepartment("Computer Science");
        List<Course> prerequisites1 = new ArrayList<>();
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setCourseName("Data Structures and Algorithms");
        course2.setCourseCode("CS201");
        course2.setCredits(4.0f);
        course2.setCourseDescription("A comprehensive study of data structures and algorithms.");
        course2.setDepartment("Computer Science");
        List<Course> prerequisites2 = new ArrayList<>();
        prerequisites2.add(course1); // Prerequisite for CS201
        course2.setPrerequisites(prerequisites2);
        courseRepository.save(course2);

        // Course Offerings
        CourseOffering courseOffering1 = new CourseOffering();
        courseOffering1.setCourse(course1);
        courseOffering1.setStartDate(LocalDate.of(2024, 5, 27));
        courseOffering1.setEndDate(LocalDate.of(2024, 6, 20));
        courseOffering1.setCapacity(50);
        courseOffering1.setCredits(3.0f);
        courseOffering1.setCourseOfferingType(CourseOfferingType.FULL_TIME);
        courseOffering1.setFaculty(faculty1);
        courseOffering1.setRoom("LH-101");
        courseOfferingRepository.save(courseOffering1);

        CourseOffering courseOffering2 = new CourseOffering();
        courseOffering2.setCourse(course2);
        courseOffering2.setStartDate(LocalDate.of(2024, 6, 24));
        courseOffering2.setEndDate(LocalDate.of(2024, 7, 18));
        courseOffering2.setCapacity(30);
        courseOffering2.setCredits(4.0f);
        courseOffering2.setCourseOfferingType(CourseOfferingType.PART_TIME);
        courseOffering2.setFaculty(faculty2);
        courseOffering2.setRoom("Lab-201");
        courseOfferingRepository.save(courseOffering2);

        // Students
        Student student1 = new Student();
        student1.setFirstName("John");
        student1.setLastName("Smith");
        student1.setEmailAddress("john.smith@example.com");
        student1.setBirthdate(LocalDate.of(2000, 10, 12));
        student1.setGenderType(GenderType.MALE);
        student1.setEntry(LocalDate.of(2020, 9, 1));
        student1.setStudentID("123456");
        student1.setAlternateID("987654");
        student1.setApplicantID("1234567");
        student1.setFaculty(faculty1); // Faculty advisor
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setEmailAddress("jane.doe@example.com");
        student2.setBirthdate(LocalDate.of(2001, 3, 21));
        student2.setGenderType(GenderType.FEMALE);
        student2.setEntry(LocalDate.of(2021, 9, 1));
        student2.setStudentID("789012");
        student2.setAlternateID("345678");
        student2.setApplicantID("7890123");
        student2.setFaculty(faculty2); // Faculty advisor
        studentRepository.save(student2);

        // Course Registrations
        CourseRegistration courseRegistration1 = new CourseRegistration();
        courseRegistration1.setStudent(student1);
        courseRegistration1.setCourseOffering(courseOffering1);
        courseRegistrationRepository.save(courseRegistration1);

        CourseRegistration courseRegistration2 = new CourseRegistration();
        courseRegistration2.setStudent(student2);
        courseRegistration2.setCourseOffering(courseOffering2);
        courseRegistrationRepository.save(courseRegistration2);

        // Attendance Records
        generateAttendanceRecord(
                student1,
                location1,
                courseOffering1.getStartDate(),
                courseOffering1.getEndDate(),
                LocalTime.of(10, 0),
                2);
        generateAttendanceRecord(
                student1,
                location1,
                courseOffering1.getStartDate(),
                courseOffering1.getEndDate(),
                LocalTime.of(13, 32),
                2);

        generateAttendanceRecord(
                student2,
                location2,
                courseOffering2.getStartDate(),
                courseOffering2.getEndDate(),
                LocalTime.of(10, 0),
                0);
        generateAttendanceRecord(
                student2,
                location2,
                courseOffering2.getStartDate(),
                courseOffering2.getEndDate(),
                LocalTime.of(13, 35),
                0);
    }

    private void generateAttendanceRecord(
            Student student,
            Location location,
            LocalDate startDate,
            LocalDate endDate,
            LocalTime scanTime,
            int absences) {
        if (startDate == null || endDate == null) {
            System.out.println("Failed to generate attendance records");
            return;
        }
        LocalDate current = startDate;
        // skip x number of days as absences
        current = current.plusDays(absences);
        while (!current.isAfter(endDate)) {
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY) {
                // skip creating any records on sunday
                current = current.plusDays(1);
                continue;
            }
            if (scanTime.isAfter(LocalTime.of(13, 0))
                    && (current.getDayOfWeek() == DayOfWeek.SATURDAY || current.equals(endDate))) {
                // no afternoon attendance record on saturdays and the last day of the course offering
                current = current.plusDays(1);
                continue;
            }

            AttendanceRecord record = new AttendanceRecord();
            record.setScanTime(LocalDateTime.of(current, scanTime));
            record.setStudent(student);
            record.setLocation(location);
            attendanceRecordRepository.save(record);

            current = current.plusDays(1);
        }
    }
}
