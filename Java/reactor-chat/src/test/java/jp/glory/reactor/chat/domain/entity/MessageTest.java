package jp.glory.reactor.chat.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.value.Name;

class MessageTest {

    private Message sut  = null;

    @DisplayName("値が設定されている場合")
    @Nested
    class WhenValueSet {

        @BeforeEach
        void setUp() {

            sut = new Message(new Name("test"), "test-content");
        }

        @DisplayName("appendPrefixが呼ばれた場合")
        @Nested
        class WhenCallPrefix {

            private Message actual = null;

            @BeforeEach
            void setUp() {

                actual = sut.appendPrefix(1);
            }

            @DisplayName("元のオブジェクトの値は変わらない")
            @Test
            void assertImmutable() {

                assertEquals("test", sut.getName().getValue());
                assertEquals("test-content", sut.getContent());
            }

            @DisplayName("コンテントの内容だけ変更されたオブジェクトが生成される")
            @Test
            void assertActual() {

                assertEquals("test", actual.getName().getValue());
                assertEquals("test-content-1", actual.getContent());
            }
        }
    }
    

}
