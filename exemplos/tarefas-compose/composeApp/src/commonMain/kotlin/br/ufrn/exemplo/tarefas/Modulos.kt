package br.ufrn.exemplo.tarefas

import br.ufrn.exemplo.tarefas.apresentacao.TarefasViewModel
import br.ufrn.exemplo.tarefas.dados.CacheDeTarefas
import br.ufrn.exemplo.tarefas.dados.RepositorioComCache
import br.ufrn.exemplo.tarefas.dados.RepositorioRemoto
import br.ufrn.exemplo.tarefas.dados.criarDataStore
import br.ufrn.exemplo.tarefas.dados.criarClienteHttp
import br.ufrn.exemplo.tarefas.dados.RepositorioEmMemoria
import br.ufrn.exemplo.tarefas.dados.enderecoDaApi
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// A raiz de composição: o único lugar que conhece as três camadas e as liga.
// Trocar a implementação do repositório (memória, rede) é trocar uma linha aqui.
// O diretório de dados vem de fora: cada plataforma sabe onde pode gravar arquivos.
fun moduloTarefas(diretorioDeDados: String) = module {
    single { criarClienteHttp() }
    single { CacheDeTarefas(criarDataStore(diretorioDeDados)) }
    // Sem endereço (TAREFAS_API=memoria no desktop), o app roda sem a API de Web II.
    single<RepositorioDeTarefas> {
        val endereco = enderecoDaApi
        if (endereco == null) RepositorioEmMemoria()
        else RepositorioComCache(RepositorioRemoto(get(), endereco), get())
    }
    viewModelOf(::TarefasViewModel)          // o Koin passa o repositório ao construtor
}
