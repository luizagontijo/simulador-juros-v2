
# Simulador de Juros (Spring Boot) — v2

Esta versão corrige o `pom.xml` (versão do parent **fixa**) e inclui um `settings-central.xml` para rodar o Maven usando o **Maven Central**, ignorando o mirror corporativo.

## Como rodar (Windows / PowerShell)
1. Garanta **Java 17** e **Maven** instalados (`java -version`, `mvn -version`).
2. Rode com o settings local (bypass do mirror):
   ```powershell
   mvn -s .\settings-central.xml test
   mvn -s .\settings-central.xml spring-boot:run
   ```

> Dica: se preferir, você pode remover/ajustar o mirror no seu `%USERPROFILE%\.m2\settings.xml`. Porém, usar `-s` aqui evita alterar seu ambiente global.

## Endpoint
- **POST** `http://localhost:8080/simulacoes/juros`
- **Body**
  ```json
  {
    "idade": 30,
    "rendaMensal": 3000.00,
    "valorSolicitado": 10000.00,
    "numeroParcelas": 12
  }
  ```
