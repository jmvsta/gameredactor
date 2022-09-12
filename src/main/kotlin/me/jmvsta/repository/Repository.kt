package me.jmvsta.repository

import me.jmvsta.data.Figure
import me.jmvsta.data.Game
import me.jmvsta.data.Player
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GamesRepository: ReactiveMongoRepository<Game, Long>

@Repository
interface FiguresRepository: ReactiveMongoRepository<Figure, Long>

@Repository
interface PlayersRepository: ReactiveMongoRepository<Player, Long>