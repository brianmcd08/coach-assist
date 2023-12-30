package io.vonteegoway.dao.position

import io.vonteegoway.models.*

interface PositionDAOFacade {
    suspend fun allPositions(): List<Position>
}
