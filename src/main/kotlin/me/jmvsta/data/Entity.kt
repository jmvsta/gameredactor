package me.jmvsta.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

abstract class Entity {
    abstract var id: Long
}

@Document(collection = "figure")
data class Figure(
    @Id override var id: Long,
    var name: String,
    var color: Short,
    var type: FigureType
): Entity()

@Document(collection = "game")
data class Game(
    @Id override var id: Long,
    var gameState: GameState?,
    var createdBy: Player,
    var players: List<Player>?,
    var currentPlayerId: Long,
    var chatHistory: ChatHistory?
): Entity()

@Document(collection = "player")
data class Player(
    @Id override var id: Long,
    var login: String,
    var inventory: Inventory?
): Entity()

@Document(collection = "inventory")
data class Inventory(
    @Id override var id: Long
): Entity()

@Document(collection = "game_state")
data class GameState(
    @Id override var id: Long
): Entity()

@Document(collection = "database_sequences")
data class DatabaseSequence (
    @Id var id: String,
    var seq: Long
)

data class ChatHistory(
    var playerId: Long,
    var type: String,
    var data: Data,
    var date: LocalDateTime
)

data class Data(
    var text: String
)
enum class FigureType {
    KING,
    QUEEN,
    KNIGHT,
    ROOK,
    BISHOP,
    CHECKER,
    PAWN
}