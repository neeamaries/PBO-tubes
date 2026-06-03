/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.tubes.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet("/testing")
public class TestingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // UBAH FILE JSP-NYA SAJA (From BE)
        request.getRequestDispatcher("/WEB-INF/views/template.jsp").forward(request, response);

    }

    // WAJIB ADA: Menangkap request GET (Ketik URL di browser)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Panggil fungsi processRequest di atas
        processRequest(request, response);
    }

    // WAJIB ADA: Menangkap request POST (Submit Form HTML)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Panggil fungsi processRequest di atas
        processRequest(request, response);
    }

}
