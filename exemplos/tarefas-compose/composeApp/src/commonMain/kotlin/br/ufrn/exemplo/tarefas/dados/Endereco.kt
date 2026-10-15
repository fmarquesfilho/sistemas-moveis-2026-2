package br.ufrn.exemplo.tarefas.dados

// Onde está a API de Tarefas. No desktop, a própria máquina; no emulador Android, a
// máquina hospedeira tem o endereço especial 10.0.2.2. Nulo quer dizer "sem API": o app
// usa o repositório em memória (no desktop, com TAREFAS_API=memoria).
expect val enderecoDaApi: String?
