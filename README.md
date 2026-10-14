# Pipeline CI Mínimo e Reproduzível

## 1. Objetivo

Este projeto implementa um pipeline de Integração Contínua (CI) com GitHub Actions para uma aplicação Java com Maven (Aplicação Biblioteca).

O pipeline é repetível a partir do repositório e automatiza: checkout do código, build, execução dos testes, identificação do artefato e registro das evidências de execução. Se qualquer etapa falhar, o pipeline é interrompido.

## 2. Estrutura do repositório

```
.
├── .github/workflows/ci.yml        # definição do pipeline
├── src/
│   ├── main/java/Main.java         # código da aplicação
│   └── test/java/MainTest.java     # teste automatizado (JUnit 5)
├── docs/                           # prints das execuções (evidências)
│   ├── evidencia-sucesso.png
│   └── evidencia-falha.png
├── pom.xml                         # configuração do Maven e dependências
└── README.md
```

## 3. Gatilho

O pipeline é iniciado automaticamente por dois eventos:

- `push` em qualquer branch do repositório;
- `pull_request`.

## 4. Etapas do pipeline

Definidas em `.github/workflows/ci.yml` (workflow **Pipeline CI**, job `build`, executado em `ubuntu-latest`):

| # | Etapa | O que faz |
|---|-------|-----------|
| 1 | Checkout do código | `actions/checkout@v4` baixa o código do commit que disparou o pipeline |
| 2 | Configurar Java | `actions/setup-java@v4` instala o Java 17 (Temurin) e ativa o cache do Maven |
| 3 | Build e testes | `mvn clean verify` compila o projeto, executa os testes e empacota o JAR |
| 4 | Enviar artefato | `actions/upload-artifact@v4` guarda o JAR gerado no GitHub Actions |

**Interrupção em caso de falha:** se uma etapa falhar (por exemplo, um teste), o GitHub Actions para o pipeline e as etapas seguintes não são executadas. Assim, nenhum artefato é gerado a partir de um código que não passou nos testes.

## 5. Dependências

| Dependência | Versão | Uso |
|-------------|--------|-----|
| Java (JDK, Temurin) | 17 | Compilar e executar a aplicação |
| Maven | usado pelo runner `ubuntu-latest` | Build e gerenciamento de dependências |
| JUnit Jupiter (JUnit 5) | 5.10.2 | Testes automatizados |
| maven-surefire-plugin | 3.2.5 | Executa os testes durante o `mvn verify` |

## 6. Artefato

- **Nome:** `aplicacao-biblioteca`
- **Conteúdo:** arquivo JAR gerado pelo Maven em `target/*.jar`
- **Onde encontrar:** aba **Actions** → abrir a execução → seção **Artifacts**, no final da página.

O artefato só é gerado quando o build e os testes passam.

## 7. Evidência de execução com sucesso

O pipeline foi executado com sucesso (execução **#28**, commit `a6d3e59`, gatilho `push` na branch `main`): o build e os testes passaram e o artefato foi gerado.

- Status: **Success**
- Duração total: 22s
- Artefatos gerados: **1**

![Execução #28 do Pipeline CI com sucesso](docs/evidencia-sucesso.png)

## 8. Evidência de falha detectada

Para demonstrar que o pipeline detecta falhas, foi feita uma alteração proposital no teste `MainTest`, deixando o valor esperado diferente do valor real (execução **#26**, commit `8250fcb`). Com isso, o pipeline falhou na etapa **Build e testes**, foi interrompido, e nenhum artefato foi gerado. Depois, a alteração foi revertida e o pipeline voltou a passar (execução #28).

- Status: **Failure**
- Job `build`: falhou (`Process completed with exit code 1`)
- Artefatos gerados: **nenhum**

![Execução #26 do Pipeline CI com falha](docs/evidencia-falha.png)

## 9. Como reproduzir

**No GitHub Actions:**

1. Acessar o repositório no GitHub.
2. Fazer uma alteração no código e dar commit (ou abrir uma Pull Request).
3. Abrir a aba **Actions**.
4. Selecionar o workflow **Pipeline CI** e abrir a execução mais recente.
5. Acompanhar as etapas e, se tudo passar, baixar o artefato na seção **Artifacts**.

**Localmente** (requer Java 17 e Maven instalados):

```bash
git clone https://github.com/bel1020304050/Pipeline-CI-m-nimo-e-reproduz-vel
cd Pipeline-CI-m-nimo-e-reproduz-vel
mvn clean verify
```

Resultado esperado: `BUILD SUCCESS` e o arquivo JAR em `target/`.

## 10. Como investigar falhas

1. Abrir a aba **Actions** e clicar na execução com ❌.
2. Abrir o job `build` e identificar a etapa marcada em vermelho.
3. Ler o log dessa etapa. Para falha de teste, procurar por `Tests run`, `FAILURE` e o nome do teste que falhou.
4. Corrigir o código ou o teste, dar commit e conferir se a nova execução fica ✅.
5. Para testar antes de dar push, rodar `mvn clean verify` na máquina local.

## 11. Proteção de segredos

- Senhas, tokens e chaves de acesso nunca devem ficar no código, no `pom.xml` ou no `ci.yml`.
- Quando forem necessários, devem ser cadastrados em **Settings → Secrets and variables → Actions** e usados no pipeline como `${{ secrets.NOME_DO_SEGREDO }}`. O GitHub oculta esses valores nos logs.
- Este pipeline não usa segredos próprios. As ações utilizadas recebem apenas o token temporário que o próprio GitHub cria a cada execução (`GITHUB_TOKEN`).

## 12. Resultado

O Pipeline CI automatiza a validação da aplicação a cada alteração: baixa o código, compila, executa os testes e gera o artefato. Em caso de falha, o pipeline é interrompido e as evidências ficam registradas na aba Actions.
