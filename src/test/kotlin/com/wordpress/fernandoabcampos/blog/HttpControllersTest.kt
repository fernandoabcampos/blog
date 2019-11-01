package com.wordpress.fernandoabcampos.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @MockkBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val fernando = User("fernando.barbeiro", "Fernando", "Barbeiro")
        val articleSpring = Article("Testing Spring Framework + Kotlin", "This is an interesting journey ... ", "Lorem ipsum", fernando)
        val articleKotlin = Article("Kotlin basics", "Lorem ipsum", "dolor sit amet", fernando)
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(articleSpring, articleKotlin)
        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].author.login").value(fernando.login))
                .andExpect(jsonPath("\$.[0].slug").value(articleSpring.slug))
                .andExpect(jsonPath("\$.[1].author.login").value(fernando.login))
                .andExpect(jsonPath("\$.[1].slug").value(articleKotlin.slug))
    }

    @Test
    fun `List users`() {
        val fernando = User("fernando.barbeiro", "Fernando", "Barbeiro")
        val mel = User("mel.pacoca", "Mel", "Paçoca")
        every { userRepository.findAll() } returns listOf(fernando, mel)
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].login").value(fernando.login))
                .andExpect(jsonPath("\$.[1].login").value(mel.login))
    }
}