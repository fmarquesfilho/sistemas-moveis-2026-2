# Stack Tecnológica — DIM0524 Desenvolvimento de Sistemas para Dispositivos Móveis

Documento de referência das tecnologias adotadas no curso, das razões da escolha e das instruções de configuração. Complementa o [Plano de Curso](PLANO_DE_CURSO.md).

Todas as ferramentas e serviços listados são gratuitos e não exigem cartão de crédito.

---

## 1. Por que Kotlin Multiplatform

**O que é.** Kotlin Multiplatform, ou KMP, permite compartilhar código Kotlin entre Android, iOS, desktop, web e servidor, mantendo a possibilidade de escrever partes específicas de cada plataforma. Compose Multiplatform é a camada de interface construída sobre ele, que estende o Jetpack Compose do Android às demais plataformas.

**Maturidade.** Compose Multiplatform para iOS é estável desde maio de 2025, com renderização por Metal. O ferramental de KMP passou a ter suporte completo em Windows e Linux, incluindo assistente de projeto, verificações prévias e Compose Hot Reload; apenas as configurações de execução de iOS e o suporte a Swift continuam restritos ao macOS, por limitação das ferramentas da Apple.

**Adequação ao curso.** A linguagem é a mesma da plataforma Android nativa, então o que se aprende aqui é transferível para desenvolvimento nativo, e não apenas para um framework. A interface é declarativa e testável sem emulador, o que permite avaliar parte do projeto automaticamente no pipeline. O alvo desktop dá um ciclo de edição e visualização rápido em qualquer sistema operacional, e o alvo web permite demonstrar o produto sem instalar nada.

**Custo de entrada.** As ferramentas são gratuitas e funcionam em Windows, macOS e Linux. Não é necessário hardware Apple para desenvolver e distribuir aplicações Android.

---

## 2. Interface: Compose Multiplatform

**Compose Multiplatform é o padrão do curso.** A interface é escrita uma vez, em `commonMain`, e roda em Android, iOS, desktop e web.

Quem tiver como alvo apenas o Android continua escrevendo exatamente o mesmo código: Jetpack Compose e Compose Multiplatform compartilham a mesma API, e a diferença está apenas nos alvos de compilação declarados no Gradle.

**SwiftUI** é admitido como camada de interface adicional sobre o módulo Kotlin compartilhado, para grupos que tenham Mac. Essa é a arquitetura clássica de KMP — lógica compartilhada, interface nativa por plataforma — e conta para o bônus de entrega multiplataforma. Não é exigida de ninguém, porque depende de hardware que nem todos têm.

### Plataforma-alvo

O alvo prioritário é **Android ou iOS**, escolhido e justificado na Sprint 0 a partir dos aparelhos da equipe e do público do produto. Quem não tiver aparelho usa o emulador do Android, gratuito.

Web e desktop não servem como alvo prioritário: o bloco final exige recursos do dispositivo, armazenamento seguro e publicação em canal de distribuição, que não existem em navegador. Ambos valem como **alvo secundário**, contando para o bônus de entrega multiplataforma, cujas regras estão em [AVALIACAO.md](AVALIACAO.md#5-bônus).

---

## 3. Bibliotecas adotadas

**Navigation Compose** para navegação, com rotas tipadas, argumentos, pilha de retorno e deep links. Existe uma geração seguinte, a Navigation 3, ainda em alpha; o curso fica na versão estável, e quem quiser experimentar a nova deve justificar na proposta.

**ViewModel multiplataforma com `StateFlow`** para estado e ciclo de vida. **Koin** para injeção de dependências: resolve em tempo de execução, sem geração de código, e funciona em `commonMain` sem configuração por plataforma.

**Ktor Client** para rede e **kotlinx.serialization** para serialização, ambos oficiais e multiplataforma. **Coil 3** para imagens.

**Room ou SQLDelight** para persistência local, à escolha do grupo, com justificativa. Room traz o modelo de anotações já conhecido de quem viu Android nativo; SQLDelight parte do SQL e gera as funções Kotlin, com verificação em tempo de compilação. **DataStore** para chave-valor.

**Testes** com `kotlin.test`, **Turbine** para fluxos e o teste de interface do Compose.

Alternativas discutidas ao longo do curso, sem adoção obrigatória: Decompose e Voyager para navegação, MVIKotlin para estado, kotlin-inject para injeção, Realm para persistência, Maestro para testes de ponta a ponta. Bibliotecas com suporte a `commonMain` podem ser consultadas em `klibs.io`.

---

## 4. Escolha do backend

O grupo escolhe uma das opções na Sprint 0 e justifica a decisão a partir das características do produto. A opção C dá direito ao bônus de integração entre disciplinas.

| Opção | Serviço | Vantagens | Limitações |
|-------|---------|-----------|------------|
| A | Supabase | PostgreSQL, autenticação, realtime e storage; camada gratuita sem cartão | Modelo de dados limitado ao que o serviço oferece |
| B | Firebase | Autenticação, Firestore, storage, notificações e crash reporting integrados | Consultas restritas ao modelo do Firestore |
| C | API própria de Web II | Controle total do backend; dá direito ao bônus de integração | Exige cursar DIM0547 e coordenar dois cronogramas |
| D | Local com APIs públicas | Sem infraestrutura de backend a manter | Menor superfície de autenticação e sincronização, que precisa ser compensada em outras dimensões do projeto |

---

## 5. Ferramentas

| Categoria | Ferramenta |
|-----------|-----------|
| Linguagem | Kotlin |
| Editor | Android Studio ou IntelliJ IDEA, com o plugin de Kotlin Multiplatform |
| Build | Gradle com catálogo de versões |
| Diagnóstico do ambiente | `kdoctor` |
| Análise estática | `ktlint` e `detekt` |
| Testes | `kotlin.test`, Turbine, teste de interface do Compose |
| Ciclo rápido de interface | Compose Hot Reload e previews |
| Versionamento e CI | GitHub e GitHub Actions |
| Distribuição | Teste interno da Play Console, TestFlight |
| Comunicação | Discord e Google Meet |

---

## 6. Configuração do ambiente

### Pré-requisitos

- Android Studio com o plugin de Kotlin Multiplatform: https://kotlinlang.org/docs/multiplatform/quickstart.html
- JDK 17 ou superior
- Emulador Android configurado ou dispositivo físico com depuração USB habilitada
- Conta no GitHub
- Para desenvolvimento iOS: macOS com Xcode instalado

Após a instalação, `kdoctor` deve executar sem erros bloqueantes.

### Estrutura esperada do repositório

```
.
├── composeApp/
│   ├── src/
│   │   ├── commonMain/kotlin/
│   │   │   ├── data/          fontes de dados, DTOs, implementações de repositório
│   │   │   ├── domain/        entidades, interfaces de repositório, casos de uso
│   │   │   └── presentation/  telas em Compose e ViewModels
│   │   ├── androidMain/kotlin/   implementações actual de Android
│   │   ├── iosMain/kotlin/       implementações actual de iOS
│   │   ├── commonTest/kotlin/    testes compartilhados
│   │   └── jvmMain/kotlin/       alvo desktop, para o ciclo rápido de interface
├── iosApp/            projeto Xcode, gerado pelo assistente
├── docs/              documentação do projeto
├── gradle/libs.versions.toml    catálogo de versões
└── .github/workflows/ pipelines de CI/CD
```

A camada de domínio não deve importar Compose nem bibliotecas de infraestrutura. Essa regra é verificada na avaliação a partir da Sprint 2.

### Portão de qualidade do pipeline

Na Sprint 0, quando ainda não há testes:

```yaml
- ./gradlew ktlintCheck detekt
- ./gradlew assembleDebug
```

Da Sprint 1 em diante, acrescenta-se a suíte de testes:

```yaml
- ./gradlew allTests
```

No bloco final, entram os testes de integração e a geração e publicação do artefato de distribuição.

---

## 7. Distribuição

| Plataforma | Canal | Custo | Exige hardware específico |
|-----------|-------|-------|---------------------------|
| Android | Artefato APK publicado como release do GitHub | Gratuito | Não |
| Android | Teste interno da Play Console | Taxa única de US$ 25 para a conta de desenvolvedor | Não |
| iOS | Simulador local | Gratuito | Mac |
| iOS | TestFlight | Requer Apple Developer Program, US$ 99 por ano | Mac |
| Desktop | Artefato publicado como release do GitHub | Gratuito | Não |

A entrega final exige que o pipeline publique automaticamente em ao menos um canal. Publicar o APK assinado como release do GitHub atende ao requisito sem custo.

---

## 8. Ambientes de desenvolvimento online

Nenhuma etapa do curso exige máquina própria capaz de rodar emulador. Estas são as alternativas, em ordem crescente de capacidade:

| Ambiente | Cadastro | O que roda | Limite |
|---|---|---|---|
| **Kotlin Playground** · `play.kotlinlang.org` | não | Kotlin e exemplos de Compose no navegador | Sem bibliotecas externas, sem câmera ou GPS |
| **Compose Multiplatform para web** | não | Demonstração do próprio projeto no navegador | Alvo web em beta |
| **Alvo desktop, na própria máquina** | não | O projeto inteiro, com Compose Hot Reload | Não exercita comportamento de dispositivo |
| **GitHub Codespaces** | sim | IDE no navegador, build e testes por Gradle | 180 h por mês com o GitHub Student Pack |

**Recomendação por sprint.** Sprint 0 e exercícios de linguagem: Kotlin Playground. Sprints 1 e 2: alvo desktop com Compose Hot Reload, que é o ciclo mais rápido e funciona em qualquer sistema operacional. Sprint 3 em diante: emulador Android ou aparelho, porque rede, persistência e recursos do dispositivo precisam ser exercitados no ambiente real.

Grupos que optarem por Codespaces devem versionar um `.devcontainer/devcontainer.json` com o JDK e o Android SDK — isso torna o ambiente reproduzível e conta como evidência de qualidade na rúbrica.

Recursos do dispositivo — câmera, GPS, sensores, notificações — **não funcionam em navegador**. No bloco final é indispensável um aparelho Android ou um emulador. Quem não tiver, combine com o docente até o fim da Sprint 3.

---

## 9. Referências de configuração

- Início rápido de Kotlin Multiplatform: https://kotlinlang.org/docs/multiplatform/quickstart.html
- Compose Multiplatform: https://kotlinlang.org/compose-multiplatform/
- Compose Hot Reload: https://kotlinlang.org/docs/multiplatform/compose-hot-reload.html
- Navegação: https://kotlinlang.org/docs/multiplatform/compose-navigation.html
- ViewModel multiplataforma: https://kotlinlang.org/docs/multiplatform/compose-viewmodel.html
- Ktor Client: https://ktor.io/docs/client-create-new-application.html
- Koin: https://insert-koin.io/
- SQLDelight: https://sqldelight.github.io/sqldelight/
- Catálogo de bibliotecas multiplataforma: https://klibs.io/
