package com.linus.dao;

import com.linus.dto.BoletimDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.utils.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoletimDao {

    private static final String SQL_BOLETIM_COMMAND =
            "SELECT m.nome AS materia, " +
                    "coalesce(CAST(n.n1 AS VARCHAR), '-') AS n1, " +
                    "coalesce(CAST(n.n2 AS VARCHAR), '-') AS n2, " +
                    "coalesce(CAST(ROUND((n.n1 + n.n2) / 2, 2) AS VARCHAR), '-') AS media, " +
                    "n.observacao " +
                    "FROM nota n " +
                    "JOIN professor p ON n.id_professor = p.id " +
                    "JOIN materia m ON p.id_materia = m.id " +
                    "WHERE n.id_aluno = ?";

    public static List<BoletimDto> findBoletimById(long id) throws SQLException, ConnectionException {
        List<BoletimDto> boletim = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection con = ConnectionManager.connect()) {

            ps = con.prepareStatement(SQL_BOLETIM_COMMAND);

            ps.setLong(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                boletim.add(new BoletimDto(rs));
            }

            return boletim;
        } finally {
            DaoUtil.closeResources(ps, rs);
        }
    }
}
