/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import model.Dentist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vikasitha
 */
public class DentistDAO {

    public boolean addDentist(Dentist dentist) {

        String sql = "INSERT INTO dentists "
                + "(dentist_name, specialization, status) "
                + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, dentist.getDentistName());
            pst.setString(2, dentist.getSpecialization());
            pst.setString(3, dentist.getStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Dentist Insert Error: " + e.getMessage()
            );

            return false;
        }
    }

    public List<Dentist> getAllDentistsList() {

        List<Dentist> dentists = new ArrayList<>();

        String sql = "SELECT * FROM dentists "
                + "ORDER BY dentist_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Dentist dentist = new Dentist();

                dentist.setDentistId(
                        rs.getInt("dentist_id")
                );

                dentist.setDentistName(
                        rs.getString("dentist_name")
                );

                dentist.setSpecialization(
                        rs.getString("specialization")
                );

                dentist.setStatus(
                        rs.getString("status")
                );

                dentists.add(dentist);
            }

        } catch (Exception e) {

            System.out.println(
                    "Dentist Loading Error: " + e.getMessage()
            );
        }

        return dentists;
    }

    public boolean updateDentist(Dentist dentist) {

        String sql = "UPDATE dentists SET "
                + "dentist_name = ?, "
                + "specialization = ?, "
                + "status = ? "
                + "WHERE dentist_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, dentist.getDentistName());
            pst.setString(2, dentist.getSpecialization());
            pst.setString(3, dentist.getStatus());
            pst.setInt(4, dentist.getDentistId());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Dentist Update Error: " + e.getMessage()
            );

            return false;
        }
    }
    
    public ResultSet getAllDentists() {

    String sql = "SELECT dentist_id, dentist_name "
            + "FROM dentists "
            + "WHERE status = 'ACTIVE' "
            + "ORDER BY dentist_name";

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement pst =
                con.prepareStatement(sql);

        return pst.executeQuery();

    } catch (Exception e) {

        System.out.println(
                "Error loading dentists: "
                + e.getMessage()
        );

        return null;
    }
}
}
