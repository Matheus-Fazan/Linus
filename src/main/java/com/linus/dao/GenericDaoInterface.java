package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface genérica para operações CRUD em banco de dados.
 *
 * @param <Model> tipo da entidade a ser manipulada
 */
public interface GenericDaoInterface<Model> {

    /**
     * Persiste uma nova entidade no banco de dados.
     *
     * @return o mesmo objeto de modelo com o campo de 'id' preenchido
     * @param model entidade a ser salva
     * @throws SQLException se houver erro na operação SQL
     * @throws ClassNotFoundException se o driver JDBC não for encontrado
     */
    Model save(Model model) throws SQLException, ConnectionException;

    /**
     * Busca uma entidade por seu identificador.
     *
     * @param id identificador único da entidade
     * @return entidade correspondente ao ID ou {@code null} se não encontrada
     * @throws SQLException se houver erro na operação SQL
     * @throws ClassNotFoundException se o driver JDBC não for encontrado
     */
    Model findById(long id) throws SQLException, ConnectionException;

    /**
     * Retorna todas as entidades cadastradas.
     *
     * @return lista contendo todas as entidades ou lista vazia se não houver registros
     * @throws SQLException se houver erro na operação SQL
     * @throws ClassNotFoundException se o driver JDBC não for encontrado
     */
    List<Model> findAll() throws SQLException, ConnectionException;

    /**
     * Atualiza uma entidade existente no banco de dados.
     *
     * @param model entidade a ser atualizada com id para identificação e demais atributos atualizados
     * @throws SQLException se houver erro na operação SQL
     * @throws ClassNotFoundException se o driver JDBC não for encontrado
     */
    void update(Model model) throws SQLException, ConnectionException, NoRegistersAlteredException;

    /**
     * Remove uma entidade do banco de dados.
     *
     * @param id identificador da entidade a ser removida
     * @throws SQLException se houver erro na operação SQL
     * @throws ClassNotFoundException se o driver JDBC não for encontrado
     */
    void delete(long id) throws SQLException, ConnectionException, NoRegistersAlteredException;
}