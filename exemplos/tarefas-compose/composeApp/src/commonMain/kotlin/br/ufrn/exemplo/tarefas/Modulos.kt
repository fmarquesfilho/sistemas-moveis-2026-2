package br.ufrn.exemplo.tarefas

import br.ufrn.exemplo.tarefas.apresentacao.TarefasViewModel
import br.ufrn.exemplo.tarefas.dados.RepositorioRemoto
import br.ufrn.exemplo.tarefas.dados.criarClienteHttp
import br.ufrn.exemplo.tarefas.dados.RepositorioEmMemoria
import br.ufrn.exemplo.tarefas.dados.enderecoDaApi
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// A raiz de composição: o único lugar que conhece as três camadas e as liga.
// Trocar a implementação do repositório (memória, rede) é trocar uma linha aqui.
val moduloTarefas = module {
    single { criarClienteHttp() }
    // Sem endereço (TAREFAS_API=memoria no desktop), o app roda sem a API de Web II.
    single<RepositorioDeTarefas> {
        val endereco = enderecoDaApi
        if (endereco == null) RepositorioEmMemoria() else RepositorioRemoto(get(), endereco)
    }
    viewModelOf(::TarefasViewModel)          // o Koin passa o repositório ao construtor
}
