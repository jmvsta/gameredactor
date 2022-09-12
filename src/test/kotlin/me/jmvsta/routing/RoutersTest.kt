package me.jmvsta.routing

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.jmvsta.data.*
import me.jmvsta.data.FigureType.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerResponse
import java.time.LocalDateTime


@WebFluxTest
@Import(GamesRouterConfig::class, GamesHandler::class)
class GamesRouterTest(@Autowired private val client: WebTestClient) {

    @MockkBean
    private lateinit var handler : GamesHandler

    private fun aGame() = Game(
            id = 0L,
            gameState = GameState(0L),
            createdBy = Player(0L, "login", Inventory(0L)),
            players = listOf(),
            currentPlayerId = 0L,
            chatHistory = ChatHistory(0L, "type", Data("data"), LocalDateTime.now())
        )

    @Test
    fun whenGetAllCalled_thenOk() {
        every {
            handler.readAll(any())
        } answers  {
            ServerResponse.ok().bodyValue(aGame())
        }

        client.get()
            .uri("/api/games")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenGetCalled_thenOk() {
        every {
            handler.read(any())
        } answers  {
            ServerResponse.ok().bodyValue(aGame())
        }

        client.get()
            .uri("/api/games/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenPostCalled_thenOk() {
        every {
            handler.create(any())
        } answers  {
            ServerResponse.ok().bodyValue(aGame())
        }

        client.post()
            .uri("/api/games")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenPutCalled_thenOk() {
        every {
            handler.update(any())
        } answers  {
            ServerResponse.ok().bodyValue(aGame())
        }

        client.put()
            .uri("/api/games/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenDeleteCalled_thenOk() {
        every {
            handler.delete(any())
        } answers  {
            ServerResponse.ok().bodyValue(aGame())
        }

        client.delete()
            .uri("/api/games/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

}

@WebFluxTest
@Import(FiguresRouterConfig::class, FiguresHandler::class)
class FiguresRouterTest(@Autowired private val client: WebTestClient) {

    @MockkBean
    private lateinit var handler : FiguresHandler

    private fun aFigure() = Figure(
        id = 0L,
        name = "name",
        color = 1,
        type = CHECKER
    )

    @Test
    fun whenGetAllCalled_thenOk() {
        every {
            handler.readAll(any())
        } answers  {
            ServerResponse.ok().bodyValue(aFigure())
        }

        client.get()
            .uri("/api/figures")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenGetCalled_thenOk() {
        every {
            handler.read(any())
        } answers  {
            ServerResponse.ok().bodyValue(aFigure())
        }

        client.get()
            .uri("/api/figures/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenPostCalled_thenOk() {
        every {
            handler.create(any())
        } answers  {
            ServerResponse.ok().bodyValue(aFigure())
        }

        client.post()
            .uri("/api/figures")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenPutCalled_thenOk() {
        every {
            handler.update(any())
        } answers  {
            ServerResponse.ok().bodyValue(aFigure())
        }

        client.put()
            .uri("/api/figures/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenDeleteCalled_thenOk() {
        every {
            handler.delete(any())
        } answers  {
            ServerResponse.ok().bodyValue(aFigure())
        }

        client.delete()
            .uri("/api/figures/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

}

@WebFluxTest
@Import(PlayersRouterConfig::class, PlayersHandler::class)
class PlayersRouterTest(@Autowired private val client: WebTestClient) {

    @MockkBean
    private lateinit var handler : PlayersHandler

    private fun aPlayer() = Player(
        id = 0L,
        login = "login",
        inventory = null
    )

    @Test
    fun whenGetAllCalled_thenOk() {
        every {
            handler.readAll(any())
        } answers  {
            ServerResponse.ok().bodyValue(aPlayer())
        }

        client.get()
            .uri("/api/players")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenGetCalled_thenOk() {
        every {
            handler.read(any())
        } answers  {
            ServerResponse.ok().bodyValue(aPlayer())
        }

        client.get()
            .uri("/api/players/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenPostCalled_thenOk() {
        every {
            handler.create(any())
        } answers  {
            ServerResponse.ok().bodyValue(aPlayer())
        }

        client.post()
            .uri("/api/players")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenPutCalled_thenOk() {
        every {
            handler.update(any())
        } answers  {
            ServerResponse.ok().bodyValue(aPlayer())
        }

        client.put()
            .uri("/api/players/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun whenDeleteCalled_thenOk() {
        every {
            handler.delete(any())
        } answers  {
            ServerResponse.ok().bodyValue(aPlayer())
        }

        client.delete()
            .uri("/api/players/542c2b97bac0505474109b41")
            .exchange()
            .expectStatus()
            .isOk
    }

}