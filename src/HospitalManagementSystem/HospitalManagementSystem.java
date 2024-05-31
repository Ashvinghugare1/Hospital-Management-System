package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url= "jdbc:mysql://localhost:3306/hospital";

    private static final String username= "root";

    private static final String password= "0000";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username,password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection, scanner);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice: ");
                int choice = scanner.nextInt();
                switch(choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    //      add patient
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    //      view patient
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;

                        //      view Doctor
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println("Booking appointment...");
                        break;
                    //      book Appointment
                    case 5:
                        System.out.println("Exiting the system...");
                            return;
                    default:
                        System.out.println("Enter valid choice ");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.println("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvaibility(doctorId, appointmentDate)){
                String appointmentQuery ="INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked");
                    }else{
                        System.out.println("Appointment Not Booked");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }else{
                System.out.println("Doctor not available.");
            }
        }else{
            System.out.println("Either Doctor Or Patient does not Exist.");
        }


        String query = "INSERT INTO appoinments (patient_id, doctor_id, appoinment_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            statement.setInt(2, doctorId);
            statement.setString(3, appointmentDate);
            ((PreparedStatement) statement).executeUpdate();
            System.out.println("Appointment booked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to book appointment.");
        }


    }

    private static boolean checkDoctorAvaibility(int doctorId, String appointmentDate) {
        String query = "SELECT COUNT (*) FROM appointments WHERE doctor_id=? AND appointment_date=?";

        return false;
    }


}
