package jp.glory.sample.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jp.glory.sample.ApplicationRoot;
import jp.glory.sample.nonweb.CodeChecker;

@RunWith(Enclosed.class)
public class SampleApiTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(ApplicationRoot.class)
    @WebAppConfiguration
    public static class Mockitoしない場合 {

        @Autowired
        private WebApplicationContext wac;

        private MockMvc mockMvc;

        @Before
        public void setUp() {

            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        public void ステータスOKが返るパターン() throws Exception {

            CommonAssertion.assretValid(mockMvc);
        }

        @Test
        public void OriginalExceptionが投げられてステータスBAD_REQUESTが返るパターン() throws Exception {

            CommonAssertion.assretOriginalInvalid(mockMvc);
        }

        @Test
        public void TypeMismatchExceptionが投げられてステータスBAD_REQUESTが返るパターン() throws Exception {

            CommonAssertion.assretBindInvalid(mockMvc);
        }
            
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(ApplicationRoot.class)
    @WebAppConfiguration
    public static class Mockitoする場合 {

        @Rule
        public final MockitoRule rule = MockitoJUnit.rule();

        private MockMvc mockMvc;

        @InjectMocks
        private SampleApi sut = null;
        
        @Autowired
        private HandlerExceptionResolver handlerExceptionResolver;

        @Mock
        private CodeChecker mockChecker = null;


        @Before
        public void setUp() {

            this.mockMvc = MockMvcBuilders.standaloneSetup(sut)
                    .setHandlerExceptionResolvers(handlerExceptionResolver).build();
        }

        @Test
        public void ステータスOKが返るパターン() throws Exception {

            Mockito.when(mockChecker.isValid(9)).thenReturn(true);

            CommonAssertion.assretValid(mockMvc);
        }

        @Test
        public void OriginalExceptionが投げられてステータスBAD_REQUESTが返るパターン() throws Exception {

            Mockito.when(mockChecker.isValid(10)).thenReturn(false);
            CommonAssertion.assretOriginalInvalid(mockMvc);
        }

        @Test
        public void TypeMismatchExceptionが投げられてステータスBAD_REQUESTが返るパターン() throws Exception {

            Mockito.when(mockChecker.isValid(Mockito.anyInt())).thenReturn(true);
            CommonAssertion.assretBindInvalid(mockMvc);
        }
    }

    private static class CommonAssertion {
     
        public static void assretValid(MockMvc mvc) throws Exception {

            mvc.perform(get("/sample/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newId", is(900)));
        }
     
        public static void assretOriginalInvalid(MockMvc mvc) throws Exception {

            mvc.perform(get("/sample/10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Throw OriginalException!!")));
        }
     
        public static void assretBindInvalid(MockMvc mvc) throws Exception {

            mvc.perform(get("/sample/test"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Throw TypeMismatchException!!")));
        }
    }
}
