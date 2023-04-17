package com.javabycode.hibernate.model;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateCacheExample2 {
	
	public static void main(String[] args) throws InterruptedException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		printLog("--------Step 1--------");
		// Get student with id=23
		Student student = (Student) session.load(Student.class, new Integer(23));
		printLog(student, 1);
	
		// Fetch same data again, and no query fired
		Student student1 = (Student) session.load(Student.class, new Integer(23));
		printLog(student1, 2);
		
		printLog("--------Step 2--------");
		// Evict the Student object with id=1 from first level cache 
		printLog("Evict the Student object id=23 from first level cache");
		session.evict(student);
		printLog("Session contains student with id=23? " + session.contains(student));

		// You will see query in logs because Hibernate get it from database.
		printLog("--------Step 3--------");
		Student student4 = (Student) session.load(Student.class, new Integer(23));
		printLog(student4, 3);
		
		tx.commit();
		System.exit(0);
	}

	private static void printLog(Student student, int count) {
		System.out.println("Get student => Name=" + student.getName() + ", Nationality=" + student.getNationality());
	}
	
	private static void printLog(String msg) {
		System.out.println(msg);
	}	
}
