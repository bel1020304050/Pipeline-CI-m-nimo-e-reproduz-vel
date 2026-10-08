# Pipeline CI Mínimo e Reproduzível

## 1. Objetivo

Este projeto apresenta um Pipeline de Integração Contínua (CI) utilizando GitHub Actions para uma aplicação Java com Maven.

O objetivo é automatizar o processo de checkout, build, execução de testes e geração de artefato.

## 2. Ferramentas utilizadas

- GitHub
- GitHub Actions
- Java 17
- Maven
- JUnit 5

## 3. Gatilho do Pipeline

O pipeline é executado automaticamente quando ocorre um push no repositório ou uma Pull Request.

## 4. Etapas do Pipeline

O pipeline possui as seguintes etapas:

1. Checkout do código.
2. Configuração do Java.
3. Execução do build e dos testes utilizando Maven.
4. Geração do arquivo JAR.
5. Armazenamento do JAR como artefato no GitHub Actions.

## 5. Comando utilizado

O build e os testes são executados através do comando:

mvn clean verify

## 6. Artefato

Ao final da execução, o arquivo JAR gerado pelo Maven é disponibilizado como artefato no GitHub Actions.

## 7. Execução com sucesso

O pipeline foi executado com sucesso no GitHub Actions.

A execução apresentou status `Success` e gerou 1 artefato.

## 8. Execução com falha

Será realizada uma alteração proposital em um teste para demonstrar que o pipeline identifica uma falha durante a execução dos testes.

## 9. Como reproduzir

Para reproduzir o pipeline:

1. Acessar o repositório no GitHub.
2. Fazer uma alteração no código.
3. Realizar um commit e push.
4. Acessar a aba Actions.
5. Selecionar o workflow Pipeline CI.
6. Verificar a execução do build e dos testes.
7. Consultar o artefato gerado quando a execução for concluída com sucesso.

## 10. Investigação de falhas

Quando o pipeline apresentar uma falha, deve-se acessar a aba Actions, abrir a execução que apresentou erro e verificar o log da etapa que falhou.

## 11. Proteção de segredos

Informações sensíveis, como senhas, tokens e chaves de acesso, não devem ser colocadas diretamente no código ou no arquivo do pipeline. Quando necessários, esses valores devem ser armazenados como Secrets do GitHub.

## 12. Resultado

O Pipeline CI automatiza o processo de validação da aplicação, executando o build, os testes e gerando o artefato de forma reprodutível.
