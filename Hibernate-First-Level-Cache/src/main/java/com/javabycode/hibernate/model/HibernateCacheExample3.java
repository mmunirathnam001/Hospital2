package com.javabycode.hibernate.model;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateCacheExample3 {
	
	public static void main(String[] args) throws InterruptedException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		printLog("--------Step 1--------");
		// Get student with id=23
		Student student = (Student) session.load(Student.class, new Integer(23));
		printLog(student, 1);		

		// Fetch same data again, check logs that no query fired
		Student student1 = (Student) session.load(Student.class, new Integer(23));
		printLog(student1, 2);

		printLog("--------Step 2--------");
		// Clear example to remove everything from first level cache
		printLog("Clear everything from first level cache");
		session.clear();
		Student student2 = (Student) session.load(Student.class, new Integer(23));
		printLog(student2, 7);
		Student student3 = (Student) session.load(Student.class, new Integer(24));
		printLog(student3, 8);

		printLog("--------Step 3--------");
		printLog("Session contains student with id=24?" + session.contains(student3));

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
