package jp.glory.reactor.chat.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.MessageNotify;
import jp.glory.reactor.chat.usecase.message.AddMessage;
import jp.glory.reactor.chat.usecase.message.AddMessageDelay;
import jp.glory.reactor.chat.web.request.MessageRequest;
import jp.glory.reactor.chat.web.response.MessageResponse;
import reactor.core.publisher.Flux;
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
     * メッセージ追加.
     */
    private final AddMessage addMessage;

    /**
     * メッセージ遅延追加.
     */
    private final AddMessageDelay addMessageDelay;

    /**
     * コンストラクタ.
     * @param addMessage メッセージ追加
     * @param addMessageDelay メッセージ遅延追加
     * @param notify メッセージ通知
     */
    public MessageHandler(final AddMessage addMessage, final AddMessageDelay addMessageDelay,
            final MessageNotify notify) {

        this.addMessage = addMessage;
        this.addMessageDelay = addMessageDelay;
        this.notify = notify;
    }

    /**
     * メッセージ一覧を取得する.
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> getMessages(final ServerRequest request) {

        Flux<MessageResponse> flux = notify.getFlux().map(this::convertResponse);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, MessageResponse.class);
    }

    /**
     * メッセージを追加する.
     * 
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> addMessage(final ServerRequest request) {

        request.bodyToMono(MessageRequest.class)
            .map(this::covertMessage)
            .subscribe(addMessage);

        return ServerResponse.ok().build();
    }

    /**
     * メッセージを遅延させて連続で追加する.
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> addMessageDelay(final ServerRequest request) {

        request.bodyToMono(MessageRequest.class)
            .map(this::covertMessage)
            .subscribe(addMessageDelay);

        return ServerResponse.ok().build();
    }

    /**
     * メッセージに変換する.
     * @param request リクエスト
     * @return メッセージ
     */
    private Message covertMessage(final MessageRequest request) {

        return new Message(new Name(request.getUsername()), request.getMessage());
    }

    /**
     * メッセージレスポンスに変換する.
     * @param message メッセージ
     * @return レスポンス
     */
    private MessageResponse convertResponse(final Message message) {

        final MessageResponse response = new MessageResponse();

        response.setName(message.getName().getValue());
        response.setContent(message.getContent());

        return response;
    }
}
