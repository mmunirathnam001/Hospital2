package com.javabycode.hibernate.model;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateCacheExample1 {
	
	public static void main(String[] args) throws InterruptedException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		printLog("--------Step 1--------");
		// Get student with id=23
		Student student = (Student) session.load(Student.class, new Integer(23));
		printLog(student, 1);

		// Waiting for sometime to change the data in backend
		printLog("Waiting for 10000 milliseconds...");
		Thread.sleep(10000);

		// Fetch same data again, and no query fired
		Student student1 = (Student) session.load(Student.class, new Integer(23));
		printLog(student1, 2);

		printLog("--------Step 2--------");
		// Create new session
		printLog("Create new session and get student by the same id");
		Session newSession = HibernateUtil.getSessionFactory().openSession();
		// Get student with id=23, query is fired because hibernate get it from database
		Student student2 = (Student) newSession.load(Student.class, new Integer(23));
		printLog(student2, 3);
	
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
