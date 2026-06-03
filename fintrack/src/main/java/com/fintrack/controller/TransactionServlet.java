package com.fintrack.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Menggunakan javax karena Tomcat 8.5
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Import class Model yang kita buat tadi
import com.fintrack.model.Transaction;

@WebServlet("/navbar")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. MEMBUAT DATA MOCK (Sederhana, tanpa DB)
        List<Transaction> listTransaksi = new ArrayList<>();
        listTransaksi.add(new Transaction("Kiriman Orang Tua", 1500000, "Pemasukan"));
        listTransaksi.add(new Transaction("Beli Kopi & Camilan", 45000, "Pengeluaran"));
        listTransaksi.add(new Transaction("Bayar Kos Bulanan", 700000, "Pengeluaran"));
        listTransaksi.add(new Transaction("Gaji Freelance Desain", 600000, "Pemasukan"));

        // 2. IKAT DATA KE REQUEST ATTRIBUTE
        // Nama "dataSaku" ini yang akan dipanggil di file JSP
        request.setAttribute("dataSaku", listTransaksi);

        // 3. OPER KE VIEW (JSP)
        request.getRequestDispatcher("/WEB-INF/views/navbar.jsp").forward(request, response);
    }
}