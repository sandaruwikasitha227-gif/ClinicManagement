/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vikasitha
 */
public class PatientDAO {

    public boolean addPatient(Patient patient) {

        String sql = "INSERT INTO patients "
                + "(patient_name, address, contact_number, email, "
                + "date_of_birth, gender) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, patient.getPatientName());
            pst.setString(2, patient.getAddress());
            pst.setString(3, patient.getContactNumber());
            pst.setString(4, patient.getEmail());

            if (patient.getDateOfBirth() == null
                    || patient.getDateOfBirth().isEmpty()) {

                pst.setNull(5, java.sql.Types.DATE);

            } else {

                pst.setDate(
                        5,
                        java.sql.Date.valueOf(
                                patient.getDateOfBirth()
                        )
                );
            }

            pst.setString(6, patient.getGender());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Patient Insert Error: " + e.getMessage()
            );

            return false;
        }
    }

    public List<Patient> getAllPatients() {

        List<Patient> patients = new ArrayList<>();

        String sql = "SELECT * FROM patients ORDER BY patient_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Patient patient = new Patient();

                patient.setPatientId(
                        rs.getInt("patient_id")
                );

                patient.setPatientName(
                        rs.getString("patient_name")
                );

                patient.setAddress(
                        rs.getString("address")
                );

                patient.setContactNumber(
                        rs.getString("contact_number")
                );

                patient.setEmail(
                        rs.getString("email")
                );

                if (rs.getDate("date_of_birth") != null) {

                    patient.setDateOfBirth(
                            rs.getDate("date_of_birth").toString()
                    );
                }

                patient.setGender(
                        rs.getString("gender")
                );

                patients.add(patient);
            }

        } catch (Exception e) {

            System.out.println(
                    "Patient Loading Error: " + e.getMessage()
            );
        }

        return patients;
    }
}
