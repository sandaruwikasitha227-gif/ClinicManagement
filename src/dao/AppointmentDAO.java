/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Vikasitha
 */
public class AppointmentDAO {

    public String generateAppointmentNumber() {

        String sql = "SELECT appointment_number "
                + "FROM appointments "
                + "ORDER BY appointment_id DESC "
                + "LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {

                String lastNumber =
                        rs.getString("appointment_number");

                int number = Integer.parseInt(
                        lastNumber.substring(2)
                );

                number++;

                return String.format("AP%05d", number);
            }

        } catch (Exception e) {

            System.out.println(
                    "Appointment Number Error: "
                    + e.getMessage()
            );
        }

        return "AP00001";
    }

    public boolean isDentistAvailable(
            int dentistId,
            String date,
            String time) {

        String sql = "SELECT COUNT(*) FROM appointments "
                + "WHERE dentist_id = ? "
                + "AND appointment_date = ? "
                + "AND appointment_time = ? "
                + "AND status <> 'CANCELLED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, dentistId);
            pst.setString(2, date);
            pst.setString(3, time);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) == 0;
            }

        } catch (Exception e) {

            System.out.println(
                    "Availability Check Error: "
                    + e.getMessage()
            );
        }

        return false;
    }

    public boolean addAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointments "
                + "(appointment_number, patient_id, dentist_id, "
                + "treatment_id, appointment_date, appointment_time, "
                + "status, consultation_fee, total_amount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, appointment.getAppointmentNumber());
            pst.setInt(2, appointment.getPatientId());
            pst.setInt(3, appointment.getDentistId());
            pst.setInt(4, appointment.getTreatmentId());
            pst.setString(5, appointment.getAppointmentDate());
            pst.setString(6, appointment.getAppointmentTime());
            pst.setString(7, appointment.getStatus());
            pst.setDouble(8, appointment.getConsultationFee());
            pst.setDouble(9, appointment.getTotalAmount());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Appointment Insert Error: "
                    + e.getMessage()
            );

            return false;
        }
    }
    
    public ResultSet searchAppointment(String appointmentNumber) {

    String sql =
            "SELECT a.appointment_number, "
            + "p.patient_name, p.address, p.contact_number, "
            + "d.dentist_name, "
            + "t.treatment_name, t.treatment_cost, "
            + "a.appointment_date, a.appointment_time, "
            + "a.status, a.consultation_fee, a.total_amount "
            + "FROM appointments a "
            + "INNER JOIN patients p "
            + "ON a.patient_id = p.patient_id "
            + "INNER JOIN dentists d "
            + "ON a.dentist_id = d.dentist_id "
            + "INNER JOIN treatments t "
            + "ON a.treatment_id = t.treatment_id "
            + "WHERE a.appointment_number = ?";

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement pst =
                con.prepareStatement(sql);

        pst.setString(1, appointmentNumber);

        return pst.executeQuery();

    } catch (Exception e) {

        System.out.println(
                "Appointment Search Error: "
                + e.getMessage()
        );

        return null;
    }
}
}
