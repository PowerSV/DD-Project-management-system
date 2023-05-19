package com.digdes.school.dao;

import com.digdes.school.dto.member.SearchMemberFilter;
import com.digdes.school.models.Member;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.repos.AbstractMemberRepository;

import java.sql.*;
import java.util.*;

public class MemberDaoJdbc implements AbstractMemberRepository<Member> {

    private final String dbUrl;

    private final String dbUser;

    private final String dbPassword;

    public MemberDaoJdbc(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public Member create(Member member) {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into member (email, firstname, lastname, middlename, position, status, account) values(?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getLastName());
            ps.setString(4, member.getMiddleName());
            ps.setString(5, member.getPosition());
            ps.setString(6, MemberStatus.ACTIVE.toString());

            String email = member.getEmail();
            String account = email == null || email.isBlank() ? null : email.substring(0, email.indexOf("@"));

            ps.setString(7, account);

            int i = ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                member = tableEntityToObject(generatedKeys);
            }
            ps.close();
            generatedKeys.close();

            return member;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Member update(Member member) {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "update member set (email, firstname, lastname, middlename, position, status, account) = (?,?,?,?,?,?,?) where id=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getFirstName());
            ps.setString(3, member.getLastName());
            ps.setString(4, member.getMiddleName());
            ps.setString(5, member.getPosition());
            if (member.getStatus() != null) {
                ps.setString(6, member.getStatus().toString());
            } else {
                ps.setString(6, MemberStatus.ACTIVE.toString());
            }
            ps.setString(7, member.getAccount());
            ps.setLong(8, member.getId());

            int i = ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                member = tableEntityToObject(generatedKeys);
            }
            ps.close();
            generatedKeys.close();

            return member;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Member tableEntityToObject(ResultSet generatedKeys) throws SQLException {
        return new Member(
                generatedKeys.getLong(1),
                generatedKeys.getString(5),
                generatedKeys.getString(4),
                generatedKeys.getString(6),
                generatedKeys.getString(7),
                generatedKeys.getString(2),
                generatedKeys.getString(3),
                MemberStatus.valueOf(generatedKeys.getString(8))

        );
    }

    @Override
    public Member deleteById(Long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from member where id=?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, id);

            Member deletedMember = new Member();

            int rowAffected = ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new Member();
            }
            ps.close();
            generatedKeys.close();

            return deletedMember;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Member> getById(Long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from member where id=?",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return Optional.of(tableEntityToObject(resultSet));
            }
            ps.close();
            resultSet.close();

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> getAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from member",
                    Statement.RETURN_GENERATED_KEYS);

            ResultSet resultSet = ps.executeQuery();
            List<Member> members = new ArrayList<>();

            while (resultSet.next()) {
                members.add(tableEntityToObject(resultSet));
            }
            ps.close();
            resultSet.close();

            return members;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> searchMembers(SearchMemberFilter filter) {
        try (Connection connection = getConnection()) {
            String seqrchQuery = "SELECT m.id, m.lastname, m.firstname, m.middleName, m.position, m.account, m.email, m.status " +
                    "FROM team_membership tm left join member m ON tm.member_id = m.id " +
                    "where 1=1";

            Map<Integer, Object> parameterMap = new HashMap<>();
            int paramIndex = 1;
            if (filter.getTeamId() != null) {
                seqrchQuery += " and tm.team_id = ?";
                parameterMap.put(paramIndex++, filter.getTeamId());
            }
            if (filter.getEmail() != null) {
                seqrchQuery += " and m.email = ?";
                parameterMap.put(paramIndex++, filter.getEmail());
            }
            if (filter.getFirstName() != null) {
                seqrchQuery += " and m.firstname = ?";
                parameterMap.put(paramIndex++, filter.getFirstName());
            }

            PreparedStatement ps = connection.prepareStatement(seqrchQuery, Statement.RETURN_GENERATED_KEYS);
            for (Map.Entry<Integer, Object> entry : parameterMap.entrySet()) {
                ps.setObject(entry.getKey(), entry.getValue());
            }

            ResultSet resultSet = ps.executeQuery();
            List<Member> members = new ArrayList<>();
            while (resultSet.next()) {
                members.add(tableEntityToObject(resultSet));
            }
            ps.close();
            resultSet.close();

            return members;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
