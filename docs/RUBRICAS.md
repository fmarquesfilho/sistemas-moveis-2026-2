# Rúbricas — DIM0524 Desenvolvimento de Sistemas para Dispositivos Móveis

**Período**: 2026.2

Os critérios de todas as entregas estão disponíveis desde o início do semestre, o que permite adiantar trabalho.

Prazos e datas: [CRONOGRAMA.md](CRONOGRAMA.md#visão-geral). Pesos e regras de nota: [AVALIACAO.md](AVALIACAO.md).

---

## Como ler as rúbricas

| Nível | Nota | Significado |
|-------|------|-------------|
| Excelente | 10 | Atende plenamente e demonstra domínio |
| Bom | 8 | Atende, com lacunas menores |
| Suficiente | 6 | Atende no mínimo aceitável |
| Insuficiente | 0 a 4 | Não atende ou está ausente |

O Componente A (entrega técnica, 50%) é a média ponderada dos critérios da sprint. O Componente B (30%) segue [AVALIACAO.md §3](AVALIACAO.md#3-componente-b--atividade-no-repositório). O Componente C (20%) usa a rúbrica de comunicação ao final deste documento.

Critérios marcados com ⚙️ têm resultado binário e podem ser conferidos localmente antes da entrega.

### Portão de qualidade

O pipeline mínimo exigido em cada sprint está em [STACK.md](STACK.md#portão-de-qualidade-do-pipeline). Pipeline com falha no momento do prazo limita o Componente A à nota 6.

---

## Sprint 0

Templates, exemplos e estrutura do vídeo e da proposta: [SPRINT-0.md](SPRINT-0.md).

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| ⚙️ **Projeto funcional e CI** | 30% | App compila e roda nos alvos Android e desktop; `ktlintCheck` e `detekt` limpos no GitHub Actions | Compila e roda; CI parcial ou com avisos | Não compila ou sem CI |
| **Proposta do produto** | 25% | Problema delimitado, público identificado, MVP viável em 4 sprints, fora-de-escopo declarado | Proposta plausível mas genérica | Vaga ou ausente |
| **Justificativa de plataforma e backend** | 30% | Escolhas justificadas a partir das características do produto e do público, com alternativas consideradas e descartadas com razão | Escolhas declaradas com justificativa superficial | Sem justificativa ou justificada por conveniência |
| ⚙️ **Primeira tela** | 15% | Tela em Compose com ao menos um componente próprio e reutilizável, estado elevado corretamente, seguindo as convenções de código Kotlin | Tela presente, tudo num único `@Composable` | Sem tela, ou apenas o gerado pelo assistente |

---

## Sprint 1

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| **Telas do MVP** | 25% | Todas as telas principais implementadas, com composição limpa e componentes próprios reutilizáveis | Maioria das telas, com repetição de código | Poucas telas ou layout quebrado |
| ⚙️ **Navegação** | 25% | Navigation Compose com rotas tipadas, argumentos, aninhamento onde faz sentido e ≥ 1 *deep link* funcional demonstrado | Navegação funciona, sem argumentos ou deep link | Troca de tela por estado solto, sem grafo de navegação |
| **Tema, responsividade e adaptatividade** | 20% | Material 3 com esquema de cor coerente, modo claro e escuro, layout adaptado a ≥ 2 larguras sem *overflow* | Tema aplicado, adaptação parcial | Sem tema ou com overflow visível |
| **Acessibilidade** | 15% | Descrições de conteúdo nos elementos interativos, contraste verificado, alvos de toque ≥ 48dp, navegação por leitor de tela testada | Descrições parciais | Ausente |
| ⚙️ **Testes de interface** | 15% | ≥ 5 testes cobrindo as telas principais e a validação do formulário, verdes no CI | ≥ 3 testes, cobertura rasa | < 3 testes ou falhando |

---

## Sprint 2

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| **Gerenciamento de estado** | 25% | ViewModel multiplataforma expondo `StateFlow`, com escopo adequado, sem estado global desnecessário e sem lógica de negócio dentro de `@Composable` | Solução aplicada com inconsistências | Estado espalhado em `remember` ou global sem controle |
| ⚙️ **Arquitetura em camadas** | 30% | `data` / `domain` / `presentation` separados em `commonMain`; **domínio não importa Compose nem bibliotecas de infraestrutura**; repositórios definidos por interface no domínio | Camadas presentes com vazamentos pontuais | Camadas indistintas |
| **Modelagem de estado da UI** | 20% | Carregando, erro, vazio e sucesso modelados explicitamente por interface selada e refletidos na interface | Estados tratados de forma parcial | Só caminho feliz |
| ⚙️ **Testes de lógica** | 15% | ≥ 8 testes de ViewModel com Turbine e dublês injetados por Koin, verdes no CI | ≥ 4 testes, cobertura rasa | < 4 testes ou falhando |
| **Documentação de arquitetura** | 10% | `docs/arquitetura.md` com diagrama de camadas e **justificativa da escolha** de gerenciamento de estado, com alternativa descartada | Documento descritivo, sem justificativa | Ausente |

---

## Sprint 3

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| **Consumo de API real** | 20% | Dados reais do backend escolhido, consumidos com Ktor Client; serialização com kotlinx.serialization; DTO separado da entidade de domínio | Dados reais, serialização manual e acoplada | Dados fictícios |
| **Tratamento de erro de rede** | 20% | Timeout, falha de conexão e resposta inválida tratados **separadamente**, cada um com estado de UI próprio; retentativa com *backoff* onde faz sentido | Erro genérico tratado | Falha silenciosa ou crash |
| **Persistência local** | 20% | Esquema versionado com migração; consultas tipadas; dados do usuário sobrevivem ao fechamento do app | Persistência funciona, sem versionamento | Ausente |
| **Offline-first** | 25% | App abre e é utilizável sem rede: mostra cache, enfileira operações e **sincroniza ao reconectar**, com política de conflito declarada em `docs/offline.md` | Cache de leitura funciona, sem fila de escrita | App inutilizável offline |
| ⚙️ **Autenticação e segurança do token** | 15% | Login funcionando; token em armazenamento seguro do sistema; refresh transparente; nada sensível em DataStore em texto claro | Login funciona, token mal armazenado | Ausente ou credenciais no código |

---

## Entrega Final

Esta entrega absorve o conteúdo do bloco final: recursos do dispositivo, segurança, desempenho e distribuição.

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| **App completo e estável** | 20% | Todos os fluxos do MVP funcionam sem crash na plataforma-alvo; tratamento de erro consistente em todo o app | Fluxos principais funcionam com falhas menores | Instável ou incompleto |
| **Recursos do dispositivo** | 15% | ≥ 2 recursos integrados por `expect`/`actual` com propósito real no produto; fluxo de permissão completo, incluindo negação permanente com caminho de recuperação | 2 recursos integrados, permissão tratada de forma básica | < 2 recursos ou app quebra ao negar permissão |
| ⚙️ **Segurança** | 15% | Dados sensíveis em armazenamento seguro; nenhuma chave de API versionada; `docs/seguranca.md` cobre o OWASP Mobile Top 10 com a mitigação e onde ela está no código | Armazenamento seguro usado; documento parcial | Chave no código ou dados sensíveis em texto claro |
| **Desempenho** | 10% | `docs/desempenho.md` com medição antes e depois de ≥ 1 otimização de recomposição, com número e explicação da causa | Medição feita sem otimização ou sem número | Ausente |
| ⚙️ **Entrega contínua** | 15% | Pipeline gera artefato assinado a partir de `main` e publica em canal de distribuição, automaticamente | Build automatizado, publicação manual | Build manual |
| ⚙️ **Suíte de testes completa** | 15% | Análise estática, testes de unidade, de ViewModel, de interface e ≥ 3 testes de integração verdes no CI; cobertura reportada | Maioria verde, integração fora do CI | Suíte quebrada |
| **Documentação pública** | 5% | Página do projeto com visão geral, arquitetura, guia de execução, capturas de tela e limitações; README permite rodar em < 15 min; licença definida | Documentação presente com lacunas | Não é possível rodar |
| ⚙️ **Ambientes de build** | 5% | ≥ 2 ambientes configurados por build type ou product flavor, com configuração externalizada e sem segredo no código | Ambientes configurados de forma parcial | Ausente |

---

## Bônus — como é verificado

Valores e condições em [AVALIACAO.md](AVALIACAO.md#5-bônus). O que se exige como evidência:

| Bônus | Evidência |
|-------|-----------|
| Integração entre disciplinas | O aplicativo consome a API do grupo em DIM0547, com a URL registrada na configuração e a chamada demonstrada em vídeo ou apresentação; ou o repositório é objeto de estudo em DIM0510, com referência mútua nos READMEs |
| Entrega multiplataforma | O pipeline gera e publica artefato funcional para duas ou mais plataformas, com adaptação de interface e de interação para a segunda |

---

## Rúbrica de Comunicação (Componente C, 20% de toda sprint)

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| **Clareza e objetividade** | 25% | Mensagem direta, dentro do tempo | Compreensível, tempo mal usado | Confusa ou muito fora do tempo |
| **Demonstração do app** | 35% | Captura real do app rodando em dispositivo ou emulador, exercitando os fluxos entregues | Demonstração parcial ou muito editada | Só slides ou capturas estáticas |
| **Justificativa técnica** | 25% | Explica **por que** cada decisão foi tomada — estado, arquitetura, plataforma, offline —, com alternativa descartada | Descreve o que foi feito, sem justificar | Sem justificativa |
| **Participação da equipe** | 15% | Todos falam sobre o que fizeram | Maioria participa | Um só fala pelo grupo |

Nas apresentações, o docente pode solicitar a execução de um fluxo específico, a ativação do modo avião ou a explicação de um trecho de código. A incapacidade de explicar a própria contribuição afeta o Fator de Participação individual.

---

## Checklist por sprint

Pode ser copiado para o `README.md` do repositório.

```markdown
### Sprint 0
- [ ] Repositório público, app compila e roda nos alvos Android e desktop
- [ ] CI verde: ktlintCheck + detekt
- [ ] docs/proposta.md com justificativa de plataforma-alvo e backend
- [ ] 1 tela em Compose com componente próprio
- [ ] Coorte (A/B), integração e intenção de multiplataforma declaradas
- [ ] Vídeo 5 min

### Sprint 1
- [ ] Todas as telas do MVP
- [ ] Navigation Compose: rotas tipadas, com argumento, ≥1 deep link
- [ ] Material 3 com modo claro/escuro
- [ ] Layout adaptado a ≥2 tamanhos de janela, sem quebra
- [ ] Acessibilidade: descrição de conteúdo, contraste, alvos ≥48dp
- [ ] Formulário com validação
- [ ] ≥5 testes de interface verdes no CI
- [ ] Vídeo 5 min

### Sprint 2
- [ ] Estado com ViewModel multiplataforma e StateFlow
- [ ] Camadas data/domain/presentation; domínio sem import de Compose
- [ ] Repositórios por interface no domínio
- [ ] Estados carregando/erro/vazio/sucesso modelados por interface selada
- [ ] Injeção de dependências com Koin em commonMain
- [ ] ≥8 testes de ViewModel com Turbine
- [ ] docs/arquitetura.md com diagrama e justificativa
- [ ] Vídeo 5 min

### Sprint 3
- [ ] Dados reais do backend escolhido, via Ktor Client
- [ ] Serialização com kotlinx.serialization; DTO separado da entidade
- [ ] Timeout, falha de rede e resposta inválida tratados separadamente
- [ ] Persistência local com Room ou SQLDelight, esquema versionado
- [ ] App utilizável offline + fila de escrita + sincronização
- [ ] docs/offline.md com política de conflito
- [ ] Auth com token em armazenamento seguro
- [ ] Vídeo 5 min

### Entrega Final
- [ ] App estável na plataforma-alvo
- [ ] ≥2 recursos do dispositivo via expect/actual, com permissões tratadas
- [ ] Dados sensíveis em armazenamento seguro; docs/seguranca.md
- [ ] docs/desempenho.md com medição antes/depois
- [ ] Pipeline publica artefato automaticamente de main
- [ ] Suíte completa verde
- [ ] ≥2 ambientes de build configurados
- [ ] Documentação pública + README (<15 min) + licença
- [ ] Vídeo 10 min
- [ ] Apresentação ao vivo
```
