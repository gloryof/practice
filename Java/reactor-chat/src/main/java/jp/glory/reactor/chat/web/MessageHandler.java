package jp.glory.reactor.chat.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.MessageNotify;
import jp.glory.reactor.chat.infra.notify.NotifyEventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * メッセージハンドラ.
 * @author gloryof
 *
 */
@Component
public class MessageHandler {

    private final MessageNotify notify = new MessageNotify();

    private final Flux<Message> flux;

    public MessageHandler() {

        flux = Flux.<Message>create(sink -> {

            notify.addListener(new NotifyEventListener<Message>() {

                @Override
                public void addData(Message data) {

                    sink.next(data);
                }
            });
        })
        .log();
    }

    public Mono<ServerResponse> getMessages(final ServerRequest request) {

        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(flux, Message.class);
    }

    public Mono<ServerResponse> addMessage(final ServerRequest request) {

        String value = request.queryParam("message").orElse("");
        notify.addMessage(new Message(new Name("test-user"), value));

        return ServerResponse.ok().build();
    }
}
