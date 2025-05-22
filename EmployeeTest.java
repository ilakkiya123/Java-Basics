package practice2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import practice.Employee;

public class EmployeeTest {

	public static void main(String[] args) {

		List<Employee> empList = new ArrayList<>();

		empList.add(new Employee(1, "Jhansi", 32, "Female", "HR", 2011, 25000.0));
		empList.add(new Employee(2, "Smith", 25, "Male", " Sales", 2015, 13500.0));
		empList.add(new Employee(3, "David", 29, "Male", "Infrastructure", 2012, 18000.0));
		empList.add(new Employee(4, "Orlen", 28, "Male", "Development", 2014, 32500.0));
		empList.add(new Employee(5, "Charles", 27, "Male", "HR", 2013, 22700.0));
		empList.add(new Employee(6, "Cathy", 43, "Male", "Security", 2016, 10500.0));
		empList.add(new Employee(7, "Ramesh", 35, "Male", "Finance", 2010, 27000.0));
		empList.add(new Employee(8, "Suresh", 31, "Male", "Development", 2015, 34500.0));
		empList.add(new Employee(9, "Gita", 24, "Female", "Sales", 2016, 11500.0));
		empList.add(new Employee(10, "Mahesh", 38, "Male", "Security", 2015, 11000.5));
		empList.add(new Employee(11, "Gauri", 27, "Female", "Infrastructure", 2014, 15700.0));
		empList.add(new Employee(12, "Nitin", 25, "Male", "Development", 2016, 28500.0));
		empList.add(new Employee(13, "Swati", 27, "Female", "Finance", 2013, 21300.0));
		empList.add(new Employee(14, "Buttler", 24, "Male", "Sales", 2015, 10700.5));
		empList.add(new Employee(15, "Ashok", 23, "Male", "Infrastructure", 2018, 12700.0));
		empList.add(new Employee(16, "Sanvi", 26, "Female", "Development", 2015, 28900.0));

		//      *************** JAVA 8 Questions and Answers ***************		

		// 1) How many male and female employees are there in the organization ?
		Map<String, Long> maleAndFemaleEmployees = empList.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()));
		System.out.println(maleAndFemaleEmployees);

		// 2) Print the name of all departments in the organization ?		
		empList.stream().map(e -> e.getDepartment()).distinct().forEach(d -> System.out.println(d));

		// 3) What is the average age of Male and Female employees ?
		Map<String, Double> averageAge = empList.stream().collect(Collectors.groupingBy
				(Employee::getGender, Collectors.averagingInt(Employee::getAge)));	
		System.out.println(averageAge);

		// 4) Get the details of highest paid employee in the organization ?
		Optional<Employee> highestPaidEmp = empList.stream().max(Comparator.comparing(Employee::getSalary));
		if(highestPaidEmp.isPresent()) {
			System.out.println(highestPaidEmp.get());
		}

		// 5) Get the names of all employees who have joined after 2015 ?
		empList.stream().filter(e -> e.getYearOfJoining() > 2015).map(e -> e.getName()).forEach(System.out::println);

		// 6) Count the number of employees in each department ?
		Map<String, Long> deptWiseEmpCount = empList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
		System.out.println(deptWiseEmpCount);

		// 7) What is the average salary of each department ?
		Map<String, Double> deptWiseAvgSalary = empList.stream().collect(Collectors.groupingBy
				(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
		System.out.println(deptWiseAvgSalary);

		// 8) Get the details of youngest male employee in the Development department ?
		Optional<Employee> youngestMaleEmp = empList.stream().filter(e -> e.getGender().equals("Male") && e.getDepartment()
				.equals("Development")).min(Comparator.comparing(Employee::getAge));
		System.out.println(youngestMaleEmp.get());

		// 9) Who has the most working experience in the organization ?	
		Optional<Employee> mostExperience = empList.stream().min(Comparator.comparing(Employee::getYearOfJoining));
		if(mostExperience.isPresent()) {
			System.out.println(mostExperience.get());
		}

		// 10) How many male and female employees are there in the sales team ?
		Map<String, Long> maleAndFemaleInSales = empList.stream().filter(e -> e.getDepartment().equals("Sales"))
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
		System.out.println(maleAndFemaleInSales);

		// 11) What is the average salary of Male and Female Employees ?
		Map<String, Double> avgSalOfMaleAndFemale = empList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
		System.out.println(avgSalOfMaleAndFemale);

		// 12) List down the names of all employees in each department ?
		/**
		 * Understanding Collectors.mapping()
			The mapping() method is a downstream collector that applies a function to each element in the stream and then collects the transformed elements. Its signature in the Collectors class is:

			public static <T, U, A, R> Collector<T, ?, R> mapping(
    		Function<? super T, ? extends U> mapper,
    		Collector<? super U, A, R> downstream)
			Parameters:

			mapper: A function that transforms elements of type T to type U.
			downstream: A collector to accumulate the transformed elements.
		 */
		Map<String, List<String>> deptWiseEmpNames = empList.stream().collect
				(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getName,
				Collectors.toList())));
		System.out.println(deptWiseEmpNames);
		
		// 13) What is the average salary and total salary of the whole organization?
		DoubleSummaryStatistics salaries = empList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
		System.out.println("Average salary of an organization : " + salaries.getAverage());
		System.out.println("Total salary of an organization : " + salaries.getSum());
		
		// 14) Separate the employees who are younger or equal to 25 years from those employees who are older than 25 years.
		Map<Boolean, List<Employee>> ageMap = empList.stream().collect(Collectors.partitioningBy(e -> e.getAge() > 25));
		List<Employee> olderThan25 = ageMap.get(true);
		List<Employee> youngerOrEqualTo25 = ageMap.get(false);
		
		System.out.println("Employees older than 25 Years -> ");
		for(Employee e : olderThan25) {
			System.out.println(e.getName());
		}
		
		System.out.println("Employees younger or equal to 25 years -> ");
		for(Employee e : youngerOrEqualTo25) {
			System.out.println(e.getName());
		}
		
		// 15) Who is the oldest employee in the organization? What is his age and which department he belongs to?		
		Optional<Employee> oldestEmployee = empList.stream().max(Comparator.comparing(Employee::getAge));
		if(oldestEmployee.isPresent()) {
			System.out.println("Age of oldest employee is : " + oldestEmployee.get().getAge());
			System.out.println("Oldest employee in an organization belongs to department -> " + oldestEmployee.get().getDepartment());
		}
		
		// 16) Get the employees with second highest salary ?
		Optional<Employee> secondHighestSalary = empList.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).findFirst();
		System.out.println(secondHighestSalary.get());
		
		// 17) Get the male employee with highest age
		Optional<Employee> maleEmpWithHighestAge = empList.stream().filter(e -> e.getGender().equals("Male")).max(Comparator.comparing(Employee::getAge));
		System.out.println(maleEmpWithHighestAge.get());
	}

}
