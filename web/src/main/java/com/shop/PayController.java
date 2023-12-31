package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/shop/paySuccess.do")
public class PayController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 클라이언트에서 온 데이터를 받습니다.
        String productName = req.getParameter("productName");
        String buyerId = req.getParameter("buyer_buyId");
        String productId = req.getParameter("productId");
        String amount = req.getParameter("amount");
        String status = req.getParameter("status");
        String paymentMethod = req.getParameter("paymentMethod");
        long paymentUnixTime = Long.parseLong(req.getParameter("paymentTime"));
        Date pay_date = new Date(paymentUnixTime * 1000);
        String orderId = req.getParameter("orderId");


        //결제 일자를 업데이트
        OrderDAO dao = new OrderDAO();
        dao.updatePaydate(orderId, pay_date);
        dao.updateStatus(orderId, "2");

        req.getRequestDispatcher("../shop/paySuccess.jsp").forward(req, resp);
    }
}