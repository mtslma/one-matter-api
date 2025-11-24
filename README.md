# One Matter API - Backend

Este repositório contém o **Backend da API REST** para o projeto **One Matter**, desenvolvido em **Spring Boot 3** com **Java**. A API é o core do sistema, responsável pela autenticação, gerenciamento de perfis, vagas, candidaturas, testes e toda a lógica de negócio do recrutamento ético e tecnológico.

---

## 🎯 Ecossistema Skill Station: Recrutamento Ético e Tecnológico

A API Java é a espinha dorsal do ecossistema, integrando o aplicativo mobile (Frontend) e a estação de testes física (IoT).

-   **Candidatura Cega (Mobile / API Java):** A API gerencia o registro de novos **Candidatos** (`USER`), realizando a validação de CPF e E-mail, e armazena as **Skills** associadas ao perfil.
-   **Gestão por Recrutadores (`ADMIN`):** A API oferece endpoints protegidos (via JWT) para que recrutadores gerenciem **Empresas**, **Vagas**, **Questões** e **Testes**.
-   **Fluxo de Testes:** A API expõe endpoints específicos (`/testes/candidatura/{id}/questoes`) para a estação IoT buscar o conteúdo da prova e um endpoint (`/testes/submit-score`) para receber a nota final via MQTT, atualizando o status da candidatura no banco de dados.

O objetivo é garantir a avaliação puramente baseada em habilidades, com um fluxo de trabalho seguro, transparente e auditável.

---

## 🔗 Informações de Acesso e Links

| Descrição                            | Link / Valor                                          |
| :----------------------------------- | :---------------------------------------------------- |
| **Repositório da API (Este)**        | https://github.com/mtslma/one-matter-api.git          |
| **Repositório Mobile (Frontend)**    | https://github.com/onematterfiap/gs-onematter-mobile  |
| **URL Base da API (Padrão Local)**   | `http://localhost:8080/api`                           |
| **URL Base da API (Deploy Azure)**   | `http://68.211.72.156:8080/api`                       |
| **Swagger / OpenAPI (Local)**        | `http://localhost:8080/api/swagger-ui/index.html`     |
| **Swagger / OpenAPI (Deploy Azure)** | `http://68.211.72.156:8080/api/swagger-ui/index.html` |
| **Credencial Admin**                 | `admin@onematter.com` / `senhaSegura123`              |
| **Credencial Candidato (USER)**      | `candidato@onematter.com` / `senhaSegura123`          |

---

## ⚙️ Tecnologias e Arquitetura

-   **Linguagem & Framework:** Java 17, Spring Boot 3.
-   **Banco de Dados:** Oracle (JDBC Driver `ojdbc11`).
-   **Persistência:** Spring Data JPA.
-   **Segurança:** Spring Security (com autenticação stateless via JWT).
-   **Validação:** Jakarta Validation.

### Estrutura de Autenticação e Autorização

-   **Perfis (`UsuarioRole`):** `ADMIN` (Recrutadores/Gerentes) e `USER` (Candidatos).
-   **Filtros JWT:** O `JwtAuthFilter` intercepta requisições e valida o token, definindo o usuário no contexto de segurança.
-   **Segurança:** Utiliza anotações `@PreAuthorize` e `requestMatchers` para proteger as rotas, garantindo que o gerenciamento de recursos (Vagas, Empresas, Questões) seja exclusivo para `ADMIN`.

---

## 💾 Setup e Execução Local

### Pré-requisitos

-   **Java Development Kit (JDK) 17**.
-   **Maven** (ou usar o wrapper `mvnw` incluído).

### 1. Configuração do Banco de Dados

A API está configurada para usar um banco de dados **Oracle**. A configuração no arquivo `src/main/resources/application.yml` já está definida para as credenciais de teste com massa de dados provisionada:

```yaml
  datasource:
  url: jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
  username: rm559728
  password: 250306
```

### 2. Compilar e Rodar o Projeto

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/mtslma/one-matter-api.git
    cd one-matter-api
    ```

2.  **Construa e Inicie a aplicação:**
    Use o Maven Wrapper:

    ```bash
    # Limpa e instala dependências
    ./mvnw clean install

    # Inicia a aplicação Spring Boot (padrão porta 8080)
    ./mvnw spring-boot:run
    ```

---

## ☁️ Deploy no Azure

O projeto está deployado em uma instância do **Azure** e acessível publicamente na porta `8080`.

-   **URL Base da API em Produção:** `http://68.211.72.156:8080/api`
-   **Como o Deploy é Realizado:** O deploy foi feito utilizando um contêiner **Docker** da aplicação Spring Boot. O contêiner é exposto na porta `8080` e mapeado para o IP público da máquina virtual ou serviço de contêiner do Azure.

Para replicar o deploy:

1.  **Gere o JAR Executável:** Utilize `./mvnw clean install` para gerar o arquivo `.jar` na pasta `target/`.
2.  **Crie a Imagem Docker:** Crie um `Dockerfile` que utilize uma imagem base do Java (e.g., `openjdk:17-jdk-slim`) e copie o JAR para a imagem, definindo o ponto de entrada.
3.  **Envie para o Azure:** Envie a imagem para um **Azure Container Registry (ACR)** e utilize um serviço como **Azure App Service for Containers** ou **Azure Container Instances (ACI)**, ou uma **Máquina Virtual (VM)** com Docker instalado, para rodar a aplicação.

---

## 🖥️ Endpoints da API (Resumo)

A API é modularizada por controllers, com proteção de rota baseada em `ADMIN` ou `USER`.

### Endpoints de Autenticação e Perfil (Público/Autenticado)

| Rota             | Método | Descrição                                                                         | Permissão   |
| :--------------- | :----- | :-------------------------------------------------------------------------------- | :---------- |
| `/auth/login`    | POST   | Autentica e gera **JWT Token** e Refresh Token.                                   | Público     |
| `/auth/register` | POST   | Cadastra um novo `USER` (Candidato).                                              | Público     |
| `/usuarios/me`   | GET    | Busca o perfil completo do usuário logado.                                        | Autenticado |
| `/usuarios/me`   | PUT    | Atualiza dados básicos (`nome`, `genero`, `telefone`, `skills`) do perfil logado. | Autenticado |

### Endpoints de Candidato (`/candidato/me`)

| Rota                              | Método | Descrição                                  |
| :-------------------------------- | :----- | :----------------------------------------- |
| `/vagas/{idVaga}/candidatar`      | POST   | Realiza a candidatura a uma vaga.          |
| `/candidato/me/candidaturas`      | GET    | Lista as candidaturas ativas do usuário.   |
| `/candidato/me/candidaturas/{id}` | DELETE | **Cancela** uma candidatura (soft delete). |

### Endpoints do Fluxo de Teste (IoT/Skills Station)

| Rota                                | Método | Descrição                                                                                                                              | Permissão   |
| :---------------------------------- | :----- | :------------------------------------------------------------------------------------------------------------------------------------- | :---------- |
| `/testes/candidatura/{id}/questoes` | GET    | Busca questões do Teste e **registra o status `EM_ANDAMENTO`** na candidatura (via `SP_REGISTRAR_INICIO`).                             | Autenticado |
| `/testes/submit-score`              | POST   | Recebe a nota (`score`) e **finaliza a prova** (via `SP_FINALIZAR_PROVA`), atualizando o status da candidatura para `TESTE_SUBMETIDO`. | Autenticado |

### Endpoints de Gerenciamento (`ADMIN` Role)

| Rota                    | Recurso            | Métodos Liberados                          |
| :---------------------- | :----------------- | :----------------------------------------- |
| `/vagas`                | Vagas              | POST, PUT, DELETE (GET é público)          |
| `/empresas`             | Empresas           | GET, POST, PUT, DELETE                     |
| `/recrutadores`         | Recrutadores       | GET, POST, PUT, DELETE                     |
| `/skills`               | Skills             | POST, PUT, DELETE (GET é público)          |
| `/skills/associar-vaga` | Skills             | POST (Associa skill a uma vaga)            |
| `/questoes`             | Banco de Questões  | GET, POST, PUT, DELETE                     |
| `/admin/users`          | Gestão de Usuários | GET, POST (Cria `ADMIN`/`USER` gerenciado) |
