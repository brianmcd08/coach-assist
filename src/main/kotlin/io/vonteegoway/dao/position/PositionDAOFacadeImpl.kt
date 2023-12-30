package io.vonteegoway.dao.position

import io.vonteegoway.models.Position
import io.vonteegoway.models.Positions
import kotlinx.coroutines.runBlocking

class PositionDAOFacadeImpl : PositionDAOFacade {
    private fun resultRowToPosition(row: Positions) = Position(
        id = row.id,
        name = row.name,
        abbreviation = row.abbreviation
    )

    override suspend fun allPositions(): List<Position> =
        Positions.values().map(::resultRowToPosition)

}

val positionDao: PositionDAOFacade = PositionDAOFacadeImpl().apply {runBlocking {}
}