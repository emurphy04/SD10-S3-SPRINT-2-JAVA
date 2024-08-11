# Java E-Commerce Platform
## System Documentation

Document Version: 1.1.0
Date: 10/08/2024

## Contents 
1. [Document Management](#1-document-management)
   1.1 [Contributors](#11-contributors)
   1.2 [Version Control](#12-version-control)
2. [User Documentation](#2-user-documentation)
   2.1 [Classes & Functionality](#21-classes-and-functionality)
   2.2 [UML Diagrams](#22-uml-diagrams)
   2.3 [How to Start/Access the Application](#23-how-to-startaccess-the-application)
3. [Development Documentation](#3-development-documentation)
   3.1 [Javadoc's](#31-javadocs)
   3.2 [Source Code Structure](#32-source-code-structure)
   3.3 [Build Process](#33-build-process)
   3.4 [Compiler Time Dependencies](#34-compiler-time-dependencies)
   3.5 [Development Standards](#35-development-standards)
   3.6 [Setting up the Database for Development](#36-setting-up-the-database-for-development)
   3.7 [Getting the Source Code from the Repository](#37-getting-the-source-code-from-the-repository)
4. [Deployment Documentation](#4-deployment-documentation)
   4.1 [Installation Manual](#41-installation-manual)
   4.2 [Demonstrating the Project](#42-demonstrating-the-project)

## 1 Document Management 

This project is a Java application designed to manage users (Admin, Buyer, Seller) and computer parts within an e-commerce platform. The system provides functionalities for user management, role-based access, and detailed management of computer parts (items) including adding, editing, and removing parts. The platform is built using Java and PostgreSQL and is console-based.

### 1.1 Contributors 

Role | Unit | Name 
-----|------|------
Software Development Lead | | Ethan Murphy 
Technical Writer/Tester | | Brenda Armstrong

### 1.2 Version Control 

Date | Version | Author | Section | Amendment 
-----|---------|--------|---------|----------
09/08/2024 | 1.0.0 | Brenda Armstrong | | Initial Version
10/08/2024 | 1.1.0 | Brenda Armstrong | All | Updated with improved functionality and code structure

## 2 User Documentation 

The Computer Parts E-Commerce Platform is a Java-based application designed to manage various computer parts available for purchase. The platform supports different user roles such as Admin, Buyer, and Seller, each with specific capabilities. Users can manage parts (items) by adding new parts, updating existing parts, or removing parts from the inventory. Buyers can browse through available parts, add them to their cart, and make purchases.

### 2.1 Classes and Functionality

#### User
The base class for all user roles in the system. It contains common attributes such as username, password, and email that are shared among all user types (Admin, Buyer, Seller).

- Attributes: username, password, email, role, cart
- Methods:
  - getRole(): Returns the role of the user.
  - addToCart(Items item): Adds an item to the user's shopping cart.
  - getCart(): Displays the user's shopping cart and total price.
  - getUsername(), getPassword(), getEmail(): Getter methods for user attributes.
  - setUsername(String), setPassword(String), setEmail(String): Setter methods for user attributes.

#### Admin
Represents an administrator with capabilities to manage the platform's overall settings and users.

- Attributes: Inherits from User
- Methods:
  - getRole(): Returns "admin" as the user's role.

#### Buyer
Represents a user who can browse, select, and purchase computer parts from the platform.

- Attributes: Inherits from User
- Methods:
  - getRole(): Returns "buyer" as the user's role.

#### Seller
Represents a user who can list computer parts for sale on the platform.

- Attributes: Inherits from User
- Methods:
  - getRole(): Returns "seller" as the user's role.

#### Items
Represents a computer part available in the inventory.

- Attributes: itemName, itemSku, itemPrice, itemDesc, itemCat, userListed
- Methods:
  - Getter and setter methods for all attributes
  - toString(): Returns a formatted string representation of the item

#### ItemDAO
Data Access Object for managing Items in the database.

- Methods:
  - getAllItems(): Retrieves all items from the database.
  - addItem(Items item): Adds a new item to the database.
  - deleteItem(String sku): Removes an item from the database.
  - searchItems(String searchTerm, String category): Searches for items based on name or category.

#### UserDAO
Data Access Object for managing Users in the database.

- Methods:
  - getAllUsers(): Retrieves all users from the database.
  - addUser(User user): Adds a new user to the database.
  - deleteUser(String username): Removes a user from the database.

#### UserService
Main class that handles user interactions and business logic.

- Methods:
  - main(String[] args): Entry point of the application.
  - login(): Handles user login.
  - signUp(): Handles user registration.
  - handleUserSession(User user): Manages user sessions based on role.
  - handleAdminSession(Admin admin): Manages admin-specific operations.
  - handleBuyerSession(Buyer buyer): Manages buyer-specific operations.
  - handleSellerSession(Seller seller): Manages seller-specific operations.

### 2.2 UML Diagrams 

[[View UML diagram Here]](https://github.com/emurphy04/SD10-S3-SPRINT-2-JAVA/blob/main/diagrams/j-diagram.png)

### 2.3 How to Start/Access the Application 

1. Install JDK: Ensure that JDK 11 or higher is installed on your system.
2. Clone Repository: Clone the project repository using Git.
3. Install Dependencies: Download and include the PostgreSQL JDBC driver and jBCrypt in your project.
   - pgJDBC: Download from [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download/postgresql-42.7.3.jar)
   - jBCrypt: Available in the dependencies directory of the project.
4. Set up Database: Follow the instructions in section 3.6 to set up the PostgreSQL database.
5. Configure Database Connection: Update the JDBC_URL, JDBC_USER, and JDBC_PASSWORD in ItemDAO.java and UserDAO.java to match your PostgreSQL setup.
6. Compile and Run: Use VS Code or your preferred IDE to compile the project:
   - Open the terminal in VS Code (Terminal > New Terminal).
   - Navigate to the directory containing your Java files.
   - Compile: `javac *.java`
   - Run: `java UserService`

## 3 Development Documentation 

### 3.1 JavaDoc

JavaDoc comments are provided for all classes and methods to document their functionality and usage. To generate JavaDoc documentation:

1. Navigate to the project root directory in the terminal.
2. Run the command: `javadoc -d doc *.java`
3. Open the generated `index.html` file in the `doc` folder to view the documentation.

### 3.2 Source Code Structure

All Java source files are organized in a single directory. Each class is defined in its own .java file:

- User.java
- Admin.java
- Buyer.java
- Seller.java
- Items.java
- ItemDAO.java
- UserDAO.java
- UserService.java

### 3.3 Build Process

Compile the project using a Java compiler:

```
javac *.java
```

You can also use the built-in "Run" and "Debug" features of VS Code to compile and execute the main method of the UserService class.

### 3.4 Compiler Time Dependencies

Add these dependencies to your project to compile successfully:

- pgJDBC: PostgreSQL JDBC driver.
- jBCrypt: A library for hashing passwords securely.

Ensure these JAR files are in your classpath when compiling and running the application.

### 3.5 Development Standards

The project follows standard Java coding conventions and best practices:

- Classes start with an uppercase letter and use CamelCase (e.g., UserService).
- Methods and variables start with a lowercase letter and use camelCase (e.g., addToCart).
- Constants are in all uppercase with underscores (e.g., JDBC_URL).
- Use meaningful and descriptive names for classes, methods, and variables.
- Include comments for complex logic or non-obvious code sections.
- Use proper indentation and formatting for improved readability.

### 3.6 Setting up the Database for Development

This application uses PostgreSQL for persistent data storage. Follow these steps to set up the database:

1. Install PostgreSQL on your development machine.
2. Create a new database named "Java".
3. Use the provided SQL scripts to create the necessary tables:
   - Run `usersCreate.sql` to create the users table.
   - Run `prodsCreate.sql` to create the products table.
   - (Optional) Run `prodsInsert.sql` to populate the products table with sample data.

Database Schema:

- Users Table
  - username (text, primary key)
  - password (text)
  - email (text)
  - role (text)

- Products Table
  - itemname (text)
  - itemsku (text, primary key)
  - itemprice (double precision)
  - itemdesc (text)
  - itemcat (text)
  - userListed (text)

### 3.7 Getting the Source Code from the Repository

To clone the repository:

```
git clone https://github.com/emurphy04/SD10-S3-SPRINT-2-JAVA
cd SD10-S3-SPRINT-2/JAVA
```

## 4 Deployment Documentation 

### 4.1 Installation Manual

To deploy the application:

1. Java Runtime Environment (JRE): Ensure that JRE 11 or higher is installed on the target system.
2. PostgreSQL: Install and configure PostgreSQL on the target system.
3. Database Configuration: 
   - Create a new database named "Java".
   - Import the SQL schema using the provided SQL scripts (usersCreate.sql and prodsCreate.sql).
4. Application Files:
   - Copy all compiled .class files to the deployment directory.
   - Include the required JAR files (PostgreSQL JDBC driver and jBCrypt) in the deployment directory.
5. Configuration:
   - Update the database connection details in ItemDAO.java and UserDAO.java if necessary.

### 4.2 Demonstrating the Project

To run the application:

1. Open a terminal or command prompt.
2. Navigate to the directory containing the compiled .class files and JAR dependencies.
3. Run the command: `java -cp .:postgresql-42.7.3.jar:jbcrypt-0.4.jar UserService`
   (Use `;` instead of `:` as a separator on Windows)
4. Follow the on-screen prompts to interact with the system. 
5. Use the following steps to demonstrate key features:
   - Register a new user (buyer or seller)
   - Log in as an admin to manage users and view all products
   - Log in as a seller to add new product listings
   - Log in as a buyer to search for products, add items to cart, and checkout

This completes the system documentation for the Java E-Commerce Platform. For any further questions or support, please contact the development team.
