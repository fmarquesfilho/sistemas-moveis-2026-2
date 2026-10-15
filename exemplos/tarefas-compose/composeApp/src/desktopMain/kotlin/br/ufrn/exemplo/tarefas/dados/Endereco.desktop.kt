package br.ufrn.exemplo.tarefas.dados

// TAREFAS_API aponta para outra máquina (TAREFAS_API=http://192.168.0.10:8080) ou desliga a
// rede (TAREFAS_API=memoria): o app roda com o repositório em memória, sem a API de Web II.
actual val enderecoDaApi: String? = when (val valor = System.getenv("TAREFAS_API")) {
    null, "" -> "http://localhost:8080"
    "memoria" -> null
    else -> valor
}
