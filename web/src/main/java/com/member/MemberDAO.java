package com.member;

import com.util.DBConnPool;
import com.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDAO extends DBConnPool {

    public int join(MemberDTO dto) {
        int result = 0;
        try {
            String query = " INSERT INTO custom (user_id, password, email) VALUES (?, ?, ?)";
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getUser_id());
            psmt.setString(2, dto.getPassword());
            psmt.setString(3, dto.getEmail());

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error("join 중 에러 발생", e);
        }
        return result;
    }

    public int login(String user_id, String hashedPw) {
        try {
            String query = "SELECT id, password, quit FROM custom WHERE user_id = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, user_id);
            rs = psmt.executeQuery();
            int id = 0;
            String DBHashedPw = "";
            boolean quit = true;
            if (rs.next()) {
                id = rs.getInt("id");
                DBHashedPw = rs.getString("password");
                quit = rs.getBoolean("quit");
            }
            if (id == 0)
                return 0; // 회원 없음
            if (quit)
                return -2; // 탈퇴한 회원
            if (DBHashedPw.equals(hashedPw)) {
                return id; //내부id를 반환
            } else return -1; //비밀번호 틀림
        } catch (SQLException e) {
            Logger.error("Login 중 에러 발생", e);
        }
        return -3; // 처리 실패
    }

    public boolean isIdAvailable(String id) {

        try {
            String query = " SELECT COUNT(*) FROM custom WHERE user_id = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, id);
            rs = psmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // count가 0이면 아이디 사용 가능
            }
        } catch (SQLException e) {
            Logger.error("isIdAvailable 중 에러 발생", e);
        }
        return false; // 예외가 발생하는 경우 false 반환
    }

    public boolean updateUserInformation(String userId, String newPassword, String newEmail) {
        Map<String, String> updates = new HashMap<>();
        if (newPassword != null && !newPassword.isEmpty()) {
            updates.put("password", newPassword);
        } else if (newEmail != null && !newEmail.isEmpty()) {
            updates.put("email", newEmail);
        } else if (updates.isEmpty()) {
            return false;
        }
        try {
            StringBuilder query = new StringBuilder("UPDATE custom SET ");

            int paramIndex = 1;
            List<String> params = new ArrayList<>();

            for (String key : updates.keySet()) {
                if (paramIndex > 1) {
                    query.append(", ");
                }
                query.append(key).append(" = ?");
                params.add(updates.get(key));
                paramIndex++;
            }
            query.append(" WHERE user_id = ?");
            psmt = con.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                psmt.setString(i + 1, params.get(i));
            }
            psmt.setString(paramIndex, userId);
            return psmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("updateUserInformation 중 에러 발생", e);
            return false;
        }
    }
}
