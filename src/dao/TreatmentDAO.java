/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vikasitha
 */
public class TreatmentDAO {

    public boolean addTreatment(Treatment treatment) {

        String sql = "INSERT INTO treatments "
                + "(treatment_name, treatment_cost, status) "
                + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, treatment.getTreatmentName());
            pst.setDouble(2, treatment.getTreatmentCost());
            pst.setString(3, treatment.getStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Treatment Insert Error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public List<Treatment> getAllTreatmentsList() {

        List<Treatment> treatments = new ArrayList<>();

        String sql = "SELECT * FROM treatments "
                + "ORDER BY treatment_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Treatment treatment =
                        new Treatment();

                treatment.setTreatmentId(
                        rs.getInt("treatment_id")
                );

                treatment.setTreatmentName(
                        rs.getString("treatment_name")
                );

                treatment.setTreatmentCost(
                        rs.getDouble("treatment_cost")
                );

                treatment.setStatus(
                        rs.getString("status")
                );

                treatments.add(treatment);
            }

        } catch (Exception e) {

            System.out.println(
                    "Treatment Loading Error: "
                    + e.getMessage()
            );
        }

        return treatments;
    }

    /*
     * Used by RegisterAppointment.java
     */
    public ResultSet getAllTreatments() {

        String sql = "SELECT treatment_id, "
                + "treatment_name, treatment_cost "
                + "FROM treatments "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY treatment_name";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            return pst.executeQuery();

        } catch (Exception e) {

            System.out.println(
                    "Error loading treatments: "
                    + e.getMessage()
            );

            return null;
        }
    }

    public boolean updateTreatment(Treatment treatment) {

        String sql = "UPDATE treatments SET "
                + "treatment_name = ?, "
                + "treatment_cost = ?, "
                + "status = ? "
                + "WHERE treatment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(
                    1,
                    treatment.getTreatmentName()
            );

            pst.setDouble(
                    2,
                    treatment.getTreatmentCost()
            );

            pst.setString(
                    3,
                    treatment.getStatus()
            );

            pst.setInt(
                    4,
                    treatment.getTreatmentId()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Treatment Update Error: "
                    + e.getMessage()
            );

            return false;
        }
    }
}
