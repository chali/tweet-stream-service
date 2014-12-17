package cz.chali.twitter.service

import org.springframework.stereotype.Service

@Service
class HiService {
    def say(text: String): Unit = println(text)
}
