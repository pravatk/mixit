package mixit.integration

import mixit.model.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTests(@Autowired val client: WebTestClient) {

    @Test
    fun `Create a new user`() {
        client.post().uri("/api/user/").accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                .syncBody(User("brian", "Brian", "Clozel", "bc@gm.com"))
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody()
                .jsonPath("\$.login").isEqualTo("brian")
    }

    @Test
    fun `Find Dan North`() {
        client.get().uri("/api/user/tastapod").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody()
                .jsonPath("\$.lastname").isEqualTo("North")
                .jsonPath("\$.firstname").isEqualTo("Dan")
                .jsonPath("\$.role").isEqualTo("USER")
    }

    @Test
    fun `Find Guillaume Ehret staff member`() {
        client.get().uri("/api/staff/guillaumeehret").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody()
                .jsonPath("\$.lastname").isEqualTo("Ehret")
                .jsonPath("\$.firstname").isEqualTo("Guillaume")
                .jsonPath("\$.role").isEqualTo("STAFF")
    }

    @Test
    fun `Find all staff members`() {
        client.get().uri("/api/staff/").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBodyList<User>()
                .hasSize(11)
    }

    @Test
    fun `Find Zenika Lyon`() {
        client.get().uri("/api/user/ZenikaLyon").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody()
                .jsonPath("\$.lastname").isEqualTo("Tournayre")
                .jsonPath("\$.firstname").isEqualTo("Louis")
                .jsonPath("\$.role").isEqualTo("USER")
    }

}
