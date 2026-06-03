package com.tubes.controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 2. TAMBAHKAN IMPORT MODEL INI
import com.tubes.model.Anggaran;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Siapkan data dari Model (contoh data statis)
        Anggaran anggaranMei = new Anggaran("Mei 2026", 5000000.0, 3200000.0);

        // 2. Kirim data ke tampilan dengan nama alias "dataAnggaran"
        request.setAttribute("dataAnggaran", anggaranMei);

        // 3. Arahkan ke halaman JSP
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}