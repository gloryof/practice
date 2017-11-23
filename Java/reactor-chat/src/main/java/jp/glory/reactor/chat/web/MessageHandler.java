package jp.glory.reactor.chat.web;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.value.Name;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * メッセージハンドラ.
 * @author gloryof
 *
 */
@Component
public class MessageHandler {

    public Mono<ServerResponse> getMessages(final ServerRequest requeset) {

        final Message message1 = new Message(new Name("user1"), "content1");
        final Message message2 = new Message(new Name("user2"), "content2");

        Flux<Message> messageFlux = Flux.just(message1, message2);
        return ServerResponse.ok().body(messageFlux, Message.class);
    }
}
