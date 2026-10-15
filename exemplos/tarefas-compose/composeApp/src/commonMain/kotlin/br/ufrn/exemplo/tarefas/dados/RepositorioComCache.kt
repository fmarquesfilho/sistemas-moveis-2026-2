package br.ufrn.exemplo.tarefas.dados

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.ufrn.exemplo.tarefas.dominio.FalhaNoRepositorio
import br.ufrn.exemplo.tarefas.dominio.Listagem
import br.ufrn.exemplo.tarefas.dominio.RepositorioDeTarefas
import br.ufrn.exemplo.tarefas.dominio.Tarefa
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

// A última lista recebida, guardada num arquivo do aparelho com o DataStore. Sobrevive ao
// fechamento do app. Guarda o JSON dos DTOs, o mesmo formato que vem da API.
class CacheDeTarefas(private val dataStore: DataStore<Preferences>) {
    private val chave = stringPreferencesKey("ultima_lista")

    suspend fun salvar(tarefas: List<Tarefa>) {
        val dtos = tarefas.map { TarefaDto(it.id, it.titulo, it.feita) }
        dataStore.edit { it[chave] = Json.encodeToString(dtos) }
    }

    suspend fun ler(): List<Tarefa>? =
        dataStore.data.first()[chave]?.let { json -> Json.decodeFromString<List<TarefaDto>>(json).map { it.paraDominio() } }
}

// Um repositório que usa outro: pede ao remoto e guarda o resultado; sem conexão, devolve
// a última lista guardada. O ViewModel não sabe que existe cache: só vê doCache = true.
class RepositorioComCache(
    private val remoto: RepositorioDeTarefas,
    private val cache: CacheDeTarefas,
) : RepositorioDeTarefas {

    override suspend fun listar(): Listagem = try {
        remoto.listar().also { cache.salvar(it.tarefas) }
    } catch (e: FalhaNoRepositorio.SemConexao) {
        val guardadas = cache.ler() ?: throw e       // nunca carregou nada: o erro continua
        Listagem(guardadas, doCache = true)
    }

    override suspend fun adicionar(titulo: String): Tarefa =
        remoto.adicionar(titulo).also { nova -> cache.salvar(cache.ler().orEmpty() + nova) }
}

// Um DataStore por arquivo e por processo: o Koin cria uma vez só (single).
fun criarDataStore(diretorio: String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(produceFile = { "$diretorio/tarefas.preferences_pb".toPath() })
