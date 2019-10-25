package com.wordpress.fernandoabcampos.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository) = ApplicationRunner {

        val user = userRepository.save(User("fernando.barbeiro", "Fernando", "Barbeiro"))
        articleRepository.save(Article(
                title = "The starting with a new programming language",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = user
        ))
        articleRepository.save(Article(
                title = "Kotlin basics",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = user
        ))
    }
}