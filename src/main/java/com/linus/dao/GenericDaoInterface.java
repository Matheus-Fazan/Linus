package com.linus.dao;

import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface genérica para operações CRUD em banco de dados.
 *
 * @param <Model>    tipo da entidade de domínio retornada pelas operações
 * @param <ModelDto> tipo do DTO utilizado como entrada nas operações
 */
public interface GenericDaoInterface<Model, ModelDto> {

    /**
     * Persiste uma nova entidade no banco de dados.
     *
     * @param dto dados da entidade a ser salva
     * @return entidade persistida com o campo de identificador preenchido
     * @throws SQLException        se houver erro na operação SQL
     * @throws ConnectionException se houver erro ao estabelecer conexão com o banco de dados
     */
    Model save(ModelDto dto) throws SQLException, ConnectionException;

    /**
     * Busca uma entidade por seu identificador.
     *
     * @param dto DTO contendo o identificador único da entidade
     * @return entidade correspondente ou {@code null} se não encontrada
     * @throws SQLException        se houver erro na operação SQL
     * @throws ConnectionException se houver erro ao estabelecer conexão com o banco de dados
     */
    Model findById(ModelDto dto ) throws SQLException, ConnectionException;

    /**
     * Retorna todas as entidades cadastradas.
     *
     * @return lista contendo todas as entidades ou lista vazia se não houver registros
     * @throws SQLException        se houver erro na operação SQL
     * @throws ConnectionException se houver erro ao estabelecer conexão com o banco de dados
     */
    List<Model> findAll() throws SQLException, ConnectionException;

    /**
     * Atualiza uma entidade existente no banco de dados.
     *
     * @param dto dados atualizados da entidade, incluindo seu identificador
     * @throws SQLException               se houver erro na operação SQL
     * @throws ConnectionException        se houver erro ao estabelecer conexão com o banco de dados
     * @throws NoRegistersAlteredException se nenhum registro foi alterado (identificador inexistente)
     */
    void update(ModelDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException;

    /**
     * Remove uma entidade do banco de dados.
     *
     * @param dto DTO contendo o identificador da entidade a ser removida
     * @throws SQLException               se houver erro na operação SQL
     * @throws ConnectionException        se houver erro ao estabelecer conexão com o banco de dados
     * @throws NoRegistersAlteredException se nenhum registro foi removido (identificador inexistente)
     */
    void delete(ModelDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException;
}