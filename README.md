# 🏨 Hotel Reservation Management System  

A simple **Java + MySQL** based console application that allows hotel staff to manage reservations.  
This project demonstrates **JDBC connectivity, CRUD operations, input validation, and layered programming concepts**.  

---

## ✨ Features  
✅ **Reserve a Room** – Add guest name, room number, and contact info  
✅ **View Reservations** – Display all reservations in a tabular format  
✅ **Get Room Number** – Find a guest’s room by Reservation ID and name  
✅ **Update Reservation** – Modify guest details, room number, or contact  
✅ **Delete Reservation** – Cancel an existing reservation  
✅ **Exit System** – Graceful exit with delay animation  

---

## 🗂️ Project Structure  

```
Hotel-Reservation-System/
│── Reservation.java       # Main class with menu and CRUD methods
│── README.md              # Project documentation
│── hotel_db.sql           # Database schema (if exported)
```

---

## 🛠️ Tech Stack  
- **Language:** Java (JDK 8+ recommended)  
- **Database:** MySQL  
- **Connector:** MySQL JDBC Driver (`mysql-connector-j`)  

---

## ⚙️ Database Setup  

1. Open MySQL Workbench or CLI.  
2. Create the database:  
   ```sql
   CREATE DATABASE hotel_db;
   USE hotel_db;
   ```
3. Create the `reservation` table:  
   ```sql
   CREATE TABLE reservation (
       res_id INT AUTO_INCREMENT PRIMARY KEY,
       guest_name VARCHAR(100) NOT NULL,
       Room_no INT NOT NULL,
       Contact VARCHAR(15) NOT NULL,
       res_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```
4. (Optional) Add constraints for cleaner data:  
   ```sql
   ALTER TABLE reservation ADD CONSTRAINT chk_contact_length CHECK (LENGTH(Contact) = 10);
   ALTER TABLE reservation ADD CONSTRAINT unique_room_date UNIQUE (Room_no, res_date);
   ```

---

## ▶️ How to Run  

1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/Hotel-Reservation-System.git
   cd Hotel-Reservation-System
   ```

2. Add MySQL JDBC Connector (`mysql-connector-j-x.x.xx.jar`) to your project classpath.  

3. Update database credentials in `Reservation.java`:  
   ```java
   private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
   private static final String username = "root";
   private static final String password = "your-password";
   ```

4. Compile and run:  
   ```bash
   javac Reservation.java
   java Reservation
   ```

---

## 📸 Sample Output  

```
--------- Hotel Management System ---------
1. Reserve a Room
2. View Reservation Details
3. Get Room Number
4. Update Reservation
5. Delete Reservation
0. EXIT
Choose an Option :
```

**Viewing reservations example:**  

```
+----------------+-----------------+---------------+----------------------+-------------------------+
| Reservation ID | Guest           | Room Number   | Contact Number       | Reservation Date        |
+----------------+-----------------+---------------+----------------------+-------------------------+
| 1              | John Doe        | 101           | 9876543210           | 2025-09-25 20:15:30     |
+----------------+-----------------+---------------+----------------------+-------------------------+
```

---

## 🚀 Future Improvements  

- Switch from `Statement` to **PreparedStatement** (to prevent SQL injection)  
- Add **input validation methods** for reusability  
- Move to **DAO + Service layer architecture** for cleaner code  
- Create a **JavaFX GUI** or **Spring Boot REST API** frontend  
- Add **Check-in / Check-out dates** and room availability logic  

---

## 👤 Author  
**Tanmay** – B.TECH(IT) 
A JAVA DEV.  
