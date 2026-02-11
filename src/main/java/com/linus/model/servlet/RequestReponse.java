package com.linus.model.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RequestReponse {

    //atributos
    private HttpServletResponse response;
    private HttpServletRequest request;

//construtor
    /**
     * Construtor que inicializa a classe com os objetos de requisição e resposta HTTP.
     * @param request Objeto {@link HttpServletRequest} contendo os dados da requisição HTTP
     * @param response Objeto {@link HttpServletResponse} para enviar respostas ao cliente
     */
    public RequestReponse(HttpServletRequest request, HttpServletResponse response){
        this.response = response;
        this.request = request;
    }
    //métodos
    /**
     * Obtém o valor de um parâmetro da requisição HTTP.
     * @param name Nome do parâmetro a ser recuperado
     * @return Valor do parâmetro como {@code String}, ou {@code null} se o parâmetro não existir
     */
    public String getRequestParameter(String name) {
        return request.getParameter(name);
    }

    /**
     * Obtém o valor de um atributo da sessão HTTP.
     * @param name Nome do atributo a ser recuperado
     * @return Valor do atributo como {@code Object}, ou {@code null} se o atributo não existir
     */
    public Object getSessionAttribute(String name) {
        return request.getSession().getAttribute(name);
    }

    /**
     * Obtém o valor de um atributo da requisição HTTP.
     * @param name Nome do atributo a ser recuperado
     * @return Valor do atributo como {@code Object}, ou {@code null} se o atributo não existir
     */
    public Object getRequestAttribute(String name) {
        return request.getAttribute(name);
    }


    /**
     * Retorna o objeto {@link HttpServletResponse} encapsulado.
     * @return Objeto de resposta HTTP original
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Retorna o objeto {@link HttpServletRequest} encapsulado.
     * @return Objeto de requisição HTTP original
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Adiciona um atributo ao escopo da requisição HTTP.
     * O atributo ficará disponível apenas durante a requisição atual.
     * @param key Nome do atributo (identificador)
     * @param obj Objeto a ser armazenado como atributo
     */
    public void addRequestAttribute(String key, Object obj){
        request.setAttribute(key, obj);
    }

    /**
     * Atualiza um atributo no escopo da requisição HTTP.
     * @param key Nome do atributo a ser atualizado
     * @param obj Novo valor do objeto
     */
    public void updateRequestAttribute(String key, Object obj){
        request.setAttribute(key, obj);
    }

    /**
     * Adiciona um atributo ao escopo da sessão HTTP.
     * O atributo ficará disponível durante toda a sessão do usuário.
     * @param key Nome do atributo (identificador)
     * @param obj Objeto a ser armazenado na sessão
     */
    public void addSessionAttribute(String key, Object obj){
        request.getSession().setAttribute(key, obj);
    }

    /**
     * Despacha a requisição para outro recurso (JSP, Servlet, HTML) mantendo a URL original.
     * @param address Caminho relativo do recurso de destino
     * @throws ServletException Se ocorrer um erro durante o despacho da requisição
     * @throws IOException Se ocorrer um erro de entrada/saída durante o forward
     */
    public void forwardTo(String address) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    /**
     * Redireciona o cliente para uma nova URL. O context path da aplicação é automaticamente adicionado.
     * @param url URL relativa de destino, sem o context path
     * @throws IOException Se ocorrer um erro durante o redirecionamento
     */
    public void redirectTo(String url) throws IOException {
        response.sendRedirect(request.getContextPath() + url);
    }

    /**
     * Remove um atributo do escopo da requisição HTTP.
     * @param key Nome do atributo a ser removido
     */
    public void removeRequestAttribute(String key){
        request.removeAttribute(key);
    }

    /**
     * Remove um atributo do escopo da sessão HTTP.
     * @param key Nome do atributo a ser removido
     */
    public void removeSessionAttribute(String key){
        request.getSession().removeAttribute(key);
    }

    /**
     * Verifica se existe um atributo específico na requisição.
     *
     * @param name Nome do atributo a ser verificado
     * @return {@code true} caso exista, {@code false} caso o contrário
     */
    public boolean hasRequestAttribute(String name){
        return request.getAttribute(name) != null;
    }

    /**
     * Verifica se existe um atributo específico na sessão da requisição.
     *
     * @param name Nome do atributo a ser verificado
     * @return {@code true} caso exista, {@code false} caso o contrário
     */
    public boolean hasSessionAttribute(String name){
        return request.getSession().getAttribute(name) != null;
    }
}