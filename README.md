# Projeto Linus

Este documento serve como um guia de arquitetura para o projeto **Linus**, uma aplicação Java baseada em servlets, JSP e Maven. O foco está em explicar as camadas, abstrações e padrões adotados, com destaque especial para os *wrappers* de requisição/resposta, validadores e outras boas práticas.

---

## 🏗️ Visão Geral da Arquitetura

A aplicação segue uma estrutura em camadas organizada sob `src/main/java/com/linus`:

1. **DAO (Data Access Object)**
   - Implementações responsáveis pela persistência (`*Dao.java`).
   - Interface genérica `GenericDaoInterface` define operações CRUD.
   - Acesso aos dados via `AcessoDao`, `AlunoDao`, etc.

2. **DTO (Data Transfer Object)**
   - Objetos simples para troca de dados entre camadas.
   - Ex.: `AlunoDto`, `ProfessorPerfilDto`, `BoletimDto`.

3. **Model / Servlets**
   - Classes de modelo (`model.dao`) representam entidades.
   - Servlets tratam requisições HTTP e delegam lógica.
   - Organização em pacotes (`servlet/admin`, `servlet/aluno`, etc.)

4. **Validação e Utilitários**
   - Diversos *validators* (`CpfValidator`, `EmailValidator`, etc.)
   - Classe `ValidationUtil` e `DaoUtil` com métodos de apoio.
   - `EmailUtil` para envio e-mail.

5. **Web (JSP + recursos estáticos)**
   - JSPs em `webapp/` para cada interação do usuário.
   - CSS/JS sob `assets` no diretório raiz ou `WEB-INF`.
   - `web.xml` configura mapeamentos de servlets e filtros.

---

## 🔍 Abstrações Chaves

### 🛡️ Wrapper de Request/Response

Uma importante abstração no projeto é o *wrapper* que encapsula o `HttpServletRequest` e `HttpServletResponse`. Ele permite:

- Recuperar parâmetros com tratamento de valores nulos.
- Encapsular lógica de leitura e escrita, reduzindo duplicação nos servlets.
- Possibilitar a criação de métodos utilitários relacionados à sessão ou cookies.

Isso melhora a legibilidade e facilita testes unitários, pois o wrapper pode ser instanciado isoladamente.

### ✅ Validators (Validação de Entrada)

A pasta `validation` contém validadores específicos:

- Cada validador implementa uma interface comum (`Validator`).
- Exemplos: `CpfValidator`, `NomeValidator`, `MatriculaValidator`.
- A classe `ValidationUtil` agrega validações e dispara exceções customizadas em caso de erro.

Essas abstrações promovem:

- **Reuso** de lógica de validação entre servlets e serviços.
- **Manutenção** simplificada ao centralizar regras.
- **Estratégia de validação** consistente com `ParamValidator` para parâmetros genéricos.

### 📦 DTOs e DAO

- **DTOs** desacoplam a camada de apresentação da camada de persistência.
- **DAOs** seguem o padrão DAO clássico, mantendo SQL/ORM separados da lógica de negócio.

A combinação assegura que alterações no banco de dados ou na interface não comprometem outras camadas.

---

## 📁 Estrutura de Pacotes (Resumo)

```
com.linus
├─ dao              # Persistência
├─ dto              # Transferência de dados
├─ exception        # Exceções personalizadas
├─ infra            # Infraestrutura (conexão com BD, etc.)
├─ model            # Entidades e enuns
├─ servlet          # Controllers HTTP
├─ utils            # Ferramentas gerais
└─ validation       # Lógica de validação
```

## 📚 Referências e Recursos

- O projeto usa Maven para dependências.
- Consulte `pom.xml` para versões e plug-ins.
- Veja `web.xml` para configuração de filtros, listeners e mapeamentos.

---

## 📘 Exemplos de Classes Bem Escritas

Abaixo estão alguns modelos que exemplificam boas abstrações já presentes no código

### 🔹 `RequestReponse` (Wrapper HTTP)

```java
public class RequestReponse {
    private HttpServletResponse response;
    private HttpServletRequest request;

    public RequestReponse(HttpServletRequest request, HttpServletResponse response){
        this.response = response;
        this.request = request;
    }
    public String getRequestParameter(String name) { … }
    public void forwardTo(String address) throws ServletException, IOException { … }
    public void redirectTo(String url) throws IOException { … }
    public Map<String, String> getAllRequestParameters() { … }
    // … getters, add/remove/update attributes, verificação de existência, etc.
}
```
> Simplifica acesso a parâmetros, atributos e dispatch/redirect, mantendo a lógica de teste isolada.

### 🔹 Validação – `CpfValidator` (e similares)

```java
public class CpfValidator implements ParamValidator {
    private static final CPFValidator CPF_VALIDATOR = new CPFValidator();

    @Override
    public void validate(String value) throws ParamException {
        if (isEmptyString(value)) throw new EmptyParamException("cpf");
        if (isOutOfBoundString(value)) throw new OutOfBoundsParamException("cpf");

        try {
            CPF_VALIDATOR.assertValid(value);
        } catch (Exception e) {
            throw new OutOfPatternCpfException();
        }
    }
}
```
> Cada validador trata `null`, tamanho e formato; são fáceis de testar e estender.

### 🔹 Utilitário de validação `ValidationUtil`

```java
public class ValidationUtil {
    private static String REGEX_EMAIL = "…";
    private static String REGEX_PASSWORD = "…";

    public static boolean isEmptyString(String s) { … }
    public static boolean isOutOfBoundString(String s){ … }
    public static boolean isNotValidEmail(String s) { … }
    public static boolean isNotValidPassword(String s) { … }
    public static boolean isNotParseableToLong(String s) { … }
}
```
> Centraliza regras comuns, reduz duplicação e favorece manutenção.

### 🔹 Interface Genérica DAO

```java
public interface GenericDaoInterface<Model, ModelDto> {
    Model save(ModelDto dto) throws SQLException, ConnectionException;
    Model findById(ModelDto dto ) throws SQLException, ConnectionException;
    List<Model> findAll() throws SQLException, ConnectionException;
    void update(ModelDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException;
    void delete(ModelDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException;
}
```
> Padroniza as operações CRUD para todos os DAOs, mantendo tratamento de exceções claro.

### 🔹 `EmailUtil` – serviço de envio de mensagens

```java
public class EmailUtil {
    private static final String EMAIL_REMETENTE = System.getenv("SMTP_USER");
    private static final String SENHA_REMETENTE = System.getenv("APP_PASS");

    public static void enviarEmail(String email, String codigoVerificacao)
            throws MessagingException, UnsupportedEncodingException,
                   EntityNotFoundException, SQLException {
        AcessoDao acessoDAO = new AcessoDao();
        Optional<String[]> resultado = acessoDAO.findCargoByEmail(email);
        if (resultado.isEmpty()) throw new EntityNotFoundException(email);
        // …configura props, auth, constrói mensagem HTML e envia…
    }
}
```
> Mantém a lógica de configuração e template de e‑mail separada dos servlets.

---

Essas classes ilustram responsabilidades bem definidas, documentação clara e reutilização eficaz. Elas funcionam como “boas práticas vivas” que a equipe pode consultar ao estender o sistema.

