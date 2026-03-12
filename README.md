# Projeto Linus

Este repositório apresenta um sistema Java web organizado com padrões e abstrações inteligentes que facilitam manutenção e evolução.

## Estrutura de Diretorios

O layout do projeto foi pensado para separar responsabilidades e tornar o fluxo de dados claro e consistente:

```
src/main/java/com/linus/
  dao/          # Acesso transparente a banco via DAOs genéricos e específicos
  dto/          # Objetos de Transferência de Dados que isolam modelo de visualização
  exception/    # Classes de exceção customizadas para camadas, com subpacotes para DAO e RequestParam
  infra/        # Infraestrutura, como gerenciador de conexões
  model/        # Entidades, enums e servlets que representam o domínio
  servlet/      # Controladores HTTP organizados por perfil (admin, aluno, professor, autenticação, etc.)
  utils/        # Utilitários e wrappers reutilizáveis (DAO, Email, validação)
  validation/   # Validators compostos para campos (CPF, e-mail, matrícula, etc.)
```

A hierarquia permite encontrar rápido qualquer componente e reforça separação entre camadas de persistência, negócio e serviços web.

## Destaques das Abstrações

### Mecanismo de DAO

O pacote `dao` é a coluna vertebral da integração com a camada de dados. Ele inclui:

- `GenericDaoInterface` e implementações genéricas que reduzem código repetido
- DAOs especializados (AlunoDao, ProfessorDao, etc.) que herdam a lógica comum e adicionam operações customizadas
- `DaoUtil` como um wrapper que simplifica transação e mapeamento de resultados

Essa abstração permite trocar o banco ou testar em memória sem mudar as camadas superiores.

### Validadores Inteligentes

No pacote `validation` estão implementações focadas em garantir dados limpos antes de alcançarem o negócio:

- Validadores para CPF, e-mail, matrícula, nome e senha, todos compostos por uma interface comum
- `ParamValidator` e `UsuarioValidator` que combinam regras para entradas HTTP

Estes componentes são montados com o padrão Strategy, permitindo inserir novas regras rapidamente e reutilizar lógica em diversos formulários.

### Wrappers e Utilitarios

`utils` traz classes de suporte que reduzem boilerplate:

- `EmailUtil` encapsula a configuração e envio de mensagens
- `ValidationUtil` provê métodos de ajuda para disparar os validadores e coletar erros
- Wrappers de DAO que uniformizam o tratamento de exceção e gerenciamento de conexão

### RequestResponse e Servlets

Os servlets se comunicam com os clientes via objetos DTO e um padrao `RequestResponse` (não mostrado no diretório mas presumido) que pacotes:

- Mensagens de sucesso/erro
- Dados retornados ao front-end

Isso reforça a separação entre a lógica de negócio e a camada de apresentação, facilitando testes automatizados e evolução de interfaces.

## Boas Praticas e Beneficios

1. **Coesão**: cada pacote tem uma responsabilidade clara.
2. **Reuso**: abstracoes genericas reduzem duplicatas de codigo e tornam extenso simples.
3. **Testabilidade**: DAOs e validadores são facilmente mockados para testes unitários.
4. **Manutenção**: alterar um validador ou regra de negócio exige modificação em um lugar apenas, não em vários servlets.

> Este projeto não é apenas um conjunto de classes Java; é um exemplo de como estruturar aplicações web com clareza, abstração e robustez. Aproveite as camadas bem definidas e as interfaces amigáveis para crescer, testar e manter o sistema.

---
