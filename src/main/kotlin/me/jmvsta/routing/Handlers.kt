package me.jmvsta.routing

import me.jmvsta.data.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class SequenceGenerator(@Autowired private val mongoOperations: ReactiveMongoOperations) {

    fun <T> generateSequence(clazz: Class<T>): Long {
        var sequenceName: String? = null
        when (clazz) {
            Player::class.java -> sequenceName = "players_sequence"
            Figure::class.java -> sequenceName = "figures_sequence"
            Game::class.java -> sequenceName = "games_sequence"
            Inventory::class.java -> sequenceName = "inventories_sequence"
        }
        return mongoOperations.findAndModify(
            query(where("_id").`is`(sequenceName)),
            Update().inc("seq", 1), options().returnNew(true).upsert(true),
            DatabaseSequence::class.java
        ).toFuture().get().seq
    }
}

@Component
class GamesHandler {

    @Qualifier("gamesRepository")
    @Autowired private lateinit var gamesRepository: ReactiveMongoRepository<Game, Long>

    @Qualifier("playersRepository")
    @Autowired private lateinit var playersRepository: ReactiveMongoRepository<Player, Long>

    @Autowired private lateinit var sequenceGenerator: SequenceGenerator

    fun create(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Game::class.java)
            .flatMap { body ->
                body.id = sequenceGenerator.generateSequence(Game::class.java)
                body.players?.forEach { player ->
                    player.id = sequenceGenerator.generateSequence(Player::class.java)
                    playersRepository.save(player)
                        .subscribe { result -> println("Entity has been saved: $result") }
                }
                body.createdBy.id = sequenceGenerator.generateSequence(Player::class.java)
                playersRepository.save(body.createdBy)
                    .subscribe { result -> println("Entity has been saved: $result") }
                return@flatMap ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(gamesRepository.save(body), Game::class.java)
            }
    }

     fun read(request: ServerRequest): Mono<ServerResponse> {
         return ServerResponse
             .ok()
             .contentType(MediaType.APPLICATION_JSON)
             .body(gamesRepository.findById(request.pathVariable("id").toLong()), Game::class.java)
     }

    fun readAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(gamesRepository.findAll(), Game::class.java)
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Game::class.java)
            .flatMap { body -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gamesRepository.save(body), Game::class.java)
            }
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(gamesRepository.deleteById(request.pathVariable("id").toLong()), Game::class.java)
    }

}

@Component
class FiguresHandler {

    @Qualifier("figuresRepository")
    @Autowired lateinit var figuresRepository: ReactiveMongoRepository<Figure, Long>

    fun create(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Figure::class.java)
            .flatMap { body -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(figuresRepository.save(body), Figure::class.java)
            }
    }

     fun read(request: ServerRequest): Mono<ServerResponse> {
         return ServerResponse
             .ok()
             .contentType(MediaType.APPLICATION_JSON)
             .body(figuresRepository.findById(request.pathVariable("id").toLong()), Figure::class.java)
    }

    fun readAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(figuresRepository.findAll(), Figure::class.java)
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Figure::class.java)
            .flatMap { body -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(figuresRepository.save(body), Figure::class.java)
            }
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(figuresRepository.deleteById(request.pathVariable("id").toLong()), Figure::class.java)
    }

}

@Component
class PlayersHandler {

    @Qualifier("playersRepository")
    @Autowired lateinit var playersRepository: ReactiveMongoRepository<Player, Long>

    @Autowired private lateinit var sequenceGenerator: SequenceGenerator

    fun create(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Player::class.java)
            .flatMap { body ->
                body.id = sequenceGenerator.generateSequence(Player::class.java)
                return@flatMap ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(playersRepository.save(body), Player::class.java)
            }
    }

    fun read(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(playersRepository.findById(request.pathVariable("id").toLong()), Player::class.java)
    }

    fun readAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(playersRepository.findAll(), Player::class.java)
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Player::class.java)
            .flatMap { body -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(playersRepository.save(body), Player::class.java)
            }
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(playersRepository.deleteById(request.pathVariable("id").toLong()), Player::class.java)
    }

}