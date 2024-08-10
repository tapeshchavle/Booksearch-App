/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
public class BookCheck extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookCheck</title>");
            out.println("</head>");
            out.println("<body>");
            String bookname = request.getParameter("bookname");
            RequestDispatcher rd1 = request.getRequestDispatcher("index.html");
            int count = 0;
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-S1QPSLM6:1521/xe", "tapesh", "abc");
                System.out.println("Connection is successfull:");
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("select * from books");
                List<Book> list = new ArrayList<>();
                if (bookname == "") {
                    out.println("<p style='color:red'>Please Enter Book Name<p>");
                    rd1.include(request, response);
                } else {
                    while (rs.next()) {
                        String book = rs.getString(1);
                        int price = rs.getInt(2);
                        Book b = new Book();
                        if ((book.contains(bookname)) || (book.toLowerCase().contains(bookname))) {
                            b.setBookname(book);
                            b.setPrice(price);
                            list.add(b);
                            count++;
                        }
                    }

                    if (count != 0) {
                        RequestDispatcher rd = request.getRequestDispatcher("ShowResult");
                        request.setAttribute("books", list);

                        rd.forward(request, response);
                    } else {

                        out.println("<p style='color:red'>This Book is Not present in Database. Try with Another Book<p>");
                        rd1.include(request, response);

                    }

                }

            } catch (SQLException e) {
                System.out.println("error");

            } finally {
                try {
                    if (con != null) {
                        con.close();
                        System.out.println("disconnected");
                    }
                } catch (SQLException e) {
                    System.out.println("connection in closing the connection:" + e.getMessage());
                }
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
