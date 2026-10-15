package br.ufrn.exemplo.tarefas.dados

import br.ufrn.exemplo.tarefas.dominio.FalhaNoRepositorio
import kotlinx.coroutines.test.runTest
import kotlinx.io.files.SystemTemporaryDirectory
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration

// O cache com um repositório em memória no lugar da rede, e o DataStore num arquivo temporário.
class RepositorioComCacheTest {

    // Um diretório novo por teste: o DataStore não aceita dois objetos para o mesmo arquivo.
    private fun cache() = CacheDeTarefas(criarDataStore("$SystemTemporaryDirectory/cache-${Random.nextLong()}"))

    @Test
    fun comConexaoDevolveDoServidorEGuarda() = runTest {
        val remoto = RepositorioEmMemoria(Duration.ZERO)
        val cache = cache()
        val listagem = RepositorioComCache(remoto, cache).listar()
        assertFalse(listagem.doCache)
        assertEquals(listagem.tarefas, cache.ler())
    }

    @Test
    fun semConexaoDevolveAUltimaListaGuardada() = runTest {
        val remoto = RepositorioEmMemoria(Duration.ZERO)
        val repo = RepositorioComCache(remoto, cache())
        repo.listar()                       // guarda
        remoto.falhar = true                // a rede cai
        val listagem = repo.listar()
        assertTrue(listagem.doCache)
        assertEquals(2, listagem.tarefas.size)
    }

    @Test
    fun semConexaoESemNadaGuardadoContinuaComErro() = runTest {
        val repo = RepositorioComCache(RepositorioEmMemoria(Duration.ZERO, falhar = true), cache())
        assertFailsWith<FalhaNoRepositorio.SemConexao> { repo.listar() }
    }
}
