<div align="center">

# 🍕 Pizzly — Back-end
API REST para o sistema de pizzaria com delivery Pizzly.

O back-end do Pizzly é responsável pela lógica de negócio da aplicação, incluindo gerenciamento de usuários, pedidos, pizzas, pagamentos e comunicação com o front-end.

</div>

---

## 🎯 Responsabilidades

- Autenticação e gerenciamento de usuários
- CRUD de pedidos, pizzas e categorias
- Gerenciamento de endereços de entrega
- Controle de pagamentos
- Comunicação entre front-end e back-end
- Persistência de dados com JPA
- Exposição de endpoints REST
- Mapeamento entre DTOs e entidades (ModelMapper)
- Tratamento centralizado de exceções (GlobalExceptionHandler)

---

## 🗂️ Entidades do Sistema

| Entidade | Descrição |
|----------|------------|
| `br.com.ifba.prg04pizzly.usuarios.entity.Usuario` | Cliente cadastrado no sistema |
| `br.com.ifba.prg04pizzly.pedidos.entity.Pedido` | Pedido realizado pelo cliente |
| `br.com.ifba.prg04pizzly.pizzas.entity.Pizza` | Pizza disponível no cardápio |
| `br.com.ifba.prg04pizzly.categorias.entity.Categoria` | Categoria das pizzas |
| `br.com.ifba.prg04pizzly.enderecos.entity.Endereco` | Endereço de entrega do cliente |
| `br.com.ifba.prg04pizzly.pagamentos.entity.Pagamento` | Dados de pagamento do pedido |
| `br.com.ifba.prg04pizzly.admin.entity.Administrador` | Usuário responsável pelo painel administrativo |

---

## 🔗 Relacionamentos

| Relacionamento | Cardinalidade |
|---------------|---------------|
| `Usuario → Pedido` | 1 para N |
| `Pedido → Pizza` | N para N |
| `Pizza → Categoria` | N para 1 |
| `Usuario → Endereco` | 1 para N |
| `Pedido → Pagamento` | 1 para 1 |

---

## 🛣️ Rotas previstas

### 🔐 Auth

| Método | Rota | Descrição |
|--------|------|------------|
| POST | `/auth/login` | Login do usuário |
| POST | `/auth/cadastro` | Cadastro de novo usuário |

---

### 👤 Usuários

| Método | Rota             | Descrição                                      |
| ------ | ---------------- | ---------------------------------------------- |
| GET    | `/usuarios`      | Listar usuários                                |
| GET    | `/usuarios/{id}` | Buscar usuário por ID                          |
| POST   | `/usuarios`      | Cadastrar usuário (recebe `UsuarioRequestDTO`) |
| PUT    | `/usuarios/{id}` | Atualizar usuário (recebe `UsuarioRequestDTO`) |
| DELETE | `/usuarios/{id}` | Deletar usuário                                |


---

### 🍕 Pizzas

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/pizzas` | Listar pizzas |
| GET | `/pizzas/{id}` | Buscar pizza por ID |
| POST | `/pizzas` | Cadastrar pizza |
| PUT | `/pizzas/{id}` | Atualizar pizza |
| DELETE | `/pizzas/{id}` | Deletar pizza |

---

### 📦 Pedidos

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/pedidos` | Listar pedidos |
| GET | `/pedidos/{id}` | Buscar pedido por ID |
| POST | `/pedidos` | Criar pedido |
| PUT | `/pedidos/{id}` | Atualizar pedido |
| DELETE | `/pedidos/{id}` | Cancelar pedido |

---

### 💳 Pagamentos

| Método | Rota | Descrição |
|--------|------|------------|
| POST | `/pagamentos` | Realizar pagamento |
| GET | `/pagamentos/{id}` | Consultar pagamento |

---

### 📍 Endereços

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/enderecos` | Listar endereços |
| POST | `/enderecos` | Cadastrar endereço |

---

### 👨‍💼 Administração

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/admin/pedidos` | Listar pedidos |
| GET | `/admin/usuarios` | Listar usuários |
| PUT | `/admin/pedidos/{id}` | Atualizar status do pedido |

---

## 🏗️ Estrutura do Projeto

```text
📦 prg04misaelcarvalhodutra-back-end
└── 📂 src
    └── 📂 main
        ├── 📂 java
        │   └── 📂 br/com/ifba/prg04pizzly
        │       ├── 📂 usuarios
        │       │   ├── 📂 controller
        │       │   │   ├── 📄 UsuarioController.java
        │       │   │   └── 📄 UsuarioIController.java
        │       │   ├── 📂 entity
        │       │   │   └── 📄 Usuario.java
        │       │   ├── 📂 repository
        │       │   │   └── 📄 UsuarioRepository.java
        │       │   ├── 📂 service
        │       │   │   ├── 📄 UsuarioService.java
        │       │   │   └── 📄 UsuarioIService.java
        │       │   └── 📂 dto
        │       │       ├── 📄 UsuarioRequestDTO.java
        │       │       └── 📄 UsuarioResponseDTO.java
        │       └── 📂 infrastructure
        │           └── 📂 mapper
        │               └── 📄 ObjectMapperUtil.java
                    └── 📂 exceptions
│                       ├── 📄 GlobalExceptionHandler.java
│                       ├── 📄 ResourceNotFoundException.java
│                       └── 📄 BusinessException.java
        └── 📂 resources
            └── 📄 application.properties
            
```
            

            
            
## 🛠️ Tecnologias
<p> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" /> <img src="https://img.shields.io/badge/ModelMapper-6DB33F?style=for-the-badge&logoColor=white" /> <img src="https://img.shields.io/badge/H2_Database-0078D4?style=for-the-badge&logo=databricks&logoColor=white" /> <img src="https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white" /> <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white" /> </p>

## 🧪 Testes da API:

Os endpoints da aplicação foram testados utilizando:

📮 Postman


Testes realizados
✅ GET
✅ POST
✅ PUT
✅ DELETE


## 📚 Conceitos Aplicados
- API REST
- CRUD
- Spring Boot
- Spring Data JPA
- Banco de Dados H2
- Arquitetura em Camadas
- ResponseEntity
- Injeção de Dependência
- Persistência de Dados
- Endpoints RESTful
- Arquitetura em Camadas
- DTO
- Validation API
- Tratamento Global de Exceções
- ModelMapper


🔗 Repositório Front-end

🍕 Pizzly Front-end:

https://github.com/MisaelCarvalhoDutra/prg04misaelcarvalhodutra-front-end