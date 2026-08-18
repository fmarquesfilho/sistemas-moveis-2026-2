# Plano de Curso — DIM0524 Desenvolvimento de Sistemas para Dispositivos Móveis

**Curso**: Bacharelado em Engenharia de Software — UFRN/DIMAp
**Carga horária**: 60 horas
**Horário**: Segundas e quartas, 14:50 às 16:30 (24T34)
**Período**: 17/08/2026 a 19/12/2026
**Docente**: Fernando Figueira Filho — fernando.figueira@ufrn.br

As aulas de 10/08, 12/08 e 17/08 não foram realizadas. O curso inicia em 19/08.

---

## Ementa

Fundamentos de desenvolvimento para dispositivos móveis. Plataformas nativas e multiplataforma. Linguagem Kotlin e Kotlin Multiplatform. Interfaces declarativas, responsivas e acessíveis com Compose Multiplatform. Navegação e gerenciamento de estado. Consumo de APIs, persistência local e estratégias offline-first. Acesso a recursos do dispositivo e segurança em aplicações móveis. Testes automatizados, desempenho e observabilidade. Empacotamento, distribuição e integração contínua.

---

## Objetivos

### Geral

Capacitar o estudante a projetar, implementar, testar e distribuir aplicações móveis multiplataforma com Kotlin Multiplatform e Compose Multiplatform, aplicando arquitetura em camadas, funcionamento offline, acesso a recursos do dispositivo e entrega automatizada.

### Específicos

Ao final do curso, o estudante será capaz de:

1. Programar em Kotlin com null safety, data classes, classes seladas e `when` como expressão
2. Escrever código assíncrono com coroutines, concorrência estruturada e `Flow`
3. Explicar a estrutura de um projeto Kotlin Multiplatform e o mecanismo `expect`/`actual`
4. Construir interfaces declarativas responsivas, adaptativas e acessíveis com Compose Multiplatform e Material 3
5. Estruturar navegação com rotas tipadas, argumentos, pilha de retorno e deep links
6. Aplicar gerenciamento de estado com ViewModel multiplataforma e `StateFlow`
7. Organizar a aplicação em camadas de dados, domínio e apresentação, com injeção de dependências
8. Consumir APIs HTTP com tratamento de erros, retentativa e estados de carregamento
9. Implementar persistência local e estratégia offline-first com sincronização e resolução de conflitos
10. Acessar recursos do dispositivo por `expect`/`actual`, tratando permissões corretamente
11. Escrever testes de unidade, de interface e de integração, e executá-los em integração contínua
12. Empacotar, assinar e distribuir a aplicação com pipeline de entrega contínua
13. Justificar a escolha de plataforma-alvo a partir das características do produto e do público

---

## Organização do curso

Este é um curso novo, oferecido pela primeira vez neste formato. As tecnologias adotadas, as razões da escolha e as instruções de configuração do ambiente estão em [STACK.md](STACK.md).

### Estrutura das sprints

Uma Sprint 0 de quatro semanas, três sprints de projeto e um bloco final, com apresentações ao fim de cada sprint. Datas, conteúdo de cada aula e prazos: [CRONOGRAMA.md](CRONOGRAMA.md).


### Projeto integrador

Equipes de 1 a 4 estudantes desenvolvem um aplicativo ao longo do semestre, em repositório público no GitHub. A atividade no repositório compõe a nota.

### Plataforma-alvo e backend

Cada grupo escolhe a plataforma-alvo prioritária — **Android ou iOS** — e a estratégia de backend, e justifica ambas na Sprint 0. As opções, os limites e o bônus de entrega multiplataforma estão em [STACK.md](STACK.md).

Publicar na App Store ou na Play Store não é exigido em nenhum momento do curso.

---

## Conteúdo programático

### Sprint 0 — Kotlin, Compose e a primeira tela

Panorama do desenvolvimento móvel: plataformas nativas e multiplataforma. Kotlin: tipos, `val` e `var`, null safety, coleções, funções, data classes, classes seladas e `when` como expressão. Funções de extensão e funções de escopo. Compose: funções `@Composable`, recomposição, estado com `remember` e elevação de estado, modificadores e layouts. Componentes próprios e reutilizáveis. Tema Material 3: esquema de cor e tipografia. Estrutura de um projeto Kotlin Multiplatform, Gradle e catálogo de versões. Ambiente de desenvolvimento, Compose Hot Reload, previews e análise estática.

### Sprint 1 — Interface e navegação

Listas e rolagem com `LazyColumn` e `LazyRow`, chaves e desempenho. Formulários e validação. Material 3 em profundidade: modo claro e escuro, e esquema de cor derivado. Responsividade e adaptatividade com classes de tamanho de janela. Acessibilidade: semântica, contraste e alvos de toque. Navegação com Navigation Compose: rotas tipadas, argumentos, pilha de retorno e deep links. Testes em Compose Multiplatform: unidade e interface.

### Sprint 2 — Estado e arquitetura

Programação assíncrona: `suspend`, coroutines e concorrência estruturada. `Flow` e `StateFlow`. ViewModel multiplataforma e coleta consciente do ciclo de vida. Modelagem do estado de tela com classes seladas. Arquitetura em camadas de dados, domínio e apresentação. Injeção de dependências e inversão de controle com Koin. Testes de ViewModel com Turbine.

### Sprint 3 — Dados, rede e offline-first

Consumo de APIs com Ktor Client: requisições, interceptação, timeouts e retentativa com backoff. Serialização com kotlinx.serialization e mapeamento entre DTOs e entidades de domínio. Tratamento de erro e os estados de carregamento, vazio, erro e sucesso. Persistência local com Room ou SQLDelight, e chave-valor com DataStore. Estratégia offline-first: cache, fila de operações pendentes, sincronização e resolução de conflitos. Autenticação no cliente e armazenamento de token.

### Bloco final — Recursos do dispositivo, segurança e distribuição

Recursos do dispositivo por `expect`/`actual`: câmera, galeria, geolocalização, sensores, compartilhamento e notificações. Modelo de permissões de Android e iOS, solicitação em contexto e negação permanente. Armazenamento seguro com Keychain e Keystore. OWASP Mobile Top 10, ofuscação, certificate pinning e proteção de chaves de API. Interoperabilidade entre Kotlin e Swift. Configuração por ambiente. Build e assinatura: APK, AAB e provisionamento iOS. Distribuição por teste interno da Play Console e TestFlight. Pipeline de CI/CD com GitHub Actions. Observabilidade em produção: crash reporting, analytics e feature flags. Desempenho: recomposições desnecessárias, estabilidade de parâmetros e perda de quadros. Testes de integração.

---

## Avaliação

Nota final por média aritmética de três unidades, compostas pelas entregas das sprints e por duas provas escritas, valendo a maior das duas notas. Cada sprint é avaliada em entrega técnica, atividade no repositório e comunicação.

Composição, pesos e regras: [AVALIACAO.md](AVALIACAO.md). Critérios de cada entrega: [RUBRICAS.md](RUBRICAS.md).


---

## Bibliografia

### Básica

Kotlin Multiplatform Documentation. Disponível em: https://kotlinlang.org/docs/multiplatform/

Compose Multiplatform Documentation. Disponível em: https://kotlinlang.org/compose-multiplatform/

Kotlin Language Documentation. Disponível em: https://kotlinlang.org/docs/home.html

### Complementar

Kotlin Coroutines Guide. Disponível em: https://kotlinlang.org/docs/coroutines-guide.html

Jetpack Compose — Thinking in Compose. Disponível em: https://developer.android.com/develop/ui/compose/mental-model

Navigation in Compose Multiplatform. Disponível em: https://kotlinlang.org/docs/multiplatform/compose-navigation.html

Ktor Client Documentation. Disponível em: https://ktor.io/docs/client-create-new-application.html

Koin Documentation. Disponível em: https://insert-koin.io/

SQLDelight Documentation. Disponível em: https://sqldelight.github.io/sqldelight/

klibs.io — catálogo de bibliotecas multiplataforma. Disponível em: https://klibs.io/

Material 3 Design Guidelines. Disponível em: https://m3.material.io/

OWASP Mobile Application Security. Disponível em: https://mas.owasp.org/

Android Developers — App Security Best Practices. Disponível em: https://developer.android.com/privacy-and-security/security-best-practices

VALENTE, Marco Tulio. *Engenharia de Software Moderna*. UFMG, 2020. Disponível em: https://engsoftmoderna.info/

Toda a bibliografia é de acesso gratuito.
