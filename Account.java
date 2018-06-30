package account;

import java.io.*;

import java.lang.Math.*;
import java.math.*;
import java.util.*;


public  class Account {
	private String firstname;
	private String lastname;
	private int id = 0;
	private double balance = 100.0;
	private double monthlyInterestRate = 5.0;
	private double withdraw;
	private double deposit;
	private double amount;

	private Date dateCreated;




	Account() {

		dateCreated = new java.util.Date();
	}

	Account(int id, String firstname, String lastname, double balance){

		this.id = id;
		this.balance = balance;
		this.firstname = firstname;
		this.lastname = lastname;
		dateCreated = new java.util.Date();
	}


	public void getMonthlyInterestRate() {
		System.out.print("Monthly Interest rate is: "+monthlyInterestRate + "%\n\n");
	}

	public java.util.Date getDateCreated() {
		return dateCreated;
	}

	public double getMonthlyInterest(double getMonthlyInterestRate) {
		return(balance*getMonthlyInterestRate);
	}

	public String acctInfo() {
		return id + " " + firstname + " " + lastname + " " + balance ;
	}

	public void createFile() {
		File file = new File("C:\\Users\\Gilianny\\Desktop\\Desktop\\BankAccount.txt");


		if (file.exists()) {
			System.out.println("File already exists");
			System.exit(1);
		}


		try {

			Account one = new Account(1, "Jane", "Doe", 100);
			Account two = new Account(2, "Paul", "Smith", 100);
			Account three = new Account(3, "Susan", "George", 100);
			Account four = new Account(4, "Bob", "Hope", 100);
			Account five = new Account(5, "Linda", "James", 100);

			PrintWriter insert = new PrintWriter(file);

			ArrayList<String> accounts = new ArrayList<>();


			insert.println(one.acctInfo());
			insert.println(two.acctInfo());
			insert.println(three.acctInfo());
			insert.println(four.acctInfo());
			insert.println(five.acctInfo());



			insert.close();




		} catch (FileNotFoundException e) {
			System.out.print("File not  found");

		}
	}

	public void readfileintoArray(File file) throws Exception {


		String value;
		Scanner input = new Scanner(file);
		ArrayList<String> accounts = new ArrayList<>();

		do {
			value = input.next(); // Read a value from the input
			accounts.add(value); // Add the value 
		} while (input.hasNext());	

		input.close();


		mainMenu(file,accounts);
	}

	public void searchInAccounts(ArrayList<String> accounts, String searchString) {

		System.out.print(accounts.subList(accounts.indexOf(searchString) , accounts.indexOf(searchString)+4));
	}

	public String depositInAccounts(ArrayList<String> accounts, String searchString, double amount) {

		String balance = String.valueOf(Double.parseDouble(accounts.get(accounts.indexOf(searchString)+3)) + amount);

		accounts.set(accounts.indexOf(searchString)+3, balance);

		return accounts.get(accounts.indexOf(searchString)+3) ;
	}

	public String withdrawInAccounts(ArrayList<String> accounts, String searchString, double amount) {

		if (amount > Double.parseDouble(accounts.get(accounts.indexOf(searchString)+3))) {

			System.out.println("\nAmount desired is more than current balance!");
		}
		else {

			String balance = String.valueOf(Double.parseDouble(accounts.get(accounts.indexOf(searchString)+3)) - amount);

			accounts.set(accounts.indexOf(searchString)+3, balance);

		}

		return accounts.get(accounts.indexOf(searchString)+3);

	}

	public void overwriteFile(File file, ArrayList<String> accounts) throws Exception{


		Scanner input = new Scanner(file);
		String value= input.next();
		String newlines = accounts.toString();

		PrintWriter output = new PrintWriter(file);


		output.println(value.replaceAll(value, newlines));



		input.close();
		output.close();

	}


	public void mainMenu(File file, ArrayList<String> accounts) throws Exception{




		Scanner keyboard = new Scanner(System.in);

		int selection;
		int selection2;
		do {
			//prompt main menu of 3 options 

			System.out.print("********************\n"
					+"Main Menu: \n \n"
					+ "1. Create New Account \n"
					+ "2. Search Existing Account \n"
					+ "3. Exit \n"
					+ "********************\n"
					+ "\nEnter selection: ");

			//insert selection
			selection = keyboard.nextInt();


			// case 1 Creates a new account

			if (selection == 1) {

				boolean contain = false;
				do {
					System.out.println("Enter An Account ID Number: ");
					String id = (keyboard.next());

					if(!accounts.contains(id)) {

						accounts.add(id);
						contain = false;
					}else {
						contain = true;
						System.out.println("Id Already Exists, Enter a Unique Id # \n");
					}
				}while(contain); 



				System.out.print("Enter First Name: ");
				String firstname = keyboard.next();
				accounts.add(firstname);
				System.out.print("Enter Last Name: ");
				String lastname = keyboard.next();
				accounts.add(lastname);
				System.out.print("Enter Balance: ");
				String balance = keyboard.next();
				accounts.add(balance);

			}

			else if (selection == 2) { 


				boolean contain = false;

				do {

					System.out.print("\nEnter Desired Account: ");
					String acctId = keyboard.next();

					if (!accounts.contains(acctId)) {
						contain = false;
						System.out.println("Enter a Valid Account Id!");
					}
					else {

						do {

							System.out.print("********************\n"
									+"Menu for Acct #: " + acctId + " \n \n"
									+ "1. Account Name and Balance \n"
									+ "2. Withdraw \n"
									+ "3. Deposit \n"
									+ "4. Get Monthly Interest Rate \n"
									+ "5. Exit to Main Menu \n"
									+ "********************\n"
									+ "\nEnter selection: ");



							selection2 = keyboard.nextInt();


							switch (selection2) {



							case 1: 

								searchInAccounts(accounts, (acctId));
								System.out.print("\n");



								break;

							case 2:
								System.out.print("How much would you like to withdraw: ");

								double amountw = keyboard.nextDouble();



								System.out.println("\nCurrent Balance: " + withdrawInAccounts( accounts, (acctId), amountw));

								break;

							case 3:
								System.out.print("How much would you like to deposit: ");

								double amountd = keyboard.nextDouble();


								System.out.println("\nCurrent Balance: " + depositInAccounts( accounts, (acctId), amountd));

								break;

							case 4:
								getMonthlyInterestRate();

							}

						}while(selection2 != 5 );

					}

				}while(contain);

			}

		}while(selection !=3);

		keyboard.close();

		overwriteFile(file, accounts);
	}
}