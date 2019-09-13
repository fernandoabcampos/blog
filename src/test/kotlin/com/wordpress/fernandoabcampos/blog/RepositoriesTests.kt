package com.wordpress.fernandoabcampos.blog

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository,
        val articleRepository: ArticleRepository) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val fernando = User("fernando.barbeiro", "Fernando", "Barbeiro")
        entityManager.persist(fernando)
        val article = Article("Testing Spring Framework + Kotlin", "This is an interesting journey ... ", "Lorem ipsum", fernando)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)
        Assertions.assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val fernando = User("fernando.barbeiro", "Fernando", "Barbeiro")
        entityManager.persist(fernando)
        entityManager.flush()
        val user = userRepository.findByLogin(fernando.login)
        Assertions.assertThat(user).isEqualTo(fernando)
    }

}