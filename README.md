# Operadora Logística (DDD em Java)

Modelagem em Domain Driven Design para o sistema de uma operadora logística que cobre gestão de fretes, rastreamento, faturamento e manutenção de frota. Projeto Java com build via Maven.

## Documentação do modelo estratégico

1. [Gestão da Complexidade](docs/01-gestao-complexidade.md)
2. [Domínios e Subdomínios](docs/02-dominios-subdominios.md)
3. [Bounded Contexts](docs/03-bounded-contexts.md)
4. [Linguagem Ubíqua](docs/04-linguagem-ubiqua.md)
5. [Design Estratégico e Shared Kernel](docs/05-design-estrategico.md)

## Estrutura do projeto

O código segue a convenção Maven (`src/main/java`) e dentro de `br/com/logistica` cada bounded context vira um pacote isolado, com suas próprias camadas de domínio, aplicação e infraestrutura.

```
SystemDesignDDD02/
├── pom.xml
├── docs/
└── src/
    └── main/
        └── java/
            └── br/com/logistica/
                ├── sharedkernel/
                │   └── domain/
                │       ├── valueobjects/   Endereco, Dinheiro, Cnpj, Placa
                │       └── identifiers/    PedidoDeFreteId
                │
                └── contexts/
                    ├── gestaofretes/       Core
                    │   ├── domain/
                    │   ├── application/
                    │   └── infrastructure/
                    │
                    ├── rastreamento/       Supporting
                    │   ├── domain/
                    │   ├── application/
                    │   └── infrastructure/
                    │
                    ├── faturamento/        Generic
                    │   ├── domain/
                    │   ├── application/
                    │   └── infrastructure/
                    │
                    └── manutencaofrota/    Supporting
                        ├── domain/
                        ├── application/
                        └── infrastructure/
```

Cada contexto tem seu próprio modelo, seu próprio vocabulário e seus próprios agregados. A comunicação entre eles acontece por eventos de domínio quando o assunto é assíncrono, e por chamadas explícitas através de portas declaradas no domínio quando é síncrono. Nenhum contexto lê direto a base de outro.

## Como compilar

```
mvn clean package
```

Requer Java 17 ou superior. Os repositórios JPA estão como stub e precisam ser implementados conforme a infraestrutura de persistência escolhida.
