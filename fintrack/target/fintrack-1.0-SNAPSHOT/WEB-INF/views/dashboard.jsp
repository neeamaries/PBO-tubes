<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fintrack.model.Transaction" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Fintrack</title>
    <style>
        table { width: 50%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .Pemasukan { color: green; font-weight: bold; }
        .Pengeluaran { color: red; font-weight: bold; }
    </style>
</head>
<body>

    <h2>FinTrack - Manajemen Keuangan</h2>
    <p>Berikut adalah catatan keuangan kamu bulan ini (Simulasi MVC):</p>

    <table>
        <thead>
            <tr>
                <th>Nama Transaksi</th>
                <th>Nominal</th>
                <th>Tipe</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Mengambil list dataSaku yang dikirim dari Servlet Controller
                List<Transaction> list = (List<Transaction>) request.getAttribute("dataSaku");
                if (list != null) {
                    for (Transaction t : list) {
            %>
            <tr>
                <td><%= t.getNama() %></td>
                <td>Rp <%= String.format("%,.0f", t.getNominal()) %></td>
                <td class="<%= t.getTipe() %>"><%= t.getTipe() %></td>
            </tr>
            <% 
                    }
                } 
            %>
        </tbody>
    </table>

</body>
</html>