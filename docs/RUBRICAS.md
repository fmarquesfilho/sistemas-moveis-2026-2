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
| ⚙️ **Primeira tela** *(opcional)* | 15% | Tela em Compose com ao menos um componente próprio e reutilizável, estado elevado corretamente, seguindo as convenções de código Kotlin | Tela presente, tudo num único `@Composable` | Apenas o gerado pelo assistente |

> **Primeira tela — opcional nesta Sprint 0.** Como os conceitos de Compose ainda estão sendo consolidados, a tela deixou de ser obrigatória. Se entregue, entra na média ponderada do Componente A com o peso de 15%. Se não entregue, o peso é neutralizado: o Componente A passa a ser a média ponderada apenas dos critérios entregues (Projeto funcional e CI, Proposta do produto, e Justificativa de plataforma e backend). Não entregar a tela não reduz a nota.

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

Última sprint do semestre (ajuste de 03/10): substitui a Sprint 2, a Sprint 3 e a Entrega Final previstas no início do período. O que for entregue aqui é o produto final.

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| ⚙️ **Estado e arquitetura** | 30% | ViewModel multiplataforma expondo `StateFlow`; `data` / `domain` / `presentation` separados em `commonMain`; **domínio não importa Compose nem bibliotecas de infraestrutura**; repositórios definidos por interface; sem lógica de negócio dentro de `@Composable` | Solução aplicada com vazamentos pontuais entre camadas | Estado espalhado em `remember`, camadas indistintas |
| **Estados da interface** | 15% | Carregando, erro, vazio e sucesso modelados por interface selada e refletidos na tela | Estados tratados de forma parcial | Só caminho feliz |
| **Dados reais e erro de rede** | 25% | Dados do backend escolhido, consumidos com Ktor Client e kotlinx.serialization, com DTO separado da entidade de domínio; falha de conexão e resposta inválida tratadas, cada uma com estado de tela próprio | Dados reais, erro genérico | Dados fictícios, falha silenciosa ou crash |
| **Persistência local** | 10% | Dados do usuário sobrevivem ao fechamento do app (Room, SQLDelight ou DataStore), e o app abre sem rede mostrando o último conteúdo | Persistência só de preferências | Ausente |
| ⚙️ **Testes de lógica** | 10% | ≥ 5 testes de ViewModel com Turbine e dublês, verdes no CI junto com os testes de interface da Sprint 1 | ≥ 3 testes, cobertura rasa | < 3 testes ou falhando |
| **App e documentação** | 10% | Fluxos do MVP sem crash na plataforma-alvo; `docs/arquitetura.md` com diagrama de camadas e a justificativa do gerenciamento de estado; README permite rodar em menos de 15 min; APK de debug anexado a uma release | App roda com falhas menores, documentação com lacunas | Instável, ou não é possível rodar |

---

## Bônus — como é verificado

Valores e condições em [AVALIACAO.md](AVALIACAO.md#5-bônus). O que se exige como evidência:

| Bônus | Evidência |
|-------|-----------|
| Integração entre disciplinas | O aplicativo consome a API do grupo em DIM0547, com a URL registrada na configuração e a chamada demonstrada no vídeo ou na *daily meeting*; ou o repositório é objeto de estudo em DIM0510, com referência mútua nos READMEs |
| Entrega multiplataforma | O pipeline gera e publica artefato funcional para duas ou mais plataformas, com adaptação de interface e de interação para a segunda |

---

## Rúbrica de Comunicação (Componente C, 20% de toda sprint)

| Critério | Peso | Excelente (10) | Suficiente (6) | Insuficiente (0–4) |
|----------|------|----------------|----------------|--------------------|
| **Clareza e objetividade** | 25% | Mensagem direta, dentro do tempo | Compreensível, tempo mal usado | Confusa ou muito fora do tempo |
| **Demonstração do app** | 35% | Captura real do app rodando em dispositivo ou emulador, exercitando os fluxos entregues | Demonstração parcial ou muito editada | Só slides ou capturas estáticas |
| **Justificativa técnica** | 25% | Explica **por que** cada decisão foi tomada — estado, arquitetura, plataforma, offline —, com alternativa descartada | Descreve o que foi feito, sem justificar | Sem justificativa |
| **Participação da equipe** | 15% | Todos falam sobre o que fizeram | Maioria participa | Um só fala pelo grupo |

A rubrica vale para o vídeo e para a *daily meeting*, que não exige slides nem preparação: conta o que o grupo mostra e explica. Nas *daily meetings*, o docente pode solicitar a execução de um fluxo específico, a ativação do modo avião ou a explicação de um trecho de código. A incapacidade de explicar a própria contribuição afeta o Fator de Participação individual.

---

## Checklist por sprint

Pode ser copiado para o `README.md` do repositório.

```markdown
### Sprint 0
- [ ] Repositório público, app compila e roda nos alvos Android e desktop
- [ ] CI verde: ktlintCheck + detekt
- [ ] docs/proposta.md com justificativa de plataforma-alvo e backend
- [ ] *(opcional)* 1 tela em Compose com componente próprio
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

### Sprint 2 (final)
- [ ] Estado com ViewModel multiplataforma e StateFlow
- [ ] Camadas data/domain/presentation; domínio sem import de Compose
- [ ] Estados carregando/erro/vazio/sucesso modelados por interface selada
- [ ] Dados reais do backend escolhido, via Ktor Client, com DTO separado
- [ ] Falha de conexão e resposta inválida com estado de tela próprio
- [ ] Dados do usuário sobrevivem ao fechamento do app
- [ ] ≥5 testes de ViewModel com Turbine, verdes no CI
- [ ] docs/arquitetura.md, README e APK de debug numa release
- [ ] Vídeo 5 min
```
