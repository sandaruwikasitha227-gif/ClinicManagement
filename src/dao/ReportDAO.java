/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Vikasitha
 */
public class ReportDAO {

    public ResultSet getAppointmentsByDate(String date) {

        String sql =
            "SELECT a.appointment_number, " +
            "p.patient_name, " +
            "d.dentist_name, " +
            "t.treatment_name, " +
            "a.appointment_date, " +
            "a.appointment_time, " +
            "a.status, " +
            "a.total_amount " +
            "FROM appointments a " +
            "INNER JOIN patients p " +
            "ON a.patient_id = p.patient_id " +
            "INNER JOIN dentists d " +
            "ON a.dentist_id = d.dentist_id " +
            "INNER JOIN treatments t " +
            "ON a.treatment_id = t.treatment_id " +
            "WHERE a.appointment_date = ? " +
            "ORDER BY a.appointment_time";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, date);

            return pst.executeQuery();

        } catch (Exception e) {

            System.out.println(
                "Report Error: " + e.getMessage()
            );

            return null;
        }
    }

    public double getRevenueByDate(String date) {

        String sql =
            "SELECT COALESCE(SUM(total_amount), 0) " +
            "FROM appointments " +
            "WHERE appointment_date = ? " +
            "AND status <> 'CANCELLED'";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, date);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {

            System.out.println(
                "Revenue Error: " + e.getMessage()
            );
        }

        return 0;
    }

    public int getAppointmentCount(String date) {

        String sql =
            "SELECT COUNT(*) " +
            "FROM appointments " +
            "WHERE appointment_date = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(1, date);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                "Appointment Count Error: "
                + e.getMessage()
            );
        }

        return 0;
    }
}
