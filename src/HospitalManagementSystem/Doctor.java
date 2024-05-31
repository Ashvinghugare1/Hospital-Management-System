package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private final Connection connection;
    private final Scanner scanner;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public void addDoctor() {
        System.out.println("Enter First Name: ");
        String name = scanner.next();
        System.out.println("Enter Doctor specializtion: ");
        scanner.next();

        try {
            String query = "INSERT INTO doctors(name, specialization) VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            String specialization = null;
            preparedStatement.setString(2, specialization);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Doctor Added Successfully");
            }else{
                System.out.println("Failed to insert Doctor");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewDoctor(){
        String query = "SELECT * FROM doctors";
        try{
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet resultSet = prepareStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+-------------+--------------------+----------------+");
            System.out.println("|Doctors Id   | Name               | Specialization |");
            System.out.println("+-------------+--------------------+----------------+");
            while(resultSet.next()){
             int id = resultSet.getInt("id");
             String name = resultSet.getString("name");
             String specialization = resultSet.getString("specialization");
             System.out.printf("| %-11d | %-18s | %-20s |\n", id, name, specialization);

            }

            System.out.println("+-------------+-------------------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <resultSet> boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
        try{
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setInt(1, id);
            try(ResultSet resultSet = prepareStatement.executeQuery()){
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
