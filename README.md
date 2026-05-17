<div align="center">

# 🍕 Pizzly Back-End
API REST para o sistema de pizzaria com delivery Pizzly.

O back-end do Pizzly é responsável pela lógica de negócio da aplicação, gerenciamento de usuários, pedidos e comunicação com o front-end.

</div>

---

## 🎯 Responsabilidades

- Gerenciamento de usuários do sistema
- Cadastro e gerenciamento de pedidos
- Controle de pizzas e cardápio
- Exposição de endpoints REST
- Persistência de dados com JPA
- Integração com banco de dados
- Comunicação entre front-end e back-end

---

## 🗂️ Entidades do Sistema

| Entidade | Descrição |
|----------|------------|
| `Usuario` | Cliente cadastrado no sistema |
| `Pedido` | Pedido realizado pelo cliente |
| `Pizza` | Pizza disponível no cardápio |
| `Categoria` | Categoria das pizzas |
| `Endereco` | Endereço de entrega do cliente |
| `Pagamento` | Dados de pagamento do pedido |
| `Administrador` | Usuário responsável pelo painel administrativo |

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

## 🛣️ Rotas da API

### 👤 Usuários

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/usuarios` | Lista todos os usuários |
| POST | `/usuarios` | Cadastra um usuário |
| PUT | `/usuarios/{id}` | Atualiza um usuário |
| DELETE | `/usuarios/{id}` | Remove um usuário |

---

### 🍕 Pizzas

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/pizzas` | Lista pizzas |
| GET | `/pizzas/{id}` | Busca pizza por ID |
| POST | `/pizzas` | Cadastra pizza |
| PUT | `/pizzas/{id}` | Atualiza pizza |
| DELETE | `/pizzas/{id}` | Remove pizza |

---

### 📦 Pedidos

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/pedidos` | Lista pedidos |
| GET | `/pedidos/{id}` | Busca pedido |
| POST | `/pedidos` | Cria pedido |
| PUT | `/pedidos/{id}` | Atualiza pedido |
| DELETE | `/pedidos/{id}` | Cancela pedido |

---

### 💳 Pagamentos

| Método | Rota | Descrição |
|--------|------|------------|
| POST | `/pagamentos` | Realiza pagamento |
| GET | `/pagamentos/{id}` | Consulta pagamento |

---

### 📍 Endereços

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/enderecos` | Lista endereços |
| POST | `/enderecos` | Cadastra endereço |

---

### 👨‍💼 Administração

| Método | Rota | Descrição |
|--------|------|------------|
| GET | `/admin/pedidos` | Lista pedidos |
| GET | `/admin/usuarios` | Lista usuários |
| PUT | `/admin/pedidos/{id}` | Atualiza status do pedido |

---

## 🏗️ Estrutura do Projeto

```text
📦 prg04misaelcarvalhodutra-back-end
└── 📂 src
    └── 📂 main
        ├── 📂 java
        │   └── 📂 br
        │       └── 📂 com
        │           └── 📂 ifba
        │               └── 📂 prg04pizzly
        │                   ├── 📂 usuarios
        │                   │   ├── 📂 controller
        │                   │   │   └── 📄 UsuarioController.java
        │                   │   │
        │                   │   ├── 📂 model
        │                   │   │   └── 📄 Usuario.java
        │                   │   │
        │                   │   ├── 📂 repository
        │                   │   │   └── 📄 UsuarioRepository.java
        │                   │   │
        │                   │   └── 📂 service
        │                   │       └── 📄 UsuarioService.java
        │                   │
        │                   └── 📄 Prg04PizzlyApplication.java
        │
        └── 📂 resources
            └── 📄 application.properties
```

## 🛠️ Tecnologias

<p>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  
  <img src="https://img.shields.io/badge/Spring_Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  
  <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  
  <img src="https://img.shields.io/badge/H2_Database-0078D4?style=for-the-badge&logo=databricks&logoColor=white" />
  
  <img src="https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=java&logoColor=white" />
  
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white" />
  
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white" />
</p>

---

## 🧪 Testes da API

Os endpoints da aplicação foram testados utilizando:

📮 Postman

Testes realizados:

✅ GET
✅ POST
✅ PUT
✅ DELETE

---

## 🔗 Repositório Front-End

🍕 Front-End:

https://github.com/MisaelCarvalhoDutra/prg04misaelcarvalhodutra-front-end