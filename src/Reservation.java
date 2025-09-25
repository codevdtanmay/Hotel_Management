import java.sql.*;
import java.util.Scanner;

public class Reservation {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";

    private static final String username = "root";

    private static final String password = "Tanmay@2508";


    public static void main(String[] args) {


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try{
            Connection connection;
            connection = DriverManager.getConnection(url, username, password);
            Statement stm = connection.createStatement();
            while(true){
                System.out.println();
                System.out.println("---------  Hotel Management System ---------");
                Scanner s = new Scanner(System.in);
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservation Details");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. EXIT");
                System.out.println();
                System.out.println("Choose an Option : ");
                int choice = s.nextInt();

                switch (choice){
                    case 1 :
                             reserve_room( s , stm);
                             break;
                    case 2 :
                        view_reserve( stm);
                        break;
                    case 3:
                        get_room_no( s, stm);
                        break;
                    case 4:
                        update_res(stm, s);
                        break;
                    case 5:
                        delete_res(stm,s);
                        break;
                    case 0:
                        exit();
                        s.close();
                        return;
                    default:
                        System.out.println("Invalid Choice");


                }
            }

        }catch (SQLException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }



    private static void reserve_room(Scanner s, Statement stm) {
        try {
            s.nextLine(); // clear buffer

            // Guest Name
            String name;
            while (true) {
                System.out.println("Enter Guest Name: ");
                name = s.nextLine();
                if (name.matches("[a-zA-Z ]+")) break;
                System.out.println("❌ Invalid name. Letters only.");
            }

            // Room Number
            int room;
            while (true) {
                System.out.println("Enter Room Number: ");
                room = s.nextInt();
                if (room > 0) break;
                System.out.println("❌ Invalid room number. Must be positive.");
            }
            s.nextLine();

            // Contact Number
            String num;
            while (true) {
                System.out.println("Enter Contact Number: ");
                num = s.nextLine();
                if (num.matches("\\d{10}")) break;
                System.out.println("❌ Invalid contact. Must be 10 digits.");
            }

            String sql = "INSERT INTO reservation (Name, Room_no, Contact) VALUES ('"
                    + name + "', " + room + ", '" + num + "')";
            int affectedrows = stm.executeUpdate(sql);

            if (affectedrows > 0) {
                System.out.println("✅ Reservation Successful");
            } else {
                System.out.println("❌ Reservation Unsuccessful");
            }

        } catch (SQLException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }


    private static void view_reserve(Statement stm){
         String sql = "SELECT * from reservation";


         try{
             ResultSet resultset = stm.executeQuery(sql);

             System.out.println("Current Reservations:");
             System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
             System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
             System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

             while(resultset.next()){
                 int resID = resultset.getInt("res_id");
                 String name = resultset.getString("Name");
                 int room = resultset.getInt("Room_no");
                 String contact = resultset.getString("Contact");
                 String res_date = resultset.getTimestamp("res_date").toString();

                 System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                         resID, name, room, contact, res_date);
             }
             System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

         }catch (SQLException e){
             System.out.println(e.getMessage());
         }


        }

        private static void get_room_no( Scanner s, Statement stm) throws SQLException {

               System.out.println("Enter Reservation ID : ");
               int resID = s.nextInt();
               System.out.println("Enter Guest Name : ");
               String name = s.next();

               String sql = "SELECT Room_no FROM reservation WHERE res_id = " + resID + " AND Name = '" + name +"' ";

               try {
                   ResultSet resultSet = stm.executeQuery(sql);

                   if (resultSet.next()) {
                       int room = resultSet.getInt("Room_no");

                       System.out.println("Room number for Reservation ID " + resID + " and Guest Name '" + name + "' is : " + room +" ");

                   } else System.out.println("Reservation is not found for the given Id and name ");

               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }

        }

        private static void update_res(Statement stm, Scanner s)throws SQLException {
            System.out.println("Enter Reservation ID To Update : ");
            int resID = s.nextInt();

            if (!reservationExists(stm, resID)){
                System.out.println("Reservation not found for given ID");
            }

            System.out.println("Enter New Guest Name : ");
            String newname = s.next();
            System.out.println("Enter New Room Number :  ");
            int newroom = s.nextInt();
            s.nextLine();
            System.out.println("Enter New Contact Number : ");
            String newcontact = s.next();

        String sql = "UPDATE reservation SET Name = '" + newname +
                "', Room_no = " + newroom +
                ", Contact = '" + newcontact +
                "' WHERE res_id = " + resID;

        try{
            int affectedrows = stm.executeUpdate(sql);

            if (affectedrows > 0){
                System.out.println("Reservation Updated Successfully");
            }else System.out.println("** Reservation Update Failed **");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        }

        private static void delete_res(Statement stm , Scanner s){
            System.out.println("Enter Reservation Id to Delete : ");
            int resID = s.nextInt();

            if (!reservationExists(stm, resID)){
                System.out.println("Reservation not found for given ID");
            }

            String sql = "DELETE  FROM reservation WHERE res_id = "+ resID;

            try{
                int affectedrows = stm.executeUpdate(sql);

                if (affectedrows>0){
                    System.out.println("Reservation Deleted Successfully");
                }else System.out.println("** Reservation Failed to delete **");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private static boolean reservationExists(Statement stm, int resID  ){
          String sql = " SELECT res_id from reservation where res_id = " + resID;

          try{
              ResultSet resultSet = stm.executeQuery(sql);

              return resultSet.next();
          }catch (SQLException e) {
              throw new RuntimeException(e);
          }
        }

        private static void exit() throws InterruptedException {
            System.out.println("Exiting System");
            int i = 5;
            while (i !=0){
                System.out.println(".");
                Thread.sleep(450);
                i--;
            }
            System.out.println("Thank You For using Hotel Management System");
        }
}

