package HospitalManagementSystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.zip.ZipFile;

public class Patient {
    private final Connection connection;
    private final Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter First Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();
        System.out.println("Enter Patient Address: ");
        String address = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender, address) VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, address);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient Added Successfully");
            }else{
                System.out.println("Failed to insert patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewPatient(){
        String query = "SELECT * FROM patients";
        try{
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet resultSet = prepareStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+-------------+--------------------+-----+---------+----------------+");
            System.out.println("|Patient Id   | Name               | Age | Gender  | Address        |");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String address = resultSet.getString("address");
                System.out.printf("| %-11d | %-18s | %-3d | %-7s | %-14s |\n", id, name, age, gender, address);
            }

            System.out.println("+-------------+--------------------+-----+---------+----------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <resultSet> boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setInt(1, id);
            try(ResultSet resultSet = prepareStatement.executeQuery()){
                return ((ResultSet) resultSet).next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}