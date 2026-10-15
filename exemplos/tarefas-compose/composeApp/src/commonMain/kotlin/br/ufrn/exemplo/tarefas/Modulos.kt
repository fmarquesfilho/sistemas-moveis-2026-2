package br.ufrn.exemplo.tarefas

import br.ufrn.exemplo.tarefas.apresentacao.TarefasViewModel
import br.ufrn.exemplo.tarefas.dados.RepositorioEmMemoria
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// A raiz de composição: o único lugar que conhece as três camadas e as liga.
// Trocar a implementação do repositório (memória, rede) é trocar uma linha aqui.
val moduloTarefas = module {
    single<RepositorioDeTarefas> { RepositorioEmMemoria() }
    viewModelOf(::TarefasViewModel)          // o Koin passa o repositório ao construtor
}
