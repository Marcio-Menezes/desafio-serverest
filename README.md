# desafio-serverest

Esse projeto tem como objetivo realizar testes automatizados utilizando RestAssured para interagir com a API do ServeRest e fazer checagem de usuários.

## Descrição

Este projeto implementa testes automatizados para a API do ServeRest utilizando a biblioteca RestAssured. Os testes cobrem diferentes aspectos da API, como criação, edição, busca e exclusão de usuários.

## Pré-Requisitos

- Java JDK 11 ou superior instalado
- Gradle instalado

## Como Executar

1. Clone este repositório para o seu computador.
2. Navegue até o diretório do projeto no terminal.
3. Execute os testes com o seguinte comando:
   
   ```bash
   gradle clean test

Ou caso você use o IntelliJ, é só clicar com o botão direito em ServeRestTest e ir em Run Test e ver a magia acontecer.
## Estrutura do Projeto

- src/test/java: Contém os testes automatizados.
- src/main/java: Contém o modelo de usuario utilizado.
- build.gradle: Arquivo de configuração do Gradle.

## Como Funciona

Os testes automatizados utilizam a biblioteca RestAssured para fazer chamadas à API do ServeRest.

O ServeRestStub é usado para criar e gerenciar instâncias de usuários para os testes.

Os testes estão organizados em ordem dentro da classe de teste e devem ser executados em conjunto.
