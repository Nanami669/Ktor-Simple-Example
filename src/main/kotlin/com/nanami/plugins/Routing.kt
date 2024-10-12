package com.nanami.plugins

import com.nanami.HttpResult
import com.nanami.TmdbService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticResources
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.h4
import kotlinx.html.head
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.submitInput
import kotlinx.html.textInput
import kotlinx.html.title

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title {
                        +"Nyaaaaa"
                    }
                }
                body {
                    h1 {
                        +"Hellonyaaaaaaa"
                    }
                    h3 {
                        +"Finally I made a ktor example thing :3"
                    }
                    p {
                        +"Go to "
                        a {
                            href = "/tmdb"
                            +"/tmdb"
                        }
                        +" to see the Tmdb client in action"
                    }
                    p {
                        +"Go to "
                        a {
                            href = "/Lucy"
                            +"/Lucy"
                        }
                        +" to play Lucy roulette!"
                    }
                }
            }
        }
        route("tmdb") {
            get {
                when (val result = TmdbService.getStarWars()) {
                    is HttpResult.Success ->
                        call.respond(result.movie)
                    is HttpResult.Failure ->
                        call.respondText(result.error)
                }
            }
        }

        route("Lucy") {
            staticResources("/static", "static")
            post("/roulette") {
                val number =
                    try {
                        call.receiveParameters()["number"]!!.toInt()
                    } catch (e: NumberFormatException) {
                        0
                    }

                if (number == 69) {
                    call.respondRedirect("https://x.com/i/status/1842904478120325540")
                }
                call.respondHtml {
                    body {
                        img {
                            src = "/Lucy/static/$number.jpeg"
                        }
                    }
                }
            }

            get {
                call.respondHtml(HttpStatusCode.OK) {
                    head {
                        title {
                            +"Lucy Roulette"
                        }
                    }
                    body {
                        h1 {
                            +"Input a number 1-5"
                        }
                        h2 {
                            +"Don't enter anything else please"
                        }
                        h3 {
                            +"I'm serious..."
                        }
                        h4 {
                            +"especially not 69 >///<"
                        }
                        form(action = "/Lucy/roulette", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                            p {
                                +"1-5:"
                                textInput {
                                    name = "number"
                                    value = "0"
                                }
                                submitInput {
                                    value = "Roulette!"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
