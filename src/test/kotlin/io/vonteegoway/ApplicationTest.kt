package io.vonteegoway

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.vonteegoway.plugins.configureRouting
import kotlin.test.Test
import kotlin.test.assertEquals


class PrimaryRoutingTests {
    @Test
    fun testPlayers() = testApplication {
        application { configureRouting() }
        client.get("/api/v1/players").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testTeams() = testApplication {
        application { configureRouting() }
        client.get("/api/v1/teams").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testTeamAssignments() = testApplication {
        application { configureRouting() }
        client.get("/api/v1/teamAssignments").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testPositions() = testApplication {
        application { configureRouting() }
        client.get("/api/v1/positions").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testPlayerPreferences() = testApplication {
        application { configureRouting() }
        client.get("/api/v1/playerPreferences").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}
