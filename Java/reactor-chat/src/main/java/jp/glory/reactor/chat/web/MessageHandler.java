package jp.glory.reactor.chat.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.MessageNotify;
import jp.glory.reactor.chat.usecase.message.AddMessage;
import jp.glory.reactor.chat.web.request.MessageRequest;
import reactor.core.publisher.Mono;

/**
 * メッセージハンドラ.
 * @author gloryof
 *
 */
@Component
public class MessageHandler {

    /**
     * メッセージ通知.
     */
    private final MessageNotify notify;

    /**
     * メッセージ追加ユースケース.
     */
    private final AddMessage addMessage;

    /**
     * コンストラクタ.
     * @param addMessage メッセージ追加ユースケース.
     * @param notify メッセージ通知
     */
    public MessageHandler(final AddMessage addMessage, final MessageNotify notify) {

        this.addMessage = addMessage;
        this.notify = notify;
    }

    /**
     * メッセージ一覧を取得する.<br>
     * <pre>
     * curl -v http://localhost:8080/messages
     * </pre>
     * で待つ。
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> getMessages(final ServerRequest request) {

        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(notify.getFlux(), Message.class);
    }

    /**
     * メッセージを追加する.<br>
     * <pre>
     * curl -X POST -v http://localhost:8080/messages/add --data "{ \"message\" : \"value\" }"  -H 'Content-Type: application/stream+json' 
     * </pre>
     * でリクエスト送信。
     * 
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> addMessage(final ServerRequest request) {

        request.bodyToMono(MessageRequest.class)
            .map(v -> new Message(new Name("test-user"), v.getMessage()))
            .subscribe(addMessage::add);

        return ServerResponse.ok().build();
    }
}
